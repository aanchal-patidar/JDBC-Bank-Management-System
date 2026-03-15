package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/mydb";
    private static final String user = "postgres";
    private static final String password = "Mypassword";

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(url, user, password);
    }
}