package util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AnimatorUtil {
    private static AnimatorUtil util;
    private AnimatorSet set;
    private View view;
    private List<Animator> animators;

    private AnimatorUtil(AnimatorSet set) {
        this.set = set;
    }

    public static AnimatorUtil with(View view, int duration) {
        if (util == null) util = new AnimatorUtil(new AnimatorSet());
        util.set.setDuration(duration);
        util.animators = new ArrayList<>();
        util.view = view;
        return util;
    }

    public static AnimatorUtil with(View view) {
        return with(view, 300);
    }

    public void playTogether() {
        set.playTogether(animators);
        set.start();
    }

    public void playTogether(Animator.AnimatorListener listener) {
        set.addListener(listener);
        set.playTogether(animators);
        set.start();
    }
    public void playSequentially() {
        set.playSequentially(animators);
        set.start();
    }
    public void playSequentially(Animator.AnimatorListener listener) {
        set.addListener(listener);
        set.playSequentially(animators);
        set.start();
    }

    private AnimatorUtil add(Animator animator) {
        animators.add(animator);
        return this;
    }


    public AnimatorUtil listener(Animator.AnimatorListener listener) {
        set.addListener(listener);
        return this;
    }

    public AnimatorUtil alpha(View view, float... values) {
        return add(ObjectAnimator.ofFloat(view, "alpha", values));
    }

    public AnimatorUtil scaleX(View view, float... values) {
        return add(ObjectAnimator.ofFloat(view, "scaleX", values));
    }

    public AnimatorUtil scale(View view, float... values) {
        add(ObjectAnimator.ofFloat(view, "scaleY", values));
        return add(ObjectAnimator.ofFloat(view, "scaleX", values));
    }

    public AnimatorUtil scaleY(View view, float... values) {
        add(ObjectAnimator.ofFloat(view, "scaleX", values));
        return add(ObjectAnimator.ofFloat(view, "scaleY", values));
    }

    public AnimatorUtil translation(View view, float... values) {
        return add(ObjectAnimator.ofFloat(view, "translationX", values));
    }

    public AnimatorUtil translationX(View view, float... values) {
        return add(ObjectAnimator.ofFloat(view, "translationX", values));
    }

    public AnimatorUtil translationY(View view, float... values) {
        return add(ObjectAnimator.ofFloat(view, "translationY", values));
    }

    public AnimatorUtil rotation(View view, float... values) {
        return add(ObjectAnimator.ofFloat(view, "rotation", values));
    }

    public AnimatorUtil alpha(float... values) {
        return alpha(view, values);
    }

    public AnimatorUtil scaleX(float... values) {
        return scaleX(view, values);
    }

    public AnimatorUtil scale(float... values) {
        return scale(view, values);
    }

    public AnimatorUtil scaleY(float... values) {
        return scaleY(view, values);
    }

    public AnimatorUtil translation(float... values) {
        return translationX(view, values);
    }

    public AnimatorUtil translationX(float... values) {
        return translationX(view, values);
    }

    public AnimatorUtil translationY(float... values) {
        return translationY(view, values);
    }

    public AnimatorUtil rotation(float... values) {
        return rotation(view, values);
    }


    public static class Listener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
