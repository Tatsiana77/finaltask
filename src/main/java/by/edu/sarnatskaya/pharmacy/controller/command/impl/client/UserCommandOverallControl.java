package by.edu.sarnatskaya.pharmacy.controller.command.impl.client;

import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import by.edu.sarnatskaya.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;


public class UserCommandOverallControl  {

    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    public static void setPreparationIntoPurchases(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_USER);
        try{
            Map<Preparation, Integer> purchases = userService.findPreparationInPurchasesByUserId(user.getId());
            session.setAttribute(PURCHASES, purchases);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Impossible to find purchases info for user with id " + user.getId(), e);
            throw new CommandException("Impossible to find purchases  info for user with id " + user.getId(), e);
        }
    }
}
