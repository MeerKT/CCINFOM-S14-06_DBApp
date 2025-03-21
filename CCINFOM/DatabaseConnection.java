import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/bank_database"; // Change to your database URL
    private static final String USERNAME = "root"; // Your MySQL username
    private static final String PASSWORD = "password"; // Your MySQL password

    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establishing the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found. Please add the JDBC connector JAR file to your project.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Please check your URL, username, or password.");
            e.printStackTrace();
        }

        return connection;
    }
}
