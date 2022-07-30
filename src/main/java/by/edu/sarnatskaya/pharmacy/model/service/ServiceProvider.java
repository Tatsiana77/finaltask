package by.edu.sarnatskaya.pharmacy.model.service;

import by.edu.sarnatskaya.pharmacy.model.service.impl.OrdersServiceImpl;
import by.edu.sarnatskaya.pharmacy.model.service.impl.PreparationServiceImpl;
import by.edu.sarnatskaya.pharmacy.model.service.impl.PrescriptionServiceImpl;
import by.edu.sarnatskaya.pharmacy.model.service.impl.UserServiceImpl;

public class ServiceProvider {
    private UserService userService = new UserServiceImpl();
    private PreparationService preparationService = new PreparationServiceImpl();
    private OrdersService ordersService = new OrdersServiceImpl();
    private PrescriptionService prescriptionService = new PrescriptionServiceImpl();


    public ServiceProvider() {
    }

    private static class ServiceProviderHolder {
        private static final ServiceProvider instance = new ServiceProvider();
    }

    public static ServiceProvider getInstance() {
        return ServiceProviderHolder.instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public PreparationService getPreparationService() {
        return preparationService;
    }

    public OrdersService getOrdersService() {
        return ordersService;
    }

    public PrescriptionService getPrescriptionService() {
        return prescriptionService;
    }
}

