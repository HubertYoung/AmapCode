package com.autonavi.minimap.component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SkipButton extends FrameLayout {
    /* access modifiers changed from: private */
    public TextView countDownView = null;
    private TextView skipView = null;

    public SkipButton(Context context, drr drr, boolean z) {
        super(context);
        init(drr, z);
    }

    private void init(drr drr, boolean z) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 5;
        setLayoutParams(layoutParams);
        int a = agn.a(getContext(), 16.0f);
        int a2 = agn.a(getContext(), 24.0f);
        setPadding(a, a2, a, a2);
        int a3 = z ? agn.a(getContext(), 84.0f) : agn.a(getContext(), 60.0f);
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(a3, -2));
        frameLayout.setPadding(agn.a(getContext(), 8.0f), agn.a(getContext(), 4.0f), agn.a(getContext(), 8.0f), agn.a(getContext(), 4.0f));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#33000000"));
        gradientDrawable.setCornerRadius(60.0f);
        gradientDrawable.setStroke(1, Color.parseColor("#b1b1b1"));
        frameLayout.setBackground(gradientDrawable);
        addView(frameLayout);
        this.countDownView = new TextView(getContext());
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 3;
        this.countDownView.setLayoutParams(layoutParams2);
        this.countDownView.setTextColor(-1);
        frameLayout.addView(this.countDownView);
        String str = z ? "跳过广告" : "跳过";
        this.skipView = new TextView(getContext());
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams3.gravity = 5;
        this.skipView.setLayoutParams(layoutParams3);
        this.skipView.setText(str);
        this.skipView.setTextColor(-1);
        frameLayout.addView(this.skipView);
        setOnClickListener(drr);
    }

    public void destroy() {
        setOnClickListener(null);
    }

    public void setCountDownText(final String str) {
        aho.a(new Runnable() {
            public final void run() {
                SkipButton.this.countDownView.setText(str);
            }
        });
    }
}
