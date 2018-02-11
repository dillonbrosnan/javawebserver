package ConfigurationReader;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public abstract class ConfigurationReader {

  public File file;
  private static Scanner checkLines;

  public ConfigurationReader ( String fileName ) {
    this.file = new File ( fileName );
    try{
      checkLines = new Scanner( file );
    }
    catch(FileNotFoundException e){
      System.out.println(e);
    }
  }
  
  public boolean hasMoreLines() {
    return checkLines.hasNextLine(); 
  }

  public String nextLine() {
    return checkLines.nextLine();
  }

  public abstract void load();
  // public void load(){
  //   while( hasMoreLines ){
  //     parseLine( nextLine() );
  //   }
  // }
  // // protected abstract void parseLine();

}