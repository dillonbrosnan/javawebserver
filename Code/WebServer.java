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

public class WebServer{

  public static void main( String[] args ){
    try{
      Server server = new Server();
      server.start();
    }
    catch( IOException e){
      System.out.println(e);
    }

  }
}