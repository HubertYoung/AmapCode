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

/* renamed from: eeu reason: default package */
/* compiled from: RideCountDownAnimatorNew */
public class eeu implements OnTouchListener {
    /* access modifiers changed from: private */
    public static final String j = "eeu";
    public AnimatorSet a;
    public AnimatorSet b;
    public AnimatorSet c;
    public TextView d;
    public TextView e;
    public TextView f;
    public View g;
    public View h;
    public b i;

    /* renamed from: eeu$a */
    /* compiled from: RideCountDownAnimatorNew */
    public class a implements AnimatorListener {
        public final void onAnimationCancel(Animator animator) {
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        private a() {
        }

        public /* synthetic */ a(eeu eeu, byte b) {
            this();
        }

        public final void onAnimationEnd(Animator animator) {
            if (animator == eeu.this.a) {
                eeu.this.f.setText("2");
            }
            if (animator == eeu.this.b) {
                eeu.this.f.setText("1");
            }
            if (animator == eeu.this.c) {
                eeu.this.g.setVisibility(8);
                eeu.this.h.setVisibility(8);
                eeu.this.h.setOnTouchListener(null);
                if (eeu.this.i != null) {
                    eeu.this.i.f();
                }
            }
        }
    }

    /* renamed from: eeu$b */
    /* compiled from: RideCountDownAnimatorNew */
    public interface b {
        void f();
    }

    /* renamed from: eeu$c */
    /* compiled from: RideCountDownAnimatorNew */
    public class c implements AnimatorUpdateListener {
        private c() {
        }

        public /* synthetic */ c(eeu eeu, byte b) {
            this();
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            eeu.this.e.setScaleX(floatValue);
            eeu.this.e.setScaleY(floatValue);
        }
    }

    /* renamed from: eeu$d */
    /* compiled from: RideCountDownAnimatorNew */
    public class d implements AnimatorUpdateListener {
        private d() {
        }

        public /* synthetic */ d(eeu eeu, byte b) {
            this();
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            eeu.this.f.setScaleX(floatValue);
            eeu.this.f.setScaleY(floatValue);
        }
    }

    /* renamed from: eeu$e */
    /* compiled from: RideCountDownAnimatorNew */
    public class e implements AnimatorUpdateListener {
        private e() {
        }

        public /* synthetic */ e(eeu eeu, byte b) {
            this();
        }

        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            eeu.this.d.setScaleX(floatValue);
            eeu.this.d.setScaleY(floatValue);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    public eeu(View view) {
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
