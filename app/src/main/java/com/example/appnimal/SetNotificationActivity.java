package com.example.appnimal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SetNotificationActivity extends AppCompatActivity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        test = findViewById(R.id.test);

        if(b!=null)
        {
            String j = (String) b.get("date");
            test.setText(j);
        }
    }
}