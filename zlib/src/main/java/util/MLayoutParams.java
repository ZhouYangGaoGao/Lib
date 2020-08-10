package util;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MLayoutParams {
    public static LinearLayout.LayoutParams marginLLP(int startDp, int endDp) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.setMarginEnd(ScreenUtils.dip2px(endDp));
        params.setMarginStart(ScreenUtils.dip2px(startDp));
        return params;
    }

    public static LinearLayout.LayoutParams marginLLP(int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, height);
        return params;
    }

    public static RelativeLayout.LayoutParams marginRLP(int marginDp) {
        marginDp = ScreenUtils.dip2px(marginDp);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.setMargins(marginDp, marginDp, marginDp, marginDp);
        return params;
    }

    public static RelativeLayout.LayoutParams marginRLP(int w, int h, int marginDp) {
        marginDp = ScreenUtils.dip2px(marginDp);
        if (w > 0) w = ScreenUtils.dip2px(w);
        if (h > 0) h = ScreenUtils.dip2px(h);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w, h);
        params.setMargins(marginDp, marginDp, marginDp, marginDp);
        return params;
    }
}
