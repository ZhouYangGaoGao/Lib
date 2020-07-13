package util;

import android.widget.LinearLayout;

public class LayoutUtil {
    public static LinearLayout.LayoutParams zoomVLp() {
        return new LinearLayout.LayoutParams(-1, 0, 1);
    }

    public static LinearLayout.LayoutParams zoomHLp() {
        return new LinearLayout.LayoutParams(0, 1, 1);
    }
}
