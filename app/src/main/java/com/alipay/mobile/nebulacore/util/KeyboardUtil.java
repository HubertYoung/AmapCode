package com.alipay.mobile.nebulacore.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.alipay.mobile.nebula.util.H5Log;

public class KeyboardUtil implements OnGlobalLayoutListener {
    public static final String TAG = "KeyboardUtil";
    public static boolean mVisible;
    private int a = 0;
    private View b;
    private KeyboardListener c;

    public interface KeyboardListener {
        void onKeyboardVisible(boolean z);
    }

    public KeyboardUtil(Activity activity) {
        mVisible = false;
        if (activity != null) {
            try {
                this.b = activity.getWindow().getDecorView().findViewById(16908290);
            } catch (Exception e) {
                H5Log.e(TAG, "exception detail", e);
            }
        }
    }

    public void setListener(KeyboardListener listener) {
        this.c = listener;
        if (this.c == null) {
            this.b.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            this.b.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }

    public void onGlobalLayout() {
        if (this.a == 0) {
            this.a = this.b.getMeasuredHeight();
        } else if (this.c != null) {
            int height = this.b.getHeight();
            if (!mVisible && this.a > height + 100) {
                mVisible = true;
                this.c.onKeyboardVisible(mVisible);
                this.a = height;
            } else if (mVisible && this.a < height - 100) {
                mVisible = false;
                this.c.onKeyboardVisible(mVisible);
                this.a = height;
            }
        }
    }
}
