package custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TextView extends androidx.appcompat.widget.AppCompatTextView {
    public int drawableIndex = -1;

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

    public TextView setLeftRes(int resId) {
        setLeftDrawable(getResources().getDrawable(resId));
        return this;
    }

    public TextView setTopRes(int resId) {
        setTopDrawable(getResources().getDrawable(resId));
        return this;
    }

    public TextView setRightRes(int resId) {
        setRightDrawable(getResources().getDrawable(resId));
        return this;
    }

    public TextView setBottomRes(int resId) {
        setBottomDrawable(getResources().getDrawable(resId));
        return this;
    }

    public TextView setLeftDrawable(Drawable drawable) {
        setCompoundDrawablesWithIntrinsicBounds(drawable, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
        return this;
    }

    public TextView setTopDrawable(Drawable drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], drawable, getCompoundDrawables()[2], getCompoundDrawables()[3]);
        return this;
    }

    public TextView setRightDrawable(Drawable drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], drawable, getCompoundDrawables()[3]);
        return this;
    }

    public TextView setBottomDrawable(Drawable drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], getCompoundDrawables()[2], drawable);
        return this;
    }

}
