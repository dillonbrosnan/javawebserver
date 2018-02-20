package Logger;

import Request.*;
import ResponseFactory.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger{
  File file;

  public Logger(String fileName){
    this.file = new File(fileName);
  }

  public void write(Request request, Response response) throws IOException {
    try {
      FileWriter fileWriter = new FileWriter ( this.file, true );
      String log = request.toString() + " " + response.toString() + "\n";
      System.out.print(log);
      fileWriter.write ( log );
      fileWriter.flush();
      fileWriter.close();
    } catch ( IOException e ){
      System.out.println ( e );
    }
  }
}