package custom;

import android.animation.Animator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.zhy.android.R;

import adapter.ViewHolder;
import anylayer.Align;
import anylayer.AnimatorHelper;
import anylayer.AnyLayer;
import anylayer.DialogLayer;
import anylayer.Layer;
import base.BSmartFragment;
import util.ScreenUtils;

public class HistoryFragment extends BSmartFragment<String> {
    protected SmartView histSmartView;
    private DialogLayer dialog;
    protected int historyCount = 10;

    {
        showTopBar = false;
        isRefresh = false;
        bgColor = 0xffaaaaaa;
    }

    @Override
    public void initView() {
        super.initView();
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
    }

    public void setSmartView(SmartView smartView) {
        histSmartView = smartView;

        initTextChange();

        initSearch();

        initDialog();
    }

    private void initDialog() {
        if (dialog == null) {
            View view = View.inflate(histSmartView.getContext(), R.layout.layout_history, null);
            dialog = AnyLayer.popup(histSmartView.topContent)
                    .align(Align.Direction.VERTICAL, Align.Horizontal.CENTER, Align.Vertical.BELOW, true)
                    .outsideInterceptTouchEvent(false)
                    .outsideTouchedToDismiss(true)
                    .backgroundDimDefault()
                    .contentView(view)
                    .contentAnimator(new DialogLayer.AnimatorCreator() {
                        @Override
                        public Animator createInAnimator(View content) {
                            return AnimatorHelper.createTopInAnim(content);
                        }

                        @Override
                        public Animator createOutAnimator(View content) {
                            return AnimatorHelper.createTopOutAnim(content);
                        }
                    });

        }
        dialog.onShowListener(new Layer.OnShowListener() {
            private FragmentManager fragmentManager;

            @Override
            public void onShowing(Layer layer) {

            }

            @Override
            public void onShown(Layer layer) {
                if (fragmentManager == null) {
                    fragmentManager = ((AppCompatActivity) histSmartView.getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.mHistory, HistoryFragment.this)
                            .commit();
                    histSmartView.rightTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!histSmartView.actionErrorCheck()) {
                                addHistory(histSmartView.getText());
                            }
                        }
                    });
                }
            }
        }).show();
    }

    private void initSearch() {
        histSmartView.centerEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    || keyEvent.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
                String text = histSmartView.getText();
                if (mData.contains(text)) {
                    mData.remove(text);
                }
                if (!TextUtils.isEmpty(text)) {
                    addHistory(text);

                }
            }
            return false;
        });
    }

    private void addHistory(String text) {
        if (histSmartView.getListener() != null) {
            histSmartView.getListener().onClick(histSmartView, 3, 0);
        }
        mData.add(0, text);
        if (mData.size() > historyCount) mData.remove(historyCount - 1);
        upData();
        dialog.dismiss();
    }

    private void initTextChange() {
        histSmartView.centerEditText.addTextChangedListener(new TextWatcher() {

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
                    log("mData", mData.size() + "");
                    histSmartView.centerEditText.requestFocus();
                }
            }
        });
    }

    @Override
    public void convert(ViewHolder h, String i) {
        TextView textView = h.getTextView(R.id.title);
        textView.setText(i);
        textView.setRightRes(R.drawable.ic_off_dark);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.drawableIndex == 2) {
                    mData.remove(i);
                    upData();
                } else onItemClick(h, i);
            }
        });
    }

    @Override
    protected void onItemClick(ViewHolder h, String i) {
        ScreenUtils.hideKeyBoard(histSmartView.centerEditText);
        histSmartView.centerEditText.setText(i);
        histSmartView.centerEditText.setSelection(i.length());
        if (histSmartView.getListener() != null) {
            histSmartView.getListener().onClick(histSmartView, 3, 0);
        }
        dialog.dismiss();
        mData.remove(i);
        mData.add(0, i);
        upData();
    }
}
