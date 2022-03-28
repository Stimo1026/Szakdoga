package com.example.appnimal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class PetAddActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText speciesEditText;
    EditText breedEditText;
    EditText ageEditText;
    private Toast mToast;
    private FirebaseFirestore mFirestore;
    private CollectionReference cRef;
    private User currUser;
    private String id;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_add);

        // firestore get user
        mFirestore = FirebaseFirestore.getInstance();
        cRef = mFirestore.collection("Useres");
        auth = FirebaseAuth.getInstance();
        getUserInfo();

        nameEditText = findViewById(R.id.nameEditText);
        speciesEditText = findViewById(R.id.speciesEditText);
        breedEditText = findViewById(R.id.breedEditText);
        ageEditText = findViewById(R.id.ageEditText);
    }

    private void getUserInfo() {
        cRef.whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                currUser = doc.toObject(User.class);
                id = doc.getId();
            }

        });
    }


    public void addPet(View view) {
        // adds pet to database
        String name = nameEditText.getText().toString();
        String breed = breedEditText.getText().toString();
        String species = speciesEditText.getText().toString();
        String ageStr = ageEditText.getText().toString();
        int age;

        // verify inputs
        if (ageStr.equals("")) {
            age = -42;
        } else {
            age = Integer.parseInt(ageEditText.getText().toString());
        }

        if (name.equals("") || breed.equals("") || species.equals("") || age == -42) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(PetAddActivity.this, "Something is missing!", Toast.LENGTH_LONG);
            mToast.show();
        } else if (age < 0) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(PetAddActivity.this, "Invalid age!", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            Pet pet = new Pet(name, species, breed, age);
            PetsActivity.pets.add(pet);
            cRef.document(id).update("pets", PetsActivity.pets);
            Intent intent = new Intent(this, PetsActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void cancel(View view) {
        Intent intent = new Intent(this, PetsActivity.class);
        startActivity(intent);
        finish();
    }
}