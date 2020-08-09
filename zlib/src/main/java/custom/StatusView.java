package custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.android.R;

import util.MLayoutParams;
import util.Resource;

public class StatusView extends RelativeLayout implements View.OnClickListener {
    private AVLoadingIndicatorView lv;
    private TextView tv;
    private String emptyStr;
    private String errorStr;
    private String loadingStr;
    private int emptyIcon;
    private int errorIcon;

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
        initLoadingView(context);
        initTextView(context);
    }

    private void initLoadingView(Context context) {
        lv = new AVLoadingIndicatorView(context);
        lv.setIndicator(loadingStr = Resource.stringArray(R.array.load_name)[25]);
        lv.setIndicatorColor(getResources().getColor(R.color.clo_theme_88));
        lv.setLayoutParams(MLayoutParams.marginRLP(50, 50, 50));
        addView(lv);
    }

    private void initTextView(Context context) {
        tv = new TextView(context);
        errorStr = Resource.string(R.string.str_error_data);
        errorIcon = R.drawable.ic_error;
        tv.setText(emptyStr = Resource.string(R.string.str_empty_data));
        tv.setTopRes(emptyIcon = R.drawable.ic_no_data);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(MLayoutParams.marginRLP(50));
        tv.setTextColor(Resource.color(R.color.clo_no_data));
        tv.setVisibility(GONE);
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

    public StatusView empty(int emptyIcon) {
        return empty(emptyStr, emptyIcon);
    }

    public StatusView error(int errorIcon) {
       return error(errorStr, errorIcon);
    }

    public StatusView loading(String loadingName) {
        if (!this.loadingStr.equals(loadingName))
            lv.setIndicator(loadingName);
        lv.setVisibility(VISIBLE);
        tv.setVisibility(GONE);return this;
    }

    public StatusView empty(String emptyStr, int emptyIcon) {
        tv.setVisibility(VISIBLE);
        tv.setTopRes(emptyIcon);
        tv.setText(emptyStr);
        lv.setVisibility(GONE);return this;
    }

    public StatusView error(String errorStr, int errorIcon) {
        tv.setVisibility(VISIBLE);
        tv.setTopRes(errorIcon);
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

    public StatusView setEmptyIcon(int emptyIcon) {
        this.emptyIcon = emptyIcon;
        return this;
    }

    public StatusView setErrorIcon(int errorIcon) {
        this.errorIcon = errorIcon;
        return this;
    }
}
