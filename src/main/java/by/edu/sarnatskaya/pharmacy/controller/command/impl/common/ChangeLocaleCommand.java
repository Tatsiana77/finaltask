package by.edu.sarnatskaya.pharmacy.controller.command.impl.common;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.util.validator.LocaleValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;


public class ChangeLocaleCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String newLocale = request.getParameter(SESSION_LOCALE);
        logger.log(Level.DEBUG, "Locale parameter is " + newLocale);
        if (LocaleValidator.isLocaleExist(newLocale)) {
            session.setAttribute(SESSION_LOCALE, newLocale);
        } else {
            logger.log(Level.WARN, "Wrong locale parameter: " + newLocale);
        }
        router.setPagePath(currentPage);
        return router;
    }
}

