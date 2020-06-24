package base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import eventbus.hermeseventbus.HermesEventBus;
import listener.OnResultListener;
import slidr.Slidr;
import slidr.model.SlidrConfig;
import util.LogUtils;
import util.StatusBarUtil;

public abstract class BActivity<M, P extends BPresenter> extends AppCompatActivity implements BView<M> {

    public static final int CREATE = 0;
    public static final int RESUME = 1;
    public int preData = 0;//0:在onCreate中加载  1:在onResume中加载
    public P presenter;
    public boolean useEventBus = false;
    public boolean slidAble = false;
    public int contentView = 0;
    public String loadingName = "";
    public String indicatorName = "";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeView();
        try {
            setRequestedOrientation(BConfig.getConfig().getOrientation());
            if (BConfig.getConfig().isFullScreen()) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } catch (Exception e) {
        }
        setContentView(contentView);
        StatusBarUtil.setColor(this, BConfig.getConfig().getColorTheme(), 0);
        ButterKnife.bind(this);
        if (slidAble) {
            Slidr.attach(this, new SlidrConfig.Builder().edge(true).build());
        }
        initView();
        if (presenter != null) presenter.attachView(this);
        if (useEventBus) HermesEventBus.getDefault().register(this);
        afterView();
        if (preData == CREATE) getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preData == RESUME) getData();
    }

    @Override
    public void getData() {

    }

    @Override
    protected void onDestroy() {
        if (useEventBus) HermesEventBus.getDefault().unregister(this);
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        super.onDestroy();
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
    public void log(String... message) {
        LogUtils.e(message.length > 1 ? message[0] : getClass().getSimpleName(), message.length > 1 ? message[1] : message[0]);
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
        log("success");
    }

    @Override
    public void fail(String message) {
        log("fail");
    }

    @Override
    public void completed() {
        log("completed");
    }


    public static void start(Class<?> cls, String... arguments) {
        if (BApp.app().currentActivity() == null) return;
        Intent starter = new Intent(BApp.app().currentActivity(), cls);
        starter.putExtra(BConstant.ARGUMENTS, arguments);
        BApp.app().currentActivity().startActivity(starter);
    }

    public static void startResult(Class<?> cls, int reQuestCode, String... arguments) {
        if (BApp.app().currentActivity() == null) return;
        Intent starter = new Intent(BApp.app().currentActivity(), cls);
        starter.putExtra(BConstant.ARGUMENTS, arguments);
        BApp.app().currentActivity().startActivityForResult(starter, reQuestCode);
    }

    @Override
    public View getView(int layoutId) {
        return View.inflate(this, layoutId, null);
    }
}
