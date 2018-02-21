package Exceptions;

import java.lang.Exception;

public class ServerException extends Exception{
  int statusCode;

  public ServerException( int statusCode, String reasonPhranse ){
    super( reasonPhranse );
    this.statusCode = statusCode;
  }
  public int getCode(){
    return this.statusCode;
  }
}