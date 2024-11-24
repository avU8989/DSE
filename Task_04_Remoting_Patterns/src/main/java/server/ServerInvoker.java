package server;

import marshaller.Marshaller;
import client.protocol.RPCRequest;

import java.io.IOException;
import java.util.logging.Logger;

public class ServerInvoker {
    private final RemoteServiceImpl remoteService;
    private final Marshaller marshaller;
    private static final Logger logger = Logger.getLogger(ServerInvoker.class.getName());

    public ServerInvoker(RemoteServiceImpl remoteService, Marshaller marshaller){
        this.remoteService = remoteService;
        this.marshaller = marshaller;
    }

    public byte[] invoke(byte[] request) throws IOException {
        RPCRequest message = marshaller.unmarshal(request);
        String methodName = message.getMethodName();
        String parameterName = message.getMessage();

        String result;

        switch (methodName) {
            case "Hello":
                result = remoteService.hello(parameterName);
                break;
            case "Goodbye":
                result = remoteService.goodbye(parameterName);
                break;
            default:
                result = "Error: Invalid method";
                break;
        }

        byte[] msg = marshaller.marshal("", result);

        return msg;
    }



}
