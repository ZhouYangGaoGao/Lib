package util;

import android.widget.LinearLayout;

public class MLayoutParams {
    public static LinearLayout.LayoutParams marginLLP(int startDp,int endDp){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.setMarginEnd(ScreenUtils.dip2px(endDp));
        params.setMarginStart(ScreenUtils.dip2px(startDp));
        return params;
    }

    public static LinearLayout.LayoutParams marginLLP(int height){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, height);
        return params;
    }
}
