package com.application.appnimal.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnimal.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemlistener onItemlistener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemlistener onItemlistener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemlistener = onItemlistener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemlistener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
