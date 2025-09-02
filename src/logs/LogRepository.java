package logs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogRepository implements LogListener {
    private List<LogEvent> allLogs = new ArrayList<>();

    @Override
    public void onLogEvent(LogEvent event) {
        allLogs.add(event);
    }

    public List<LogEvent> getLogsByIteration(long iterationTimestamp) {
        return allLogs.stream()
                .filter(log -> log.getTimestamp() == iterationTimestamp)
                .collect(Collectors.toList());
    }

    public List<LogEvent> getLogsByNode(String nodeName) {
        return allLogs.stream()
                .filter(log -> log.getSource().equals(nodeName))
                .collect(Collectors.toList());
    }

    public List<LogEvent> getAllLogs() {
        return new ArrayList<>(allLogs);
    }
}
