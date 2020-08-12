package base;

import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import bean.Info;
import custom.StatusView;

public abstract class BListDataFragment<M> extends BFragment<Object, BPresenter<BView<?>>> implements Info.DataListener<List<M>> {
    protected List<M> mData = new ArrayList<>();//主列表数据
    protected StatusView mStatusView;//状态视图

    {
        info.setLevelCache(24 * 60 * 60);
    }

    @Override
    public void onData(List<M> ms) {//数据处理完成 更新页面
        if (info.isRefresh) mData.clear();
        mData.addAll(ms);
        if (mStatusView != null) mStatusView.empty();
        upData();
    }

    protected abstract void upData();

    @Override
    public void success(Object data) {//数据加载完成 进行处理
        if (data == null) return;
        if (BList.class.isAssignableFrom(data.getClass())) {
            onData(((BList<M>) data).getList());
            total(((BList<M>) data).getTotal());
        } else if (List.class.isAssignableFrom(data.getClass())) {
            onData((List<M>) data);
            total(((List<M>) data).size());
        } else fail(new Gson().toJson(data));
    }

    @Override
    public void getData() {
        if (info.needNew(this)) {
            getNew();
        }
    }

    protected void initStatusView() {
        mStatusView = new StatusView(getContext()) {
            @Override
            public void onClick(View v) {
                getNew();
            }
        };
    }

    protected void getNew() {
        if (mStatusView != null) mStatusView.loading();
        super.getData();
    }

    @Override
    public void onStop() {
        info.save(mData);
        super.onStop();
    }

    @Override
    public void fail(String message) {//数据加载失败 吐司提示
        super.fail(message);
        if (mStatusView != null) mStatusView.error(message + "\n点击重试");
        upData();
    }

    public void total(int total) {//列表总数量
    }
}
