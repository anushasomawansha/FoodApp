package com.example.foodrunner.IT19028460;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.foodrunner.CartManagement.Model.ui.CreateShoppingList;
import com.example.foodrunner.CartManagement.Model.ui.ListShoppingList;
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
    public ActivityTestRule<ListShoppingList> submitToast =new ActivityTestRule(ListShoppingList.class);

    private ListShoppingList list=null;

    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(CreateShoppingList.class.getName(),null,false);

    @Before
    public void setUp() throws Exception{

        list=submitToast.getActivity();
    }
    @Test
    public void testActivityLauncher(){
        assertNotNull(list.findViewById(R.id.addButton));
        onView(withId(R.id.addButton)).perform(click());
        Activity createShoppingList=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(createShoppingList);
        createShoppingList.finish();
    }
    @After
    public void tearDown() throws Exception{
        list=null;
    }

}
