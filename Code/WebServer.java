import ConfigurationReader.*;
import Authorization.*;
import Request.*;
import ResponseFactory.*;
import Logger.*;
import Worker.*;
import Exceptions.*;

import java.io.*;
import java.net.*;
import java.net.URISyntaxException;
import java.io.PrintWriter;

public class WebServer{
  // private static final String CONF_PATH = "./conf/httpd.conf";
  // private static final String MIME_PATH = "./conf/mime.types";
  // private static HttpdConf httpdConf;
  // private static MimeTypes mimeTypes;

  public static void main( String[] args ){
    // // Request request = new Request("GET / http/1.1\r\nHost: net.tutsplus.com \r\n" +
    // //   "Host2: net.tutsplus.com2\r\n\r\nhome=Cosby&favorite+flavor=flies\r\nhome=Cosby&favorite+flavor=flies\r\n");
    // String s = "GET /cgi-bin/ http/1.1\r\nHost: net.tutsplus.com \r\n" +
    //   "Host2: net.tutsplus.com2\r\nContent-Length: 40\r\n\r\nhome=Cosby&favorite+flavor=flies\r\nhome=Cosby&favorite+flavor=flies2131\r\n";
    //   InputStream is = new ByteArrayInputStream( s.getBytes() );
    //   Request request = new Request( is );
    // request.print();
    // Resource resource = new Resource (request.getUri() , new HttpdConf("bullshit.txt") );
    // try {      
    //   System.out.println( resource.absolutePath() );
    // } catch( URISyntaxException e ) {
    //   System.out.println( e );
    // }
    // if(resource.isScript()){
    //   System.out.println("isScript");
    // }
    // MimeTypes mimetype = new MimeTypes("./conf/mime.types");
    // mimetype.load();
    // System.out.println( mimetype.lookup("ez") );
    

    // HttpdConf httpdConf =  new HttpdConf("./conf/httpd.conf");
    // httpdConf.load();
    // System.out.println( httpdConf.getDocumentRoot() );

    // Htaccess hta = new Htaccess("./Support/_.htaccess");
    // hta.load();
    // hta.print();
    try{
      Server server = new Server();
      server.start();
    }
    catch( IOException e){
      System.out.println(e);
    }

  }


  // public static void start() throws IOException{
  //   try{}
  //   // System.out.println("conf path: " + CONF_PATH);

  //   // httpdConf = new HttpdConf( CONF_PATH );
  //   // mimeTypes = new MimeTypes( MIME_PATH );
  //   // mimeTypes.load();
  //   // httpdConf.load();
  //   // try{
  //   //   ServerSocket socket = new ServerSocket( Integer.parseInt(httpdConf.getPort()) );
  //   //   Socket client = null;
  //   //   Thread worker = null;
  //   //   //Htaccess hta = new Htaccess("./Support/_.htaccess");
  //   //   while( true ){
  //   //     client = socket.accept();
  //   //     worker = new Worker( client, httpdConf, mimeTypes );
  //   //     worker.start();
  //   //     //outputRequest( client );
        
  //   //     // TODO: Response
  //   //     //client.close();
  //   //   }
  //   // }
  //   // catch(IOException e){
  //   //   System.out.println(e);
  //   // }
  // }

  // protected static void outputRequest( Socket client ) throws IOException {
  //   // System.out.println( client.getInputStream().toString() );
    
  //   Request request = new Request( client.getInputStream() );
  //   HttpdConf httpdConf =  new HttpdConf("./conf/httpd.conf");
  //   Resource rsource = new Resource(request.getUri(), httpdConf);

  //   request.print();
  //   rsource.print();
  // }
}