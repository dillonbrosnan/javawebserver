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

public class ResponseFactory {
  // public static Response getResponse( Request request, Resource resource ){
  //   Response response = null;
  //   return response;
  //   // response = new Response( request, resource );
  // }

  // public static Response getResponse( Request request, Resource resource, ServerException exception ){
    
  //   Response response = null;

  //   if( exception instanceof BadRequestException ){
  //     response = new BadRequestResponse( resource );
  //   }
  //   // else if( exception instanceof UnauthorizedException ){
  //   //   response = new UnauthorizedResponse( resource );
  //   // }
  //   return response;
  // }
  public static Response getResponse( Request request, Resource resource ) throws IOException{
    Response response = null;
    Path filePath = Paths.get( resource.getAbsolutePath() );
    FormattedDate modDate;
    if( resource.isProtected() ){
      System.out.println( "ResponseFactory.getResponse resource.getAccessFilePath() " + resource.getAccessFilePath());
      Htaccess htaccess = new Htaccess( resource.getAccessFilePath() );
      System.out.println("!ResponseFactory.headerKeyExists: "+ !request.headerKeyExists("Authorization"));
      if( !request.headerKeyExists( "Authorization" ) ) {
        return new UnauthorizedResponse( resource );
      }
      else if( !htaccess.isAuthorized( request.getHeader( "Authorization" ) ) ) {
        return new ForbiddenResponse( resource );
      } 
      // else{
      //   System.out.println("ResponseFactory line 39: " + request.getHeader("Authorization"));
      // }
    }
    return new OKResponse( resource );
    // File file = new File( resource.absolutePath() );
    // System.out.println("ResponseFactory.resource.absolutePath: " + resource.absolutePath());
    // if( !file.exists() ){
    //   return new FileNotFoundResponse( resource );
    // }
    // return response;
  }
  
  // isProtected(){
  //   create htacces 
  //   check headers against htacces
  //     401
  //   check username pwd combo
  //     403 
  //   bail
  // }
  // accessValidation(){
  //   //checking headers of request against htacess
  // }

}