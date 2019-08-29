package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUIHelper;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUCheckBox extends CheckBox implements AUViewInterface {
    private float defalutTextSize;
    private boolean dynamicTextSize;
    private Boolean isAP;

    public AUCheckBox(Context context) {
        super(context);
        this.dynamicTextSize = false;
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    public AUCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.dynamicTextSize = false;
        initSelfDefAttrs(context, attrs);
    }

    public AUCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.dynamicTextSize = false;
        initSelfDefAttrs(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setScaleSize();
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(AUViewEventHelper.wrapClickListener(l));
    }

    private void initSelfDefAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextAttr);
            this.dynamicTextSize = a.getBoolean(0, false);
            a.recycle();
        }
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        this.defalutTextSize = getTextSize();
        setScaleSize();
    }

    private void setScaleSize() {
        if (this.dynamicTextSize && AntUIHelper.getTextSizeGearGetter() != null) {
            float scaleSize = DensityUtil.getTextSize(DensityUtil.px2sp(getContext(), this.defalutTextSize), AntUIHelper.getTextSizeGearGetter().getCurrentGear());
            if (!DensityUtil.isValueEqule(DensityUtil.px2sp(getContext(), getTextSize()), scaleSize)) {
                super.setTextSize(2, scaleSize);
            }
        }
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
