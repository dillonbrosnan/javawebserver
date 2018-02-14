package ResponseFactory;

import Exceptions.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class ForbiddenResponse extends ServerResponse{
  public ForbiddenResponse( Resource resource ){
    super( resource );
    this.statusCode = 403;
    this.reasonPhrase = "Forbidden";
  }
  public void send( OutputStream out ){
    String body = "<html><head><title>403 Forbidden</title></head><body>" 
      + "<h1>403 - Forbidden</h1></body></html>";
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    this.sendAlwaysPhrase( output );
    output.write( this.CRLF );
    output.flush();
    output.write( body );
    output.flush();
  }
}