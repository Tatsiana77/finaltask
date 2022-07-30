package by.edu.sarnatskaya.pharmacy.model.connection;

import com.mysql.jdbc.Driver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

class ConnectionFactory {

    private static final Logger logger = LogManager.getLogger();

    private static final String BUNDLE_STYLE ="db";
    private static final String DB_URL = "db.url";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_USER = "db.user";
    private static final String DB_DRIVER = "db.driver";
    private static final String DATABASE_URL;
    private static final String DATABASE_PASSWORD;
    private static final String DATABASE_USER;
    private static final String DATABASE_DRIVER;

    static {

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_STYLE);
            DATABASE_URL =bundle.getString(DB_URL);
            DATABASE_PASSWORD = bundle.getString(DB_PASSWORD);
            DATABASE_USER = bundle.getString(DB_USER);
            DATABASE_DRIVER = bundle.getString(DB_DRIVER);
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "driver: " + BUNDLE_STYLE+ "not found");
            throw new RuntimeException("Driver is not found ");
        }
    }

    private ConnectionFactory() {
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

}
