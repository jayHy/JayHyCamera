package com.jayhy.app.camera.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jayhy.app.camera.R;
import com.jayhy.app.camera.databinding.ViewTopMenuBinding;

/**
 * Created by jayhy on 2017. 11. 1..
 */

public class MenuTopView {

    private Context context = null;
    private LayoutInflater inflater = null;
    private ViewTopMenuBinding binding = null;

    public MenuTopView(Context context) {
        this.context = context;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        init();
    }

    private void init() {
        binding = ViewTopMenuBinding.bind(inflater.inflate(R.layout.view_top_menu, null));
    }

    public View getView() {
        return binding.getRoot();
    }

    public void close() {

    }
}
