package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.antui.screenadpt.AUAttrsUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class AURelativeLayout extends RelativeLayout implements AUViewInterface {
    private Boolean isAP;

    public AURelativeLayout(Context context) {
        super(context);
    }

    public AURelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
            AUAttrsUtils.adptApMinMax(this, context);
        }
    }

    public AURelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
            AUAttrsUtils.adptApMinMax(this, context);
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        if (!AUScreenUtils.checkApFlag(getContext(), attrs, this)) {
            return super.generateLayoutParams(attrs);
        }
        if (getLayoutParams() != null && (getLayoutParams() instanceof GridLayoutManager.LayoutParams) && getSuggestedMinimumHeight() > 0 && getLayoutParams().height < getSuggestedMinimumHeight()) {
            getLayoutParams().height = getSuggestedMinimumHeight();
        }
        int[] sizeAndMargin = AUAttrsUtils.getViewSizeAndMargin(getContext(), AUAttrsUtils.handleAttrs(attrs));
        LayoutParams lp = super.generateLayoutParams(attrs);
        AUAttrsUtils.replaceLayoutParam(getContext(), lp, sizeAndMargin);
        return lp;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            if (getLayoutParams() == null) {
                setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } catch (Exception e) {
            StringBuffer msg = new StringBuffer("APRelativeLayout:" + getId());
            int viewCount = getChildCount();
            for (int i = 0; i < viewCount; i++) {
                View view = getChildAt(i);
                if (view != null) {
                    msg.append(new StringBuilder(MergeUtil.SEPARATOR_KV).append(view.getId()).toString());
                }
            }
            throw new IllegalStateException(msg.toString(), e);
        }
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
