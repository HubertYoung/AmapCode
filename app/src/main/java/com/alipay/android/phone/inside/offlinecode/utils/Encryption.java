package com.alipay.android.phone.inside.offlinecode.utils;

import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdataencrypt.IDynamicDataEncryptComponent;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.ex.ExceptionLogger;

public class Encryption {
    public static final String TAG = "Encryption";
    private IDynamicDataEncryptComponent mDynamicEncrypt;

    static class Holder {
        static Encryption instance = new Encryption();

        private Holder() {
        }
    }

    public static Encryption getInstance() {
        return Holder.instance;
    }

    private Encryption() {
        getDynamicStore();
    }

    private IDynamicDataEncryptComponent getDynamicStore() {
        if (this.mDynamicEncrypt == null) {
            try {
                this.mDynamicEncrypt = SecurityGuardManager.getInstance(LauncherApplication.a()).getDynamicDataEncryptComp();
            } catch (Throwable unused) {
            }
        }
        return this.mDynamicEncrypt;
    }

    public String encrypt(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            str2 = getDynamicStore().dynamicEncryptDDp(str);
        } catch (SecException e) {
            ExceptionLogger e2 = LoggerFactory.e();
            String str3 = TAG;
            StringBuilder sb = new StringBuilder("errorCode=");
            sb.append(e.getErrorCode());
            e2.a(str3, (String) "StorageEncryptEx", (Throwable) e, sb.toString());
            str2 = "";
        }
        return str2;
    }

    public String decrypt(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            str2 = getDynamicStore().dynamicDecryptDDp(str);
        } catch (SecException e) {
            ExceptionLogger e2 = LoggerFactory.e();
            String str3 = TAG;
            StringBuilder sb = new StringBuilder("errorCode=");
            sb.append(e.getErrorCode());
            e2.a(str3, (String) "StorageDecryptEx", (Throwable) e, sb.toString());
            str2 = "";
        }
        return str2;
    }
}
