package com.mirea.kt.android2023;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ViewHolder>{

    private ArrayList<Reading> readings;

    public ReadingAdapter(ArrayList<Reading> readings) {
        this.readings = readings;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView dataView;
        private final TextView readingView;
        private final TextView tarifView;

        ViewHolder(View view){
            super(view);
            dataView = view.findViewById(R.id.tvReadingData);
            readingView = view.findViewById(R.id.tvReadingReading);
            tarifView = view.findViewById(R.id.tvReadingTarif);
        }
    }

    @NonNull
    @Override
    public ReadingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reading, parent, false);
        return new ReadingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingAdapter.ViewHolder holder, int position) {
        Reading reading = readings.get(position);
        holder.dataView.setText(reading.getData());
        holder.readingView.setText(String.valueOf(reading.getReading()));
        holder.tarifView.setText(String.valueOf(reading.getTarif()));
    }

    @Override
    public int getItemCount() {
        return readings.size();
    }
}
