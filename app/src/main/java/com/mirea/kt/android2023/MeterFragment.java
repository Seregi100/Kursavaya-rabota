package com.mirea.kt.android2023;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MeterFragment extends Fragment {

    private String type;

    public MeterFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
            this.type = bundle.getString("type", "Электричество");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_meter, container, false);
        TextView tvType = (TextView) inf.findViewById(R.id.tvFragType);
        ImageView ivType = (ImageView) inf.findViewById(R.id.ivFragIm);
        if (this.type=="Электричество"){
            tvType.setText("Электричество");
            ivType.setImageResource(R.drawable.electro);
        } else if (this.type=="ГВС") {
            tvType.setText("Горячая вода");
            ivType.setImageResource(R.drawable.hw);
        } else if (this.type=="ХВС") {
            tvType.setText("Холодная вода");
            ivType.setImageResource(R.drawable.cw);
        } else if (this.type=="Газ") {
            tvType.setText("Газ");
            ivType.setImageResource(R.drawable.gas);
        }
        return inf;
    }
}