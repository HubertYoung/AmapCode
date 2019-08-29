package defpackage;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import com.autonavi.minimap.R;

/* renamed from: efy reason: default package */
/* compiled from: CountDownAnimatorNew */
public class efy implements OnTouchListener {
    /* access modifiers changed from: private */
    public static final String j = "efy";
    public AnimatorSet a;
    public AnimatorSet b;
    public AnimatorSet c;
    public TextView d;
    public TextView e;
    public TextView f;
    public View g;
    public View h;
    public b i;

    /* renamed from: efy$a */
    /* compiled from: CountDownAnimatorNew */
    public class a implements AnimatorListener {
        public final void onAnimationCancel(Animator animator) {
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        private a() {
        }

        public /* synthetic */ a(efy efy, byte b) {
            this();
        }

        public final void onAnimationEnd(Animator animator) {
            if (animator == efy.this.a) {
                efy.this.f.setText("2");
            }
            if (animator == efy.this.b) {
                efy.this.f.setText("1");
            }
            if (animator == efy.this.c) {
                efy.this.g.setVisibility(8);
                efy.this.h.setVisibility(8);
                efy.this.h.setOnTouchListener(null);
                if (efy.this.i != null) {
                    efy.this.i.e();
                }
            }
        }
    }

    /* renamed from: efy$b */
    /* compiled from: CountDownAnimatorNew */
    public interface b {
        void e();
    }

    /* renamed from: efy$c */
    /* compiled from: CountDownAnimatorNew */
    public class c implements AnimatorUpdateListener {
        private c() {
        }

        public /* synthetic */ c(efy efy, byte b) {
            this();
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            efy.this.e.setScaleX(floatValue);
            efy.this.e.setScaleY(floatValue);
        }
    }

    /* renamed from: efy$d */
    /* compiled from: CountDownAnimatorNew */
    public class d implements AnimatorUpdateListener {
        private d() {
        }

        public /* synthetic */ d(efy efy, byte b) {
            this();
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            efy.this.f.setScaleX(floatValue);
            efy.this.f.setScaleY(floatValue);
        }
    }

    /* renamed from: efy$e */
    /* compiled from: CountDownAnimatorNew */
    public class e implements AnimatorUpdateListener {
        private e() {
        }

        public /* synthetic */ e(efy efy, byte b) {
            this();
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            efy.this.d.setScaleX(floatValue);
            efy.this.d.setScaleY(floatValue);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    public efy(View view) {
        this.h = view.findViewById(R.id.anim_layout);
        this.h.setOnTouchListener(this);
        this.h.setVisibility(8);
        this.g = view.findViewById(R.id.anim_layout);
        this.d = (TextView) view.findViewById(R.id.wave_view);
        this.e = (TextView) view.findViewById(R.id.bg_view);
        this.f = (TextView) view.findViewById(R.id.text);
        efx.a(this.f);
    }

    public static void a(View view) {
        view.setVisibility(0);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f});
        ofFloat.setDuration(200);
        animatorSet.play(ofFloat);
        animatorSet.start();
        AnimatorSet animatorSet2 = new AnimatorSet();
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0f, 0.0f});
        ofFloat2.setDuration(300);
        animatorSet2.play(ofFloat2);
        animatorSet2.setStartDelay(2100);
        animatorSet2.start();
    }
}
