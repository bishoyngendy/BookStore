/**
 * Created by programajor on 5/4/18.
 */

package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static String DATABASE_URL = "jdbc:mysql://localhost:3306/BOOK_STORE?autoReconnect=true&useSSL=false";
    private static String DATABASE_USERNAME = "root";
    private static String DATABASE_PASSWORD = "";

    private static Connection instance = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        instance = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        return instance;
    }
}
