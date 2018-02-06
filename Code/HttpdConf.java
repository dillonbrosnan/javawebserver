import java.util.HashMap;

public class HttpdConf extends ConfigurationReader {
  private Map<String,String> aliases = new Hashtable<String, String>();
  private Map<String,String> scriptAliases = new Hashtable<String,String>();
  private Map<String,String> everythingElse = new Hashtable<String,String>();
  private String configuration[], line;

  private int DEFAULT_PORT, CONF_KEY = 0, CONF_VALUE = 1;

  public HttpdConf( String fileName ) {
    super( fileName );
  }

  public void load() {
    while( hasMoreLines() ) {
      line = this.nextLine();
      configuration = line.split(" ");
      if(configuration )      
    }      
  }
 }