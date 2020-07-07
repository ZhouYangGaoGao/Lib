package com.zhy.app.modules.test;

import android.location.Location;
import android.view.View;

import com.zhy.android.adapter.CommonAdapter;
import com.zhy.app.R;
import com.zhy.app.modules.one.view.SmartViewFragment;

import java.util.Arrays;

import base.BPermissionFragment;
import base.BSmartFragment;
import base.BWebFragment;
import util.GPSUtils;
import util.GoTo;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/10  2:18 PM
 * - generate by MvpAutoCodePlus plugin.
 */

public class TestFragment extends BSmartFragment<Class> {

    @Override
    public void beforeView() {
//        heardView = getView(R.layout.layout_test_heard);
//        footView = getView(R.layout.layout_test_foot);
        itemLayoutId = R.layout.item_text;
        isCard = 15;
        mData.addAll(Arrays.asList(SLikeFragment.class,AVLFragment.class, BWebFragment.class, BannerFragment.class,
                PhotoFragment.class, SmartViewFragment.class));
    }

    @Override
    public void convert(CommonAdapter.ViewHolder h, Class i) {
        h.setText(R.id.title, i.getSimpleName());
    }

    @Override
    protected void onItemClick(CommonAdapter.ViewHolder h, Class i) {
        GoTo.start(i);
    }

    @Override
    public void afterView() {
        mTopView.rightTextView.setText("权限");
        mTopView.rightTextView.setOnClickListener(
                view -> BPermissionFragment.request(()
                        -> mTopView.centerTextView.setText("权限都有了")));

        mTopView.setBack(false);
        mTopView.leftTextView.setText("定位");
        mTopView.leftTextView.setOnClickListener(new View.OnClickListener() {
            GPSUtils gpsUtils;

            @Override
            public void onClick(View view) {
                gpsUtils = GPSUtils.location(new GPSUtils.OnLocationListener() {

                    @Override
                    public void location(Location location) {
                        mTopView.centerTextView.setText(location == null ? "定位失败" :
                                location.getLongitude() + "<-定位->"
                                        + location.getLatitude());
                        if (gpsUtils != null) gpsUtils.removeListener();
                    }
                });
            }
        });
    }
}

