package com.seekting.demo2016.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.util.Log;
import android.view.View;

import com.seekting.demo2016.R;
import com.seekting.demo2016.view.RectView;

/**
 * Created by seekting on 16-12-12.
 */

public class MaterialDesignJump2Activity extends AppCompatActivity {
    static {
        MainActivity.putTitle(AActivity.class, "MaterialDesignJump");
        MainActivity.putDesc(AActivity.class, "MaterialDesignJump");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.material_design_jump2);
        RectView imageView = (RectView) findViewById(R.id.img_main);
        imageView.setCircle();
        imageView.setTransitionName("big_img");
        imageView.setVisibility(View.VISIBLE);
        Log.d("ImageView", "ImageView" + imageView.toString());
        imageView.getLayoutParams().width = 900;
        imageView.getLayoutParams().height = 1500;
        Transition transition = new AutoTransition();
        transition.setDuration(5000);
        getWindow().setEnterTransition(transition);
        getWindow().setExitTransition(transition);
        getWindow().setSharedElementEnterTransition(transition);
//        supportStartPostponedEnterTransition();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Handler mHandler = new Handler();
//        supportPostponeEnterTransition();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

//
            }
        }, 1000);


    }
}
