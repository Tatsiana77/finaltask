package by.edu.sarnatskaya.pharmacy.model.dao.impl;

import by.edu.sarnatskaya.pharmacy.model.entity.Category;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.connection.ConnectionPool;
import by.edu.sarnatskaya.pharmacy.model.dao.PreparationDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class PreparationDaoImpl implements PreparationDao {

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String FIND_PREPARATION_BY_ID = "SELECT  preparations.id, title, price, amount, description, image, active, conditions.condition_status, categories.type FROM preparations" + "JOIN categories ON categories_id =categories.id" + "JOIN conditions ON condition_id = conditions.id" + "WHERE preparations.id = ?";

    private static final String FIND_ALL_PREPARATION = "SELECT  preparations.id, title, price, " + "           amount, description, image, active, conditions.condition_status, categories.type  FROM preparations" + "        JOIN categories ON categories_id =categories.id" + "         JOIN conditions ON condition_id = conditions.id";

    private static final String FIND_PREPARATION_BY_CATEGORY = "SELECT  preparations.id, title,price, amount, description, image, active, categories.type  FROM preparations   " + "JOIN categories ON preparations.categories_id =categories.id WHERE active = 1 AND categories.type =?";

    private static final String FIND_PREPARATION_BY_CONDITION = "SELECT  preparations.id, title, price, amount, description, image, active, conditions.condions_status FROM preparation " + "JOIN conditions ON preparation.condition_id= conditions.id";

    private static final String FIND_PREPARATION_STATUS_BY_ID = "SELECT active FROM preparations WHERE id =?";

    private static final String SEARCH_PREPARATIONS_BY_TITLE = "SELECT preparations.id, title, price, amount, description, image, active, categories.type  FROM  preparations" + "JOIN  categories ON   preparations.categories_id =categories.id  WHERE active = 1 AND title LIKE CONCAT( '%',?,'%') ";

    private static final String FIND_ALL_CATEGORIES = " SELECT  id, type FROM categories";

    private static final String FIND_CATEGORY_EXIST = "SELECT  id FROM  categories WHERE type = ?";

    private static final String FIND_PREPARATION_BY_TITLE = "SELECT preparations.id FROM preparations WHERE title= ?";

    private static final String INSERT_NEW_PREPARATIONS = "INSERT INTO preparations (title, price, amount, description, image, active, conditions_id, categories_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_PREPARATION_TITLE = "UPDATE preparations SET title = ? WHERE id =?";

    private static final String UPDATE_PREPARATION_PRICE = "UPDATE preparations SET price = ? WHERE id =?";

    private static final String UPDATE_PREPARATION_AMOUNT = "UPDATE preparations SET amount = ? WHERE id =?";

    private static final String UPDATE_PREPARATION_DESCRIPTION = "UPDATE preparations SET  description = ? WHERE id =?";

    private static final String UPDATE_PREPARATION_PICTURE = "UPDATE preparations SET image = ? WHERE id =?";

    private static final String UPDATE_PREPARATION = "UPDATE preparations SET title =?, price=? , amount=?, description =? , image =?, active=?," +
            " condition_id =(SELECT  id FROM conditions WHERE conditions_status =? ), categories_id =(SELECT id FROM categories WHERE type =?)";

    private static final String DELETE_PREPARATION_BY_ID = "DELETE FROM preparations WHERE preparations.id =?";

    private static final String FIND_PREPARATION_IN_ANY_ORDER = "SELECT  id  FROM order WHERE preparations_id=? LIMIT 1";

    @Override
    public Optional<Preparation> findPreparationById(long preparationId) throws DaoException {
        Optional<Preparation> preparationOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PREPARATION_BY_ID)) {
            statement.setLong(1, preparationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Preparation preparation = PreparationCreator.createPreparation(resultSet);
                    preparationOptional = Optional.of(preparation);
                }
                logger.log(Level.DEBUG, "findPreparationById method was completed successfully." + ((preparationOptional.isPresent()) ? " Preparation with id " + preparationId + " was found" : " User with id " + preparationId + " don't exist"));
                return preparationOptional;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find preparation by id. Database access error:", e);
            throw new DaoException("Impossible to find preparation by id. Database access error:", e);
        }
    }


    @Override
    public List<Preparation> findPreparationByCategory(Category type) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PREPARATION_BY_CATEGORY)) {
            statement.setString(1, type.toString());
            ResultSet resultSet = statement.executeQuery();
            List<Preparation> preparations = new ArrayList<>();
            if (resultSet.next()) {
                Preparation preparation = PreparationCreator.createPreparation(resultSet);
                preparations.add(preparation);
            }
            logger.log(Level.DEBUG, "Preparation condition is  " + preparations);
            return preparations;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find preparation by category from database. Database access error:", e);
            throw new DaoException("Impossible to find preparation by category from database. Database access error:", e);
        }
    }

    @Override
    public List<Preparation> findPreparationByCondition(Preparation.Condition condition) throws DaoException {
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(FIND_PREPARATION_BY_CONDITION)) {
            statement.setString(1, condition.toString());
            ResultSet resultSet = statement.executeQuery();
            List<Preparation> preparations = new ArrayList<>();
            if (resultSet.next()) {
                Preparation preparation = PreparationCreator.createPreparation(resultSet);
                preparations.add(preparation);
            }
            logger.log(Level.DEBUG, "Preparations condition is  " + preparations);
            return preparations;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find preparations by condition from database. Database access error:", e);
            throw new DaoException("Impossible to find preparations by condition   from database. Database access error:", e);
        }
    }

    @Override
    public List<Category> findAllCategories() throws DaoException{
        try (Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_CATEGORIES);
            List<Category> categories = new ArrayList<>();
            if (resultSet.next()) {
                Category category = new Category(resultSet.getInt(CATEGORIES_ID), resultSet.getString(CATEGORIES_TYPE));
                categories.add(category);
            }
            logger.info("findAllCategories method was completed successfully. " + categories.size() + " All categories types were found");
            return categories;
        } catch (SQLException e) {
            logger.error("Impossible to find category types. Database error:", e);
            throw new DaoException("Impossible to find category types. Database error:", e);
        }
    }

    @Override
    public List<Preparation> searchPreparation(String value) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_PREPARATIONS_BY_TITLE)) {
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            List<Preparation> preparations = new ArrayList<>();
            if (resultSet.next()) {
                Preparation preparation = PreparationCreator.createPreparation(resultSet);
                preparations.add(preparation);
            }
            logger.log(Level.DEBUG, "");
            return preparations;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "");
            throw new DaoException("");
        }
    }

    @Override
    public long insertNewPreparation(Preparation preparation, InputStream image) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_PREPARATIONS, RETURN_GENERATED_KEYS)) {
            statement.setString(1, preparation.getTitle());
            statement.setBigDecimal(2, preparation.getPrice());
            statement.setBigDecimal(3, preparation.getAmount());
            statement.setString(4, preparation.getDescription());
            statement.setBlob(5, image);
            statement.setBoolean(6, preparation.isActive());
            statement.setString(7, String.valueOf(preparation.getCondition().ordinal() + 1));
            statement.setString(8, preparation.getCategory().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            long preparationId = 0;
            if (resultSet.next()) {
                preparationId = resultSet.getLong(1);
                logger.log(Level.INFO, "InsertNewPreparation method was completed successfully. Preparation with id " + preparationId + " was added");
            }
            return preparationId;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to insert preparation into database. Database access error:", e);
            throw new DaoException("Impossible to insert preparation into database. Database access error:", e);
        }
    }


    @Override
    public boolean updatePreparationTitle(long preparationId, String title) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PREPARATION_TITLE)) {
            statement.setString(1, title);
            statement.setLong(2, preparationId);
            boolean result = statement.executeUpdate() == 1;
            logger.log(Level.DEBUG, "Result of preparations  title  update for user with id " + preparationId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to update preparations  title. Database access error:", e);
            throw new DaoException("Impossible to update preparations  title. Database access error:", e);
        }
    }

    @Override
    public boolean updatePreparationPrice(long preparationId, BigDecimal price) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PREPARATION_PRICE)) {
            statement.setBigDecimal(1, price);
            statement.setLong(2, preparationId);
            boolean result = statement.executeUpdate() == 1;
            logger.log(Level.DEBUG, "Result of preparations  price  update for user with id " + preparationId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to update preparations  price. Database access error:", e);
            throw new DaoException("Impossible to update preparations  price. Database access error:", e);
        }
    }


    @Override
    public boolean updatePreparationAmount(long preparationId, BigDecimal amount) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PREPARATION_AMOUNT)) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, preparationId);
            boolean result = statement.executeUpdate() == 1;
            logger.log(Level.DEBUG, "Result of preparations  amount  update for user with id " + preparationId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to update preparations  amount. Database access error:", e);
            throw new DaoException("Impossible to update preparations  amount. Database access error:", e);
        }
    }

    @Override
    public boolean updatePreparationPicture(long preparationId, InputStream image) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PREPARATION_PICTURE)) {
            statement.setBlob(1, image);
            statement.setLong(2, preparationId);
            boolean result = statement.executeUpdate() == 1;
            logger.log(Level.DEBUG, "Result of preparations  image update for user with id " + preparationId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to update preparations  image. Database access error:", e);
            throw new DaoException("Impossible to update preparations  image. Database access error:", e);
        }
    }

    @Override
    public boolean updatePreparationDescription(long preparationId, String description) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PREPARATION_DESCRIPTION)) {
            statement.setString(1, description);
            statement.setLong(2, preparationId);
            boolean result = statement.executeUpdate() == 1;
            logger.log(Level.DEBUG, "Result of preparations  description update for user with id " + preparationId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to update preparations  description. Database access error:", e);
            throw new DaoException("Impossible to update preparations  description. Database access error:", e);
        }
    }

    @Override
    public boolean updatePreparation(Preparation preparation, InputStream image) throws DaoException {
        boolean result;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PREPARATION)) {
            statement.setString(1, preparation.getTitle());
            statement.setBigDecimal(2, preparation.getPrice());
            statement.setBigDecimal(3, preparation.getAmount());
            statement.setString(4, preparation.getDescription());
            statement.setBlob(5, image);
            statement.setString(6, String.valueOf(preparation.getCondition().ordinal() + 1));
            statement.setString(7, preparation.getCategory().toString());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Impossible to update preparation into database. Database error:", e);
            throw new DaoException("Impossible to  to update preparation  into database. Database error:", e);
        }
        return result;
    }

    @Override
    public boolean isCategoriesExist(String categoryType) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_CATEGORY_EXIST)) {
            statement.setString(1, categoryType);
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.isBeforeFirst();
            logger.debug("isCategoriesExist  method was completed successfully. Result: " + result);
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to check existence of category type. Database access error:", e);
            throw new DaoException("Impossible to check existence of category type. Database access error:", e);
        }
    }

    @Override
    public boolean isPreparationExist(String title) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PREPARATION_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.isBeforeFirst();
            logger.debug("isPreparationExist  method was completed successfully. Result: " + result);
            return result;
        } catch (SQLException e) {
            logger.error("Impossible to check existence of preparation . Database access error:", e);
            throw new DaoException("Impossible to check existence of preparation. Database access error:", e);
        }
    }

    @Override
    public boolean isPreparationStatusById(long preparationId) throws DaoException {
        try (Connection connection = connectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(FIND_PREPARATION_STATUS_BY_ID)) {
            statement.setLong(1, preparationId);
            ResultSet resultSet = statement.executeQuery();
            boolean result = false;
            if (resultSet.next()) {
                result = resultSet.getBoolean(PREPARATIONS_ACTIVE);
            }
            logger.log(Level.DEBUG, "isPreparationStatusById  method was completed successfully. Result: " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to check availability of status. Database access error:", e);
            throw new DaoException("Impossible to check availability of  status. Database access error:", e);
        }
    }


    @Override
    public boolean deletePreparation(List<Long> idList) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PREPARATION_BY_ID);
             PreparedStatement checkStatement = connection.prepareStatement(FIND_PREPARATION_IN_ANY_ORDER)) {
            for (long preparationId : idList) {
                checkStatement.setLong(1, preparationId);
                ResultSet resultSet = checkStatement.executeQuery();
                if (!resultSet.isBeforeFirst()) {
                    statement.setLong(1, preparationId);
                    statement.addBatch();
                }
            }
            boolean result = statement.executeBatch().length > 0;
            logger.log(Level.INFO, result ? "deletePrepationById method was completed successfully. Users with id " + idList + " were deleted"
                    : "Preparation with id " + idList + " weren't deleted");
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to delete preparation from database. Database access error:", e);
            throw new DaoException("Impossible to delete preparation from database. Database access error:", e);
        }
    }

    @Override
    public List<Preparation> findAll() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_PREPARATION)) {
            List<Preparation> preparations = new ArrayList<>();
            while (resultSet.next()) {
                Preparation preparation = PreparationCreator.createPreparation(resultSet);
                preparations.add(preparation);
            }
            logger.log(Level.DEBUG, "findAllPreparations method was completed successfully. " + preparations.size() + " were found");
            return preparations;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find preparations. Database access error:", e);
            throw new DaoException("Impossible to find preparations. Database access error:", e);
        }
    }
}
