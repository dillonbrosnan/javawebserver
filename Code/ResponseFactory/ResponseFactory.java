package ResponseFactory;

import Request.*;
import Exceptions.*;
import Authorization.*;

import java.io.File;

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
  public static Response getResponse( Request request, Resource resource ){
    Response response = null;
    if( resource.isProtected() ){
      Htaccess htaccess = new Htaccess( resource.getAccessFilePath() );
      if( !request.headerKeyExists( "Authorization" ) ) {
        return new UnauthorizedResponse( resource );
      }
      else if( !htaccess.isAuthorized( request.getHeader( "Authorization" ) ) ) {
        return new ForbiddenResponse( resource );
      } 
    }
    File file = new File( resource.absolutePath() );
    if( !file.exists() ){
      return new FileNotFoundResponse( resource );
    }
    return response;
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