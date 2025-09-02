package logs;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements LogListener {
    private LogFormatter formatter;
    private String filePath;

    public FileLogger(LogFormatter formatter, String filePath) {
        this.formatter = formatter;
        this.filePath = filePath;
    }

    @Override
    public void onLogEvent(LogEvent event) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(formatter.format(event) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
