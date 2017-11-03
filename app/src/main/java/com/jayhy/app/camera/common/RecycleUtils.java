package com.jayhy.app.camera.common;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by jayhy on 2017. 11. 2..
 */

public class RecycleUtils {

    public static void recursiveRecycle(View root) {
        if (root == null)
            return;

        root.setBackgroundDrawable(null);

        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                recursiveRecycle(group.getChildAt(i));
            }

            if (!(root instanceof AdapterView)) {
                group.removeAllViews();
            }

        }

        if (root instanceof ImageView) {
            ((ImageView) root).setImageDrawable(null);
        }

        root = null;

        return;
    }

    public static void recursiveRecycle(List<WeakReference<View>> recycleList) {
        for (WeakReference<View> ref : recycleList)
            recursiveRecycle(ref.get());
    }

    /**
     * 비트맵 메모리 해제
     */
    public static void clearImageBitmap(ImageView imageView) {
        Drawable drawable = null;
        Bitmap bitmap = null;
        try {
            drawable = imageView.getDrawable();
            if(drawable instanceof BitmapDrawable){
                bitmap = ((BitmapDrawable) drawable).getBitmap();
                if(bitmap != null) {
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            drawable.setCallback(null);
        } catch(Exception e) {

        } finally {
            if(drawable != null) drawable = null;
            if(bitmap != null) bitmap = null;
        }
    }
}
