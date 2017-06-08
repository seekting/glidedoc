package com.seekting.demo2016.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.seekting.demo2016.AppEnv;

public class SeektingActivity extends BaseActivity {
    //1.touchEvent的传递
    //2.measure的传递

    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "SeektingActivity";

    static {
        MainActivity.putTitle(SeektingActivity.class, "事件传递，及measure传递");
        MainActivity.putDesc(SeektingActivity.class, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout linearLayout = new LinearLayout(this) {
            int evcount = 0;

            @Override
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                    evcount++;
                    if (evcount >= 30) {
                        Log.e(TAG, "抢事件");
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    evcount = 0;
                }
                return super.dispatchTouchEvent(ev);


            }
        };

        Button btn = new Button(this) {

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                boolean result = super.onTouchEvent(event);
                Log.d(TAG, "result=" + result + ",action=" + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    Log.e(TAG, "这是哪里调啊", new NullPointerException());

                }
                return result;
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int mode = MeasureSpec.getMode(widthMeasureSpec);
                switch (mode) {
                case MeasureSpec.AT_MOST:
                    Log.d(TAG, "result=AT_MOST");
                    break;
                case MeasureSpec.EXACTLY:
                    Log.d(TAG, "result=EXACTLY");
                    break;
                case MeasureSpec.UNSPECIFIED:
                    Log.d(TAG, "result=UNSPECIFIED");
                    break;
                }
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        };
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        linearLayout.addView(btn, layoutParams);
        btn.setText("test");
        setContentView(linearLayout);

    }

}
