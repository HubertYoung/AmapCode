package com.jiuyan.inimage.http.interfaces;

import com.alipay.android.hackbyte.ClassVerifier;

public interface IEncryptAction {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    String encrypt(String str, String str2);
}
