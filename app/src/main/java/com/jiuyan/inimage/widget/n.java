package com.jiuyan.inimage.widget;

import android.widget.ImageView.ScaleType;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: PhotoCropViewFreedom */
/* synthetic */ class n {
    static final /* synthetic */ int[] a = new int[ScaleType.values().length];
    public static final Class b;

    static {
        Class cls;
        if (Boolean.TRUE.booleanValue()) {
            cls = String.class;
        } else {
            cls = ClassVerifier.class;
        }
        b = cls;
        try {
            a[ScaleType.FIT_START.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[ScaleType.FIT_END.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[ScaleType.FIT_CENTER.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            a[ScaleType.FIT_XY.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
    }
}
