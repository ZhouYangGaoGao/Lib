package custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import background.drawable.DrawableCreator;
import background.drawable.DrawableFactory;
import util.ScreenUtils;

public class TextView extends androidx.appcompat.widget.AppCompatTextView {
    public int drawableIndex = -1;
    private boolean isAutoZoom;


    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            drawableIndex = -1;
            // getCompoundDrawables获取是一个数组，数组0,1,2,3,对应着左，上，右，下 这4个位置的图片，如果没有就为null
            Drawable leftDrawable = getCompoundDrawables()[0];
            Drawable topDrawable = getCompoundDrawables()[1];
            Drawable rightDrawable = getCompoundDrawables()[2];
            Drawable bottomDrawable = getCompoundDrawables()[3];
            //判断的依据是获取点击区域相对于屏幕的x值比我(获取TextView的最右边界减去边界宽度)大就可以判断点击在Drawable上
            if (leftDrawable != null && event.getX() <= (leftDrawable.getBounds().width() + getCompoundDrawablePadding())) {
                drawableIndex = 0;
            }
            if (topDrawable != null && event.getY() <= (topDrawable.getBounds().height() + getCompoundDrawablePadding())) {
                drawableIndex = 1;
            }
            if (rightDrawable != null && event.getX() >= (getWidth() - rightDrawable.getBounds().width() - getCompoundDrawablePadding())) {
                drawableIndex = 2;
            }
            if (bottomDrawable != null && event.getY() >= (getHeight() - bottomDrawable.getBounds().height() - getCompoundDrawablePadding())) {
                drawableIndex = 3;
            }
        }
        return super.onTouchEvent(event);
    }

    public TextView setLeftRes(int... resId) {
        setLeftDrawable(getDrawables(resId));
        return this;
    }

    private Drawable[] getDrawables(int[] resId) {
        Drawable[] drawable = new Drawable[resId.length];
        for (int i = 0; i < resId.length; i++) {
            drawable[i] = getResources().getDrawable(resId[i]);
        }
        return drawable;
    }

    private Drawable getDrawable(Drawable... drawable) {
        if (drawable.length == 9) return null;
        if (drawable.length > 1) {
            return new DrawableCreator.Builder()
                    .setPressedDrawable(drawable[0])
                    .setUnPressedDrawable(drawable[1]).build();
        } else return drawable[0];
    }

    public TextView setTopRes(int... resId) {
        setTopDrawable(getDrawables(resId));
        return this;
    }

    public TextView setRightRes(int... resId) {
        setRightDrawable(getDrawables(resId));
        return this;
    }

    public TextView setBottomRes(int... resId) {
        setBottomDrawable(getDrawables(resId));
        return this;
    }

    public TextView setLeftDrawable(Drawable... drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getDrawable(drawable), getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
        return this;
    }

    public TextView setTopDrawable(Drawable... drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getDrawable(drawable), getCompoundDrawables()[2], getCompoundDrawables()[3]);
        return this;
    }

    public TextView setRightDrawable(Drawable... drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], getDrawable(drawable), getCompoundDrawables()[3]);
        return this;
    }

    public TextView setBottomDrawable(Drawable... drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], getCompoundDrawables()[2], getDrawable(drawable));
        return this;
    }

    public void setRes(int drawableIndex, int resId) {
        switch (drawableIndex) {
            case 0:
                setLeftRes(resId);
                break;
            case 1:
                setTopRes(resId);
                break;
            case 2:
                setRightRes(resId);
                break;
            case 3:
                setBottomRes(resId);
                break;
        }
    }

    public void setAutoZoom(boolean autoZoom) {
        isAutoZoom = autoZoom;
    }

    public void setRipple(int rippleColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(new DrawableCreator.Builder()
                    .setPressedSolidColor(0x11ffffff, 0x00ffffff)
                    .setRipple(true, rippleColor).build());
        }
    }

    public void setRipple(int drawableIndex, int rippleColor) {
        switch (drawableIndex) {
            case 0:
                setLeftDrawable(new DrawableCreator.Builder()
                        .setUnPressedDrawable(getCompoundDrawables()[0])
                        .setRipple(true, rippleColor).build());
                break;
            case 1:
                setTopDrawable(new DrawableCreator.Builder()
                        .setUnPressedDrawable(getCompoundDrawables()[1])
                        .setRipple(true, rippleColor).build());
                break;
            case 2:
                setRightDrawable(new DrawableCreator.Builder()
                        .setUnPressedDrawable(getCompoundDrawables()[2])
                        .setRipple(true, rippleColor).build());
                break;
            case 3:
                setBottomDrawable(new DrawableCreator.Builder()
                        .setUnPressedDrawable(getCompoundDrawables()[3])
                        .setRipple(true, rippleColor).build());
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRipple(rippleColor);
                }
        }
    }


    public void setMarquee() {
        setSingleLine();
        setHorizontalFadingEdgeEnabled(true);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isAutoZoom) {
            float defaultTextSize = 0;
            int maxWidth = 0;
            TextPaint paint = getPaint();
            if (defaultTextSize == 0.0f) {
                defaultTextSize = getTextSize();
            }
            float textSize = defaultTextSize;
            paint.setTextSize(textSize);
            if (maxWidth == 0)
                maxWidth = getWidth();
            float textViewWidth = maxWidth - getPaddingLeft() - getPaddingRight();//不包含左右padding的空间宽度
            String text = getText().toString();
            float textWidth = paint.measureText(text);
            while (textWidth > textViewWidth) {
                if (textWidth - textViewWidth < 50) {
                    textSize -= 0.01;
                } else {
                    textSize -= 0.1;
                }
                paint.setTextSize(textSize);
                textWidth = paint.measureText(text);
            }
        }
        super.onDraw(canvas);
    }

    public void setDrawablePadding(int dp) {
        setCompoundDrawablePadding(ScreenUtils.dip2px(dp));
    }
}
