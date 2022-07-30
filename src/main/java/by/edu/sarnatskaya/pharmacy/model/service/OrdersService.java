package by.edu.sarnatskaya.pharmacy.model.service;

import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.entity.User;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrdersService {

    List<Orders> findOrdersByUserId(long userId) throws ServiceException;

    Optional<Orders.OrdersStatus> findOrdersByStatus(long ordersId) throws ServiceException;

    boolean updateOrdersStatus(long ordersId, Orders.OrdersStatus status) throws ServiceException;

    long insertNewOrders(Orders orders) throws ServiceException;

    Map<Preparation, Integer> findPreparationsForOrders(long orderId) throws ServiceException;

    boolean updateOrdersStatus(String ordersId, Orders.OrdersStatus refuse, User.Role role);

    List<Orders> findOrdersByStatuses(EnumSet<Orders.OrdersStatus> statuses);
}
