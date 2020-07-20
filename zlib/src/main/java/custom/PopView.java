package custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zhy.android.R;

import base.BApp;
import util.MDrawable;
import util.ScreenUtils;

public abstract class PopView extends LinearLayout {


    public PopView(String... items) {
        this(BApp.app().act());
        setBackground(MDrawable.solid(getResources().getColor(R.color.picture_color_grey),15));
        setPadding(ScreenUtils.dip2px(10),0,ScreenUtils.dip2px(10),0);
        for (int i = 0; i < items.length; i++) {
//            new TextView()
        }
    }

    public PopView(Context context) {
        this(context, null);
    }

    public PopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    abstract void onItemClick (int index);
}
