package by.edu.sarnatskaya.pharmacy.model.dao.impl;

import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.connection.ConnectionPool;
import by.edu.sarnatskaya.pharmacy.model.dao.OrdersDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class OrdersDaoImpl implements OrdersDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

    private  static  final String FIND_ALL_ORDERS = "SELECT orders.id, created, amount, dose, cost , orders.status_id, orders.users_id " +
            "FROM orders " +
            "JOIN users ON orders.id = users_id  " +
            "JOIN purchases ON  orders_id = purchases.id " +
            "JOIN orders_status ON orders.status_id= orders_status.id  ";

    private static final String FIND_ORDERS_BY_USERS_ID   = FIND_ALL_ORDERS + "WHERE  orders.users_id = ?";

    private static final String FIND_ORDER_BY_STATUS = "SELECT orders.id, created, amount, dose, cost , orders.status_id, orders.users_id,  " +
            "FROM orders " +
            "JOIN orders_status ON orders.status_id= orders_status.id " +
            "WHERE  orders_id =?";

    private static final String UPDATE_ORDERS_STATUS = "UPDATE orders SET status_id = ? WHERE id = ?";

    private static final String INSERT_NEW_ORDERS = "INSERT INTO  orders(created, amount, dose, cost, status_id,  users_id) VALUES (?, ?, ? , ?, ? , ?)";

    private static final String INSERT_PREPARATION_IN_ORDERS = "INSERT INTO purchases (id, quantity, orders_id, preparations_id) VALUES(?, ?, ?, ?)";

    private static final String DELETE_PREPARATION_FROM_ORDERS = "DELETE FROM purchases  WHERE users_id =? AND purchases.id= ? ";

    private static final String FIND_PREPARATIONS_IN_ORDERS = "SELECT  preparations.id, title, price, amount, description, image, active, purchases.quantity , categories.type, conditions.conditions_status  FROM preparations  JOIN categories ON preparations.categories_id = categories.id JOIN purchases ON purchases.preparations_id = preparations.id AND orders_id = ?";


    @Override

    public List<Orders> findAll() throws DaoException {
        return null;
    }

    @Override
    public List<Orders> findOrdersByUserId(long userId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BY_USERS_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Orders> order = new ArrayList<>();
            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getLong(ORDERS_ID));
                orders.setCreated(LocalDateTime.parse(resultSet.getString(ORDERS_CREATED), FORMATTER));
                orders.setAmount(resultSet.getBigDecimal(ORDERS_AMOUNT));
                orders.setDose(resultSet.getBigDecimal(ORDERS_DOSE));
                orders.setCost(resultSet.getBigDecimal(ORDERS_COST));
                orders.setStatus(Orders.OrdersStatus.valueOf(resultSet.getString(ORDERS_ORDERS_STATUS_ID).toUpperCase()));
                orders.setUserId(resultSet.getLong(ORDERS_USER_ID));
                order.add(orders);
            }
            logger.log(Level.DEBUG, !order.isEmpty() ? order.size() + " orders were found with user id " + userId
                    : "There weren't any orders with user id " + userId);
            return order;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find orders with user id " + userId + " into database. Database access error:", e);
            throw new DaoException("Impossible to find orders with user id " + userId + " into database. Database access error:", e);
        }
    }


    @Override
    public Optional<Orders.OrdersStatus> findOrdersByStatus(long ordersId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_STATUS)) {
            statement.setLong(1, ordersId);
            ResultSet resultSet = statement.executeQuery();
            Optional<Orders.OrdersStatus> optionalOrdersStatus = Optional.empty();
            if (resultSet.next()) {
                Orders.OrdersStatus status = Orders.OrdersStatus.valueOf(resultSet.getString(ORDERS_STATUS).toUpperCase());
                optionalOrdersStatus = Optional.of(status);
            }
            logger.log(Level.DEBUG, optionalOrdersStatus.map(status -> "Order with id " + ordersId + " has status " + status)
                    .orElseGet(() -> "Order with id " + ordersId + " doesn't have any status"));
            return optionalOrdersStatus;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find orders by status. Database access error:", e);
            throw new DaoException("Impossible to find orders by status. Database access error:", e);
        }
    }


    @Override
    public boolean updateOrdersStatus(long ordersId, Orders.OrdersStatus status) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDERS_STATUS)) {
            statement.setLong(1, status.ordinal() + 1);
            statement.setLong(2, ordersId);
            boolean result = statement.executeUpdate() == 1;
            logger.log(Level.INFO, result ? "Order status for order with id " + ordersId + " was changed to " + status
                    : "Order status for order with id " + ordersId + " wasn't changed to " + status);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to change order status for order with id " + ordersId + " into database. Database access error:", e);
            throw new DaoException("Impossible to change order status for order with id " + ordersId + " into database. Database access error:", e);
        }
    }


    @Override
    public long insertNewOrders(Orders orders) throws DaoException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false); // не позволит  сгруппировать несколько последующих операторов в рамках одной и той же транзакции
            try (PreparedStatement ordersStatement = connection.prepareStatement(INSERT_NEW_ORDERS, RETURN_GENERATED_KEYS);
                 PreparedStatement preparationStatement = connection.prepareStatement(INSERT_PREPARATION_IN_ORDERS);
                 PreparedStatement purchasesStatement = connection.prepareStatement(DELETE_PREPARATION_FROM_ORDERS)) {
                ordersStatement.setTimestamp(1, Timestamp.valueOf(orders.getCreated()));
                ordersStatement.setBigDecimal(2, orders.getAmount());
                ordersStatement.setBigDecimal(3, orders.getDose());
                ordersStatement.setBigDecimal(4, orders.getCost());
                ordersStatement.setLong(5, orders.getStatus().ordinal() + 1);
                ordersStatement.setLong(6, orders.getUserId());
                ordersStatement.executeUpdate();
                ResultSet resultSet = ordersStatement.getGeneratedKeys();
                long ordersId = 0;
                if (resultSet.next()) {
                    ordersId = resultSet.getLong(1);
                    for (Map.Entry<Preparation, Integer> entry : orders.getPreparations().entrySet()) {
                        Preparation preparation = entry.getKey();
                        int quantity = entry.getValue();
                        preparationStatement.setLong(1, ordersId);
                        preparationStatement.setLong(2, preparation.getId());
                        preparationStatement.setLong(3, quantity);
                        preparationStatement.addBatch();
                    }
                    preparationStatement.executeBatch();
                    for (Preparation preparation : orders.getPreparations().keySet()) {
                        purchasesStatement.setLong(1, orders.getUserId());
                        purchasesStatement.setLong(2, preparation.getId());
                        purchasesStatement.addBatch();
                    }
                    purchasesStatement.executeBatch();
                }
                connection.commit();
                logger.log(Level.INFO, "insertNewOrders method was completed successfully. Order with id " + ordersId + " was added");
                return ordersId;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                logger.log(Level.ERROR, "Change cancellation error in the current transaction:", throwables);
            }
            logger.log(Level.ERROR, "Impossible to insert orders into database. Database access error:", e);
            throw new DaoException("Impossible to insert orders into database. Database access error:", e);
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException throwables) {
                logger.log(Level.ERROR, "Database access error occurs:", throwables);
            }
        }
    }

    @Override
    public Map<Preparation, Integer> findPreparationsForOrders(long orderId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PREPARATIONS_IN_ORDERS)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            Map<Preparation, Integer> preparations = new LinkedHashMap<>();
            while (resultSet.next()) {
                Preparation preparation = PreparationCreator.createPreparation(resultSet);
                Integer quantity = resultSet.getInt(ORDERS_QUANTITY);
                preparations.put(preparation, quantity);
            }
            logger.log(Level.DEBUG, !preparations.isEmpty() ? preparations.size() + " type of preparation were found for order with id " + orderId
                    : "There weren't any preparation for order with id " + orderId);
            return preparations;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find preparation for order with id " + orderId + " into database. Database access error:", e);
            throw new DaoException("Impossible to find preparation for order with id " + orderId + " into database. Database access error:", e);
        }
    }



}
