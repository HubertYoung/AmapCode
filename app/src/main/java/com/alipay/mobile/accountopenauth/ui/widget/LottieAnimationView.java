package com.alipay.mobile.accountopenauth.ui.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.alipay.android.phone.inside.accountopenauth.R;

public class LottieAnimationView extends LinearLayout {
    /* access modifiers changed from: private */
    public LottieView a;
    /* access modifiers changed from: private */
    public LottieView b;
    /* access modifiers changed from: private */
    public LottieView c;
    private ValueAnimator d;

    public LottieAnimationView(Context context) {
        super(context);
        a();
    }

    public LottieAnimationView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    private void a() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.c, this, true);
        this.a = (LottieView) inflate.findViewById(R.id.m);
        this.b = (LottieView) inflate.findViewById(R.id.n);
        this.c = (LottieView) inflate.findViewById(R.id.o);
    }

    public void playAnimation() {
        this.d = ValueAnimator.ofInt(new int[]{0, 900});
        this.d.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                if (intValue < 300) {
                    LottieAnimationView.access$100(LottieAnimationView.this, LottieAnimationView.this.a);
                } else if (intValue < 600) {
                    LottieAnimationView.access$100(LottieAnimationView.this, LottieAnimationView.this.b);
                } else {
                    LottieAnimationView.access$100(LottieAnimationView.this, LottieAnimationView.this.c);
                }
            }
        });
        this.d.setDuration(900);
        this.d.setRepeatCount(-1);
        this.d.start();
    }

    public void cancelAnimation() {
        if (this.d != null && this.d.isRunning()) {
            this.d.cancel();
        }
    }

    static /* synthetic */ void access$100(LottieAnimationView lottieAnimationView, final View view) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f, 1.0f});
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                view.setScaleX(floatValue);
                view.setScaleY(floatValue);
                view.setAlpha(floatValue);
            }
        });
        ofFloat.setDuration(500);
        ofFloat.start();
    }
}
