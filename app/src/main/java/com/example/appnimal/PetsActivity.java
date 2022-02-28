package com.example.appnimal;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class PetsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseAuth auth;
    public static ArrayList<Pet> pets = new ArrayList<Pet>();
    private Toast mToast;
    private RecyclerView mRecycleView;
    private PetAdapter mAdapter;
    private CardView cardView;
    private FirebaseFirestore mFirestore;
    private CollectionReference cRef;
    private User currUser;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);
        cardView = findViewById(R.id.cardView);
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
        navigationView.setCheckedItem(R.id.nav_pets);
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

        mFirestore = FirebaseFirestore.getInstance();
        cRef = mFirestore.collection("Useres");
        auth = FirebaseAuth.getInstance();
        getUserInfo();


        mRecycleView = findViewById(R.id.petRec);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 1));

        mAdapter = new PetAdapter(this, pets);
        mRecycleView.setAdapter(mAdapter);

        if (PetsActivity.pets.size() >= 4) {
            cardView.setVisibility(View.INVISIBLE);
            cardView.getLayoutParams().height = 0;
            cardView.setClickable(false);
        } else {
            cardView.setVisibility(View.VISIBLE);
            cardView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            cardView.setClickable(true);
        }

    }

    @Override
    protected void onResume() {
        if (PetsActivity.pets.size() >= 4) {
            cardView.setVisibility(View.INVISIBLE);
            cardView.getLayoutParams().height = 0;
            cardView.setClickable(false);
        } else {
            cardView.setVisibility(View.VISIBLE);
            cardView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            cardView.setClickable(true);
        }
        mAdapter.notifyDataSetChanged();
        super.onResume();
    }

    private void getUserInfo() {
        cRef.whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                currUser = doc.toObject(User.class);
                id = doc.getId();
                pets = currUser.getPets();
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
                openWalks();
                break;

            case R.id.nav_profile:
                openProfile();
                break;

            case R.id.nav_settings:
                openSettings();
                break;

            case R.id.nav_pets:
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

    public void openAddPet(View view) {

        if (PetsActivity.pets.size() >= 4) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(PetsActivity.this, "You already have 4 pets!", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            Intent intent = new Intent(this, PetAddActivity.class);
            finish();
            startActivity(intent);
        }
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

    public void deletePet(View view) {
        //TODO
    }
}