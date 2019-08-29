package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

public class RotateTextView extends TextView implements AnimationListener {
    /* access modifiers changed from: private */
    public RotateType mRotateType;
    /* access modifiers changed from: private */
    public String mUpdateText;

    public enum RotateType {
        ROTATE_UP,
        ROTATE_DOWN
    }

    public static class a extends Animation {
        private final float a;
        private final float b;
        private final float c;
        private final float d;
        private final float e = 310.0f;
        private final boolean f;
        private Camera g;

        public a(float f2, float f3, float f4, float f5, boolean z) {
            this.a = f2;
            this.b = f3;
            this.c = f4;
            this.d = f5;
            this.f = z;
        }

        public final void initialize(int i, int i2, int i3, int i4) {
            super.initialize(i, i2, i3, i4);
            this.g = new Camera();
        }

        /* access modifiers changed from: protected */
        public final void applyTransformation(float f2, Transformation transformation) {
            float f3 = this.a;
            float f4 = f3 + ((this.b - f3) * f2);
            float f5 = this.c;
            float f6 = this.d;
            Camera camera = this.g;
            Matrix matrix = transformation.getMatrix();
            camera.save();
            if (this.f) {
                camera.translate(0.0f, 0.0f, this.e * f2);
            } else {
                camera.translate(0.0f, 0.0f, this.e * (1.0f - f2));
            }
            camera.rotateX(f4);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-f5, -f6);
            matrix.postTranslate(f5, f6);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }

    public RotateTextView(Context context) {
        this(context, null);
    }

    public RotateTextView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RotateTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onAnimationEnd(Animation animation) {
        post(new Runnable() {
            public final void run() {
                a aVar = new a(RotateType.ROTATE_UP == RotateTextView.this.mRotateType ? -90.0f : 90.0f, 0.0f, ((float) RotateTextView.this.getWidth()) / 2.0f, ((float) RotateTextView.this.getHeight()) / 2.0f, false);
                aVar.setDuration(250);
                aVar.setFillAfter(true);
                aVar.setInterpolator(new DecelerateInterpolator());
                aVar.setAnimationListener(new AnimationListener() {
                    public final void onAnimationRepeat(Animation animation) {
                    }

                    public final void onAnimationStart(Animation animation) {
                    }

                    public final void onAnimationEnd(Animation animation) {
                        if (!TextUtils.isEmpty(RotateTextView.this.mUpdateText)) {
                            RotateTextView.this.setText(RotateTextView.this.mUpdateText);
                        }
                    }
                });
                RotateTextView.this.startAnimation(aVar);
            }
        });
    }

    public void startAnimation(RotateType rotateType, String str) {
        this.mRotateType = rotateType;
        this.mUpdateText = str;
        a aVar = new a(0.0f, RotateType.ROTATE_UP == rotateType ? 90.0f : -90.0f, ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, true);
        aVar.setDuration(250);
        aVar.setFillAfter(true);
        aVar.setInterpolator(new AccelerateInterpolator());
        aVar.setAnimationListener(this);
        startAnimation(aVar);
    }
}
