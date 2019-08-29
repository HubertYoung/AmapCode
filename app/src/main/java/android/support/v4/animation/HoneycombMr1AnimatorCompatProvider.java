package android.support.v4.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

class HoneycombMr1AnimatorCompatProvider implements AnimatorProvider {
    private TimeInterpolator a;

    static class AnimatorListenerCompatWrapper implements AnimatorListener {
        final AnimatorListenerCompat a;
        final ValueAnimatorCompat b;

        public AnimatorListenerCompatWrapper(AnimatorListenerCompat animatorListenerCompat, ValueAnimatorCompat valueAnimatorCompat) {
            this.a = animatorListenerCompat;
            this.b = valueAnimatorCompat;
        }

        public void onAnimationStart(Animator animator) {
            this.a.onAnimationStart(this.b);
        }

        public void onAnimationEnd(Animator animator) {
            this.a.onAnimationEnd(this.b);
        }

        public void onAnimationCancel(Animator animator) {
            this.a.onAnimationCancel(this.b);
        }

        public void onAnimationRepeat(Animator animator) {
            this.a.onAnimationRepeat(this.b);
        }
    }

    static class HoneycombValueAnimatorCompat implements ValueAnimatorCompat {
        final Animator a;

        public HoneycombValueAnimatorCompat(Animator animator) {
            this.a = animator;
        }

        public void setTarget(View view) {
            this.a.setTarget(view);
        }

        public void addListener(AnimatorListenerCompat animatorListenerCompat) {
            this.a.addListener(new AnimatorListenerCompatWrapper(animatorListenerCompat, this));
        }

        public void setDuration(long j) {
            this.a.setDuration(j);
        }

        public void start() {
            this.a.start();
        }

        public void cancel() {
            this.a.cancel();
        }

        public void addUpdateListener(final AnimatorUpdateListenerCompat animatorUpdateListenerCompat) {
            if (this.a instanceof ValueAnimator) {
                ((ValueAnimator) this.a).addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        animatorUpdateListenerCompat.onAnimationUpdate(HoneycombValueAnimatorCompat.this);
                    }
                });
            }
        }

        public float getAnimatedFraction() {
            return ((ValueAnimator) this.a).getAnimatedFraction();
        }
    }

    HoneycombMr1AnimatorCompatProvider() {
    }

    public final ValueAnimatorCompat a() {
        return new HoneycombValueAnimatorCompat(ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}));
    }

    public final void a(View view) {
        if (this.a == null) {
            this.a = new ValueAnimator().getInterpolator();
        }
        view.animate().setInterpolator(this.a);
    }
}
