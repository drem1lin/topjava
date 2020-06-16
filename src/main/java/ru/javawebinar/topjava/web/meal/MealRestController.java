package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {
    public List<MealTo> getAll() {
        log.info("getAll");
        return super.getAll(authUserId());
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return super.get(mealId, authUserId());
    }

    public Meal create(MealTo mealTo) {
        log.info("create {}", mealTo);
        Meal meal = MealsUtil.createFromTo(mealTo);
        return super.create(meal, authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        super.delete(mealId, authUserId());
    }

    public void update(MealTo mealTo) {
        Meal meal = MealsUtil.createFromTo(mealTo);
        super.update(meal, authUserId());
    }
}