package com.alipay.mobile.common.image;

import android.graphics.Bitmap;
import com.alipay.android.hackbyte.ClassVerifier;

public interface ImageLoaderListener {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    void onCancelled(String str);

    void onFailed(String str, int i, String str2);

    void onPostLoad(String str, Bitmap bitmap);

    void onPreLoad(String str);

    void onProgressUpdate(String str, double d);
}
