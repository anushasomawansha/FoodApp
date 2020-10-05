package com.example.foodrunner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodrunner.CatManagement.Activity.AddItem;
import com.example.foodrunner.CatManagement.Activity.Breakfast;
import com.example.foodrunner.CatManagement.Activity.BreakfastAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editmail,editPwd;
    Button loginBtn,resetBtn,newUserBtn , btnAdminLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editmail=findViewById(R.id.editMobile);
        editPwd=findViewById(R.id.editPwd);
        loginBtn=findViewById(R.id.loginBtn);
        btnAdminLogin=findViewById(R.id.btnAdminLogin);
        newUserBtn=findViewById(R.id.newUserBtn);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editmail.getText().toString().trim();
                String password=editPwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    editmail.setError("UserName is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    editPwd.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    editPwd.setError("Password Must be more than 6 Characters.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login successfully!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Breakfast.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AddItem.class);
                startActivity(intent);
            }
        });
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
    }
}
