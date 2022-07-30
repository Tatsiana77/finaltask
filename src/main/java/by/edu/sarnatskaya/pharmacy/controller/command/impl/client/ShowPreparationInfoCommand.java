package by.edu.sarnatskaya.pharmacy.controller.command.impl.client;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.model.service.PrescriptionService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowPreparationInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final PrescriptionService prescriptionService = ServiceProvider.getInstance().getPrescriptionService();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();


        return null;
    }
}
