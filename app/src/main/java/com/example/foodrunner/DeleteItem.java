package com.example.foodrunner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodrunner.CatManagement.Activity.ProductList;
import com.example.foodrunner.CatManagement.Model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DeleteItem extends AppCompatActivity {

    TextView textViewName , textViewPrice;
    Button btnDel;
    DatabaseReference dbRef;
    StorageReference storageReference;
    ListView listViewProducts;


    List<Products> productsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        listViewProducts= findViewById(R.id.listViewProducts);
        productsList = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference("Products");
        storageReference = FirebaseStorage.getInstance().getReference("Products");//test




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

                ProductList adapter = new ProductList(DeleteItem.this,productsList);
                listViewProducts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}