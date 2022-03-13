package com.example.appnimal;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class WalksActivity extends AppCompatActivity implements PetsForWalkAdapter.OnPetListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView selectedPetTv;
    private FirebaseAuth auth;
    private CollectionReference cRef;
    private User currUser;
    private Toast mToast;
    private RecyclerView mRecycleView;
    private PetsForWalkAdapter mAdapter;
    private FirebaseFirestore mFirestore;
    private String id;
    private Pet selectedPet = null;
    public static ArrayList<Pet> pets = new ArrayList<Pet>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walks);

        selectedPetTv = findViewById(R.id.selectedPetTv);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();

        if (pets.isEmpty()) {
            selectedPetTv.setText("You have no pets! \n Go add one first.");
        } else {
            if (selectedPet == null) {
                selectedPetTv.setText("No pet selected yet ...");
            }
        }

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_walk);
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

        mFirestore = FirebaseFirestore.getInstance();
        cRef = mFirestore.collection("Useres");
        auth = FirebaseAuth.getInstance();



        getUserInfo();

    }

    private void getUserInfo() {
        cRef.whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                currUser = doc.toObject(User.class);
                id = doc.getId();
                pets = currUser.getPets();

                mRecycleView = findViewById(R.id.pets_for_walk);
                mRecycleView.setLayoutManager(new GridLayoutManager(this, 2));

                mAdapter = new PetsForWalkAdapter(this, pets, this);
                mRecycleView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

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

    private void signOut() {
        auth.signOut();
        finish();
        Toast.makeText(this, "Log out succesfull!", Toast.LENGTH_LONG).show();
    }

    private void openPets() {
        Intent intent = new Intent(this, PetsActivity.class);
        startActivity(intent);
        finish();

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

    public void goWalk(View view) {
        if (pets.isEmpty()) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(WalksActivity.this, "You can't go on a walk alone ...", Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            if (selectedPet == null) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(WalksActivity.this, "Select a pet first ...", Toast.LENGTH_SHORT);
                mToast.show();
            } else {
                Intent intent = new Intent(this, OnWalkActivity.class);
                intent.putExtra("petName", selectedPet.getName());
                startActivity(intent);
                finish();
            }
        }
    }

    public void refresh(View view) {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onPetClick(int position) {
        selectedPet = pets.get(position);

        selectedPetTv.setText("Walk with: " + selectedPet.getName());
    }
}