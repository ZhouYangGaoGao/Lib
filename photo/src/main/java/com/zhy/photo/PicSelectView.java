package com.zhy.photo;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.lifecycle.LifeCycle;

import java.util.List;

import photopicker.adapter.GridImageAdapter;
import photopicker.lib.PictureSelectionModel;
import photopicker.lib.PictureSelector;
import photopicker.lib.config.PictureConfig;
import photopicker.lib.config.PictureMimeType;
import photopicker.lib.decoration.GridSpacingItemDecoration;
import photopicker.lib.entity.LocalMedia;
import photopicker.lib.style.PictureParameterStyle;

public class PicSelectView extends RecyclerView {
    private GridImageAdapter adapter;
    private PictureSelector pictureSelector;
    private int numColumns = 4, max = 9;
    private View.OnClickListener addListener;

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        init();
    }

    public PicSelectView(@NonNull Context context) {
        this(context, null);
    }

    public PicSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.PicSelectView);
        max = t.getInt(R.styleable.PicSelectView_max, 9);
        int min = t.getInt(R.styleable.PicSelectView_min, 1);
        numColumns = t.getInt(R.styleable.PicSelectView_numColumns, 4);
        int orientation = t.getInt(R.styleable.PicSelectView_android_orientation, 1);
        int compressSize = t.getInt(R.styleable.PicSelectView_compressSize, 1000);
        int type = t.getInt(R.styleable.PicSelectView_type, 1);
        this.offset = dip2px(t.getInt(R.styleable.PicSelectView_offset, 1));
        boolean onlyCamera = t.getBoolean(R.styleable.PicSelectView_onlyCamera, false);
        boolean showCamera = t.getBoolean(R.styleable.PicSelectView_showCamera, true);
        boolean compress = t.getBoolean(R.styleable.PicSelectView_compress, false);
        boolean isCrop = t.getBoolean(R.styleable.PicSelectView_isCrop, false);
        boolean preview = t.getBoolean(R.styleable.PicSelectView_preview, true);
        boolean single = t.getBoolean(R.styleable.PicSelectView_single, false);
        boolean isGif = t.getBoolean(R.styleable.PicSelectView_isGif, false);
        boolean circleCrop = t.getBoolean(R.styleable.PicSelectView_circleCrop, false);
        boolean onlyShow = t.getBoolean(R.styleable.PicSelectView_onlyShow, false);
        t.recycle();
        addItemDecoration(new GridSpacingItemDecoration(numColumns, (int) this.offset, false));
        setLayoutManager(new GridLayoutManager(context, numColumns, orientation, false));

        Fragment lifeFragment = LifeCycle.setOnActivityResult((AppCompatActivity) getContext(), (requestCode, resultCode, data) -> {
            if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
                initData(PictureSelector.obtainMultipleResult(data));
            }
        });

        addListener = onlyShow ? null : view -> {
            getModel(onlyCamera, type)//打开相册或相机,选择文件类型
                    .maxSelectNum(max)//最大选择数量
                    .minSelectNum(min)//最小选择数量
                    .selectionMode(single ? 1 : 2)//是否单选
                    .compress(compress)//是否压缩
                    .minimumCompressSize(compressSize)//压缩大小
                    .enableCrop(isCrop)//是否裁剪
                    .circleDimmedLayer(circleCrop)//圆形裁剪框
                    .isGif(isGif)//
                    .previewImage(preview)//是否预览
                    .previewVideo(preview)
                    .enablePreviewAudio(preview)
                    .isCamera(showCamera)//是否显示相机按钮
                    .selectionMedia(adapter.getList())//选择的文件列表
                    .forResult(lifeFragment, PictureConfig.CHOOSE_REQUEST);//
        };
    }

    float offset = 1;

    public void init() {
        if (adapter != null) return;
        setAdapter(adapter = new GridImageAdapter(getContext(), (int) ((getMeasuredWidth() - (offset
                * (numColumns - 1)) - getPaddingLeft() - getPaddingRight()) / numColumns), addListener));
        adapter.setSelectMax(max);
        adapter.setOnItemClickListener((position, v) -> {
            LocalMedia media = adapter.getList().get(position);
            String mimeType = media.getMimeType();
            int mediaType = PictureMimeType.getMimeType(mimeType);
            switch (mediaType) {
                case PictureConfig.TYPE_VIDEO:// 预览视频
                    getPictureSelector().externalPictureVideo(media.getPath());
                    break;
                case PictureConfig.TYPE_AUDIO:// 预览音频
                    getPictureSelector().externalPictureAudio(media.getPath());
                    break;
                default:// 预览图片
                    getPictureSelector()
                            .themeStyle(R.style.picture_default_style)
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .setPictureStyle(new PictureParameterStyle())// 动态自定义相册主题, 这个是配合 .theme();结合使用
                            .openExternalPreview(position, adapter.getList());
                    break;
            }
        });
    }

    private PictureSelectionModel getModel(boolean onlyCamera, int type) {
        return onlyCamera ? getPictureSelector().openCamera(type) : getPictureSelector().openGallery(type);
    }

    private PictureSelector getPictureSelector() {
        if (pictureSelector == null) {
            pictureSelector = PictureSelector.create((Activity) getContext());
        }
        return pictureSelector;
    }

    public List<LocalMedia> getSelectList() {
        return adapter.getList();
    }

    public void initData(List<LocalMedia> mediaList) {
        adapter.setList(mediaList);
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
