package ResponseFactory;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import Request.*;

public class OKResponse extends Response{
  public OKResponse ( Resource resource ){
    super( resource );
    this.statusCode = 200;
    this.reasonPhrase = "OK";
  }
  public void send( OutputStream out ) throws IOException{
    BufferedWriter output  = new BufferedWriter( new OutputStreamWriter( out ) );
    
    
  }
}