package com.mirea.kt.android2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;


public class AppActivity extends AppCompatActivity implements MeterAdapter.OnMeterClickListener, NewMeterFragment.OnCallback {

    private String choosenType;
    private ArrayList<Meter> meters = new ArrayList<>();
    private int meterCounter=0;
    private MeterAdapter adapter;
    @Override
    public void type(String choosenType) {
        this.choosenType = choosenType;
    }

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
        meterCounter+=1;
        meters.add(new Meter((double)20020, "ГВС", meterCounter));
        meterCounter+=1;
        meters.add(new Meter((double)476, "Электричество", meterCounter));
        RecyclerView rcView = findViewById(R.id.recyclerViewMeters);
        adapter = new MeterAdapter(meters, this);
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
            NewMeterFragment newMeterDialog = new NewMeterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            newMeterDialog.show(transaction, "type_meter");
            meterCounter+=1;
            type(choosenType);
            meters.add(new Meter((double)3003, choosenType, meterCounter));
            adapter.notifyDataSetChanged();
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
        Bundle b = new Bundle();
        b.putString("type", meter.getType());
        b.putDouble("reading", meter.getReading());
        b.putInt("number", meter.getMeterNum());
        intent.putExtra("bundle", b);
        startActivity(intent);
    }
}