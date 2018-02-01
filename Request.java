import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.Hashtable;

public class Request{


  private String uri;
  private String httpVersion;
  private String verb;
  private String httpRequest;
  private String[] parsedTest;
  private Hashtable<String, String> headers;
  private StringBuffer messageBody;
  private static final int HEADER_KEY = 0;
  private static final int HEADER_VALUE= 1;

  public Request(){

  }
  public Request(String httpRequest){
    this.httpRequest = httpRequest;
  	try{
  	  parse();
  	}
  	catch( IOException ex ){
  		//TODO implement 400 response handling
  		System.out.println( ex.toString() );
  	}
  }

  public void parse() throws IOException{
  	BufferedReader reader = new BufferedReader( new StringReader( httpRequest ) );
  	String line;

  	parseRequestLine( reader.readLine() );

  	headers = new Hashtable<String, String>();
    line = reader.readLine();
    while( line != "\r\n" && line != null ){
      addToHeaders( line );
      line = reader.readLine();
    }

    //TODO incorrect logic according to jrob 
    //"protocol specifies additional headers to determine how
    //to read the body which may not be string"
    line = reader.readLine();
    while( line != "\r\n" && line != null ){
    	appendBody( line );
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

  //TODO "not correct treatment of body, leave as bytestream?"
  private void appendBody( String bodyLine ){
    this.messageBody.append( bodyLine ).append( "\r\n" );
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
  }
}