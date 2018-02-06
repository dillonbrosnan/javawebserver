import java.net.URI;
import java.net.URISyntaxException;

public class Resource{
  private String uri;
  private HttpdConf config;

  public Resource( String uri, HttpdConf config ){
    this.uri = uri;
    this.config = config;
  }
  public String absolutePath() throws URISyntaxException {
    URI uriObject = new URI( this.uri );
    return uriObject.getPath();
  }
  public boolean isScript(){
    if( this.uri.contains( "cgi-bin" ) ){
      return true;
    }
    return false;
  }
  //TODO: protected method
  public boolean isProtected(){
    return false;
  }

}

