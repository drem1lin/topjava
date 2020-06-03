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
        HashMap<LocalDate, Integer> daysWithExcess = new HashMap<>();
        for (UserMeal meal : meals)
        {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            if(!daysWithExcess.containsKey(mealDate))
                daysWithExcess.put(mealDate, meal.getCalories());
            else
                daysWithExcess.put(mealDate, daysWithExcess.get(mealDate) + meal.getCalories());
        }


        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals)
        {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if(isInTimeInterval(mealTime, startTime, endTime))
            {
                LocalDate mealDate = meal.getDateTime().toLocalDate();
                boolean exceed = daysWithExcess.get(mealDate) > caloriesPerDay;
                mealsWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed));
            }
        }
        return mealsWithExcess;
    }

    public static Map<LocalDate, Integer> caloriesMap = new HashMap<>();

    public static void addToMap(LocalDate mealDate, int calories)
    {
        if(!caloriesMap.containsKey(mealDate))
            caloriesMap.put(mealDate, calories);
        else
            caloriesMap.put(mealDate, caloriesMap.get(mealDate) + calories);
    }

    public static boolean isInTimeInterval(LocalTime mealTime, LocalTime startTime, LocalTime endTime)
    {
        return startTime.compareTo(mealTime)<0 && mealTime.compareTo(endTime)<0;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        caloriesMap.clear();
        meals.forEach(elt -> addToMap(elt.getDateTime().toLocalDate(), elt.getCalories()));
        List<UserMealWithExcess> result = new ArrayList<>();
        meals.stream().filter(elt -> isInTimeInterval(elt.getDateTime().toLocalTime(), startTime, endTime))
               .forEach(elt -> result.add(
                        new UserMealWithExcess(elt.getDateTime(), elt.getDescription(), elt.getCalories(),
                                caloriesMap.get(elt.getDateTime().toLocalDate()) > caloriesPerDay)));
        return result;
    }

}
