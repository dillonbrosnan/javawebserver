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
    Logger logger = new Logger( httpdConf.getLogFile() );
    try{
      parseRequest( client.getInputStream() );
      resource = new Resource( request.getUri(), httpdConf, mime );
      response = ResponseFactory.getResponse( request, resource);   
      response.send( client.getOutputStream() );         
      client.close();
      logger.write( request, response );
    }
    catch( BadRequestException e){
      response = new BadRequestResponse( resource );
    }
     catch( Exception e){
      response = new InternalServerErrorResponse( resource );
    }
  }
  public void parseRequest( InputStream inputStream ) throws BadRequestException, IOException{
    request = new Request( inputStream );
    request.parse();
  }
}