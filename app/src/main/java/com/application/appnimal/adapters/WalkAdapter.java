package com.application.appnimal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.appnimal.classes.Walk;
import com.example.appnimal.R;

import java.util.ArrayList;

public class WalkAdapter extends RecyclerView.Adapter<WalkAdapter.ViewHolder> {

    private ArrayList<Walk> walks = new ArrayList<>();
    private Context mContext;
    public WalkAdapter(Context context, ArrayList<Walk> walksData){
        this.walks = walksData;
        this.mContext = context;
    }


    @NonNull
    @Override
    public WalkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.walk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalkAdapter.ViewHolder holder, int position) {
        Walk currentWalk = walks.get(position);
        holder.bindTo(currentWalk);
    }

    @Override
    public int getItemCount() {
        return walks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private TextView length;
        private TextView steps;
        private TextView duration;
        private TextView petName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            petName = itemView.findViewById(R.id.with);
            length = itemView.findViewById(R.id.length);
            steps = itemView.findViewById(R.id.steps);
            duration = itemView.findViewById(R.id.duration);
        }

        public void bindTo(Walk currentWalk) {
            date.setText(currentWalk.getDate());
            petName.setText(currentWalk.getPetName());
            length.setText(currentWalk.getLength());
            steps.setText(String.valueOf(currentWalk.getSteps()));
            duration.setText(String.valueOf(currentWalk.getMeters()));
        }
    }
}
