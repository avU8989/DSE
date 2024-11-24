package kickstart;

import kickstart.LogEntry;

import java.util.ArrayList;
import java.util.Objects;

public interface RemoteService {
    void logSingleEntry(LogEntry entry);
    void removeOldLogs(int amountToRemove);
    void addLogsInBulk(LogEntry[] logBulk);
    LogEntry[] search(String searchTerm);
}
