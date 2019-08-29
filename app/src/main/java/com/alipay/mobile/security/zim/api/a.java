package com.alipay.mobile.security.zim.api;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: ZIMFacade */
final class a implements com.alipay.mobile.security.zim.b.a {
    a() {
    }

    public final String a(Context context) {
        return ZIMFacade.b(context);
    }

    public final String a() {
        return "android";
    }

    public final String b() {
        return Build.MODEL;
    }

    public final String b(Context context) {
        if (context == null) {
            return "";
        }
        return context.getPackageName();
    }

    public final String c(Context context) {
        String str = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            BioLog.w((Throwable) e);
            return str;
        }
    }

    public final String c() {
        return VERSION.RELEASE;
    }
}
