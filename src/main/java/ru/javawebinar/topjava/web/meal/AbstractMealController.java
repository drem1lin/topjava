package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

public class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll(int userId) {
        log.info("getAll");
        return MealsUtil.getTos(new ArrayList<>(service.getAll(userId)), authUserCaloriesPerDay());
    }

    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal = service.get(id, userId);
        checkUser(meal, userId);
        return meal;
    }

    public Meal create(Meal meal, int userId) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(userId);
        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        Meal meal = service.get(id, userId);
        checkUser(meal, userId);
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int userId){
        log.info("update {}", meal);
        assureUserIdConsistent(meal, userId);
        service.update(meal, userId);
    }
}
