package custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.zhy.android.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.List;

import base.BActivity;
import base.BFragment;
import cn.bingoogolapple.photopicker.adapter.GridImageAdapter;
import listener.OnResultListener;
import util.LogUtils;

public class PicSelectView extends RecyclerView implements OnResultListener {
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
        int offset = t.getInt(R.styleable.PicSelectView_offset, 1);
        this.offset = ScreenUtils.dip2px(context, offset);
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
        setLayoutManager(new GridLayoutManager(context, numColumns));
        addListener = onlyShow ? null : new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext() instanceof BActivity) {
                    ((BActivity) getContext()).addResultListener(PicSelectView.this);
                }
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
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }

        };
    }

    float offset = 1;

    public void init() {
        if (adapter != null) return;
        setAdapter(adapter = new GridImageAdapter(getContext(), (int) ((getMeasuredWidth() - (offset * (numColumns - 1))) / numColumns), addListener));
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

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    initData(PictureSelector.obtainMultipleResult(data));
                    break;
            }
        }
    }

    public void initData(List<LocalMedia> mediaList) {
        adapter.setList(mediaList);
    }
}
