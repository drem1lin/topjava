package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealToTestData {
    public static TestMatcher<MealTo> MEAL_TO_MATCHER = TestMatcher.usingFieldsComparator(MealTo.class);

    public static final List<MealTo> MEALS_TO = MealsUtil.getTos(MealTestData.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY);

    public static final int MEAL1_ID = START_SEQ + 2;
    public static final MealTo MEAL_TO1 = new MealTo(MEAL1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false);
}
