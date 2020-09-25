package com.example.foodrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodrunner.ui.ListShoppingList;

import java.util.UUID;

public class CartMainActivity extends AppCompatActivity {

    Handler handler; //to create splash screen
    private static String uniqueID =null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_main);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(CartMainActivity.this, ListShoppingList.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    public synchronized static String getSubscriberId(Context context)
    {
        if(uniqueID==null)
        {
            SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_UNIQUE_ID,Context.MODE_PRIVATE);
            uniqueID=sharedPreferences.getString(PREF_UNIQUE_ID,null);
            if(uniqueID==null)
            {
                uniqueID="subscriber"+ UUID.randomUUID().toString();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(PREF_UNIQUE_ID,uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }
}