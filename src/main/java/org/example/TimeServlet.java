package org.example;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Отримуємо значення параметра timezone з запиту
        String timezone = request.getParameter("timezone");

        // Встановлюємо часовий пояс відповідно до параметра timezone
        ZoneId zoneId;
        if (timezone != null && !timezone.isEmpty()) {
            // Виправлення формату параметру timezone
            timezone = timezone.replace(" ", "+");
            zoneId = ZoneId.of(timezone);
        } else {
            zoneId = ZoneId.of("UTC");
        }

        // Отримуємо поточний час в вказаному часовому поясі
        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = currentTime.format(formatter);

        // Формуємо HTML відповідь
        String htmlResponse = "<html><body>";
        htmlResponse += "<h2>Current time:</h2>";
        htmlResponse += "<p>" + formattedTime + "</p>";
        htmlResponse += "</body></html>";

        // Надсилаємо HTML відповідь
        response.getWriter().write(htmlResponse);
    }
}
