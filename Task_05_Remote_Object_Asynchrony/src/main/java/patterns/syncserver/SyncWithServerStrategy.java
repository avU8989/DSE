package patterns.syncserver;

import exceptions.SyncWithServerException;
import kickstart.IMarshall;
import network.NetworkRequestHandler;
import patterns.AsyncInvocationStrategy;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SyncWithServerStrategy<T> implements AsyncInvocationStrategy<AcknowledgmentMessage<T>> {
    private static final Logger logger = Logger.getLogger(SyncWithServerStrategy.class.getName());
    private final NetworkRequestHandler networkRequestHandler;
    private final ExecutorService executor;

    public SyncWithServerStrategy(NetworkRequestHandler networkRequestHandler) {
        this.networkRequestHandler = networkRequestHandler;
        this.executor = Executors.newCachedThreadPool();
    }

    @Override
    public AcknowledgmentMessage<T> invokeAsync(byte[] requestMessage) throws ExecutionException, InterruptedException {
        return executor.submit(() -> {
            try {
                networkRequestHandler.sendMessage(requestMessage);
                logger.info("Request message sent, waiting for acknowledgment...");

                while (true) {
                    try {
                        byte[] response = networkRequestHandler.readMessage();
                        Object unmarshalledResponse = IMarshall.unmarshall(response);

                        if (unmarshalledResponse instanceof AcknowledgmentMessage) {
                            AcknowledgmentMessage<T> ack = (AcknowledgmentMessage<T>) unmarshalledResponse;
                            return ack;
                        }
                    } catch (ClassNotFoundException e) {
                        logger.severe("ClassNotFoundException during unmarshalling: " + e.getMessage());
                        throw new SyncWithServerException("Error during unmarshalling the response");
                    } catch (IOException e) {
                        logger.severe("IO Exception during request processing: " + e.getMessage());
                        throw new SyncWithServerException("Error during invoking the RequestMessage for the strategy: SyncWithServer");
                    }
                }

            } catch (Exception e) {
                logger.severe("General Exception during request processing: " + e.getMessage());
                throw new SyncWithServerException("Error during processing the request message");
            }
        }).get();
    }
}
