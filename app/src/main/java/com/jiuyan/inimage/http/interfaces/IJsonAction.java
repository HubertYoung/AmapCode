package com.jiuyan.inimage.http.interfaces;

import com.alipay.android.hackbyte.ClassVerifier;

public interface IJsonAction {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    Object parse(String str, Class cls);
}
