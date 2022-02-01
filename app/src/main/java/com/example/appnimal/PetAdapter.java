package com.example.appnimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    private ArrayList<Pet> pets = new ArrayList<>();
    private Context mContext;

    PetAdapter(Context context, ArrayList<Pet> petsData) {
        this.pets = petsData;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pet, parent, false));
    }

    @Override
    public void onBindViewHolder(PetAdapter.ViewHolder holder, int position) {
        Pet currentPet = pets.get(position);
        holder.bindTo(currentPet);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView species;
        private TextView breed;
        private TextView age;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            species = itemView.findViewById(R.id.species);
            breed = itemView.findViewById(R.id.breed);
            age = itemView.findViewById(R.id.age);


        }

        public void bindTo(Pet currentPet) {
            name.setText(currentPet.getName());
            species.setText(currentPet.getSpecies());
            breed.setText(currentPet.getBreed());
            age.setText(String.valueOf(currentPet.getAge()));
        }
    }
}

