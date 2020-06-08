package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAO {
    private static volatile MealDAO instance;
    private static final Map<Integer, Meal> empMap = new ConcurrentHashMap<>();
    private static AtomicInteger nextId = new AtomicInteger(0);

    private MealDAO()
    {

        addMeal(new Meal(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500));
        addMeal(new Meal(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000));
        addMeal(new Meal(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500));
        addMeal(new Meal(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение", 100));
        addMeal(new Meal(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                        "Завтрак", 1000));
        addMeal(new Meal(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                        "Обед", 500));
        addMeal(new Meal(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                    "Ужин", 410));
    }

    public static MealDAO getInstance() {
        MealDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (MealDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MealDAO();
                }
            }
        }
        return localInstance;
    }

    public static Meal getMeal(Integer empNo) {
        return empMap.getOrDefault(empNo, new Meal(0, LocalDateTime.now(), "Некорректный номер приема пищи", 0));
    }

    public static Meal addMeal(Meal emp) {
        empMap.put(emp.getId(), emp);
        return emp;
    }

    public static Meal updateMeal(Meal emp) {
        empMap.put(emp.getId(), emp);
        return emp;
    }

    public static void deleteMeal(Integer mealId) {
        empMap.remove(mealId);
    }

    public static List<MealTo> getAllMealTo() {
        return MealsUtil.filteredByStreams(empMap.values(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    public static Integer getNextId() {
        return nextId.incrementAndGet();
    }
}
