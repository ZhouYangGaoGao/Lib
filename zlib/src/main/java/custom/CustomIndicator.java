package custom;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.animation.ValueAnimator;

import com.wang.avi.Indicator;

import java.util.ArrayList;

public class CustomIndicator extends Indicator {


    public static final float SCALE = 1.0f;

    //scale x ,y
    private float[] scaleFloats = new float[]{
            SCALE, SCALE, SCALE,
            SCALE, SCALE, SCALE,
            SCALE, SCALE, SCALE,
            SCALE, SCALE, SCALE,
            SCALE, SCALE, SCALE,
            SCALE, SCALE, SCALE};


    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing = scaleFloats.length;
        float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 12;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        for (int i = 0; i < scaleFloats.length; i++) {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        for (int i = 0; i < scaleFloats.length; i++) {
            final int index = i;

            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(100 * (i + 1));

            addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();

                }
            });
            animators.add(scaleAnim);
        }
        return animators;
    }


}