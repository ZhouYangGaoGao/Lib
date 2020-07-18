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
    public ImageView imageView;

    public ImageViewCard(Context context) {
        this(context, null);
    }

    public ImageViewCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.ImageViewCard);
        Drawable drawable = t.getDrawable(R.styleable.ImageViewCard_android_src);
        t.recycle();
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        addView(imageView);
        setCardElevation(0);
    }

    public ImageViewCard loadImage(Object model) {
        ImageUtils.loadImage(imageView.getContext(), model, imageView);
        return this;
    }

    public ImageViewCard loadImage(Object model,int dfSrcId) {
        ImageUtils.loadImage(imageView.getContext(), model,dfSrcId, imageView);
        return this;
    }

    public void loadImageResize(Object model, int w, int h) {
        ImageUtils.loadImageResize(imageView.getContext(), model, imageView,w,h);
    }

    public ImageView getImageView() {
        return imageView;
    }

}
