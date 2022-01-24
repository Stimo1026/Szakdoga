package com.example.appnimal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;
    private FirebaseAuth auth;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordAgainEditText);

        auth = FirebaseAuth.getInstance();
    }

    private void startAppnimal() {
        Intent intent = new Intent(this, AppnimalActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }

    public void register(View view) {

        String username = userNameEditText.getText().toString();
        String email = userEmailEditText.getText().toString();
        String pw = passwordEditText.getText().toString();
        String pwConfirm = passwordConfirmEditText.getText().toString();

        if (!pw.equals(pwConfirm)) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG);
            mToast.show();
        }
        if (username.equals("") || email.equals("") || pw.equals("") || pwConfirm.equals("")) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(RegisterActivity.this, "Something is missing!", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        storeNewUser();
                        //Log.d(LOG_TAG, "User created successfully");
                        startAppnimal();
                    } else {
                        //Log.d(LOG_TAG, "User was't created successfully:", task.getException());
                        if (mToast != null) {
                            mToast.cancel();
                        }
                        mToast = Toast.makeText(RegisterActivity.this, "Error: creating user", Toast.LENGTH_LONG);
                        mToast.show();
                    }
                }
            });
        }
    }

    private void storeNewUser() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("User");

        reference.setValue("First record!");

    }
}

