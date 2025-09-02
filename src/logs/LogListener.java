package logs;

public interface LogListener {
    void onLogEvent(LogEvent event);
}

public class ConsoleLogger implements LogListener {
    private LogFormatter formatter;

    public ConsoleLogger(LogFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void onLogEvent(LogEvent event) {
        System.out.println(formatter.format(event));
    }
}
