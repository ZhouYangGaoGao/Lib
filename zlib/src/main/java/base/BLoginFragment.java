package base;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhy.android.R;

import annotation.InjectPresenter;
import custom.SmartView;
import hawk.Hawk;
import rx.Subscription;
import util.GoTo;
import util.ScreenUtils;

public abstract class BLoginFragment<M> extends BFragment<M, LoginPresenter<M>> implements View.OnClickListener {

    @InjectPresenter
    public LoginPresenter presenter;
    protected SmartView titleView;
    protected SmartView phone;
    protected SmartView captcha;
    protected SmartView password;
    protected SmartView checkPassword;
    protected TextView login;
    protected TextView next;
    protected TextView register;

    private String mode;
    private String toast;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_login;
        mode = getIntent().getStringExtra(BConfig.LOGIN_MODE);
        if (TextUtils.isEmpty(mode)) mode = BConfig.LOGIN_MODE_LOGIN;
    }

    private void resetView() {
        phone.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        captcha.setVisibility(View.GONE);
        checkPassword.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
    }

    private void initCaptcha() {
        titleView.centerTextView.setText("手机验证");
        toast = getString(R.string.str_captcha_success);
        phone.setVisibility(View.VISIBLE);
        captcha.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
    }

    private void initReset() {
        titleView.centerTextView.setText("密码重置");
        toast = getString(R.string.str_reset_success);
        password.setVisibility(View.VISIBLE);
        checkPassword.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
    }

    private void initRegister() {
        titleView.centerTextView.setText("注册");
        toast = getString(R.string.str_register_success);
        phone.setVisibility(View.VISIBLE);
        captcha.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
    }

    private void initLogin() {
        titleView.centerTextView.setText("登录");
        toast = getString(R.string.str_login_success);
        phone.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
    }

    @Override
    public void initView() {
        titleView = (SmartView) findViewById(R.id.titleView);
        phone = (SmartView) findViewById(R.id.phone);
        captcha = (SmartView) findViewById(R.id.captcha);
        password = (SmartView) findViewById(R.id.password);
        checkPassword = (SmartView) findViewById(R.id.checkPassword);
        login = (TextView) findViewById(R.id.login);
        next = (TextView) findViewById(R.id.next);
        register = (TextView) findViewById(R.id.register);
        login.setOnClickListener(this);
        next.setOnClickListener(this);
        register.setOnClickListener(this);
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
        GoTo.start(goTo(data));
        if (isFinish()) getActivity().finish();
    }

    @Override
    public void completed() {
        login.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            if (password.actionErrorCheck()) return;
            login.setEnabled(false);
            presenter.action(login(phone.getText(), password.getText()));
        } else if (view.getId() == R.id.register) {
            if (mode.equals(BConfig.LOGIN_MODE_LOGIN)) {
                resetView();
                initRegister();
                ScreenUtils.showKeyBoard(phone.centerEditText);
                mode = BConfig.LOGIN_MODE_REGISTER;
            } else {
                if (captcha.actionErrorCheck()) return;
                register.setEnabled(false);
                presenter.action(captcha(phone.getText()));
            }

        } else if (view.getId() == R.id.next) {

        }

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

    protected abstract Class goTo(M data);

    protected boolean isFinish() {
        return true;
    }
}
