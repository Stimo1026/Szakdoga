package com.example.appnimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetsForWalkAdapter extends RecyclerView.Adapter<PetsForWalkAdapter.ViewHolder> {

    private ArrayList<Pet> pets = new ArrayList<>();
    private Context mContext;

    PetsForWalkAdapter(Context context, ArrayList<Pet> petsData) {
        this.pets = petsData;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pet_for_walk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_for_walk);
            species = itemView.findViewById(R.id.species_for_walk);

        }

        public void bindTo(Pet currentPet) {
            name.setText(currentPet.getName());
            species.setText(currentPet.getSpecies());
        }
    }
}

