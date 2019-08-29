package defpackage;

import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.text.TextUtils;
import android.view.View;

/* renamed from: dwi reason: default package */
/* compiled from: RouteAnimatorUtil */
public final class dwi {
    public static void a(final View view, long j, AnimatorListener animatorListener, String str, float f, float f2) {
        if (view != null && !TextUtils.isEmpty(str) && Float.compare(f, f2) != 0) {
            if (j <= 0) {
                j = 500;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, str, new float[]{f, f2});
            ofFloat.setDuration(j);
            if (animatorListener != null) {
                ofFloat.addListener(animatorListener);
            }
            ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view.requestLayout();
                }
            });
            ofFloat.start();
        }
    }
}
