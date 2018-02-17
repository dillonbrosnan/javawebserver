package ConfigurationReader;

import java.util.Hashtable;
import java.util.Map;

public class HttpdConf extends ConfigurationReader {
  private Map<String,String> aliases = new Hashtable<String, String>();
  private Map<String,String> scriptAliases = new Hashtable<String,String>();
  private Map<String,String> everythingElse = new Hashtable<String,String>();
  private String configuration[], line;

  private int DEFAULT_PORT, CONF_KEY = 0, CONF_VALUE = 1, SCRIPT_KEY = 1, SCRIPT_VALUE = 2;

  public HttpdConf( String fileName ) {
    super( fileName );
        System.out.println("File Name: " + fileName);
    //this.load();
  }

  public void load() {
    while( hasMoreLines() ) {
      line = this.nextLine();
      // System.out.println("Line in load() HttpdConf.java: " + line);
      line = line.replace( "\"", "" );
      this.configuration = line.split( " " );
      storeValues();
    }      
  }

  private void storeValues() {
    if( this.configuration[CONF_KEY].contains( "ScriptAlias" )) {
      scriptAliases.put(this.configuration[SCRIPT_KEY], this.configuration[SCRIPT_VALUE]);
    } else if( this.configuration[CONF_KEY] == "Alias") {
      aliases.put( this.configuration[SCRIPT_KEY], this.configuration[SCRIPT_VALUE] );
    } else {
      everythingElse.put( this.configuration[CONF_KEY], this.configuration[CONF_VALUE] );
    } 
  }

  public String getServerRoot() {
    return everythingElse.get( "ServerRoot" );
  }

  public String getDocumentRoot() {
    return everythingElse.get( "DocumentRoot" );
  }

  public String getPort() {
    return everythingElse.get( "Listen" );
  }

  public String getLogFile() {
    return everythingElse.get( "LogFile" );
  }

  public String getAccessFileName() {
    return everythingElse.get( "AccessFileName" );
  }
  
  public String getDirectoryIndex() {
    return everythingElse.get( "DirectoryIndex" );
  }

  public String getAlias( String pathToCheck ) {
    return aliases.get( pathToCheck );
  }

  public String getScriptAlias( String pathToCheck ) {
    return scriptAliases.get( pathToCheck );
  }
  public boolean scriptAliasesEmpty(){
    return this.scriptAliases.isEmpty();
  }

}