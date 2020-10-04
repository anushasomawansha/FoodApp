package com.example.foodrunner.IT19028460;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {
    @Override
    protected boolean matchesSafely(Root item) {
        int type=item.getWindowLayoutParams().get().type;
        if((type== WindowManager.LayoutParams.TYPE_TOAST)){
            IBinder windowsToken= item.getDecorView().getWindowToken();
            IBinder appToken= item.getDecorView().getApplicationWindowToken();
            if(windowsToken==appToken){
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
            description.appendText("Toast Fail");
    }
}
