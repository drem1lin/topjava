package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.hasUserId() && !meal.getUserId().equals(userId))
            throw new NotFoundException("wrong user" + userId + " for meal " + meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (!repository.get(id).getUserId().equals(userId))
            throw new NotFoundException("you cannot delete not your meal");
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        if (!repository.get(id).getUserId().equals(userId))
            throw new NotFoundException("you cannot get not your meal");
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values()
                .stream()
                .filter(m -> m.getUserId().equals(userId))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());
    }
}

