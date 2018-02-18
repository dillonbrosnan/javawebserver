package ResponseFactory;

import Request.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Path;

public class OKResponse extends Response{
  public OKResponse( Resource resource ){
    super( resource );
    this.statusCode = 200;
    this.reasonPhrase = "OK";
  }
  public void send( OutputStream out ) throws IOException{
    File file = new File( resource.absolutePath() );
    Path path = Paths.get( resource.absolutePath() );

    byte[] body = Files.readAllBytes( path );
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    
    this.sendAlwaysPhrase( output );
    this.sendGenericHeader( output, "Content-Length", String.valueOf( file.length() ) );
    // this.sendGenericHeader( output, "Content-Length", String.valueOf( file.length() ) );
    // //this.sendGenericHeader( output, "Connection", "Closed" );
    // this.sendGenericHeader( output, "Content-Type", "text/html" );

    output.write( this.CRLF );
    output.flush();
    out.write( body );
    out.flush();
  }
}