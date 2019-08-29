package com.autonavi.minimap.component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class SearchButton extends TextView {
    public SearchButton(Context context, drr drr, String str) {
        super(context);
        init(drr, str);
    }

    private void init(drr drr, String str) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 81;
        layoutParams.bottomMargin = agn.a(getContext(), 60.0f);
        setLayoutParams(layoutParams);
        setPadding(agn.a(getContext(), 30.0f), agn.a(getContext(), 12.0f), agn.a(getContext(), 30.0f), agn.a(getContext(), 12.0f));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor("#b3000000"));
        gradientDrawable.setCornerRadius(60.0f);
        setBackground(gradientDrawable);
        setText(str);
        setTextColor(-1);
        setOnClickListener(drr);
    }

    public void destroy() {
        setOnClickListener(null);
    }
}
