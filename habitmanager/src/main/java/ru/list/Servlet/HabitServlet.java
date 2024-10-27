package ru.list.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.list.ApplicationData;
import ru.list.Controller.HabitController;

@WebServlet("/habits")
public class HabitServlet extends HttpServlet {
    ApplicationData applicationData = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        applicationData = ApplicationData.getInstance();
        HabitController habitController = applicationData.gHabitController();
        String response = habitController.getHabits();

        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(response);
        out.flush();   
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        applicationData = ApplicationData.getInstance();
        HabitController habitController = applicationData.gHabitController();
        String jsonHabit = req.getParameter("habit");
        boolean result = false;
        if (jsonHabit != null) {
            result = habitController.addHabit(jsonHabit);
        }

        if (result) {
            resp.setStatus(201); //CREATED
        } else {
            resp.setStatus(304); //NOT MODIFIED
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        applicationData = ApplicationData.getInstance();
        HabitController habitController = applicationData.gHabitController();
        String strId = req.getParameter("id");
        boolean result = false;

        if (strId != null) {
            int id = Integer.parseInt(strId);
            result = habitController.deleteHabit(id);
        }
        
        if (result) {
            resp.setStatus(200); //OK
        } else {
            resp.setStatus(304); //NOT MODIFIED
        }
    }

}
