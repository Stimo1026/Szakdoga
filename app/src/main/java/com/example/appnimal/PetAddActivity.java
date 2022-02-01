package com.example.appnimal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PetAddActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText speciesEditText;
    EditText breedEditText;
    EditText ageEditText;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_add);

        nameEditText = findViewById(R.id.nameEditText);
        speciesEditText = findViewById(R.id.speciesEditText);
        breedEditText = findViewById(R.id.breedEditText);
        ageEditText = findViewById(R.id.ageEditText);
    }

    public void addPet(View view) {
        String name = nameEditText.getText().toString();
        String breed = breedEditText.getText().toString();
        String species = speciesEditText.getText().toString();
        String ageStr = ageEditText.getText().toString();
        int age;

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
            finish();
        }

    }

    public void cancel(View view) {
        finish();
    }
}