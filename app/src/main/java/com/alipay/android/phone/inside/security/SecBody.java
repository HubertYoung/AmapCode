package com.alipay.android.phone.inside.security;

import android.content.ContextWrapper;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class SecBody {
    public SecBody(ContextWrapper contextWrapper) {
        try {
            SecurityGuardManager.getInstance(contextWrapper);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "security", (String) "SecBodyInitEx", th);
        }
    }
}
