package base;


import java.util.ArrayList;
import java.util.List;

import annotation.InjectPresenter;
import rx.Observable;
import util.Suber;

/**
 * @author ZhouYang
 * @describe
 * @date 2019-07-19  10:42
 * - generate by MvpAutoCodePlus plugin.
 */
public class SmartPresenter<M> extends BPresenter<ISmartContract.View> implements ISmartContract.Presenter {
    public SmartPresenter() {
    }

    @Override
    public void getDatas() {
        final Observable suber = mView.getPageList();

        if (suber != null)
            subscribe(new Suber<BList<M>>(mView, suber) {
                @Override
                public void onSuccess(BList<M> mListModel) {
                    if (mListModel.getList() != null) {
                        mView.onDatas(mListModel.getList());
                        mView.total(mListModel.getTotal());
                    } else {
                        mView.onDatas(new ArrayList());
                        mView.total(0);
                    }
                }
            });
        else {
            final Observable suberList = mView.getList();
            if (suberList != null)
                subscribe(new Suber<List<M>>(mView, suberList) {
                    @Override
                    public void onSuccess(List<M> mList) {
                        if (mList != null && mList.size() > 0) {
                            mView.total(mList.size());
                            mView.onDatas(mList);
                        } else {
                            mView.total(0);
                            mView.onDatas(new ArrayList());
                        }
                    }
                });
        }
    }
}

