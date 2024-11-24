package patterns.fireforget;

import client.ClientProxy;
import kickstart.RequestMessage;
import kickstart.ResponseMessage;
import network.NetworkRequestHandler;
import patterns.AsyncInvocationStrategy;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


//sends invocation across the network
//returns control to the calling client immediately
//client does not get any acknowledgement

//for storing logs
public class FireAndForgetStrategy<T> implements AsyncInvocationStrategy<ResponseMessage<T>> {
    private static final Logger logger = Logger.getLogger(FireAndForgetStrategy.class.getName());
    private NetworkRequestHandler networkRequestHandler;
    private ExecutorService executorService;

    public FireAndForgetStrategy(NetworkRequestHandler networkRequestHandler){
        this.networkRequestHandler = networkRequestHandler;
        this.executorService = Executors.newCachedThreadPool();

    }

    @Override
    public ResponseMessage<T> invokeAsync(byte[] requestMessage) {
        this.executorService.submit(() -> {
            try{
                networkRequestHandler.sendMessage(requestMessage);
                return new ResponseMessage(true);
            } catch (IOException e) {
                logger.severe("Error during invoking the RequestMessage for the strategy: Fire and Forget");
                return new ResponseMessage(false);

            } catch (Exception e) {
                logger.severe("Error during marshalling the RequestMessage for the strategy: Fire and Forget");
                return new ResponseMessage(false);

            }
        });

        return new ResponseMessage(false);

    }
}
