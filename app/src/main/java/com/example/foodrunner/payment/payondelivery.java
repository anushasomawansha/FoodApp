package com.example.foodrunner.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodrunner.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class payondelivery extends AppCompatActivity {

    EditText name2,postalcode2,adress2,nicnumber2,phonenumber2;
    Button btn2;

    DatabaseReference databasedelivery;
    PaymentOrders payO;

    //method to clear all user inputs
    private void clearControls(){
        name2.setText("");
        postalcode2.setText("");
        adress2.setText("");
        nicnumber2.setText("");

        phonenumber2.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payondelivery);

        name2 = findViewById(R.id.name2);
        postalcode2 = findViewById(R.id.postalcode2);
        adress2 = findViewById(R.id.adress2);
        nicnumber2 = findViewById(R.id.nicnumber2);
        phonenumber2 = findViewById(R.id.phonenumber2);
        btn2 = findViewById(R.id.btn2);

        databasedelivery = FirebaseDatabase.getInstance().getReference().child("Orders");

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PaymentOrders();
            }
        });
    }

    private void   PaymentOrders(){
        String userName = name2.getText().toString().trim();
        String postalcode = postalcode2.getText().toString().trim();
        String adress = adress2.getText().toString().trim();
        String nicnumber= nicnumber2.getText().toString().trim();
        String pnumber= phonenumber2.getText().toString().trim();

        if(TextUtils.isEmpty(name2.getText().toString())) {
            Toast.makeText(payondelivery.this, "Enter name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(postalcode2.getText().toString())){
            Toast.makeText(payondelivery.this,"Enter postal code",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(adress2.getText().toString())){
            Toast.makeText(payondelivery.this,"Enter address",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(nicnumber2.getText().toString())){
            Toast.makeText(payondelivery.this,"Enter Nic Number",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(phonenumber2.getText().toString())){
            Toast.makeText(payondelivery.this,"Enter phone number",Toast.LENGTH_SHORT).show();
        }


        else{
            String id = databasedelivery.push().getKey();

            PaymentOrders payO = new PaymentOrders(id,userName,postalcode,adress,nicnumber,pnumber);
            databasedelivery.push().setValue(payO);
            Toast.makeText(this,"Order Complete!!",Toast.LENGTH_LONG).show();
        }





    }


}