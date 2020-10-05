package com.example.foodrunner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodrunner.CatManagement.Activity.Breakfast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText editEmail, editName, editMobile, editLocation, editPwd;
    private Button signUpBtn,loginBtn;
    private ProgressBar progressBar;
    private String email;
    private String name;
    private String mobileNo;
    private String location;
    private String password;
    private User user;
    ProgressBar progressbar;
    private FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editEmail = findViewById(R.id.editEmail);
        editName = findViewById(R.id.editName);
        editMobile = findViewById(R.id.editMobile);
        editLocation = findViewById(R.id.editLocation);
        editPwd = findViewById(R.id.editPwd);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);

        fAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String email = editEmail.getText().toString().trim();
                String password = editPwd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editPwd.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    editPwd.setError("Password Must be more than 6 Characters.");
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Breakfast.class));
                        } else {
                            Toast.makeText(SignupActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }
}
