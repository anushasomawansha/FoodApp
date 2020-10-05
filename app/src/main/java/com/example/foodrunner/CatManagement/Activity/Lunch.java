package com.example.foodrunner.CatManagement.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodrunner.CartMainActivity;
import com.example.foodrunner.CatManagement.Model.Products;
import com.example.foodrunner.Delivery;
import com.example.foodrunner.MainActivity;
import com.example.foodrunner.R;
import com.example.foodrunner.SignupActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lunch extends AppCompatActivity {
    ListView listViewProducts;
    Button btnBreakfast,btnLunch,btnDinner;
    Query dbRef;
    List<Products> productsList;
    private ProgressBar mProgressCircle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        dbRef = FirebaseDatabase.getInstance().getReference("Products").orderByChild("category").equalTo("Lunch");

        listViewProducts = findViewById(R.id.listViewProducts);
        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch=findViewById(R.id.btnLunch);
        btnDinner=findViewById(R.id.btnDinner);
        mProgressCircle=findViewById(R.id.progressCircle);
        productsList = new ArrayList<>();

        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lunch.this,Breakfast.class);
                startActivity(intent);
            }
        });

        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lunch.this,Lunch.class);
                startActivity(intent);
            }
        });

        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lunch.this,Dinner.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(),Breakfast.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.location:
                        startActivity(new Intent(getApplicationContext(), Delivery.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsList.clear();
                for(DataSnapshot productSnapshot: snapshot.getChildren()){
                    Products products = productSnapshot.getValue(Products.class);

                    productsList.add(products);

                }
                mProgressCircle.setVisibility(View.INVISIBLE);
                ProductList adapter = new ProductList(Lunch.this,productsList);
                listViewProducts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Lunch.this, error.getMessage() , Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);

            }
        });
    }
}