package custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.android.R;

import util.MLayoutParams;

public class StatusView extends RelativeLayout {
    private AVLoadingIndicatorView avl;
    private TextView tv;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        avl = new AVLoadingIndicatorView(context);
        avl.setIndicatorColor(getResources().getColor(R.color.clo_theme_88));
        avl.setLayoutParams(MLayoutParams.marginRLP(40,40,50));
        addView(avl);
        tv = new TextView(context);
        tv.setText(getContext().getString(R.string.str_empty_data));
        tv.setTopRes(R.drawable.ic_no_data);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(MLayoutParams.marginRLP(50));
        tv.setTextColor(getResources().getColor(R.color.clo_no_data));
        tv.setVisibility(GONE);
        addView(tv);
    }

    public void loading(){
        avl.show();
        tv.setVisibility(GONE);
    }

    public void empty(){
        tv.setVisibility(VISIBLE);
        avl.hide();
    }

    public AVLoadingIndicatorView getAvl() {
        return avl;
    }

    public TextView getTv() {
        return tv;
    }
}
