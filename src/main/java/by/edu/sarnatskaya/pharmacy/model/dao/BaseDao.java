package by.edu.sarnatskaya.pharmacy.model.dao;

import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.entity.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public interface BaseDao< T extends AbstractEntity> {
    static Logger logger = LogManager.getLogger();

    List<T> findAll() throws DaoException;


    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Try to close statement was failed");
            }
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Try to close connection was failed");

        }
    }
}
