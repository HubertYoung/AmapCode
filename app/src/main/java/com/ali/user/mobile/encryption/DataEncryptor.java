package com.ali.user.mobile.encryption;

import android.content.ContextWrapper;
import com.ali.user.mobile.info.AppInfo;
import com.alipay.android.phone.inside.security.util.SsoLoginUtils;

public class DataEncryptor {
    public static String a(ContextWrapper contextWrapper, String str) throws Exception {
        return new UtilWX(contextWrapper).a(str, a(contextWrapper));
    }

    public static String b(ContextWrapper contextWrapper, String str) throws Exception {
        return new UtilWX(contextWrapper).b(str, a(contextWrapper));
    }

    private static DataContext a(ContextWrapper contextWrapper) {
        SsoLoginUtils.a(contextWrapper);
        DataContext dataContext = new DataContext();
        dataContext.b = AppInfo.getInstance().getAppKey(contextWrapper).getBytes();
        return dataContext;
    }
}
