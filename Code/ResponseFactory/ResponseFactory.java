package ResponseFactory;

import Request.*;
import Exceptions.*;

public class ResponseFactory {
  public static Response getResponse( Request request, Resource resource ){
    Response response = null;
    return response;
    // response = new Response( request, resource );
  }

  public static Response getResponse( Request request, Resource resource, ServerException exception ){
    
    Response response = null;

    if( exception instanceof BadRequestException ){
      response = new BadRequestResponse( resource );
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