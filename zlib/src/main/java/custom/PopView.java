package custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zhy.android.R;

import base.BApp;
import util.MDrawable;
import util.ScreenUtils;

public class PopView extends LinearLayout {

    public PopView(String... items) {
        this(BApp.app().act());
        setBackground(MDrawable.solid(getResources().getColor(R.color.picture_color_grey), 10));
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(-2, -2);
        params.setMargins(dp(5),dp(5),dp(5),dp(5));
        setLayoutParams(params);
        setPadding(ScreenUtils.dip2px(10), 0, ScreenUtils.dip2px(10), 0);
        for (int i = 0; i < items.length; i++) {
            TextView view = new TextView(items[i]);
            view.setTextColor(0xffffffff);
            view.setId(i);
            view.setLayoutParams(new LayoutParams(dp(100), dp(40)));
            view.setGravity(Gravity.CENTER);
            if (i < items.length - 1)
                view.setBottomRes(R.drawable.line);
            int finalI = i;
//            view.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClick(finalI);
//                }
//            });
            addView(view);
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

    private int dp(float dipValue) {
        final float scale = BApp.app().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

//    protected abstract void onItemClick(int index);
}
