package com.jayhy.app.camera.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jayhy.app.camera.common.CommonValues;

/**
 * Created by jayhy on 2017. 11. 2..
 */

public class DBHelper extends SQLiteOpenHelper {

    private final static int version = 1;

    private final static String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, CommonValues.dbPath, null, version);
        Log.d(TAG, "DBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        init(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                init(db);
                break;
        }
    }

    private void init(SQLiteDatabase db) {
        createTablePixel(db);
        createTableQuality(db);
    }

    private void createTablePixel(SQLiteDatabase db) {
        String sql = "";
        try {
            sql = "create table pixel";
            sql += "(";
            sql += "idx integer primary key autoincrement not null,";
            sql += "type integer not null,";
            sql += "view varchar(10) not null,";
            sql += "width integer null,";
            sql += "height integer null,";
            sql += "value integer null";
            sql += ");";
            db.execSQL(sql);
        } catch(Exception e) {
            Log.d(TAG, "createTablePixel Exception : " + e.toString());
        } finally {
            if(sql != null) sql = null;
        }
    }

    private void createTableQuality(SQLiteDatabase db) {
        String sql = "";
        try {
            sql = "create table quality";
            sql += "(";
            sql += "idx integer primary key autoincrement not null,";
            sql += "type integer not null,";
            sql += "view varchar(10) not null,";
            sql += "width integer null,";
            sql += "height integer null,";
            sql += "value integer null";
            sql += ");";
            db.execSQL(sql);
        } catch(Exception e) {
            Log.d(TAG, "createTableQuality Exception : " + e.toString());
        } finally {
            if(sql != null) sql = null;
        }
    }
}
