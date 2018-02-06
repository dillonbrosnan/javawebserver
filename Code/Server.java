import java.io.*;
import java.net.URISyntaxException;

public class Server{
  public static void main( String[] args ){
    // Request request = new Request("GET / http/1.1\r\nHost: net.tutsplus.com \r\n" +
    //   "Host2: net.tutsplus.com2\r\n\r\nhome=Cosby&favorite+flavor=flies\r\nhome=Cosby&favorite+flavor=flies\r\n");
    String s = "GET /cgi-bin/ http/1.1\r\nHost: net.tutsplus.com \r\n" +
      "Host2: net.tutsplus.com2\r\n\r\nhome=Cosby&favorite+flavor=flies\r\nhome=Cosby&favorite+flavor=flies\r\n";
      InputStream is = new ByteArrayInputStream( s.getBytes() );
      Request request = new Request( is );
    request.print();
    Resource resource = new Resource (request.getUri() , new HttpdConf("bullshit.txt") );
    try {      
      System.out.println( resource.absolutePath() );
    } catch( URISyntaxException e ) {
      System.out.println( e );
    }
    if(resource.isScript()){
      System.out.println("isScript");
    }
    MimeTypes mimetype = new MimeTypes("./conf/mime.types");
    mimetype.load();
    System.out.println( mimetype.lookup("ez") );
    
  }


//   public void start(){
//     ServerSocket socket = new ServerSocket( DEFAULT_PORT );
//     Socket client = null;
//     while( true ){
//       client = socket.accept();
//       outputRequest( client );
//       // TODO: Response
//       client.close();
//     }
//   }

  // protected static void outputRequest( Socket client ) throws IOException {
  //   Request request = new Request( new InputStream( client.getInputStream() ) );
  // }
}