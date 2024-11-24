package client;

import kickstart.LogEntry;
import kickstart.ResponseMessage;
import network.ConnectionRole;
import network.UDPNetworkHandler;
import patterns.polling.PollObject;
import patterns.resultcallback.ResultCallback;
import patterns.resultcallback.ResultCallbackObject;
import patterns.syncserver.AcknowledgmentMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Client {
    private ClientProxy proxy;
    public Client(ClientProxy proxy) {
        this.proxy = proxy;
    }

    public void logSingleEntry(LogEntry entry) {
        proxy.logSingleEntry(entry);
        System.out.println("Log single entry sent");
    }

    public void removeOldLogs(int toRemove){
        ResponseMessage acknowledgmentMessage =  proxy.removeOldLogs(toRemove);
        System.out.println(acknowledgmentMessage.getResponseData());
    }

    public void addLogsInBulk(LogEntry[] logEntries) {
        try {
            ResponseMessage<String> response = proxy.addLogsInBulk(logEntries);

            PollObject<String> pollObject = new PollObject<>();
            pollObject.update(response);

            ResponseMessage<String> finalResponse = pollObject.awaitResult();
            System.out.println("Adding Logs in Bulk with Polling");
            System.out.println("Final Response: " + finalResponse.getResponseData());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void searchLogs(String searchTerm) {
        proxy.searchLogs(searchTerm, new ResultCallback<LogEntry[]>() {
            @Override
            public void onResult(LogEntry[] result) {
                System.out.println("Searched Log entries are: ");
                for (LogEntry entry : result) {
                    System.out.println("Log entry: " + entry.getLogEntry());
                }
            }

            @Override
            public void onError(String e) {
                System.err.println("Error: " + e);

            }
        });
    }
}
