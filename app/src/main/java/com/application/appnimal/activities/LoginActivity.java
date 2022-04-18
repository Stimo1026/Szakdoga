package com.application.appnimal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appnimal.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private GoogleSignInClient googlesignin;
    private FirebaseAuth auth;
    EditText editTextTextPersonName;
    EditText editTextTextPassword;
    private static final int RC_SIGN_IN = 123;
    private Toast mToast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //this is a parsing error, works like this
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googlesignin = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException ignored) {
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        // google login auth
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in success, update UI with the signed-in user's information
                            startAppnimal();
                        } else {
                            if (mToast != null) mToast.cancel();
                            // if sign in fails, display a message to the user.
                            mToast = Toast.makeText(LoginActivity.this, "Sign in with Google failed!", Toast.LENGTH_LONG);
                            mToast.show();
                        }
                    }
                });
    }

    private void startAppnimal() {
        // starts activity
        Intent intent = new Intent(this, AppnimalActivity.class);
        startActivity(intent);
    }

    public void loginWithGoogle(View view){
        Intent signInIntent = googlesignin.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void login(View view){
        // login checks password and name
        String uname = editTextTextPersonName.getText().toString();
        String pw = editTextTextPassword.getText().toString();

        if(pw.equals("") || uname.equals("")){
            if (mToast != null) {mToast.cancel();}
            mToast = Toast.makeText(LoginActivity.this, "Something is missing!", Toast.LENGTH_LONG);
            mToast.show();
        }else{
            auth.signInWithEmailAndPassword(uname, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startAppnimal();
                    } else {
                        if (mToast != null) mToast.cancel();
                        mToast = Toast.makeText(LoginActivity.this, "Invalid email or password!", Toast.LENGTH_LONG);
                        mToast.show();
                    }
                }
            });
        }

    }
    public void cancel(View view){
        finish();
    }
}