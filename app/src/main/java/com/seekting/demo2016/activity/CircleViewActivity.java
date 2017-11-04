package com.seekting.demo2016.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.seekting.demo2016.R;
import com.seekting.demo2016.view.AvaterView;

/**
 * Created by Administrator on 2017/8/29.
 */

public class CircleViewActivity extends Activity {
    public static final String url = "https://ad.12306.cn/res/delivery/0001/2017/08/28/201708281643098782.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView circleImageView = new AvaterView(this);
//        circleImageView = new ImageView(this);

        RequestManager g = Glide.with(this);
        g.load(url).placeholder(R.mipmap.ic_launcher).into(circleImageView);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(circleImageView, 100, 100);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) circleImageView.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;

        setContentView(frameLayout);

    }
}
