/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package photopicker.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zhy.photo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import photopicker.adapter.CCRPhotoPageAdapter;
import photopicker.baseadapterlib.CCROnNoDoubleClickListener;
import photopicker.loader.CCRImage;
import photopicker.loader.CCRImageLoader;
import photopicker.photoview.PhotoViewAttacher;
import photopicker.utils.CCRAsyncTask;
import photopicker.utils.CCRPhotoPickerUtil;
import photopicker.utils.CCRSavePhotoTask;
import photopicker.utils.MyUtilHelper;
import photopicker.widgets.CCRHackyViewPager;


/**
 * @author Acheng
 * @Created on 2020/5/16.
 * @Email 345887272@qq.com
 * @Description 说明:图片预览界面
 */
public class CCRPhotoPreviewActivity extends CCRPPToolbarActivity implements PhotoViewAttacher.OnViewTapListener, CCRAsyncTask.Callback<Void>, CCRPhotoPageAdapter.LongClickListener {
    private static final String EXTRA_SAVE_PHOTO_DIR = "EXTRA_SAVE_PHOTO_DIR";
    private static final String EXTRA_PREVIEW_PHOTOS = "EXTRA_PREVIEW_PHOTOS";
    private static final String EXTRA_CURRENT_POSITION = "EXTRA_CURRENT_POSITION";
    private static final String CLICK_CLOSE = "CLICK_CLOSE";

    private TextView mTitleTv;
    private ImageView mDownloadIv;
    private TextView numberText;
    private CCRHackyViewPager mContentHvp;
    private CCRPhotoPageAdapter mPhotoPageAdapter;
    private TextView saveButton;
    private RelativeLayout saveLayout;
    private boolean mIsSinglePreview;
    private boolean isClickClose = false;//是否单击图片关闭预览
    private File mSavePhotoDir;

    private boolean mIsHidden = false;
    private CCRSavePhotoTask mSavePhotoTask;

    /**
     * 上一次标题栏显示或隐藏的时间戳
     */
    private long mLastShowHiddenTime;

    @Override
    public boolean onLongClick(View v) {
        MyUtilHelper.showAnimation(1, true, saveLayout, this);
        return true;
    }

    public static class IntentBuilder {
        private Intent mIntent;

        public IntentBuilder(Context context) {
            mIntent = new Intent(context, CCRPhotoPreviewActivity.class);
        }

        /**
         * 保存图片的目录，如果传 null，则没有保存图片功能
         */
        public IntentBuilder saveImgDir(@Nullable File saveImgDir) {
            mIntent.putExtra(EXTRA_SAVE_PHOTO_DIR, saveImgDir);
            return this;
        }

        /**
         * 当前预览的图片路径
         */
        public IntentBuilder previewPhoto(String photoPath) {
            mIntent.putStringArrayListExtra(EXTRA_PREVIEW_PHOTOS, new ArrayList<>(Arrays.asList(photoPath)));
            return this;
        }

        /**
         * 当前预览的图片路径集合
         */
        public IntentBuilder previewPhotos(ArrayList<String> previewPhotos) {
            mIntent.putStringArrayListExtra(EXTRA_PREVIEW_PHOTOS, previewPhotos);
            return this;
        }

        /**
         * 当前预览的图片索引
         */
        public IntentBuilder currentPosition(int currentPosition) {
            mIntent.putExtra(EXTRA_CURRENT_POSITION, currentPosition);
            return this;
        }

        public Intent build() {
            return mIntent;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setNoLinearContentView(R.layout.bga_pp_activity_photo_preview);
        mContentHvp = findViewById(R.id.hvp_photo_preview_content);

        mToolbar.setVisibility(View.GONE);//图片预览时隐藏导航栏
        MyUtilHelper.hideBottomUIMenu(this);//进入图片预览界面隐藏虚拟按键
    }

