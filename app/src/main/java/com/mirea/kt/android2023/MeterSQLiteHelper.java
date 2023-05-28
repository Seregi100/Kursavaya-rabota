package com.mirea.kt.android2023;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MeterSQLiteHelper extends SQLiteOpenHelper {

    public MeterSQLiteHelper(@Nullable Context c, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory f, int version) {
        super(c, name, f, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "TABLE_METERS" + " (" + "_id integer primary key autoincrement," + "type text," + "meter_number int" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}