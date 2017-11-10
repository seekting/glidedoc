package com.seekting.demo2016.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.seekting.demo2016.R;

public class VideoGlideActivity extends BaseActivity {
    static {
        MainActivity.putTitle(VideoGlideActivity.class, "");
        MainActivity.putDesc(VideoGlideActivity.class, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String str = "file:///storage/emulated/0/DCIM/Camera/VID_20171106_165818.mp4";
        ImageView imageView = new ImageView(this);
        Glide.with(this).load(str)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)

                .placeholder(R.mipmap.ic_launcher)
//                .override(200, 200)
                .into(imageView);
        setContentView(imageView);

    }

}
