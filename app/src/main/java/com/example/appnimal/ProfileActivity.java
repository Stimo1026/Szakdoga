package com.example.appnimal;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextInputEditText fullName;
    private TextInputEditText userName;
    private TextInputEditText email;
    private TextInputEditText password;
    private FirebaseAuth auth;
    private FirebaseFirestore mFirestore;
    private CollectionReference cRef;
    private User currUser;
    private String id;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.fullName);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_profile);
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

        mFirestore = FirebaseFirestore.getInstance();
        cRef = mFirestore.collection("Useres");
        auth = FirebaseAuth.getInstance();
        getUserInfo();
    }

    @Override
    protected void onResume() {
        getUserInfo();
        super.onResume();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_home:
                openHome();
                break;

            case R.id.nav_calendar:
                openCalendar();
                break;

            case R.id.nav_walk:
                openWalks();
                break;

            case R.id.nav_profile:
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

    public void updateUser(View view) {

        String newUserName = userName.getText().toString();
        String newFullName = fullName.getText().toString();
        String newEmail = email.getText().toString();
        String newPassword = password.getText().toString();

        if(newUserName.equals("") || newFullName.equals("") || newEmail.equals("") || newPassword.equals("")){
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(ProfileActivity.this, "Something is missing!", Toast.LENGTH_LONG);
            mToast.show();
        }else{
            cRef.document(id).update("email", newEmail);
            cRef.document(id).update("fullName", newFullName);
            cRef.document(id).update("userName", newUserName);
            cRef.document(id).update("pw", newPassword);
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(ProfileActivity.this, "Updated succesfully!", Toast.LENGTH_LONG);
            mToast.show();

            userName.setText(newUserName);
            fullName.setText(newFullName);
            email.setText(newEmail);
            password.setText(newPassword);

            Objects.requireNonNull(auth.getCurrentUser()).updateEmail(newEmail);
            auth.getCurrentUser().updatePassword(newPassword);
        }
    }


    private void getUserInfo() {
        cRef.whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                currUser = doc.toObject(User.class);
                id = doc.getId();
                userName.setText(currUser.getUserName());
                fullName.setText(currUser.getFullName());
                email.setText(currUser.getEmail());
                password.setText(currUser.getPw());
            }

        });
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


}