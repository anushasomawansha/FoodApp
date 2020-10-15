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
import com.example.foodrunner.LoginActivity;
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

public class Breakfast extends AppCompatActivity {

    ListView listViewProducts;
    Button btnBreakfast,btnLunch,btnDinner,btnAddtoCart;
    private ProgressBar mProgressCircle;
    Query dbRef;
    List<Products> productsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        dbRef = FirebaseDatabase.getInstance().getReference("Products").orderByChild("category").equalTo("Breakfast");


        listViewProducts = findViewById(R.id.listViewProducts);
        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch=findViewById(R.id.btnLunch);
        btnDinner=findViewById(R.id.btnDinner);
        productsList = new ArrayList<>();
        btnAddtoCart = listViewProducts.findViewById(R.id.btnDel);
        mProgressCircle=findViewById(R.id.progressCircle);

        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Breakfast.this,Breakfast.class);
                startActivity(intent);
            }
        });

        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Breakfast.this,Lunch.class);
                startActivity(intent);
            }
        });

        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Breakfast.this,Dinner.class);
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
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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

                ProductList adapter = new ProductList(Breakfast.this,productsList);
                listViewProducts.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Breakfast.this, error.getMessage() , Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }






}