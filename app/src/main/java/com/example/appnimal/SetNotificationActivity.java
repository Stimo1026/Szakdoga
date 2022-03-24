package com.example.appnimal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appnimal.databinding.ActivitySetNotificationBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

public class SetNotificationActivity extends AppCompatActivity {

    private TextView test;
    private Button timeButton;
    private Button setNotiButton;
    private TextInputEditText notiText;
    int hour, minute;
    private AlarmManager alarmManager;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification);
        setNotiButton = findViewById(R.id.setNotiButton);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        test = findViewById(R.id.test);
        timeButton = findViewById(R.id.timeButton);
        notiText = findViewById(R.id.notiText);


        creatChannel();
        if (b != null) {
            String text = (String) b.get("date");

            test.setText(text);
        }
    }

    public void setAlarm(View view) {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if(timeButton.getText().equals("SELECT TIME")){
            Toast.makeText(SetNotificationActivity.this, "Select a time first!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(notiText.getText().equals("")){
            Toast.makeText(SetNotificationActivity.this, "Set a text first'", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SetNotificationActivity.this,
                0, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        Toast.makeText(SetNotificationActivity.this, "Alarm set!", Toast.LENGTH_SHORT).show();
    }

    private void creatChannel() {
        CharSequence name = "AppnimalChannel";
        String desc = "Channel for Alarm Manager";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("Appnimal", name, importance);
        channel.setDescription(desc);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format((Locale.getDefault()), "%02d:%02d", hour, minute));

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
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