package com.mirea.kt.android2023;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBManagerReading {

    private SQLiteOpenHelper sqLiteHelper;
    private int meterNum;

    public void setMeterNum(int meterNum) {
        this.meterNum = meterNum;
    }

    public DBManagerReading(SQLiteOpenHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
    }

    public boolean saveReadingToDatabase(Reading reading){
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("data", reading.getData());
        cv.put("reading", reading.getReading());
        cv.put("tarif", reading.getTarif());
        long rowId = db.insert("TABLE_READINGS" + meterNum, null, cv);
        cv.clear();
        db.close();
        return rowId != -1;
    }

    public ArrayList<Reading> loadAllReadingsFromDatabase(){
        ArrayList<Reading> readings = new ArrayList<>();
        SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
        Cursor dbCursor = db.query("TABLE_READINGS" + meterNum, null, null, null, null, null, null);
        if (dbCursor.moveToFirst()){
            do{
                String data = dbCursor.getString(dbCursor.getColumnIndexOrThrow("data"));
                double reading = dbCursor.getDouble(dbCursor.getColumnIndexOrThrow("reading"));
                double tarif = dbCursor.getDouble(dbCursor.getColumnIndexOrThrow("tarif"));
                Reading read = new Reading(data, reading, tarif);
                read.setMeterNum(meterNum);
                readings.add(read);
            }while (dbCursor.moveToNext());
        }
        dbCursor.close();
        db.close();
        return readings;
    }
}