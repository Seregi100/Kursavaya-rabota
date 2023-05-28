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
        Reading testRead = new Reading("21.05.2023", 400.0, 200.0);
        testRead.setMeterNum(meterNum);
        ArrayList<Reading> readings = dbManagerReading.loadAllReadingsFromDatabase();
        readings.add(testRead);
        ReadingAdapter adapter = new ReadingAdapter(readings);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recView.setAdapter(adapter);
        if (type=="Электричество"){
            tvType.setText("Электричество");
            ivType.setImageResource(R.drawable.electro);
        } else if (type=="ГВС") {
            tvType.setText("Горячая вода");
            ivType.setImageResource(R.drawable.hw);
        } else if (type=="ХВС") {
            tvType.setText("Холодная вода");
            ivType.setImageResource(R.drawable.cw);
        } else if (type=="Газ") {
            tvType.setText("Газ");
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
                Log.i("uiop", "Добавлено новое показание");
            } else {
                Log.d("uiop", "Ошибка при добавлении показания");
            }
            adapter.notifyDataSetChanged();
        });
    }
}