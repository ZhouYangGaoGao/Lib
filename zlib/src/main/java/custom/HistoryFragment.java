package custom;

import android.view.View;
import android.widget.EditText;

import com.zhy.android.R;
import com.zhy.android.adapter.CommonAdapter;

import background.drawable.DrawableCreator;
import base.BSmartFragment;

public class HistoryFragment extends BSmartFragment<String> {
    private EditText editText;

    @Override
    public void beforeView() {
        itemLayoutId = R.layout.item_text;
        bgColor = 0xff999999;
    }

    @Override
    public void afterView() {
        refreshLayout.setEnablePureScrollMode(true);
        mSmartView.topContent.setVisibility(View.GONE);
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void convert(CommonAdapter.ViewHolder h, String i) {
        h.setText(R.id.title, i);
        setBackground(h.getConvertView(),cardPressedColor,0xffffffff);
    }

    @Override
    protected void onItemClick(CommonAdapter.ViewHolder h, String i) {
        if (editText != null) {
            editText.setText(i);
            editText.setSelection(i.length());
        }
    }
}
