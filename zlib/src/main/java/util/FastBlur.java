package util;

/**
 * Created by Lee on 2018/4/26.
 */

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import base.BApp;

public class FastBlur {

    //图片缩放比例
    private static final float BITMAP_SCALE = 0.1f;

    //图片模糊1f-25f
    private static final float BITMAP_RADIUS = 2f;

    /**
     * 模糊图片的具体方法
     *
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    public static Bitmap blurBitmap( Bitmap image) {
        return blurBitmap(image,BITMAP_RADIUS);
    }
    public static Bitmap blurBitmap( Bitmap image, float blurRadius) {
        return blurBitmap( image, blurRadius, BITMAP_SCALE);
    }

    public static Bitmap blurBitmap( Bitmap image, float blurRadius, float scale) {
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * scale);
        int height = Math.round(image.getHeight() * scale);

        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(BApp.app().act());
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
}


