package com.example.foodrunner.IT19007670;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import com.example.foodrunner.CatManagement.Activity.BreakfastAdmin;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UpdateTest {

    @Rule
    public ActivityTestRule<BreakfastAdmin> addItemActivityTestRule = new ActivityTestRule(BreakfastAdmin.class);

    @Before
    public void setUp() throws Exception{

    }


    @Test
    public void updateItemTest(){
        onView(withText("Product Updated"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception{

    }

}
