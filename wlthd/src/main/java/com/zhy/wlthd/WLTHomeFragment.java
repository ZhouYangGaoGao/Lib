package com.zhy.wlthd;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.orient.me.widget.rv.adapter.GridAdapter;
import com.orient.me.widget.rv.layoutmanager.table.TableLayoutManager;
import com.orient.me.widget.rv.rv.TableRecyclerView;
import com.zhy.wlthd.bean.Check;
import com.zhy.wlthd.bean.News;
import com.zhy.wlthd.bean.Permission;
import com.zhy.wlthd.bean.TableCell;
import com.zhy.wlthd.bean.User;
import com.zhy.wlthd.manager.WLTApp;
import com.zhy.wlthd.manager.WLTManager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import base.BApp;
import base.BConfig;
import base.BListDataFragment;
import base.BSub;
import base.BWebFragment;
import butterknife.BindView;
import custom.TextView;
import enums.CacheType;
import hawk.Hawk;
import rx.Observable;
import util.DateUtil;
import util.GoTo;
import util.ImageUtils;
import util.IncludeUtil;
import util.JsonUtil;
import util.MIntent;
import util.ScreenUtils;
import util.layout.RLParams;

public class WLTHomeFragment extends BListDataFragment<Permission> {
    @BindView(R.id.mTableView)
    TableRecyclerView mTableView;
    private GridAdapter<TableCell> mAdapter;
    private List<TableCell> cells = new LinkedList<>();
    private User user;
    private float r;
    private News news;
    private List<Check> check;
    private int[] bgs = {
            0xff28ACA9, 0xff5DABE9, R.mipmap.bg_welcome, 0xffDCAA63,
            0xffDF907F, 0xff01BF9D, 0xffEC8B7B,
            0xffE7A856, 0xff28ACA9, 0xff7F80BB, R.mipmap.bg_welcome,
            0xff74C6DB, 0xffE170A7, 0xff00C29F};

    private int[] icons = {
            R.drawable.ic_zcst, R.drawable.ic_zcdj, 0, R.drawable.ic_rwgl,
            R.drawable.ic_ylyzt, R.drawable.ic_jddc, R.drawable.ic_sysc,
            R.drawable.ic_sjtj, R.drawable.ic_rygl, R.drawable.ic_hcys, R.drawable.ic_tz,
            R.drawable.ic_grzx, R.drawable.ic_xtgl, R.drawable.ic_xxzx};

    private String[] urls = {
            "self-registration", "info-filling", "self-registration", "task-objectives",
            "frost-picture", "inspector-general", "operation-manual",
            "statistical", "person-manage", "spot-check", "news-information",
            "user-management", "account-manage", "notice-announcement",
    };

    @Override
    public void beforeView() {
        user = Hawk.get(BConfig.LOGIN);
        contentViewId = R.layout.activity_home;
        info.setCacheType(CacheType.none);
    }

    @Override
    public void afterView() {
        log("屏幕尺寸", "" + ScreenUtils.getScreenInch());
        r = (float) (ScreenUtils.getScreenInch() / 8f);
        ScreenUtils.setBounds(mTableView, 1.3, 0.8);
        getNews();
        getCheck();
        mTableView.setLayoutManager(new TableLayoutManager(TableLayoutManager.MODE_A, 6, 4) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mTableView.setNestedScrollingEnabled(true);
        mTableView.setAdapter(mAdapter = new GridAdapter<TableCell>() {
            @Override
            public ViewHolder<TableCell> onCreateViewHolder(View root, int viewType) {
                return new ImageHolder(root);
            }

            @Override
            public int getItemLayout(TableCell tableCell, int position) {
                return R.layout.item_menu;
            }
        });

        cells.add(new TableCell(0, 1, 1, 1, 1, 1));
        cells.add(new TableCell(1, 1, 1, 2, 1, 1));
        cells.add(new TableCell(2, 2, 1, 3, 2, 2));
        cells.add(new TableCell(3, 1, 1, 5, 2, 1));

        cells.add(new TableCell(4, 1, 2, 1, 2, 1));
        cells.add(new TableCell(5, 1, 2, 5, 1, 1));
        cells.add(new TableCell(6, 1, 2, 6, 1, 1));

        cells.add(new TableCell(7, 1, 3, 1, 1, 1));
        cells.add(new TableCell(8, 1, 3, 2, 1, 1));
        cells.add(new TableCell(9, 1, 3, 3, 2, 1));
        cells.add(new TableCell(10, 0, 3, 5, 2, 2));

        cells.add(new TableCell(11, 1, 4, 1, 2, 1));
        cells.add(new TableCell(12, 1, 4, 3, 1, 1));
        cells.add(new TableCell(13, 1, 4, 4, 1, 1));

    }

    private void getCheck() {
        presenter.sub(new BSub<List<Check>>(WLTManager.api().taskPlanList(DateUtil.getYear(new Date()) + "")) {
            @Override
            public void onSuccess(List<Check> obj) {
                check = obj;
            }
        });
    }

    private void getNews() {
        presenter.sub(new BSub<News>(WLTManager.api().indexNotice("news")) {
            @Override
            public void onSuccess(News obj) {
                news = obj;
            }
        });
    }

    @Override
    protected void upData() {
        mAdapter.addAllData(cells);
    }

    @Override
    protected Observable<?> get() {
        return WLTManager.api().getRolePermission();
    }

    class ImageHolder extends GridAdapter.ViewHolder<TableCell> {

        TextView textView;
        ImageView imageView;
        RelativeLayout contentLayout;

        public ImageHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mTextView);
            imageView = itemView.findViewById(R.id.mImageView);
            contentLayout = itemView.findViewById(R.id.contentLayout);
        }

