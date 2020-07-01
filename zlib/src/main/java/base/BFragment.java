package base;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.zhy.android.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import annotation.InjectPresenter;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eventbus.hermeseventbus.HermesEventBus;
import util.LogUtils;

public abstract class BFragment<M, P extends BPresenter> extends Fragment implements BView<M> {
    public P presenter;
    protected static final int NEVER = -1;
    protected static final int CREATE = 0;
    protected static final int RESUME = 1;
    protected int preData = 0;//0:在onCreate中加载  1:在onResume中加载
    private View rootView;
    protected boolean useEventBus = false;
    protected int contentViewId = R.layout.layout_list;
    protected String loadingName = "努力加载中";
    protected String indicatorName;
    private Unbinder unbinder;
    /**
     * 保存使用注解的 Presenter ，用于解绑
     */
    private List<BPresenter> mInjectPresenters;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeView();
        mInjectPresenters = new ArrayList<>();

        try {
            // 通过反射获取presenter的真实类型
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            for (int i = 0; i < pt.getActualTypeArguments().length; i++) {
                Class<?> aClass = (Class<?>) pt.getActualTypeArguments()[i];
                if (BPresenter.class.isAssignableFrom(aClass)) {
                    presenter = (P) aClass.newInstance();
                    presenter.attachView(this);
                    mInjectPresenters.add(presenter);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获得某个类的所有的公共（public）的字段，包括父类中的字段
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            if (field.getAnnotation(InjectPresenter.class) != null) {
                try {
                    Class<? extends BPresenter> type = (Class<? extends BPresenter>) field.getType();
                    BPresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attachView(this);
                    field.setAccessible(true);
                    field.set(this, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    log("SubClass must extends Class:BPresenter");
                }
            }
        }

        if (useEventBus) HermesEventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(contentViewId, null);
        unbinder = ButterKnife.bind(this, rootView);
        indicatorName = getResources().getStringArray(R.array.load_name)[3];
        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterView();
        if (preData == CREATE) getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (preData == RESUME) getData();
    }

    @Override
    public void onDestroyView() {
        if (useEventBus) HermesEventBus.getDefault().unregister(this);
        for (BPresenter presenter : mInjectPresenters) {
            presenter.detachView();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;

        unbinder.unbind();
        super.onDestroyView();
    }

    public View findViewById(int id) {
        return rootView.findViewById(id);
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
    public void log(String... message) {
        LogUtils.e(message.length > 1 ? message[0] : getClass().getSimpleName(), message.length > 1 ? message[1] : message[0]);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
        return View.inflate(getContext(), layoutId, null);
    }
}
