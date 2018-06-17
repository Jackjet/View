
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 旋转的圆
 */
public class ProgressView extends View {

    private Paint paint;
    private int mTextColor = Color.GRAY;
    private int mWidth = dip2px(getContext(), 5);
    private int change = 0;
    private long millis = 500;
    private boolean isStop = false;
    private boolean isWrite = false;
    private String text = "";
    private int mWidth1;
    private int mHeight;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        mWidth1 = mWidth * 8;
        mHeight = mWidth * 3;
        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth1, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth1, mHeight);
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
            setMeasuredDimension(widthMeasureSpec, mHeight);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(mTextColor);
        if (isWrite) {
            paint.setTextSize(dip2px(getContext(), 16));
            canvas.drawText(text, mWidth1 / 2, mHeight / 2, paint);
        } else {
            paint.setColor(mTextColor);
            if (change % 3 == 0) {
                canvas.drawCircle(getWidth() / 2 - mWidth * 4, getMeasuredHeight() / 2, mWidth * 0.7f, paint);
                canvas.drawCircle(getWidth() / 2, getMeasuredHeight() / 2, mWidth * 0.8f, paint);
                canvas.drawCircle(getWidth() / 2 + mWidth * 4, getMeasuredHeight() / 2, mWidth, paint);
            } else if (change % 3 == 1) {
                canvas.drawCircle(getWidth() / 2 - mWidth * 4, getMeasuredHeight() / 2, mWidth, paint);
                canvas.drawCircle(getWidth() / 2, getMeasuredHeight() / 2, mWidth * 0.7f, paint);
                canvas.drawCircle(getWidth() / 2 + mWidth * 4, getMeasuredHeight() / 2, mWidth * 0.8f, paint);
            } else if (change % 3 == 2) {
                canvas.drawCircle(getWidth() / 2 - mWidth * 4, getMeasuredHeight() / 2, mWidth * 0.8f, paint);
                canvas.drawCircle(getWidth() / 2, getMeasuredHeight() / 2, mWidth, paint);
                canvas.drawCircle(getWidth() / 2 + mWidth * 4, getMeasuredHeight() / 2, mWidth * 0.7f, paint);
            }
        }
    }

    public void startAnim() {
        isWrite = false;
        isStop = true;
        change = 1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (change > 0) {
                    change++;
                    postInvalidate();
                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isStop) {
                        change = 0;
                    }
                }
            }
        }).start();
    }

    public void stopAnim() {
        isStop = false;
    }

    public void setText(String text) {
        stopAnim();
        isWrite = true;
        invalidate();

    }

    /**
     * 设置圆的半径
     * size：dp
     * 默认5dp
     */
    public void setSize(int size) {
        this.mWidth = dip2px(getContext(), size);
    }

    /**
     * 设置小圆的颜色
     * 默认黑色
     */
    public void setColor(int color) {
        mTextColor = color;
    }

    /**
     * 设置变化的间隔
     * millis：毫秒
     * 默认每500毫秒变化一次
     */
    public void setChangeTime(long millis) {
        this.millis = millis;
    }


    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
