package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.antui.screenadpt.AUAttrsUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;

public class AUFrameLayout extends FrameLayout implements AUViewInterface {
    private Boolean isAP;

    public AUFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public AUFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public AUFrameLayout(Context context) {
        super(context);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        if (!AUScreenUtils.checkApFlag(getContext(), attrs, this)) {
            return super.generateLayoutParams(attrs);
        }
        int[] sizeAndMargin = AUAttrsUtils.getViewSizeAndMargin(getContext(), AUAttrsUtils.handleAttrs(attrs));
        LayoutParams lp = super.generateLayoutParams(attrs);
        AUAttrsUtils.replaceLayoutParam(getContext(), lp, sizeAndMargin);
        return lp;
    }

    public void addView(View child) {
        extendAP(child);
        super.addView(child);
    }

    private void extendAP(View child) {
        if (child instanceof AUViewInterface) {
            AUViewInterface auViewInterface = (AUViewInterface) child;
            if (auViewInterface.isAP() == null) {
                auViewInterface.setAP(isAP());
            }
        }
    }

    public void addView(View child, int index) {
        extendAP(child);
        super.addView(child, index);
    }

    public void addView(View child, int width, int height) {
        extendAP(child);
        super.addView(child, width, height);
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        extendAP(child);
        super.addView(child, params);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        extendAP(child);
        super.addView(child, index, params);
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
