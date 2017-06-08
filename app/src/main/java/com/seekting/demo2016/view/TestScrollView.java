package com.seekting.demo2016.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RenderNode;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.ScrollView;

import com.seekting.demo2016.AppEnv;

/**
 * Created by seekting on 2016/9/24.
 */
public class TestScrollView extends ScrollView {
    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "TestScrollView";

    public TestScrollView(Context context) {
        super(context);
    }

    public TestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "TestScrollView.draw()");
        }

        super.draw(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "TestScrollView.dispatchDraw()");
        }

        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (DEBUG) {
            Log.d(TAG, "TestScrollView.drawChild()");
        }

        return super.drawChild(canvas, child, drawingTime);

    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        if (DEBUG) {
            Log.d(TAG, "TestScrollView.getChildStaticTransformation()");
        }
        return super.getChildStaticTransformation(child, t);
    }

    @Override
    public RenderNode updateDisplayListIfDirty() {
        if (DEBUG) {
            Log.d(TAG, "TestScrollView.updateDisplayListIfDirty()");
        }
        return super.updateDisplayListIfDirty();
    }

    @Override
    public void buildDrawingCache(boolean autoScale) {
        if (DEBUG) {
            Log.d(TAG, "TestScrollView.buildDrawingCache()");
        }
        super.buildDrawingCache(autoScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "TestScrollView.onDraw()" + canvas);
        }

        super.onDraw(canvas);


    }


}
