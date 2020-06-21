package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer,Map<Integer,Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        //получим хранилище пользователя
        Map<Integer,Meal> userMeals= repository.get(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if(userMeals == null) { //для пользователя еще нет ни meal ни записи в мапе
                userMeals = new ConcurrentHashMap<>();
            }
            userMeals.put(meal.getId(), meal);
            repository.put(userId, userMeals);
            return meal;
        }

        if(userMeals==null) { // meal старый, а для пользователя нет базы. такого быть не может
            return null;
        }
        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer,Meal> userMeals= repository.get(userId);
        if(userMeals==null){
            return false;
        }
        return userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer,Meal> userMeals= repository.get(userId);
        if(userMeals==null){
            return null;
        }
        return userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer,Meal> userMeals= repository.get(userId);
        if(userMeals==null){
            return null;
        }
        return userMeals.values()
                .stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }
}

