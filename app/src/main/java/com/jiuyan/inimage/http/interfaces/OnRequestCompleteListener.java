package com.jiuyan.inimage.http.interfaces;

import com.alipay.android.hackbyte.ClassVerifier;

public interface OnRequestCompleteListener {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    void onFailed(int i);

    void onSuccess(byte[] bArr);
}
