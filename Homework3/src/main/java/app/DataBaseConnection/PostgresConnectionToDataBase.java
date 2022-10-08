package app.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionToDataBase {
    public static Connection getConnection() {

        try {
            Class.forName(PostgresPropereties.DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        try {
            Connection connection = DriverManager.getConnection(PostgresPropereties.URL, PostgresPropereties.USERNAME, PostgresPropereties.PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
