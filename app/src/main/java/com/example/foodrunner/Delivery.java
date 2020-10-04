package com.example.foodrunner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Delivery extends AppCompatActivity implements View.OnClickListener {

    //Initialize values
    Button btnDatePicker, btnTimePicker,btnPicker,viewLoc,submitBtn;
    TextView tvg;
    int PLACE_PICKER_REQUEST = 1;
    EditText dateIn, timeIn,editAddress;
    TextView addressView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Dialog myDialog;
    FirebaseDatabase db;
    DatabaseReference dbRef;
    DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        //Assign values
        btnDatePicker = (Button) findViewById(R.id.btnDatePicker);
        btnTimePicker = (Button) findViewById(R.id.btnTimePicker);
        dateIn = findViewById(R.id.date_in);
        timeIn =  findViewById(R.id.time_in);
        addressView = (TextView) findViewById(R.id.addressView);
        btnPicker = (Button) findViewById(R.id.btnPicker);
        tvg = (TextView) findViewById(R.id.tvg);
        viewLoc =(Button) findViewById(R.id.viewLoc);
        submitBtn =findViewById(R.id.submitBtn);


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        myDialog = new Dialog(this);

        //Location picker onclick
        btnPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(Delivery.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }

            }
        });

        final Pick pck=new Pick();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("User").child("1");
                pck.setDate(dateIn.getText().toString().trim());
                pck.setTime(timeIn.getText().toString().trim());


                dbRef.child("1").setValue(pck);

            }
        });

        //Retrieve Address from the database
        viewLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("User").child("1");
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String location=snapshot.child("address").getValue().toString();

                        addressView.setText(location);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
    //End of onCreate method

    //Location picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);{
            if(requestCode == PLACE_PICKER_REQUEST){
                if(resultCode == RESULT_OK){
                    Place place = PlacePicker.getPlace(data,Delivery.this);
                    StringBuilder stringBuilder = new StringBuilder();
                    String latitude = String.valueOf(place.getLatLng().latitude);
                    String longitude = String.valueOf(place.getLatLng().longitude);
                    stringBuilder.append("LATITUDE :");
                    stringBuilder.append(latitude);
                    stringBuilder.append("\n");
                    stringBuilder.append("LONGITUDE :");
                    stringBuilder.append(longitude);
                    tvg.setText(stringBuilder.toString());

                }
            }
        }
    }

    //Date & Time picker
    @Override
        public void onClick(View v) {

            if (v == btnDatePicker) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                //Launch Date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateIn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
            if (v == btnTimePicker) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeIn.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }


        //Popup box
     /*   public void ShowPopup(View v) {
            TextView txtclose;
            Button btnFollow;
            myDialog.setContentView(R.layout.custompopup);
            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            txtclose.setText("M");
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }*/


        















}
//End of class


