package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

public class ValidationUtil {

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void checkNewMeal(Meal meal) {
        if (meal.hasUserId()) {
            throw new IllegalArgumentException(meal + " must be new (id=null)");
        }
    }


    public static void checkUser(Meal meal, Integer userId) {
        if (!meal.getUserId().equals(userId)){
            throw new IllegalArgumentException(meal.toString() + "must be yours");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static void assureUserIdConsistent(Meal meal, int userId) {
        if (meal.getUserId() != userId) {
            throw new IllegalArgumentException(meal + " must be with userId=" + userId);
        }
    }

    public static void assureMealIdConsistent(Meal meal, int mealId) {
        if (meal.getId() != mealId) {
            throw new IllegalArgumentException(meal + " must be with mealId=" + mealId);
        }
    }
}