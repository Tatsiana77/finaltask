package by.edu.sarnatskaya.pharmacy.controller.command.impl.pharmacist;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.model.service.PreparationService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.PREPARATIONS_LIST;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.PREPARATION_MANAGEMENT_PAGE;

public class PreparationManagementCommand implements Command {
    private static final PreparationService preparationService = ServiceProvider.getInstance().getPreparationService();


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        try {
            List<Preparation> preparations = preparationService.findAll();
            session.setAttribute(PREPARATIONS_LIST, preparations);
            router.setPagePath(PREPARATION_MANAGEMENT_PAGE);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Impossible to find preparations:", e);
            throw new CommandException("Impossible to find preparations:", e);
        }
    }
}
