package client;

import kickstart.RequestMessage;
import network.NetworkRequestHandler;
import patterns.AsyncInvocationStrategy;

import java.io.IOException;
import java.util.logging.Logger;

public class ClientRequestHandler<T> {
    private NetworkRequestHandler networkRequestHandler;

    public ClientRequestHandler(NetworkRequestHandler networkRequestHandler){
        this.networkRequestHandler = networkRequestHandler;
    }


    public T sendRequest(AsyncInvocationStrategy<T> asyncInvocationStrategy, byte[] requestMessage){
       if(asyncInvocationStrategy == null) {
           throw new IllegalStateException("Async Invocation Strategy has not been set up");
       }
       try{
           return asyncInvocationStrategy.invokeAsync(requestMessage);

       }catch(Exception e){
           e.printStackTrace();
           throw new RuntimeException("Error during invoking Async");
       }
    }

    public NetworkRequestHandler getNetworkRequestHandler() {
        return networkRequestHandler;
    }
}
