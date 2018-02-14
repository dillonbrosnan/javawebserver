package ResponseFactory;

import Exceptions.*;
import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class FileNotFoundResponse extends Response{
  public FileNotFoundResponse( Resource resource ){
    super( resource );
    this.statusCode = 404;
    this.reasonPhrase = "File Not Found";
  }
  public void send( OutputStream out ) throws IOException{
    String body = "<html><head><title>404 File Not Found</title></head><body>" 
      + "<h1>404 - File Not Found</h1></body></html>";
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    this.sendAlwaysPhrase( output );
    output.write( this.CRLF );
    output.flush();
    output.write( body );
    output.flush();
  }
}