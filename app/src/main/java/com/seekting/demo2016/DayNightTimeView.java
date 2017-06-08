package com.seekting.demo2016;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static com.seekting.demo2016.AppEnv.bAppdebug;

/**
 * Created by seekting on 16-11-10.
 * <p>
 * 'rise' => "06:07",//日出时间
 * 'set' => "18:04",//日落时间
 */

public class DayNightTimeView extends View {

    public static final boolean DEBUG = bAppdebug;
    public static final String TAG = "DayNightTimeView";
    public static final float DEGREE_PER_MINUTE = 360 / (24 * 60f);
    public static final float DEGREE_OFF_SET = 90;
    private Drawable mBgDrawable;
    private PointF mCenterPoint = new PointF();
    private float mRadius;
    private float mDensity;
    private int mDayBg = 0xffffd715;
    private int mNightBg = 0xff1f9aff;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mDayArcRectF1 = new RectF();


    private float mDayBeginDegree = 0;
    private float mDayEndDegree = 180;
    public static PaintFlagsDrawFilter PFD = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    /**
     * 日出 日落
     *
     * @param rise 06:00
     * @param set
     */
    public void setSunRiseAndSet(String rise, String set) {
        mDayBeginDegree = 0;
        mDayEndDegree = 180;
        if (TextUtils.isEmpty(rise) || TextUtils.isEmpty(set)) {
            return;
        }
        int[] riseArray = getHoursAndMinutes(rise);
        if (riseArray == null) {
            return;
        }
        int[] setArray = getHoursAndMinutes(set);

        if (setArray == null) {
            return;
        }
        int minute = riseArray[0] * 60 + riseArray[1];
        if (DEBUG) {
            Log.d(TAG, "setSunRiseAndSet. from hour=" + minute);
        }
        mDayBeginDegree = minute * DEGREE_PER_MINUTE;
        minute = setArray[0] * 60 + setArray[1];
        if (DEBUG) {
            Log.d(TAG, "setSunRiseAndSet. to" +
                    " minute=" + minute);
        }
        mDayEndDegree = minute * DEGREE_PER_MINUTE;
        if (DEBUG) {
            Log.d(TAG, "setSunRiseAndSet.mDayBeginDegree=" + mDayBeginDegree + ",mDayEndDegree=" + mDayEndDegree);
        }

    }

    private static int[] getHoursAndMinutes(String time) {
        String[] list = time.split(":");
        if (list.length != 2) {
            return null;
        }
        int result[] = new int[2];
        result[0] = Integer.valueOf(list[0]);
        result[1] = Integer.valueOf(list[1]);
        return result;
    }

    public DayNightTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBgDrawable = context.getResources().getDrawable(R.mipmap.day_night_time_bg);
        mDensity = context.getResources().getDisplayMetrics().density;
        setSunRiseAndSet("00:00", "15:04");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int pareintWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (pareintWidth == 0) {
            return;
        }
        int width = mBgDrawable.getIntrinsicWidth();
        int height = mBgDrawable.getIntrinsicHeight();
        mBgDrawable.setBounds(0, 0, width, height);
        mCenterPoint.set(width / 2f, height / 2f);
        mRadius = mDensity * 64f;
        setArcRectF(mDayArcRectF1, mCenterPoint, mRadius);
        setMeasuredDimension(width, height);

    }

    private static void setArcRectF(RectF rectF, PointF center, float radius) {
        rectF.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.setDrawFilter(PFD);
        canvas.rotate(DEGREE_OFF_SET, mCenterPoint.x, mCenterPoint.y);
        mPaint.setColor(mNightBg);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint);
        mPaint.setColor(mDayBg);
        canvas.drawArc(mDayArcRectF1, mDayBeginDegree, mDayEndDegree - mDayBeginDegree, true, mPaint);
        canvas.restore();
        mBgDrawable.draw(canvas);
    }
}
