import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.Hashtable;

public class Request{

  private String requestLine;
  private String uri;
  private String httpVersion;
  private String verb;
  private String test;
  private String[] parsedTest;
  private Hashtable<String,String> headers;
  private StringBuffer messageBody;

  public Request(){

  }
  public Request(String test){
  	this.test = test;
  	try{
  	  parse();
  	}
  	catch( IOException ex ){
  		System.out.println( ex.toString() );
  	}
  }

  public void parse() throws IOException{
  	BufferedReader reader = new BufferedReader( new StringReader( test ) );

  	setRequestLine( reader.readLine() );
  	parseRequestLine( this.requestLine );

    String headerLine = reader.readLine();
    while( headerLine.length() > 0){
      System.out.println( "Header" + headerLine );
      appendHeader( headerLine );
      headerLine = reader.readLine();
    }

    String bodyLine = reader.readLine();
    while( bodyLine != null){
    	appendBody( bodyLine );
    	bodyLine = reader.readLine();
    }
  }
  
  private void setRequestLine( String requestLineSet ){
  	this.requestLine = requestLineSet;
  }

  private void parseRequestLine(String requestLineParse){
  	System.out.println(requestLineParse);
  	String[] requestLineSubstrings = requestLineParse.split( "\\s" );

  	setVerb( requestLineSubstrings[0] );
  	setUri( requestLineSubstrings[1] );
  	setHttpVersion( requestLineSubstrings[2] );
  }

  private void appendHeader( String headerLine ){
  	int indexOfColon = headerLine.indexOf( ":" );
  	this.headers.put( headerLine.substring( 0, indexOfColon ) , headerLine.substring( indexOfColon+1, headerLine.length() ) );
  }

  private void appendBody( String bodyLine ){
    this.messageBody.append( bodyLine ).append( "\r\n" );
  }

  public void setVerb( String verb ){
  	this.verb = verb;
  }
  public void setUri( String uri ){
  	this.uri = uri;
  }
  public void setHttpVersion( String httpVersion ){
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


  public String toString(){
  	return ( "Verb: " + this.verb + " uri: " + this.uri + 
  	  " httpVersion: " + this.httpVersion );
  }
}