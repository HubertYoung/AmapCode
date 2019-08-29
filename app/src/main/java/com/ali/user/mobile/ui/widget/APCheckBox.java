package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.font.TextSizeGearGetter;
import com.ali.user.mobile.utils.DensityUtil;

public class APCheckBox extends CheckBox {
    private static TextSizeGearGetter c;
    private boolean a;
    private float b;

    public APCheckBox(Context context) {
        super(context);
        this.a = false;
        this.b = getTextSize();
        a();
    }

    public APCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = false;
        a(context, attributeSet);
    }

    public APCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = false;
        a(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        a();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(APViewEventHelper.a(onClickListener));
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.t);
        this.a = obtainStyledAttributes.getBoolean(R.styleable.u, false);
        this.b = getTextSize();
        obtainStyledAttributes.recycle();
        a();
    }

    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        this.b = getTextSize();
        a();
    }

    private void a() {
        if (this.a && c != null) {
            float a2 = DensityUtil.a(DensityUtil.b(getContext(), this.b), c.a());
            if (!DensityUtil.a(DensityUtil.b(getContext(), getTextSize()), a2)) {
                super.setTextSize(2, a2);
            }
        }
    }

    public static TextSizeGearGetter getTextSizeGearGetter() {
        return c;
    }

    public static void setTextSizeGearGetter(TextSizeGearGetter textSizeGearGetter) {
        c = textSizeGearGetter;
    }
}
