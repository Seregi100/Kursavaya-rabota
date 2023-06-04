package com.mirea.kt.android2023;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExampleMeter extends AppCompatActivity {

    private DBManagerReading dbManagerReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_meter);
        Bundle b = getIntent().getBundleExtra("bundle");
        String type = b.getString("type");
        int meterNum = b.getInt("number");
        ReadingSQLiteHelper RLSQLH = new ReadingSQLiteHelper(this, "reading_database" + meterNum +".db", null, 1);
        RLSQLH.setMeter(meterNum);
        this.dbManagerReading = new DBManagerReading(RLSQLH);
        this.dbManagerReading.setMeterNum(meterNum);
        Button btNewReading = findViewById(R.id.btNewReading);
        RecyclerView recView = findViewById(R.id.recyclerViewReadings);
        TextView tvType = findViewById(R.id.tvType);
        ImageView ivType = findViewById(R.id.ivImage);
        EditText etNewReading = findViewById(R.id.etNewReading);
        EditText etNewTarif = findViewById(R.id.etNewTarif);
        ArrayList<Reading> readings = dbManagerReading.loadAllReadingsFromDatabase();
        ReadingAdapter adapter = new ReadingAdapter(readings);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recView.setAdapter(adapter);
        if (type.equals("Электричество")){
            tvType.setText(getString(R.string.electro));
            ivType.setImageResource(R.drawable.electro);
        } else if (type.equals("Горячая вода")) {
            tvType.setText(getString(R.string.hw));
            ivType.setImageResource(R.drawable.hw);
        } else if (type.equals("Холодная вода")) {
            tvType.setText(getString(R.string.cw));
            ivType.setImageResource(R.drawable.cw);
        } else if (type.equals("Газ")) {
            tvType.setText(getString(R.string.gas));
            ivType.setImageResource(R.drawable.gas);
        }
        btNewReading.setOnClickListener(v -> {
            double newReading = Double.parseDouble(etNewReading.getText().toString());
            double newTarif = Double.parseDouble(etNewTarif.getText().toString());
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Reading newRead = new Reading(format.format(currentTime), newReading, newTarif);
            newRead.setMeterNum(meterNum);
            boolean res = dbManagerReading.saveReadingToDatabase(newRead);
            readings.add(newRead);
            if (res) {
                Log.i(getString(R.string.app_tag), "Добавлено новое показание");
            } else {
                Log.d(getString(R.string.app_tag), "Ошибка при добавлении показания");
            }
            adapter.notifyDataSetChanged();
        });
    }
}