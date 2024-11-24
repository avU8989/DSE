package client;

import client.protocol.RPCMethod;
import client.protocol.RPCParameters;
import client.protocol.RPCRequest;
import exceptions.InvalidMethodRequest;
import marshaller.Marshaller;

import java.util.logging.Logger;

public class ClientRequestor {
    private ClientRequestHandler clientRequestHandler;
    private Marshaller marshaller;
    private static final Logger logger = Logger.getLogger(ClientRequestor.class.getName());

    public ClientRequestor(ClientRequestHandler clientRequestHandler, Marshaller marshaller){
        this.clientRequestHandler = clientRequestHandler;
        this.marshaller = marshaller;
    }

    //here you can try to change the first parameter of the marshal to an unknown Method call,
    //the server will send an errorMessage in the response Object
    //we will verify if the errorMessage is empty or not if its not empty we will throw our own Exception for invalid Method calls
    public String invokeRemoteRequest(RPCMethod method, RPCParameters parameter){
        byte[] request = this.marshaller.marshal(method.getMethodName(), parameter.getParameter());
        //byte[] requestWithUnknownMethodCall = this.marshaller.marshal("UNKNOWN METHOD", parameter.getParameter());
        clientRequestHandler.sendRequest(request);
        byte[] response = clientRequestHandler.receiveResponse();

        RPCRequest rpcRequest = this.marshaller.unmarshal(response);
        if(!rpcRequest.getErrorMessage().isEmpty()){
            throw new InvalidMethodRequest(rpcRequest.getMessage());
        }
        return rpcRequest.getMessage();
    }

}
