package base;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.android.R;

import custom.SmartView;
import hawk.Hawk;
import listener.SmartListener;
import rx.Subscription;
import util.GoTo;

public abstract class BLoginFragment<M> extends BFragment<M, BPresenter<BView<?>>> implements View.OnClickListener {

    protected SmartView titleView,phone,captcha,password,checkPassword;
    protected LinearLayout editContent,actionLayout;
    protected TextView forget,login,next,register;
    protected String mode,toast;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_login;
        mode = getIntent().getStringExtra(BConfig.LOGIN_MODE);
        if (TextUtils.isEmpty(mode)) mode = BConfig.LOGIN_MODE_LOGIN;
    }

    protected void resetView() {
        forget.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        captcha.setVisibility(View.GONE);
        checkPassword.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
    }

    protected void initCaptcha() {
        resetView();
        mode = BConfig.LOGIN_MODE_CAPTCHA;
        titleView.centerTextView.setText("手机验证");
        toast = getString(R.string.str_captcha_success);
        phone.setVisibility(View.VISIBLE);
        captcha.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
    }

    protected void initReset() {
        resetView();
        mode = BConfig.LOGIN_MODE_RESET;
        titleView.centerTextView.setText("密码重置");
        toast = getString(R.string.str_reset_success);
        password.setCheckId(0);
        password.setVisibility(View.VISIBLE);
        checkPassword.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
    }

    protected void initRegister() {
        resetView();
        mode = BConfig.LOGIN_MODE_REGISTER;
        titleView.centerTextView.setText("注册");
        toast = getString(R.string.str_register_success);
        phone.setVisibility(View.VISIBLE);
        captcha.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
    }

    protected void initLogin() {
        resetView();
        mode = BConfig.LOGIN_MODE_LOGIN;
        titleView.centerTextView.setText("登录");
        titleView.setBack(false);
        toast = getString(R.string.str_login_success);
        phone.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
        register.setAlpha(0.5f);
    }

    @Override
    public void initView() {
        actionLayout = (LinearLayout) findViewById(R.id.actionLayout);
        editContent = (LinearLayout) findViewById(R.id.editContent);
        titleView = (SmartView) findViewById(R.id.titleView);
        phone = (SmartView) findViewById(R.id.phone);
        captcha = (SmartView) findViewById(R.id.captcha);
        password = (SmartView) findViewById(R.id.password);
        checkPassword = (SmartView) findViewById(R.id.checkPassword);
        forget = (TextView) findViewById(R.id.forget);
        login = (TextView) findViewById(R.id.login);
        next = (TextView) findViewById(R.id.next);
        register = (TextView) findViewById(R.id.register);
        forget.setOnClickListener(this);
        login.setOnClickListener(this);
        next.setOnClickListener(this);
        register.setOnClickListener(this);
        captcha.setListener(new SmartListener() {
            @Override
            public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {
                presenter.sub(captcha(phone.getText()));
            }
        }, 2);
        switch (mode) {
            case BConfig.LOGIN_MODE_LOGIN:
                initLogin();
                break;
            case BConfig.LOGIN_MODE_REGISTER:
                initRegister();
                break;
            case BConfig.LOGIN_MODE_RESET:
                initReset();
                break;
            case BConfig.LOGIN_MODE_CAPTCHA:
                initCaptcha();
                break;
        }
    }

    @Override
    public void success(M data) {
        toast(toast);
        Hawk.put(BConfig.LOGIN, data);
        GoTo.start(goTo(data), new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_RESET));
        if (isFinish()) getActivity().finish();
    }

    @Override
    public void completed() {
        login.setEnabled(true);
        next.setEnabled(true);
        register.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.forget) {//忘记按钮点击
            forgetClick();
        }

        if (view.getId() == R.id.login) {//登录按钮点击
            if (loginClick()) return;
        }

        if (view.getId() == R.id.register) {//登录页面 注册按钮点击
            registerClick();
        }

        if (view.getId() == R.id.next) {//重置密码页面 下一步按钮点击
            nextClick();
        }
    }

    protected void nextClick() {
        if (mode.equals(BConfig.LOGIN_MODE_CAPTCHA)) {
            if (captcha.actionErrorCheck()) return;
            if (captcha.getText().equals(captchaStr)) {
                toast("验证成功");
                captchaSuccess();
            } else {
                toast("验证码不正确");
            }
        } else {
            if (checkPassword.actionErrorCheck()) return;
            next.setEnabled(false);
            presenter.sub(reset(password.getText(), checkPassword.getText()));
        }
    }

    protected void captchaSuccess() {
        GoTo.start(getClass(), new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_RESET));
    }

    protected void forgetClick() {
        initCaptcha();
    }

    protected boolean loginClick() {
        if (password.actionErrorCheck()) return true;
        login.setEnabled(false);
        presenter.sub(login(phone.getText(), password.getText()));
        return false;
    }

    protected void registerClick() {
        if (mode.equals(BConfig.LOGIN_MODE_LOGIN)) {
            GoTo.start(this.getClass(), new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_REGISTER));
            return;
        }
        if (mode.equals(BConfig.LOGIN_MODE_REGISTER)) {//注册页面 注册按钮点击
            if (captcha.actionErrorCheck()) return;
            register.setEnabled(false);
            presenter.sub(register(phone.getText(), captcha.getText()));
        }
        return;
    }

    protected Subscription login(String phone, String password) {
        return null;
    }

    protected Subscription register(String phone, String captcha) {
        return null;
    }

    protected Subscription captcha(String phone) {
        return null;
    }

    protected Subscription reset(String password, String checkPassword) {
        return null;
    }

    protected Class<?> goTo(M data) {
        return null;
    }

    protected String captchaStr;

    protected boolean isFinish() {
        return true;
    }
}
