package com.seekting.demo2016.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by seekting on 16-12-12.
 */

public class RectView extends View {
    boolean isCircle = false;

    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isCircle) {

            Paint paint = new Paint();

            int centerX = getMeasuredWidth() / 2;
            int centerY = getMeasuredHeight() / 2;
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(centerX, centerY, centerX, paint);
        } else {
            canvas.drawColor(Color.RED);
        }
    }

    public void setCircle() {

        isCircle = true;

    }
}
