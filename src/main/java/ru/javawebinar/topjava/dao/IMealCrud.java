package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public interface IMealCrud {
    Meal get(Integer id);

    Meal add(LocalDateTime dateTime, String description, Integer calories);

    Meal update(Integer id, LocalDateTime dateTime, String description, Integer calories);

    void delete(Integer mealId);
}
