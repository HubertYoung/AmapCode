package com.ali.user.mobile.login.sso.impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;

public class SSOProviderHelper {
    public static SsoLoginInfo a(Context context) {
        String str;
        String str2;
        String str3;
        try {
            Cursor query = context.getContentResolver().query(Uri.parse("content://com.alipay.ali.authlogin/aliuser_sdk_sso"), null, null, null, null);
            AliUserLog.c("SSOProviderHelper", String.format("query sso token cursor:%s", new Object[]{query}));
            if (query != null) {
                if (query.moveToNext()) {
                    str3 = query.getString(query.getColumnIndex("loginId"));
                    str2 = query.getString(query.getColumnIndex("headImg"));
                    str = query.getString(query.getColumnIndex("alipaySsoToken"));
                    AliUserLog.c("SSOProviderHelper", String.format("sso id:%s, img:%s, token:%s, userId:%s", new Object[]{str3, str2, str, null}));
                } else {
                    str3 = null;
                    str2 = null;
                    str = null;
                }
                query.close();
                if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str)) {
                    SsoLoginInfo ssoLoginInfo = new SsoLoginInfo();
                    ssoLoginInfo.loginId = str3;
                    ssoLoginInfo.loginToken = str;
                    ssoLoginInfo.headImg = str2;
                    ssoLoginInfo.userId = null;
                    return ssoLoginInfo;
                }
            }
        } catch (Throwable th) {
            AliUserLog.b((String) "SSOProviderHelper", th);
        }
        return null;
    }
}
