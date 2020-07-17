package mvp.main.view;

import android.widget.ImageView;

import com.zhy.wanandroid.R;

import base.BConfig;
import base.BFragment;
import butterknife.BindView;
import custom.SmartView;
import hawk.Hawk;
import mvp.login.model.LoginModel;
import util.StatusBarUtil;

public class MyFragment extends BFragment {
    @BindView(R.id.mName)
    SmartView mName;
    @BindView(R.id.mPhone)
    SmartView mPhone;
    @BindView(R.id.mBg)
    ImageView mBg;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_my;
    }

    @Override
    public void afterView() {
        LoginModel user = Hawk.get(BConfig.LOGIN);
        mName.centerTextView.setText(user.getNickname());
        mPhone.centerTextView.setText(user.getPublicName());
    }
}
