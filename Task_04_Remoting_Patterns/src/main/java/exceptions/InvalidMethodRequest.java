package exceptions;

public class InvalidMethodRequest extends RuntimeException{
    public InvalidMethodRequest(String message){
        super(message);
    }
}
