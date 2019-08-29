package android.support.v4.animation;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

class DonutAnimatorCompatProvider implements AnimatorProvider {

    static class DonutFloatValueAnimator implements ValueAnimatorCompat {
        List<AnimatorListenerCompat> a = new ArrayList();
        List<AnimatorUpdateListenerCompat> b = new ArrayList();
        View c;
        /* access modifiers changed from: private */
        public long d;
        /* access modifiers changed from: private */
        public long e = 200;
        /* access modifiers changed from: private */
        public float f = 0.0f;
        private boolean g = false;
        private boolean h = false;
        /* access modifiers changed from: private */
        public Runnable i = new Runnable() {
            public void run() {
                float a2 = (((float) (DonutFloatValueAnimator.this.c.getDrawingTime() - DonutFloatValueAnimator.this.d)) * 1.0f) / ((float) DonutFloatValueAnimator.this.e);
                if (a2 > 1.0f || DonutFloatValueAnimator.this.c.getParent() == null) {
                    a2 = 1.0f;
                }
                DonutFloatValueAnimator.this.f = a2;
                DonutFloatValueAnimator.d(DonutFloatValueAnimator.this);
                if (DonutFloatValueAnimator.this.f >= 1.0f) {
                    DonutFloatValueAnimator.this.a();
                } else {
                    DonutFloatValueAnimator.this.c.postDelayed(DonutFloatValueAnimator.this.i, 16);
                }
            }
        };

        public void setTarget(View view) {
            this.c = view;
        }

        public void addListener(AnimatorListenerCompat animatorListenerCompat) {
            this.a.add(animatorListenerCompat);
        }

        public void setDuration(long j) {
            if (!this.g) {
                this.e = j;
            }
        }

        public void start() {
            if (!this.g) {
                this.g = true;
                for (int size = this.a.size() - 1; size >= 0; size--) {
                    this.a.get(size).onAnimationStart(this);
                }
                this.f = 0.0f;
                this.d = this.c.getDrawingTime();
                this.c.postDelayed(this.i, 16);
            }
        }

        /* access modifiers changed from: private */
        public void a() {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                this.a.get(size).onAnimationEnd(this);
            }
        }

        public void cancel() {
            if (!this.h) {
                this.h = true;
                if (this.g) {
                    for (int size = this.a.size() - 1; size >= 0; size--) {
                        this.a.get(size).onAnimationCancel(this);
                    }
                }
                a();
            }
        }

        public void addUpdateListener(AnimatorUpdateListenerCompat animatorUpdateListenerCompat) {
            this.b.add(animatorUpdateListenerCompat);
        }

        public float getAnimatedFraction() {
            return this.f;
        }

        static /* synthetic */ void d(DonutFloatValueAnimator donutFloatValueAnimator) {
            for (int size = donutFloatValueAnimator.b.size() - 1; size >= 0; size--) {
                donutFloatValueAnimator.b.get(size).onAnimationUpdate(donutFloatValueAnimator);
            }
        }
    }

    public final void a(View view) {
    }

    DonutAnimatorCompatProvider() {
    }

    public final ValueAnimatorCompat a() {
        return new DonutFloatValueAnimator();
    }
}
