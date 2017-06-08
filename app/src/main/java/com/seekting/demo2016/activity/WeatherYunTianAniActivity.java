package com.seekting.demo2016.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.seekting.demo2016.AppEnv;
import com.seekting.demo2016.R;
import com.seekting.demo2016.view.OvercastAniView;
import com.seekting.demo2016.view.TestLinearLayout;
import com.seekting.demo2016.view.TestScrollView;

public class WeatherYunTianAniActivity extends BaseActivity {
    static {
        MainActivity.putTitle(WeatherYunTianAniActivity.class, "天气动画");
        MainActivity.putDesc(WeatherYunTianAniActivity.class, "阴天天气动画");
    }

    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "WeatherYunTianAniActivity";

    private OvercastAniView mOvercastAniView;
    private TestLinearLayout mTestLinearLayout;
    private int mA;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private BitmapDrawable mBitmapDrawable;
    private TestScrollView mTestScrollView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_yun_tian_ani_activity);
        mTestScrollView = (TestScrollView) findViewById(R.id.scroll_view);
        mOvercastAniView = (OvercastAniView) findViewById(R.id.overcast_ani_view);
        mTestLinearLayout = (TestLinearLayout) findViewById(R.id.test_linearlayout);


    }

}
