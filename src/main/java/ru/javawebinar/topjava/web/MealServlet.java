package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.dao.MemoryMealDao;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.net.URLEncoder.encode;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private MemoryMealDao mealsDb = new MemoryMealDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id;

        String action = request.getParameter("action");
        if(action==null)
            action = "";
        switch (action) {
            case "delete":
                id = Integer.parseInt(request.getParameter("Id"));
                log.debug("Deleting meal " + id);
                mealsDb.delete(id);
                response.sendRedirect(request.getRequestURI());
                return;
            case "edit":
                Meal meal = null;
                if (null != request.getParameter("Id")) {
                    id = Integer.parseInt(request.getParameter("Id"));
                    meal = mealsDb.get(id);
                }
                log.debug("Open adding/editing meal window for " + ((meal == null) ? "adding new" : "editing existing"));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/addmeal.jsp").forward(request, response);
                return;
            case "add":
                id = Integer.parseInt(request.getParameter("Id"));
                int calories = Integer.parseInt(request.getParameter("calories"));
                String description = request.getParameter("description");
                String dateString = request.getParameter("datetime");
                LocalDateTime date = LocalDateTime.parse(dateString);
                if (id == 0) {
                    log.debug("Adding meal " + description + "\t" + dateString + "\t" + calories);
                    mealsDb.add(date, description, calories);
                } else {
                    log.debug("Updating meal " + id + "\t" + description + "\t" + dateString + "\t" + calories);
                    mealsDb.update(id, date, description, calories);
                }
                response.sendRedirect(request.getRequestURI());
                return;
            default:
                log.debug("Displaying meals");
                List<MealTo> mealToList = MealsUtil.filteredByStreams(mealsDb.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("mealToList", mealToList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
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
        response.setCharacterEncoding("UTF-8");
        log.debug("Post: " + request.getParameter("description") + "\t" + request.getParameter("calories") + "\t" +
                request.getParameter("datetime"));
        int calories = getIntOrDefault(request, "calories", 0);
        String description = request.getParameter("description");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("datetime"));
        int Id = getIntOrDefault(request, "id", 0);
        response.sendRedirect("meals?action=add&Id=" + Id + "&description=" + encode(description, "UTF-8") + "&calories=" + calories +
                "&datetime=" + date);
    }
}
