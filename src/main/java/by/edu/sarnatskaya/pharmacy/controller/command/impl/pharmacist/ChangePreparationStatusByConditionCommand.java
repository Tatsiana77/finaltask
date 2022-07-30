package by.edu.sarnatskaya.pharmacy.controller.command.impl.pharmacist;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.User;
import by.edu.sarnatskaya.pharmacy.model.service.PreparationService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.edu.sarnatskaya.pharmacy.controller.Router.RouterType.REDIRECT;
import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;
import static by.edu.sarnatskaya.pharmacy.model.entity.Preparation.Condition.*;


public class ChangePreparationStatusByConditionCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final PreparationService preparationService = ServiceProvider.getInstance().getPreparationService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        StringBuilder paramPagePath = new StringBuilder();
        User user = (User) session.getAttribute(SESSION_USER);
        String preparationId  = request.getParameter(SELECTED);
        String action = request.getParameter(ACTION);
        try {
            action = action != null ? action : EMPTY;
            boolean result = switch (action) {
                case FREE_PRESCRIPTION -> preparationService.isPreparationStatusById(preparationId, FREE, user.getRole());
                case WITH_PRESCRIPTION -> preparationService.isPreparationStatusById(preparationId, PRESCRIPTION, user.getRole());
                default -> {
                    logger.log(Level.WARN, "Unexpected value: " + action);
                    yield false;
                }
            };
            ManagerCommandsOverallControl.setActiveOrdersToAttribute(request);
            paramPagePath.append(PREPARATION_CONFIRMATION_PAGE).append(PARAMETERS_START).append(ACTION_RESULT)
                            .append(EQUALS).append(result);
            router.setPagePath(paramPagePath.toString());
            if (result) {
                router.setRouterType(REDIRECT);
            }
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Impossible to change preparation  status. Preparation  id is" + preparationId + " , action is ", e);
            throw new CommandException("Impossible to change preparation  status. Preparation  id is" + preparationId + " , action is ", e);
        }
    }
}
