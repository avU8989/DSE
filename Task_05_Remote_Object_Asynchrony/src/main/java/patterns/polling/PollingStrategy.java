package patterns.polling;

import kickstart.IMarshall;
import kickstart.RequestMessage;
import kickstart.ResponseMessage;
import network.NetworkRequestHandler;
import patterns.AsyncInvocationStrategy;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class PollingStrategy<T> implements AsyncInvocationStrategy<ResponseMessage<T>> {
    private static final Logger logger = Logger.getLogger(PollingStrategy.class.getName());
    private final ExecutorService executor;
    private static final int POLL_INTERVAL = 3000;
    private final NetworkRequestHandler networkRequestHandler;

    public PollingStrategy(NetworkRequestHandler networkRequestHandler) {
        this.executor = Executors.newCachedThreadPool();
        this.networkRequestHandler = networkRequestHandler;
    }

    @Override
    public ResponseMessage<T> invokeAsync(byte[] requestMessage) throws Exception {
        PollObject<T> pollResult = new PollObject<>();
        logger.info("Invoking with Polling pattern");
        RequestMessage<T> request = IMarshall.unmarshall(requestMessage);

        // Simulate compression delay before sending the initial request
        executor.submit(() -> {
            try {
                Thread.sleep(5000);
                logger.info("Sending initial request...");
                networkRequestHandler.sendMessage(requestMessage);

                if (pollResult.isComplete()) {
                    logger.info("Initial response marked operation as completed.");
                } else {
                    System.out.println("Result not available yet. Doing other tasks...");
                    startPolling(pollResult, request);
                }
            } catch (IOException e) {
                logger.severe("Error during sending initial request: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            return pollResult.awaitResult();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error during retrieving result", e);
        }
    }

    private void startPolling(PollObject<T> pollResult, RequestMessage<T> request) {
        executor.submit(() -> {
            while (!pollResult.isComplete()) {
                try {
                    System.out.println("Polling..............");
                    Thread.sleep(POLL_INTERVAL);
                    logger.info("Sending poll request...");
                    PollRequest pollRequest = new PollRequest<>(request.getMethod(), request.getRequestData(), request.getRequestID());
                    networkRequestHandler.sendMessage(pollRequest.marshall());
                    byte[] response = networkRequestHandler.readMessage();
                    ResponseMessage<T> unmarshalledResponse = IMarshall.unmarshall(response);
                    logger.info("Poll response received: " + unmarshalledResponse.getResponseData());
                    pollResult.update(unmarshalledResponse);
                } catch (IOException | InterruptedException e) {
                    logger.severe("Error during polling: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
