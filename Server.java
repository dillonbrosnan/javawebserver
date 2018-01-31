public class Server{
  public static void main( String[] args ){
  	Request request = new Request("GET / http/1.1\r\nHost: net.tutsplus.com\r\n");
  	System.out.println( request.toString() );
  }
}