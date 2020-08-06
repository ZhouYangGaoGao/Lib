package base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.zhy.android.R;

import bean.Info;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import enums.LevelCache;
import enums.LevelDataTime;
import eventbus.hermeseventbus.HermesEventBus;
import rx.Observable;
import util.LogUtils;

public abstract class BFragment<M, P extends BPresenter<BView<?>>> extends Fragment implements BView<M> {
    public P presenter;
    protected LevelDataTime levelDataTime = LevelDataTime.create;
    protected LevelCache levelCache = LevelCache.none;
    protected Info info = new Info();//其他参数
    protected View contentView;
    protected int contentViewId = 0;
    private Unbinder unbinder;
    private BActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info.TAG = getClass().getSimpleName();
        setIndex(FragmentPagerItem.getPosition(getArguments()));
        if (BActivity.class.isAssignableFrom(getActivity().getClass())) {
            activity = (BActivity) getActivity();
            activity.initPresenter(this);
        }
        beforeView();
        if (info.useEventBus) HermesEventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null)
            contentView = inflater.inflate(contentViewId == 0 ? R.layout.layout_list : contentViewId, container, false);
        unbinder = ButterKnife.bind(this, contentView);
        setTitle(getIntent().getStringExtra(BConfig.TITLE));
        if (TextUtils.isEmpty(info.title) && getArguments() != null)
            setTitle(getArguments().getString(BConfig.TITLE,""));
        initView();
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterView();
        if (levelDataTime == LevelDataTime.create) {
            getData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (levelDataTime == LevelDataTime.resume) getData();
    }

    @Override
    public void onDestroyView() {
        if (info.useEventBus) HermesEventBus.getDefault().unregister(this);
        unbinder.unbind();
        super.onDestroyView();
    }

    protected View findViewById(@IdRes int id) {
        return contentView.findViewById(id);
    }

    protected Intent getIntent() {
        return getActivity().getIntent();
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
    public void getData() {//获取数据
        Observable observable = get();
        if (presenter != null && observable != null) {
            presenter.sub(BSub.get(this, observable));
        } else {
            completed();
        }
    }

    protected Observable<?> get() {
        return null;
    }

    @Override
    public void log(String... msg) {
        LogUtils.e(getClass().getSimpleName(), msg.length > 1 ? msg[0] + "  " + msg[1] : msg[0]);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dialog(String... message) {
        if (activity != null) activity.dialog(message);
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
        return activity == null ? null : activity.getView(layoutId);
    }

    public Fragment setType(String type) {
        info.type = type;
        return this;
    }

    public Fragment setIndex(int index) {
        info.index = index;
        return this;
    }

    public Fragment setTitle(String title) {
        info.title = title;
        return this;
    }


}
