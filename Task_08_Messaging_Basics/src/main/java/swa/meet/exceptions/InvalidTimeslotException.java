package swa.meet.exceptions;

public class InvalidTimeslotException extends RuntimeException{

    public InvalidTimeslotException(String message){
        super(message);
    }
}
