package com.amap.bundle.blutils.zip;

import android.graphics.Bitmap;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IBitmapCompressedListener {
    void onCompress(Bitmap bitmap, String str);

    void onException(Exception exc);
}
