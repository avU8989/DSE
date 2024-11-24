package client.protocol;

public class RPCParameters {
    private String parameter;

    public RPCParameters(String parameter){
        this.parameter = parameter;
    }

    public String getParameter(){
        return this.parameter;
    }
}
