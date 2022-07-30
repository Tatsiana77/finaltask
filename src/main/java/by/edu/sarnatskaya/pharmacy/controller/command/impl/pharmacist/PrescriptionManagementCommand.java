package by.edu.sarnatskaya.pharmacy.controller.command.impl.pharmacist;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.controller.command.Command;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Prescription;
import by.edu.sarnatskaya.pharmacy.model.service.PrescriptionService;
import by.edu.sarnatskaya.pharmacy.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import java.util.List;

import static by.edu.sarnatskaya.pharmacy.controller.command.AttributeName.*;
import static by.edu.sarnatskaya.pharmacy.controller.command.PagePath.*;

public class PrescriptionManagementCommand implements Command {

    private static final PrescriptionService prescriptionService = ServiceProvider.getInstance().getPrescriptionService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        try {
            List<Prescription> prescriptions = prescriptionService.findAll();
            session.setAttribute(PRESCRIPTION_LIST, prescriptions);
            router.setPagePath(PRESCRIPTION_MANAGEMENT_PAGE);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Impossible to find prescription: ", e);
            throw new CommandException("Impossible to find prescription: ");
        }
    }
}
