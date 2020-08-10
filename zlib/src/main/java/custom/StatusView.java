package custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.android.R;

import base.BApp;
import util.MLayoutParams;
import util.Resource;

public class StatusView extends RelativeLayout implements View.OnClickListener {
    private AVLoadingIndicatorView lv;
    private TextView tv;
    private String emptyStr;
    private String errorStr;
    private String loadingStr;
    private int loadingColor;
    private Drawable emptyIcon;
    private Drawable errorIcon;
    private int defaultModel;

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
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.StatusView);
        loadingStr = getResources().getStringArray(R.array.load_name)[t.getInt(R.styleable.StatusView_loadingIndex, 25)];
        errorStr = t.getString(R.styleable.StatusView_errorText);
        emptyStr = t.getString(R.styleable.StatusView_emptyText);
        loadingColor = t.getColor(R.styleable.StatusView_loadingColor, getResources().getColor(R.color.clo_theme_88));
        emptyIcon = t.getDrawable(R.styleable.StatusView_emptySrc);
        errorIcon = t.getDrawable(R.styleable.StatusView_errorSrc);
        defaultModel = t.getInt(R.styleable.StatusView_defaultModel, 3);
        t.recycle();

        if (errorStr == null) errorStr = getResources().getString(R.string.str_error_data);
        if (emptyStr == null) emptyStr = getResources().getString(R.string.str_empty_data);
        if (errorIcon == null) errorIcon = getResources().getDrawable(R.drawable.ic_error);
        if (emptyIcon == null) emptyIcon = getResources().getDrawable(R.drawable.ic_no_data);

        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        initLoadingView(context);
        initTextView(context);

        switch (defaultModel) {
            case 1:
                empty();
                break;
            case 2:
                error();
                break;
            case 3:
                loading();
                break;
        }
    }

    private void initLoadingView(Context context) {
        lv = new AVLoadingIndicatorView(context);
        lv.setIndicator(loadingStr);
        lv.setIndicatorColor(loadingColor);
        int margin = dip2px(50);
        LayoutParams params = new LayoutParams(margin, margin);
        params.setMargins(margin, margin, margin, margin);
        lv.setLayoutParams(params);
        addView(lv);
    }

    private void initTextView(Context context) {
        tv = new TextView(context);
        tv.setText(emptyStr);
        tv.setTopDrawable(emptyIcon);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.clo_no_data));
        tv.setOnClickListener(this);
        addView(tv);
    }

    public StatusView loading() {
        return loading(loadingStr);
    }

    public StatusView empty() {
        return empty(emptyStr);
    }

    public StatusView error() {
        return error(errorStr);
    }

    public StatusView empty(String emptyStr) {
        return empty(emptyStr, emptyIcon);
    }

    public StatusView error(String errorStr) {
        return error(errorStr, errorIcon);
    }

    public StatusView empty(Drawable emptyIcon) {
        return empty(emptyStr, emptyIcon);
    }

    public StatusView error(Drawable errorIcon) {
        return error(errorStr, errorIcon);
    }

    public StatusView loading(String loadingName) {
        if (!this.loadingStr.equals(loadingName))
            lv.setIndicator(loadingName);
        lv.setVisibility(VISIBLE);
        tv.setVisibility(GONE);
        return this;
    }

    public StatusView empty(String emptyStr, Drawable emptyIcon) {
        tv.setVisibility(VISIBLE);
        tv.setTopDrawable(emptyIcon);
        tv.setText(emptyStr);
        lv.setVisibility(GONE);
        return this;
    }

    public StatusView error(String errorStr, Drawable errorIcon) {
        tv.setVisibility(VISIBLE);
        tv.setTopDrawable(errorIcon);
        tv.setText(errorStr);
        lv.hide();
        return this;
    }

    public AVLoadingIndicatorView getLoadingView() {
        return lv;
    }

    public TextView getTextView() {
        return tv;
    }

    @Override
    public void onClick(View v) {
        loading();
    }

    public StatusView setEmptyStr(String emptyStr) {
        this.emptyStr = emptyStr;
        return this;
    }

    public StatusView setErrorStr(String errorStr) {
        this.errorStr = errorStr;
        return this;
    }

    public StatusView setLoadingStr(String loadingStr) {
        this.loadingStr = loadingStr;
        return this;
    }

    public StatusView setEmptyIcon(Drawable emptyIcon) {
        this.emptyIcon = emptyIcon;
        return this;
    }

    public StatusView setEmptyIcon(int emptyIcon) {
        this.emptyIcon = Resource.drawable(emptyIcon);
        return this;
    }

    public StatusView setErrorIcon(Drawable errorIcon) {
        this.errorIcon = errorIcon;
        return this;
    }

    public StatusView setErrorIcon(int errorIcon) {
        this.errorIcon = Resource.drawable(errorIcon);
        return this;
    }

    public StatusView setLoadingColor(int loadingColor) {
        this.loadingColor = loadingColor;
        return this;
    }

    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
