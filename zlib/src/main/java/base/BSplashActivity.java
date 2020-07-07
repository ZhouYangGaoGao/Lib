package base;


import com.zhy.android.R;

import hawk.Hawk;
import rx.functions.Action1;
import util.GoTo;
import util.StatusBarUtil;
import util.Timer;

public class BSplashActivity extends BActivity {
    protected int bgId = R.mipmap.bga_pp_ic_holder_dark;
    protected Class loginCls = BLoginFragment.class, homeCls = BHomeActivity.class;
    {
        contentViewId = R.layout.frame_layout;
        statusBarColor = 0xffffffff;
    }

    @Override
    public void initView() {
        findViewById(R.id.content).setBackgroundResource(bgId);
        StatusBarUtil.setTransparentForImageView(this,null);
        Timer.timer(17).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if (Hawk.get(BConfig.LOGIN) == null)
                    GoTo.start(loginCls);
                else
                    GoTo.start(homeCls);
                finish();
            }
        });
    }

}
