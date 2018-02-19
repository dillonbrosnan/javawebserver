package Worker;

import ConfigurationReader.*;
import Request.*;
import ResponseFactory.*;
import Exceptions.*;
import Logger.*;

import java.lang.Thread;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;


public class Worker extends Thread {
  private Socket client;
  private MimeTypes mime;
  private HttpdConf httpdConf;
  private Request request;
  private Resource resource;
  private Response response;


  public Worker( Socket socket, HttpdConf httpdConf, MimeTypes mime ){
    this.client = socket;
    this.httpdConf = httpdConf;
    this.mime = mime;
  }

  public void run() {
    // try{
    //   // request = new Request( client.getInputStream() );  
    //   // request.parse();
    //   // request.print();
    //   parseRequest( client.getInputStream() );
    //   resource = new Resource( request.getUri(), httpdConf, mime);
    //   response = ResponseFactory.getResponse( request, resource );
    // } catch ( BadRequestException  e){
    //   response = new BadRequestResponse( resource );
    // } catch ( IOException e ) {
    //   System.out.println(e);
    //   //response = ResponseFactory.getResponse( request, resource, e );
    // }
    
    // try {      
    //   response.send( client.getOutputStream() );
    //   client.close();
    // } catch ( IOException e){
    //     System.out.println("CAUGHT IOException in worker response.send(): " + e);
    // }
    Logger logger = new Logger( httpdConf.getLogFile() );
    try{
      parseRequest( client.getInputStream() );
      resource = new Resource( request.getUri(), httpdConf, mime );
      response = ResponseFactory.getResponse( request, resource);   
    }
    catch( IOException e){
      System.out.println("IOException line 59 worker.java " + e );
    }
    catch( BadRequestException e){
      response = new BadRequestResponse( resource );
    }
    try{
      response.send( client.getOutputStream() );         
      client.close();
    }
    catch( IOException e){
      System.out.println("IOException line 69 worker.java " + e );
    }

    try {
      logger.write( request, response );
    } 
    catch ( IOException e ){
      System.out.println("IOException in Logger" + e );
    }
  }
  public void parseRequest( InputStream inputStream ) throws BadRequestException, IOException{
    request = new Request( inputStream );
    request.parse();
  }
}