package com.jayhy.app.camera.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jayhy.app.camera.item.SupportItem;

/**
 * Created by jayhy on 2017. 11. 2..
 */

public class DBManager {

    private SQLiteDatabase db = null;
    private Context context = null;

    private String table_pixel = "pixel";
    private String table_quality = "quality";

    private final static String TAG = "DBManager";

    public DBManager(Context context) {
        Log.d(TAG, "DBManager context ==> " + context);
        this.context = context;
        this.open();
    }

    public void open() throws SQLException {
        Log.d(TAG, "open context ==> "  + context);
        try {
            db = (new DBHelper(context).getWritableDatabase());
        } catch (SQLException e) {
            db = (new DBHelper(context).getReadableDatabase());
        }
    }

    private void close() {
        if(context != null) context = null;
        if(db != null) {
            db.close();
            db = null;
        }

    }

    private ContentValues setSupportValue(SupportItem item) {
        ContentValues values = null;
        try {
            values = new ContentValues();
            values.put("type", item.getType());
            values.put("view", item.getView());
            values.put("width", item.getWidth());
            values.put("height", item.getHeight());
            values.put("value", item.getValue());
        } catch(Exception e) {

        } finally {

        }

        return values;

    }

    public long insertSupportPixel(SupportItem item) {
        long result = -1;
        ContentValues values = setSupportValue(item);
        try {
            result = db.insert(table_pixel, null, values);
        } catch (Exception e) {

        } finally {

        }

        return result;
    }

    public long insertSupportQuality(SupportItem item) {
        long result = -1;
        ContentValues values = setSupportValue(item);
        try {
            result = db.insert(table_quality, null, values);
        } catch (Exception e) {

        } finally {

        }

        return result;
    }

    public boolean isExistPixel() {
        boolean result = false;

        Cursor cursor = null;
        String sql = "";
        try {
            sql += "select idx from ";
            sql += table_pixel;
            cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) result = true;

        } catch (Exception e) {
            Log.d(TAG, "isExistPixel Exception : "+e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }

            if(sql != null) sql = null;
        }

        return result;
    }

    public boolean isExistQuality() {
        boolean result = false;

        Cursor cursor = null;
        String sql = "";
        try {
            sql += "select idx from ";
            sql += table_quality;
            cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) result = true;

        } catch (Exception e) {
            Log.d(TAG, "isExistPixel Exception : "+e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }

            if(sql != null) sql = null;
        }

        return result;
    }
}
