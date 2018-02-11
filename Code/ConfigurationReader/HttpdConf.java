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
  }

  public void load() {
    while( hasMoreLines() ) {
      line = this.nextLine();
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
}