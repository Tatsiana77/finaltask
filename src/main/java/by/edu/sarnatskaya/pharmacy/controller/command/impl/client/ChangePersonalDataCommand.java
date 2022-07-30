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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;
/**
 * Change personal data command.
 * Used by clients for editing their personal data.
 *
 * @see Command
 */

public class ChangePersonalDataCommand implements Command {

    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    /**
     * @param request the HttpServletRequest
     * @return the {@link Router}
     * @throws CommandException if the request could be not handled.
     */
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute(SESSION_USER);
        String type = request.getParameter(TYPE);
        String value = request.getParameter(VALUE);
        try {
            type = type != null ? type : EMPTY;
            boolean result = switch (type) {
                case NAME -> {
                    boolean changeResult = userService.updateNameById(user.getId(), value);
                    request.setAttribute(NAME_CHANGE_RESULT, changeResult);
                    yield changeResult;
                }
                case SURNAME -> {
                    boolean changeResult = userService.updateSurnameById(user.getId(), value);
                    request.setAttribute(SURNAME_CHANGE_RESULT, changeResult);
                    yield changeResult;
                }
                case EMAIL -> {
                    Map<String, String> emailResult = new HashMap<>();
                    emailResult.put(EMAIL, value);
                    boolean changeResult = userService.updateEmailById(user.getId(), emailResult);
                    if (changeResult) {
                        request.setAttribute(EMAIL_CHANGE_RESULT, true);
                    } else {
                        request.setAttribute(EMAIL_CHANGE_RESULT, emailResult.get(EMAIL));
                    }
                    yield changeResult;
                }
                case PHONE -> {
                    Map<String, String> phoneResult = new HashMap<>();
                    phoneResult.put(PHONE, value);
                    boolean changeResult = userService.updatePhoneById(user.getId(), phoneResult);
                    if (changeResult) {
                        request.setAttribute(PHONE_CHANGE_RESULT, changeResult);
                    } else {
                        request.setAttribute(PHONE_CHANGE_RESULT, phoneResult.get(PHONE));
                    }
                    yield changeResult;
                }
                default -> {
                    logger.log(Level.WARN, "Unexpected value: " + type);
                    yield false;
                }
            };
            List<User >usersList = userService.findAllUsers();
            session.setAttribute(USER_LIST, usersList);
            request.setAttribute(PERSONAL_DATA_CHANGE_RESULT, result);
            router.setPagePath(SETTINGS_PAGE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Personal data cannot be changed:", e);
            throw new CommandException("Personal data cannot be changed:", e);
        }
        return router;
    }
}
