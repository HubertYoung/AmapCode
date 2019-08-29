package com.alipay.android.phone.inside.security;

import android.content.ContextWrapper;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.staticdatastore.IStaticDataStoreComponent;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.ex.ExceptionLogger;
import com.alipay.android.phone.inside.security.api.SecurityGuardInit;

public class StaticDataStore {
    private IStaticDataStoreComponent a;

    public StaticDataStore(ContextWrapper contextWrapper) {
        SecurityGuardManager securityGuardManager;
        try {
            securityGuardManager = SecurityGuardManager.getInstance(contextWrapper);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "security", (String) "SDSInitEx", th);
            securityGuardManager = null;
        }
        if (securityGuardManager != null) {
            this.a = securityGuardManager.getStaticDataStoreComp();
        }
    }

    public final String a(int i) {
        String str;
        String a2 = SecurityGuardInit.a();
        try {
            if (this.a != null && i >= 0) {
                if (i <= 8) {
                    str = this.a.getAppKeyByIndex(i, a2);
                    return str;
                }
            }
            str = null;
            return str;
        } catch (SecException e) {
            ExceptionLogger e2 = LoggerFactory.e();
            StringBuilder sb = new StringBuilder("ErrorCode=");
            sb.append(e.getErrorCode());
            e2.a((String) "security", (String) "SDSGetAppKeyByIndexSecEx", (Throwable) e, sb.toString());
            return "";
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "security", (String) "SDSGetAppKeyByIndex", th);
            return "";
        }
    }
}
