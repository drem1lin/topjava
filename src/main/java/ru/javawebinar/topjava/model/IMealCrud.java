package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public interface IMealCrud {
    Meal getMeal(Integer id);

    Meal addMeal(LocalDateTime dateTime, String description, Integer calories);

    Meal updateMeal(Integer id, LocalDateTime dateTime, String description, Integer calories);

    void deleteMeal(Integer mealId);
}
