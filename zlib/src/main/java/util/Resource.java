package util;

import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.InputStream;

import base.BApp;

public class Resource {

    public static int color(int colorId) {
        return resources().getColor(colorId);
    }

    public static String string(int stringId) {
        return resources().getString(stringId);
    }

    public static Drawable drawable(int drawableId) {
        return resources().getDrawable(drawableId);
    }

    public static ColorStateList colorStateList(int colorStateListId) {
        return resources().getColorStateList(colorStateListId);
    }

    public static float dimension(int dimensionId) {
        return resources().getDimension(dimensionId);
    }

    public static String[] stringArray(int stringArrayId) {
        return resources().getStringArray(stringArrayId);
    }

    public static boolean bool(int boolId) {
        return resources().getBoolean(boolId);
    }

    public static AssetManager assets() {
        return resources().getAssets();
    }

    public static int widthPixels() {
        return displayMetrics().widthPixels;
    }

    public static DisplayMetrics displayMetrics() {
        return resources().getDisplayMetrics();
    }

    public static int heightPixels() {
        return displayMetrics().heightPixels;
    }

    public static float xdpi() {
        return displayMetrics().xdpi;
    }

    public static float ydpi() {
        return displayMetrics().ydpi;
    }

    public static String[] getLocales() {
        return assets().getLocales();
    }

    public static InputStream openAssets(String fileName) throws IOException {
        return assets().open(fileName);
    }

    public static Resources resources() {
        return BApp.app().getResources();
    }
}
