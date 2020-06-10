package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;

public interface IMealCrud {
    Meal get(Integer id);

    Meal add(Meal meal);

    Meal update(Meal meal);

    void delete(Integer mealId);

    Collection<Meal> getAll();
}
