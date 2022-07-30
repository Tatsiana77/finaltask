package by.edu.sarnatskaya.pharmacy.controller.command.impl.pharmacist;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.Purchase;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.OrdersService;
import by.edu.sarnatskaya.pharmacy.model.service.PrescriptionService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import by.edu.sarnatskaya.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.ORDERS_LIST;
import static by.edu.sarnatskaya.pharmacy.model.entity.Orders.OrdersStatus.*;


public class ManagerCommandsOverallControl {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = ServiceProvider.getInstance().getUserService();
    private  static final OrdersService ordersService = ServiceProvider.getInstance().getOrdersService();
    private  static final PrescriptionService prescriptionService = ServiceProvider.getInstance().getPrescriptionService();

    public ManagerCommandsOverallControl(HttpServletRequest request) {
    }

    static void setActiveOrdersToAttribute(HttpServletRequest request) throws CommandException{
        HttpSession session = request.getSession();
        try{
            Map<Orders, User> activeOrders =  new LinkedHashMap<>();
            List<Orders> activeOrdersList= ordersService.findOrdersByStatuses(EnumSet.of(PREPARING, ORDERED));
            for(Orders orders : activeOrdersList){
                Optional<User> optionalUser = userService.findUserById(orders.getUserId());
                if(optionalUser.isPresent()){
                    User client = optionalUser.get();
                    activeOrders.put(orders, client);
                }else{
                    logger.log(Level.WARN, "User with id " + orders.getUserId() + " doesn't exist");
                }
            }
            session.setAttribute(ORDERS_LIST, activeOrders);
        }catch (ServiceException e){
            logger.log(Level.ERROR, "Impossible to set active orders request attribute:", e);
            throw new CommandException("Impossible to set active orders request attribute:", e);
        }
    }
}
