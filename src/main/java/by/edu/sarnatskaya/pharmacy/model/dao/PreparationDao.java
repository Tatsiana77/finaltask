package by.edu.sarnatskaya.pharmacy.model.dao;

import by.edu.sarnatskaya.pharmacy.model.entity.Category;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;


import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PreparationDao extends BaseDao<Preparation> {

    Optional<Preparation> findPreparationById(long preparationId) throws DaoException;

    List<Preparation> findPreparationByCategory(Category type) throws DaoException;

    List<Preparation> findPreparationByCondition(Preparation.Condition condition) throws DaoException;

    List<Category> findAllCategories() throws DaoException, SQLException;

    List<Preparation> searchPreparation(String value) throws DaoException;

    long insertNewPreparation(Preparation preparation, InputStream image) throws DaoException;

    boolean updatePreparationTitle(long preparationId, String title)throws  DaoException;

    boolean updatePreparationPrice(long preparationId, BigDecimal price)throws  DaoException;

    boolean updatePreparationAmount(long preparationId, BigDecimal amount)throws  DaoException;

    boolean updatePreparationPicture(long preparationId, InputStream image)throws  DaoException;

    boolean updatePreparationDescription(long preparationId, String description)throws DaoException;

    boolean updatePreparation(Preparation preparation, InputStream image)throws  DaoException;

    boolean isCategoriesExist(String categoryType)throws DaoException;

    boolean isPreparationExist(String title)throws DaoException;

    boolean isPreparationStatusById(long preparationId) throws DaoException;

    boolean deletePreparation(List<Long> idList)throws DaoException;


}
