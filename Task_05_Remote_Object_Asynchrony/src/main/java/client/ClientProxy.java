package client;

import kickstart.*;
import patterns.resultcallback.ResultCallback;
import patterns.resultcallback.ResultCallbackObject;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

public class ClientProxy implements IRemoteService {
    private ClientRequestor clientRequestor;
    private static final Logger logger = Logger.getLogger(ClientProxy.class.getName());

    public ClientProxy(ClientRequestor clientRequestor) {
        this.clientRequestor = clientRequestor;
    }

    @Override
    public ResponseMessage<Boolean> logSingleEntry(LogEntry entry) {

        RequestMessage<LogEntry> singleLogRequest = new RequestMessage<>(KnownMethods.singleLog, entry, UUID.randomUUID());
        logger.info("Requesting singleLog operation.............");
        return (ResponseMessage<Boolean>) this.clientRequestor.invokeRemoteRequest(singleLogRequest);

    }

    @Override
    public ResponseMessage<String> removeOldLogs(int amountToRemove) {
        RequestMessage<Integer> removeLogRequest = new RequestMessage<>(KnownMethods.removeOldLogs, amountToRemove, UUID.randomUUID());
        logger.info("Requesting removeLog operation.............");
        return (ResponseMessage<String>) this.clientRequestor.invokeRemoteRequest(removeLogRequest);
    }

    @Override
    public ResponseMessage<String> addLogsInBulk(LogEntry[] logBulk) {
        RequestMessage<LogEntry[]> addLogsInBulkRequest = new RequestMessage<>(KnownMethods.addLogsInBulk, logBulk, UUID.randomUUID());
        logger.info("Requesting addLogsInBulk operation.............");
        logger.info(Arrays.toString(logBulk));

        return (ResponseMessage<String>) this.clientRequestor.invokeRemoteRequest(addLogsInBulkRequest);

    }

    public void searchLogs(String searchTerm, ResultCallback<LogEntry[]> callback) {
        ResultCallbackObject<LogEntry[]> callbackObject = new ResultCallbackObject<>(callback);
        RequestMessage<String> requestMessage = new RequestMessage<>(KnownMethods.searchLogs, searchTerm, UUID.randomUUID(), callbackObject);
        logger.info("Requesting searchLogs operation.............");
        clientRequestor.invokeRemoteRequestWithCallback(requestMessage, callback);
    }

}
