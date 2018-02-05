import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public abstract class ConfigurationReader {

  private File file;
  private static Scanner checkLines;

  public ConfigurationReader ( String fileName ) {
    this.file = new File ( fileName );
  }

  public boolean hasMoreLines () throws FileNotFoundException{

    boolean hasMoreLines = false;

    try {   

      checkLines = new Scanner ( file );
      hasMoreLines = checkLines.hasNextLine();  
      checkLines.close(); 

    } catch ( FileNotFoundException e ) {

      System.out.println( e );

    }
    return hasMoreLines; 
  }

  public String nextLine () {
    return checkLines.nextLine();
  }

  public abstract void load ();

}