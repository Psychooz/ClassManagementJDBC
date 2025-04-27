package xyz.ziadboukhalkhal.servlet.classmanagement.dao;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Enumeration;
import java.sql.Driver;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class DatabaseManager {
    private static final String CONF_FILE = "database.properties";
    private static Properties databaseConfig = new Properties();
    private static DatabaseManager instance;
    private static Connection connection;

    private DatabaseManager() {
        loadDriver();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void loadDriver() {
        try {
            databaseConfig.load(this.getClass().getClassLoader().getResourceAsStream(CONF_FILE));
            Class.forName(databaseConfig.getProperty("database.driver"));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        databaseConfig.getProperty("database.url"),
                        databaseConfig.getProperty("database.username"),
                        databaseConfig.getProperty("database.password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Cleanup on shutdown
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
                // Deregister JDBC driver
                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()) {
                    Driver driver = drivers.nextElement();
                    if (driver.getClass().getName().equals("com.mysql.cj.jdbc.Driver")) {
                        DriverManager.deregisterDriver(driver);
                    }
                }
                // Stop cleanup thread
                AbandonedConnectionCleanupThread.checkedShutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}