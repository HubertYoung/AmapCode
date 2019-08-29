package com.autonavi.minimap.ajx3;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.autonavi.minimap.ajx3.widget.AjxView;

public class AjxOrientationHelper {
    private int mCurrentOrientation;

    public static class SingletonHolder {
        public static AjxOrientationHelper instance = new AjxOrientationHelper();
    }

    public static AjxOrientationHelper getInstance() {
        return SingletonHolder.instance;
    }

    private AjxOrientationHelper() {
        this.mCurrentOrientation = 1;
    }

    public int getOrientation() {
        return this.mCurrentOrientation;
    }

    public boolean onConfigurationChanged(AjxView ajxView, int i) {
        boolean z = this.mCurrentOrientation != i;
        if (z) {
            this.mCurrentOrientation = i;
            dispatchOrientationChange(ajxView);
        }
        return z;
    }

    public boolean onSizeChanged(AjxView ajxView, int i, int i2, int i3, int i4) {
        boolean z = false;
        boolean z2 = (i - i2) * (i3 - i4) < 0;
        if (z2 && (i3 == i || i4 == i2)) {
            z2 = false;
        }
        boolean z3 = i3 <= 0 && i4 <= 0;
        if (z2 || z3) {
            if (ajxView != null) {
                View rootView = ajxView.getRootView();
                if (rootView != null) {
                    int width = rootView.getWidth();
                    int height = rootView.getHeight();
                    if (width > 0 && height > 0) {
                        i2 = height;
                        i = width;
                    }
                }
            }
            int i5 = i > i2 ? 2 : 1;
            if (z3) {
                if (1 != i5) {
                    z = true;
                }
                z2 = z;
            }
            this.mCurrentOrientation = i5;
            if (z2) {
                dispatchOrientationChange(ajxView);
            }
        }
        return z2;
    }

    private void dispatchOrientationChange(AjxView ajxView) {
        Context context = ajxView.getContext();
        if (context != null && (context instanceof Activity)) {
            int i = 0;
            switch (((Activity) ajxView.getContext()).getWindowManager().getDefaultDisplay().getRotation()) {
                case 1:
                    i = -90;
                    break;
                case 2:
                    i = 180;
                    break;
                case 3:
                    i = 90;
                    break;
            }
            ajxView.dispatchOrientationChange(i);
        }
    }
}
