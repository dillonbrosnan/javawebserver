package Request;
import Exceptions.*;

import ConfigurationReader.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Resource{
  private HttpdConf httpdConf;
  private MimeTypes mime;
  private String uri;
  private String absolutePath;
  private String accessFilePath;
  private boolean isFile;  
  private boolean isDirectory;
  private boolean isScript;
  private boolean isProtected;
  private boolean isAlias;

  public Resource( String uri, HttpdConf httpdConf, MimeTypes mime ){
    this.uri = uri;
    this.httpdConf = httpdConf;
    this.mime = mime;
    isFile = false;
    isScript = false;
    isProtected = false;
    this.findAbsolutePath();
    this.checkAccessExists();
  }
  public HttpdConf getConfiguration() {
    return this.httpdConf;
  }

  public MimeTypes getMimeTypes() {
    return this.mime;
  }
  public boolean isScript(){
    if ( !this.httpdConf.scriptAliasesEmpty() ){
      isScript = true;
    }
    return isScript;
  }
  //TODO: protected method
  public boolean isProtected(){
    return isProtected;
  }

  public String getAbsolutePath() {
    // URI uriObject = new URI( this.uri );
    // return uriObject.getPath();
    return absolutePath;
  }

  protected void findAbsolutePath() {
    System.out.println("findAbsolutePath resource.java uri: " + this.uri);
    String temporary = "/";
    String[] uriSplit = uri.split( "/" );
    System.out.println(Arrays.toString(uriSplit));    
    isDirectory = this.uri.endsWith( "/" );
    System.out.println(isDirectory);

    if( uri.equals( "/" )) {
      absolutePath = httpdConf.getDocumentRoot();
    } else {
        temporary += uriSplit[0] + "/";
        if( isAlias( temporary )) {
          absolutePath = httpdConf.getAlias( temporary ) + restOfPath( uriSplit );
          this.isAlias = true;
        } else if( httpdConf.getScriptAlias( temporary ) != null ) {
          absolutePath = httpdConf.getScriptAlias( temporary ) + restOfPath( uriSplit );
          this.isScript = true;
        } else {
          resolveUnmodUri();
        }      
    }
    if ( isDirectory ) {
      appendDirIndex();
    } 
  } //TODO: check file exists (no: throw 404)
  // protected void fileExists(){
  //   File fn = new File( this.absolutePath() );
  //   if( !fn.exists() ){
  //     throw new FileNotFoundException();
  //   }
  // }

  private void resolveUnmodUri() {
    absolutePath = "";
    absolutePath += httpdConf.getDocumentRoot() + this.uri.replaceFirst( "/", "" );
  }

  private void appendDirIndex() {
    absolutePath += httpdConf.getDirectoryIndex();
  }

  private boolean isAlias( String path ) {
    return httpdConf.getAlias( path ) != null;
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
    System.out.println("Absolute path in Resource checkAccessExists " + absolutePath);
    accessFilePath = absolutePath;
    String[] uriTokens = this.uri.split("/");
    String docRootAppended = httpdConf.getDocumentRoot();
    String docRootUriHtaccess = "";
    File accessFile;

    if( this.uri.equals( "/" )) {
      docRootAppended = httpdConf.getDocumentRoot() + httpdConf.getAccessFileName();
      accessFilePath = docRootAppended;
    } else {
      for( int i = 0; i < uriTokens.length && !isProtected; i++ ){
        docRootAppended += (uriTokens[i] + "/");
        docRootUriHtaccess = docRootAppended + httpdConf.getAccessFileName();
        accessFile = new File( docRootUriHtaccess );
        isProtected = accessFile.exists();
      }
      accessFilePath = docRootUriHtaccess;
    }

    accessFile = new File( accessFilePath );
    isProtected = accessFile.exists();     

  }
  public String getAccessFilePath(){
    return this.accessFilePath;
  }
  public boolean exists(){
    return Files.exists( Paths.get (absolutePath ) );
  }
  public String getMimeType(){
    String[] tokens;
    String extensions;

    tokens = absolutePath.split("\\.");
    extensions = tokens[tokens.length-1];
    return mime.lookup( extensions );
  }

  public void print(){
    System.out.println(isProtected());
    System.out.println( isAlias(uri) );
  }

}

