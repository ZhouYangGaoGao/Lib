package custom;

import android.view.View;
import android.widget.EditText;

import com.zhy.android.R;
import com.zhy.android.adapter.CommonAdapter;

import background.drawable.DrawableCreator;
import base.SmartFragment;

public class HistoryFragment extends SmartFragment<String> {
    @Override
    public void beforeView() {
        numColumns = 3;
        itemLayoutId = R.layout.item_text;
        for (int i = 0; i < 10; i++) {
            mData.add("item" + 1);
        }
    }

    @Override
    public void afterView() {
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        mTopView.topContent.setVisibility(View.GONE);
    }

    EditText editText;

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void convert(CommonAdapter.ViewHolder h, String i) {
        h.setText(R.id.title, i);
        h.getConvertView().setBackground(new DrawableCreator.Builder().setPressedSolidColor(0xff999999, 0xffffffff).build());
        h.setClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText!=null){
                    editText.setText(i);
                    editText.setSelection(i.length());
                }
            }
        });
    }
}
