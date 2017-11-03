package com.jayhy.app.camera;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.jayhy.app.camera.databinding.ActivityMainBinding;
import com.jayhy.app.camera.db.DBManager;
import com.jayhy.app.camera.view.GLCameraPreview;
import com.jayhy.app.camera.view.MenuTopView;

public class MainActivity extends AppCompatActivity {

    private Context context = null;
    private ActivityMainBinding binding = null;

    private SurfaceView surfaceView = null;
    private SurfaceHolder surfaceHolder = null;
    private GLCameraPreview glCameraPreview = null;
    private Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.preview.setVisibility(View.VISIBLE);
        surfaceHolder = binding.preview.getHolder();

        MenuTopView menuTopView = new MenuTopView(this);
        binding.layoutMain.addView(menuTopView.getView());

        glCameraPreview = new GLCameraPreview(this);
        camera = glCameraPreview.getCamera();

//        DBManager dbManager = new DBManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    }
}
