package com.mirea.kt.android2023;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class NewMeterFragment extends DialogFragment {

    private String[] meterTypes = {"Электричество", "Горячая вода", "Холодная вода", "Газ"};
    private DBManagerMeter dbManagerMeter;
    private MeterAdapter adapter;
    private int meterCounter;
    private ArrayList<Meter> meters;
    private String choosenType;
    public NewMeterFragment(DBManagerMeter dbManagerMeter, MeterAdapter adapter, int meterCounter, ArrayList<Meter> meters) {
        this.dbManagerMeter = dbManagerMeter;
        this.adapter = adapter;
        this.meterCounter = meterCounter;
        this.meters = meters;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите тип счетчика")
                .setSingleChoiceItems(meterTypes, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                choosenType = meterTypes[item];
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Meter meter = new Meter(choosenType, meterCounter);
                        dbManagerMeter.saveMeterToDatabase(meter);
                        meters.add(meter);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {}
                });

        return builder.create();
    }
}