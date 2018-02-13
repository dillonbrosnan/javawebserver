package ResponseFactory;

public class BadRequestResponse extends Response{
  public BadRequestResponse( Resource resource ){
    super( resource );
    this.statusCode = 400;
    this.reasonPhrase = "Bad Request";
  }
  public void send( OutputStream out ) throws IOException{
    BufferedWriter output = new BufferedWriter( new OutputStreamWriter ( out ) );

    String body = "400"
    this.sendAlwaysPhrase( output );
    this.sendGenericHeader( output, "Content-Length", String.valueOf( body.length() ) );
    this.sendGenericHeader( output, "Connection", "Closed" );
    output.write( this.CRLF );
    output.flush();
    output.write( body );
    output.flush();
  }
}