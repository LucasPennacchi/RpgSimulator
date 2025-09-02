package logs;

public interface LogFormatter {
    String format(LogEvent event);
}

public class SimpleTextFormatter implements LogFormatter {
    @Override
    public String format(LogEvent event) {
        return "[" + event.getType() + "] " +
                event.getSource() + ": " +
                event.getMessage() + " (" + event.getTimestamp() + ")";
    }
}

public class JsonFormatter implements LogFormatter {
    @Override
    public String format(LogEvent event) {
        return "{ \"type\": \"" + event.getType() + "\", " +
                "\"source\": \"" + event.getSource() + "\", " +
                "\"message\": \"" + event.getMessage() + "\", " +
                "\"timestamp\": " + event.getTimestamp() + " }";
    }
}
