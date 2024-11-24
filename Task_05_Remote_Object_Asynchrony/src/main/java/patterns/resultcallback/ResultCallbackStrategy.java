package patterns.resultcallback;

import kickstart.IMarshall;
import kickstart.LogEntry;
import kickstart.ResponseMessage;
import network.NetworkRequestHandler;
import patterns.AsyncInvocationStrategy;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ResultCallbackStrategy<T> implements AsyncInvocationStrategy<Void> {
    private final ResultCallback<T> callback;
    private final ExecutorService executorService;
    private final NetworkRequestHandler networkRequestHandler;
    private static final Logger logger = Logger.getLogger(ResultCallbackStrategy.class.getName());

    public ResultCallbackStrategy(ResultCallback<T> callback, NetworkRequestHandler networkRequestHandler) {
        this.callback = callback;
        this.networkRequestHandler = networkRequestHandler;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public Void invokeAsync(byte[] requestMessage) {
        executorService.submit(() -> {
            try {
                // Send the initial request
                logger.info("Sending request...");
                networkRequestHandler.sendMessage(requestMessage);

                ResponseMessage<T> unmarshalledResponse;
                while (true) {
                    byte[] response = networkRequestHandler.readMessage();
                    unmarshalledResponse = IMarshall.unmarshall(response);
                    logger.info("Response received: " + unmarshalledResponse.getResponseData().toString());

                    // Check if the response is an intermediate message
                    String responseDataString = unmarshalledResponse.getResponseData().toString();
                    if (!"Operation in progress".equals(responseDataString) && !"Search initiated".equals(responseDataString)) {
                        break; // Exit loop if it's the final result
                    }
                }

                // Notify the callback with the result
                callback.onResult(unmarshalledResponse.getResponseData());
            } catch (IOException e) {
                e.printStackTrace();
                logger.severe("Error during request: " + e.getMessage());
                callback.onError(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                logger.severe("Unexpected error: " + e.getMessage());
                callback.onError(e.getMessage());
            }
        });
        return null;
    }
}
