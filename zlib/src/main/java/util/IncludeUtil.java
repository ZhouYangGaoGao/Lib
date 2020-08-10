package util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class IncludeUtil {
    private static IncludeUtil includeUtil;
    private TextView textView;
    private SpannableStringBuilder style;
    private String fullText;

    private IncludeUtil() {
    }

    public static IncludeUtil with(TextView textView) {
        if (includeUtil == null) includeUtil = new IncludeUtil();
        includeUtil.textView = textView;
        includeUtil.fullText = textView.getText().toString().trim();
        includeUtil.style = new SpannableStringBuilder(includeUtil.fullText);
        return includeUtil;
    }

    public IncludeUtil addColor(String text, int color) {
        style.setSpan(new ForegroundColorSpan(color),
                fullText.indexOf(text), fullText.indexOf(text)
                        + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return includeUtil;
    }

    public void setColor(String text, int color) {
        addColor(text, color);
        textView.setText(style);
    }

    public static void setColor(TextView textView, String text, int color) {
        with(textView).setColor(text, color);
    }

    public IncludeUtil addSize(String text, float proportion) {
        style.setSpan(new RelativeSizeSpan(proportion),
                fullText.indexOf(text), fullText.indexOf(text)
                        + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return includeUtil;
    }

    public void setSize(String text, float proportion) {
        addSize(text, proportion);
        textView.setText(style);
    }

    public static void setSize(TextView textView, String text, float proportion) {
        with(textView).setSize(text, proportion);
    }


    public IncludeUtil addSize(String text, int size) {
        style.setSpan(new RelativeSizeSpan(size),
                fullText.indexOf(text), fullText.indexOf(text)
                        + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return includeUtil;
    }

    public void setSize(String text, int size) {
        addSize(text, size);
        textView.setText(style);
    }

    public static void setSize(TextView textView, String text, int size) {
        with(textView).setSize(text, size);
    }
}
