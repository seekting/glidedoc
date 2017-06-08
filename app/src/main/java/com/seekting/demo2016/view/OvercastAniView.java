package com.seekting.demo2016.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.seekting.demo2016.AppEnv;

/**
 * Created by seekting on 2016/9/24.
 */
public class OvercastAniView extends View implements ValueAnimator.AnimatorUpdateListener {
    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "OvercastAniView";
    public int i = 0;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRect = new RectF();
    private DWave mDWave;
    private ValueAnimator mValueAnimator;

    public OvercastAniView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setTextSize(14 * getResources().getDisplayMetrics().density);
        mDWave = new DWave();
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.start();
    }

    public OvercastAniView(Context context) {
        super(context);
    }

    @Override
    public void invalidate() {
        super.invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mDWave.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "OvercastAniView.draw()");
        }
        super.draw(canvas);
        mDWave.draw(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "OvercastAniView.dispatchDraw()");
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "OvercastAniView.onDraw()");
        }
        super.onDraw(canvas);

//        int s = canvas.saveLayerAlpha(mRect, 255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
////        int s = canvas.saveLayer();
//        if (DEBUG) {
//            Log.d(TAG, "OvercastAniView.onDraw() s=" + s);
//        }
//        canvas.drawText(String.valueOf(i), 60 * i, 60 * i, mPaint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        mDWave.setFactorAndInvalidate(value);
        invalidate();
    }
}
