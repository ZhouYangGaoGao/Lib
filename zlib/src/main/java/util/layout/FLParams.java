package util.layout;

import android.widget.FrameLayout;

import base.BApp;

public class FLParams extends FrameLayout.LayoutParams {
    public FLParams(int width, int height) {
        super(px(width), px(height));
    }
    public static FLParams WW() {
        return new FLParams(-2, -2);
    }

    public static FLParams MM() {
        return new FLParams(-1, -1);
    }

    public static FLParams WM() {
        return new FLParams(-2, -1);
    }

    public static FLParams MW() {
        return new FLParams(-1, -2);
    }

    public static FLParams WW(int gravity) {
        return new FLParams(-2, -2, gravity);
    }

    public static FLParams MM(int gravity) {
        return new FLParams(-1, -1, gravity);
    }

    public static FLParams WM(int gravity) {
        return new FLParams(-2, -1, gravity);
    }

    public static FLParams MW(int gravity) {
        return new FLParams(-1, -2, gravity);
    }

    public FLParams(int width, int height, int gravity) {
        super(px(width), px(height), gravity);
    }

    public FLParams gravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public FLParams margin(int l, int t, int r, int b) {
        setMargins(l, t, r, b);
        return this;
    }

    public FLParams margin(int margin) {
        margin = px(margin);
        return margin(margin, margin, margin, margin);
    }

    public FLParams marginS(int start) {
        setMarginStart(px(start));
        return this;
    }

    public FLParams marginE(int end) {
        setMarginEnd(px(end));
        return this;
    }

    public FLParams direction(int direction) {
        setLayoutDirection(direction);
        return this;
    }

    public FLParams marginH(int margin) {
        margin = px(margin);
        return margin(margin, 0, margin, 0);
    }

    public FLParams marginV(int margin) {
        margin = px(margin);
        return margin(0, margin, 0, margin);
    }

    public FLParams marginL(int margin) {
        margin = px(margin);
        return margin(margin, 0, 0, 0);
    }

    public FLParams marginT(int margin) {
        margin = px(margin);
        return margin(0, margin, 0, 0);
    }

    public FLParams marginR(int margin) {
        margin = px(margin);
        return margin(0, 0, margin, 0);
    }

    public FLParams marginB(int margin) {
        margin = px(margin);
        return margin(0, 0, 0, margin);
    }

    private static int px(float dipValue) {
        if (dipValue < 0) return (int) dipValue;
        final float scale = BApp.app().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
