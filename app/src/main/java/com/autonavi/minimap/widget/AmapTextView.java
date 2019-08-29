package com.autonavi.minimap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.autonavi.minimap.widget.AutofitHelper.OnTextSizeChangeListener;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AmapTextView extends TextView implements OnTextSizeChangeListener {
    private float mDensity;
    private AutofitHelper mHelper;
    private float mScaledDensity;

    private void init(Context context, AttributeSet attributeSet, int i) {
    }

    public void onTextSizeChange(float f, float f2) {
    }

    public AmapTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AmapTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public AmapTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        if (this.mHelper != null) {
            this.mHelper.setTextSize(i, f);
        }
    }

    public void setLines(int i) {
        super.setLines(i);
        if (this.mHelper != null) {
            this.mHelper.setMaxLines(i);
        }
    }

    public void setMaxLines(int i) {
        super.setMaxLines(i);
        if (this.mHelper != null) {
            this.mHelper.setMaxLines(i);
        }
    }

    public void setTextSize(float f) {
        super.setTextSize(getScaleSpSize(f));
    }

    private float getScaleSpSize(float f) {
        if (this.mScaledDensity <= 0.0f) {
            return f;
        }
        float f2 = f / this.mScaledDensity;
        return this.mScaledDensity != this.mDensity ? f2 / (this.mScaledDensity / this.mDensity) : f2;
    }

    public boolean isSizeToFit() {
        return this.mHelper.isEnabled();
    }

    public void setSizeToFit(boolean z) {
        this.mHelper.setEnabled(z);
    }

    public float getMinTextSize() {
        return this.mHelper.getMinTextSize();
    }

    public void setMinTextSize(int i) {
        this.mHelper.setMinTextSize(2, (float) i);
    }

    public float getPrecision() {
        return this.mHelper.getPrecision();
    }

    public void setPrecision(float f) {
        this.mHelper.setPrecision(f);
    }
}
