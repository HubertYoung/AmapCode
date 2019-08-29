package com.alipay.sdk.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.cons.a;

public final class k {
    private static final String a = "content://com.alipay.android.app.settings.data.ServerProvider/current_server";

    public static String a(Context context) {
        if (EnvUtils.isSandBox()) {
            return a.b;
        }
        if (context == null) {
            return a.a;
        }
        String str = a.a;
        if (TextUtils.isEmpty(str)) {
            str = a.a;
        }
        return str;
    }

    private static String b(Context context) {
        Cursor query = context.getContentResolver().query(Uri.parse(a), null, null, null, null);
        String str = null;
        if (query != null && query.getCount() > 0) {
            if (query.moveToFirst()) {
                str = query.getString(query.getColumnIndex("url"));
            }
            query.close();
        }
        return str;
    }
}
