package defpackage;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/* renamed from: ebv reason: default package */
/* compiled from: RouteAnimationTool */
public final class ebv {
    public static synchronized void a(final View view, final View view2, final AnimatorListener animatorListener) {
        synchronized (ebv.class) {
            if (view != null) {
                try {
                    view.post(new Runnable() {
                        public final void run() {
                            ebv.a(view, animatorListener);
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (view2 != null) {
                view2.post(new Runnable() {
                    public final void run() {
                        ebv.b(view2, animatorListener);
                    }
                });
            }
        }
    }

    public static synchronized void a(final View view, final View view2) {
        synchronized (ebv.class) {
            if (view != null) {
                try {
                    view.post(new Runnable() {
                        final /* synthetic */ AnimatorListener b = null;

                        public final void run() {
                            ebv.a(view, this.b);
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (view2 != null) {
                view2.post(new Runnable() {
                    final /* synthetic */ AnimatorListener b = null;

                    public final void run() {
                        ebv.b(view2, this.b);
                    }
                });
            }
        }
    }

    public static synchronized void b(final View view, final View view2, final AnimatorListener animatorListener) {
        synchronized (ebv.class) {
            if (view != null) {
                try {
                    view.post(new Runnable() {
                        public final void run() {
                            View view = view;
                            AnimatorListener animatorListener = animatorListener;
                            if (view != null) {
                                view.setTranslationY((float) view.getHeight());
                                int measuredHeight = view.getMeasuredHeight();
                                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{400.0f, 0.0f});
                                ofFloat.setDuration(400);
                                ofFloat.addUpdateListener(new AnimatorUpdateListener(view, measuredHeight, animatorListener) {
                                    final /* synthetic */ View a;
                                    final /* synthetic */ int b;
                                    final /* synthetic */ AnimatorListener c;

                                    {
                                        this.a = r1;
                                        this.b = r2;
                                        this.c = r3;
                                    }

                                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                        this.a.setAlpha(floatValue / 400.0f);
                                        this.a.setTranslationY((((float) this.b) / 400.0f) * (floatValue - 400.0f));
                                        this.a.requestLayout();
                                        if (Float.compare(floatValue, 0.0f) == 0) {
                                            this.c.onAnimationEnd(null);
                                        }
                                    }
                                });
                                view.setVisibility(0);
                                ofFloat.start();
                            }
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (view2 != null) {
                view2.post(new Runnable() {
                    public final void run() {
                        View view = view2;
                        if (view != null) {
                            view.setTranslationY((float) view.getHeight());
                            view.post(new Runnable(view) {
                                final /* synthetic */ View a;

                                {
                                    this.a = r1;
                                }

                                public final void run() {
                                    final int measuredHeight = this.a.getMeasuredHeight();
                                    ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{400.0f, 0.0f});
                                    ofFloat.setDuration(400);
                                    ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                            AnonymousClass12.this.a.setAlpha(floatValue / 400.0f);
                                            AnonymousClass12.this.a.setTranslationY((((float) measuredHeight) / 400.0f) * (400.0f - floatValue));
                                            AnonymousClass12.this.a.requestLayout();
                                        }
                                    });
                                    this.a.setVisibility(0);
                                    ofFloat.start();
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    public static void a(final View view, final AnimatorListener animatorListener) {
        view.setTranslationY((float) (0 - view.getHeight()));
        view.post(new Runnable() {
            public final void run() {
                ViewPropertyAnimator animate = view.animate();
                animate.alpha(1.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(0.0f);
                if (animatorListener != null) {
                    animate.setListener(animatorListener);
                }
                animate.start();
            }
        });
    }

    public static void a(final View view) {
        view.post(new Runnable() {
            public final void run() {
                ViewPropertyAnimator animate = view.animate();
                animate.alpha(1.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY((float) (0 - view.getHeight()));
                animate.start();
            }
        });
    }

    static /* synthetic */ void b(final View view, final AnimatorListener animatorListener) {
        view.setTranslationY((float) view.getHeight());
        view.post(new Runnable() {
            public final void run() {
                ViewPropertyAnimator animate = view.animate();
                animate.alpha(1.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(1.0f);
                if (animatorListener != null) {
                    animate.setListener(animatorListener);
                }
                animate.start();
            }
        });
    }
}
