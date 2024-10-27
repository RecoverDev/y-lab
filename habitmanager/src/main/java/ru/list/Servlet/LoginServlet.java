package ru.list.Servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.list.ApplicationData;
import ru.list.Controller.AutorizationController;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {
    ApplicationData applicationData = null;
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        applicationData = ApplicationData.getInstance();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        AutorizationController controller = applicationData.getAutorizationController();
        boolean result = controller.login(email, password);

        if (result) {
            resp.setStatus(200); //OK
        } else {
            resp.setStatus(401); //Unauthorized 
        }
    }

}
