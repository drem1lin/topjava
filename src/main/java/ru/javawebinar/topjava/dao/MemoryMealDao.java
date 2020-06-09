package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealDao implements IMealCrud {
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(0);

    public MemoryMealDao() {

        add(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000);
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410);
    }

    public Meal get(Integer id) {
        return mealMap.getOrDefault(id, null);
    }

    public Meal add(LocalDateTime dateTime, String description, Integer calories) {
        Meal meal = new Meal(getNextId(), dateTime, description, calories);
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    public Meal update(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        mealMap.put(id, meal);
        return meal;
    }

    public void delete(Integer mealId) {
        mealMap.remove(mealId);
    }

    public Collection<Meal> getAllMeals() {
        return mealMap.values();
    }

    private Integer getNextId() {
        return nextId.incrementAndGet();
    }
}
