package swa.meet.exceptions;

public class AlreadySubmittedAttendanceException extends RuntimeException{
    public AlreadySubmittedAttendanceException(String message){
        super(message);
    }
}
