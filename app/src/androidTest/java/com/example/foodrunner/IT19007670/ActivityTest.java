package com.example.foodrunner.IT19007670;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.foodrunner.CatManagement.Activity.AddItem;
import com.example.foodrunner.CatManagement.Activity.BreakfastAdmin;
import com.example.foodrunner.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;


public class ActivityTest {

    @Rule
    public ActivityTestRule<AddItem> mActivityTestRule = new ActivityTestRule<AddItem>(AddItem.class);

    private AddItem mAddItem=null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(BreakfastAdmin.class.getName(),null,false);

    @Before
    public void setUp() throws Exception{
        mAddItem=mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchActivity(){
        assertNotNull(mAddItem.findViewById(R.id.btnAdd));
        onView(withId(R.id.btnNext)).perform(click());
        Activity breakfastAdmin = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(breakfastAdmin);
        breakfastAdmin.finish();
    }
    @After
    public void tearDown() throws Exception{
        mAddItem=null;
    }
}
