package logs;

import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private List<LogListener> listeners = new ArrayList<>();

    public void addListener(LogListener listener) {
        listeners.add(listener);
    }

    public void log(String type, String source, String message) {
        LogEvent event = new LogEvent(type, source, message);
        for (LogListener listener : listeners) {
            listener.onLogEvent(event);
        }
    }
}
