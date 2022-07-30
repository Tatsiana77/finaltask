package by.edu.sarnatskaya.pharmacy.controller.command.impl.common;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.WRONG_COMMAND;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.ERROR_400_PAGE;

public class NonExistentCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        request.setAttribute(WRONG_COMMAND, Boolean.TRUE);
        router.setPagePath(ERROR_400_PAGE);
        return router;
    }
}
