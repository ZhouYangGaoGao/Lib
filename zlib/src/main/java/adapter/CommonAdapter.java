package adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 万能适配器
 * 适用于 ListView GridView RecyclerView等
 * Created by YangYang on 2015/7/27.
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected List<T> listData;
    protected final int mItemLayoutId;
    private SparseArray<View> views;

    public CommonAdapter(Context context, List<T> listData, int itemLayoutId) {
        this.mInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.mItemLayoutId = itemLayoutId;
        this.views = new SparseArray<>();
    }

    @Override
    public int getCount() {
        if (listData == null) return 0;
        return listData.size();
    }

    @Override
    public T getItem(int position) {
        if (listData == null) return null;
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = views.get(position);
        final ViewHolder viewHolder = ViewHolder.get(convertView, mItemLayoutId, position);
        convert(viewHolder, getItem(position));
        views.put(position, convertView);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder h, T i);

}