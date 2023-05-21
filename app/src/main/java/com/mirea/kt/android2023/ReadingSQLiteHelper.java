package com.mirea.kt.android2023;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ReadingSQLiteHelper extends SQLiteOpenHelper{

    private int meterNum;

    public void setMeter(int meterNum) {
        this.meterNum = meterNum;
    }

    public ReadingSQLiteHelper(@Nullable Context c, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory f, int version) {
        super(c, name, f, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "TABLE_READINGS" + meterNum + " (" + "_id integer primary key autoincrement," + "data text," + "reading real," + "tarif real" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}