package ru.list.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.list.ApplicationData;
import ru.list.Controller.AutorizationController;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    ApplicationData applicationData = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        applicationData = ApplicationData.getInstance();
        AutorizationController controller = applicationData.getAutorizationController();
        controller.logout();

        resp.setStatus(200); //OK
    }


}
