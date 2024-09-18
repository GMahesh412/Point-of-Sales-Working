package com.pos.point_of_sales.controller.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBCConnectionFactory is responsible for establishing JDBC connections to the db & manages exceptions and logs.
 * @author Mahesh Yadav
 */
public class JDBCConnectionFactory {

    private static final Logger LOG = LoggerFactory.getLogger(JDBCConnectionFactory.class);

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/inventorya?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    /**
     * Retrieves a connection to the database.
     * @return Connection object representing the database connection.
     * @throws SQLException if a database access error occurs or the url is null
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            handleSQLException(e);
            throw e;
        }
    }

    /**
     * Handles SQLException by logging details of the exception.
     * @param ex The SQLException to handle.
     */
    public static void handleSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                logSQLException((SQLException) e);
            }
        }
    }

    /**
     * Logs details of the SQLException.
     * @param ex The SQLException to log.
     */
    private static void logSQLException(SQLException ex) {
        LOG.error("SQLState: {}", ex.getSQLState());
        LOG.error("Error Code: {}", ex.getErrorCode());
        LOG.error("Message: {}", ex.getMessage());

        Throwable t = ex.getCause();
        while (t != null) {
            LOG.error("Cause: {}", t);
            t = t.getCause();
        }
    }
}
