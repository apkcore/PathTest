package com.apkcore.pathtest.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 贝赛尔曲线
 * Created by Apkcore on 2017/3/20.
 */

public class WaveView extends View {
    private Paint mPaint, mPaint2;
    private Path mPath, mPath2;
    private PaintFlagsDrawFilter pfd;
    private int mWaveCount;

    private int offset;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mWL = 1000;
    private int mCenterY;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAnimator();
    }

    private void initAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWL);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#59c3e2"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(10);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.parseColor("#59c3e2"));
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(10);

        mPath = new Path(); //创建Path对象
        mPath2 = new Path(); //创建Path对象
        pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆
        canvas.drawCircle(mScreenWidth / 2, mCenterY, 300, mPaint2);

        mPath.reset();
        mPath.moveTo(-mWL + offset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo((-mWL * 3 / 4) + (i * mWL) + offset, mCenterY + 60, (-mWL / 2) + (i * mWL) + offset, mCenterY);
            mPath.quadTo((-mWL / 4) + (i * mWL) + offset, mCenterY - 60, i * mWL + offset, mCenterY);
        }
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();

        mPath2.addCircle(mScreenWidth / 2, mCenterY, 300, Path.Direction.CCW);
        //api19以上才能用
//        mPath.op(mPath2, Path.Op.INTERSECT);
        //改用canvas.clipPath
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        canvas.setDrawFilter(pfd);
        canvas.drawPath(mPath2, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        mWaveCount = (int) Math.round(mScreenWidth / mWL + 1.5);
        mCenterY = mScreenHeight / 2;
    }
}
