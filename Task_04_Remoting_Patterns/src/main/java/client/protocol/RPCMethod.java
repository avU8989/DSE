package client.protocol;

public enum RPCMethod  {
    HELLO("Hello"),
    GOODBYE("Goodbye");

    private final String methodName;

    RPCMethod(String methodName){
        this.methodName = methodName;
    }

    public String getMethodName(){
        return this.methodName;
    }
}
