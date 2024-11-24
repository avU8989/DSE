package client;

import kickstart.LogEntry;
import kickstart.ResponseMessage;
import patterns.resultcallback.ResultCallback;
import patterns.resultcallback.ResultCallbackObject;

public interface IRemoteService {
    ResponseMessage<Boolean> logSingleEntry(LogEntry entry);
    ResponseMessage<String> removeOldLogs(int amountToRemove);
    ResponseMessage<String> addLogsInBulk(LogEntry[] logBulk);
    void searchLogs(String searchTerm, ResultCallback<LogEntry[]> callback);
}
