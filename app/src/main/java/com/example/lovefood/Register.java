package com.example.lovefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    private EditText etName;
    private EditText etPhone2;
    private EditText etPassword2;
    private Button btRegister1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etPhone2 = findViewById(R.id.etPhone2);
        etPassword2 = findViewById(R.id.etPassword2);
        btRegister1 = findViewById(R.id.btRegister1);
    }
}
