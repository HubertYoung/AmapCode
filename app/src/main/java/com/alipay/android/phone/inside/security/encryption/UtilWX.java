package com.alipay.android.phone.inside.security.encryption;

import android.content.ContextWrapper;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import com.alibaba.wireless.security.open.staticdatastore.IStaticDataStoreComponent;

public class UtilWX {
    private ContextWrapper a;

    public UtilWX(ContextWrapper contextWrapper) {
        this.a = contextWrapper;
    }

    public final String a(String str, DataContext dataContext) {
        String str2;
        if (!(str == null || str.length() <= 0 || dataContext == null)) {
            try {
                IStaticDataStoreComponent staticDataStoreComp = SecurityGuardManager.getInstance(this.a).getStaticDataStoreComp();
                if (staticDataStoreComp != null) {
                    if (dataContext.b != null) {
                        str2 = new String(dataContext.b);
                    } else {
                        str2 = staticDataStoreComp.getAppKeyByIndex(dataContext.a < 0 ? 0 : dataContext.a, "");
                    }
                    if (str2 != null) {
                        IStaticDataEncryptComponent staticDataEncryptComp = SecurityGuardManager.getInstance(this.a).getStaticDataEncryptComp();
                        if (staticDataEncryptComp != null) {
                            return staticDataEncryptComp.staticSafeEncrypt(16, str2, str, "");
                        }
                    }
                }
            } catch (SecException unused) {
                return null;
            }
        }
        return null;
    }

    public final String b(String str, DataContext dataContext) {
        String str2;
        if (!(str == null || str.length() <= 0 || dataContext == null)) {
            try {
                IStaticDataStoreComponent staticDataStoreComp = SecurityGuardManager.getInstance(this.a).getStaticDataStoreComp();
                if (staticDataStoreComp != null) {
                    if (dataContext.b != null) {
                        str2 = new String(dataContext.b);
                    } else {
                        str2 = staticDataStoreComp.getAppKeyByIndex(dataContext.a < 0 ? 0 : dataContext.a, "");
                    }
                    if (str2 != null) {
                        IStaticDataEncryptComponent staticDataEncryptComp = SecurityGuardManager.getInstance(this.a).getStaticDataEncryptComp();
                        if (staticDataEncryptComp != null) {
                            return staticDataEncryptComp.staticSafeDecrypt(16, str2, str, "");
                        }
                    }
                }
            } catch (SecException unused) {
                return null;
            }
        }
        return null;
    }
}