        @Override
        protected void onBind(TableCell tableCell) {
            switch (tableCell.getType()) {
                case 0:
                    contentLayout.setOnClickListener(v -> GoTo.start(BWebFragment.class, new MIntent(BConfig.TOP_SHOW, false)
                            .putExtra(BConfig.URL, WLTApp.url.replace(urls[6], urls[tableCell.getIndex()]) + JsonUtil.getJson(user))));
                    if (icons[tableCell.getIndex()] == 0) return;
                    textView.setDrawablePadding((int) (4 * r));
                    textView.setLeftRes(icons[tableCell.getIndex()]);
                    textView.setTextSize(10 * r);
                    textView.setTextColor(0xffdddddd);
                    textView.setLayoutParams(RLParams.MW().rule(RelativeLayout.ALIGN_PARENT_BOTTOM));
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setBackgroundColor(0x66000000);
                    textView.setPadding(20, 10, 20, 10);
                    textView.setText(news == null ? "新闻资讯" : news.getTitle());
                    if (news == null)
                        contentLayout.setBackgroundResource(bgs[tableCell.getIndex()]);
                    else
                        ImageUtils.loadImage(getActivity(), news.getPostImg(), imageView);
                    break;
                case 2:
                    if (BuildConfig.DEBUG){
                        textView.setMaxLines(4);
                        textView.setText(user.getRealName() +
                                "\n" + user.getPhone() + "\n" +
                                "版本号:" + BuildConfig.VERSION_NAME);
                        imageView.setOnClickListener(v -> BApp.app().logout());
                    }
                    imageView.setImageResource(bgs[tableCell.getIndex()]);
                    break;
                case 1:
                    if (WLTHomeFragment.this.mData == null) return;
                    for (int i = 0; i < WLTHomeFragment.this.mData.size(); i++) {
                        Permission permission = WLTHomeFragment.this.mData.get(i);
                        if (permission.getCode().equals(urls[tableCell.getIndex()])) {
                            initMenu(tableCell, permission);
                            break;
                        }
                    }
                    break;
            }
        }

        private void initMenu(TableCell tableCell, Permission permission) {
            textView.setTextSize(14 * r);
            textView.setText(permission.getName() + "\n" + permission.getRemarks());
            textView.setTopRes(icons[tableCell.getIndex()]);
            textView.setDrawablePadding(5);
            IncludeUtil.with(textView)
                    .addStyle(permission.getName(), Typeface.BOLD)
                    .addColor(permission.getRemarks(), 0xffeeeeee)
                    .setSize(permission.getRemarks(), 0.75f);
            contentLayout.setBackgroundColor(bgs[tableCell.getIndex()]);
            textView.setOnClickListener(v -> {
                if ("spot-check".equals(permission.getCode())) {
                    if (check == null || check.size() == 0) return;
                    for (int j = 0; j < check.get(0).getPlanList().size(); j++) {
                        if (!"accept_check".equals(check.get(0).getPlanList().get(j).getStage()))
                            continue;
                        if (!check.get(0).getPlanList().get(j).getStatus().equals("doing"))
                            continue;
                        if (TextUtils.isEmpty(check.get(0).getPlanList().get(j).getEndTime()))
                            continue;
                        if (!check.get(0).getPlanList().get(j).getEndTime().startsWith(check.get(0).getYear()))
                            continue;
                        GoTo.start(BWebFragment.class, new MIntent(BConfig.TOP_SHOW, false)
                                .putExtra(BConfig.URL, WLTApp.url.replace(urls[6], urls[tableCell.getIndex()]) + JsonUtil.getJson(user)));
                        return;
                    }
                    toast("请等待抽检结束!");
                } else
                    GoTo.start(BWebFragment.class, new MIntent(BConfig.TOP_SHOW, false)
                            .putExtra(BConfig.URL, WLTApp.url.replace(urls[6], urls[tableCell.getIndex()]) + JsonUtil.getJson(user)));
            });
        }
    }
}