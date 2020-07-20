package util;

import android.graphics.drawable.Drawable;

import background.drawable.DrawableCreator;

public class MDrawable {
    public static Drawable press(int res,int resPressed){
        return new DrawableCreator.Builder().setPressedSolidColor(resPressed,res).build();
    }

    public static Drawable select(int res,int resSelected){
        return new DrawableCreator.Builder().setSelectedSolidColor(resSelected,res).build();
    }

    public static Drawable select(Drawable drawable,Drawable drawableSelected){
        return new DrawableCreator.Builder()
                .setUnSelectedDrawable(drawable)
                .setSelectedDrawable(drawableSelected).build();
    }

    public static Drawable press(Drawable drawable,Drawable drawablePressed){
        return new DrawableCreator.Builder()
                .setUnPressedDrawable(drawable)
                .setPressedDrawable(drawablePressed).build();
    }

    public static Drawable tag(int clo,int radius){
        return new DrawableCreator.Builder()
                .setStrokeWidth(1)
                .setCornersRadius(ScreenUtils.dip2px(radius))
                .setStrokeColor(clo).build();

    }

    public static Drawable solid(int clo,int radius){
        return new DrawableCreator.Builder()
                .setSolidColor(clo)
                .setCornersRadius(ScreenUtils.dip2px(radius))
                .build();

    }
}
