import Worker.*;
import ConfigurationReader.*;

import java.net.*;
import java.io.*;
import java.lang.Thread;


public class WebServer{
  private static final String CONF_PATH = "./conf/httpd.conf";
  private static final String MIME_PATH = "./conf/mime.types";
  private static HttpdConf httpdConf;
  private static MimeTypes mimeTypes;
  private static int listenPort;

  public WebServer() throws IOException{
    httpdConf = new HttpdConf( CONF_PATH );
    mimeTypes = new MimeTypes( MIME_PATH );

    httpdConf.load();
    mimeTypes.load();
  }
  public static void start() throws IOException{
    listenPort = Integer.parseInt( httpdConf.getPort() );
    ServerSocket socket = new ServerSocket( listenPort );
    Socket client = null;
    Thread worker = null;

    while( true ){
      client = socket.accept();
      worker = new Worker( client, httpdConf, mimeTypes );
      worker.start();
    }
  }
}