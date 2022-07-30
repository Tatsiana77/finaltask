package by.edu.sarnatskaya.pharmacy.controller.command.impl.client;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.OrdersService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;


/**
 * Order action command.
 * Used by clients for doing action with {@link Orders}.
 */

public class OrderActionCommand implements Command {
    private static final OrdersService ordersService = ServiceProvider.getInstance().getOrdersService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        StringBuilder paramPagePath = new StringBuilder();
        User user = (User) session.getAttribute(SESSION_USER);
        String action = request.getParameter(ACTION);
        String ordersId = request.getParameter(SELECTED);
        try {
            action = action != null ? action : EMPTY;
            boolean actionResult = switch (action) {
                case REFUSE -> ordersService.updateOrdersStatus(ordersId, Orders.OrdersStatus.REFUSE, user.getRole());
                case PREPARING -> ordersService.updateOrdersStatus(ordersId, Orders.OrdersStatus.PREPARING, user.getRole());
                default -> {
                    logger.log(Level.WARN, "Unexpected value: " + action);
                    yield false;
                }
            };
            paramPagePath.append(ORDER_PAGE)
                    .append(PARAMETERS_START)
                    .append(ACTION_RESULT)
                    .append(EQUALS)
                    .append(actionResult);
            List<Orders> orders = ordersService.findOrdersByUserId(user.getId());
            session.setAttribute(ORDER_LIST, orders);
            router.setPagePath(paramPagePath.toString());
            if (actionResult) {
                router.setRouterType(Router.RouterType.REDIRECT);
            }
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Impossible to do action with order:", e);
            throw new CommandException("Impossible to do action with order:", e);
        }
    }
}
