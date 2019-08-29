package com.alipay.mobile.security.bio.service;

import android.content.Context;
import com.alipay.mobile.security.bio.runtime.Runtime;

@Deprecated
public class LocalizeAssistor {
    @Deprecated
    public static String getFrameworkVersion(Context context) {
        return Runtime.getFrameworkVersion(context);
    }
}
