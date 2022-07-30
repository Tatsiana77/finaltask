package by.edu.sarnatskaya.pharmacy.controller.command.impl.common;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import by.edu.sarnatskaya.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.Optional;


import static by.edu.sarnatskaya.pharmacy.controller.Router.RouterType.REDIRECT;
import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;

public class AuthenticationCommand implements Command {
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        try {
            Optional<User> userOptional = userService.findUserByLoginAndPassword(login, password);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getRole() == User.Role.ADMIN || user.getStatus() != User.Status.BLOCKED) {
                    session.setAttribute(SESSION_USER, user);
                    request.setAttribute(AUTHENTICATION_RESULT, Boolean.TRUE);
                } else {
                    router.setErrorCode(HttpServletResponse.SC_FORBIDDEN);
                    router.setErrorMessage(ACCOUNT_BLOCKAGE_MESSAGE);
                }
            } else {
                request.setAttribute(AUTHENTICATION_RESULT, Boolean.FALSE);
            }
            router.setPagePath(MAIN_PAGE);
            router.setRouterType(REDIRECT);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Authentication cannot be completed:", e);
            throw new CommandException("Authentication cannot be completed:", e);
        }
    }
}
