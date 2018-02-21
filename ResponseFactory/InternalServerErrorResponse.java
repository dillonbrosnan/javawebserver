package ResponseFactory;

import Exceptions.*;
import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class InternalServerErrorResponse extends Response{

  public InternalServerErrorResponse( Resource resource ){
    super( resource );
    this.statusCode = 500;
    this.reasonPhrase = "Internal Server Error";
  }
  
  public void send( OutputStream out ) throws IOException{
    String body = "<html><head><title>500 Internal Server Error</title></head><body>" 
      + "<h1>500 - Internal Server Error</h1></body></html>";
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    this.sendAlwaysPhrase( output );
    output.write( this.CRLF );
    output.flush();
    output.write( body );
    output.flush();
  }
}