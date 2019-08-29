package com.autonavi.bundle.amaphome.widget;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class OldMapStatusBar extends TextView {
    public OldMapStatusBar(Context context, CharSequence charSequence) {
        super(context);
        setText(charSequence);
        setTextSize(1, 12.0f);
        setGravity(17);
        setTextColor(-1);
        setBackgroundColor(Color.parseColor("#547EFF"));
        setPadding(0, euk.a() ? euk.a(getContext()) : 0, 0, 0);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getResources().getDisplayMetrics().widthPixels, bet.a(getContext(), 20) + (euk.a() ? euk.a(getContext()) : 0));
    }
}
