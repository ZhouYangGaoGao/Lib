package base;

import java.util.List;

import rx.Observable;

/**
 * @author ZhouYang
 * @describe
 * @date 2019-07-19  10:42
 * - generate by MvpAutoCodePlus plugin.
 */

public interface ISmartContract {
    interface View<M> extends BView<M>{
        void total(int total);
        void onDatas(List<M> datas);
        Observable<BResponse<BList<M>>> getPageList();
        Observable<BResponse<List<M>>> getList();
    }

    interface Presenter extends IPresenter {
        void getDatas();
    }
}
