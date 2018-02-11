package ResponseFactory;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import Request.*;

public class OK extends Response{
  public OK ( Resource resource ){
    super( resource );
    this.code = 200;
    this.reasonPhrase = "OK";
  }
  public send( OutputStream out ) throws IOException{
    BufferedWriter output  = new BufferedWriter( new OutputStreamWriter( out ) );
  }
}