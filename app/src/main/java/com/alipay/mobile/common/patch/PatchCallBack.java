package com.alipay.mobile.common.patch;

import com.alipay.android.hackbyte.ClassVerifier;

public interface PatchCallBack {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    void onDownloadNewFileProgressUpdate(double d);

    void onDownloadPatchProgressUpdate(double d);

    void onFail(int i);

    void onSuccess(String str);
}
