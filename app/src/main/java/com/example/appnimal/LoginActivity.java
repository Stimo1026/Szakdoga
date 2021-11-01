package com.example.appnimal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText editTextTextPersonName;
    EditText editTextTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

    }

    private void startAppnimal() {
        Intent intent = new Intent(this, AppnimalActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        String uname = editTextTextPersonName.getText().toString();
        String pw = editTextTextPassword.getText().toString();


        auth.signInWithEmailAndPassword(uname, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startAppnimal();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void cancel(View view){
        finish();
    }
}