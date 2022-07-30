package by.edu.sarnatskaya.pharmacy.model.service.impl;

import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.dao.OrdersDao;
import by.edu.sarnatskaya.pharmacy.model.dao.DaoProvider;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.OrdersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrdersServiceImpl implements OrdersService {
    private static final Logger logger = LogManager.getLogger();
    private static final OrdersDao ordersDao= DaoProvider.getInstance().getOrdersDao();


    @Override
    public List<Orders> findOrdersByUserId(long userId) throws ServiceException {
        return null;
    }

    @Override
    public Optional<Orders.OrdersStatus> findOrdersByStatus(long ordersId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean updateOrdersStatus(long ordersId, Orders.OrdersStatus status) throws ServiceException {
        return false;
    }

    @Override
    public long insertNewOrders(Orders orders) throws ServiceException {
        return 0;
    }

    @Override
    public Map<Preparation, Integer> findPreparationsForOrders(long orderId) throws ServiceException {
        return null;
    }

    @Override
    public boolean updateOrdersStatus(String ordersId, Orders.OrdersStatus refuse, User.Role role) {
        return false;
    }

    @Override
    public List<Orders> findOrdersByStatuses(EnumSet<Orders.OrdersStatus> statuses) {
        return null;
    }

}
