package com.mirea.kt.android2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class AppActivity extends AppCompatActivity implements MeterAdapter.OnMeterClickListener {

    private DBManagerMeter dbManagerMeter;
    private String choosenType;
    private ArrayList<Meter> meters = new ArrayList<>();
    private int meterCounter=0;
    private MeterAdapter adapter;
    private boolean flashFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        MeterSQLiteHelper sqhelper = new MeterSQLiteHelper(this, "meter_database.db", null, 1);
        this.dbManagerMeter = new DBManagerMeter(sqhelper);
        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if (ab!=null){
            ab.setTitle(getString(R.string.name));
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        meters = dbManagerMeter.loadAllMetersFromDatabase();
        RecyclerView rcView = findViewById(R.id.recyclerViewMeters);
        adapter = new MeterAdapter(meters, this);
        adapter.setDbManagerMeter(dbManagerMeter);
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
            meterCounter+=1;
            NewMeterFragment newMeterDialog = new NewMeterFragment(dbManagerMeter, adapter, meterCounter, meters);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            newMeterDialog.show(transaction, "type_meter");
            Log.i(getString(R.string.app_tag), "Add new meter");
            return true;
        } else if (itemId == R.id.action_ligth) {
            boolean isCameraFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            if (!isCameraFlash){
                Toast toast = Toast.makeText(this, getString(R.string.no_flashlight_messge), Toast.LENGTH_LONG);
                toast.show();
                Log.i(getString(R.string.app_tag), "No fleshlight on device");
            }else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Camera cam = Camera.open();
                    Camera.Parameters p = cam.getParameters();
                    if (!flashFlag) {
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        cam.setParameters(p);
                        cam.startPreview();
                        Log.i(getString(R.string.app_tag), "Fleshlight ON");
                    } else {
                        cam.stopPreview();
                        cam.release();
                        Log.i(getString(R.string.app_tag), "Fleshlight OFF");
                    }
                } else {
                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = null;
                    if (!flashFlag) {
                        try {
                            cameraId = camManager.getCameraIdList()[0];
                            camManager.setTorchMode(cameraId, true);
                            flashFlag = true;
                            Log.i(getString(R.string.app_tag), "Fleshlight ON");
                        } catch (CameraAccessException e) {
                            Log.e(getString(R.string.app_tag), "CameraAccessException error");
                        }
                    } else {
                        try {
                            cameraId = camManager.getCameraIdList()[0];
                            camManager.setTorchMode(cameraId, false);
                            flashFlag = false;
                            Log.i(getString(R.string.app_tag), "Fleshlight OFF");
                        } catch (CameraAccessException e) {
                            Log.e(getString(R.string.app_tag), "CameraAccessException error");
                        }
                    }
                }
            }
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
        b.putInt("number", meter.getMeterNum());
        intent.putExtra("bundle", b);
        Log.i(getString(R.string.app_tag), "Meter activity begin");
        startActivity(intent);
    }
}