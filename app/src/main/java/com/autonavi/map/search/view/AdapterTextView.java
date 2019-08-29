package com.autonavi.map.search.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class AdapterTextView extends TextView {
    private float offset;

    public AdapterTextView(Context context) {
        this(context, null);
    }

    public AdapterTextView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AdapterTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutParams(new LayoutParams(0, -2, 1.0f));
        this.offset = (float) getContext().getResources().getDimensionPixelSize(R.dimen.default_margin_1A);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        super.onMeasure(MeasureSpec.makeMeasureSpec(Math.min((int) measureTextWidth(), MeasureSpec.getSize(i)), Integer.MIN_VALUE), i2);
    }

    private float measureTextWidth() {
        if (TextUtils.isEmpty(getText().toString())) {
            return 0.0f;
        }
        return getPaint().measureText(getText().toString()) + ((float) getPaddingLeft()) + ((float) getPaddingRight()) + this.offset;
    }
}
