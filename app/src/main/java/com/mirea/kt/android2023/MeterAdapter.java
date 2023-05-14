package com.mirea.kt.android2023;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class MeterAdapter extends RecyclerView.Adapter<MeterAdapter.ViewHolder>{
    private ArrayList<Meter> meters;
    private OnMeterClickListener onMeterClickListener;

    public MeterAdapter(ArrayList<Meter> meters) {
        this.meters = meters;
    }

    public MeterAdapter(ArrayList<Meter> meters, OnMeterClickListener onMeterClickListener){
        this.meters = meters;
        this.onMeterClickListener = onMeterClickListener;
    }

    interface OnMeterClickListener{
        void onMeterClick(Meter meter, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView typeView;
        private final TextView lastReadingView;

        ViewHolder(View view){
            super(view);
            typeView = view.findViewById(R.id.tvMeterType);
            lastReadingView = view.findViewById(R.id.tvLastReading);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeterAdapter.ViewHolder holder, int position) {
        Meter meter = meters.get(position);
        holder.typeView.setText(String.format("%s", meter.getType()));
        holder.lastReadingView.setText(String.format("%f", meter.getReading()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMeterClickListener.onMeterClick(meter, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return meters.size();
    }
}
