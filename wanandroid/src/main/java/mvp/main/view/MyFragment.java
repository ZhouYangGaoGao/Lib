package mvp.main.view;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.tencent.bugly.beta.Beta;
import com.zhy.wanandroid.R;

import base.BActivity;
import base.BApp;
import base.BConfig;
import base.BFragment;
import base.BSub;
import base.Manager;
import butterknife.BindView;
import butterknife.OnClick;
import custom.ImageCard;
import custom.TextView;
import hawk.Hawk;
import mvp.home.view.CollectFragment;
import mvp.login.model.LoginModel;
import util.FastBlur;
import util.GoTo;
import util.MIntent;

public class MyFragment extends BFragment {


    @BindView(R.id.mIcon)
    ImageCard mIcon;
    @BindView(R.id.mBg)
    ImageView mBg;
    @BindView(R.id.mName)
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
        mTheme.setText(BConfig.get().isNoColor() ? "色彩" : "黑白");
        mBg.setImageBitmap(FastBlur.blurBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_code)));
    }

    @OnClick({R.id.mRootView, R.id.mIcon, R.id.mCollect, R.id.mTheme, R.id.mUpDate, R.id.mLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mRootView:
                break;
            case R.id.mIcon:
//                PictureSelector.create(this)
//                        .openGallery(PictureConfig.TYPE_IMAGE)
//                        .isCamera(true)
//                        .selectionMode(PictureConfig.SINGLE)
//                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.mCollect:
                GoTo.start(CollectFragment.class, new MIntent(BConfig.TITLE, "我的收藏"));
                break;
            case R.id.mTheme:
                BConfig.get().setNoColor(!BConfig.get().isNoColor());
                ((BActivity) getActivity()).noColor();
                mTheme.setText(BConfig.get().isNoColor() ? "色彩" : "黑白");
                break;
            case R.id.mUpDate:
                Beta.checkUpgrade();
                break;
            case R.id.mLogout:
                presenter.sub(BSub.get(Manager.getApi().logout()));
                BApp.app().logout();
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST)
//            mIcon.loadImage(PictureSelector.obtainMultipleResult(data).get(0).getPath());
//    }
}
