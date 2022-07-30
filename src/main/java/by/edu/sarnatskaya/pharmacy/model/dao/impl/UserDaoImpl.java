package by.edu.sarnatskaya.pharmacy.model.dao.impl;

import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.connection.ConnectionPool;
import by.edu.sarnatskaya.pharmacy.model.dao.UserDao;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String FIND_USER_BY_ID = "SELECT  user.id, name, surname, login + " +
            ", password, email, phone, role, status FROM users" +
            " JOIN roles ON roles_id=roles.id" +
            " JOIN status ON status_id=status.id" +
            " WHERE users.id=?";
    private static final String FIND_USER_BY_LOGIN = "SELECT  id FROM users WHERE login = ?";

    private static final String FIND_USER_BY_EMAIL = "SELECT id FROM users WHERE email = ?";

    private static final String FIND_USER_BY_PHONE = "SELECT id FROM users WHERE phone =?";

    private static final String FIND_USER_BY_ID_AND_PASSWORD = "SELECT  id FROM users WHERE id=? AND password = ?";

    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT  users.id, name, surname, login, password, email" +
            "phone, status, role FROM users" +
            "JOIN roles ON roles_id = roles.id" +
            "JOIN status ON status_id = status.id" +
            "WHERE login = ? AND password = ? ";
    private static final String FIND_ALL_USERS = "SELECT  users.id, name, surname, login + " +
            ", password, email, phone, role, status FROM users" +
            " JOIN roles ON roles_id=roles.id" +
            " JOIN status ON status_id=status.id";
    private static final String FIND_USER_STATUS_BY_ID = "SELECT status FROM users " +
            "JOIN status ON status.id =status_id WHERE users.id=?";

    private static final String INSERT_NEW_USER = "INSERT INTO users(name, surname, login, " +
            "password, email, phone,status_id, role_id) +" +
            "VALUES (?,?,?,?,?,?,?,?)";

    private static final String UPDATE_USER_STATUS_BY_USER_ID = "UPDATE users SET status_id=? WHERE id=?";

    private static final String UPDATE_USER_NAME = "UPDATE users SET name=? WHERE id=?";

    private static final String UPDATE_USER_SURNAME = "UPDATE users SET surname=? WHERE id=?";

    private static final String UPDATE_USER_PHONE = "UPDATE users SET phone=? WHERE id=?";

    private static final String UPDATE_USER_EMAIL = "UPDATE users SET email =? WHERE id=?";

    private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password=? WHERE id=?";

    private static final String UPDATE_USER_ROLE_BY_EMAIL = "UPDATE users SET roles=? WHERE email=?";



    @Override
    public boolean isLoginExist(String login) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.isBeforeFirst();
            logger.log(Level.DEBUG, "isLoginExist method was completed successfully. Result: " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to check existence of user login. Database access error:", e);
            throw new DaoException("Impossible to check existence of user login. Database access error:", e);
        }
    }

    @Override
    public boolean isEmailExist(String email) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.isBeforeFirst();
            logger.log(Level.DEBUG, "isEmailExist method was completed successfully. Result: " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to check existence of user email. Database access error:", e);
            throw new DaoException("Impossible to check existence of user email. Database access error:", e);
        }
    }

    @Override
    public boolean isPhoneExist(String phoneNumber) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_PHONE)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.isBeforeFirst();
            logger.log(Level.DEBUG, "isPhoneExist method was completed successfully. Result: " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to check existence of user phone number. Database access error:", e);
            throw new DaoException("Impossible to check existence of user phone number. Database access error:", e);
        }
    }

    @Override
    public boolean isUserExist(long userId, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_AND_PASSWORD)) {
            statement.setLong(1, userId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.isBeforeFirst();
            logger.log(Level.DEBUG, "isUserExist method was completed successfully. Result: " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to check existence of user. Database access error:", e);
            throw new DaoException("Impossible to check existence of user. Database access error:", e);
        }
    }

    @Override
    public boolean updateUserName(long userId, String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_NAME)) {
            statement.setString(1, name);
            statement.setLong(2, userId);
            boolean result = statement.executeUpdate() == 1;
            logger.debug("Result of user first name update for user with id " + userId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to update user first name. Database access error:", e);
            throw new DaoException("Impossible to update user first name. Database access error:", e);
        }
    }


    @Override
    public boolean updateUserSurname(long userId, String surname) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SURNAME)) {
            statement.setString(1, surname);
            statement.setLong(2, userId);
            boolean result = statement.executeUpdate() == 1;
            logger.debug("Result of user first surname update for user with id " + userId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to update user  surname. Database access error:", e);
            throw new DaoException("Impossible to update user surname. Database access error:", e);
        }
    }

    @Override
    public boolean updateUserPassword(long userId, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
            statement.setString(1, password);
            statement.setLong(2, userId);
            boolean result = statement.executeUpdate() == 1;
            logger.debug("Result of user first passwordupdate for user with id " + userId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to update user  password. Database access error:", e);
            throw new DaoException("Impossible to update user password. Database access error:", e);
        }
    }

    @Override
    public boolean updateUserEmail(long userId, String email) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_EMAIL)) {
            statement.setString(1, email);
            statement.setLong(2, userId);
            boolean result = statement.executeUpdate() == 1;
            logger.debug("Result of user email update  for user with id " + userId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to update user  email. Database access error:", e);
            throw new DaoException("Impossible to update user email. Database access error:", e);
        }
    }

    @Override
    public boolean updateUserPhone(long userId, String phone) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PHONE)) {
            statement.setString(1, phone);
            statement.setLong(2, userId);
            boolean result = statement.executeUpdate() == 1;
            logger.debug("Result of user  phone number update for user with id " + userId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to update user  phone number. Database access error:", e);
            throw new DaoException("Impossible to update user phone number. Database access error:", e);
        }
    }

    @Override
    public boolean updateUserStatusById(User.Status status, Long userId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS_BY_USER_ID)) {
            statement.setString(1, status.name());
            statement.setLong(2, userId);
            boolean result = statement.executeUpdate() == 1;
            logger.info("updateUserStatusById method was completed successfully. User status with user id  "
                    + userId + " were updated to " + status + " status");
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to insert new user into database. Database access error:", e);
            throw new DaoException("Impossible to insert new user into database. Database access error:", e);
        }
    }

    @Override
    public boolean createAdmin(String email) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_ROLE_BY_EMAIL)) {
            statement.setString(1, "admin");
            statement.setString(2, "email");
            boolean result = statement.executeUpdate() == 1;
            logger.info("makeAdmin method was completed successfully. User role with user email  "
                    + email + " were updated to admin");
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to update user role. Database access error:", e);
            throw new DaoException("Impossible to update user role. Database access error:", e);
        }
    }


    @Override
    public long insertNewUser(User user, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_USER, RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, password);
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getStatus().name().toLowerCase());
            statement.setString(8, user.getRole().name().toLowerCase());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long userId = 0;
                if (resultSet.next()) {
                    userId = resultSet.getLong(1);
                    logger.info("insertNewUser  method was completed successfully. User with id " + userId + " was added");
                }
                return userId;
            }

        } catch (SQLException e) {
            logger.error("Impossible to insert new user into database. Database access error:", e);
            throw new DaoException("Impossible to insert new user into database. Database access error:", e);
        }
    }


    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = UserCreator.createUser(resultSet);
                    userOptional = Optional.of(user);

                }
            }
            logger.debug("findUserByLoginAndPassword method was completed successfully." +
                    (userOptional.map(user -> " User with id " + user.getId() + " was found")
                            .orElse(" User with these email and password don't exist")));
            return userOptional;

        } catch (SQLException e) {
            logger.error("Impossible to find user in database.", e);
            throw new DaoException("Impossible to find user in database. ", e);
        }
    }

    @Override
    public Optional<User> findUserById(long id) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = UserCreator.createUser(resultSet);
                    userOptional = Optional.of(user);
                }
                logger.log(Level.DEBUG, "findUserById method was completed successfully."
                        + ((userOptional.isPresent()) ? " User with id " + id + " was found" : " User with id " + id + " don't exist"));
                return userOptional;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find user by id. Database access error:", e);
            throw new DaoException("Impossible to find user by id. Database access error:", e);
        }
    }


    @Override
    public User.Status findUserStatusById(long id) throws DaoException {

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_STATUS_BY_ID)) {
            statement.setString(1, "id");
            ResultSet resultSet = statement.executeQuery();
            User.Status status = null;
            if (resultSet.next()) {
                status = User.Status.valueOf(resultSet.getString(USER_STATUS));
            }
            logger.log(Level.DEBUG, status != null ? "User status is " + status : "User doesnt have any status");
            return status;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find user status by user id from database. Database access error:", e);
            throw new DaoException("Impossible to find user status by user id from database. Database access error:", e);
        }
    }

    @Override
    public List<Orders> findOrdersByStatuses(EnumSet<Orders.OrdersStatus> statuses) {
        return null;
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = UserCreator.createUser(resultSet);
                users.add(user);
            }
            logger.log(Level.DEBUG, "findAllUsers method was completed successfully. " + users.size() + " were found");
            return users;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find users. Database access error:", e);
            throw new DaoException("Impossible to find users. Database access error:", e);
        }
    }
}
