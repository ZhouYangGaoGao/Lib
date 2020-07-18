package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import base.BApp;
import util.ImageUtils;

public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context context;

    private ViewHolder(int layoutId, int position) {
        this.context = BApp.app().act();
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = View.inflate(context, layoutId, null);
        mConvertView.setTag(this);
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param convertView
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(View convertView, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public ViewHolder changeLayout(int layoutId) {
        mConvertView = View.inflate(context, layoutId, null);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends View> T getTagView(String tag) {
        View view = mConvertView.findViewWithTag(tag);
        if (view == null) return null;
        return (T) view;
    }

    public custom.TextView getTextView(int id) {
        return getView(id);
    }

    public LinearLayout getLinearLayout(int id) {
        return getView(id);
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;

    }

    /**
     * 设置HTML格式text
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setTextHtml(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(Html.fromHtml(text));
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color0x) {
        TextView view = getView(viewId);
        view.setTextColor(color0x);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public ViewHolder setImage(int viewId, Object model) {
//            if (getView(viewId).getClass().getSimpleName().equals("ImageViewCard")) {
//                ImageViewCard imageViewCard = getView(viewId);
//                imageViewCard.loadImage(drawableId);
//            } else
        ImageUtils.loadImage(context, model, (ImageView) getView(viewId));
        return this;
    }

    /**
     * 为View设置背景图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setBgRes(int viewId, int drawableId) {
        View view = getView(viewId);
        view.setBackgroundResource(drawableId);
        return this;
    }

    /**
     * 显示隐藏view
     *
     * @param viewId
     * @param visibility
     * @return
     */
    public ViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 给view设置点击事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setClick(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 给整项设置点击事件
     *
     * @param listener
     * @return
     */
    public ViewHolder setClick(View.OnClickListener listener) {
        mConvertView.setOnClickListener(listener);
        return this;
    }

    /**
     * 给整项设置长按事件
     *
     * @param listener
     * @return
     */
    public ViewHolder setLongClick(View.OnLongClickListener listener) {
        mConvertView.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, Object url) {
        if (url != null)
//                if (getView(viewId).getClass().getSimpleName().equals("ImageViewCard")) {
//                    ImageViewCard imageViewCard = getView(viewId);
//                    imageViewCard.loadImage(url);
//                } else
            ImageUtils.loadImage(context, url, (ImageView) getView(viewId));
        return this;
    }

    public ViewHolder setImageResize(int viewId, Object url, int w, int h) {
        if (url != null)
            ImageUtils.loadImageResize(context, url, (ImageView) getView(viewId), w, h);
        return this;
    }

    /**
     * 设置图片 带默认图
     *
     * @param viewId
     * @param url
     * @param difImgRes
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, String url, int difImgRes) {
//            if (getView(viewId).getClass().getSimpleName().equals("ImageViewCard")) {
//                ImageViewCard imageViewCard = getView(viewId);
//                imageViewCard.loadImage(url, difImgRes);
//            } else
        ImageUtils.loadImage(context, url, difImgRes, (ImageView) getView(viewId));
        return this;
    }

    public void setIncludTextColor(View textView, String keyWord, int colorOxff) {
        TextView textView1 = (TextView) textView;
        String text = textView1.getText().toString().trim();
        if (text.contains(keyWord)) {
            SpannableStringBuilder style = new SpannableStringBuilder(
                    text);
            style.setSpan(new ForegroundColorSpan(colorOxff),
                    text.indexOf(keyWord), text.indexOf(keyWord)
                            + keyWord.length(),
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            textView1.setText(style);
        }
    }

    public void setIncludTextColor(View textView, int colorOxff, String... keyWords) {
        TextView textView1 = (TextView) textView;
        String text = textView1.getText().toString().trim();
        SpannableStringBuilder style = new SpannableStringBuilder(
                text);
        for (int i = 0; i < keyWords.length; i++) {
            if (!TextUtils.isEmpty(keyWords[i]) && text.contains(keyWords[i])) {
                style.setSpan(new ForegroundColorSpan(colorOxff),
                        text.indexOf(keyWords[i]), text.indexOf(keyWords[i])
                                + keyWords[i].length(),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        textView1.setText(style);
    }


    public void setIncludTextBg(TextView textView, final String keyWord, Drawable drawable, final int color) {
        setIncludTextBg(textView, keyWord, drawable, color, 0.75f);
    }

    public void setIncludTextBg(TextView textView, final String keyWord, Drawable drawable, final int color, final float proportion) {
        String text = textView.getText().toString();
        if (text.contains(keyWord)) {
            final float size = textView.getTextSize();
            drawable.setBounds(0, (int) (-0.3 * proportion * size), (int) (size * proportion * (keyWord.length() - 1)), (int) (size * 0.9));
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.setSpan(new ImageSpan(drawable) {
                              @Override
                              public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
                                  super.draw(canvas, text, start, end, x, top, y, (int) (bottom * 0.95), paint);
                                  paint.setColor(color);
                                  paint.setTextSize(proportion * size);
                                  canvas.drawText(text.subSequence(start, end).toString(), (float) (x + 0.26 * proportion * size), (float) (y * 0.95), paint);
                              }
                          },
                    text.indexOf(keyWord), text.indexOf(keyWord)
                            + keyWord.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(style);
        }
    }


    /**
     * 设置字体
     *
     * @param viewId 需要设置字体的TextView id
     * @param tf     字体
     * @return
     */
    public TextView setTypeFace(int viewId, Typeface tf) {
        TextView tv = getView(viewId);
        tv.setTypeface(tf);
        return tv;
    }

}
