package com.example.appnimal;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AppnimalActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth auth;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appnimal_main);

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.toolbar);

        auth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setStatusBarcolor();

        user = FirebaseAuth.getInstance().getCurrentUser();

        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

        navigationView.setCheckedItem(R.id.nav_home);
        storeNewUser();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_calendar:
                openCalendar();
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
            makeToast("Log out succesfull!");

        }

    }

    private void signOut() {
        auth.signOut();
        finish();
        makeToast("Log out succesfull!");
    }

    private void openCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
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

    private void openPets() {
        Intent intent = new Intent(this, PetsActivity.class);
        startActivity(intent);
        finish();
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
        }
    }

    private void storeNewUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }

}