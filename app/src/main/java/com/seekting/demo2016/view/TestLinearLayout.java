package com.seekting.demo2016.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RenderNode;
import android.widget.LinearLayout;

import com.seekting.demo2016.AppEnv;

import java.lang.reflect.Field;

/**
 * Created by seekting on 2016/9/24.
 */
public class TestLinearLayout extends LinearLayout {
    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "TestLinearLayout";

    public TestLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    public void draw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.draw()");
        }
        super.draw(canvas);

    }

    @Override
    protected void dispatchGetDisplayList() {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.dispatchGetDisplayList()");
        }
        super.dispatchGetDisplayList();
    }

    @Override
    public Bitmap getDrawingCache() {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.getDrawingCache()");
        }
        return super.getDrawingCache();
    }

    @Override
    public RenderNode updateDisplayListIfDirty() {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.updateDisplayListIfDirty()");
        }
//
        Class cls = getClass();
        try {
            Field f = cls.getField("mAttachInfo");
            Object mAttachInfo = f.get(this);

            if (DEBUG) {
                Log.d(TAG, "TestLinearLayout.updateDisplayListIfDirty()mAttachInfo=" + mAttachInfo);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        RenderNode re = super.updateDisplayListIfDirty();
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.updateDisplayListIfDirty()isValid=" + re.isValid());
        }
        return re;
    }

    @Override
    public boolean canHaveDisplayList() {

        boolean b = super.canHaveDisplayList();
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.canHaveDisplayList()b=" + b);
        }
        return b;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.computeScroll()");
        }
    }

    @Override
    public Bitmap getDrawingCache(boolean autoScale) {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.getDrawingCache()");
        }
        return super.getDrawingCache(autoScale);
    }

    @Override
    public int getLayerType() {
        int type = super.getLayerType();

        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.getLayerType()" + type);
        }
        return type;
    }

    @Override
    public void buildDrawingCache(boolean autoScale) {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.buildDrawingCache()");
        }
        super.buildDrawingCache(autoScale);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.dispatchDraw()");
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DEBUG) {
            Log.d(TAG, "TestLinearLayout.onDraw()" + canvas.isHardwareAccelerated());
        }
        super.onDraw(canvas);


    }
}
