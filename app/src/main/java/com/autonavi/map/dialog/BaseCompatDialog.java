package com.autonavi.map.dialog;

import android.app.Activity;
import android.content.ContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class BaseCompatDialog extends bto {
    private volatile boolean a;
    /* access modifiers changed from: private */
    public volatile boolean b = true;
    /* access modifiers changed from: private */
    public final Activity c;
    private final DialogRootView d;

    static class DialogRootView extends LinearLayout {
        private BaseCompatDialog mDialog;

        public DialogRootView(BaseCompatDialog baseCompatDialog) {
            super(baseCompatDialog.getContext());
            this.mDialog = baseCompatDialog;
            setLayoutParams(new LayoutParams(-1, -1));
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
        }
    }

    public BaseCompatDialog(Activity activity, int i) {
        super(new ContextWrapper(activity), i);
        this.c = activity;
        this.d = new DialogRootView(this);
    }

    public void setContentView(int i) {
        this.d.removeAllViews();
        getLayoutInflater().inflate(i, this.d, true);
        super.setContentView(this.d);
    }

    public void setContentView(View view) {
        this.d.removeAllViews();
        this.d.addView(view, new ViewGroup.LayoutParams(-1, -1));
        super.setContentView(this.d);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.d.removeAllViews();
        this.d.addView(view, layoutParams);
        super.setContentView(this.d);
    }

    public boolean isShowing() {
        return this.a;
    }

    public void show() {
        this.a = true;
        aho.a(new Runnable() {
            public final void run() {
                if (!BaseCompatDialog.this.b) {
                    BaseCompatDialog.this.b = true;
                } else if (!BaseCompatDialog.this.c.isFinishing()) {
                    try {
                        BaseCompatDialog.super.show();
                    } catch (Throwable unused) {
                    }
                }
            }
        });
    }

    public void dismiss() {
        this.a = false;
        aho.a(new Runnable() {
            public final void run() {
                if (BaseCompatDialog.super.isShowing()) {
                    try {
                        BaseCompatDialog.super.dismiss();
                    } catch (Throwable unused) {
                    }
                } else {
                    BaseCompatDialog.this.b = false;
                }
            }
        });
    }
}
