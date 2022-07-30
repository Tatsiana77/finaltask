package by.edu.sarnatskaya.pharmacy.controller.command.impl.pharmacist;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.service.PreparationService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;

public class UpdatePurchasesCommand implements Command {
    private static final PreparationService preparationService = ServiceProvider.getInstance().getPreparationService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String purchasesId = request.getParameter(SELECTED_PURCHASES);
        try {
            String action = request.getParameter(ACTION);
            String preparationId = request.getParameter(SELECTED);
            action = action != null ? action : EMPTY;
            boolean result = switch (action){
                case APPEND -> preparationService.updatePreparation(preparationId, purchasesId);
                case REMOVE -> preparationService.deletePreparation(preparationId, purchasesId);
                default -> {
                    logger.log(Level.WARN, "Unexpected value: "+ action);
                    yield false;
                }
            };
            request.setAttribute(ACTION_RESULT , result);
            new ManagerCommandsOverallControl(request);
            return  router;
        } catch (ServiceException  e) {
            logger.log(Level.ERROR, "Impossible to update purchases by  id " + purchasesId, e);
            throw new CommandException("Impossible to update purchases  id " + purchasesId, e);
        }
    }
}
