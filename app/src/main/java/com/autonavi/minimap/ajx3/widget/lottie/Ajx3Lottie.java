package com.autonavi.minimap.ajx3.widget.lottie;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import com.airbnb.lottie.LottieAnimationView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallbackImpl;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
@SuppressLint({"ViewConstructor"})
public class Ajx3Lottie extends LottieAnimationView implements ViewExtension {
    protected BaseProperty mAttribute;
    private boolean mLoop = false;
    private boolean mWasAnimatingWhenDetached = false;

    public Ajx3Lottie(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAttribute = new Ajx3LottieProperty(this, iAjxContext);
    }

    public BaseProperty getProperty() {
        return this.mAttribute;
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mAttribute.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mAttribute.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mAttribute.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mAttribute.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mAttribute.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAttribute.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mAttribute.getAttribute(str);
    }

    public void getProgress(JsFunctionCallbackImpl jsFunctionCallbackImpl) {
        if (jsFunctionCallbackImpl != null) {
            jsFunctionCallbackImpl.callback(Float.valueOf(getProgress()));
        }
    }

    public void loop(boolean z) {
        super.loop(z);
        this.mLoop = z;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mLoop && this.mWasAnimatingWhenDetached && !isAnimating()) {
            playAnimation();
        }
        this.mWasAnimatingWhenDetached = false;
    }

    public void onDetachedFromWindow() {
        this.mWasAnimatingWhenDetached = isAnimating();
        super.onDetachedFromWindow();
    }
}
