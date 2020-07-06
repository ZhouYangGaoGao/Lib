package base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhy.android.R;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
import util.StatusBarUtil;

public abstract class BActivity<M, P extends BPresenter> extends AppCompatActivity implements BView<M> {

    protected P presenter;
    protected int preData = 0;
    protected boolean useEventBus = false;
    protected boolean slidFinish = false;
    protected boolean isFullScreen = BConfig.getConfig().isFullScreen();
    protected int statusBarColor = BConfig.getConfig().getColorTheme();
    protected int contentView = 0;

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
        View view = getView(contentView);
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
    private List<BPresenter> mInjectPresenters;

    @Override
    protected void onDestroy() {
        if (useEventBus) HermesEventBus.getDefault().unregister(this);
        if (mInjectPresenters != null) {
            for (BPresenter presenter : mInjectPresenters) {
                presenter.detach();
            }
            mInjectPresenters.clear();
            mInjectPresenters = null;
        }
        super.onDestroy();
    }

    protected void initPresenter(BView view) {
        // 通过反射获取presenter的真实类型
        Type viewType = view.getClass().getGenericSuperclass();
        if (ParameterizedType.class.isAssignableFrom(viewType.getClass())) {
            ParameterizedType pt = (ParameterizedType) viewType;
            for (int i = 0; i < pt.getActualTypeArguments().length; i++) {
                Class<?> aClass = (Class<?>) pt.getActualTypeArguments()[i];
                if (BPresenter.class.isAssignableFrom(aClass)) {
                    try {
                        if (BActivity.class.isAssignableFrom(view.getClass())) {
                            ((BActivity) view).presenter = (BPresenter) aClass.newInstance();
                            ((BActivity) view).presenter.attach(view);
                            if (mInjectPresenters == null)
                                mInjectPresenters = new ArrayList<>();
                            mInjectPresenters.add(((BActivity) view).presenter
                            );
                        } else {
                            ((BFragment) view).presenter = (BPresenter) aClass.newInstance();
                            ((BFragment) view).presenter.attach(view);
                            if (mInjectPresenters == null)
                                mInjectPresenters = new ArrayList<>();
                            mInjectPresenters.add(((BFragment) view).presenter
                            );
                        }
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        //获得某个类的所有的公共（public）的字段，包括父类中的字段
        Field[] fields = view.getClass().getFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            if (field.getAnnotation(InjectPresenter.class) != null) {
                try {
                    Class<? extends BPresenter> type = (Class<? extends BPresenter>) field.getType();
                    BPresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attach(view);
                    field.setAccessible(true);
                    field.set(view, mInjectPresenter);
                    if (mInjectPresenters == null)
                        mInjectPresenters = new ArrayList<>();
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    log("SubClass must extends Class:BPresenter");
                }
            }
        }
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
