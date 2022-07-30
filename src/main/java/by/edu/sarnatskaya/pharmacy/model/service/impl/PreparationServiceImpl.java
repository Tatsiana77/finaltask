package by.edu.sarnatskaya.pharmacy.model.service.impl;

import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Category;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.dao.PreparationDao;
import by.edu.sarnatskaya.pharmacy.model.dao.DaoProvider;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.PreparationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PreparationServiceImpl  implements PreparationService {
    private static final Logger logger = LogManager.getLogger();
    private static final PreparationDao preparationDao= DaoProvider.getInstance().getPreparationsDao();

    @Override
    public Optional<Preparation> findPreparationById(long preparationId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<Preparation> findPreparationByCategory(Category type) throws ServiceException {
        return null;
    }

    @Override
    public List<Preparation> findPreparationByCondition(Preparation.Condition condition) throws ServiceException {
        return null;
    }

    @Override
    public List<Category> findAllCategories() throws ServiceException, SQLException {
        return null;
    }

    @Override
    public List<Preparation> searchPreparation(String value) throws ServiceException {
        return null;
    }

    @Override
    public long insertNewPreparation(Preparation preparation, InputStream image) throws ServiceException {
        return 0;
    }

    @Override
    public boolean updatePreparationTitle(long preparationId, String title) throws ServiceException {
        return false;
    }

    @Override
    public boolean updatePreparationPrice(long preparationId, BigDecimal price) throws ServiceException {
        return false;
    }

    @Override
    public boolean updatePreparationAmount(long preparationId, BigDecimal amount) throws ServiceException {
        return false;
    }

    @Override
    public boolean updatePreparationPicture(long preparationId, InputStream image) throws ServiceException {
        return false;
    }

    @Override
    public boolean updatePreparationDescription(long preparationId, String description) throws ServiceException {
        return false;
    }

    @Override
    public boolean updatePreparation(String preparationId, String purchasesId) throws ServiceException {
        return false;
    }

    @Override
    public boolean isCategoriesExist(String categoryType) throws ServiceException {
        return false;
    }

    @Override
    public boolean isPreparationExist(String title) throws ServiceException {
        return false;
    }


    @Override
    public boolean deletePreparation(String preparationId, String purchasesId) throws ServiceException {
        return false;
    }

    @Override
    public List<Preparation> findAll() {
        return null;
    }

    @Override
    public boolean isPreparationStatusById(String preparationId, Preparation.Condition free, User.Role role) throws ServiceException {
        return false;
    }
}
