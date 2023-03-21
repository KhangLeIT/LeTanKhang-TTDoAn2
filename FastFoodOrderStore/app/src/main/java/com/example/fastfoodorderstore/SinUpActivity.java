package com.example.fastfoodorderstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SinUpActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword1;
    private EditText editPassword2;
    private Button buttonSignUp;
    private TextView textHaveAccount;

    // 1 Bien chung thuc
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_up);


        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.editEmail);
        editPassword1 = findViewById(R.id.editPassword1);
        editPassword2 = findViewById(R.id.editPassword2);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textHaveAccount = findViewById(R.id.textHaveAccount);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(
                        editEmail.getText().toString(),
                        editPassword1.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Sign up successfully",
                                    Toast.LENGTH_LONG
                            ).show();

                            // Luu csdl
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference refAccount =  database
                                    .getReference()
                                    .child("account");
                            Acount account = new Acount(editEmail.getText().toString(),
                                    "","","",""
                            );
                            refAccount
                                    .child(mAuth.getCurrentUser().getUid())
                                    .setValue(account);

                            Intent intent = new Intent(
                                    SinUpActivity.this,
                                    LoginActivity.class
                            );
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
        textHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        SinUpActivity.this,
                        LoginActivity.class
                );
                startActivity(intent);
            }
        });
    }
}