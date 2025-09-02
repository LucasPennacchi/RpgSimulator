package logs;

public class LogEvent {
    private String type;      // Ex: "POPULATION", "WAR", "ECONOMY"
    private String source;    // Origem do log, ex: "managers.PopulationManager"
    private String message;   // Texto descritivo
    private long timestamp;   // Momento do log

    public LogEvent(String type, String source, String message) {
        this.type = type;
        this.source = source;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getType() { return type; }
    public String getSource() { return source; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}
