
package util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import base.BApp;

public class ScreenUtils {

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public static int getScreenWidth() {

        WindowManager wm = (WindowManager) BApp.app()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;

    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getScreenHeight() {

        WindowManager wm = (WindowManager) BApp.app()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;

    }

    /**
     * 适配用的 按屏幕宽度比例
     *
     * @param view 要适配的view
     * @param h    要显示的高度和屏幕宽度的比例
     */
    public static float setHight(View view, double h) {
        ViewGroup.LayoutParams gLayoutParams = view.getLayoutParams();
        gLayoutParams.height = (int) (ScreenUtils.getScreenWidth() * h);
        view.setLayoutParams(gLayoutParams);
        return gLayoutParams.height;
    }

    public static float setHight(View view, int h) {
        ViewGroup.LayoutParams gLayoutParams = view.getLayoutParams();
        if (h < 0) {
            gLayoutParams.height = h;
        } else gLayoutParams.height = dip2px(h);
        view.setLayoutParams(gLayoutParams);
        return gLayoutParams.height;
    }

    public static float setHightPx(View view, int h) {
        ViewGroup.LayoutParams gLayoutParams = view.getLayoutParams();
        if (h < 0) {
            gLayoutParams.height = h;
        } else gLayoutParams.height = h;
        view.setLayoutParams(gLayoutParams);
        return gLayoutParams.height;
    }

    public static float setWidth(View view, int w) {
        ViewGroup.LayoutParams gLayoutParams = view.getLayoutParams();
        if (w < 0) {
            gLayoutParams.width = w;
        } else gLayoutParams.width = dip2px(w);
        view.setLayoutParams(gLayoutParams);
        return gLayoutParams.width;
    }

    /**
     * 适配用的 按屏幕宽度比例
     *
     * @param view 要适配的view
     * @param h    要显示的高度和屏幕宽度的比例
     */
    public static float setWidth(View view, double h) {
        ViewGroup.LayoutParams gLayoutParams = view.getLayoutParams();//底部导航栏做了适配
        gLayoutParams.width = (int) (ScreenUtils.getScreenWidth() * h);
        view.setLayoutParams(gLayoutParams);
        return gLayoutParams.width;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {

            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);

        } catch (Exception e) {

            e.printStackTrace();

        }
        return statusHeight;

    }

    public static Bitmap snapShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, view.getWidth(), view.getHeight());
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @return
     */
    public static Bitmap snapShotWithStatusBar() {

        View view = BApp.app().act().getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {

        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static boolean isLandscape() {

        Configuration mConfiguration = BApp.app().getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
//横屏
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
//竖屏
            return false;
        }
        return false;
    }

    public static int px2dip(float pxValue) {
        final float scale = BApp.app().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dip2px(float dipValue) {
        final float scale = BApp.app().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int px2sp(float pxValue) {
        final float fontScale =BApp.app().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(float spValue) {
        final float fontScale = BApp.app().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void showKeyBoard(View view) {
        InputMethodManager manager = ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyBoard(View view) {
        InputMethodManager manager = ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null)
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
