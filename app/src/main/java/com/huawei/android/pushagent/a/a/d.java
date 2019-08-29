package com.huawei.android.pushagent.a.a;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.a.a.c;

public class d {
    public static String a(Context context, String str, String str2) {
        String str3;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("_v2");
            str3 = c.b(context, new e(context, str).b(sb.toString()));
        } catch (Exception e) {
            c.c("PushLogSC2816", e.toString(), e);
            str3 = "";
        }
        if (TextUtils.isEmpty(str3)) {
            c.a("PushLogSC2816", "not exist for:".concat(String.valueOf(str2)));
        }
        return str3;
    }

    public static boolean a(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("_v2");
        return new e(context, str).a(sb.toString(), c.a(context, str3));
    }
}
