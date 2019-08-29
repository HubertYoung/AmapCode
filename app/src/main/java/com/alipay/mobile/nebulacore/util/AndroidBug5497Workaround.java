package com.alipay.mobile.nebulacore.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5StatusBarUtils;

public class AndroidBug5497Workaround {
    private View a;
    private int b;
    private LayoutParams c;
    private int d;
    private NavigationBarUtil e;

    public static void assistActivity(Activity activity, boolean isTransparent) {
        try {
            new AndroidBug5497Workaround(activity, isTransparent);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5AndroidBug5497Workaround", throwable);
        }
    }

    private AndroidBug5497Workaround(Activity activity, final boolean isTransparent) {
        this.e = new NavigationBarUtil(activity);
        this.a = ((FrameLayout) activity.findViewById(16908290)).getChildAt(0);
        try {
            this.d = activity.getResources().getDimensionPixelSize(activity.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android"));
        } catch (Throwable throwable) {
            H5Log.e((String) "H5AndroidBug5497Workaround", throwable);
        }
        this.a.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                try {
                    AndroidBug5497Workaround.this.a(isTransparent);
                } catch (Throwable throwable) {
                    H5Log.e((String) "H5AndroidBug5497Workaround", throwable);
                }
            }
        });
        this.c = (LayoutParams) this.a.getLayoutParams();
    }

    /* access modifiers changed from: private */
    public void a(boolean isTransparent) {
        int usableHeightNow = a();
        if (usableHeightNow != this.b) {
            int usableHeightSansKeyboard = this.a.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            H5Log.d("H5AndroidBug5497Workaround", "heightDifference " + heightDifference + " usableHeightSansKeyboard " + usableHeightSansKeyboard + " statusBarHeight:" + this.d + " hasNavigationBar " + this.e.hasNavigationBar() + " NavigationBarHeight " + this.e.getNavigationBarHeight());
            if (heightDifference > usableHeightSansKeyboard / 4) {
                if (!H5StatusBarUtils.isSupport() || !H5StatusBarUtils.isConfigSupport() || isTransparent) {
                    this.c.height = usableHeightSansKeyboard - heightDifference;
                } else {
                    this.c.height = (usableHeightSansKeyboard - heightDifference) + this.d;
                }
            } else if (!H5StatusBarUtils.isSupport() || !H5StatusBarUtils.isConfigSupport() || isTransparent) {
                this.c.height = usableHeightSansKeyboard - this.d;
            } else {
                this.c.height = usableHeightSansKeyboard;
                if (this.e.hasNavigationBar()) {
                    this.c.height -= this.e.getNavigationBarHeight();
                }
            }
            this.a.requestLayout();
            this.b = usableHeightNow;
        }
    }

    private int a() {
        Rect r = new Rect();
        this.a.getWindowVisibleDisplayFrame(r);
        return r.bottom - r.top;
    }
}
