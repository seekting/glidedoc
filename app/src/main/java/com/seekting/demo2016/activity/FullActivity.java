package com.seekting.demo2016.activity;

import android.os.Bundle;
import android.view.WindowManager;

public class FullActivity extends BaseActivity {
    static {
        MainActivity.putTitle(FullActivity.class, "");
        MainActivity.putDesc(FullActivity.class, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }

}
