package com.alipay.mobile.tinyappcommon.remotedebug.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/* compiled from: RemoteDebugStateView */
public final class a extends com.alipay.mobile.tinyappcommon.view.a {
    private TextView c;
    /* access modifiers changed from: private */
    public com.alipay.mobile.tinyappcommon.remotedebug.state.a d;

    public a(Context context) {
        super(context);
        setFocusable(false);
        setOutsideTouchable(false);
    }

    /* access modifiers changed from: protected */
    public final View a(Object data) {
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.gravity = 17;
        lp.setMargins(0, 0, 0, 30);
        this.c = new TextView(this.a);
        this.c.setText("命中断点...");
        this.c.setTextSize(18.0f);
        this.c.setTextColor(-1);
        this.c.setBackgroundColor(0);
        this.c.setLayoutParams(lp);
        TextView button = new TextView(this.a);
        button.setText("退出");
        button.setTextSize(15.0f);
        button.setTextColor(-1);
        button.setPadding(25, 8, 25, 8);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0);
        drawable.setCornerRadius(3.0f);
        drawable.setStroke(3, -1);
        button.setBackgroundDrawable(drawable);
        button.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (a.this.d != null) {
                    a.this.d.a();
                }
            }
        });
        button.setLayoutParams(lp);
        LinearLayout container = new LinearLayout(this.a);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        container.setLayoutParams(layoutParams);
        container.setBackgroundColor(0);
        container.addView(this.c);
        container.addView(button);
        container.setOrientation(1);
        return container;
    }

    /* access modifiers changed from: protected */
    public final int a() {
        return -2;
    }

    /* access modifiers changed from: protected */
    public final int b() {
        return -2;
    }

    public final void a(String state) {
        this.c.setText(state);
    }

    public final void c() {
        e();
        if (this.b != null) {
            this.b.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final void d() {
    }

    public final void a(com.alipay.mobile.tinyappcommon.remotedebug.state.a listener) {
        this.d = listener;
    }
}
