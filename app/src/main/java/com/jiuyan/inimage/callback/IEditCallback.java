package com.jiuyan.inimage.callback;

import android.graphics.Bitmap;
import com.alipay.android.hackbyte.ClassVerifier;

public interface IEditCallback {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    void onEditCancel();

    void onEditDone(boolean z, Bitmap bitmap, String str);

    void onEditNothing(Bitmap bitmap);
}
