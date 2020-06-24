package com.zhy.app.modules.test;

import android.location.Location;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.android.adapter.CommonAdapter;
import com.zhy.app.R;
import com.zhy.app.base.Manager;

import java.util.Collections;

import base.BList;
import base.BResponse;
import base.PagerFragment;
import base.PermissionFragment;
import base.SmartFragment;
import rx.Observable;
import util.GPSUtils;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/10  2:18 PM
 * - generate by MvpAutoCodePlus plugin.
 */

public class TestFragment extends SmartFragment<String> {

    @Override
    public void beforeView() {
        preData = NEVER;
        heardView = getView(R.layout.layout_test_heard);
        itemLayoutId = R.layout.item_avl;
        Collections.addAll(mData, getResources().getStringArray(R.array.load_name));
    }

    @Override
    public void convert(CommonAdapter.ViewHolder h, String i) {
        AVLoadingIndicatorView avl = h.getView(R.id.avl);
        avl.setIndicator(i);
    }

    @Override
    public void afterView() {
        mTopView.rightTextView.setText("权限");
        mTopView.rightTextView.setOnClickListener(
                view -> PermissionFragment.request(()
                        -> mTopView.centerTextView.setText("权限都有了")));

        mTopView.setBack(false);
        mTopView.leftTextView.setText("定位");
        mTopView.leftTextView.setOnClickListener(new View.OnClickListener() {
            GPSUtils gpsUtils;

            @Override
            public void onClick(View view) {
                if (gpsUtils != null) gpsUtils.removeListener();
                gpsUtils = GPSUtils.updates(new GPSUtils.OnLocationListener() {
                    int count = 1;

                    @Override
                    public void location(Location location) {
                        mTopView.centerTextView.setText(location == null ? "定位失败" :
                                location.getLongitude() + "<-定" + (count++) + "位->"
                                        + location.getLatitude());
                    }
                });
            }
        });
    }

//    @Override
//    public Observable<BResponse<BList<TestModel>>> getPageList() {
//        return Manager.getManager().getNewsList();
//    }
}

