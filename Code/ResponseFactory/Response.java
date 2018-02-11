package ResponseFactory;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import Request.*;
public abstract class Response {

  public int code;
  public String reasonPhrase;
  public Resource resource;

  public Response( Resource resource ){
    this.resource = resource;
  }

  public abstract void send( OutputStream out ) throws IOException;


}