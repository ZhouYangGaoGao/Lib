package base;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhy.android.R;

import java.util.ArrayList;
import java.util.List;

import annotation.Presenter;
import background.BackgroundLibrary;
import butterknife.ButterKnife;
import eventbus.hermeseventbus.HermesEventBus;
import listener.OnResultListener;
import slidr.Slidr;
import slidr.model.SlidrConfig;
import util.LogUtils;
import util.Reflector;
import util.StatusBarUtil;

public class BActivity<M, P extends BPresenter<BView<?>>> extends AppCompatActivity implements BView<M> {

    public P presenter;
    protected int preData = 0;
    protected boolean useEventBus = false;
    protected boolean slidFinish = false;
    protected boolean isFullScreen;
    protected int statusBarColor;
    protected int contentViewId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        statusBarColor = getResources().getColor(R.color.clo_status_bar);
        isFullScreen = BConfig.get().isFullScreen();
        super.onCreate(savedInstanceState);
        beforeView();
        if (useEventBus) HermesEventBus.getDefault().register(this);
        fullScreen();
        initPresenter(this);
        BackgroundLibrary.inject(this);
        contentView();
        StatusBarUtil.setColor(this, statusBarColor, 0);
        ButterKnife.bind(this);
        if (slidFinish) Slidr.attach(this, new SlidrConfig.Builder().edge(true).build());
        initView();
        noColor();
    }

    public void noColor() {
        Paint paint = getPaint();
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, BConfig.get().isNoColor() ? paint : null);
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        return paint;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
     *
     * @param bView
     */
    protected void initPresenter(Object bView) {
        List<BPresenter<BView<?>>> presenters = Reflector.get(bView, Presenter.class);
        if (presenters != null && presenters.size() > 0)
            for (BPresenter<BView<?>> inject : presenters)
                if (inject != null) addPresenter((BView<?>) bView, inject);
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
