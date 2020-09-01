package com.zhy.photo;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.zhy.lifecycle.LifeCycle;

import java.util.List;

import photopicker.lib.PictureSelectionModel;
import photopicker.lib.PictureSelector;
import photopicker.lib.config.PictureConfig;
import photopicker.lib.entity.LocalMedia;

public class FileModel {
    /**
     * type
     * public final static int TYPE_ALL = 0;
     * public final static int TYPE_IMAGE = 1;
     * public final static int TYPE_VIDEO = 2;
     * public final static int TYPE_AUDIO = 3;
     */
    int compressSize = 1014;//压缩大小
    boolean onlyCamera;//直接打开相机
    int type = 1;//类型
    int max = 9;//最大选择数量
    int min = 1;//最小选择数量
    boolean single;//是否单选
    boolean compress = true;//是否压缩
    boolean isCrop;//是否裁切
    boolean isGif;//是否选gif
    boolean circleCrop;//是否显示圆形裁切框
    boolean showCamera;//是否显示相机按钮
    boolean preview;//是否开启预览

    private Context context;
    private Fragment lifeFragment;
    private PictureSelector pictureSelector;
    private OnFilesGetListener listener;

    public FileModel(Context context, OnFilesGetListener listener) {
        this.context = context;
        this.listener = listener;
    }

    PictureSelectionModel getModel(boolean onlyCamera, int type) {
        return onlyCamera ? getPictureSelector().openCamera(type) : getPictureSelector().openGallery(type);
    }

    PictureSelector getPictureSelector() {
        if (pictureSelector == null) {
            pictureSelector = PictureSelector.create((Activity) context);
        }
        return pictureSelector;
    }

    public void go(List<LocalMedia> mediaList) {
        if (lifeFragment == null) {
            lifeFragment = LifeCycle.setOnResult((AppCompatActivity) context, (requestCode, resultCode, data) -> {
                if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
                    if (listener != null)
                        listener.files(PictureSelector.obtainMultipleResult(data));
                }
            });
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
                .selectionMedia(mediaList)//选择的文件列表
                .forResult(lifeFragment, PictureConfig.CHOOSE_REQUEST);//
    }

    public void setListener(OnFilesGetListener listener) {
        this.listener = listener;
    }

    public interface OnFilesGetListener {
        void files(List<LocalMedia> mediaList);
    }
}
