package base;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import util.Subs;

/**
 * @author ZhouYang
 * @describe
 * @date 2019-07-19  10:42
 * - generate by MvpAutoCodePlus plugin.
 */
public class SmartPresenter<M> extends BPresenter<ISmartContract.View> implements ISmartContract.Presenter {

    @Override
    public boolean getDatas() {
        Observable subs = mView.getPageList();
        if (subs != null) {
            sub(new Subs<BList<M>>(mView, subs) {
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
            return true;
        } else {
            subs = mView.getList();
            if (subs != null) {
                sub(new Subs<List<M>>(mView, subs) {
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
                return true;
            }
        }
        return false;
    }
}

