package custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zhy.android.BuildConfig;
import com.zhy.android.R;

import background.drawable.DrawableCreator;
import base.BConfig;
import base.BHistoryFragment;
import listener.SmartListener;
import bean.Smart;
import rx.Subscription;
import util.MTimer;
import util.RexUtils;

public class SmartView extends LinearLayout {
    public TextView leftTextView, centerTextView, rightTextView;
    public EditText centerEditText;
    public RelativeLayout topContent;
    public View line;
    private float centerVMargin, centerHMargin, centerRMargin, centerLMargin;
    private int checkId, measure, mode = 10, captchaSecond, historyCount;
    private Subscription subscribe;
    private boolean back;

    private static final int MEASURE_MAX = 110;//两边等于大的那边
    private static final int MEASURE_CUSTOM = 111;//使用自定义
    private static final int MEASURE_DIFFERENT = 112;//填充空余

    private static final int MODE_SEARCH = 1;//搜索
    private static final int MODE_TOP = 0;//顶部
    private static final int MODE_LOGIN = 2;//登录
    private static final int MODE_PASSWORD = 3;//password
    private static final int MODE_CAPTCHA = 4;//captcha 验证码
    private static final int MODE_TEST = 5;//test

    public static final int GRAVITY_CENTER = 11;
    public static final int GRAVITY_RIGHT = 12;
    public static final int GRAVITY_LEFT = 10;

    public static final int INPUT_TYPE_NUMBER = 0;
    public static final int INPUT_TYPE_PASSWORD = 1;
    public static final int INPUT_TYPE_NUMBER_PASSWORD = 2;


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
        float iconPadding = t.getFloat(R.styleable.SmartView_icoPadding, 8);
        float bigTextSize = t.getFloat(R.styleable.SmartView_bigTextSize, 16);
        float smallTextSize = t.getFloat(R.styleable.SmartView_smallTextSize, bigTextSize - 5);
        centerVMargin = t.getFloat(R.styleable.SmartView_centerVMargin, 5);
        centerHMargin = t.getFloat(R.styleable.SmartView_centerHMargin, 60);
        centerRMargin = t.getFloat(R.styleable.SmartView_centerRMargin, 0);
        centerLMargin = t.getFloat(R.styleable.SmartView_centerLMargin, 0);
        mode = t.getInt(R.styleable.SmartView_mode, mode);
        measure = t.getInt(R.styleable.SmartView_measure, 0);
        int inputType = t.getInt(R.styleable.SmartView_inputType, -1);
        captchaSecond = t.getInt(R.styleable.SmartView_captchaSecond, 20);
        historyCount = t.getInt(R.styleable.SmartView_historyCount, 10);
        int gravity = t.getInt(R.styleable.SmartView_gravity, mode == 0 ? GRAVITY_CENTER : GRAVITY_LEFT);
        checkId = t.getResourceId(R.styleable.SmartView_checkId, 0);
        int textColor = t.getColor(R.styleable.SmartView_textColor, mode < 2 ?
                getResources().getColor(R.color.clo_big_title) : getResources().getColor(R.color.clo_title));
        int textRColor = textColor;
        String leftText = t.getString(R.styleable.SmartView_leftText);
        String rightText = t.getString(R.styleable.SmartView_rightText);
        String hint = t.getString(R.styleable.SmartView_hint);
        String centerText = t.getString(R.styleable.SmartView_centerText);
        boolean back = t.getBoolean(R.styleable.SmartView_back, mode < 2);
        boolean enable = t.getBoolean(R.styleable.SmartView_enable, true);
        boolean focusable = t.getBoolean(R.styleable.SmartView_focusable, true);
        boolean delete = t.getBoolean(R.styleable.SmartView_delete, true);
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
        tvs = new TextView[]{leftTextView, centerTextView, rightTextView, centerEditText};
        line = view.findViewById(R.id.line);
        LayoutParams params = new LayoutParams(orientation == 1 ? -1 : 0, height > 0 ? dip2px(height)
                : (int) (height == -2 ? getResources().getDimension(R.dimen.dim_top_hight) : -1));
        if (orientation == 0) {
            params.weight = 1;
            setGravity(Gravity.CENTER_VERTICAL);
        } else setGravity(Gravity.CENTER_HORIZONTAL);
        addView(view, params);

