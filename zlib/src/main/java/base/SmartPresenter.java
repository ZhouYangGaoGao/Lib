package base;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author ZhouYang
 * @describe
 * @date 2019-07-19  10:42
 * - generate by MvpAutoCodePlus plugin.
 */
public class SmartPresenter<M> extends BPresenter<ISmartContract.View<?>> implements ISmartContract.Presenter {

    @Override
    public boolean getDatas() {
//        final Observable suber = mView.getPageList();
//
//        if (suber != null) {
//
//            sub(BSub.get(mView,mView.getPageList()));
//
//            sub(new BSub<BBean<BList<M>>,BList<M>>(mView, suber) {
//                @Override
//                public void onSuccess(BList<M> mListModel) {
//                    if (mListModel.getList() != null) {
//                        mView.onPagerDatas(mListModel);
//                        mView.total(mListModel.getTotal());
//                    } else {
//                        mView.onDatas(new ArrayList());
//                        mView.total(0);
//                    }
//                }
//            });
//            return true;
//        } else {
//            final Observable suberList = mView.getList();
//            if (suberList != null) {
//                sub(new BSub(mView, suberList) {
//                    @Override
//                    public void onSuccess(List<M> mList) {
//                        if (mList != null && mList.size() > 0) {
//                            mView.total(mList.size());
//                            mView.onDatas(mList);
//                        } else {
//                            mView.total(0);
//                            mView.onDatas(new ArrayList());
//                        }
//                    }
//                });
//                return true;
//            }
//        }

        return false;
    }
}

