package com.jiuyan.inimage.callback;

import android.graphics.Bitmap;
import com.alipay.android.hackbyte.ClassVerifier;

public interface IFaceDelegate {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    public interface IDetectCallback {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void onDetectResult(int[][] iArr);
    }

    void detectFace(Bitmap bitmap, IDetectCallback iDetectCallback);
}
