package com.alipay.sdk.auth;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import com.alipay.sdk.data.c;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.widget.a;

public final class h {
    private static final String a = "com.eg.android.AlipayGphone";
    private static final int b = 65;
    /* access modifiers changed from: private */
    public static a c;
    /* access modifiers changed from: private */
    public static String d;

    private static boolean a(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.eg.android.AlipayGphone", 128);
            if (packageInfo != null && packageInfo.versionCode >= 65) {
                return true;
            }
            return false;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    public static void a(Activity activity, APAuthInfo aPAuthInfo) {
        b a2 = b.a();
        c.a();
        a2.a((Context) activity);
        if (a((Context) activity)) {
            StringBuilder sb = new StringBuilder();
            sb.append("alipayauth://platformapi/startapp");
            sb.append("?appId=20000122");
            sb.append("&approveType=005");
            sb.append("&scope=kuaijie");
            sb.append("&productId=");
            sb.append(aPAuthInfo.getProductId());
            sb.append("&thirdpartyId=");
            sb.append(aPAuthInfo.getAppId());
            sb.append("&redirectUri=");
            sb.append(aPAuthInfo.getRedirectUri());
            a(activity, sb.toString());
            return;
        }
        if (activity != null) {
            try {
                if (!activity.isFinishing()) {
                    a aVar = new a(activity, a.a);
                    c = aVar;
                    aVar.a();
                }
            } catch (Exception unused) {
                c = null;
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("app_id=");
        sb2.append(aPAuthInfo.getAppId());
        sb2.append("&partner=");
        sb2.append(aPAuthInfo.getPid());
        sb2.append("&scope=kuaijie");
        sb2.append("&login_goal=auth");
        sb2.append("&redirect_url=");
        sb2.append(aPAuthInfo.getRedirectUri());
        sb2.append("&view=wap");
        sb2.append("&prod_code=");
        sb2.append(aPAuthInfo.getProductId());
        new Thread(new i(activity, sb2, aPAuthInfo)).start();
    }

    private static void b(Activity activity, APAuthInfo aPAuthInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("alipayauth://platformapi/startapp");
        sb.append("?appId=20000122");
        sb.append("&approveType=005");
        sb.append("&scope=kuaijie");
        sb.append("&productId=");
        sb.append(aPAuthInfo.getProductId());
        sb.append("&thirdpartyId=");
        sb.append(aPAuthInfo.getAppId());
        sb.append("&redirectUri=");
        sb.append(aPAuthInfo.getRedirectUri());
        a(activity, sb.toString());
    }

    private static void c(Activity activity, APAuthInfo aPAuthInfo) {
        if (activity != null) {
            try {
                if (!activity.isFinishing()) {
                    a aVar = new a(activity, a.a);
                    c = aVar;
                    aVar.a();
                }
            } catch (Exception unused) {
                c = null;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("app_id=");
        sb.append(aPAuthInfo.getAppId());
        sb.append("&partner=");
        sb.append(aPAuthInfo.getPid());
        sb.append("&scope=kuaijie");
        sb.append("&login_goal=auth");
        sb.append("&redirect_url=");
        sb.append(aPAuthInfo.getRedirectUri());
        sb.append("&view=wap");
        sb.append("&prod_code=");
        sb.append(aPAuthInfo.getProductId());
        new Thread(new i(activity, sb, aPAuthInfo)).start();
    }

    public static void a(Activity activity, String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
        }
    }
}
