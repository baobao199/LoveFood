package com.example.lovefood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    private EditText etEmail1;
    private EditText etPassword1;
    private Button btLogin1,btRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail1 = findViewById(R.id.etEmail1);
        mAuth=FirebaseAuth.getInstance();
        etPassword1 = findViewById(R.id.etPassword1);
        btLogin1 = findViewById(R.id.btLogin1);
        btRegister = findViewById(R.id.btRegister);
        loading = new ProgressDialog(this);
        currentUser = mAuth.getCurrentUser();

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegister();
            }
        });
        btLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowLogin();
            }
        });
    }

    private void AllowLogin() {
        String email,password;
        email=etEmail1.getText().toString();
        password=etPassword1.getText().toString();

        if(TextUtils.isEmpty(email) ||TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please fill full all your information",Toast.LENGTH_SHORT).show();
        }
        else{

            loading.setTitle("Waiting....");
            loading.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            SendUserToMain();
                            Toast.makeText(LogIn.this, "Logged in Successful...", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                        else{
                            String message = task.getException().toString();
                            Toast.makeText(LogIn.this, "Error  "+ message, Toast.LENGTH_SHORT).show();
                        }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null)
        {
            SendUserToMain();
        }
    }

    private void SendUserToMain() {
        Intent mainIntent = new Intent(LogIn.this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void SendUserToRegister() {
        Intent registerIntent = new Intent(LogIn.this, Register.class);
        startActivity(registerIntent);
    }
}
