package Authorization;

import java.io.IOException;
import ConfigurationReader.*;

public class Htaccess extends ConfigurationReader{
  private Htpassword userFile;
  private String authType;
  private String authName;
  private String require; //htp object?

  public Htaccess (String fileName){
    super(fileName);
  }

  public void load(){
    String[] fileLine = this.nextLine().split( "\\s+" );
    try{
          this.userFile = new Htpassword( fileLine[1] );
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

  public boolean isAuthorized( String authName ){
    return this.userFile.isAuthorized( authName );    
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
