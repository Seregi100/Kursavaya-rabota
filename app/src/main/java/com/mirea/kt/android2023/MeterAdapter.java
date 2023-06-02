package com.mirea.kt.android2023;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class MeterAdapter extends RecyclerView.Adapter<MeterAdapter.ViewHolder>{
    private ArrayList<Meter> meters;
    private OnMeterClickListener onMeterClickListener;
    private DBManagerMeter dbManagerMeter;

    public void setDbManagerMeter(DBManagerMeter dbManagerMeter) {
        this.dbManagerMeter = dbManagerMeter;
    }

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
        private final ImageButton btDeleteMeter;
        private final ImageView imageType;

        ViewHolder(View view){
            super(view);
            typeView = view.findViewById(R.id.tvMeterType);
            btDeleteMeter = view.findViewById(R.id.btDeleteMeter);
            imageType = view.findViewById(R.id.ivImageMeterType);
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
        if (meter.getType()=="Электричество"){
            holder.imageType.setImageResource(R.drawable.electro);
        } else if (meter.getType()=="Горячая вода") {
            holder.imageType.setImageResource(R.drawable.hw);
        } else if (meter.getType()=="Холодная вода") {
            holder.imageType.setImageResource(R.drawable.cw);
        } else if (meter.getType()=="Газ") {
            holder.imageType.setImageResource(R.drawable.gas);
        }
        holder.btDeleteMeter.setOnClickListener(v -> {
            int actualPosition = holder.getAdapterPosition();
            dbManagerMeter.deleteMeterFromDatabase(meter.getMeterNum());
            Log.i("uiop", "Delete meter with number " + meter.getMeterNum());
            meters.remove(actualPosition);
            notifyItemRemoved(actualPosition);
            notifyItemRangeChanged(actualPosition, meters.size());
        });
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
