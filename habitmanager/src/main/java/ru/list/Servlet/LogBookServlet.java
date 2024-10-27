package ru.list.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.list.ApplicationData;
import ru.list.Controller.LogBookController;

@WebServlet("/logbooks")
public class LogBookServlet extends HttpServlet {
    ApplicationData applicationData = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        applicationData = ApplicationData.getInstance();
        LogBookController logbookController = applicationData.getLogBookController();

        String strId = req.getParameter("id");
        String response = "";

        if (strId != null) {
            int id = Integer.parseInt(strId);
            response = logbookController.getLogBooks(id);
        }


        PrintWriter out = resp.getWriter();
        if (response.isEmpty()) {
            resp.setStatus(204); // NO CONTENT
        } else {
            resp.setStatus(200); // OK
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(response);
        out.flush();   
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        applicationData = ApplicationData.getInstance();
        LogBookController logbookController = applicationData.getLogBookController();
        String jsonLogbook = req.getParameter("logbook");
        boolean result = false;
        if (jsonLogbook != null) {
            result = logbookController.addLogbook(jsonLogbook);
        }

        if (result) {
            resp.setStatus(201); //CREATED
        } else {
            resp.setStatus(304); //NOT MODIFIED
        }
    }

}


