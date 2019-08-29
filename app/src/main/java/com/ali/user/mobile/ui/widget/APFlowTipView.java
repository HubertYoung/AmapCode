package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import com.ali.user.mobile.security.ui.R;

public class APFlowTipView extends APLinearLayout {
    public static final int TYPE_EMPTY = 17;
    public static final int TYPE_NETWORK_ERROR = 16;
    public static final int TYPE_OVER_FLOW = 19;
    public static final int TYPE_WARNING = 18;
    private APButton a;
    private APTextView b;
    private APTextView c;
    private APImageView d;
    private boolean e = false;
    private int f;

    public APFlowTipView(Context context) {
        super(context);
    }

    public APFlowTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.o, this, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.f);
        this.f = obtainStyledAttributes.getInt(R.styleable.g, 16);
        this.e = obtainStyledAttributes.getBoolean(R.styleable.h, false);
        obtainStyledAttributes.recycle();
        setBackgroundColor(getResources().getColor(R.color.l));
        setOrientation(1);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.a = (APButton) findViewById(R.id.b);
        this.b = (APTextView) findViewById(R.id.bZ);
        this.c = (APTextView) findViewById(R.id.bM);
        this.d = (APImageView) findViewById(R.id.ab);
        resetFlowTipType(this.f);
    }

    public void setIsSimpleType(boolean z) {
        this.e = z;
    }

    public void resetFlowTipType(int i) {
        this.f = i;
        if (!this.e && this.f == 16) {
            this.d.setImageResource(R.drawable.w);
        }
    }

    public void setNoAction() {
        this.a.setVisibility(4);
    }

    public void setTips(String str) {
        if (TextUtils.isEmpty(str)) {
            this.b.setVisibility(8);
            return;
        }
        this.b.setText(Html.fromHtml(str));
        this.b.setVisibility(0);
    }

    public void setSubTips(String str) {
        if (TextUtils.isEmpty(str)) {
            this.c.setVisibility(8);
            return;
        }
        this.c.setText(Html.fromHtml(str));
        this.c.setVisibility(0);
    }

    public APButton getActionButton() {
        return this.a;
    }

    public APImageView getIcon() {
        return this.d;
    }

    public void setAction(String str, OnClickListener onClickListener) {
        if (TextUtils.isEmpty(str)) {
            this.a.setVisibility(8);
        } else {
            this.a.setVisibility(0);
            this.a.setText(Html.fromHtml(str));
        }
        this.a.setOnClickListener(onClickListener);
    }
}
