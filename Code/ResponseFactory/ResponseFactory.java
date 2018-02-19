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


public class ResponseFactory {
  public static Response getResponse( Request request, Resource resource ) throws IOException{
    Response response = null;
    Path filePath = Paths.get( resource.getAbsolutePath() );
    FormattedDate modDate;
    String requestVerb = request.getVerb();
    if( resource.isProtected() ){
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
    }
    if( !requestVerb.equals( "PUT " ) && !resource.exists() ){
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
      } else {
        response = new OKResponse( resource );
        if ( resource.isScript() ){
          try{
            String command = resource.getAbsolutePath();
            System.out.println( "Command: " + command);
            Process process = Runtime.getRuntime().exec( command, request.getEnvp() );
            InputStream scriptOutput = process.getInputStream();
            response.setBody( scriptOutput.readAllBytes() );
            response.setOtherHeaders("Content-Type", "text/html");
            response.setOtherHeaders("Content-Length", Long.toString( response.getBodyLength() ) );
            process.waitFor();
            if ( process.exitValue() ==  0){
              System.out.println("successful script");
            }else{
              System.out.println("unsucessful script");
            }
          } catch (Exception e){
            System.out.println( e );
          }
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
      response = new CreatedResponse( resource );
      response.setOtherHeaders( "Location", request.getUri() );
    }
    else if( requestVerb.equals( "Delete" ) ){
      Files.delete( filePath );
      response = new NoContentResponse( resource );
    }
    return response;
  }
}