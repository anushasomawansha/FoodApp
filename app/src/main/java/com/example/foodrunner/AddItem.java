package com.example.foodrunner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItem extends AppCompatActivity {
    Button btnAdd , btnDel;
    EditText etName , etPrice ,etCategory;
    DatabaseReference dbRef;

    public void clearControls(){
        etName.setText("");
        etPrice.setText("");
        etCategory.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        btnDel = findViewById(R.id.btnDel);
        btnAdd = findViewById(R.id.btnAdd);
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etCategory = findViewById(R.id.etCategory);


        dbRef= FirebaseDatabase.getInstance().getReference().child("Products");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = etName.getText().toString();
                Float price = Float.parseFloat(etPrice.getText().toString());
                String category = etCategory.getText().toString();


                String key = dbRef.push().getKey();
                dbRef.child(key).child("name").setValue(name);
                dbRef.child(key).child("price").setValue(price);
                dbRef.child(key).child("category").setValue(category);

               /* products.setName(etName.getText().toString().trim());
                products.setDes(etDes.getText().toString().trim());
                products.setPrice(Float.parseFloat(etPrice.getText().toString().trim()));
               // dbRef.child("pro1").setValue(products);
                dbRef.push().setValue(products);*/


                Intent intent = new Intent(AddItem.this , DeleteItem.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext() , "Saved Successfully" , Toast.LENGTH_SHORT).show();
                clearControls();

            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddItem.this,DeleteItem.class);
                startActivity(intent);
            }
        });
    }
}