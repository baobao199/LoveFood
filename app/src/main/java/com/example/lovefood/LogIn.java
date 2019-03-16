package com.example.lovefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    private EditText etPhone1;
    private EditText etPassword1;
    private Button btLogin1,btRegister;

    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etPhone1 = findViewById(R.id.etPhone1);
        etPassword1 = findViewById(R.id.etPassword1);
        btLogin1 = findViewById(R.id.btLogin1);
        btRegister = findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegister();
            }
        });

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
