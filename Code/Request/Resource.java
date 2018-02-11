package Request;

import java.net.URI;
import java.net.URISyntaxException;

public class Resource{
  private HttpdConf config;
  private String uri;
  private String absolutePath;
  private String accessFilePath;
  private boolean isFile;  
  private boolean isDirectory;
  private boolean isScript;
  private boolean isProtected;
  private boolean isAlias;
  

  public Resource( String uri, HttpdConf config ){
    this.uri = uri;
    this.config = config;
    isFile = false;
    isScript = false;
    isProtected = false;
    this.findAbsolutePath();
    this.findAccessFilePath();
  }
  
  public boolean isScript(){
    if( this.uri.contains( "cgi-bin" ) ){
      isScript = true;
    }
    return isScript;
  }
  //TODO: protected method
  protected boolean isProtected(){
    return false;
  }

  public String absolutePath() {
    // URI uriObject = new URI( this.uri );
    // return uriObject.getPath();
    return absolutePath;
  }

  protected void findAbsolutePath() {
    String temporary = "/";
    String[] uriSplit = uri.split( "/" );    
    isDirectory = this.uri.endsWith( "/" );

    if( uri.equals( "/" )) {
      absolutePath = config.getDocumentRoot();
    } else {
        temporary += uriSplit[0] + "/";
        if( isAlias( temporary )) {
          absolutePath = config.getAlias( temporary ) + restOfPath( uriSplit );
          this.isAlias = true;
        } else if( config.getScriptAlias( temporary ) != null ) {
          absolutePath = config.getScriptAlias( temporary ) + restOfPath( uriSplit );
          this.isScript = true;
        } else {
          resolveUnmodUri();
        }      
    }

    isFile = isFile( absolutePath );
  }

  private void resolveUnmodUri() {
    absolutePath = "";
    absolutePath += config.getDocumentRoot() + this.uri;
  }

  private boolean isFile( String absolutePath ) {
    if( isDirectory ) {
      
    }
  }

  private boolean isAlias( String path ) {
    return config.getAlias( path ) != null;
  }

  private String restOfPath( String[] uriSplit ) {
    for(int nextToken = 1; nextToken < uriSplit.length(); nextToken++ ) {
      absolutePath += uriSplit[nextToken];
      if(nextToken == uriSplit.length() - 1 || isDirectory ) {
        absolutePath += "/";
      }
    }
  }

}

