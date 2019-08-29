package defpackage;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/* renamed from: ert reason: default package */
/* compiled from: AnimatorUtil */
public final class ert {
    public static long a = 200;
    public static long b = 200;

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

    public static ValueAnimator a(View view) {
        float alpha = view.getAlpha();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", new float[]{alpha, 1.0f});
        ofFloat.setTarget(view);
        ofFloat.setDuration((long) (((float) b) * (1.0f - alpha)));
        return ofFloat;
    }

    public static ValueAnimator b(View view) {
        float alpha = view.getAlpha();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", new float[]{alpha, 0.0f});
        ofFloat.setTarget(view);
        ofFloat.setDuration((long) (((float) b) * alpha));
        return ofFloat;
    }

    public static ValueAnimator a(int... iArr) {
        return ValueAnimator.ofInt(iArr);
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

    public static void c(View view) {
        ValueAnimator a2 = a();
        a2.setTarget(view);
        a2.setDuration(b);
        a2.start();
    }
}
