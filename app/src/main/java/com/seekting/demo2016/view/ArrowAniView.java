package com.seekting.demo2016.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ValueAnimator;
import com.seekting.demo2016.R;
import com.seekting.demo2016.util.ScreenUtil;

public class ArrowAniView extends View {

    public static final int arrow_size = 3;
    public static final PorterDuffColorFilter COLOR_FILTER = new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    private Bitmap bitmap;
    private int leftGap;
    private int oneWidth, oneHeight;
    private float mTopOffset, mLeftOffset;

    private int gap;
    private ValueAnimator valeAnimator;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private float mPercentX;
    private int mWrapContentWidth, mWrapContentHeight;
    private LinearGradient linearGradient;
    private Rect mDrawRect = new Rect();

    private int[] colors = new int[]{0x11000000, 0xff000000, 0x11000000};
    private float[] positions = new float[]{0f, 0.5f, 1f};
    private int mShaderWidth;
    private Matrix mMatrix = new Matrix();

    public ArrowAniView(Context context) {
        super(context, null);
        init(context);
    }

    public ArrowAniView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public ArrowAniView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(
                R.mipmap.arrow_right);
        bitmap = bitmapDrawable.getBitmap();
        oneWidth = ScreenUtil.dpToPxInt(context, 10);
        oneHeight = ScreenUtil.dpToPxInt(context, 16);
        leftGap = ScreenUtil.dpToPxInt(context, 12);
        gap = ScreenUtil.dpToPxInt(context, 12);
        valeAnimator = ValueAnimator.ofFloat(0, 1);
        valeAnimator.setInterpolator(new LinearInterpolator());
        valeAnimator.setRepeatCount(-1);
        valeAnimator.setDuration(2000);
        valeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                mPercentX = (Float) arg0.getAnimatedValue();

                invalidate(mDrawRect);
            }
        });
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = getSuggestWidth(widthMode, widthSize);
        int height = getSuggestHeight(heightMode, heightSize);

        mDrawRect.top = (int) (mTopOffset - 1);
        mDrawRect.left = (int) (mLeftOffset - 1);
        mDrawRect.bottom = mDrawRect.top + mWrapContentHeight + 1;
        mDrawRect.right = mDrawRect.left + mWrapContentWidth + 1;
        mShaderWidth = bitmap.getWidth() * 4;
        linearGradient = new LinearGradient(0, height / 2, mShaderWidth, height / 2, colors, positions, Shader.TileMode.CLAMP);
        setMeasuredDimension(width, height);

    }

    private int getSuggestWidth(int widthMode, int width) {
        int result;
        mWrapContentWidth = (oneWidth + gap) * arrow_size + leftGap;
        switch (widthMode) {
        case MeasureSpec.AT_MOST:
            result = mWrapContentWidth;
            break;
        case MeasureSpec.EXACTLY:
            result = width;
            if (result > mWrapContentWidth) {
//                居中显示
                mLeftOffset = (result - mWrapContentWidth) >> 1;

            } else {
                mLeftOffset = 0;
            }
            break;
        case MeasureSpec.UNSPECIFIED:
        default:
            result = width;
        }
        return result;

    }

    private int getSuggestHeight(int heightMode, int height) {
        int result;
        mWrapContentHeight = bitmap.getHeight();
        switch (heightMode) {
        case MeasureSpec.AT_MOST:
            result = mWrapContentHeight;
            break;
        case MeasureSpec.EXACTLY:
            result = height;
            if (result > mWrapContentHeight) {
                mTopOffset = (result - mWrapContentHeight) >> 1;
            } else {
                mTopOffset = 0;
            }
            break;
        case MeasureSpec.UNSPECIFIED:
        default:
            result = height;
        }
        return result;

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        valeAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valeAnimator.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sc = 0;
        boolean filter = true;
        if (filter) {
            sc = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.MATRIX_SAVE_FLAG
                    | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                    | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        }
        canvas.save();
        canvas.translate(leftGap + mLeftOffset, mTopOffset);
        int top = 0;
        int left = 0;
        paint.setColorFilter(COLOR_FILTER);
        for (int i = 0; i < arrow_size; i++) {
            left = i * (oneWidth + gap);
            canvas.drawBitmap(bitmap, left, top, paint);
        }
        paint.setColorFilter(null);
        canvas.restore();
        if (filter) {
            paint.setXfermode(porterDuffXfermode);
        }
        handleMask(canvas);
        if (filter) {
            canvas.restoreToCount(sc);
        }
        paint.setXfermode(null);
        paint.setShader(null);

    }


    private void handleMask(Canvas canvas) {
        float x = -mShaderWidth + mPercentX * (mWrapContentWidth + mShaderWidth) + mLeftOffset;
        canvas.save();
        mMatrix.reset();
        mMatrix.setTranslate(x, 0);
        linearGradient.setLocalMatrix(mMatrix);
        paint.setShader(linearGradient);
        canvas.drawRect(mDrawRect, paint);
        canvas.restore();
    }

}
