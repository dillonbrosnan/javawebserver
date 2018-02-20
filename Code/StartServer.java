import ConfigurationReader.*;
import Authorization.*;
import Request.*;
import ResponseFactory.*;
import Logger.*;
import Worker.*;
import Exceptions.*;

import java.io.*;
import java.net.*;
import java.net.URISyntaxException;
import java.io.PrintWriter;

public class StartServer{

  public static void main( String[] args ){
    try{
      WebServer server = new WebServer();
      server.start();
    }
    catch( IOException e ){
      System.out.println( e );
    }

  }
}