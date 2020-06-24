package base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.zhy.android.R;

import java.util.ArrayList;
import java.util.List;

import annotation.InjectPresenter;
import custom.AutoScrollViewPager;
import rx.Observable;
import util.ImageUtils;
import util.ScreenUtils;

public abstract class PagerFragment<M> extends BFragment<M, SmartPresenter<M>> implements
        ISmartContract.View<M>, AutoScrollViewPager.OnPageClickListener {
    @InjectPresenter
    public SmartPresenter presenter;
    protected AutoScrollViewPager mViewPager;
    private View mRootView;
    protected SmartTabLayout indicator;
    protected PagerAdapter adapter;
    protected List<M> mData = new ArrayList<>();
    protected boolean useIndicate = true, isAutoScroll = true;
    protected int itemLayoutId = R.layout.item_avl;

    {
        contentViewId = R.layout.fragment_pager;
    }

    @Override
    public void onDatas(List<M> datas) {
        mData.clear();
        mData.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPageClick(AutoScrollViewPager pager, int position) {

    }

    @Override
    public Observable<BResponse<List<M>>> getList() {
        return null;
    }

    @Override
    public Observable<BResponse<BList<M>>> getPageList() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        mRootView = findViewById(R.id.mRootView);
        mViewPager = (AutoScrollViewPager) findViewById(R.id.mViewPager);
        indicator = (SmartTabLayout) findViewById(R.id.indicator);
        mViewPager.setScrollFactor(10);
        mViewPager.setOnPageClickListener(this);
        upDate();
    }

    private void initAdapter() {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(getContext(), itemLayoutId, null);
                convert(new Viewholder(view, position), mData.get(position));
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        indicator.setViewPager(mViewPager);
        if (isAutoScroll) mViewPager.startAutoScroll();
        if (useIndicate && mData.size() > 1) {
            indicator.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.GONE);
        }
    }

    public void upDate() {
        initAdapter();
        if (mData.size() > 0) {
            mRootView.setVisibility(View.VISIBLE);
        } else mRootView.setVisibility(View.GONE);
    }

    public abstract void convert(Viewholder h, M data);

    @Override
    public void getData() {
        presenter.getDatas();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAutoScroll) mViewPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isAutoScroll) mViewPager.stopAutoScroll();
    }

    public class Viewholder {
        private View itemView;
        private int position;

        public Viewholder(View itemView, int position) {
            this.itemView = itemView;
            this.position = position;
        }

        public PagerFragment.Viewholder setImage(int viewId, Object url) {
            ImageUtils.loadImage(getContext(), url, itemView.findViewById(viewId));
            return this;
        }

        public PagerFragment.Viewholder setClick(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
            return this;
        }

        public PagerFragment.Viewholder setText(int viewId, String text) {
            TextView view = itemView.findViewById(viewId);
            view.setText(text);
            return this;
        }

        public <T extends View> T getView(int viewId) {
            return (T) itemView.findViewById(viewId);
        }

        public int getPosition() {
            return position;
        }
    }

    @Override
    public void total(int total) {

    }
}
