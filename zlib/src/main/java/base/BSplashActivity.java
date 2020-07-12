package base;


import com.zhy.android.R;

import annotation.Presenter;
import custom.TextView;
import hawk.Hawk;
import util.GoTo;
import util.StatusBarUtil;
import util.Timer;

public class BSplashActivity extends BActivity<Object, BPresenter<BView<?>>> {

    @Presenter
    public BPresenter<BView<?>> presenter;
    protected int bgId = R.drawable.ic_welcomm;
    protected Class loginCls = BLoginFragment.class, homeCls = BHomeActivity.class;
    protected TextView tvEmpty;
    protected int delay =17;
    {
        contentViewId = R.layout.layout_empty;
        statusBarColor = 0xffffffff;
    }

    @Override
    public void initView() {
        tvEmpty = findViewById(R.id.tv_empty);
        tvEmpty.setTopRes(bgId);
        tvEmpty.setText("欢迎");
        StatusBarUtil.setTransparentForImageView(this, null);
        Timer.timer(delay).subscribe(aLong -> {
            if (Hawk.get(BConfig.LOGIN) == null)
                GoTo.start(loginCls);
            else
                GoTo.start(homeCls);
            finish();
        });
    }

}
