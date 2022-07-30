package by.edu.sarnatskaya.pharmacy.model.dao;

import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;


import java.util.*;

public interface OrdersDao extends BaseDao<Orders> {

    List<Orders> findOrdersByUserId(long userId) throws DaoException;

    Optional<Orders.OrdersStatus> findOrdersByStatus(long ordersId) throws DaoException;

    boolean updateOrdersStatus(long ordersId, Orders.OrdersStatus status) throws DaoException;

    long insertNewOrders(Orders orders) throws DaoException;

    Map<Preparation, Integer> findPreparationsForOrders(long orderId) throws DaoException;


}
