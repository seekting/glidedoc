package com.seekting.demo2016.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.seekting.demo2016.view.ArrowAniView;

public class ArrowAniActivity extends BaseActivity {
    static {
        MainActivity.putTitle(ArrowAniActivity.class, "");
        MainActivity.putDesc(ArrowAniActivity.class, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrowAniView aniView = new ArrowAniView(this);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);
        frameLayout.addView(aniView);
        frameLayout.setBackgroundColor(Color.GRAY);
        int wrap = FrameLayout.LayoutParams.WRAP_CONTENT;
        int match = FrameLayout.LayoutParams.MATCH_PARENT;

        aniView.setBackgroundColor(Color.DKGRAY);
        int height = Math.round(72 * getResources().getDisplayMetrics().density);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(match, match);
        layoutParams.gravity = Gravity.CENTER;
        aniView.setLayoutParams(layoutParams);


    }

}
