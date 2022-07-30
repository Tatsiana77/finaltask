package by.edu.sarnatskaya.pharmacy.model.dao;


import by.edu.sarnatskaya.pharmacy.model.dao.impl.OrdersDaoImpl;
import by.edu.sarnatskaya.pharmacy.model.dao.impl.PreparationDaoImpl;
import by.edu.sarnatskaya.pharmacy.model.dao.impl.PrescriptionDaoImpl;
import by.edu.sarnatskaya.pharmacy.model.dao.impl.UserDaoImpl;


public class DaoProvider {
    private UserDao userDao = new UserDaoImpl();
    private PreparationDao preparationsDao = new PreparationDaoImpl();
    private OrdersDao ordersDao = new OrdersDaoImpl();
    private PrescriptionDao prescriptionDao = new PrescriptionDaoImpl();

    public DaoProvider() {
    }

    private static class DaoProviderHolder {
        private static final DaoProvider instance = new DaoProvider();
    }

    public static DaoProvider getInstance() {
        return DaoProviderHolder.instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PreparationDao getPreparationsDao() {
        return preparationsDao;
    }

    public OrdersDao getOrdersDao() {
        return ordersDao;
    }

    public PrescriptionDao getPrescriptionDao() {
        return prescriptionDao;
    }
}
