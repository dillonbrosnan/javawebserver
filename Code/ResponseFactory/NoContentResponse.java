package ResponseFactory;

import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class NoContentResponse extends Response {

  public NoContentResponse( Resource resource ){
    super( resource );
    this.statusCode = 204;
    this.reasonPhrase = "Not Content";
  }
  
  public void send( OutputStream out ) throws IOException{
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    this.sendAlwaysPhrase( output );
    output.write(this.CRLF);
    output.flush();
  }
}