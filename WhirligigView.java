package com.lsmya.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 旋转的圆
 */
public class WhirligigView extends View {

    private Paint paint;
    private int mTextColor = Color.WHITE;
    private int angle = 0;
    private int num = 6;
    private int speed = 2;
    private int radius;

    private int width;

    public WhirligigView(Context context) {
        this(context, null);
    }

    public WhirligigView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WhirligigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        //高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = (int) (getMeasuredWidth() / 3.5);
        radius = (int) (width * 0.4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(mTextColor);
        for (int i = 0; i < 6; i++) {
            canvas.save();
            canvas.rotate(360 / num * i + angle, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
            canvas.drawCircle(width, width, radius, paint);
            canvas.restore();
        }

        angle += speed;
        if (angle == 360) {
            angle = 0;
        }
        OnRotateChangeListener();
        invalidate();
    }

    /**
     * 设置圆的数量
     */
    public void setNumber(int number) {
        this.num = number;
    }

    /**
     * 设置控件的速度
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * 设置小圆的颜色
     */
    public void setColor(int color) {
        mTextColor = color;
    }

    /**
     * 旋转监听
     */
    public void OnRotateChangeListener() {

    }
}
