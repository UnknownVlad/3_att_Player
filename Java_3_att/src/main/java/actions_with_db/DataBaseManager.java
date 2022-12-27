package actions_with_db;

import java.sql.*;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "vlad";
    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    public static final String DRIVER = "org.postgresql.Driver";


    public static Connection getDBConnection() {
        Connection connection = null;
        System.out.println(connection);
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void createTabel(String request, Connection connection) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(request);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTabel(String request, Connection connection){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(request);
        } catch (SQLException e) {
            System.out.println("+" + e.getMessage());
        }

    }
    public static void executeUpdate(String request, Connection connection) throws SQLException {
        connection.createStatement().executeUpdate(request);
    }
    public static ResultSet executeUpdateQuaery(String request, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(request);
    }
    public static void insertListToDataBase(List<String> insertRequests, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (String a: insertRequests) {
            statement.executeUpdate(a);
        }
    }
    public static ResultSet getResultSet(String request, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(request);
    }

}
