package ConfigurationReader;

import java.util.Hashtable;
import java.util.Arrays;

public class MimeTypes extends ConfigurationReader{
  private Hashtable<String,String> types;
  private static final int MIME_VALUE= 0;

  public MimeTypes( String fileName ){
    super( fileName );
  }

  public void load(){
    types = new Hashtable<String,String>();
    String mimeLine = null;
    while( this.hasMoreLines() ){
      if( !( ( mimeLine = this.nextLine() ).contains("#") ) ){
        String mimeParts[] = mimeLine.split( "\\s+" );
        System.out.println("Line in MimeTypes: " + Arrays.toString(mimeParts));
        for( int i = 1; i < mimeParts.length; i++ ){
          this.types.put( mimeParts[i], mimeParts[MIME_VALUE] );
        }
      }
    }
  }
  public String lookup( String extension ){
    return this.types.get( extension );
  }
}

//key lhs
//val rhs