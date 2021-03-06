package com.application.appnimal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.example.appnimal.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRegist(View view){
        // opens register with animation
        Intent intent = new Intent(this, RegisterActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.register),"transition_register");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
        startActivity(intent,options.toBundle());
    }

    public  void openLogin(View view){
        // opens login with animation
        Intent intent = new Intent(this, LoginActivity.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View, String>(findViewById(R.id.login),"transition_login");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

        startActivity(intent,options.toBundle());

    }
}