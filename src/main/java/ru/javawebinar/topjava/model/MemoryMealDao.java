package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealDao implements IMealCrud {
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(0);

    public MemoryMealDao() {

        addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500);
        addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000);
        addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500);
        addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100);
        addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000);
        addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500);
        addMeal( LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410);
    }

    public Meal getMeal(Integer id) {
        return mealMap.getOrDefault(id, null);
    }

    public Meal addMeal(LocalDateTime dateTime, String description, Integer calories) {
        Meal meal = new Meal(getNextId(), dateTime, description, calories);
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    public Meal updateMeal(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        mealMap.put(id, meal);
        return meal;
    }

    public void deleteMeal(Integer mealId) {
        mealMap.remove(mealId);
    }

    public List<MealTo> getAllMealTo() {
        return MealsUtil.filteredByStreams(mealMap.values(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    private Integer getNextId() {
        return nextId.incrementAndGet();
    }
}
