package client;

import client.protocol.RPCMethod;
import client.protocol.RPCParameters;
import client.protocol.RemoteService;

import java.io.IOException;
import java.util.logging.Logger;

public class ClientProxy implements RemoteService {
    ClientRequestor requestor;
    private static final Logger logger = Logger.getLogger(ClientProxy.class.getName());

    public ClientProxy(ClientRequestor requestor){
        this.requestor = requestor;
    }

    @Override
    public String hello(String name) throws IOException {
        RPCMethod method = RPCMethod.HELLO;
        RPCParameters parameters = new RPCParameters(name);
        //we have to forward the method call to the requestor
        String ret = requestor.invokeRemoteRequest(method, parameters);
        return ret;
    }

    @Override
    public String goodbye(String name) throws IOException {
        RPCMethod method = RPCMethod.GOODBYE;
        RPCParameters parameters = new RPCParameters(name);
        return requestor.invokeRemoteRequest(method, parameters);
    }
}
