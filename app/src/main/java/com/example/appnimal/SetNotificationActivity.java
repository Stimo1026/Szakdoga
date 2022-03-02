package com.example.appnimal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.appnimal.databinding.ActivitySetNotificationBinding;

import java.util.Locale;

public class SetNotificationActivity extends AppCompatActivity {

    private TextView test;
    private Button timeButton;
    int hour, minute;
    private ActivitySetNotificationBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification);

        binding = ActivitySetNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        test = findViewById(R.id.test);
        timeButton = findViewById(R.id.timeButton);

        if (b != null) {
            String text = (String) b.get("date");

            test.setText(text);
        }
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format((Locale.getDefault()), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void cancel(View view) {
        finish();
    }
}