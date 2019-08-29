package com.alipay.mobile.tinyappcommon.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.alipay.mobile.antui.basic.AUPopupWindow;

/* compiled from: BasePopupWindowWithMask */
public abstract class a extends AUPopupWindow {
    protected Context a;
    protected View b;
    private ViewGroup c;

    /* access modifiers changed from: protected */
    public abstract int a();

    /* access modifiers changed from: protected */
    public abstract View a(Object obj);

    /* access modifiers changed from: protected */
    public abstract int b();

    public a(Context context) {
        this(context, null);
    }

    public a(Context context, Object data) {
        super(context);
        this.a = context;
        this.c = (ViewGroup) ((Activity) context).findViewById(16908290);
        setContentView(a(data));
        setWidth(b());
        setHeight(a());
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
    }

    public void showAsDropDown(View anchor) {
        if (anchor == null) {
            anchor = this.c;
        }
        a(0.4f);
        super.showAsDropDown(anchor);
    }

    public final void e() {
        a(0.6f);
        super.showAtLocation(this.c, 17, 0, 0);
    }

    private void a(float alpha) {
        c();
        if (this.b == null) {
            this.b = new View(this.a);
        }
        this.b.setBackgroundColor(-16777216);
        this.b.setAlpha(alpha);
        this.b.setFitsSystemWindows(false);
        d();
        this.c.addView(this.b, new LayoutParams(-1, -1));
    }

    /* access modifiers changed from: protected */
    public void d() {
        this.b.setOnKeyListener(new OnKeyListener() {
            public final boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return false;
                }
                a.this.c();
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.b != null) {
            this.c.removeView(this.b);
            this.b = null;
        }
    }

    public void dismiss() {
        c();
        super.dismiss();
    }
}
