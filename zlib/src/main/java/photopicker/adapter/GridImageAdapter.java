package photopicker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.android.R;
import photopicker.lib.config.PictureMimeType;
import photopicker.lib.entity.LocalMedia;
import photopicker.lib.tools.DateUtils;
import photopicker.lib.tools.SdkVersionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.pictureselector.adapter
 * email：893855882@qq.com
 * data：16/7/27
 */
public class GridImageAdapter extends
        RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> list = new ArrayList<>();
    private int selectMax = 9;
    /**
     * 点击添加图片跳转
     */
    private View.OnClickListener onClickListener;


    public GridImageAdapter(Context context, View.OnClickListener onClickListener) {
        mInflater = LayoutInflater.from(context);
        if (onClickListener == null) {
            onlyShow = true;
        } else
            this.onClickListener = onClickListener;
    }

    public GridImageAdapter(Context context, int height, View.OnClickListener onClickListener) {
        this.height = height;
        mInflater = LayoutInflater.from(context);
        if (onClickListener == null) {
            onlyShow = true;
        } else
            this.onClickListener = onClickListener;
    }

    private boolean onlyShow = false;

    public GridImageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        onlyShow = true;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }
    private int height;

    public void setList(List<LocalMedia> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        ImageView llDel;
        TextView tvDuration;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.fiv);
            llDel = view.findViewById(R.id.iv_del);
            tvDuration = view.findViewById(R.id.tv_duration);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax && !onlyShow) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    public List<LocalMedia> getList() {
        return list;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.gv_filter_image,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //少于8张，显示继续添加的图标
        if (height != 0)
            viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(height, height));
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.drawable.ic_add_img);
            viewHolder.mImg.setOnClickListener(onClickListener);
            viewHolder.llDel.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.llDel.setVisibility(onlyShow ? View.GONE : View.VISIBLE);
            viewHolder.llDel.setOnClickListener(view -> {
                int index = viewHolder.getAdapterPosition();
                // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                if (index != RecyclerView.NO_POSITION && list.size() > index) {
                    list.remove(index);
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(index, list.size());
                }
            });
            LocalMedia media = list.get(position);
            int chooseModel = media.getChooseModel();
            String path;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = SdkVersionUtils.checkedAndroid_Q() ? media.getAndroidQToPath() : media.getPath();
            }
            // 图片
            if (media.isCompressed()) {
                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                Log.i("压缩地址::", media.getCompressPath());
            }

            Log.i("原图地址::", media.getPath());
            if (media.isCut()) {
                Log.i("裁剪地址::", media.getCutPath());
            }
            long duration = media.getDuration();
            viewHolder.tvDuration.setVisibility(PictureMimeType.eqVideo(media.getMimeType())
                    ? View.VISIBLE : View.GONE);
            if (chooseModel == PictureMimeType.ofAudio()) {
                viewHolder.tvDuration.setVisibility(View.VISIBLE);
                viewHolder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.picture_icon_audio, 0, 0, 0);

            } else {
                viewHolder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.ic_video, 0, 0, 0);
            }
            viewHolder.tvDuration.setText(DateUtils.formatDurationTime(duration));
            if (chooseModel == PictureMimeType.ofAudio()) {
                viewHolder.mImg.setImageResource(R.drawable.picture_audio_placeholder);
            } else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.app_color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(viewHolder.itemView.getContext())
                        .load(path)
                        .apply(options)
                        .into(viewHolder.mImg);
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(v -> {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(adapterPosition, v);
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
