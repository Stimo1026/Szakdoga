package com.application.appnimal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.appnimal.classes.Pet;
import com.example.appnimal.R;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    private ArrayList<Pet> pets = new ArrayList<>();
    private OnPetListener mOnPetLsitener;
    private Context mContext;

    public PetAdapter(Context context, ArrayList<Pet> petsData, OnPetListener onPetListener) {
        this.pets = petsData;
        this.mOnPetLsitener = onPetListener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.pet, parent, false), mOnPetLsitener);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView species;
        private TextView breed;
        private TextView age;
        OnPetListener onPetListener;

        public ViewHolder(@NonNull View itemView, OnPetListener onPetListener) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            species = itemView.findViewById(R.id.species);
            breed = itemView.findViewById(R.id.breed);
            age = itemView.findViewById(R.id.age);
            this.onPetListener = onPetListener;

            itemView.setOnClickListener(this);
        }

        public void bindTo(Pet currentPet) {
            name.setText(currentPet.getName());
            species.setText(currentPet.getSpecies());
            breed.setText(currentPet.getBreed());
            age.setText(String.valueOf(currentPet.getAge()));
        }

        @Override
        public void onClick(View v) {
            onPetListener.onPetClick(getAdapterPosition());
        }
    }

    public interface OnPetListener {
        void onPetClick(int position);
    }
}

