package exceptions;

public class UndefinedMethodException extends RuntimeException{
    public UndefinedMethodException(String message){
        super(message);
    }
}
