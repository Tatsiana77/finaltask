package by.edu.sarnatskaya.pharmacy.model.service;

import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.model.entity.User;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean  registerUser(Map<String, String> userData) throws ServiceException;
    Optional<User> findUserById(long userId) throws ServiceException;
    Optional<User> findUserByLoginAndPassword(String login, String password) throws ServiceException;
    List<User> findAllUsers()throws  ServiceException;


    boolean createNewAccount(Map<String, String> userData) throws ServiceException;
    boolean makeAdmin(String email) throws ServiceException;
    boolean updateUserStatusesById(User.Status status, String[] userIdArray) throws ServiceException;
    boolean updateNameById(long userId, String name)throws  ServiceException;
    boolean updateSurnameById(long userId, String surname) throws ServiceException;
    boolean updateEmailById( long userId,  Map<String, String>  emailResult) throws ServiceException;
    boolean updatePhoneById( long userId,  Map<String, String>  phoneResult) throws ServiceException;

    boolean changePassword(long id, Map<String, String> passwordData) throws ServiceException;


    Map<Preparation, Integer> findPreparationInPurchasesByUserId(long userId) throws ServiceException;
}
