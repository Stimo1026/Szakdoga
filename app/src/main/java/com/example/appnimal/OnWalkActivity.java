package com.example.appnimal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class OnWalkActivity extends AppCompatActivity implements SensorEventListener {

    TextView pet_tv;
    TextView steps_tv;
    TextView time_tv;
    TextView meters_tv;
    SensorManager sensorManager;
    Sensor stepDetector;
    int stepCount = 0;
    Timer timer;
    double time = 0.0;
    TimerTask timerTask;
    private CollectionReference cRef;
    private FirebaseFirestore mFirestore;
    private User currUser;
    private String id;
    private FirebaseAuth auth;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_walk);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        stepCount = 0;
        pet_tv = findViewById(R.id.pet_name);
        steps_tv = findViewById(R.id.steps_took);
        time_tv = findViewById(R.id.time_spent);
        meters_tv = findViewById(R.id.meters_walked);

        if (b != null) {
            pet_tv.setText((String) b.get("petName"));
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        }
        timer = new Timer();
        startTimer();

        mFirestore = FirebaseFirestore.getInstance();
        cRef = mFirestore.collection("Useres");
        auth = FirebaseAuth.getInstance();

        getUserInfo();
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        time_tv.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }


    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    @Override
    protected void onResume() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorManager.unregisterListener(this, stepDetector);
        }
        super.onPause();
    }

    private void getUserInfo() {
        cRef.whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                currUser = doc.toObject(User.class);
                id = doc.getId();
            }

        });
    }

    public void stopWalak(View view) {
        int steps;
        int length;
        String date;
        String petName;
        String duration;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        date = dtf.format(now);
        duration = time_tv.getText().toString();
        petName = pet_tv.getText().toString();
        steps = Integer.parseInt(steps_tv.getText().toString());
        length = Integer.parseInt(meters_tv.getText().toString());

        Walk walk = new Walk(date,petName,duration,steps,length);
        WalksActivity.walks.add(walk);
        cRef.document(id).update("walks", WalksActivity.walks);


        Intent intent = new Intent(this, WalksActivity.class);
        startActivity(intent);
        finish();
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, WalksActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == stepDetector) {
            stepCount = (int) (stepCount + event.values[0]);
            steps_tv.setText(String.valueOf(stepCount));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}