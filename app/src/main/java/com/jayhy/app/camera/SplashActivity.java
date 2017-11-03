package com.jayhy.app.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jayhy.app.camera.common.RecycleUtils;

/**
 * Created by jayhy on 2017. 11. 2..
 */

public class SplashActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        checkPermission();
    }

    /**
     * 런타임 퍼미션 체크(파일 읽기 쓰기, 인터넷, 카메라)
     */
    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
            int permissionInternet = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);

            if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED || permissionCamera == PackageManager.PERMISSION_DENIED || permissionInternet == PackageManager.PERMISSION_DENIED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST);

            } else {
                startMainAcitivty();
            }
        } else {
            startMainAcitivty();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    startMainAcitivty();
                } else {
                    Toast.makeText(this, "auth", Toast.LENGTH_LONG).show();
                    finish();
                }

                return;
            }
        }
    }

    private void startMainAcitivty() {
        Intent intent = null;
        try {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } finally {
            if(intent != null) intent = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            RecycleUtils.recursiveRecycle(this.getWindow().getDecorView());
        } catch (Exception e) {

        }

    }
}
