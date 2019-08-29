package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.amap.bundle.blutils.device.DeviceInfo;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.server.aos.serverkey;

/* renamed from: ajp reason: default package */
/* compiled from: WebViewUtil */
public final class ajp {
    private static String a;

    public static void a(final Context context) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            b(context);
            return;
        }
        aho.a(new Runnable() {
            public final void run() {
                ajp.b(context);
            }
        });
        AMapLog.warning("paas.webview", "WebViewUtil", Log.getStackTraceString(new RuntimeException("clearAllCookies must invoke in main thread!")));
    }

    static void b(Context context) {
        try {
            if (VERSION.SDK_INT >= 22) {
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();
                return;
            }
            CookieSyncManager createInstance = CookieSyncManager.createInstance(context);
            createInstance.startSync();
            CookieManager.getInstance().removeAllCookie();
            createInstance.stopSync();
            createInstance.sync();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("clearAllCookies error: ");
            sb.append(e.getLocalizedMessage());
            AMapLog.error("paas.webview", "WebViewUtil", sb.toString());
        }
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.contains("yaohao.html") || str.contains("yhedit.html") || str.contains("yhview.html")) {
            return true;
        }
        return false;
    }

    public static void c(final Context context) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            d(context);
            return;
        }
        aho.a(new Runnable() {
            public final void run() {
                ajp.d(context);
            }
        });
        AMapLog.warning("paas.webview", "WebViewUtil", Log.getStackTraceString(new RuntimeException("clearSessionCookies must invoke in main thread!")));
    }

    static void d(Context context) {
        try {
            if (VERSION.SDK_INT >= 22) {
                CookieManager.getInstance().removeSessionCookies(null);
                CookieManager.getInstance().flush();
                return;
            }
            CookieSyncManager createInstance = CookieSyncManager.createInstance(context);
            createInstance.startSync();
            CookieManager.getInstance().removeSessionCookie();
            createInstance.stopSync();
            createInstance.sync();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("clearSessionCookies error: ");
            sb.append(e.getLocalizedMessage());
            AMapLog.error("paas.webview", "WebViewUtil", sb.toString());
        }
    }

    public static String a() {
        boolean z;
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("diu:");
        stringBuffer.append(NetworkParam.getDiu());
        stringBuffer.append(";");
        stringBuffer.append("tid:");
        stringBuffer.append(NetworkParam.getTaobaoID());
        stringBuffer.append(";");
        stringBuffer.append("div:");
        stringBuffer.append(NetworkParam.getDiv());
        stringBuffer.append(";");
        stringBuffer.append("networktype:");
        int b = aaw.b(AMapAppGlobal.getApplication());
        if (b <= 0 || b > 4) {
            b = 0;
        }
        stringBuffer.append(b);
        stringBuffer.append(";");
        DeviceInfo instance = DeviceInfo.getInstance(AMapAppGlobal.getApplication());
        stringBuffer.append("carrier:");
        stringBuffer.append(instance.getMcc());
        stringBuffer.append("-");
        stringBuffer.append(instance.getMnc());
        stringBuffer.append(";");
        stringBuffer.append("dibv:");
        stringBuffer.append(NetworkParam.getDibv());
        stringBuffer.append(";");
        stringBuffer.append("adiu:");
        stringBuffer.append(NetworkParam.getAdiu());
        stringBuffer.append(";");
        stringBuffer.append("dic:");
        stringBuffer.append(NetworkParam.getDic());
        do {
            String amapEncode = serverkey.amapEncode(stringBuffer.toString());
            a = amapEncode;
            String lowerCase = amapEncode.toLowerCase();
            z = lowerCase.contains("ipod") || lowerCase.contains("ipad") || lowerCase.contains("iphone");
            if (z) {
                stringBuffer.append(";");
                stringBuffer.append("p0:0");
                continue;
            }
        } while (z);
        return a;
    }
}
