package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MemoryMealDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private MemoryMealDao mealsDb = new MemoryMealDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("Id"));
            mealsDb.deleteMeal(id);
            response.sendRedirect(request.getRequestURI());
            return;
        }
        if (action != null && action.equalsIgnoreCase("edit")) {
            Meal meal = null;
            if(null != request.getParameter("Id")) {
                int id = Integer.parseInt(request.getParameter("Id"));
                meal = mealsDb.getMeal(id);
            }
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/addmeal.jsp").forward(request, response);
            return;
        }
        if (action != null && action.equalsIgnoreCase("add")) {
            int id = Integer.parseInt(request.getParameter("Id"));
            int calories = Integer.parseInt(request.getParameter("calories"));
            String description = request.getParameter("description");
            String dateString = request.getParameter("datetime");
            LocalDateTime date = LocalDateTime.parse(dateString);
            if (id == 0) {
                mealsDb.addMeal(date, description, calories);
            } else {
                mealsDb.updateMeal(id, date, description, calories);
            }
            response.sendRedirect(request.getRequestURI());
            return;
        }

        List<MealTo> mealToList = mealsDb.getAllMealTo();
        request.setAttribute("mealToList", mealToList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    protected Integer getIntOrDefault(HttpServletRequest request, String paramName, Integer def) {
        int val = def;
        try {
            val = Integer.parseInt(request.getParameter(paramName));
        } catch (NumberFormatException nfe) {
            log.debug(nfe.toString());
        }
        return val;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("Post: " + request.getParameter("description") + "\t" + request.getParameter("calories") + "\t" +
                request.getParameter("datetime"));
        int calories = getIntOrDefault(request, "calories", 0);
        String description = request.getParameter("description");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("datetime"));
        int Id = getIntOrDefault(request, "id", 0);
        response.sendRedirect("meals?action=add&Id=" + Id + "&description=" + description + "&calories=" + calories +
                "&datetime=" + date);
    }
}
