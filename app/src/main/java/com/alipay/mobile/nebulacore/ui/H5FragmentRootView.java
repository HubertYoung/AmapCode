package com.alipay.mobile.nebulacore.ui;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.RelativeLayout;
import com.alipay.mobile.nebula.util.H5Log;

public class H5FragmentRootView extends RelativeLayout {
    private String a = "H5FragmentRootView@";
    private boolean b = false;
    private boolean c = false;
    private int d = 0;
    private int e = 0;

    public H5FragmentRootView(Context context) {
        super(context);
    }

    public H5FragmentRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public H5FragmentRootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* Debug info: failed to restart local var, previous not found, register: 7 */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void init(android.content.Context r8, android.os.Bundle r9) {
        /*
            r7 = this;
            r4 = 1
            r3 = 0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = r7.a
            java.lang.StringBuilder r5 = r5.append(r6)
            int r6 = r7.hashCode()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r7.a = r5
            java.lang.String r5 = "fullscreen"
            boolean r5 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r9, r5, r3)
            if (r5 == 0) goto L_0x0060
            java.lang.String r5 = r7.a
            java.lang.String r6 = "disable mEnableNewAdjustInput by fullScreen."
            com.alipay.mobile.nebula.util.H5Log.d(r5, r6)
            r1 = r7
        L_0x002b:
            r1.b = r3
        L_0x002d:
            java.lang.String r3 = r7.a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "mEnableNewAdjustInput: "
            r5.<init>(r6)
            boolean r6 = r7.b
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r5)
            boolean r3 = r7.b
            if (r3 == 0) goto L_0x005f
            int r3 = com.alipay.mobile.nebula.util.H5StatusBarUtils.getStatusBarHeight(r8)
            r7.e = r3
            r7.setFitsSystemWindows(r4)
            boolean r3 = r8 instanceof android.app.Activity
            if (r3 == 0) goto L_0x005f
            android.app.Activity r8 = (android.app.Activity) r8
            android.view.Window r3 = r8.getWindow()
            r4 = 16
            r3.setSoftInputMode(r4)
        L_0x005f:
            return
        L_0x0060:
            java.lang.Class<com.alipay.mobile.nebula.provider.H5ConfigProvider> r5 = com.alipay.mobile.nebula.provider.H5ConfigProvider.class
            java.lang.String r5 = r5.getName()
            java.lang.Object r0 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r5)
            com.alipay.mobile.nebula.provider.H5ConfigProvider r0 = (com.alipay.mobile.nebula.provider.H5ConfigProvider) r0
            if (r0 == 0) goto L_0x002d
            java.lang.String r5 = "h5_enableNewAdjustInput"
            java.lang.String r2 = r0.getConfigWithProcessCache(r5)
            boolean r5 = android.text.TextUtils.isEmpty(r2)
            if (r5 != 0) goto L_0x002d
            java.lang.String r5 = "NO"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 != 0) goto L_0x0085
            r3 = r4
            r1 = r7
            goto L_0x002b
        L_0x0085:
            r1 = r7
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.ui.H5FragmentRootView.init(android.content.Context, android.os.Bundle):void");
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.b) {
            H5Log.d(this.a, "onAttachedToWindow mNeedRestoreWindowInsets: " + this.c);
            if (this.c) {
                a();
                this.c = false;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.b) {
            H5Log.d(this.a, "onDetachedFromWindow mWindowInsetBottom: " + this.d);
            if (this.d > 0) {
                this.c = true;
            }
        }
    }

    private void a() {
        if (VERSION.SDK_INT > 19) {
            requestApplyInsets();
        } else if (VERSION.SDK_INT >= 16) {
            requestFitSystemWindows();
        }
    }

    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (!this.b) {
            return super.onApplyWindowInsets(insets);
        }
        WindowInsets afterTransform = insets;
        if (VERSION.SDK_INT > 19) {
            int top = insets.getSystemWindowInsetTop() - this.e;
            if (top < 0) {
                top = 0;
            }
            int left = insets.getSystemWindowInsetLeft();
            int right = insets.getSystemWindowInsetRight();
            int bottom = insets.getSystemWindowInsetBottom();
            this.d = bottom;
            afterTransform = insets.replaceSystemWindowInsets(left, top, right, bottom);
        }
        H5Log.d(this.a, "onApplyWindowInsets, before: " + insets + ", after: " + afterTransform);
        return super.onApplyWindowInsets(afterTransform);
    }
}
