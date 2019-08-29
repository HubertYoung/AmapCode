package defpackage;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.amap.bundle.logs.AMapLog;

/* renamed from: bvn reason: default package */
/* compiled from: SearchResultHeaderAnim */
public final class bvn {
    public View a;
    private float b;
    private ObjectAnimator c;
    private volatile boolean d = true;

    public bvn(Context context) {
        if (context != null) {
            this.b = (float) agn.a(context, 90.0f);
        }
    }

    public final void a() {
        AMapLog.d("SYW", ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW);
        if (this.a != null) {
            this.a.setVisibility(0);
            if (!this.d) {
                this.d = true;
                this.a.requestLayout();
                if (this.a.getVisibility() == 8) {
                    this.a.setVisibility(0);
                }
                if (this.c != null && this.c.isRunning()) {
                    this.c.cancel();
                }
                this.c = new ObjectAnimator();
                this.c.setTarget(this.a);
                this.c.setProperty(View.TRANSLATION_Y);
                this.c.setDuration(180);
                this.c.addListener(new AnimatorListener() {
                    public final void onAnimationCancel(Animator animator) {
                    }

                    public final void onAnimationRepeat(Animator animator) {
                    }

                    public final void onAnimationStart(Animator animator) {
                    }

                    public final void onAnimationEnd(Animator animator) {
                        bvn.this.a.setTranslationY(0.0f);
                        bvn.this.a.requestLayout();
                    }
                });
                this.c.setFloatValues(new float[]{-this.b, 0.0f});
                this.c.start();
            }
        }
    }

    public final void b() {
        AMapLog.d("SYW", ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE);
        if (this.d) {
            this.d = false;
            if (this.a != null && this.a.getVisibility() != 8) {
                if (this.c != null && this.c.isRunning()) {
                    this.c.cancel();
                }
                this.c = new ObjectAnimator();
                this.c.setTarget(this.a);
                this.c.setProperty(View.TRANSLATION_Y);
                this.c.setDuration(180);
                this.c.setFloatValues(new float[]{0.0f, -this.b});
                this.c.start();
            }
        }
    }
}
