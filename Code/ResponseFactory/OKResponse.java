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
    byte[] body;

    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );
    this.sendAlwaysPhrase( output );
    this.sendOtherHeaders( output );
    System.out.println("OKResponse isScript= " + resource.isScript());

    if( resource.isScript() ){
        body = "REDIRECTED SCRIPT OUTPUT".getBytes();
    }
    else{
        File file = new File( resource.getAbsolutePath() );
        Path path = Paths.get( resource.getAbsolutePath() );
        body = Files.readAllBytes( path );
        this.sendHeaderTemplate( output, "Content-Type", "text/html");
        this.sendHeaderTemplate( output, "Content-Length", Long.toString( file.length() ) );
        this.sendHeaderTemplate( output, "Last-Modified", Long.toString( file.lastModified() ) );
    }
    output.write( this.CRLF );
    output.flush();

    if( !this.getVerb().equals( "HEAD") ){
        out.write( body, 0, body.length );
        out.flush();
    }
    // if resource.isScript(){
    //     body = "REDIRECTED SCRIPT OUTPUT".getBytes();
    // }
    // BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );
    // File file = new File( resource.getAbsolutePath() );;
    // Path path = Paths.get( resource.getAbsolutePath() );
    // byte[] body = Files.readAllBytes( path );
    
    // this.sendAlwaysPhrase( output );
    // this.sendHeaderTemplate( output, "Content-Type", "text/html");
    // this.sendHeaderTemplate( output, "Content-Length", Long.toString( file.length() ) );
    // this.sendHeaderTemplate( output, "Last-Modified", Long.toString( file.lastModified() ) );

    // output.write( this.CRLF );
    // output.flush();
    // out.write( body, 0, body.length );
    // out.flush();
    // out.close();
  }
}