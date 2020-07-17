package mvp.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.zhy.wanandroid.R;

import background.view.BLImageView;
import base.BApp;
import base.BConfig;
import base.BFragment;
import base.Manager;
import base.Subs;
import butterknife.BindView;
import butterknife.OnClick;
import custom.PicSelectView;
import custom.TextView;
import hawk.Hawk;
import mvp.login.model.LoginModel;
import photopicker.lib.PictureSelector;
import photopicker.lib.config.PictureConfig;
import util.ImageUtils;

public class MyFragment extends BFragment {


    @BindView(R.id.mIcon)
    BLImageView mIcon;
    @BindView(R.id.mPhone)
    TextView mPhone;
    @BindView(R.id.mCollect)
    TextView mCollect;
    @BindView(R.id.mTheme)
    TextView mTheme;
    @BindView(R.id.mUpDate)
    TextView mUpDate;
    @BindView(R.id.mLogout)
    TextView mLogout;

    @Override
    public void beforeView() {
        contentViewId = R.layout.fragment_my;
    }

    @Override
    public void afterView() {
        LoginModel user = Hawk.get(BConfig.LOGIN);
        mPhone.setText(user.getUsername());
    }

    @OnClick({R.id.mRootView, R.id.mIcon, R.id.mCollect, R.id.mTheme, R.id.mUpDate, R.id.mLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mRootView:

                break;
            case R.id.mIcon:
                PictureSelector.create(this)
                        .openGallery(PictureConfig.TYPE_IMAGE)
                        .compress(true)
                        .enableCrop(true)
                        .circleDimmedLayer(true)
                        .isCamera(true)
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.mCollect:
            case R.id.mTheme:
                toast("开发中");
                break;
            case R.id.mUpDate:
                Beta.checkUpgrade();
                break;
            case R.id.mLogout:
                presenter.sub(Subs.get(Manager.getApi().logout()));
                BApp.app().logout();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("" + (data == null));
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    log(PictureSelector.obtainMultipleResult(data).get(0).toString());
                    ImageUtils.loadImage(getContext(), PictureSelector.obtainMultipleResult(data).get(0).getCutPath(), mIcon);
                    break;
            }
        }
    }
}
