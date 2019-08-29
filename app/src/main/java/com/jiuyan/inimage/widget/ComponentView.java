package com.jiuyan.inimage.widget;

import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.callback.ComponentLister;
import com.jiuyan.inimage.util.a;

public abstract class ComponentView extends FrameLayout {
    protected boolean a = true;
    protected ComponentLister b;

    public abstract void a();

    public abstract void setBitmapPreviously(Bitmap bitmap);

    public ComponentView(Context context) {
        super(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public ComponentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ComponentView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        a();
    }

    public boolean b() {
        return this.a;
    }

    public void setShowWithAnimationEnabled(boolean z) {
        this.a = z;
    }

    public void setComponentListener(ComponentLister componentLister) {
        this.b = componentLister;
    }

    public void a(View view, boolean z, AnimatorListener animatorListener) {
        if (!z) {
            a.a(view, true, animatorListener);
        } else {
            a.a(view, false, animatorListener);
        }
    }
}
