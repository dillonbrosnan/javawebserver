package Request;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
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
  private InputStream httpRequestStream;
  private String[] parsedTest;
  private Hashtable<String, String> headers;
  private byte[] messageBody;
  private static final int HEADER_KEY = 0;
  private static final int HEADER_VALUE= 1;
  private static final String[] verbs = {
    "GET", "HEAD", "POST", "PUT", "DELETE"
  };

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
    this.httpRequestStream = httpRequestStream;  
    try{
      parse();
    }
    catch( IOException ex ){
      //TODO implement 400 response handling
      System.out.println( ex.toString() );
    }
  }
  public void parse() throws IOException{
    BufferedReader reader = new BufferedReader( new InputStreamReader( httpRequestStream, "UTF-8" ) );
    String line;

    parseRequestLine( reader.readLine() );

    headers = new Hashtable<String, String>();
    line = reader.readLine();
    while( !line.isEmpty() ){
      addToHeaders( line );
      line = reader.readLine();
    }
    line = reader.readLine();
    if ( hasBody() ){
      storeBody();
    }
  }

  private void parseRequestLine(String requestLineParse){
    System.out.println(requestLineParse);
    String[] requestLineSubstrings = requestLineParse.split( "\\s" );

    if( isVerb( requestLineSubstrings[0] ) ){
      setVerb( requestLineSubstrings[0] );
    }
    setUri( requestLineSubstrings[1] );
    setHttpVersion( requestLineSubstrings[2] );
  }
  //commas?
  private void addToHeaders( String headerLine ){
    String[] headerParts = headerLine.split(": ");  
    this.headers.put( headerParts[HEADER_KEY], headerParts[HEADER_VALUE] );
  }

  private void storeBody(){
    try{
      int bodySize = Integer.parseInt( headers.get( "Content-Length" ) );
      messageBody = new byte[bodySize];
      httpRequestStream.read( messageBody, 0, bodySize );
    }
    catch( IOException e ){
      System.out.println( e );
    }
  }
  private boolean hasBody(){
    return headers.containsKey( "Content-Length" );
  }
  private boolean isVerb( String verb ){
    return Arrays.asList( verbs ).contains( verb ); 
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
    System.out.println( "REQUEST LINE: " );
    System.out.println( "Verb: " + this.verb + ", uri: " + this.uri + 
      ", httpVersion: " + this.httpVersion );
    System.out.println( "HEADERS: ");
    for( String key: headers.keySet() ){
      String value = headers.get(key).toString();
      System.out.println( "Key: " + key + ", Value: " + value);
    }
    System.out.println( "BODYSIZE: " );
    System.out.println(Arrays.toString(messageBody));
  }
}