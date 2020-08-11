package util;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class IncludeUtil {
    private static IncludeUtil util;
    private TextView textView;
    private SpannableString span;
    private String fullText;

    private IncludeUtil() {
    }

    public static IncludeUtil with(TextView textView) {
        if (util == null) util = new IncludeUtil();
        util.textView = textView;
        util.fullText = textView.getText().toString().trim();
        util.span = new SpannableString(util.fullText);
        return util;
    }

    public IncludeUtil addClickable(String text,ClickableSpan listener) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return setSpan(listener, fullText, text);
    }

    public void setClickable(String text,ClickableSpan listener) {
        addClickable(text,listener);
        textView.setText(span);
    }

    public static void setClickable(TextView textView, String text,ClickableSpan listener) {
        with(textView).setClickable(text,listener);
    }

    public IncludeUtil addStrikethrough(String text) {
        return setSpan(new StrikethroughSpan(), fullText, text);
    }

    public void setStrikethrough(String text) {
        addStrikethrough(text);
        textView.setText(span);
    }

    public static void setStrikethrough(TextView textView, String text) {
        with(textView).setStrikethrough(text);
    }

    public IncludeUtil addUnderline(String text) {
        return setSpan(new UnderlineSpan(), fullText, text);
    }

    public void setUnderline(String text) {
        addUnderline(text);
        textView.setText(span);
    }

    public static void setUnderline(TextView textView, String text) {
        with(textView).setUnderline(text);
    }

    public IncludeUtil addBgColor(String text, int color) {
        return setSpan(new BackgroundColorSpan(color), fullText, text);
    }

    public void setBgColor(String text, int color) {
        addBgColor(text, color);
        textView.setText(span);
    }

    public static void setImage(TextView textView, String text, Drawable drawable) {
        with(textView).setImage(text, drawable);
    }

    public IncludeUtil addImage(String text, Drawable drawable) {
        return setSpan(new ImageSpan(drawable), fullText, text);
    }

    public void setImage(String text, Drawable drawable) {
        addImage(text, drawable);
        textView.setText(span);
    }

    public static void setBgColor(TextView textView, String text, int color) {
        with(textView).setBgColor(text, color);
    }

    public IncludeUtil addColor(String text, int color) {
        return setSpan(new ForegroundColorSpan(color), fullText, text);
    }

    public void setColor(String text, int color) {
        addColor(text, color);
        textView.setText(span);
    }

    public static void setColor(TextView textView, String text, int color) {
        with(textView).setColor(text, color);
    }

    public IncludeUtil addSuperscript(String text, Parcel src ) {
        return setSpan(new SuperscriptSpan(src), fullText, text);
    }

    public void setSuperscript(String text, Parcel src) {
        addSuperscript(text, src);
        textView.setText(span);
    }

    public static void setSuperscript(TextView textView, String text, Parcel src) {
        with(textView).setSuperscript(text, src);
    }

    public IncludeUtil addSubscript(String text, Parcel src ) {
        return setSpan(new SubscriptSpan(src), fullText, text);
    }

    public void setSubscript(String text, Parcel src) {
        addSubscript(text, src);
        textView.setText(span);
    }

    public static void setSubscript(TextView textView, String text, Parcel src) {
        with(textView).setSubscript(text, src);
    }

    public IncludeUtil addSuperscript(String text ) {
        return setSpan(new SuperscriptSpan(), fullText, text);
    }

    public void setSuperscript(String text ) {
        addSuperscript(text);
        textView.setText(span);
    }

    public static void setSuperscript(TextView textView, String text ) {
        with(textView).setSuperscript(text);
    }

    public IncludeUtil addSubscript(String text) {
        return setSpan(new SubscriptSpan(), fullText, text);
    }

    public void setSubscript(String text ) {
        addSubscript(text);
        textView.setText(span);
    }

    public static void setSubscript(TextView textView, String text ) {
        with(textView).setSubscript(text );
    }


    public IncludeUtil addStyle(String text, Parcel src ) {
        return setSpan(new StyleSpan(src), fullText, text);
    }

    public void setStyle(String text, Parcel src) {
        addStyle(text, src);
        textView.setText(span);
    }

    public static void setStyle(TextView textView, String text, Parcel src) {
        with(textView).setStyle(text, src);
    }

    public IncludeUtil addStyle(String text, int style) {
        return setSpan(new StyleSpan(style), fullText, text);
    }

    public void setStyle(String text, int style) {
        addStyle(text, style);
        textView.setText(span);
    }

    public static void setStyle(TextView textView, String text, int style) {
        with(textView).setStyle(text, style);
    }

    public IncludeUtil addSize(String text, float proportion) {
        return setSpan(new RelativeSizeSpan(proportion), fullText, text);
    }

    public void setSize(String text, float proportion) {
        addSize(text, proportion);
        textView.setText(span);
    }

    public static void setSize(TextView textView, String text, float proportion) {
        with(textView).setSize(text, proportion);
    }

    public IncludeUtil addSize(String text, int size) {
        return setSpan(new RelativeSizeSpan(size), fullText, text);
    }

    public void setSize(String text, int size) {
        addSize(text, size);
        textView.setText(span);
    }

    public static void setSize(TextView textView, String text, int size) {
        with(textView).setSize(text, size);
    }

    private IncludeUtil setSpan(Object what, String fullText, String text) {
        span.setSpan(what,
                fullText.indexOf(text), fullText.indexOf(text)
                        + text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return util;
    }
}
