package com.amap.bundle.utils.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContextWrapper;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class CompatDialog extends Dialog {
    /* access modifiers changed from: private */
    public final Activity mActivity;
    /* access modifiers changed from: private */
    public volatile boolean mAllowInvokeShow = true;
    private volatile boolean mIsShowing;
    private final DialogRootView mRootView;

    static class DialogRootView extends LinearLayout {
        private CompatDialog mDialog;

        public DialogRootView(CompatDialog compatDialog) {
            super(compatDialog.getContext());
            this.mDialog = compatDialog;
            setLayoutParams(new LayoutParams(-1, -1));
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            this.mDialog.onLayout(z, i, i2, i3, i4);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    public CompatDialog(Activity activity) {
        super(new ContextWrapper(activity));
        this.mActivity = activity;
        this.mRootView = new DialogRootView(this);
    }

    public CompatDialog(Activity activity, int i) {
        super(new ContextWrapper(activity), i);
        this.mActivity = activity;
        this.mRootView = new DialogRootView(this);
    }

    public CompatDialog(Activity activity, boolean z, OnCancelListener onCancelListener) {
        super(new ContextWrapper(activity), z, onCancelListener);
        this.mActivity = activity;
        this.mRootView = new DialogRootView(this);
    }

    public void setContentView(int i) {
        this.mRootView.removeAllViews();
        getLayoutInflater().inflate(i, this.mRootView, true);
        super.setContentView(this.mRootView);
    }

    public void setContentView(View view) {
        this.mRootView.removeAllViews();
        this.mRootView.addView(view, new LayoutParams(-1, -1));
        super.setContentView(this.mRootView);
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        this.mRootView.removeAllViews();
        this.mRootView.addView(view, layoutParams);
        super.setContentView(this.mRootView);
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public void show() {
        this.mIsShowing = true;
        aho.a(new Runnable() {
            public final void run() {
                if (!CompatDialog.this.mAllowInvokeShow) {
                    CompatDialog.this.mAllowInvokeShow = true;
                } else if (!CompatDialog.this.mActivity.isFinishing()) {
                    try {
                        CompatDialog.super.show();
                    } catch (Throwable unused) {
                    }
                }
            }
        });
    }

    public void dismiss() {
        this.mIsShowing = false;
        aho.a(new Runnable() {
            public final void run() {
                if (CompatDialog.super.isShowing()) {
                    try {
                        CompatDialog.super.dismiss();
                    } catch (Throwable unused) {
                    }
                } else {
                    CompatDialog.this.mAllowInvokeShow = false;
                }
            }
        });
    }
}
