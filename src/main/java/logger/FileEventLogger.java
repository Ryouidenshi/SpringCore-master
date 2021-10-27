package logger;

import bean.Event;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    private final String fileName;
    private File file;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    private void init() throws IOException {
        file = new File(fileName);

        if (!file.exists()) {
            throw new IllegalArgumentException("File not found");
        }

        if (!file.canWrite()) {
            throw new IllegalArgumentException("Can't write to file");
        }
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString() + "\n", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
