package Request;

import ConfigurationReader.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;

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
    this.checkAccessExists();
  }
  
  public boolean isScript(){
    if( this.uri.contains( "cgi-bin" ) ){
      isScript = true;
    }
    return isScript;
  }
  //TODO: protected method
  protected boolean isProtected(){
    return isProtected;
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
    if ( isDirectory ) {
      appendDirIndex();
    } 
  }

  private void resolveUnmodUri() {
    absolutePath = "";
    absolutePath += config.getDocumentRoot() + this.uri.replaceFirst( "/", "" );
  }

  private void appendDirIndex() {
    absolutePath += config.getDirectoryIndex();
  }

  private boolean isAlias( String path ) {
    return config.getAlias( path ) != null;
  }

  private String restOfPath( String[] uriSplit ) {
    String tempAbsolutePath = "";
    for(int nextToken = 1; nextToken < uriSplit.length; nextToken++ ) {
      tempAbsolutePath += uriSplit[nextToken];
      if(nextToken == uriSplit.length - 1 || isDirectory ) {
        tempAbsolutePath += "/";
      }
    }
    return tempAbsolutePath;
  }
  private void checkAccessExists() {
    accessFilePath = absolutePath;
    String[] uriTokens = this.uri.split("/");
    String docRootAppended = config.getDocumentRoot();
    String docRootUriHtaccess;
    File accessFile;

    if( this.uri.equals( "/" )) {
      docRootAppended = config.getDocumentRoot() + config.getAccessFileName();
    } else {
      for( int i = 0; i < uriTokens.length && !isProtected; i++ ){
        docRootAppended += (uriTokens[i] + "/");
        docRootUriHtaccess = docRootAppended + config.getAccessFileName();
        accessFile = new File( docRootUriHtaccess );
        isProtected = accessFile.exists();
      }
    }

    accessFile = new File( docRootAppended );
    isProtected = accessFile.exists();  
    

  }

  public void print(){
    System.out.println(isProtected());
    System.out.println( isAlias(uri) );
  }

}

