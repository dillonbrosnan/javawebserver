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
    System.out.println("below creating BufferedWriter");
    System.out.println("OKResponse absolutePath: " + resource.getAbsolutePath());
    File file = new File( resource.getAbsolutePath() );
    System.out.println("Below creating file");
    Path path = Paths.get( resource.getAbsolutePath() );
    System.out.println("Below creating path");

    byte[] body = Files.readAllBytes( path );
    System.out.println("Q!@#!@#@!$@@!$!@ " + body.length);
    System.out.println("Below creating body");
    
    this.sendAlwaysPhrase( output );
    System.out.println("below sending sendAlwaysPhrase");
    this.sendGenericHeader( output, "Content-Type", "text/html");
    System.out.println("below sending Content-Type header");
    this.sendGenericHeader( output, "Content-Length", "3143" );
    this.sendGenericHeader( output, "Last-Modified", Long.toString( file.lastModified() ) );
    System.out.println("below sending Content-Length header");
    // this.sendGenericHeader( output, "Content-Length", String.valueOf( file.length() ) );
    // //this.sendGenericHeader( output, "Connection", "Closed" );
    // this.sendGenericHeader( output, "Content-Type", "text/html" );

    output.write( this.CRLF );
    output.flush();
    out.write( body, 0, body.length );
    out.flush();
  }
}