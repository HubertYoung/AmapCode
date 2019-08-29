package com.q;

import android.app.Application;
import android.content.Context;
import com.amap.api.location.AMapLocation;
import com.baidu.location.BDLocation;
import com.sijla.b.b;
import com.sijla.b.d;
import com.sijla.callback.QtCallBack;
import com.sijla.g.e;
import org.json.JSONArray;

public class Qt {
    public static String _3uid = "";
    public static boolean _allowUseNetWork = true;
    public static String _channel = "";
    public static QtCallBack _dauCallBack = null;
    private static boolean hasInit = false;
    private static d sdkControler;

    public static boolean _s() {
        return false;
    }

    @Deprecated
    public static void appHidden(Context context) {
    }

    @Deprecated
    public static void appStart(Context context) {
    }

    public static void isAllowNetworkConnections(Context context, boolean z) {
    }

    public static void onEvent(Context context, JSONArray jSONArray) {
    }

    public static void onReceiveBDLocation(Context context, BDLocation bDLocation) {
    }

    public static void onReceiveGDLocation(Context context, AMapLocation aMapLocation) {
    }

    public static void onStartInput(Context context, String str) {
    }

    public static void init(Application application, String str, String str2) {
        init(application, str, str2, null);
    }

    public static void init(Application application, String str, String str2, QtCallBack qtCallBack) {
        init(application, str, str2, null, true, qtCallBack);
    }

    public static void init(Application application, String str, String str2, String str3, QtCallBack qtCallBack) {
        init(application, str, str2, str3, true, qtCallBack);
    }

    public static void init(Application application, String str, String str2, boolean z, QtCallBack qtCallBack) {
        init(application, str, str2, null, z, qtCallBack);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0039 A[Catch:{ Throwable -> 0x0087 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0043 A[Catch:{ Throwable -> 0x0087 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void init(android.app.Application r5, java.lang.String r6, java.lang.String r7, java.lang.String r8, boolean r9, com.sijla.callback.QtCallBack r10) {
        /*
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0087 }
            if (r5 != 0) goto L_0x0007
            return
        L_0x0007:
            if (r9 != 0) goto L_0x000a
            return
        L_0x000a:
            java.lang.String r2 = com.sijla.g.a.a.f(r5)     // Catch:{ Throwable -> 0x0087 }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0087 }
            r4 = 26
            if (r3 >= r4) goto L_0x0017
            com.sijla.b.b.a(r5, r2)     // Catch:{ Throwable -> 0x0087 }
        L_0x0017:
            if (r6 != 0) goto L_0x001b
            java.lang.String r6 = "null"
        L_0x001b:
            _channel = r6     // Catch:{ Throwable -> 0x0087 }
            if (r7 == 0) goto L_0x002b
            java.lang.String r6 = ""
            boolean r6 = r6.equals(r7)     // Catch:{ Throwable -> 0x0087 }
            if (r6 == 0) goto L_0x0028
            goto L_0x002b
        L_0x0028:
            _3uid = r7     // Catch:{ Throwable -> 0x0087 }
            goto L_0x002f
        L_0x002b:
            java.lang.String r6 = ""
            _3uid = r6     // Catch:{ Throwable -> 0x0087 }
        L_0x002f:
            _allowUseNetWork = r9     // Catch:{ Throwable -> 0x0087 }
            _dauCallBack = r10     // Catch:{ Throwable -> 0x0087 }
            boolean r6 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0087 }
            if (r6 == 0) goto L_0x003d
            java.lang.String r8 = r5.getPackageName()     // Catch:{ Throwable -> 0x0087 }
        L_0x003d:
            boolean r6 = r2.equals(r8)     // Catch:{ Throwable -> 0x0087 }
            if (r6 == 0) goto L_0x0086
            boolean r6 = hasInit     // Catch:{ Throwable -> 0x0087 }
            if (r6 == 0) goto L_0x004e
            java.lang.String r5 = "拦截到多次调用Init方法"
            com.sijla.b.b.b(r5)     // Catch:{ Throwable -> 0x0087 }
            return
        L_0x004e:
            com.sijla.b.b.a(r5)     // Catch:{ Throwable -> 0x0087 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0087 }
            com.sijla.b.b.a(r5, r6)     // Catch:{ Throwable -> 0x0087 }
            com.sijla.b.d r6 = sdkControler     // Catch:{ Throwable -> 0x0087 }
            if (r6 != 0) goto L_0x0066
            com.sijla.b.d r6 = new com.sijla.b.d     // Catch:{ Throwable -> 0x0087 }
            r6.<init>(r5)     // Catch:{ Throwable -> 0x0087 }
            sdkControler = r6     // Catch:{ Throwable -> 0x0087 }
            r6.a()     // Catch:{ Throwable -> 0x0087 }
        L_0x0066:
            r5 = 1
            hasInit = r5     // Catch:{ Throwable -> 0x0087 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0087 }
            java.lang.String r6 = "QuestMobile SDK init finish in process: "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0087 }
            r5.append(r2)     // Catch:{ Throwable -> 0x0087 }
            java.lang.String r6 = " cost:"
            r5.append(r6)     // Catch:{ Throwable -> 0x0087 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0087 }
            r8 = 0
            long r6 = r6 - r0
            r5.append(r6)     // Catch:{ Throwable -> 0x0087 }
            java.lang.String r6 = " 毫秒"
            r5.append(r6)     // Catch:{ Throwable -> 0x0087 }
        L_0x0086:
            return
        L_0x0087:
            r5 = move-exception
            r5.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.q.Qt.init(android.app.Application, java.lang.String, java.lang.String, java.lang.String, boolean, com.sijla.callback.QtCallBack):void");
    }

    public static void setAppkey(Context context, String str) {
        b.a(str);
    }

    public static void onEvent(Context context, String str, String str2) {
        e.a(context, str, str2);
    }

    public static void showLog(boolean z) {
        b.a(z);
    }
}
