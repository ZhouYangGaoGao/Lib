package base;

import android.animation.Animator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.zhy.android.R;

import org.greenrobot.eventbus.Subscribe;

import adapter.ViewHolder;
import anylayer.Align;
import anylayer.AnimatorHelper;
import anylayer.AnyLayer;
import anylayer.DialogLayer;
import anylayer.Layer;
import custom.SmartView;
import custom.TextView;
import enums.CacheType;
import util.ScreenUtils;

public class BHistoryFragment extends BListFragment<String> implements View.OnClickListener,
        TextWatcher, Layer.OnShowListener, DialogLayer.AnimatorCreator {
    protected SmartView histSmartView;
    private DialogLayer dialog;
    protected int historyCount = 10;
    private FragmentManager fragmentManager;

    {
        info.showTop = false;
        info.useEventBus = true;
        grid.bgColor = 0xffeeeeee;
        card.card = false;
        info.setCacheType(CacheType.only);
    }

    @Override
    public void initView() {
        super.initView();
        refreshLayout.setEnablePureScrollMode(true);
        mStatusView.empty();
        mStatusView.getTextView().setText("无历史记录");
    }

    public void setSmartView(SmartView smartView,int historyCount) {
        this.historyCount=historyCount;
        histSmartView = smartView;
        histSmartView.centerEditText.addTextChangedListener(this);
        initSearch();
        initDialog();
    }

    private void initDialog() {
        if (dialog == null) {
            dialog = AnyLayer.popup(histSmartView.topContent)
                    .align(Align.Direction.VERTICAL, Align.Horizontal.CENTER, Align.Vertical.BELOW, true)
                    .outsideInterceptTouchEvent(false)
                    .outsideTouchedToDismiss(true)
                    .backgroundDimAmount(0f)
                    .contentView(View.inflate(BApp.app().act(), R.layout.layout_history, null))
                    .contentAnimator(this);

        }
        dialog.onShowListener(this).show();
    }

    @Override
    public void onShowing(Layer layer) {
    }

    @Override
    public void onShown(Layer layer) {
        if (fragmentManager == null) {
            fragmentManager = ((AppCompatActivity) histSmartView.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mHistory, this).commit();
            histSmartView.rightTextView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (!histSmartView.actionErrorCheck()) {
            addHistory(histSmartView.getText());
        }
    }

    private void initSearch() {
        histSmartView.centerEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    || keyEvent.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
                String text = histSmartView.getText();
                addHistory(text);
            }
            return false;
        });
    }

    @Subscribe
    public void addHistory(SearchBean searchBean) {
        addHistory(searchBean.keyWord);
    }

    private void addHistory(String text) {
        mData.remove(text);
        if (!TextUtils.isEmpty(text)) {
            ScreenUtils.hideKeyBoard(histSmartView.centerEditText);
            histSmartView.centerEditText.setText(text);
            histSmartView.centerEditText.setSelection(text.length());
            if (histSmartView.getListener() != null) {
                histSmartView.getListener().onClick(histSmartView, 3, 0);
            }
            mData.add(0, text);
            if (mData.size() > historyCount) mData.remove(historyCount - 1);
            upData();
            dialog.dismiss();
        }
    }

    @Override
    public Animator createInAnimator(View content) {
        return AnimatorHelper.createAlphaInAnim(content);
    }

    @Override
    public Animator createOutAnimator(View content) {
        return AnimatorHelper.createAlphaOutAnim(content);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!histSmartView.isEmpty()) return;
        if (!dialog.isShow()) {
            dialog.show();
            histSmartView.centerEditText.requestFocus();
        }
    }

    @Override
    public void convert(ViewHolder h, String i) {
        TextView textView = h.getTextView(R.id.title);
        textView.setText(i);
        textView.setRightRes(R.drawable.ic_off_dark);
        textView.setOnClickListener(v -> {
            if (textView.drawableIndex == 2) {
                mData.remove(i);
                upData();
            } else onItemClick(h, i);
        });
    }

    @Override
    protected void onItemClick(ViewHolder h, String i) {
        addHistory(i);
    }


    public static class SearchBean {
        private String keyWord;
        private String type;

        public SearchBean(String keyWord) {
            this.keyWord = keyWord;
        }

        public SearchBean(String keyWord, String type) {
            this.keyWord = keyWord;
            this.type = type;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public String getType() {
            return type;
        }
    }
}
