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
import android.view.ViewGroup;
import android.view.textservice.TextInfo;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import background.drawable.DrawableCreator;
import background.drawable.DrawableFactory;
import base.BApp;
import util.MDrawable;
import util.ScreenUtils;

public class TextView extends androidx.appcompat.widget.AppCompatTextView {
    public int drawableIndex = -1;
    private boolean isAutoZoom;
    private boolean textClickable = true;

    public TextView(String text) {
        this(BApp.app().act());
    }

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
        return !textClickable && drawableIndex == -1 ? false:super.onTouchEvent(event);
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
        if (drawable.length == 0) return null;
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

    public TextView setRes(int drawableIndex, int resId) {
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
        return this;
    }

    public TextView setTextRes(int res) {
        super.setText(res);
        return this;
    }

    public TextView setText(String text) {
        super.setText(text);
        return this;
    }

    public TextView setAutoZoom(boolean autoZoom, int... lines) {
        isAutoZoom = autoZoom;
        if (lines.length > 0) setMaxLines(lines[0]);
        else if (autoZoom) setSingleLine();
        return this;
    }

    public TextView setRipple(int rippleColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(new DrawableCreator.Builder()
                    .setPressedSolidColor(0x11ffffff, 0x00ffffff)
                    .setRipple(true, rippleColor).build());
        }
        return this;
    }

    public TextView setRipple(int drawableIndex, int rippleColor) {
        Drawable drawable = new DrawableCreator.Builder()
                .setUnPressedDrawable(getCompoundDrawables()[drawableIndex])
                .setRipple(true, rippleColor).build();
        switch (drawableIndex) {
            case 0:
                setLeftDrawable(drawable);
                break;
            case 1:
                setTopDrawable(drawable);
                break;
            case 2:
                setRightDrawable(drawable);
                break;
            case 3:
                setBottomDrawable(drawable);
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRipple(rippleColor);
                }
        }
        return this;
    }

    public TextView setEllipsize(TextUtils.TruncateAt where, int... lines) {
        if (lines.length > 0) {
            setMaxLines(lines[0]);
        }
        if (where.equals(TextUtils.TruncateAt.MARQUEE)) {
            setSingleLine();
            setHorizontalFadingEdgeEnabled(true);
            setMarqueeRepeatLimit(-1);
        }
        super.setEllipsize(where);
        return this;
    }

    public TextView setMarquee(int repeat) {
        setSingleLine();
        setHorizontalFadingEdgeEnabled(true);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(repeat);
        return this;
    }

    public TextView setMBackground(Drawable background) {
        super.setBackground(background);
        return this;
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
            float textViewWidth = maxWidth - getTotalPaddingRight() - getTotalPaddingLeft();//不包含左右padding的空间宽度
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

    public TextView setDrawablePadding(int dp) {
        setCompoundDrawablePadding(ScreenUtils.dip2px(dp));
        return this;
    }

    public TextView tagStyle(int cloStroke, int cloText, int sizeSp) {
        setBackground(MDrawable.tag(cloStroke, 2));
        setTextSize(sizeSp);
        if (cloStroke == 0)
            setPadding(0, ScreenUtils.dip2px(1), 0, ScreenUtils.dip2px(1));
        else
            setPadding(ScreenUtils.dip2px(4), ScreenUtils.dip2px(1), ScreenUtils.dip2px(4), ScreenUtils.dip2px(1));
        setTextColor(cloText);
        return this;
    }

    public TextView tagStyle(int cloStroke, int cloText, int radius, int sizeSp) {
        setBackground(MDrawable.tag(cloStroke, radius));
        setTextSize(sizeSp);
        if (cloStroke == 0)
            setPadding(0, ScreenUtils.dip2px(1), 0, ScreenUtils.dip2px(1));
        else
            setPadding(ScreenUtils.dip2px(4), ScreenUtils.dip2px(1), ScreenUtils.dip2px(4), ScreenUtils.dip2px(1));
        setTextColor(cloText);
        return this;
    }

    public TextView tagStyle(int clo, int sizeSp) {
        setBackground(MDrawable.tag(clo, 1));
        setTextSize(sizeSp);
        if (clo == 0)
            setPadding(0, ScreenUtils.dip2px(1), 0, ScreenUtils.dip2px(1));
        else
            setPadding(ScreenUtils.dip2px(4), ScreenUtils.dip2px(1), ScreenUtils.dip2px(4), ScreenUtils.dip2px(1));
        setTextColor(clo);
        return this;
    }

    public TextView setLayout(ViewGroup.LayoutParams layout) {
        setLayoutParams(layout);
        return this;
    }

    public void setTextClickable(boolean textClickable) {
        this.textClickable = textClickable;
    }
}
