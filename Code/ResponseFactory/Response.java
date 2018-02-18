package ResponseFactory;

import Request.*;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class Response {

  public int code;
  public String httpVersion = "HTTP/1.1";
  public String server = "TeamDoubleDServer";
  public String CRLF = "\r\n";
  public String reasonPhrase;
  public int statusCode;
  public byte[] body;
  public Resource resource;
  public String headers;
  public String verb;
  protected String otherHeaders = "";

  public Response( Resource resource ){
    this.resource = resource;
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
    DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/DD HH:mm:ss" );
    Date date = new Date();
    String dateString = "Date: " + dateFormat.format( date ) + CRLF;

    out.write( dateString );
    out.flush();
  }
  protected void sendServer( BufferedWriter out ) throws IOException{
    String serverString = "Server: " + server + CRLF;

    out.write( serverString );
    out.flush();
  }
  protected void sendGenericHeader( BufferedWriter out, String header, String value ) throws IOException{
    String genericHeader = header + ": " + value + CRLF;

    out.write(genericHeader);
    out.flush();
  }
  public void setVerb( String verb ){
    this.verb = verb;
  }
  public void setGenericHeader( String header, String value ){
    otherHeaders += header + ": " + value + CRLF;
  }

}