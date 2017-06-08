package com.seekting.demo2016;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by seekting on 17-3-23.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        Log.d("seekting", "MyGlideModule.applyOptions()");
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

        Log.d("seekting", "MyGlideModule.registerComponents()");
    }
}
