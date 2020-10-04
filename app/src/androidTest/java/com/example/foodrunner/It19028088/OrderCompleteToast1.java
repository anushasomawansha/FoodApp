package com.example.foodrunner.It19028088;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.foodrunner.payment.OnlinePayment;
import com.example.foodrunner.payment.payondelivery;

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
public class OrderCompleteToast1 {

    @Rule
    public ActivityTestRule<payondelivery> submitToast1 =new ActivityTestRule(payondelivery.class);

    @Before
    public void setUp() throws Exception{

    }
    @Test
    public void testPaymentToast(){
        onView(withText("Order Complete!!"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception{

    }
}
