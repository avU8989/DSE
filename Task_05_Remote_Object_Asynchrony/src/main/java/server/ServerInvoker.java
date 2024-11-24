package server;

import exceptions.UndefinedMethodException;
import kickstart.*;
import patterns.polling.PollRequest;
import patterns.resultcallback.ResultCallbackObject;
import patterns.syncserver.AcknowledgmentMessage;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ServerInvoker {
    private final RemoteObject remoteObject;
    private static final Logger logger = Logger.getLogger(ServerInvoker.class.getName());
    private final Map<UUID, Boolean> completedRequests = new ConcurrentHashMap<>();

    public ServerInvoker(RemoteObject remoteObject) {
        this.remoteObject = remoteObject;
    }

    public byte[] invoke(byte[] request) throws Exception {
        try {
            RequestMessage<?> requestMessage = IMarshall.unmarshall(request);
            UUID requestId = requestMessage.getRequestID();
            logger.info("Received request: " + requestMessage.toString());

            if (requestMessage instanceof PollRequest) {
                return handlePollRequest(requestId);
            }

            switch (requestMessage.getMethod()) {
                case singleLog:
                    return handleSingleLog(requestMessage);

                case removeOldLogs:
                    return handleRemoveOldLogs(requestMessage);

                case addLogsInBulk:
                    return handleAddLogsInBulk(requestMessage, requestId);

                case searchLogs:
                    return handleSearchLogs(requestMessage);

                default:
                    throw new UndefinedMethodException("Method not known to the remote object!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error during marshalling request message from client");
            return new ResponseMessage<>("Error processing request").marshall();
        }
    }

    private byte[] handlePollRequest(UUID requestId) throws Exception {
        logger.info("Handling poll request. Request ID: " + requestId);
        boolean isComplete = completedRequests.getOrDefault(requestId, false);
        logger.info("Checking status for request ID: " + requestId + " - Complete: " + isComplete);
        logger.info("This is the remote for polling: " + remoteObject);

        if (isComplete) {
            logger.info("Operation completed for request ID: " + requestId);
            completedRequests.remove(requestId);
            return new ResponseMessage<>("Operation completed").marshall();
        } else {
            logger.info("Operation in progress for request ID: " + requestId);
            return new ResponseMessage<>("Operation in progress").marshall();
        }
    }

    private byte[] handleSingleLog(RequestMessage<?> requestMessage) throws Exception {
        LogEntry logEntry = (LogEntry) requestMessage.getRequestData();
            remoteObject.logSingleEntry(logEntry);

        logger.info("LogEntry added: " + logEntry);
        logger.info("Current state of remote object: " + remoteObject);
        return new ResponseMessage<>("Single log entry processed").marshall();
    }

    private byte[] handleRemoveOldLogs(RequestMessage<?> requestMessage) throws Exception {
        int amountToRemove = (int) requestMessage.getRequestData();
        remoteObject.removeOldLogs(amountToRemove);
        logger.info("Current state of remote object after removing logs: " + remoteObject);
        return new AcknowledgmentMessage<>("Removed old logs successfull", true).marshall();
    }

    private byte[] handleAddLogsInBulk(RequestMessage<?> requestMessage, UUID requestId) throws Exception {
        logger.info("Handling initial addLogsInBulk request. Request ID: " + requestId);
        try {
            // Simulate space reservation delay
            Thread.sleep(3000);
            logger.info("Simulating space reservation delay for request ID: " + requestId);
            LogEntry[] logBulk = (LogEntry[]) requestMessage.getRequestData();
            remoteObject.addLogsInBulk(logBulk);
            logger.info("This is the remote for adding logs in bulk: " + remoteObject);
            completedRequests.put(requestId, true);
            logger.info("Completed processing addLogsInBulk for request ID: " + requestId);
            return new ResponseMessage<>("Operation completed").marshall();
        } catch (InterruptedException e) {
            logger.severe("Error processing addLogsInBulk: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] handleSearchLogs(RequestMessage<?> requestMessage) throws Exception {
        String searchTerm = (String) requestMessage.getRequestData();
        LogEntry[] results;
        results = remoteObject.search(searchTerm);
        logger.info("This is the remote for searching: " + remoteObject);
        ResponseMessage<LogEntry[]> response = new ResponseMessage<>(results);
        logger.info("Search results: " + response);
        return response.marshall();
    }
}
