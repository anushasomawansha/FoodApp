package com.example.foodrunner;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName , textViewPrice;
    Button btnDelete;
    DatabaseReference dbRef;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewPrice = itemView.findViewById(R.id.textViewPrice);
        btnDelete = itemView.findViewById(R.id.btnDel);


    }
}
