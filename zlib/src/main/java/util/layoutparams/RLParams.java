package util.layoutparams;

import android.widget.RelativeLayout;

import base.BApp;

public class RLParams extends RelativeLayout.LayoutParams {
    public RLParams(int w, int h) {
        super(w, h);
    }

    public RLParams rule(int... verbs) {
        for (int i : verbs) {
            addRule(i);
        }
        return this;
    }

    public RLParams subject(int verb, int subject) {
        addRule(verb, subject);
        return this;
    }

    public static RLParams WW() {
        return new RLParams(-2, -2);
    }

    public static RLParams MM() {
        return new RLParams(-1, -1);
    }

    public static RLParams WM() {
        return new RLParams(-2, -1);
    }

    public static RLParams MW() {
        return new RLParams(-1, -2);
    }

    public RLParams margin(int l, int t, int r, int b) {
        setMargins(l, t, r, b);
        return this;
    }

    public RLParams margin(int margin) {
        margin = px(margin);
        return margin(margin, margin, margin, margin);
    }

    public RLParams marginStart(int start) {
        setMarginStart(start);
        return this;
    }

    public RLParams marginEnd(int end) {
        setMarginEnd(end);
        return this;
    }

    public RLParams direction(int direction) {
        setLayoutDirection(direction);
        return this;
    }

    public RLParams marginH(int margin) {
        margin = px(margin);
        return margin(margin, 0, margin, 0);
    }

    public RLParams marginV(int margin) {
        margin = px(margin);
        return margin(0, margin, 0, margin);
    }

    public RLParams marginL(int margin) {
        margin = px(margin);
        return margin(margin, 0, 0, 0);
    }

    public RLParams marginT(int margin) {
        margin = px(margin);
        return margin(0, margin, 0, 0);
    }

    public RLParams marginR(int margin) {
        margin = px(margin);
        return margin(0, 0, margin, 0);
    }

    public RLParams marginB(int margin) {
        margin = px(margin);
        return margin(0, 0, 0, margin);
    }

    private static int px(float dipValue) {
        if (dipValue < 0) return (int) dipValue;
        final float scale = BApp.app().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
