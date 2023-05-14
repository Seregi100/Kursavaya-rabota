package com.mirea.kt.android2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class AppActivity extends AppCompatActivity implements MeterAdapter.OnMeterClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if (ab!=null){
            ab.setTitle("Счётчики");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        ArrayList<Meter> meters = new ArrayList<>();
        meters.add(new Meter((double)20020, "ГВС"));
        meters.add(new Meter((double)476, "Электричество"));
        RecyclerView rcView = findViewById(R.id.recyclerView);
        MeterAdapter adapter = new MeterAdapter(meters, this);
        rcView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.action_add) {
            return true;
        } else if (itemId == R.id.action_update) {
            return true;
        } else if (itemId == R.id.action_exit) {
            finish();
            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMeterClick(Meter meter, int position){
        Intent intent = new Intent(this, ExampleMeter.class);
        Bundle type = new Bundle();
        type.putString("type", meter.getType());
        intent.putExtra("type", type);
        startActivity(intent);
    }
}