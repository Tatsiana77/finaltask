package by.edu.sarnatskaya.pharmacy.model.service;

import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Category;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.model.entity.User;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PreparationService {
    Optional<Preparation> findPreparationById(long preparationId) throws ServiceException;

    List<Preparation> findPreparationByCategory(Category type) throws ServiceException;

    List<Preparation> findPreparationByCondition(Preparation.Condition condition) throws ServiceException;

    List<Category> findAllCategories() throws ServiceException, SQLException;

    List<Preparation> searchPreparation(String value) throws ServiceException;

    long insertNewPreparation(Preparation preparation, InputStream image) throws ServiceException;

    boolean updatePreparationTitle(long preparationId, String title)throws  ServiceException;

    boolean updatePreparationPrice(long preparationId, BigDecimal price)throws  ServiceException;

    boolean updatePreparationAmount(long preparationId, BigDecimal amount)throws  ServiceException;

    boolean updatePreparationPicture(long preparationId, InputStream image)throws  ServiceException;

    boolean updatePreparationDescription(long preparationId, String description)throws ServiceException;

    boolean updatePreparation(String  preparationId, String purchasesId)throws  ServiceException;

    boolean isCategoriesExist(String categoryType)throws ServiceException;

    boolean isPreparationExist(String title)throws ServiceException;


    boolean deletePreparation(String  preparationId, String purchasesId)throws ServiceException;

    List<Preparation> findAll() throws ServiceException;

    boolean isPreparationStatusById(String preparationId, Preparation.Condition free, User.Role role)throws  ServiceException;
}
