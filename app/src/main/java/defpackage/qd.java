package defpackage;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;

/* renamed from: qd reason: default package */
/* compiled from: RouteCarResultAnimation */
public final class qd {
    /* access modifiers changed from: private */
    public static int a = 400;

    public static void a(final View view) {
        if (view.getMeasuredHeight() <= 0 || view.getMeasuredWidth() <= 0) {
            try {
                view.measure(0, 0);
            } catch (Exception e) {
                AMapLog.e("RouteCarResultAnimation", e.toString());
            }
        }
        final int measuredHeight = view.getMeasuredHeight();
        final int measuredWidth = view.getMeasuredWidth();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA.getName(), new float[]{1.0f});
        ofFloat.setDuration(400);
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            final /* synthetic */ boolean a = false;
            final /* synthetic */ float b = -1.0f;
            final /* synthetic */ float c = 0.0f;

            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                if (this.a) {
                    ((MarginLayoutParams) view.getLayoutParams()).setMargins((int) (((double) ((this.b - this.c) * ((float) measuredWidth))) * (1.0d - ((double) animatedFraction))), 0, 0, 0);
                    view.requestLayout();
                    return;
                }
                int i = (int) (((double) ((this.b - this.c) * ((float) measuredHeight))) * (1.0d - ((double) animatedFraction)));
                "startTransBoundAnim    onAnimationUpdate h:".concat(String.valueOf(i));
                qd.a();
                ((MarginLayoutParams) view.getLayoutParams()).setMargins(0, i, 0, 0);
                view.requestLayout();
            }
        });
        ofFloat.addListener(new AnimatorListener() {
            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
                qd.a();
            }

            public final void onAnimationEnd(Animator animator) {
                qd.a();
            }

            public final void onAnimationCancel(Animator animator) {
                new StringBuilder("startTransBoundAnim    onAnimationCancel ").append(Log.getStackTraceString(new Throwable()));
                qd.a();
            }
        });
        ofFloat.start();
    }

    public static void a(Context context, View view) {
        Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.drive_result_top_in);
        loadAnimation.setAnimationListener(null);
        view.startAnimation(loadAnimation);
    }

    public static void b(Context context, View view) {
        Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.drive_result_top_out);
        loadAnimation.setAnimationListener(null);
        view.startAnimation(loadAnimation);
    }

    public static void a(Context context, View view, AnimationListener animationListener) {
        Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.drive_result_bottom_in);
        loadAnimation.setAnimationListener(animationListener);
        view.startAnimation(loadAnimation);
    }

    public static void b(Context context, View view, AnimationListener animationListener) {
        Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.drive_result_bottom_out);
        loadAnimation.setAnimationListener(animationListener);
        view.startAnimation(loadAnimation);
    }

    public static void c(Context context, final View view) {
        AnonymousClass3 r0 = new AnimationListener() {
            final /* synthetic */ AnimationListener a = null;

            public final void onAnimationStart(Animation animation) {
                if (this.a != null) {
                    this.a.onAnimationStart(animation);
                }
            }

            public final void onAnimationEnd(Animation animation) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public final void run() {
                        if (view != null && view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }
                    }
                });
                if (this.a != null) {
                    this.a.onAnimationEnd(animation);
                }
            }

            public final void onAnimationRepeat(Animation animation) {
                if (this.a != null) {
                    this.a.onAnimationRepeat(animation);
                }
            }
        };
        Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.drive_result_alpha_out);
        loadAnimation.setAnimationListener(r0);
        view.startAnimation(loadAnimation);
    }

    public static void b(final View view) {
        view.post(new Runnable() {
            public final void run() {
                view.animate().alpha(0.0f).setDuration((long) qd.a).translationY((float) (0 - view.getHeight())).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(null);
            }
        });
    }

    public static void a(final View view, final AnimatorListener animatorListener) {
        view.post(new Runnable() {
            public final void run() {
                view.animate().alpha(0.0f).setDuration((long) qd.a).translationY((float) (view.getHeight() + 0)).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(animatorListener);
            }
        });
    }

    static /* synthetic */ void a() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("startTransBoundAnim    onAnimationCancel ");
            sb.append(Log.getStackTraceString(new Throwable()));
            AMapLog.debug("route.carnavi", "RouteCarResultAnimation", sb.toString());
        }
    }
}
