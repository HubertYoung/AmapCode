package com.alipay.android.phone.inside.common.setting;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.inside.common.util.DebugUtil;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class InsideSetting {
    public static String a() {
        return DebugUtil.a() ? a(LauncherApplication.a()) : "https://mobilegw.alipay.com/mgw.htm";
    }

    public static boolean b() {
        String a = a();
        return a.contains("mobilegw.alipay.com") || a.contains("mobilegwpre.alipay.com");
    }

    private static String a(Context context) {
        String str = null;
        try {
            Cursor query = context.getContentResolver().query(Uri.parse("content://com.alipay.android.app.settings.data.ServerProvider/current_server"), null, null, null, null);
            if (query != null && query.getCount() > 0) {
                if (query.moveToFirst()) {
                    String string = query.getString(query.getColumnIndex("url"));
                    if (!TextUtils.isEmpty(string)) {
                        str = string;
                    }
                }
                query.close();
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        if (TextUtils.isEmpty(str)) {
            str = "https://mobilegw.alipay.com/mgw.htm";
        }
        LoggerFactory.f().b((String) "inside", "InsideSetting::getCashierSettingUrl > url=".concat(String.valueOf(str)));
        return str;
    }
}
