package client;

import kickstart.LogEntry;
import kickstart.RequestMessage;
import kickstart.ResponseMessage;
import network.NetworkRequestHandler;
import patterns.AsyncInvocationStrategy;
import patterns.fireforget.FireAndForgetStrategy;
import patterns.polling.PollingStrategy;
import patterns.resultcallback.ResultCallback;
import patterns.resultcallback.ResultCallbackObject;
import patterns.resultcallback.ResultCallbackStrategy;
import patterns.syncserver.AcknowledgmentMessage;
import patterns.syncserver.SyncWithServerStrategy;

import java.util.logging.Logger;

public class ClientRequestor {
    private ClientRequestHandler clientRequestHandler;
    private static final Logger logger = Logger.getLogger(ClientRequestor.class.getName());

    public ClientRequestor(ClientRequestHandler clientRequestHandler) {
        this.clientRequestHandler = clientRequestHandler;
    }

    public Object invokeRemoteRequest(RequestMessage<?> request) {
        ResponseMessage<?> response = null;
        try {
            byte[] marshalled = request.marshall();
            AsyncInvocationStrategy<?> invocationStrategy;
            NetworkRequestHandler networkRequestHandler = clientRequestHandler.getNetworkRequestHandler();
            switch (request.getMethod()) {
                case singleLog:
                    invocationStrategy = new FireAndForgetStrategy(networkRequestHandler);
                    return clientRequestHandler.sendRequest(invocationStrategy, marshalled);
                case addLogsInBulk:
                    invocationStrategy = new PollingStrategy<String>(networkRequestHandler);
                    response = (ResponseMessage<String>) clientRequestHandler.sendRequest(invocationStrategy, marshalled);
                    return response;
                case removeOldLogs:
                    invocationStrategy = new SyncWithServerStrategy<String>(networkRequestHandler);
                    AcknowledgmentMessage ackResponse = (AcknowledgmentMessage) clientRequestHandler.sendRequest(invocationStrategy, marshalled);
                    return ackResponse;
                case searchLogs:
                    ResultCallbackObject<LogEntry[]> callbackObject = (ResultCallbackObject<LogEntry[]>) request.getCallbackObject();
                    invocationStrategy = new ResultCallbackStrategy<>(callbackObject, networkRequestHandler);
                    clientRequestHandler.sendRequest(invocationStrategy, marshalled);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown method: " + request.getMethod());
            }
        } catch (Exception e) {
            logger.severe("Error during marshalling: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    public <T> void invokeRemoteRequestWithCallback(RequestMessage<?> request, ResultCallback<T> callback) {
        try {
            ResultCallbackObject<T> resultCallbackObject = new ResultCallbackObject<>();
            request.setCallbackObject(resultCallbackObject);
            byte[] marshalled = request.marshall();
            AsyncInvocationStrategy<Void> invocationStrategy = new ResultCallbackStrategy<LogEntry[]>((ResultCallback<LogEntry[]>) callback, clientRequestHandler.getNetworkRequestHandler());
            clientRequestHandler.sendRequest(invocationStrategy, marshalled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
