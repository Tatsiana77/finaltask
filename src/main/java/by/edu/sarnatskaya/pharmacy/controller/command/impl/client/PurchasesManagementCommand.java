package by.edu.sarnatskaya.pharmacy.controller.command.impl.client;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.PURCHASES_PAGE;

public class PurchasesManagementCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserCommandOverallControl.setPreparationIntoPurchases(request);
        router.setPagePath(PURCHASES_PAGE );
        return  router;
    }
}
