package mvp.main.view;

import com.zhy.wanandroid.R;

import base.BApp;
import base.BConfig;
import base.BFragment;
import butterknife.BindView;
import custom.SmartView;
import hawk.Hawk;
import mvp.login.model.LoginModel;

public class MyFragment extends BFragment {
    @BindView(R.id.mName)
    SmartView mName;
    @BindView(R.id.mPhone)
    SmartView mPhone;
    @BindView(R.id.mEmail)
    SmartView mEmail;
    @BindView(R.id.mTopView)
    SmartView mTopView;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_my;
    }

    @Override
    public void afterView() {
        LoginModel user = Hawk.get(BConfig.LOGIN);
        mName.centerTextView.setText(user.getNickname());
        mPhone.centerTextView.setText(user.getPublicName());
        mEmail.centerTextView.setText(user.getEmail());
        mTopView.rightTextView.setOnClickListener(view -> BApp.app().logout());
    }
}
