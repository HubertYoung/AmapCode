package com.ali.user.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.commonbiz.util.ApkVerifyTool;
import com.alipay.mobile.common.share.widget.ResUtils;

public class CommonUtil {
    private static final String a = "CommonUtil";

    public static boolean a(Bundle bundle) {
        return bundle != null && "sms".equals(bundle.getString(ResUtils.STYLE));
    }

    public static boolean a(Context context) {
        try {
            return ApkVerifyTool.a(context);
        } catch (Throwable th) {
            AliUserLog.a(a, "isAlipayAppInstalled error", th);
            return false;
        }
    }

    public static void a(Activity activity) {
        if (a((Context) activity)) {
            StringBuilder sb = new StringBuilder();
            sb.append("alipays://platformapi/startApp");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setData(Uri.parse(sb.toString()));
            intent.addCategory("android.intent.category.BROWSABLE");
            activity.startActivity(intent);
            return;
        }
        b(activity);
    }

    public static void b(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            intent.setData(Uri.parse("https://ds.alipay.com"));
            activity.startActivity(intent);
        } catch (Throwable th) {
            AliUserLog.b(a, "guideDownloadAlipay error,change start style", th);
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.VIEW");
            intent2.setData(Uri.parse("https://ds.alipay.com"));
            activity.startActivity(intent2);
        }
    }

    public static void a(Activity activity, String str) {
        if (a((Context) activity)) {
            StringBuilder sb = new StringBuilder();
            sb.append("alipays://platformapi/startApp?appId=20000015&sourceId=trustedInside&logonId=");
            sb.append(str);
            sb.append("&fromWhich=alipay_inside");
            String str2 = a;
            StringBuilder sb2 = new StringBuilder("forget pwd = ");
            sb2.append(sb.toString());
            AliUserLog.c(str2, sb2.toString());
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            intent.setData(Uri.parse("https://m.alipay.com"));
            activity.startActivity(intent);
        } catch (Throwable th) {
            AliUserLog.b(a, "guideForgotPwdDownloadAlipay error,change start style", th);
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.VIEW");
            intent2.setData(Uri.parse("https://m.alipay.com"));
            activity.startActivity(intent2);
        }
    }
}
