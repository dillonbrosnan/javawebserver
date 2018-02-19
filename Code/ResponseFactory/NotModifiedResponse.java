package ResponseFactory;

import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class NotModifiedResponse extends Response {
  public NotModifiedResponse( Resource resource ){
    super( resource );
    this.statusCode = 304;
    this.reasonPhrase = "Not Modified";
  }
  public void send( OutputStream out ) throws IOException{
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    this.sendAlwaysPhrase( output );
    this.sendOtherHeaders( output );
    output.write(this.CRLF);
    output.flush();
  }
}
