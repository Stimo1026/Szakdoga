package com.application.appnimal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnimal.R;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemlistener onItemlistener;
    public CalendarAdapter(ArrayList<String> daysofMonth, OnItemlistener onItemlistener) {
        this.daysOfMonth = daysofMonth;
        this.onItemlistener = onItemlistener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //set cell size to 1/6th of parent layout
        layoutParams.height = (int) (parent.getHeight() * 0.16);
        return new CalendarViewHolder(view, onItemlistener);
    }
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemlistener{
        void onItemClick(int position, String dayText);
    }
}
