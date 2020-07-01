package custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zhy.android.R;

import java.util.ArrayList;
import java.util.List;

import background.drawable.DrawableCreator;
import base.BConfig;
import hawk.Hawk;
import rx.Subscription;
import rx.functions.Action1;
import util.RexUtils;
import util.Timer;

public class SmartView extends LinearLayout {
    public TextView leftTextView, centerTextView, rightTextView;
    public EditText centerEditText;
    public RelativeLayout topContent;
    public View line;
    private float centerVMargin, centerHMargin, centerRMargin, centerLMargin;
    private int checkId, measure, mode;
    private FragmentWindow historyWindow;

    public SmartView(Context context) {
        this(context, null);
    }

    public SmartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SmartView);
        int orientation = t.getInt(R.styleable.SmartView_android_orientation, 0);
        float height = t.getFloat(R.styleable.SmartView_defHeight, -2);
        float iconPadding = t.getFloat(R.styleable.SmartView_icoPadding, 5);
        float bigTextSize = t.getFloat(R.styleable.SmartView_bigTextSize, 16);
        float smallTextSize = t.getFloat(R.styleable.SmartView_smallTextSize, bigTextSize - 5);
        centerVMargin = t.getFloat(R.styleable.SmartView_centerVMargin, 5);
        centerHMargin = t.getFloat(R.styleable.SmartView_centerHMargin, 60);
        centerRMargin = t.getFloat(R.styleable.SmartView_centerRMargin, 0);
        centerLMargin = t.getFloat(R.styleable.SmartView_centerLMargin, 0);
        mode = t.getInt(R.styleable.SmartView_mode, 10);
        measure = t.getInt(R.styleable.SmartView_measure, 112);
        int inputType = t.getInt(R.styleable.SmartView_inputType, -1);
        int captchaSecond = t.getInt(R.styleable.SmartView_captchaSecond, 20);
        int gravity = t.getInt(R.styleable.SmartView_gravity, mode == 0 ? 11 : -1);
        checkId = t.getResourceId(R.styleable.SmartView_checkId, 0);
        int historyLayout = t.getResourceId(R.styleable.SmartView_historyLayout, R.layout.fragment_pop);
        int textColor = t.getColor(R.styleable.SmartView_textColor, mode < 2 ? 0xffffffff : 0xff000000);
        int textRColor = textColor;
        String leftText = t.getString(R.styleable.SmartView_leftText);
        String rightText = t.getString(R.styleable.SmartView_rightText);
        String hint = t.getString(R.styleable.SmartView_hint);
        String centerText = t.getString(R.styleable.SmartView_centerText);
        boolean back = t.getBoolean(R.styleable.SmartView_back, mode < 2);
        boolean enable = t.getBoolean(R.styleable.SmartView_enable, true);
        boolean focusable = t.getBoolean(R.styleable.SmartView_focusable, true);
        boolean delete = t.getBoolean(R.styleable.SmartView_delete, true);
        boolean historyAble = t.getBoolean(R.styleable.SmartView_history, true);
        boolean onlyIcon = t.getBoolean(R.styleable.SmartView_onlyIcon, false);
        boolean hideLine = t.getBoolean(R.styleable.SmartView_hideLine, false);
        Drawable llIcon = t.getDrawable(R.styleable.SmartView_llIcon);
        Drawable ltIcon = t.getDrawable(R.styleable.SmartView_ltIcon);
        Drawable lrIcon = t.getDrawable(R.styleable.SmartView_lrIcon);
        Drawable lbIcon = t.getDrawable(R.styleable.SmartView_lbIcon);
        Drawable clIcon = t.getDrawable(R.styleable.SmartView_clIcon);
        Drawable ctIcon = t.getDrawable(R.styleable.SmartView_ctIcon);
        Drawable crIcon = t.getDrawable(R.styleable.SmartView_crIcon);
        Drawable cbIcon = t.getDrawable(R.styleable.SmartView_cbIcon);
        Drawable rlIcon = t.getDrawable(R.styleable.SmartView_rlIcon);
        Drawable rtIcon = t.getDrawable(R.styleable.SmartView_rtIcon);
        Drawable rrIcon = t.getDrawable(R.styleable.SmartView_rrIcon);
        Drawable rbIcon = t.getDrawable(R.styleable.SmartView_rbIcon);
        t.recycle();

        RelativeLayout view = (RelativeLayout) View.inflate(context, R.layout.view_smart, null);
        leftTextView = view.findViewById(R.id.leftTextView);
        centerTextView = view.findViewById(R.id.centerTextView);
        centerEditText = view.findViewById(R.id.centerEditText);
        rightTextView = view.findViewById(R.id.rightTextView);
        topContent = view.findViewById(R.id.relativeLayout);
        line = view.findViewById(R.id.line);
        LayoutParams params = new LayoutParams(orientation == 1 ? -1 : 0, height > 0 ? dip2px(height) : (height == -2 ? dip2px(45) : -1));
        if (orientation == 0) {
            params.weight = 1;
            setGravity(Gravity.CENTER_VERTICAL);
        } else setGravity(Gravity.CENTER_HORIZONTAL);
        addView(view, params);

        if (getBackground() == null && (mode < 2)) {
            view.setBackgroundColor(BConfig.getConfig().getColorTheme());//top search 模式默认主题颜色背景
        }
        if (hideLine) line.setVisibility(GONE);
        switch (mode) {
            case 10://common
            case 0://top
                centerEditText.setVisibility(GONE);
                break;
            case 1://search
                initHistory(historyAble, historyLayout);
                centerTextView.setVisibility(GONE);
                centerEditText.setBackground(new DrawableCreator.Builder()
                        .setCornersRadius(dip2px(25))
                        .setSolidColor(0xffffffff).build());
                crIcon = getResources().getDrawable(android.R.drawable.ic_menu_search);
                centerEditText.setImeOptions(0x00000003);

                break;
            case 2://login
                if (TextUtils.isEmpty(leftText)) leftText = "手机号";
                if (delete) {
                    if (rlIcon == null)
                        rlIcon = getResources().getDrawable(R.drawable.ic_off_dark);
                    rightTextView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            centerEditText.setText("");
                        }
                    });
                }
                break;
            case 3://password
                if (TextUtils.isEmpty(leftText)) leftText = "密码";
                rlIcon = getResources().getDrawable(R.drawable.ic_eye);
                inputType = 1;
                rightTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (rightTextView.isSelected()) {
                            rightTextView.setSelected(false);
                            centerEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else {
                            rightTextView.setSelected(true);
                            centerEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        if (!TextUtils.isEmpty(centerEditText.getText()))
                            centerEditText.setSelection(centerEditText.getText().length());
                    }
                });
                break;
            case 4://captcha 验证码
                if (TextUtils.isEmpty(leftText)) leftText = "验证码";
                if (TextUtils.isEmpty(rightText)) rightText = "获取验证码";
                inputType = 0;
                textRColor = BConfig.getConfig().getColorTheme();
                rightTextView.setPadding(dip2px(8), dip2px(3), dip2px(8), dip2px(3));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) rightTextView.getLayoutParams();
                rlp.height = -2;
                rightTextView.setLayoutParams(rlp);
                rightTextView.setBackground(new DrawableCreator.Builder()
                        .setRipple(true,0x88888888)
                        .setCornersRadius(dip2px(15))
                        .setSolidColor(0xffeeeeee).build());

                rightTextView.setOnClickListener(new OnClickListener() {
                    Subscription subscribe;

                    @Override
                    public void onClick(View view) {
                        SmartView captchaCheckView = ((ViewGroup) getParent()).findViewById(checkId);
                        if (captchaCheckView != null && captchaCheckView.phoneErrorTest()) return;

                        rightTextView.setClickable(false);
                        subscribe = Timer.interval(1000).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                if (((Activity) context).isFinishing() && !subscribe.isUnsubscribed()) {
                                    subscribe.unsubscribe();
                                    return;
                                }
                                if (captchaSecond - aLong == 0 && !subscribe.isUnsubscribed()) {
                                    subscribe.unsubscribe();
                                    rightTextView.setClickable(true);
                                    rightTextView.setText("重新获取");
                                    rightTextView.setTextColor(BConfig.getConfig().getColorTheme());
                                } else {
                                    rightTextView.setTextColor(0xffbbbbbb);
                                    rightTextView.setText((captchaSecond - aLong) + "秒后重新获取");
                                }
                            }
                        });
                    }
                });
                break;
            case 5:
                if (TextUtils.isEmpty(leftText)) leftText = "标题";
                if (TextUtils.isEmpty(centerText)) centerText = "内容";
                if (TextUtils.isEmpty(rightText)) rightText = "备注";
                centerEditText.setVisibility(GONE);
                break;
        }

        if (back) {
            llIcon = getResources().getDrawable(R.drawable.ic_arrow_back);
            leftTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) getContext()).finish();
                }
            });
        }
        leftTextView.setCompoundDrawablesWithIntrinsicBounds(llIcon, ltIcon, lrIcon, lbIcon);
        centerTextView.setCompoundDrawablesWithIntrinsicBounds(clIcon, ctIcon, crIcon, cbIcon);
        centerEditText.setCompoundDrawablesWithIntrinsicBounds(clIcon, ctIcon, crIcon, cbIcon);
        rightTextView.setCompoundDrawablesWithIntrinsicBounds(rlIcon, rtIcon, rrIcon, rbIcon);


        switch (gravity) {
            case 11:
                centerEditText.setGravity(Gravity.CENTER);
                rightTextView.setGravity(Gravity.CENTER);
                break;
            case 12:
                centerEditText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                rightTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            default:
                centerEditText.setGravity(Gravity.CENTER_VERTICAL);
                rightTextView.setGravity(Gravity.CENTER_VERTICAL);
        }

        switch (inputType) {
            case 0:
                centerEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 1:
                centerEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 2:
                centerEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
        }
        leftTextView.setCompoundDrawablePadding(dip2px(iconPadding));
        centerTextView.setCompoundDrawablePadding(dip2px(iconPadding));
        centerEditText.setCompoundDrawablePadding(dip2px(iconPadding));
        rightTextView.setCompoundDrawablePadding(dip2px(iconPadding));
        if (!onlyIcon)
            leftTextView.setText(leftText);
        rightTextView.setText(rightText);
        centerTextView.setText(centerText);
        centerTextView.setSelected(true);
        centerEditText.setText(centerText);
        centerTextView.setTextColor(textColor);
        leftTextView.setTextColor(textColor);
        rightTextView.setTextColor(textRColor);
        centerEditText.setHint(TextUtils.isEmpty(hint) ? "请输入" + (TextUtils.isEmpty(leftText) ? "内容" : leftText) : hint);
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(bigTextSize));
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(mode == 4 ? smallTextSize : bigTextSize));
        centerEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(bigTextSize));
        centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(bigTextSize));
        if (measure == 111)
            initCenterMargin(dip2px(centerVMargin),
                    width(dip2px(centerLMargin), centerHMargin),
                    width(dip2px(centerRMargin), centerHMargin));
        centerEditText.setEnabled(enable);
        centerEditText.setEnabled(focusable);

    }

    private void initHistory(boolean historyAble, int historyLayoutId) {
        if (historyAble) {
            centerEditText.addTextChangedListener(new TextWatcher() {
                HistoryFragment historyFragment;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!isEmpty() || history == null) return;
                    if (historyWindow == null) {
                        historyWindow = new FragmentWindow(getContext());
                        historyFragment = (HistoryFragment) historyWindow.setContentView(historyLayoutId);
                        if (historyFragment == null) return;
                        historyFragment.setEditText(centerEditText);
                    }
                    if (historyFragment != null) {
                        historyFragment.onDatas(history);
                        historyWindow.showAsDropDown(line);
                    }
                }
            });
            centerEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
                        String text = getText();
                        if (history == null) {
                            history = new ArrayList<>();
                        }
                        if (history.contains(text)) {
                            history.remove(text);
                        }
                        if (!TextUtils.isEmpty(text)) {
                            history.add(0, text);
                            if (history.size() > 10) history.remove(9);
                        }
                    }
                    return false;
                }
            });
        }
    }

    public void setBack(boolean back) {
        if (back) {
            leftTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_back, 0, 0, 0);
            leftTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) getContext()).finish();
                }
            });
        } else {
            leftTextView.setOnClickListener(null);
            leftTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    private int width(float newWight, float customWidth) {
        return customWidth == 0 ? (int) (newWight == 0 ? dip2px(centerHMargin) : newWight) : dip2px(customWidth);
    }

    private void initCenterMargin(int centerVMargin, int centerLMargin, int centerRMargin) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) centerEditText.getLayoutParams();
        if (lp == null) lp = new RelativeLayout.LayoutParams(-1, -1);
        lp.setMargins(centerLMargin, centerVMargin, centerRMargin, centerVMargin);
        centerEditText.setLayoutParams(lp);
        centerTextView.setLayoutParams(lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (measure != 111)
            initCenterMargin(dip2px(centerVMargin),
                    measure == 110 ? Math.max(leftTextView.getMeasuredWidth(), rightTextView.getMeasuredWidth()) : width(leftTextView.getMeasuredWidth(), centerLMargin),
                    measure == 110 ? Math.max(leftTextView.getMeasuredWidth(), rightTextView.getMeasuredWidth()) : width(rightTextView.getMeasuredWidth(), centerRMargin));
    }

    public String getText() {
        return centerEditText.getText().toString().trim();
    }

    public boolean phoneErrorTest() {
        if (emptyTest()) return true;
        if (!RexUtils.checkPhone(getText())) {
            Toast.makeText(getContext(), "手机号码不正确", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getText());
    }

    public boolean emptyTest() {
        if (isEmpty()) {
            Toast.makeText(getContext(), TextUtils.isEmpty(leftTextView.getText()) ? "请输入内容" : (leftTextView.getText().toString().trim() + "不能为空"), Toast.LENGTH_SHORT).show();
        }
        return isEmpty();
    }

    private List<String> history;

    @Override
    protected void onDetachedFromWindow() {
        if (historyWindow != null && historyWindow.isShowing()) {
            historyWindow.dismiss();
            historyWindow = null;
        }
        if (history != null && history.size() > 0) Hawk.put("SmartView_" + getId(), history);
        super.onDetachedFromWindow();
    }

    public boolean actionErrorCheck() {
        SmartView captchaCheckView = ((ViewGroup) getParent()).findViewById(checkId);
        if (captchaCheckView != null && (mode == 4 ? captchaCheckView.phoneErrorTest() : captchaCheckView.actionErrorCheck()))
            return true;
        else return emptyTest();
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
