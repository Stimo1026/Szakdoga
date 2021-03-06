package com.application.appnimal.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.application.appnimal.adapters.CalendarAdapter;
import com.example.appnimal.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemlistener {

    TextView monthYearText;
    RecyclerView calendarRecycleView;
    LocalDate selectedDate;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //hooks
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        calendarRecycleView = findViewById(R.id.CalendarRecycleView);
        monthYearText = findViewById(R.id.MonthYearTextView);

        // sets the current date
        selectedDate = LocalDate.now();
        setMonthView();

        // menu
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_calendar);
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

    }

    private void setMonthView() {
        // sets the days of month in the layout
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonth(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonth(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        // selects the month from days and gets the length of it
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstofMonth = selectedDate.withDayOfMonth(1);
        // gets the first day of a month
        int dayOfWeek = firstofMonth.getDayOfWeek().getValue();
        for(int i = 1; i <= 42; i++){
            // fills the empty cells
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                // there is no day in cell
                daysInMonthArray.add("");
            }
            else{
                // there is a day in cell
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date){
        // simple formatter for the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void prevMonth(View view){
        // called from layout button back
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }
    public void nextMonth(View view){
        // called from layout button forward
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_home:
                openHome();
                break;

            case R.id.nav_calendar:
                break;

            case R.id.nav_walk:
                openWalks();
                break;

            case R.id.nav_profile:
                openProfile();
                break;

            case R.id.nav_settings:
                openSettings();
                break;

            case R.id.nav_pets:
                openPets();
                break;

            case R.id.nav_logout:
                signOut();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            auth.signOut();
            Toast.makeText(this, "Log out succesfull!", Toast.LENGTH_LONG).show();

        }

    }
    private void openPets() {
        Intent intent = new Intent(this, PetsActivity.class);
        startActivity(intent);
        finish();

    }

    private void signOut() {
        auth.signOut();
        finish();
        Toast.makeText(this, "Log out succesfull!", Toast.LENGTH_LONG).show();
    }

    private void openCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
        finish();
    }

    private void openHome() {
        Intent intent = new Intent(this, AppnimalActivity.class);
        startActivity(intent);
        finish();
    }

    private void openWalks() {
        Intent intent = new Intent(this, WalksActivity.class);
        startActivity(intent);
        finish();
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    private void openProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemClick(int position, String daytext) {
        // if we click a cell open a new activity with current date
        if(!daytext.equals("")){
            Intent intent = new Intent(this, SetNotificationActivity.class);
            intent.putExtra("date", daytext + ". " + monthYearFromDate(selectedDate));
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedDay", daytext);
            startActivity(intent);
        }
    }
}