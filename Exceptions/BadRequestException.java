package Exceptions;

public class BadRequestException extends ServerException {
  public BadRequestException(){
    super( 400, "Bad Request" );
  }
}