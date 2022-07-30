package by.edu.sarnatskaya.pharmacy.controller.command.impl.admin;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import by.edu.sarnatskaya.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import static by.edu.sarnatskaya.pharmacy.controller.Router.RouterType.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;
import static by.edu.sarnatskaya.pharmacy.model.entity.User.Status.ACTIVE;
import static by.edu.sarnatskaya.pharmacy.model.entity.User.Status.BLOCKED;
/**
 * {@link User} general control command
 * Used by admin for changing users statuses.
 * */
public class UserActionCommand implements Command {
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        StringBuilder paramPagePath = new StringBuilder();
        String action = request.getParameter(ACTION);
        String[] userIdArray = request.getParameterValues(SELECTED);
        try {
            action = action != null ? action : EMPTY;
            Boolean actionResult = switch (action) {
                case UNBLOCK -> userService.updateUserStatusesById(ACTIVE, userIdArray);
                case BLOCK-> userService.updateUserStatusesById(BLOCKED, userIdArray);
                default -> Boolean.FALSE;
            };
            List<User> users = new ArrayList<>();
            session.setAttribute(USER_LIST, users);
            paramPagePath.append(USER_MANAGEMENT_PAGE).append(PARAMETERS_START).append(USER_ACTION_RESULT)
                    .append(EQUALS).append(actionResult);
            router.setPagePath(paramPagePath.toString());
            if (actionResult) {
                router.setRouterType(REDIRECT);
            }
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Action with user cannot be completed:", e);
            throw new CommandException("Action with user cannot be completed:", e);
        }
    }
}

