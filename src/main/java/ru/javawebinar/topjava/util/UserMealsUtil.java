package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> daysWithExcess = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            daysWithExcess.merge(mealDate, meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                LocalDate mealDate = meal.getDateTime().toLocalDate();
                boolean exceed = daysWithExcess.get(mealDate) > caloriesPerDay;
                mealsWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
            }
        }
        return mealsWithExcess;
    }

    public static Map<LocalDate, Integer> caloriesMap = new HashMap<>();

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        caloriesMap.clear();
        meals.forEach(elt -> caloriesMap.merge(elt.getDateTime().toLocalDate(), elt.getCalories(), Integer::sum));
        List<UserMealWithExcess> result = new ArrayList<>();
        meals.stream().filter(elt -> TimeUtil.isBetweenHalfOpen(elt.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(elt -> result.add(
                        new UserMealWithExcess(elt.getDateTime(), elt.getDescription(), elt.getCalories(),
                                caloriesMap.getOrDefault(elt.getDateTime().toLocalDate(), 0) > caloriesPerDay)));
        return result;
    }

}
