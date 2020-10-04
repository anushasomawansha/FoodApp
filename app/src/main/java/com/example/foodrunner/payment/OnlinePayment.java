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

public class OnlinePayment extends AppCompatActivity {

    EditText name1,cardnum1,expdate1,cvv1;
    Button btnPAY;

    DatabaseReference databaseOrders;
    Orders mem;

    //method to clear all user inputs
    private void clearControls(){
        name1.setText("");
        cardnum1.setText("");
        expdate1.setText("");
        cvv1.setText("");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);



        name1 =findViewById(R.id.name1);
        cardnum1=findViewById(R.id.cardnum1);
        expdate1=findViewById(R.id.expdate1);
        cvv1=findViewById(R.id.cvv1);
        btnPAY=findViewById(R.id.btnPAY);

        databaseOrders = FirebaseDatabase.getInstance().getReference().child("Orders");

        btnPAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrders();
            }
        });

    }

    private void addOrders(){
        String name = name1.getText().toString().trim();
        String cardnumber = cardnum1.getText().toString().trim();
        String expdate = expdate1.getText().toString().trim();
        String cvv = cvv1.getText().toString().trim();

        if(TextUtils.isEmpty(name1.getText().toString())) {
            Toast.makeText(OnlinePayment.this, "Enter name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(cardnum1.getText().toString())){
            Toast.makeText(OnlinePayment.this,"Enter card number",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(expdate1.getText().toString())){
            Toast.makeText(OnlinePayment.this,"Enter expiary date",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(cvv1.getText().toString())){
            Toast.makeText(OnlinePayment.this,"Enter CVV number",Toast.LENGTH_SHORT).show();
        }


        else{
            String id = databaseOrders.push().getKey();
            Orders mem = new Orders(id,name,cardnumber,expdate,cvv);
            databaseOrders.push().setValue(mem);
            Toast.makeText(this,"Order Complete!!",Toast.LENGTH_LONG).show();
        }









    }

}