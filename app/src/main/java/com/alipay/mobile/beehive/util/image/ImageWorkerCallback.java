package com.alipay.mobile.beehive.util.image;

import android.graphics.drawable.BitmapDrawable;

public interface ImageWorkerCallback {
    void onCancel(String str);

    void onFailure(String str, int i, String str2);

    void onProgress(String str, double d);

    void onStart(String str);

    void onSuccess(String str, BitmapDrawable bitmapDrawable);
}
