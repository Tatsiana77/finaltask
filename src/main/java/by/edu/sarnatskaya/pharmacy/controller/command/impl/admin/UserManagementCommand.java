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

import java.util.List;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.USER_LIST;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.USER_MANAGEMENT_PAGE;

public class UserManagementCommand  implements Command {

    private static final UserService userService = ServiceProvider.getInstance().getUserService();
    /**
     * @param request the HttpServletRequest
     * @return the {@link Router}
     * @throws CommandException if the request could  be not  handled.
     */

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        try {
            List<User> users = userService.findAllUsers();
            session.setAttribute(USER_LIST, users);
            router.setPagePath(USER_MANAGEMENT_PAGE);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Users cannot be found:", e);
            throw new CommandException("Users cannot be found:", e);
        }
    }
}
