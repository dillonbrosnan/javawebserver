package ResponseFactory;

import Request.*;
import Exceptions.*;
import Authorization.*;
import Date.*;

import java.io.File;
import java.time.LocalDateTime;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.lang.Runtime;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;


public class ResponseFactory {

  public static Response getResponse( Request request, Resource resource ) throws IOException{
    Response response = null;
    Path filePath = Paths.get( resource.getAbsolutePath() );
    FormattedDate modDate;
    String requestVerb = request.getVerb();

    if( resource.isProtected() ){
      response = checkAuthorization( request, resource );
      if( response != null ){
        return response;
      }
    }

    if( !requestVerb.equals( "PUT" ) && !resource.exists() ){
      
      return new FileNotFoundResponse( resource );
    }

    if( requestVerb.equals( "GET" ) || requestVerb.equals( "HEAD" ) || 
      requestVerb.equals( "POST" ) ){
      if( requestVerb.equals( "POST" ) ){
        Files.write( filePath, request.getBody() );
      }

      modDate = new FormattedDate( Files.getLastModifiedTime( filePath ).toMillis() );
      if( request.isModifiedSince() && request.getModifiedDate().equals( modDate.toString() ) ) {
        response = new NotModifiedResponse( resource );
      } 
      else {
        if ( resource.isScript() ){
          try{
            response = runScript( request, resource );
            if ( response.getStatusCode() == 500 ){
              return response;
            }
          } 
          catch (Exception e){
            System.out.println( e );
          }
        }
        else{
          response = new OKResponse( resource );
        }    
        response.setVerb( requestVerb );
      }
      response.setOtherHeaders( "Last-Modified", modDate.toString() );
      response.setOtherHeaders( "Cache-Control", "max-age=3600" );
      FormattedDate expire = new FormattedDate( LocalDateTime.now().plusSeconds( 3600 ) );
      response.setOtherHeaders( "Expires", expire.toString() );
    }

    else if( requestVerb.equals( "PUT" ) ){
      Files.write( filePath, request.getBody() );
      // File file = new File( resource.getAbsolutePath() );
      // FileOutputStream fOS = new FileOutputStream( file );
      // fOS.write( request.getBody() );
      // fOS.close();
      if( Files.exists( filePath ) ){
        response = new CreatedResponse( resource );
      } else {
        response = new InternalServerErrorResponse( resource );
      }     

      response.setOtherHeaders( "Location", request.getUri() );
    }

    else if( requestVerb.equals( "Delete" ) ){
      Files.delete( filePath );
      response = new NoContentResponse( resource );
    }

    return response;
  }

  private static Response checkAuthorization( Request request, Resource resource ){
    Response response = null;
    Htaccess htaccess = new Htaccess( resource.getAccessFilePath() );
    if( !request.headerKeyExists( "Authorization" ) ) {
      response = new UnauthorizedResponse( resource );
      response.setOtherHeaders( "WWW-Authenticate", "Basic realm = \"" + htaccess.getAuthName() + "\"");
      return response;
    }
    else if( !htaccess.isAuthorized( request.getHeader( "Authorization" ) ) ) {
      response = new ForbiddenResponse( resource );
      return response;
    }
    return response;    
  }

  private static Response runScript( Request request, Resource resource ) throws Exception {
    Response response = new OKResponse( resource );
    String command = resource.getAbsolutePath();  
    ProcessBuilder processBuilder = new ProcessBuilder( command );
    Map<String,String> env = processBuilder.environment();
    Map<String,String> headers = request.getHeaders();
    String value = "";
    String[] queryString;

    if(request.getUri().contains("?")){
      queryString = request.getUri().split("?");
      value = queryString[1];
      env.put("HTTP_QUERY_STRING",value);
    }            
    env.put("HTTP_SERVER_PROTOCOL",request.getHttpVersion());

    for ( String key: headers.keySet() ){
      value = headers.get( key ).toString();
      String envpString = "HTTP_" + key.toUpperCase();
      env.put(envpString, value);
    }
    
    Process process = processBuilder.start();
    if( request.getVerb() == "PUT" || request.getVerb() == "POST" ){
      OutputStream scriptInput = process.getOutputStream();
      scriptInput.write( request.getBody() ); 
      scriptInput.flush();
    }
    InputStream scriptOutput = process.getInputStream();
    response.setBody( scriptOutput.readAllBytes() );
    process.waitFor();
    if ( process.exitValue() ==  0){
      return response;
    }else{
      return new InternalServerErrorResponse( resource );
    }
  }
}