package base;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.zhy.android.R;

import java.util.Objects;

import custom.SmartView;
import custom.TextView;
import hawk.Hawk;
import listener.SmartListener;
import rx.Subscription;
import util.GoTo;

public abstract class BLoginFragment<M> extends BFragment<M, BPresenter<BView<?>>>
        implements View.OnClickListener, SmartListener {

    protected SmartView titleView, phone, captcha, password, checkPassword;
    protected LinearLayout topContent,centerContent, bottomContent;
    protected TextView forget, login, next, register;
    protected String mode, toast, captchaStr;

    {
        contentViewId = R.layout.fragment_login;
    }

    @Override
    public void initView() {
        mode = getIntent().getStringExtra(BConfig.LOGIN_MODE);
        if (TextUtils.isEmpty(mode)) mode = BConfig.LOGIN_MODE_LOGIN;
        bottomContent = (LinearLayout) findViewById(R.id.bottomContent);
        centerContent = (LinearLayout) findViewById(R.id.centerContent);
        topContent = (LinearLayout) findViewById(R.id.topContent);
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
        captcha.setListener(this, 2);
        switch (mode) {
            case BConfig.LOGIN_MODE_LOGIN:
                titleView.setBack(false);
                initLogin(true);
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
    public void onClick(SmartView smartView, int textViewIndex, int drawableIndex) {
        if (smartView.getId() == R.id.captcha) {
            presenter.sub(sendCaptcha(phone.getText()));
        }
    }

    protected void resetView() {
        centerContent.setVisibility(View.GONE);
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

    protected void initLogin(boolean needRegister) {
        resetView();
        mode = BConfig.LOGIN_MODE_LOGIN;
        titleView.centerTextView.setText("登录");
        toast = getString(R.string.str_login_success);
        phone.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        if (needRegister){
            register.setVisibility(View.VISIBLE);
            register.setAlpha(0.5f);
        }
    }

    @Override
    public void success(M data) {
        toast(toast);
        Hawk.put(BConfig.LOGIN, data);
        GoTo.start(goTo(data), new Intent().putExtra(BConfig.LOGIN_MODE, BConfig.LOGIN_MODE_RESET));
        if (isFinish()) Objects.requireNonNull(getActivity()).finish();
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
            return;
        }

        if (view.getId() == R.id.login) {//登录按钮点击
            loginClick();
            return;
        }

        if (view.getId() == R.id.register) {//登录页面 注册按钮点击
            registerClick();
            return;
        }

        if (view.getId() == R.id.next) {//重置密码页面 下一步按钮点击
            nextClick();
        }
    }

    protected void nextClick() {
        if (mode.equals(BConfig.LOGIN_MODE_CAPTCHA)) {
            if (captcha.actionErrorCheck()) return;
            checkCaptcha();
        } else {
            if (checkPassword.actionErrorCheck()) return;
            presenter.sub(reset(phone.getText(), password.getText()));
        }
        next.setEnabled(false);
    }

    private void checkCaptcha() {
        Subscription subscription = checkCaptcha(phone.getText(), captcha.getText());
        if (subscription == null)
            if (captcha.getText().equals(captchaStr)) {
                toast("验证成功");
                initReset();
            } else {
                toast("验证码不正确");
            }
        else presenter.sub(subscription);
    }

    protected void forgetClick() {
        initCaptcha();
    }

    protected void loginClick() {
        if (password.actionErrorCheck()) return;
        login.setEnabled(false);
        presenter.sub(login(phone.getText(), password.getText()));
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
    }

    protected Subscription login(String phone, String password) {
        return null;
    }

    protected Subscription register(String phone, String captcha) {
        return null;
    }

    protected Subscription sendCaptcha(String phone) {
        return null;
    }

    protected Subscription checkCaptcha(String phone, String captcha) {
        return null;
    }

    protected Subscription reset(String phone, String password) {
        return null;
    }

    protected Class<?> goTo(M data) {
        return null;
    }

    protected boolean isFinish() {
        return true;
    }
}
