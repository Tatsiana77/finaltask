package by.edu.sarnatskaya.pharmacy.controller.command.impl.client;

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

import java.util.*;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.SETTINGS_PAGE;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;

/**
 * This command used by {@link User} for changing password from their  profile.
 */
public class ChangeUserPassword implements Command {
    private static final UserService userService = ServiceProvider.getInstance().getUserService();


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute(SESSION_USER);
        Map<String, String> passwordData = new HashMap<>();

        updateUserDataFromRequest(request, passwordData);

        passwordData.put(OLD_PASSWORD, request.getParameter(OLD_PASSWORD));
        passwordData.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
        passwordData.put(CONFIRM_PASSWORD, request.getParameter(CONFIRM_PASSWORD));
        try {
            boolean actionResult = userService.changePassword(user.getId(), passwordData);
            if (actionResult) {
                request.setAttribute(PASSWORD_CHANGE_RESULT, actionResult);
            } else {
                for (String key : passwordData.keySet()) {
                    String validationResult = passwordData.get(key);
                    if (!Boolean.parseBoolean(validationResult)) {
                        switch (validationResult) {
                            case INCORRECT_PASSWORD -> request.setAttribute(PASSWORD_CHANGE_RESULT, INCORRECT_MESSAGE);
                            case INVALID_PASSPORT -> request.setAttribute(PASSWORD_CHANGE_RESULT, INVALID_MESSAGE);
                            case PASSWORD_DISCREPANCY -> request.setAttribute(PASSWORD_CHANGE_RESULT, PASSWORD_DISCREPANCY);
                        }
                    }
                }
            }
            Optional<User> usersList = userService.findUserById(user.getId());
            session.setAttribute(USER_LIST, usersList);
            router.setPagePath(SETTINGS_PAGE);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "User password cannot be changed:", e);
            throw new CommandException("User password cannot be changed:", e);
        }
    }

    private void updateUserDataFromRequest(HttpServletRequest request, Map<String, String> userData) {
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(NEW_PASSWORD, request.getParameter(NEW_PASSWORD));
    }
}
