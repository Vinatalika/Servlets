package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Перевіряємо, чи існує параметр timezone
        String timezone = httpRequest.getParameter("timezone");

        if (timezone == null || timezone.isEmpty()) {
            // Повертаємо помилку, якщо параметр timezone не вказаний
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Invalid timezone");
            return;
        }

        // Валідуємо часовий пояс
        if (!isValidTimezone(timezone)) {
            // Повертаємо помилку, якщо часовий пояс недійсний
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Invalid timezone");
            return;
        }

        // Продовжуємо ланцюжок фільтрів, якщо все валідно
        chain.doFilter(request, response);
    }

    private boolean isValidTimezone(String timezone) {
        try {
            // Спробуємо створити об'єкт TimeZone з вказаним значенням
            TimeZone.getTimeZone(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // Метод init не потрібен, але вимагається інтерфейсом Filter
    }

    public void destroy() {
        // Метод destroy не потрібен, але вимагається інтерфейсом Filter
    }
}
