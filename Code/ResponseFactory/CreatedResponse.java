package ResponseFactory;

import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class CreatedResponse extends Response{
  
  public CreatedResponse( Resource resource ){
    super( resource );
    this.statusCode = 201;
    this.reasonPhrase = "Created";
  }

  public void send( OutputStream out ) throws IOException{
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter( out ) );

    this.sendAlwaysPhrase( output );
    this.sendOtherHeaders( output );
    this.sendHeaderTemplate( output, "Content-Type", this.resource.getMimeType() );
    output.write(this.CRLF);
    output.flush();
  }
}