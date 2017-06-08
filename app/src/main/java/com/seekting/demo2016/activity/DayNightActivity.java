package com.seekting.demo2016.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.seekting.demo2016.R;

public class DayNightActivity extends BaseActivity {
    static {
        MainActivity.putTitle(DayNightActivity.class, "白昼");
        MainActivity.putDesc(DayNightActivity.class, "");
    }

    private View mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_night_activity);
        mTest = findViewById(R.id.test_bg);
        mTest.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTest.setBackgroundDrawable(new ColorDrawable(0xffffff00));
            }
        }, 1000);
    }

}
