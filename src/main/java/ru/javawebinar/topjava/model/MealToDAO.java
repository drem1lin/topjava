package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealToDAO {
    private static volatile MealToDAO instance;
    private static final Map<Integer, MealTo> empMap = new ConcurrentHashMap<>();
    private static AtomicInteger nextId = new AtomicInteger(1);

    private MealToDAO()
    {

        addMealTo(new MealTo( getNextId(),LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500, false));
        addMealTo(new MealTo(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000, false));
        addMealTo(new MealTo(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500, false));
        addMealTo(new MealTo(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение", 100, true));
        addMealTo(new MealTo(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                        "Завтрак", 1000, true));
        addMealTo(new MealTo(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                        "Обед", 500, true));
        addMealTo(new MealTo(getNextId(),LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                    "Ужин", 410, true));
    }

    public static MealToDAO getInstance() {
        MealToDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (MealToDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MealToDAO();
                }
            }
        }
        return localInstance;
    }

    public static MealTo getMealTo(Integer empNo) {
        return empMap.getOrDefault(empNo, new MealTo(0, LocalDateTime.now(), "Некорректный номер приема пищи", 0, false));
    }

    public static MealTo addMealTo(MealTo emp) {
        nextId.incrementAndGet();
        empMap.put(emp.getId(), emp);
        return emp;
    }

    public static MealTo updateMealTo(MealTo emp) {
        empMap.put(emp.getId(), emp);
        return emp;
    }

    public static void deleteMealTo(Integer mealId) {
        empMap.remove(mealId);
    }

    public static List<MealTo> getAllMealTo() {
        Collection<MealTo> c = empMap.values();
        List<MealTo> list = new ArrayList<>(c);
        return list;
    }

    public static Integer getNextId() {
        return nextId.get();
    }
}
