package ResponseFactory;

public class ResponseFactory {
  public ResponseFactory() {

  }
  public static Response getResponse( Request request, Resource resource, ServerException exception ){
    
    Response response = null;

    if( exception instanceof BadRequestException ){
      response = new BadRequestException( resource );
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