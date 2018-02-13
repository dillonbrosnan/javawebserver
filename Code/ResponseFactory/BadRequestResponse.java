package ResponseFactory;

import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class BadRequestResponse extends Response{
  public BadRequestResponse( Resource resource ){
    super( resource );
    this.statusCode = 400;
    this.reasonPhrase = "Bad Request";
  }
  public void send( OutputStream out ) throws IOException{
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    String body = "<html><head><title>400 Bad Request</title></head><body>" 
      + "<h1>400 - Bad Request</h1></body></html>";
    this.sendAlwaysPhrase( output );
    this.sendGenericHeader( output, "Content-Length", String.valueOf( body.length() ) );
    this.sendGenericHeader( output, "Connection", "Closed" );
    output.write( this.CRLF );
    output.flush();
    output.write( body );
    output.flush();
  }
}