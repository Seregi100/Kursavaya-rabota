package com.mirea.kt.android2023;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBManagerMeter {
    private SQLiteOpenHelper sqLiteHelper;

    public DBManagerMeter(SQLiteOpenHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
    }

    public void saveMeterToDatabase(Meter meter){
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type", meter.getType());
        cv.put("meter_number", meter.getMeterNum());
        long rowId = db.insert("TABLE_METERS", null, cv);
        cv.clear();
        db.close();
    }

    public void deleteMeterFromDatabase(int meterNum){
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        long rowId = db.delete("TABLE_METERS", "meter_number="+meterNum, null);
        db.close();
    }

    public ArrayList<Meter> loadAllMetersFromDatabase(){
        ArrayList<Meter> meters = new ArrayList<>();
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        Cursor dbCursor = db.query("TABLE_METERS", null, null, null, null, null, null);
        if (dbCursor.moveToFirst()){
            do{
                String type = dbCursor.getString(dbCursor.getColumnIndexOrThrow("type"));
                int meter_number = dbCursor.getInt(dbCursor.getColumnIndexOrThrow("meter_number"));
                meters.add(new Meter(type, meter_number));
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return meters;
    }
}
