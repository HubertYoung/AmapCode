package com.jiuyan.inimage.http.interfaces;

import com.alipay.android.hackbyte.ClassVerifier;

public interface IHttpAction {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    void buildUrl(String str, String str2);

    void execute(OnRequestCompleteListener onRequestCompleteListener);

    void putParam(String str, String str2);
}
