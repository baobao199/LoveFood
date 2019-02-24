package com.example.lovefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {
    private EditText etPhone1;
    private EditText etPassword1;
    private Button btLogin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etPhone1 = findViewById(R.id.etPhone1);
        etPassword1 = findViewById(R.id.etPassword1);
        btLogin1 = findViewById(R.id.btLogin1);


    }
}
