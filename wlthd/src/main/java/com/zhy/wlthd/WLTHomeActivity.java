package com.zhy.wlthd;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.orient.me.widget.rv.adapter.GridAdapter;
import com.orient.me.widget.rv.layoutmanager.table.TableLayoutManager;
import com.orient.me.widget.rv.rv.TableRecyclerView;

import java.util.LinkedList;
import java.util.List;

import base.BActivity;
import base.BConfig;
import base.BWebFragment;
import butterknife.BindView;
import custom.TextView;
import hawk.Hawk;
import util.GoTo;
import util.IncludeUtil;
import util.JsonUtil;
import util.MIntent;
import util.ScreenUtils;

public class WLTHomeActivity extends BActivity {
    @BindView(R.id.mTableView)
    TableRecyclerView mTableView;
    private GridAdapter<TableCell> mAdapter;
    private float r;
    private int[] bgs = {
            0xff28ACA9, 0xff5DABE9, R.mipmap.bg_welcome, 0xffE7A855,
            0xff28ACA9, 0xff01BF9D, 0xffEC8B7B,
            0xffEC8B7B, 0xff28ACA9, 0xff7F80BB, R.mipmap.bg_welcome,
            0xff74C6DB, 0xffE170A7, 0xff00C29F};

    @Override
    public void beforeView() {
        contentViewId = R.layout.activity_home;
    }

    @Override
    public void afterView() {
        log("屏幕尺寸", "" + ScreenUtils.getScreenInch());
        r = (float) (ScreenUtils.getScreenInch() / 8f);
        ScreenUtils.setBounds(mTableView, 1.1, 0.667);
        mTableView.setLayoutManager(new TableLayoutManager(TableLayoutManager.MODE_A, 6, 4));
        mTableView.setAdapter(mAdapter = new GridAdapter<TableCell>() {
            @Override
            public ViewHolder<TableCell> onCreateViewHolder(View root, int viewType) {
                return viewType == R.layout.layout_banner ? new BannerHolder(root) : new ImageHolder(root);
            }

            @Override
            public int getItemLayout(TableCell tableCell, int position) {
                return tableCell.getType() == 3 ? R.layout.layout_banner : R.layout.item_menu;
            }
        });

        List<TableCell> cells = new LinkedList<>();

        cells.add(new TableCell("自查上图", bgs[0], "自查造林小班登记", 1, 1, 1, 1, 1));
        cells.add(new TableCell("自查登记", bgs[1], "营造林展示中心", 1, 1, 2, 1, 1));
        cells.add(new TableCell("", bgs[2], "7", 0, 1, 3, 2, 2));
        cells.add(new TableCell("任务管理", bgs[3], "各工程类型营林任务管理", 1, 1, 5, 2, 1));


        cells.add(new TableCell("造林一张图", bgs[4], "森林长廊、义务植树等自查登记", 1, 2, 1, 2, 1));
        cells.add(new TableCell("进度督查", bgs[5], "省市县自查进度督查", 1, 2, 5, 1, 1));
        cells.add(new TableCell("使用手册", bgs[6], "学习操作中心", 1, 2, 6, 1, 1));


        cells.add(new TableCell("数据统计", bgs[7], "图文播报营林数据", 1, 3, 1, 1, 1));
        cells.add(new TableCell("人员管理", bgs[8], "造林绿化体系管理", 1, 3, 2, 1, 1));
        cells.add(new TableCell("系统管理", bgs[9], "平台设置管理", 1, 3, 3, 2, 1));
        cells.add(new TableCell("我国将建立林木种质资源数据库", bgs[10], "14", 2, 3, 5, 2, 2));


        cells.add(new TableCell("个人中心", bgs[11], "个人信息更新管理", 1, 4, 1, 2, 1));
        cells.add(new TableCell("核查验收", bgs[12], "省市抽检核查验收", 1, 4, 3, 1, 1));
        cells.add(new TableCell("消息中心", bgs[13], "营林信息发布", 1, 4, 4, 1, 1));

        mAdapter.addAllData(cells);
    }

    /**
     * 图片的Holder
     */
    static class BannerHolder extends GridAdapter.ViewHolder<TableCell> {
        public BannerHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void onBind(TableCell tableCell) {
        }
    }

    class ImageHolder extends GridAdapter.ViewHolder<TableCell> {

        TextView textView;
        RelativeLayout contentLayout;

        public ImageHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mTextView);
            contentLayout = itemView.findViewById(R.id.contentLayout);
        }

        @Override
        protected void onBind(TableCell tableCell) {

            switch (tableCell.getType()) {
                case 0:
                    contentLayout.setBackgroundResource(tableCell.getBg());
                    break;
                case 2:
                    contentLayout.setBackgroundResource(tableCell.getBg());
                    textView.setText(tableCell.getName());
                    textView.setDrawablePadding((int) (4 * r));
                    textView.setLeftRes(R.drawable.ic_tz);
                    textView.setTextSize(10 * r);
                    textView.setTextColor(0xffdddddd);
                    contentLayout.setPadding(20, 0, 20,20);
                    contentLayout.setGravity(Gravity.BOTTOM);

                    break;
                case 1:
                    textView.setTextSize(14 * r);
                    textView.setText(tableCell.getName() + "\n" + tableCell.getDesc());
                    textView.setTopRes(tableCell.getIcon());
                    IncludeUtil.with(textView).addStyle(tableCell.getName(), Typeface.BOLD)
                            .addColor(tableCell.getDesc(),0xffeeeeee)
                            .setSize(tableCell.getDesc(), 0.7f);
                    contentLayout.setBackgroundColor(tableCell.getBg());
                    break;
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WLTLoginModel user = Hawk.get(BConfig.LOGIN);
                    GoTo.start(BWebFragment.class, new MIntent(BConfig.TOP_SHOW, false)
                            .putExtra(BConfig.URL, WLTConstant.url + JsonUtil.getJson(user)));
                }
            });
        }
    }
}
