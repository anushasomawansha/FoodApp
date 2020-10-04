package com.example.foodrunner.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.foodrunner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listViewOnline extends AppCompatActivity {

    ListView myListView;
    List<Orders> ordersList;

    DatabaseReference orderDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_online);

        myListView = findViewById(R.id.myListView);
        ordersList = new ArrayList<>();

        orderDB = FirebaseDatabase.getInstance().getReference("Orders");

        orderDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersList.clear();


                for (DataSnapshot orderDataSnap : snapshot.getChildren()) {

                    Orders orders = orderDataSnap.getValue(Orders.class);
                    ordersList.add(orders);
                }

                listadapter adapter = new listadapter(listViewOnline.this,ordersList);
                 myListView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
}}