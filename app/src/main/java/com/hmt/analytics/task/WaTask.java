package com.hmt.analytics.task;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;

public class WaTask {
    public static native String check(Context context, String str, String str2, String str3, String str4);

    public static String a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        System.load(str);
        StringBuilder sb = new StringBuilder();
        sb.append(ewc.b);
        sb.append("A");
        ewc.b = sb.toString();
        String check = check(context.getApplicationContext(), euw.B(context), euw.C(context), euw.b(), euw.g(context));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ewc.b);
        sb2.append(DiskFormatter.B);
        ewc.b = sb2.toString();
        ewp.a(context, (String) "temp_key_from_so", (Object) check);
        return check;
    }
}
