package com.apkcore.pathtest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.apkcore.pathtest.R;

/**
 * 简单使用
 * Created by Apkcore on 2017/3/26.
 */

public class SimpleView extends View {
    private Path mPath;
    private Paint mPaint;

    public SimpleView(Context context) {
        this(context, null);
    }

    public SimpleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);
        mPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 线的操作
         */
        mPath.moveTo(100, 100);
        mPath.lineTo(300, 300);
        mPath.lineTo(100, 300);
        mPath.lineTo(100, 100);
        mPath.moveTo(300, 300);
        mPath.lineTo(500, 300);
        mPath.setLastPoint(500, 500);//改变上一次操作路径的结束坐标点
        mPath.lineTo(100, 500);
        mPath.close();

        /**
         *  基本形状
         */
        RectF rectF = new RectF(100,800,200,900);
        mPath.addRect(rectF, Path.Direction.CW);
        mPath.setLastPoint(150,1000);

        mPath.addCircle(500,1000,200, Path.Direction.CW);

        rectF = new RectF(100,1200,500,1700);
        mPath.addRoundRect(rectF,100,100, Path.Direction.CW);//圆角的两个半径


        canvas.drawPath(mPath, mPaint);//画出路径
    }
}
