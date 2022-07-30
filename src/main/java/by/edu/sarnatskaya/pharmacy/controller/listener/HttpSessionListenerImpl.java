package by.edu.sarnatskaya.pharmacy.controller.listener;

import by.edu.sarnatskaya.pharmacy.controller.command.PagePath;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.time.LocalDate;

import static by.edu.sarnatskaya.pharmacy.controller.command.SessionAttribute.*;


@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener{
    private static final String DEFAULT_LOCALE = "en_EN";
    private static final String DEFAULT_LANGUAGE = "en_US";


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE );
        session.setAttribute(CURRENT_PAGE, PagePath.INDEX_PAGE);
        LocalDate today = LocalDate.now();
        session.setAttribute(TODAY, today.toString());
        session.setAttribute(TOMORROW, today.plusDays(1).toString());
    }
}
