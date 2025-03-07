package swa.meet.exceptions;

public class ErrorDetails {
    private final int statusCode;
    private final String message;
    private final String details;

    public ErrorDetails(int statusCode, String message, String details){
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
