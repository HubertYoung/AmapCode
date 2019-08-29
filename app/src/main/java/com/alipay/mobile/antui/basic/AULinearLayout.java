package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.screenadpt.AUAttrsUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;

public class AULinearLayout extends LinearLayout implements AUViewGroupInterface {
    private Boolean isAP;

    public AULinearLayout(Context context) {
        super(context);
    }

    public AULinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public AULinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public AULinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        if (!AUScreenUtils.checkApFlag(getContext(), attrs, this) || attrs == null) {
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

    public View getView() {
        return this;
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(AUViewEventHelper.wrapClickListener(l));
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
