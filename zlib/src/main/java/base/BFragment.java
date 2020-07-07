package base;

import android.content.Intent;
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

import butterknife.ButterKnife;
import butterknife.Unbinder;
import eventbus.hermeseventbus.HermesEventBus;
import util.LogUtils;

public abstract class BFragment<M, P extends BPresenter> extends Fragment implements BView<M> {
    protected P presenter;
    protected int preData = 0;
    protected View contentView;
    protected int contentViewId = 0;
    protected boolean useEventBus = false;
    private Unbinder unbinder;
    private BActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeView();
        if (BActivity.class.isAssignableFrom(getActivity().getClass())) {
            activity = (BActivity) getActivity();
            activity.initPresenter(this);
        }
        if (useEventBus) HermesEventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null)
            contentView = inflater.inflate(contentViewId == 0 ? R.layout.layout_list : contentViewId, null);
        unbinder = ButterKnife.bind(this, contentView);
        initView();
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterView();
        if (preData == BConfig.GET_DATA_CREATE) getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (preData == BConfig.GET_DATA_RESUME) getData();
    }

    @Override
    public void onDestroyView() {
        if (useEventBus) HermesEventBus.getDefault().unregister(this);
        unbinder.unbind();
        super.onDestroyView();
    }

    protected View findViewById(int id) {
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
    public void getData() {
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
}