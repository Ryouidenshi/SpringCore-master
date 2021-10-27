package database;

import bean.Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private final Connection connection;

    public DatabaseService(String url, String name, String password) {
        try {
            connection = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can not connect to database");
        }
    }


    public void addEvent(Event event) {
        try {
            connection.setAutoCommit(false);
            EventDAO dao = new EventDAO(connection);
            dao.createTable();
            dao.insert(event);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {

            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {

            }
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
