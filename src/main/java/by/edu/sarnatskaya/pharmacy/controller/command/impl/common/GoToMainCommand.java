package by.edu.sarnatskaya.pharmacy.controller.command.impl.common;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.MAIN_PAGE;

public class GoToMainCommand  implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPagePath(MAIN_PAGE);
        return router;
    }
}
