package com.edu.epam.finaltask.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final int DEFAULT_POOL_SIZE = 8;
    private static AtomicBoolean isCreated = new AtomicBoolean();
    private static ConnectionPool instance = new ConnectionPool();
    private static Lock locker = new ReentrantLock(true);
    private static BlockingQueue<ProxyConnection> freeConnection;
    private static BlockingQueue<ProxyConnection> givenConnections;

   private ConnectionPool() {
        freeConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        logger.log(Level.INFO, "Try to create connection pool");
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnection.add(proxyConnection);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "can't create connection to database : " + e.getMessage());
            }
        }
        if (freeConnection.size() == 0) {
            logger.log(Level.FATAL, "connections pool don't created, pool size: " + freeConnection.size());
            throw new RuntimeException("connection pool don't created");
        }
        logger.log(Level.INFO, "Connection pool was created");
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            locker.lock();
            if (instance == null) {
                instance = new ConnectionPool();
                isCreated.set(true);
            }
            locker.unlock();
        }
        return instance;
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnection.take();
            givenConnections.add((ProxyConnection) connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "InterruptedException in method getConnection " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && givenConnections.remove(connection)) {
            try {
                freeConnection.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "InterruptedException in method getConnection " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            closeAnyConnection();
        }
        deregisterDrivers();
    }

    private void closeAnyConnection() {
        try {
            freeConnection.take().reallyClose();
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "InterruptedException in method destroyPool " + e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in method destroyPool " + e.getMessage());
        }
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SQLException in method deregisterDrivers " + e.getMessage());
            }
        });
    }
}
