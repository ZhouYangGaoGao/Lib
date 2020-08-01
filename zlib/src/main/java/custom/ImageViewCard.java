package custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


import androidx.cardview.widget.CardView;

import com.zhy.android.R;

import util.ImageUtils;

/**
 * 图片卡  就是加圆角和阴影用的
 * Created by YangYang on 2017/11/7.
 */

public class ImageViewCard extends CardView {
    private ImageView imageView;
    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    public ImageViewCard(Context context) {
        this(context, null);
    }

    public ImageViewCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageView = new ImageView(context);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.ImageViewCard);
        Drawable drawable = t.getDrawable(R.styleable.ImageViewCard_android_src);
        int scaleType = t.getInt(R.styleable.ImageViewCard_android_scaleType, 6);
        t.recycle();
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        imageView.setScaleType(sScaleTypeArray[scaleType]);
        addView(imageView);
    }

    public ImageViewCard loadImage(Object model) {
        ImageUtils.loadImage(imageView.getContext(), model, imageView);
        return this;
    }

    public ImageViewCard loadImage(Object model, int dfSrcId) {
        ImageUtils.loadImage(imageView.getContext(), model, dfSrcId, imageView);
        return this;
    }

    public void loadImageResize(Object model, int w, int h) {
        ImageUtils.loadImageResize(imageView.getContext(), model, imageView, w, h);
    }

    public ImageView getImageView() {
        return imageView;
    }

}
