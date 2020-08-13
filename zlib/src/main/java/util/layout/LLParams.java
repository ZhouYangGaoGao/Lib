package util.layout;

import android.widget.LinearLayout;

import base.BApp;

/**
 * W : WRAP_CONTENT
 * M : MATCH_PARENT
 * Z : ZOOM weight 无参为默认为1
 * G : gravity
 */
public class LLParams extends LinearLayout.LayoutParams {

    public LLParams(int width, int height) {
        super(px(width), px(height));
    }

    public static LLParams WW() {
        return new LLParams(-2, -2);
    }

    public static LLParams MM() {
        return new LLParams(-1, -1);
    }

    public static LLParams WM() {
        return new LLParams(-2, -1);
    }

    public static LLParams MW() {
        return new LLParams(-1, -2);
    }

    public static LLParams ZW(int weight) {
        return new LLParams(0, -2, weight);
    }

    public static LLParams ZM(int weight) {
        return new LLParams(0, -1, weight);
    }

    public static LLParams WZ(int weight) {
        return new LLParams(-2, 0, weight);
    }

    public static LLParams MZ(int weight) {
        return new LLParams(-1, 0, weight);
    }

    public static LLParams ZW() {
        return new LLParams(0, -2, 1);
    }

    public static LLParams ZM() {
        return new LLParams(0, -1, 1);
    }

    public static LLParams WZ() {
        return new LLParams(-2, 0, 1);
    }

    public static LLParams MZ() {
        return new LLParams(-1, 0, 1);
    }

    public static LLParams WW(int gravity) {
        return new LLParams(-2, -2).gravity(gravity);
    }

    public static LLParams MM(int gravity) {
        return new LLParams(-1, -1).gravity(gravity);
    }

    public static LLParams WM(int gravity) {
        return new LLParams(-2, -1).gravity(gravity);
    }

    public static LLParams MW(int gravity) {
        return new LLParams(-1, -2).gravity(gravity);
    }

    public static LLParams ZW(int weight, int gravity) {
        return new LLParams(0, -2, weight).gravity(gravity);
    }

    public static LLParams ZM(int weight, int gravity) {
        return new LLParams(0, -1, weight).gravity(gravity);
    }

    public static LLParams WZ(int weight, int gravity) {
        return new LLParams(-2, 0, weight).gravity(gravity);
    }

    public static LLParams MZ(int weight, int gravity) {
        return new LLParams(-1, 0, weight).gravity(gravity);
    }

    public static LLParams ZWG(int gravity) {
        return new LLParams(0, -2, 1).gravity(gravity);
    }

    public static LLParams ZMG(int gravity) {
        return new LLParams(0, -1, 1).gravity(gravity);
    }

    public static LLParams WZG(int gravity) {
        return new LLParams(-2, 0, 1).gravity(gravity);
    }

    public static LLParams MZG(int gravity) {
        return new LLParams(-1, 0, 1).gravity(gravity);
    }

    public LLParams(int width, int height, float weight) {
        super(px(width), px(height), weight);
    }

    public LLParams weight(int weight) {
        this.weight = weight;
        return this;
    }

    public LLParams gravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public LLParams margin(int l, int t, int r, int b) {
        setMargins(l, t, r, b);
        return this;
    }

    public LLParams margin(int margin) {
        margin = px(margin);
        return margin(margin, margin, margin, margin);
    }

    public LLParams marginS(int start) {
        setMarginStart(px(start));
        return this;
    }

    public LLParams marginE(int end) {
        setMarginEnd(px(end));
        return this;
    }

    public LLParams direction(int direction) {
        setLayoutDirection(direction);
        return this;
    }

    public LLParams marginH(int margin) {
        margin = px(margin);
        return margin(margin, 0, margin, 0);
    }

    public LLParams marginV(int margin) {
        margin = px(margin);
        return margin(0, margin, 0, margin);
    }

    public LLParams marginL(int margin) {
        margin = px(margin);
        return margin(margin, 0, 0, 0);
    }

    public LLParams marginT(int margin) {
        margin = px(margin);
        return margin(0, margin, 0, 0);
    }

    public LLParams marginR(int margin) {
        margin = px(margin);
        return margin(0, 0, margin, 0);
    }

    public LLParams marginB(int margin) {
        margin = px(margin);
        return margin(0, 0, 0, margin);
    }

    private static int px(float dipValue) {
        if (dipValue < 0) return (int) dipValue;
        final float scale = BApp.app().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
