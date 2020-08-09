package com.zhy.app.modules.test;

import android.location.Location;
import android.view.View;

import com.zhy.app.R;
import com.zhy.app.modules.flow.CategoryActivity;
import com.zhy.app.modules.one.view.SmartViewFragment;

import java.util.Arrays;

import adapter.ViewHolder;
import anylayer.AnyLayer;
import base.BListFragment;
import base.BWebFragment;
import enums.LevelCache;
import util.GPSUtils;
import util.GoTo;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/10  2:18 PM
 * - generate by MvpAutoCodePlus plugin.
 */

public class TestFragment extends BListFragment<Class> {

    @Override
    public void beforeView() {
        grid.numColumns = 3;
        grid.itemLayoutId = R.layout.item_text;
        info.levelCache = LevelCache.none;
        mData.addAll(Arrays.asList(NewsFragment.class,
                PicSelectViewFragment.class, SLikeFragment.class, AVLFragment.class,
                BWebFragment.class, BannerFragment.class,
                PhotoFragment.class, SmartViewFragment.class, CategoryActivity.class));
    }

    @Override
    public void convert(ViewHolder h, Class i) {
        h.setText(R.id.title, i.getSimpleName());
        h.getTextView(R.id.title).setAutoZoom(true);
        h.getTextView(R.id.title).setSingleLine();
    }

    @Override
    protected void onItemClick(ViewHolder h, Class i) {
        GoTo.start(i);
    }

    @Override
    public void afterView() {
        mSmartView.rightTextView.setText("权限");
        mSmartView.rightTextView.setOnClickListener(
                view ->
                        AnyLayer.dialog(getActivity())
                                .contentView(R.layout.dialog_icon)
                                .backgroundBlurPercent(0.05f)
                                .backgroundColorInt(0x33ffffff)
                                .show()
//                        BPermissionFragment.request(()
//                        -> mSmartView.centerTextView.setText("权限都有了"))
        );

        mSmartView.setBack(false);
        mSmartView.leftTextView.setText("定位");
        mSmartView.leftTextView.setOnClickListener(new View.OnClickListener() {
            GPSUtils gpsUtils;

            @Override
            public void onClick(View view) {
                gpsUtils = GPSUtils.location(new GPSUtils.OnLocationListener() {

                    @Override
                    public void location(Location location) {
                        mSmartView.centerTextView.setText(location == null ? "定位失败" :
                                location.getLongitude() + "<-定位->"
                                        + location.getLatitude());
                        if (gpsUtils != null) gpsUtils.removeListener();
                    }
                });
            }
        });
    }

}

