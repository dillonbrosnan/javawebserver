package ResponseFactory;

import Request.*;
import Date.*;

import java.time.LocalDateTime;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class Response {

  public String httpVersion = "HTTP/1.1";
  public String server = "TeamDoubleDServer";
  public String CRLF = "\r\n";
  public String reasonPhrase;
  public int statusCode;
  public byte[] body = null;
  public Resource resource;
  public String headers;
  public String verb;
  protected String otherHeaders = "";

  public Response( Resource resource ){
    this.resource = resource;
  }
  public Response(){
    
  }

  public abstract void send( OutputStream out ) throws IOException;

  protected void sendAlwaysPhrase( BufferedWriter out ) throws IOException{
    sendStatus( out );
    sendDate( out );
    sendServer( out );
  }

  protected void sendStatus( BufferedWriter out ) throws IOException{
    String statusString = httpVersion + " " + statusCode + " " + reasonPhrase + CRLF;

    out.write( statusString );
    out.flush();
  }

  protected void sendDate( BufferedWriter out ) throws IOException{
    FormattedDate date = new FormattedDate( LocalDateTime.now() );
    String dateString = "Date: " + date.toString() + CRLF;

    out.write( dateString );
    out.flush();
  }

  protected void sendServer( BufferedWriter out ) throws IOException{
    String serverString = "Server: " + server + CRLF;

    out.write( serverString );
    out.flush();
  }

  public void sendHeaderTemplate( BufferedWriter out, String header, String value ) throws IOException{
    String line = header + ": " + value + CRLF;

    out.write( line );
    out.flush();
  }

  public void setOtherHeaders( String header, String value ){
    otherHeaders += header + ": " + value + CRLF;
  }

  protected void sendOtherHeaders( BufferedWriter out ) throws IOException{
    if( otherHeaders != null){
      out.write( otherHeaders );
      out.flush();
    }
  }

  public void setVerb( String verb ){
    this.verb = verb;
  }

  public String getVerb(){
    return this.verb;
  }

  public void setBody( byte[] body ){
    this.body = body;
  }

  public long getBodyLength(){
    return this.body.length;
  }
  
  public int getStatusCode(){
    return this.statusCode;
  }

  public String toString() {
    if( body != null ) {
      return this.statusCode + " " + body.length;
    }
    return this.statusCode + " -";
  }
}