package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {

    @Autowired
    private MealService service;

    @RequestMapping("/delete")
    public String deleteMeal(@RequestParam("id") String Id) {
        var id = Integer.parseInt(Id);
        service.delete(id, SecurityUtil.authUserId());
        //model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), 2000));
        return "redirect:/meals";
    }

    @RequestMapping("/create")
    public String createMeal(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @RequestMapping("/update")
    public String updateMeal(Model model, @RequestParam("id") String Id) {
        var meal = service.get(Integer.parseInt(Id), SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping("/filter")
    public String filterMeal(Model model, @RequestParam("startDate") String startDate,
                             @RequestParam("endDate") String endDate,
                             @RequestParam("startTime") String startTime,
                             @RequestParam("endTime") String endTime) {
        LocalDate ldStartDate = parseLocalDate(startDate);
        LocalDate ldEndDate = parseLocalDate(endDate);
        LocalTime ltStartTime = parseLocalTime(startTime);
        LocalTime ltEndTime = parseLocalTime(endTime);
        model.addAttribute("meals", MealsUtil.getTos(service.getBetweenInclusive(ldStartDate, ldEndDate, SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return"meals";
    }

    @RequestMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @PostMapping("/meals")
    public String SetMeals(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        String idString = request.getParameter("id");

        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if(idString!=null && !idString.isEmpty())
            meal.setId(Integer.parseInt(idString));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            service.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:meals";
    }

}
