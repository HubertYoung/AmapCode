package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.autonavi.minimap.R;

/* renamed from: ach reason: default package */
/* compiled from: AnimatorUtil */
public final class ach {
    public static long a = 200;
    public static long b = 375;
    public static TimeInterpolator c = new AccelerateDecelerateInterpolator();

    public static ValueAnimator a() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(null, "alpha", new float[]{0.0f, 1.0f});
        ofFloat.setDuration(a);
        return ofFloat;
    }

    public static ValueAnimator b() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(null, "alpha", new float[]{1.0f, 0.0f});
        ofFloat.setDuration(a);
        return ofFloat;
    }

    public static void a(View view, final View view2, long j) {
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(a).setStartDelay(j).start();
        view2.setAlpha(1.0f);
        view2.setScaleX(0.0f);
        view2.setScaleY(0.0f);
        view2.setVisibility(0);
        view2.animate().alpha(0.0f).scaleX(2.5f).scaleY(2.5f).setDuration(600).setStartDelay(j).setListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                view2.setVisibility(8);
            }
        }).start();
    }

    public static void a(View view) {
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setTranslationY((float) (-view.getContext().getResources().getDimensionPixelOffset(R.dimen.route_edit_flag_translation)));
        view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).translationY(0.0f).setDuration(a).setStartDelay(200).start();
    }

    public static void b(View view) {
        view.setAlpha(0.0f);
        view.setTranslationY((float) (-view.getContext().getResources().getDimensionPixelOffset(R.dimen.route_edit_end_translation)));
        view.animate().alpha(1.0f).translationY(0.0f).setDuration(a).setStartDelay(400).start();
    }

    public static ValueAnimator c(View view) {
        float alpha = view.getAlpha();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", new float[]{alpha, 1.0f});
        ofFloat.setTarget(view);
        ofFloat.setDuration((long) (((float) a) * (1.0f - alpha)));
        return ofFloat;
    }

    public static ValueAnimator d(View view) {
        float alpha = view.getAlpha();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", new float[]{alpha, 0.0f});
        ofFloat.setTarget(view);
        ofFloat.setDuration((long) (((float) a) * alpha));
        return ofFloat;
    }

    public static ValueAnimator a(int... iArr) {
        ValueAnimator ofInt = ValueAnimator.ofInt(iArr);
        ofInt.setDuration(a);
        return ofInt;
    }

    public static ValueAnimator a(int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(null, "translationY", new float[]{(float) i, 0.0f});
        ofFloat.setDuration(a);
        return ofFloat;
    }

    public static ValueAnimator b(int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(null, "translationY", new float[]{(float) (-i), 0.0f});
        ofFloat.setDuration(a);
        return ofFloat;
    }
}
