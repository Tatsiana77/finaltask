package by.edu.sarnatskaya.pharmacy.controller.command.impl.common;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import by.edu.sarnatskaya.pharmacy.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;


import java.util.HashMap;
import java.util.Map;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;

public class RegistrationCommand implements Command {
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        Map<String, String> dataResult = new HashMap<>();
        dataResult.put(LOGIN, request.getParameter(LOGIN));
        dataResult.put(PASSWORD, request.getParameter(PASSWORD));
        dataResult.put(CONFIRM_PASSWORD, request.getParameter(CONFIRM_PASSWORD));
        dataResult.put(EMAIL, request.getParameter(EMAIL));
        dataResult.put(PHONE, request.getParameter(PHONE));
        dataResult.put(USER_ROLE, request.getParameter(USER_ROLE));
        try {
            boolean registrationResult = userService.registerUser(dataResult);
            String currentPage = (String) request.getSession().getAttribute(CURRENT_PAGE);
            if (currentPage.equals(USER_MANAGEMENT_PAGE)) {
                request.setAttribute(USER_LIST, userService.findAllUsers());
                router.setPagePath(USER_MANAGEMENT_PAGE);
            } else {
                if (registrationResult) {
                    router.setPagePath(ACCOUNT_CREATION_DETAILS_PAGE);
                } else {
                    router.setPagePath(REGISTRATION_PAGE);
                }
            }
            if (!registrationResult) {
                for (String key : dataResult.keySet()) {
                    String validationResult = dataResult.get(key);
                    if (Boolean.parseBoolean(validationResult)) {
                        switch (key) {
                            case LOGIN -> request.setAttribute(VALID_LOGIN, request.getParameter(LOGIN));
                            case EMAIL -> request.setAttribute(VALID_EMAIL, request.getParameter(EMAIL));
                            case PHONE -> request.setAttribute(VALID_PHONE_NUMBER, request.getParameter(PHONE));
                        }
                    } else {
                        switch (validationResult) {
                            case INVALID_LOGIN_RESULT -> request.setAttribute(INVALID_LOGIN, INVALID_MESSAGE);
                            case NOT_UNIQUE_LOGIN_RESULT -> request.setAttribute(INVALID_LOGIN, NOT_UNIQUE_MESSAGE);
                            case INVALID_PASSPORT_RESULT -> request.setAttribute(INVALID_PASSPORT, INVALID_MESSAGE);
                            case PASSWORD_DISCREPANCY -> request.setAttribute(INVALID_PASSPORT, PASSWORD_DISCREPANCY);
                            case INVALID_EMAIL_RESULT -> request.setAttribute(INVALID_EMAIL, INVALID_MESSAGE);
                            case NOT_UNIQUE_EMAIL_RESULT -> request.setAttribute(INVALID_EMAIL, NOT_UNIQUE_MESSAGE);
                            case INVALID_PHONE_NUMBER_RESULT -> request.setAttribute(INVALID_PHONE_NUMBER, INVALID_MESSAGE);
                            case NOT_UNIQUE_PHONE_NUMBER_RESULT -> request.setAttribute(INVALID_PHONE_NUMBER, NOT_UNIQUE_MESSAGE);
                        }
                        logger.log(Level.DEBUG, "Validation result: " + key + " - " + validationResult);
                    }
                }
            }
            request.setAttribute(REGISTRATION_RESULT, registrationResult);
            return  router;
        }catch (ServiceException e){
            logger.log(Level.ERROR, "User cannot be registered:", e);
            throw new CommandException("User cannot be registered:", e);
        }
    }
}