    @Override
    protected void setListener() {
        mContentHvp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                renderTitleTv();
            }
        });


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        isClickClose = getIntent().getBooleanExtra(CLICK_CLOSE, true);
        mSavePhotoDir = (File) getIntent().getSerializableExtra(EXTRA_SAVE_PHOTO_DIR);
        if (mSavePhotoDir != null && !mSavePhotoDir.exists()) {
            mSavePhotoDir.mkdirs();
        }

        ArrayList<String> previewPhotos = getIntent().getStringArrayListExtra(EXTRA_PREVIEW_PHOTOS);
        int currentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);
        mIsSinglePreview = previewPhotos.size() == 1;
        if (mIsSinglePreview) {
            currentPosition = 0;
        }

        mPhotoPageAdapter = new CCRPhotoPageAdapter(this, previewPhotos, this);
        mContentHvp.setAdapter(mPhotoPageAdapter);
        mContentHvp.setCurrentItem(currentPosition);

        // 过2秒隐藏标题栏
        mToolbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                hiddenTitleBar();
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bga_pp_menu_photo_preview, menu);
        MenuItem menuItem = menu.findItem(R.id.item_photo_preview_title);
        View actionView = menuItem.getActionView();

        mTitleTv = actionView.findViewById(R.id.tv_photo_preview_title);
        saveButton = findViewById(R.id.save);
        saveLayout = findViewById(R.id.save_layout);
        numberText = findViewById(R.id.number_text);
        mDownloadIv = actionView.findViewById(R.id.iv_photo_preview_download);
        mDownloadIv.setVisibility(View.GONE);
        mDownloadIv.setOnClickListener(new CCROnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mSavePhotoTask == null) {
                    savePic();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtilHelper.showAnimation(1, false, saveLayout, CCRPhotoPreviewActivity.this);
                savePic();
            }
        });
        saveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtilHelper.showAnimation(1, false, saveLayout, CCRPhotoPreviewActivity.this);
            }
        });
//        if (mSavePhotoDir == null) {
//            mDownloadIv.setVisibility(View.INVISIBLE);
//        }

        renderTitleTv();

        return true;
    }

    private void renderTitleTv() {
        if (mTitleTv == null || mPhotoPageAdapter == null) {
            return;
        }

        if (mIsSinglePreview) {
            //mTitleTv.setText(R.string.bga_pp_view_photo);
        } else {
            numberText.setText((mContentHvp.getCurrentItem() + 1) + "/" + mPhotoPageAdapter.getCount());
            //mTitleTv.setText((mContentHvp.getCurrentItem() + 1) + "/" + mPhotoPageAdapter.getCount());
        }
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        if (isClickClose) {
            finish();
            overridePendingTransition(0, R.anim.a3);
        } else {
            if (System.currentTimeMillis() - mLastShowHiddenTime > 500) {
                mLastShowHiddenTime = System.currentTimeMillis();
                if (mIsHidden) {
                    showTitleBar();
                } else {
                    hiddenTitleBar();
                }
            }
        }
    }

    private void showTitleBar() {
        if (mToolbar != null) {
            ViewCompat.animate(mToolbar).translationY(0).setInterpolator(new DecelerateInterpolator(2)).setListener(new ViewPropertyAnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(View view) {
                    mIsHidden = false;
                }
            }).start();
        }
    }

    private void hiddenTitleBar() {
        if (mToolbar != null) {
            ViewCompat.animate(mToolbar).translationY(-mToolbar.getHeight()).setInterpolator(new DecelerateInterpolator(2)).setListener(new ViewPropertyAnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(View view) {
                    mIsHidden = true;
                }
            }).start();
        }
    }

    private synchronized void savePic() {
        if (mSavePhotoTask != null) {
            return;
        }

        final String url = mPhotoPageAdapter.getItem(mContentHvp.getCurrentItem());
        File file;
        if (url.startsWith("file")) {
            file = new File(url.replace("file://", ""));
            if (file.exists()) {
                CCRPhotoPickerUtil.showSafe(getString(R.string.bga_pp_save_img_success_folder, file.getParentFile().getAbsolutePath()));
                return;
            }
        }

        // 通过MD5加密url生成文件名，避免多次保存同一张图片
        file = new File(mSavePhotoDir, CCRPhotoPickerUtil.md5(url) + ".png");
        if (file.exists()) {
            CCRPhotoPickerUtil.showSafe(getString(R.string.bga_pp_save_img_success_folder, mSavePhotoDir.getAbsolutePath()));
            return;
        }

        mSavePhotoTask = new CCRSavePhotoTask(this, this, file);
        CCRImage.download(url, new CCRImageLoader.DownloadDelegate() {
            @Override
            public void onSuccess(String url, Bitmap bitmap) {
                if (mSavePhotoTask != null) {
                    mSavePhotoTask.setBitmapAndPerform(bitmap);
                }
            }

            @Override
            public void onFailed(String url) {
                mSavePhotoTask = null;
                CCRPhotoPickerUtil.show(R.string.bga_pp_save_img_failure);
            }
        });
    }

    @Override
    public void onPostExecute(Void aVoid) {
        mSavePhotoTask = null;
    }

    @Override
    public void onTaskCancelled() {
        mSavePhotoTask = null;
    }

    @Override
    protected void onDestroy() {
        if (mSavePhotoTask != null) {
            mSavePhotoTask.cancelTask();
            mSavePhotoTask = null;
        }
        super.onDestroy();
    }
}