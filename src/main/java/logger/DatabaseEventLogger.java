package logger;

import bean.Event;
import database.DatabaseService;
import org.h2.engine.Database;

public class DatabaseEventLogger implements EventLogger {

    DatabaseService databaseService;

    public DatabaseEventLogger(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void logEvent(Event event) {
        databaseService.addEvent(event);
    }
}
