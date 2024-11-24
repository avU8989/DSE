package client.protocol;

public class RPCRequest {
    private final String methodName;
    private final String message;
    private String errorMessage;

    public RPCRequest(){
        this.methodName = "";
        this.message = "";
    }

    public RPCRequest(String methodName, String message){
       this.methodName = methodName;
       this.message = message;
       this.errorMessage = "";
    }
    public RPCRequest(String message){
        this.methodName = "";
        this.message = message;
        this.errorMessage = "";
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMessage() {
        return message;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "RPCMessage{" +
                "methodName='" + methodName + '\'' +
                ", parameter='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
