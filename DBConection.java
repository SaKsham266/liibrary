import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
class DBConnection {
    private static final String URL = "jdbc:mariadb://localhost:3307/LibraryDB?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root"; // Replace with your MariaDB username
    private static final String PASSWORD = "qwerty"; // Replace with your MariaDB password

    public static Connection getConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }
        return null;
    }
}

