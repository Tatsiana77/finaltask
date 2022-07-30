package by.edu.sarnatskaya.pharmacy.controller.command;

import by.edu.sarnatskaya.pharmacy.controller.Router;
import by.edu.sarnatskaya.pharmacy.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.edu.sarnatskaya.pharmacy.controller.command.ParameterName.*;



public interface Command {
    Logger logger = LogManager.getLogger();

    Router execute(HttpServletRequest request) throws CommandException;

    default  int getPage(HttpServletRequest request){
        int page =FIRST_PAGE;
        String stringPage = request.getParameter( PAGE_NUMBER );
        if(stringPage !=null){
            try {
                page = Integer.parseInt(stringPage);
            } catch (NumberFormatException e) {
                logger.log(Level.WARN, "Cannot parse page number " +  stringPage + ". The first page will be used - 1", e);
            }
        }
        return page;
    }
}
