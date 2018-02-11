package ResponseFactory;

public class ResponseFactory() {
  public ResponseFactory() {
    public static Response getResponse( Request request, Resource resource ){
      return new Response( resource );
    }
  }
}