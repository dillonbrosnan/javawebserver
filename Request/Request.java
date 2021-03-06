package Request;

import Exceptions.*;
import Date.*;
import java.time.LocalDateTime;
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
import java.util.Map;

public class Request{


  private String uri;
  private String httpVersion;
  private String verb;
  private String httpRequest;
  private String requestLine;
  private FormattedDate date;
  private InputStream httpRequestStream;  
  private Hashtable<String, String> headers;
  private byte[] messageBody;
  private static final int HEADER_KEY = 0;
  private static final int HEADER_VALUE= 1;
  private static final int AUTH_KEY = 0;
  private static final int AUTH_VALUE= 1;
  private static final String[] verbs = {
    "GET", "HEAD", "POST", "PUT", "DELETE"
  };

  public Request(){

  }

  public Request( String httpRequest ){
    this.httpRequest = httpRequest;
  }

  public Request( InputStream httpRequestStream ) {
    this.httpRequestStream = httpRequestStream;  
    date = new FormattedDate( LocalDateTime.now() );
  }

  public void parse() throws IOException, BadRequestException{
    BufferedReader reader = new BufferedReader( new InputStreamReader( httpRequestStream, "UTF-8" ) );
    String line;

    parseRequestLine( reader.readLine() );
    
    headers = new Hashtable<String, String>();
    line = reader.readLine();
    while( !line.isEmpty() ){
      addToHeaders( line );
      line = reader.readLine();
    }

    if ( hasBody() ){
      storeBody();
    }
    if( !isVerb( this.verb ) ){
      throw new BadRequestException();
    }
  }

  private void parseRequestLine( String requestLineParse ) {
    String[] requestLineSubstrings = requestLineParse.split( "\\s" );
    requestLine = requestLineParse;
    setVerb( requestLineSubstrings[0] );
    setUri( requestLineSubstrings[1] );
    setHttpVersion( requestLineSubstrings[2] );
  }

  private void addToHeaders( String headerLine ){
    String[] headerParts = headerLine.split(": "); 

    if(headerParts[HEADER_KEY].equals("Authorization")) {
      String[] authSplit = headerParts[HEADER_VALUE].split( " " );
      this.headers.put( headerParts[HEADER_KEY], authSplit[AUTH_VALUE] );
    }
    else{
      this.headers.put( headerParts[HEADER_KEY], headerParts[HEADER_VALUE] );
    }
  }

  private void storeBody(){
    try{
      int bodySize = Integer.parseInt( headers.get( "Content-Length" ) );
      messageBody = new byte[bodySize];
      httpRequestStream.read( messageBody );
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

  public String getHeader( String header ) {
    return headers.get( header );
  }

  public boolean headerKeyExists( String key ){
    return headers.containsKey( key );
  }

  public Map<String,String> getHeaders(){
    return headers;
  }

  public byte[] getBody(){
    return messageBody;
  }

  public boolean isModifiedSince(){
    return headers.get( "If-Modified-Since" ) != null;
  }

  public String getModifiedDate(){
    return headers.get( "If-Modified-Since" );
  }

  public String toString() {
   
    String serverLog = getHeader( "Host" ) + 
      " - " + getHeader( "Authorization" ) + 
      " " + "[" + date.toString() + "]" + "  \"" + requestLine + "\"";

    return serverLog;
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