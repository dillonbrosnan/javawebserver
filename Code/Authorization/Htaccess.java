package Authorization;

import java.io.IOException;
import ConfigurationReader.*;

public class Htaccess extends ConfigurationReader{
  private Htpassword userFile;
  private String authType;
  private String authName;
  private String require;
   //htp object?

  public Htaccess (String fileName){
    super(fileName);
    load();
  }

  public void load(){
    String[] fileLine = this.nextLine().split( "\\s+" );
    try{
      String strippedQuotesFileName = fileLine[1].replace( "\"", "" );
      this.userFile = new Htpassword( strippedQuotesFileName );
    }
    catch(IOException e){
      System.out.println(e);
    }

    String[] authTypeLine = this.nextLine().split( "\\s+" );
    this.authType = authTypeLine[1];

    this.authName = getAuthName( this.nextLine() );

    String[] requireLine = this.nextLine().split( "\\s+" );
    this.require = requireLine[1];
  }

  public boolean isAuthorized( String authInfo ){
    return this.userFile.isAuthorized( authInfo );    
  }

  public void print(){
    System.out.println("authType: " + this.authType);
    System.out.println("require: " + this.require);
    System.out.println("authName: " + this.authName);
    System.out.println(this.isAuthorized(this.authName));
  }
  
  public String getAuthName(String line){
    String[] authNameLine = line.split( "\\s+" );
    String returnName = authNameLine[1];
    for( int i = 2; i < authNameLine.length; i++ ){
      returnName += " " + authNameLine[i];
    }
    return returnName = returnName.replace( "\"", "" );
  }

}
