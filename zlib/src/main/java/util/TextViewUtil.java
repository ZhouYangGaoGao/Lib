package util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

public class TextViewUtil {

    public static void setIncludTextColor(View textView, String keyWord, int colorOxff) {
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

    public static void setIncludTextSize(View textView, String keyWord, float proportion) {
        TextView textView1 = (TextView) textView;
        String text = textView1.getText().toString().trim();
        if (text.contains(keyWord)) {
            SpannableStringBuilder style = new SpannableStringBuilder(
                    text);
            style.setSpan(new RelativeSizeSpan(proportion),
                    text.indexOf(keyWord), text.indexOf(keyWord)
                            + keyWord.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textView1.setText(style);
        }
    }

    public static void setIncludTextStyle(View textView, String keyWord, int typeface) {
        TextView textView1 = (TextView) textView;
        String text = textView1.getText().toString().trim();
        if (text.contains(keyWord)) {
            SpannableStringBuilder style = new SpannableStringBuilder(
                    text);
            style.setSpan(new StyleSpan(typeface),
                    text.indexOf(keyWord), text.indexOf(keyWord)
                            + keyWord.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView1.setText(style);
        }
    }


    public static void setIncludTextStyleAndSize(View textView, String keyWord, int typeface, float proportion) {
        TextView textView1 = (TextView) textView;
        String text = textView1.getText().toString().trim();
        if (text.contains(keyWord)) {
            SpannableStringBuilder style = new SpannableStringBuilder(
                    text);
            int end = text.indexOf(keyWord)
                    + keyWord.length();
            style.setSpan(new StyleSpan(typeface),
                    text.indexOf(keyWord), end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            style.setSpan(new RelativeSizeSpan(proportion),
                    text.indexOf(keyWord), end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textView1.setText(style);
        }
    }

    public static void setIncludTextStyleAndSizeAndColor(View textView, String keyWord, int typeface, float proportion, int colorOxff) {
        TextView textView1 = (TextView) textView;
        String text = textView1.getText().toString().trim();
        if (text.contains(keyWord)) {
            SpannableStringBuilder style = new SpannableStringBuilder(
                    text);
            int end = text.indexOf(keyWord)
                    + keyWord.length();
            style.setSpan(new StyleSpan(typeface),
                    text.indexOf(keyWord), end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new RelativeSizeSpan(proportion),
                    text.indexOf(keyWord), end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(colorOxff),
                    text.indexOf(keyWord), end,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            textView1.setText(style);
        }
    }

    //https://blog.csdn.net/a2241076850/article/details/70225408
}
