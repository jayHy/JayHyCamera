package com.jayhy.app.camera.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jayhy.app.camera.common.CommonValues;
import com.jayhy.app.camera.db.DBManager;
import com.jayhy.app.camera.item.SupportItem;

import java.io.IOException;
import java.util.List;

/**
 * Created by jayhy on 2017. 11. 1..
 */
public class GLCameraPreview extends GLSurfaceView {

    private static Context context = null;
    private SurfaceHolder mHolder = null;

    public static int cameraChangedState = 0;
    private static Camera mCamera;
    private static Camera.Parameters parameters = null;

    private List<Camera.Size> pictrueSizeList = null;
    private List<Camera.Size> previewSizeList = null;

    private int cameraMode = 0;

    private final static String TAG = "GLCameraPreview";

    public GLCameraPreview(Context context) {
        super(context);

        this.context = context;

        initHolder();
    }
    public GLCameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        initHolder();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) { // 세로 전환시 발생
            mCamera.setDisplayOrientation(90);
            parameters.setRotation(90);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) { // 가로 전환시 발생
            mCamera.setDisplayOrientation(180);
            parameters.setRotation(180);
        }
    }

    @SuppressWarnings("deprecation")
    private void initHolder() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mCamera = Camera.open(CommonValues.CameraChanged);

        DBManager dbManager = null;
        Resources resources = null;
        Configuration config = null;

        try {
            dbManager = new DBManager(context);

            mCamera.setPreviewDisplay(holder);
            parameters = mCamera.getParameters();
            resources = Resources.getSystem();
            config = resources.getConfiguration();
            onConfigurationChanged(config);

            parameters.setZoom(CommonValues.zoomLevel);
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)){
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

            int pictrue_width = 0;
            int pictrue_height = 0;

            if(!dbManager.isExistPixel()) {
                pictrueSizeList = parameters.getSupportedPictureSizes();
                for(int i=0; i<pictrueSizeList.size(); i++) {
                    Camera.Size size = pictrueSizeList.get(i);
                    SupportItem item = new SupportItem();
                    if(i == 0) {
                        pictrue_width = size.width;
                        pictrue_height = size.height;
                        item.setSelected(1);
                    } else {
                        item.setSelected(0);
                    }
                    item.setType(1);
                    item.setWidth(size.width);
                    item.setHeight(size.height);
                    item.setView(size.width + "x" + size.height);

                    dbManager.insertSupportPixel(item);
                }
            } else {

            }

            int preview_width = 0;
            int preview_height = 0;

            if(!dbManager.isExistQuality()) {
                previewSizeList = parameters.getSupportedPreviewSizes();

                for(int i=0; i<previewSizeList.size(); i++) {
                    SupportItem item = new SupportItem();
                    Camera.Size size = previewSizeList.get(i);
                    if(i == 0) {
                        preview_width = size.width;
                        preview_height = size.height;
                        item.setSelected(1);
                    } else {
                        item.setSelected(0);
                    }
                    item.setType(2);
                    item.setWidth(size.width);
                    item.setHeight(size.height);
                    item.setView(size.width + "x" + size.height);
                    dbManager.insertSupportQuality(item);
                }
            } else {

            }

            parameters.setPictureSize(pictrue_width, pictrue_height);
            parameters.setPreviewSize(preview_width, preview_height);
//
            mCamera.setParameters(parameters);

        } catch (IOException e) {
            Log.d(TAG, "surfaceCreated IOException : " + e.toString());
            mCamera.release();
            mCamera = null;
        } finally {

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        DBManager dbManager = null;
        Resources resources = null;
        Configuration config = null;

        try {
            mCamera.setPreviewDisplay(holder);
            parameters = mCamera.getParameters();
            resources = Resources.getSystem();
            config = resources.getConfiguration();
            onConfigurationChanged(config);

            parameters.setZoom(CommonValues.zoomLevel);
            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)){
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }

            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

            int pictrue_width = 0;
            int pictrue_height = 0;

            pictrueSizeList = parameters.getSupportedPictureSizes();
            for(int i=0; i<pictrueSizeList.size(); i++) {
                Camera.Size size = pictrueSizeList.get(i);
                if(i == 0) {
                    pictrue_width = size.width;
                    pictrue_height = size.height;
                }
            }

            previewSizeList = parameters.getSupportedPreviewSizes();

            int preview_width = 0;
            int preview_height = 0;

            for(int i=0; i<previewSizeList.size(); i++) {
                Camera.Size size = previewSizeList.get(i);
                if(i == 0) {
                    preview_width = size.width;
                    preview_height = size.height;
                }
            }

            // 현재 디바이스에서 지원되는 preview 크기를 받아옴
            previewSizeList = parameters.getSupportedPreviewSizes();

            parameters.setPictureSize(pictrue_width, pictrue_height);
            parameters.setPreviewSize(preview_width, preview_height);

            mCamera.setParameters(parameters);
            mCamera.startPreview();

        } catch (IOException e) {
            Log.d(TAG, "surfaceChanged IOException : " + e.toString());
            mCamera.release();
            mCamera = null;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    public boolean capture(Camera.PictureCallback handler) {
        if(mCamera != null) {
            mCamera.takePicture(shutterCallback, pictureCallback, handler);
            return true;
        } else {
            return false;
        }
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    public void restartPreview() {
        mCamera.startPreview();
    }

    public Camera getCamera() {
        return mCamera;
    }

//    public int getMaxZoom() {
//        return maxZoom;
//    }
}
