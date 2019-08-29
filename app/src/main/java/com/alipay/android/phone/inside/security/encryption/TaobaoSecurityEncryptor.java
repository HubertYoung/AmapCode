package com.alipay.android.phone.inside.security.encryption;

import android.content.ContextWrapper;
import com.alipay.android.phone.inside.security.util.SsoLoginUtils;

public class TaobaoSecurityEncryptor {
    public static String a(ContextWrapper contextWrapper, String str) throws Exception {
        return new UtilWX(contextWrapper).a(str, a(contextWrapper));
    }

    public static String b(ContextWrapper contextWrapper, String str) throws Exception {
        return new UtilWX(contextWrapper).b(str, a(contextWrapper));
    }

    private static DataContext a(ContextWrapper contextWrapper) {
        SsoLoginUtils.a(contextWrapper);
        DataContext dataContext = new DataContext();
        dataContext.b = null;
        return dataContext;
    }
}
