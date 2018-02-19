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
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );
    File file = new File( resource.getAbsolutePath() );;
    Path path = Paths.get( resource.getAbsolutePath() );
    byte[] body = Files.readAllBytes( path );
    
    this.sendAlwaysPhrase( output );
    this.sendHeaderTemplate( output, "Content-Type", resource.getMimeType() );
    this.sendHeaderTemplate( output, "Content-Length", Long.toString( file.length() ) );
    this.sendHeaderTemplate( output, "Last-Modified", Long.toString( file.lastModified() ) );

    output.write( this.CRLF );
    output.flush();
    out.write( body, 0, body.length );
    out.flush();
    out.close();
  }
}