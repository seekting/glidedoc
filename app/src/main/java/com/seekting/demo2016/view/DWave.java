package com.seekting.demo2016.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by seekting on 2016/9/27.
 */
public class DWave {

    /**
     * 0-1的一个浮点数
     */
    private float mFactor;
    private Path mPathLeft, mPathRight;
    private RectF mRectF;
    private Paint mPaint;


    /**
     * 最大振幅
     */
    private float mPeek;
    /**
     * 最大1/4波长
     */
    private float mWaveLength;


    public DWave() {
        mPathLeft = new Path();
        mPathRight = new Path();
        mRectF = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    public void setPeek(float peek) {
        mPeek = peek;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        this.mRectF.set(left, top, right, bottom);
        mWaveLength = mRectF.width() / 4f;
        if (mPeek == 0) {
            mPeek = 4 * 3f;
        }


    }


    /**
     * factor 0-0.5-1-0.5-0....
     *
     * @param factor
     */
    public void setFactorAndInvalidate(float factor) {
        mFactor = factor;
        mPathLeft.reset();
        mPathRight.reset();
        //它是一个:-0.5f<cFactor<0.5f数
//        float cFactor = (factor - 0.5f) / 0.5f;
        //以中心为基础两边画孤
        // 先绘制中间的波，从左边到右边
        float centerX = mRectF.centerX();
        float beginY = mRectF.top + mPeek;
        float beginX = mRectF.left - mWaveLength - mFactor * mWaveLength * 4;
        float peek = mPeek;
        mPathLeft.moveTo(beginX, beginY);
        mPathLeft.quadTo(beginX + mWaveLength, beginY + peek, beginX + 2 * mWaveLength, beginY);
        mPathLeft.quadTo(beginX + 3 * mWaveLength, beginY - peek, beginX + 4 * mWaveLength, beginY);
        mPathLeft.quadTo(beginX + 5 * mWaveLength, beginY + peek, beginX + 6 * mWaveLength, beginY);
        mPathLeft.quadTo(beginX + 7 * mWaveLength, beginY - peek, beginX + 8 * mWaveLength, beginY);

        //移到最底下
        mPathLeft.lineTo(beginX + 8 * mWaveLength, mRectF.bottom);
        mPathLeft.lineTo(beginX, mRectF.bottom);
        mPathLeft.close();
//        mPathLeft.lineTo(beginX,beginY);
//        mPathLeft.quadTo(beginX + 9 * mWaveLength, centerY + peek, beginX + 10 * mWaveLength, centerY);

        beginX = mRectF.left - 5 * mWaveLength + mFactor * mWaveLength * 4;
        mPathRight.moveTo(beginX, beginY);
        mPathRight.quadTo(beginX + mWaveLength, beginY + peek, beginX + 2 * mWaveLength, beginY);
        mPathRight.quadTo(beginX + 3 * mWaveLength, beginY - peek, beginX + 4 * mWaveLength, beginY);
        mPathRight.quadTo(beginX + 5 * mWaveLength, beginY + peek, beginX + 6 * mWaveLength, beginY);
        mPathRight.quadTo(beginX + 7 * mWaveLength, beginY - peek, beginX + 8 * mWaveLength, beginY);
        mPathRight.quadTo(beginX + 9 * mWaveLength, beginY + peek, beginX + 10 * mWaveLength, beginY);
        mPathRight.lineTo(beginX + 10 * mWaveLength, mRectF.bottom);
        mPathRight.lineTo(beginX, mRectF.bottom);
        mPathRight.close();
    }

//        Log.d("DWave", "factor" + factor + "quadY=" + quadY);


    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mRectF.left, mRectF.top, mRectF.centerX(), mRectF.bottom);
        canvas.drawPath(mPathLeft, mPaint);
        canvas.restore();
        canvas.save();

        canvas.clipRect(mRectF.centerX(), mRectF.top, mRectF.right, mRectF.bottom);
        canvas.drawPath(mPathRight, mPaint);
        canvas.restore();

    }
}
