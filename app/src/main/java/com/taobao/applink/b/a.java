package com.taobao.applink.b;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.taobao.applink.util.TBAppLinkUtil;

public class a {
    private static String a;
    private static String b;
    private static String c;

    public static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            a = context.getPackageName();
        }
        return a;
    }

    public static String b(Context context) {
        if (TextUtils.isEmpty(b)) {
            try {
                b = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.loadLabel(context.getPackageManager()).toString();
            } catch (Exception unused) {
            }
        }
        return b;
    }

    public static String c(Context context) {
        if (TextUtils.isEmpty(c) && context != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(TBAppLinkUtil.TAOPACKAGENAME, 0);
                if (packageInfo != null) {
                    c = packageInfo.versionName;
                }
            } catch (NameNotFoundException unused) {
            }
        }
        return c;
    }
}
