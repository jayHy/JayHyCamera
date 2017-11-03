package com.jayhy.app.camera;

import android.app.Application;
import android.os.Environment;

import com.jayhy.app.camera.common.CommonValues;

/**
 * Created by jayhy on 2017. 11. 2..
 */

public class CameraApplication extends Application {

    private final static String TAG = "CameraApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        CommonValues.dbPath = Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/DB.db";

    }
}
