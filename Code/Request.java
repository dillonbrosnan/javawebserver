import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
// import java.*;

public class Request{


  private String uri;
  private String httpVersion;
  private String verb;
  private String httpRequest;
  private String[] parsedTest;
  private Hashtable<String, String> headers;
  private byte[] messageBody;
  private static final int HEADER_KEY = 0;
  private static final int HEADER_VALUE= 1;

  public Request(){

  }

  public Request( String httpRequest ){
    this.httpRequest = httpRequest;
    try{
      parse();
    }
    catch( IOException ex ){
      //TODO implement 400 response handling
      System.out.println( ex.toString() );
    }
  }

  public Request( InputStream httpRequestStream ) {
    //this.httpRequest = IOUtils.toString( httpRequestStream, StandardCharsets.UTF_8 );
    
    try{
      inputStreamToString( httpRequestStream );
      parse();
    }
    catch( IOException ex ){
      //TODO implement 400 response handling
      System.out.println( ex.toString() );
    }
  }
  
  private void inputStreamToString( InputStream httpRequestStream ) throws IOException {
    try (Scanner scanner = new Scanner( httpRequestStream , StandardCharsets.UTF_8.name() ) ) {
      this.httpRequest = scanner.useDelimiter( "\\A" ).next();
    } 
          
  }

  public void parse() throws IOException{
    BufferedReader reader = new BufferedReader( new StringReader( httpRequest ) );
    String line;

    parseRequestLine( reader.readLine() );

    headers = new Hashtable<String, String>();
    line = reader.readLine();
    while( line.length() > 0 ){
      System.out.println(line);
      addToHeaders( line );
      line = reader.readLine();
    }

    line = reader.readLine();
    messageBody = new byte[0];
    while( line != "\r\n" && line != null ){
      storeBody( line );
      line = reader.readLine();
    }
  }

  private void parseRequestLine(String requestLineParse){
    String[] requestLineSubstrings = requestLineParse.split( "\\s" );

    setVerb( requestLineSubstrings[0] );
    setUri( requestLineSubstrings[1] );
    setHttpVersion( requestLineSubstrings[2] );
  }

  private void addToHeaders( String headerLine ){
    String[] headerParts = headerLine.split(": ");  
    this.headers.put( headerParts[HEADER_KEY], headerParts[HEADER_VALUE] );
  }

  private void storeBody( String bodyLine ){
    byte[] bodyLineBytes = bodyLine.getBytes();
    int totalLength = messageBody.length + bodyLineBytes.length;
    byte[] destination = new byte[totalLength];
    
    System.arraycopy( messageBody, 0, destination, 0, messageBody.length );
    System.arraycopy( bodyLineBytes, 0, destination, messageBody.length, bodyLineBytes.length );
    messageBody = Arrays.copyOf( destination, totalLength);
  }
  private void setVerb( String verb ){
    this.verb = verb;
  }
  private void setUri( String uri ){
    this.uri = uri;
  }
  private void setHttpVersion( String httpVersion ){
    this.httpVersion = httpVersion;
  }

  public String getVerb(){
    return this.verb;
  }
  public String getUri(){
    return this.uri;
  }
  public String getHttpVersion(){
    return this.httpVersion;
  }

  public void print(){
    System.out.println( "Verb: " + this.verb + ", uri: " + this.uri + 
      ", httpVersion: " + this.httpVersion );

    for( String key: headers.keySet() ){
      String value = headers.get(key).toString();
      System.out.println( "Key: " + key + ", Value: " + value);
    }
    String messageBodyString = new String( messageBody, Charset.forName("UTF-8") );
    System.out.println( messageBodyString );
  }
}