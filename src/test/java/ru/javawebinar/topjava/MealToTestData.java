package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealToTestData {
    public static TestMatcher<MealTo> MEAL_TO_MATCHER = TestMatcher.usingFieldsComparator(MealTo.class);

    public static final List<MealTo> MEALS_TO = MealsUtil.getTos(MealTestData.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY);
}
