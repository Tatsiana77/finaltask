package by.edu.sarnatskaya.pharmacy.model.dao;

import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.User;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {

    boolean isLoginExist(String login) throws DaoException;

    boolean isEmailExist(String email) throws DaoException;

    boolean isPhoneExist(String phoneNumber) throws DaoException;

    boolean isUserExist(long userId, String password) throws DaoException;

    boolean updateUserName(long userId, String name) throws DaoException;

    boolean updateUserSurname(long userId, String surname) throws DaoException;

    boolean updateUserPassword(long userId, String password) throws DaoException;

    boolean updateUserEmail(long userId, String email) throws DaoException;

    boolean updateUserPhone(long userId, String phone) throws DaoException;

    boolean updateUserStatusById(User.Status status, Long userId) throws DaoException;

    boolean createAdmin(String email) throws DaoException;

    long insertNewUser(User user, String password) throws DaoException;

    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;

    Optional<User> findUserById(long id) throws DaoException;

    User.Status findUserStatusById(long id) throws DaoException;

    List<Orders> findOrdersByStatuses(EnumSet<Orders.OrdersStatus> statuses);


}
