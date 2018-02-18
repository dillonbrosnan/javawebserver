package ResponseFactory;

import Exceptions.*;
import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class UnauthorizedResponse extends Response{
  public UnauthorizedResponse( Resource resource ){
    super( resource );
    this.statusCode = 401;
    this.reasonPhrase = "Unauthorized";
  }
  public void send( OutputStream out ) throws IOException{
    String body = "<html><head><title>401 Unauthorized</title></head><body>" 
      + "<h1>401 - Unauthorized</h1></body></html>";
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    this.sendAlwaysPhrase( output );
    this.sendGenericHeader( output, "WWW-Authenticate", "Basic" );
    this.sendGenericHeader( output, "Content-Length", String.valueOf( body.length() ) );
    this.sendGenericHeader( output, "Content-Type", "text/html" );
    output.write( this.CRLF );
    output.flush();
    output.write( body );
    output.flush();
  }
}