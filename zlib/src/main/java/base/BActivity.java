package base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import annotation.InjectPresenter;
import background.BackgroundLibrary;
import butterknife.ButterKnife;
import eventbus.hermeseventbus.HermesEventBus;
import listener.OnResultListener;
import slidr.Slidr;
import slidr.model.SlidrConfig;
import util.LogUtils;
import util.Reflector;
import util.StatusBarUtil;

public abstract class BActivity<M, P extends BPresenter> extends AppCompatActivity implements BView<M> {

    protected P presenter;
    protected int preData = 0;
    protected boolean useEventBus = false;
    protected boolean slidFinish = false;
    protected boolean isFullScreen = BConfig.getConfig().isFullScreen();
    protected int statusBarColor = BConfig.getConfig().getColorTheme();
    protected int contentViewId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeView();
        fullScreen();
        initPresenter(this);
        BackgroundLibrary.inject(this);
        contentView();
        StatusBarUtil.setColor(this, statusBarColor, 0);
        ButterKnife.bind(this);
        if (slidFinish) Slidr.attach(this, new SlidrConfig.Builder().edge(true).build());
        initView();
        if (useEventBus) HermesEventBus.getDefault().register(this);
        afterView();
        if (preData == BConfig.GET_DATA_CREATE) getData();
    }

    private void contentView() {
        View view = getView(contentViewId);
        if (view.getBackground() == null)
            view.setBackgroundColor(0xffffffff);
        setContentView(view);
    }

    private void fullScreen() {
        try {
            if (isFullScreen) {
                requestWindowFeature(1);
                getWindow().setFlags(1024, 1024);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 保存使用注解的 Presenter 和默认的 presenter，用于解绑
     */
    private List<BPresenter<BView<?>>> mInjectPresenters = new ArrayList<>();

    @Override
    protected void onDestroy() {
        if (useEventBus) HermesEventBus.getDefault().unregister(this);
        if (mInjectPresenters != null) {
            for (BPresenter<BView<?>> presenter : mInjectPresenters) {
                if (presenter != null)
                    presenter.detach();
            }
            mInjectPresenters.clear();
            mInjectPresenters = null;
        }
        super.onDestroy();
    }

    /**
     * 获取fragment或activity中泛型或注解的presenter
     * @param view
     */
    protected void initPresenter(BView<?> view) {
        List<BPresenter<BView<?>>> injects = Reflector.get(view, InjectPresenter.class);
        if (injects != null && injects.size() > 0)
            for (BPresenter<BView<?>> inject : injects)
                if (inject != null) addPresenter(view, inject);

        BPresenter<BView<?>> bPresenter = Reflector.get(view, 1);
        if (bPresenter == null) return;
        addPresenter(view, bPresenter);
        if (BActivity.class.isAssignableFrom(view.getClass()))
            presenter = (P) bPresenter;
        else
            ((BFragment) view).presenter = bPresenter;
    }

    private void addPresenter(BView<?> view, BPresenter<BView<?>> presenter) {
        if (presenter == null) return;
        presenter.attach(view);
        if (mInjectPresenters == null) mInjectPresenters = new ArrayList<>();
        mInjectPresenters.add(presenter);
    }

    private OnResultListener resultListener;

    public void addResultListener(OnResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultListener != null) {
            resultListener.onResult(requestCode, resultCode, data);
        }
        resultListener = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preData == BConfig.GET_DATA_RESUME) getData();
    }

    @Override
    public void beforeView() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void afterView() {
    }

    @Override
    public void getData() {
    }


    @Override
    public void log(String... msg) {
        LogUtils.e(getClass().getSimpleName(), msg.length > 1 ? msg[0] + "  " + msg[1] : msg[0]);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dialog(String... message) {

    }


    @Override
    public void success(M data) {
    }

    @Override
    public void fail(String message) {
    }

    @Override
    public void completed() {
    }

    @Override
    public View getView(int layoutId) {
        return View.inflate(this, layoutId, null);
    }
}
