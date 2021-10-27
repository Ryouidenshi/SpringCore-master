package database;

import bean.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventDAO {

    private final Executor executor;

    public EventDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void insert(Event event) throws SQLException {
        executor.execUpdate(
                "INSERT INTO Events (Message, DateTime) " +
                "VALUES ('" + event.getMsg().replace("'","") + "', '"
                        + dateToSqlDateTimeString(event.getDate()) + "');"
        );
    }


    public void createTable() throws SQLException {
        executor.execUpdate(
                "CREATE TABLE IF NOT EXISTS Events" +
                "(" +
                "Id INT AUTO_INCREMENT," +
                "Message VARCHAR(256)," +
                "DateTime DATETIME," +
                "PRIMARY KEY (Id)" +
                ");"
        );
    }

    private String dateToSqlDateTimeString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String month = Integer.toString(calendar.get(Calendar.MONTH));
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE) + ":"
                + calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + day + " " + time;
    }
}
