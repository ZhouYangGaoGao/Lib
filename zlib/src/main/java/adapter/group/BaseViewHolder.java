package adapter.group;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.databinding.DataBindingUtil;
//import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import custom.ImageCard;
import util.ImageUtils;

/**
 * 通用的RecyclerView.ViewHolder。提供了根据viewId获取View的方法。
 * 提供了对View、TextView、ImageView的常用设置方法。
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private int position;

    public int getGroupPosition() {
        return position;
    }

    public BaseViewHolder setGroupPosition(int position) {
        this.position = position;
        return this;
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

//    /**
//     * 获取item对应的ViewDataBinding对象
//     *
//     * @param <T>
//     * @return
//     */
//    public <T extends ViewDataBinding> T getBinding() {
//        return DataBindingUtil.getBinding(this.itemView);
//    }

    /**
     * 根据View Id 获取对应的View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T get(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    //******** 提供对View、TextView、ImageView的常用设置方法 ******//

    public BaseViewHolder setText(int viewId, String text) {
        TextView tv = get(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setText(int viewId, int textRes) {
        TextView tv = get(viewId);
        tv.setText(textRes);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = get(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setTextSize(int viewId, int size) {
        TextView view = get(viewId);
        view.setTextSize(size);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public BaseViewHolder setImage(int viewId, Object model) {
        if (get(viewId).getClass().getSimpleName().equals("ImageViewCard")) {
            ImageCard imageCard = get(viewId);
            imageCard.loadImage(model);
        } else
            ImageUtils.loadImage(get(viewId).getContext(), model, (ImageView) get(viewId));
        return this;
    }

    public BaseViewHolder setImageRound(int viewId, Object model, int dpRadius) {
        if (model != null)
            ImageUtils.loadRoundImage(get(viewId).getContext(), model, dpRadius, (ImageView) get(viewId));
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = get(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = get(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = get(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder setVisible(int viewId, int visible) {
        View view = get(viewId);
        view.setVisibility(visible);
        return this;
    }

    public custom.TextView getTextView(int id) {
        return get(id);
    }

    public View getConvertView() {
        return mViews.get(position);
    }

    public BaseViewHolder setClick(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }
}
