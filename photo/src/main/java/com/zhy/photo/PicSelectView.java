package com.zhy.photo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import photopicker.adapter.GridImageAdapter;
import photopicker.lib.config.PictureConfig;
import photopicker.lib.config.PictureMimeType;
import photopicker.lib.decoration.GridSpacingItemDecoration;
import photopicker.lib.entity.LocalMedia;
import photopicker.lib.style.PictureParameterStyle;

public class PicSelectView extends RecyclerView {
    private GridImageAdapter adapter;
    private int numColumns = 4;
    private View.OnClickListener addListener;
    private FileModel fileModel;

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
        fileModel = new FileModel();
        fileModel.init(context);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.PicSelectView);
        fileModel.max = t.getInt(R.styleable.PicSelectView_max, 9);
        fileModel.min = t.getInt(R.styleable.PicSelectView_min, 1);
        numColumns = t.getInt(R.styleable.PicSelectView_numColumns, 4);
        int orientation = t.getInt(R.styleable.PicSelectView_android_orientation, 1);
        fileModel.compressSize = t.getInt(R.styleable.PicSelectView_compressSize, 1000);
        fileModel.type = t.getInt(R.styleable.PicSelectView_type, 1);
        this.offset = dip2px(t.getInt(R.styleable.PicSelectView_offset, 1));
        fileModel.onlyCamera = t.getBoolean(R.styleable.PicSelectView_onlyCamera, false);
        fileModel.showCamera = t.getBoolean(R.styleable.PicSelectView_showCamera, true);
        fileModel.compress = t.getBoolean(R.styleable.PicSelectView_compress, false);
        fileModel.isCrop = t.getBoolean(R.styleable.PicSelectView_isCrop, false);
        fileModel.preview = t.getBoolean(R.styleable.PicSelectView_preview, true);
        fileModel.single = t.getBoolean(R.styleable.PicSelectView_single, false);
        fileModel.isGif = t.getBoolean(R.styleable.PicSelectView_isGif, false);
        fileModel.circleCrop = t.getBoolean(R.styleable.PicSelectView_circleCrop, false);
        boolean onlyShow = t.getBoolean(R.styleable.PicSelectView_onlyShow, false);
        t.recycle();
        addItemDecoration(new GridSpacingItemDecoration(numColumns, (int) this.offset, false));
        setLayoutManager(new GridLayoutManager(context, numColumns, orientation, false));
        fileModel.setListener(new FileModel.OnFilesGetListener() {
            @Override
            public void files(List<LocalMedia> mediaList) {
                initData(mediaList);
            }
        });
        addListener = onlyShow ? null : view -> {
            fileModel.go(adapter.getList());
        };
    }

    float offset = 1;

    public void init() {
        if (adapter != null) return;
        setAdapter(adapter = new GridImageAdapter(getContext(), (int) ((getMeasuredWidth() - (offset
                * (numColumns - 1)) - getPaddingLeft() - getPaddingRight()) / numColumns), addListener));
        adapter.setSelectMax(fileModel.max);
        adapter.setOnItemClickListener((position, v) -> {
            LocalMedia media = adapter.getList().get(position);
            String mimeType = media.getMimeType();
            int mediaType = PictureMimeType.getMimeType(mimeType);
            switch (mediaType) {
                case PictureConfig.TYPE_VIDEO:// 预览视频
                    fileModel.getPictureSelector().externalPictureVideo(media.getPath());
                    break;
                case PictureConfig.TYPE_AUDIO:// 预览音频
                    fileModel.getPictureSelector().externalPictureAudio(media.getPath());
                    break;
                default:// 预览图片
                    fileModel.getPictureSelector()
                            .themeStyle(R.style.picture_default_style)
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .setPictureStyle(new PictureParameterStyle())// 动态自定义相册主题, 这个是配合 .theme();结合使用
                            .openExternalPreview(position, adapter.getList());
                    break;
            }
        });
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
