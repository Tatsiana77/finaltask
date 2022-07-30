package by.edu.sarnatskaya.pharmacy.controller.command.impl.client;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.AttributeName;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Orders;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.OrdersService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.ORDER_LIST;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.ORDER_PAGE;

/**
 * Find user {@link Orders} command.
 * Used by clients for displaying {@link Orders}.
 *
 */

public class ShowUserOrdersCommand implements Command {
    private static final OrdersService ordersService = ServiceProvider.getInstance().getOrdersService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
      Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AttributeName.SESSION_USER );
        try {

            List<Orders> ordersList = ordersService.findOrdersByUserId(user.getId());
            session.setAttribute(ORDER_LIST, ordersList);
            router.setPagePath(ORDER_PAGE);
            return router;
        } catch ( ServiceException e) {
            logger.log(Level.ERROR, "User orders cannot be found:", e);
            throw new CommandException("User orders cannot be found:", e);
        }
    }
}
