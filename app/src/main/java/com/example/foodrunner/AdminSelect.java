package com.example.foodrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foodrunner.CatManagement.Activity.AddItem;
import com.example.foodrunner.payment.listViewOnline;

public class AdminSelect extends AppCompatActivity {
    Button btnProductAdmin,btnOrderAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select);

        btnProductAdmin = findViewById(R.id.button4);
        btnOrderAdmin=findViewById(R.id.button5);

        btnProductAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSelect.this, AddItem.class);
                startActivity(intent);
            }
        });


        btnOrderAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSelect.this, listViewOnline.class);
                startActivity(intent);
            }
        });
    }
}