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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPhone2;
    private EditText etPassword2;
    private EditText etAddress;
    private Button btRegister1;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPhone2 = findViewById(R.id.etPhone2);
        etPassword2 = findViewById(R.id.etPassword2);
        btRegister1 = findViewById(R.id.btRegister1);
        etAddress = findViewById(R.id.etAddress);
        loading = new ProgressDialog(this);

        btRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }

        });
    }

    private void CreateNewAccount() {
        final String email,phoneNumber,address,password;
        email=etEmail.getText().toString();
        phoneNumber=etPhone2.getText().toString();
        address=etAddress.getText().toString();
        password=etPassword2.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneNumber)||TextUtils.isEmpty(address)||TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please fill full all your information",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading.setTitle("Creating New Account");
            loading.setCanceledOnTouchOutside(true);
            loading.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    SendUserToMain();
                                    Toast.makeText(Register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                    UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                    UserRef.child("emailUser").setValue(email);
                                    UserRef.child("phoneNumber").setValue(phoneNumber);
                                    UserRef.child("address").setValue(address);
                                    loading.dismiss();
                                }
                                else{
                                    String message = task.getException().toString();
                                    Toast.makeText(Register.this, "Error  "+ message, Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
        }
    }


    private void SendUserToMain() {
        Intent mainIntent = new Intent(Register.this,MainActivity.class);
        startActivity(mainIntent);
    }
}
