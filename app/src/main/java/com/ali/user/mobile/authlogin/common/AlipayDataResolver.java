package com.ali.user.mobile.authlogin.common;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.ali.user.mobile.log.AliUserLog;

public class AlipayDataResolver {
    private ContentResolver a;

    public AlipayDataResolver(Context context) {
        this.a = context.getContentResolver();
    }

    public final int a(int i) {
        try {
            Cursor query = this.a.query(Uri.parse("content://com.alipay.ali.authlogin/auth_login_sdk_version"), null, null, null, null);
            if (query == null) {
                AliUserLog.c("AlipayDataResolver", "getAlipayAuthLoginSupportVersion cursor == null");
                return i;
            }
            int intValue = query.moveToFirst() ? Integer.valueOf(query.getString(0)).intValue() : i;
            query.close();
            AliUserLog.c("AlipayDataResolver", "getAlipayAuthLoginSupportVersion result=".concat(String.valueOf(intValue)));
            return intValue;
        } catch (Throwable th) {
            AliUserLog.b("AlipayDataResolver", "getAlipayAuthLoginSupportVersion error", th);
            return i;
        }
    }
}
