package com.seekting.demo2016.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeImageTransform;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.seekting.demo2016.R;

import static com.seekting.demo2016.R.id.img_main;

/**
 * Created by seekting on 16-12-12.
 */

public class MaterialDesignJumpActivity extends AppCompatActivity {
    static {
        MainActivity.putTitle(AActivity.class, "MaterialDesignJump");
        MainActivity.putDesc(AActivity.class, "MaterialDesignJump");
    }

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_jump);


        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("seekting", "handleMessage.what=" + msg.what);
//                if (msg.what == 2) {
//                    mHandler.removeMessages(1);
//                }
            }
        };
        mHandler.sendMessageDelayed(mHandler.obtainMessage(1), 3000);
        mHandler.removeMessages(1);
        mHandler.sendMessage(mHandler.obtainMessage(2));
        final View imageView = findViewById(img_main);
        imageView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Transition transition = new ChangeImageTransform();
                transition.setDuration(30000);
                getWindow().setExitTransition(transition);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MaterialDesignJumpActivity.this, Pair.create((View) imageView, "big_img"));
                Bundle bundle = activityOptions.toBundle();
                Intent intent = new Intent(MaterialDesignJumpActivity.this, MaterialDesignJump2Activity.class);
                startActivity(intent, bundle);
            }
        });


    }
}
