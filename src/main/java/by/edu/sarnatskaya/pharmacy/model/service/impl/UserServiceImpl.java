package by.edu.sarnatskaya.pharmacy.model.service.impl;

import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.dao.UserDao;
import by.edu.sarnatskaya.pharmacy.model.dao.DaoProvider;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.UserService;
import by.edu.sarnatskaya.pharmacy.util.PasswordEncoder;
import by.edu.sarnatskaya.pharmacy.util.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Map;
import java.util.Optional;



public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserDao userDao = DaoProvider.getInstance().getUserDao();


    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        return false;
    }

    @Override
    public Optional<User> findUserById(long userId) throws ServiceException {
        Optional<User> userOptional;
        try {
            userOptional = userDao.findUserById(userId);
            return userOptional;

        } catch (DaoException e) {
            logger.log(Level.ERROR, "Impossible to find user by id ", e);
            throw new ServiceException("Impossible to find user by id ", e);
        }
    }



    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            if (UserValidator.isLoginValid(login) && UserValidator.isPasswordValid(password)) {
                String passwordHash = PasswordEncoder.passwordEncoding(password);
                optionalUser = userDao.findUserByLoginAndPassword(login, passwordHash);
            }
            return optionalUser;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Impossible to find user by login and password:", e);
            throw new ServiceException("Impossible to find user by login and password:", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        return null;
    }


    @Override
    public boolean createNewAccount(Map<String, String> userData) throws ServiceException {
        return false;
    }



    @Override
    public boolean makeAdmin(String email) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateUserStatusesById(User.Status status, String[] userIdArray) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateNameById(long userId, String name) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateSurnameById(long userId, String surname) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateEmailById(long userId, Map<String, String> emailResult) throws ServiceException {
        return false;
    }

    @Override
    public boolean updatePhoneById(long userId, Map<String, String> phoneResult) throws ServiceException {
        return false;
    }

    @Override
    public boolean changePassword(long id, Map<String, String> passwordData) throws ServiceException {
        return false;
    }

    @Override
    public Map<Preparation, Integer> findPreparationInPurchasesByUserId(long userId) throws ServiceException {
        return null;
    }

}