        if (getBackground() == null && (mode < 2)) {
            view.setBackgroundColor(getResources().getColor(R.color.clo_top_bg));//top search 模式默认主题颜色背景
        }
        switch (mode) {
            case MODE_TOP://top
                if (measure == 0) measure = MEASURE_MAX;
                centerEditText.setVisibility(GONE);
                centerTextView.setVisibility(VISIBLE);
                centerTextView.setMarquee(1);
                hideLine = true;
                break;
            case MODE_SEARCH://search
                hideLine = true;
                crIcon = getResources().getDrawable(android.R.drawable.ic_menu_search);
                search();
                break;
            case MODE_LOGIN://login
                centerEditText.setVisibility(VISIBLE);
                if (TextUtils.isEmpty(leftText)) leftText = "手机号";
                if (TextUtils.isEmpty(centerText) && BuildConfig.DEBUG && !TextUtils.isEmpty(BConfig.get().getTestPhone()))
                    centerText = BConfig.get().getTestPhone();
                if (leftText.contains("手机")) inputType = 0;
                if (delete) {
                    if (rrIcon == null)
                        rrIcon = getResources().getDrawable(R.drawable.ic_off_dark);
                    rightTextView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            centerEditText.setText("");
                        }
                    });
                }
                break;
            case MODE_PASSWORD://password
                centerEditText.setVisibility(VISIBLE);
                if (TextUtils.isEmpty(leftText)) leftText = "密码";
                if (TextUtils.isEmpty(centerText) && BuildConfig.DEBUG && !TextUtils.isEmpty(BConfig.get().getTestPassword()))
                    centerText = BConfig.get().getTestPassword();
                rrIcon = getResources().getDrawable(R.drawable.ic_eye);
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
            case MODE_CAPTCHA://captcha 验证码
                centerEditText.setVisibility(VISIBLE);
                if (TextUtils.isEmpty(leftText))
                    leftText = getContext().getString(R.string.str_captcha);
                if (TextUtils.isEmpty(rightText))
                    rightText = getContext().getString(R.string.str_get_captcha);
                inputType = 0;
                textRColor = BConfig.get().getColorTheme();
                rightTextView.setPadding(dip2px(8), dip2px(3), dip2px(8), dip2px(3));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) rightTextView.getLayoutParams();
                rlp.height = -2;
                rightTextView.setLayoutParams(rlp);
                rightTextView.setBackground(new DrawableCreator.Builder()
                        .setRipple(true, getResources().getColor(R.color.clo_ripple))
                        .setCornersRadius(dip2px(15))
                        .setSolidColor(0xffeeeeee).build());
                break;
            case MODE_TEST:
                if (BuildConfig.DEBUG && TextUtils.isEmpty(leftText)) leftText = "标题";
                if (BuildConfig.DEBUG && TextUtils.isEmpty(centerText)) centerText = "内容";
                if (BuildConfig.DEBUG && TextUtils.isEmpty(rightText)) rightText = "备注";
                centerTextView.setVisibility(VISIBLE);
                break;
            default:
                centerTextView.setVisibility(VISIBLE);
                measure = MEASURE_DIFFERENT;
        }
        if (hideLine) line.setVisibility(GONE);
        if (back) {
            llIcon = getResources().getDrawable(R.drawable.ic_arrow_back);
            setBack(true);
        }

        initIcon(llIcon, ltIcon, lrIcon, lbIcon, clIcon, ctIcon, crIcon, cbIcon, rlIcon, rtIcon, rrIcon, rbIcon);
        initGravity(gravity);
        initInputType(inputType);
        initDrawablePadding(iconPadding);
        initText(leftText, rightText, centerText, onlyIcon);
        initTextColor(textColor, textRColor);
        centerEditText.setHint(TextUtils.isEmpty(hint) ? "请输入" + (TextUtils.isEmpty(leftText) ? "内容" : leftText) : hint);
        initTextSize(bigTextSize, smallTextSize);
        if (measure == MEASURE_CUSTOM)
            initCenterMargin(dip2px(centerVMargin),
                    width(dip2px(centerLMargin), centerHMargin),
                    width(dip2px(centerRMargin), centerHMargin));
        centerEditText.setEnabled(enable);
        centerEditText.setEnabled(focusable);
    }

    private void initIcon(Drawable llIcon, Drawable ltIcon, Drawable lrIcon, Drawable lbIcon,
                          Drawable clIcon, Drawable ctIcon, Drawable crIcon, Drawable cbIcon,
                          Drawable rlIcon, Drawable rtIcon, Drawable rrIcon, Drawable rbIcon) {

        leftTextView.setCompoundDrawablesWithIntrinsicBounds(llIcon, ltIcon, lrIcon, lbIcon);
        centerTextView.setCompoundDrawablesWithIntrinsicBounds(clIcon, ctIcon, crIcon, cbIcon);
        centerEditText.setCompoundDrawablesWithIntrinsicBounds(clIcon, ctIcon, crIcon, cbIcon);
        rightTextView.setCompoundDrawablesWithIntrinsicBounds(rlIcon, rtIcon, rrIcon, rbIcon);
    }

    private void initText(String leftText, String rightText, String centerText, boolean onlyIcon) {
        if (!onlyIcon)
            leftTextView.setText(leftText);
        rightTextView.setText(rightText);
        centerTextView.setText(centerText);
        centerTextView.setSelected(true);
        centerEditText.setText(centerText);
    }

    public void initTextColor(int textColor, int textRColor) {
        centerTextView.setTextColor(textColor);
        leftTextView.setTextColor(textColor);
        centerEditText.setTextColor(textColor);
        rightTextView.setTextColor(textRColor);
    }

    private void initTextSize(float bigTextSize, float smallTextSize) {
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(bigTextSize));
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(mode == 4 ? smallTextSize : bigTextSize));
        centerEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(bigTextSize));
        centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(bigTextSize));
    }

    private void initDrawablePadding(float iconPadding) {
        leftTextView.setCompoundDrawablePadding(dip2px(iconPadding));
        centerTextView.setCompoundDrawablePadding(dip2px(iconPadding));
        centerEditText.setCompoundDrawablePadding(dip2px(iconPadding));
        rightTextView.setCompoundDrawablePadding(dip2px(iconPadding));
    }

    private void initInputType(int inputType) {
        switch (inputType) {
            case INPUT_TYPE_NUMBER:
                centerEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INPUT_TYPE_PASSWORD:
                centerEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case INPUT_TYPE_NUMBER_PASSWORD:
                centerEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
        }
    }

    private void initGravity(int gravity) {
        switch (gravity) {
            case GRAVITY_CENTER:
                centerEditText.setGravity(Gravity.CENTER);
                centerTextView.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_RIGHT:
                centerEditText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                centerTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            case GRAVITY_LEFT:
                centerEditText.setGravity(Gravity.CENTER_VERTICAL);
                centerTextView.setGravity(Gravity.CENTER_VERTICAL);
                break;
        }
    }

    public SmartView search() {
        centerEditText.setImeActionLabel("搜索", EditorInfo.IME_ACTION_SEARCH);
        centerEditText.setVisibility(VISIBLE);
        centerTextView.setVisibility(GONE);
        centerEditText.setBackground(new DrawableCreator.Builder()
                .setCornersRadius(dip2px(25))
                .setSolidColor(0x66ffffff).build());
        centerEditText.setImeOptions(0x00000003);
        centerEditText.setHintTextColor(BConfig.get().getColorTheme88());
        centerEditText.setGravity(Gravity.CENTER_VERTICAL);
        centerEditText.setLeftRes(android.R.drawable.ic_menu_search);
        return this;
    }

    public void initHistory(BHistoryFragment historyFragment) {
        historyFragment.setSmartView(this, historyCount);
    }

    public void setBack(boolean back) {
        if (this.back = back) {
            leftTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_back, 0, 0, 0);
            leftTextView.setDrawablePadding(0);
            leftTextView.setOnClickListener(view -> ((Activity) getContext()).finish());
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
        if (measure != MEASURE_CUSTOM)
            initCenterMargin(dip2px(centerVMargin),
                    measure == MEASURE_MAX ? Math.max(leftTextView.getMeasuredWidth(),
                            rightTextView.getMeasuredWidth()) : width(leftTextView.getMeasuredWidth(), centerLMargin),
                    measure == MEASURE_MAX ? Math.max(leftTextView.getMeasuredWidth(),
                            rightTextView.getMeasuredWidth()) : width(rightTextView.getMeasuredWidth(), centerRMargin));
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
            Toast.makeText(getContext(), TextUtils.isEmpty(leftTextView.getText()) ? "请输入内容" :
                    (leftTextView.getText().toString().trim() + "不能为空"), Toast.LENGTH_SHORT).show();
        }
        return isEmpty();
    }

    public boolean actionErrorCheck() {
        SmartView captchaCheckView = ((ViewGroup) getParent()).findViewById(checkId);
        if (captchaCheckView != null && (mode == 4 ? captchaCheckView.phoneErrorTest() : captchaCheckView.actionErrorCheck()))
            return true;
        else return emptyTest();
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    private SmartListener listener;

    /**
     * @param listener 返回被点击的SmartView 被点击的TextView坐标 被点击的图标坐标
     * @param indexes  不填时,左中右三个都设置点击监听
     *                 填数字0:左边点击 1:中间点击 2:右边点击
     *                 可选多个
     */
    public void setListener(SmartListener listener, int... indexes) {
        if (Smart.class.isAssignableFrom(listener.getClass())) {
            indexes = ((Smart) listener).indexes;
        }
        this.listener = listener;
        if (indexes == null || indexes.length == 0) {
            for (int i = 0; i < tvs.length; i++) {
                initClick(i);
            }
        } else {
            for (int index : indexes) {
                if (index >= 0 && index <= 3)
                    initClick(index);
            }
        }
    }

    public SmartListener getListener() {
        return listener;
    }

    private void initClick(int index) {
        TextView textView = getTVs()[index];
        textView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onClick(SmartView.this, index, textView.drawableIndex);
                if (back && index == 0 && leftTextView.drawableIndex == 0)
                    ((Activity) getContext()).finish();
                if (index == 2 && mode == 4) initCaptcha();
            }
        });

        if (Smart.class.isAssignableFrom(listener.getClass())) {
            Smart smart = (Smart) listener;
            for (int j = 0; j < smart.res[index].length; j++) {
                if (smart.res[index][j] != 0) {
                    textView.setRes(j, smart.res[index][j]);
                }

                if (smart.drawable[index][j] != null) {
                    textView.setDrawable(j, smart.drawable[index][j]);
                }
            }
            if (!TextUtils.isEmpty(smart.text[index])) {
                textView.setText(smart.text[index]);
            }

            if (smart.padding[index] != 0)
                textView.setDrawablePadding(smart.padding[index]);
            initGravity(smart.gravity);
        }
    }

    private void initCaptcha() {
        SmartView captchaCheckView = ((ViewGroup) getParent()).findViewById(checkId);
        if (captchaCheckView != null && captchaCheckView.phoneErrorTest()) return;
        rightTextView.setClickable(false);
        subscribe = MTimer.interval(1000).subscribe(aLong -> {
            if (((Activity) getContext()).isFinishing() && !subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
                return;
            }
            if (captchaSecond - aLong == 0 && !subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
                rightTextView.setClickable(true);
                rightTextView.setText(R.string.str_get_agin);
                rightTextView.setTextColor(BConfig.get().getColorTheme());
            } else {
                rightTextView.setTextColor(0xffbbbbbb);
                rightTextView.setText("  " + (captchaSecond - aLong) + "s  ");
            }
        });
    }

    private TextView[] tvs;

    public TextView[] getTVs() {
        return tvs;
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
