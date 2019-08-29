package com.alipay.android.phone.inside.common.info;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import com.alipay.android.phone.inside.common.util.CommonUtil;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import java.util.Map;

public class AppInfo {
    static String a = "productName";
    static String b = "productVersion";
    static String c = "productID";
    private static AppInfo d;
    private final Context e = CommonUtil.a();
    private ActivityManager f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private int m;
    private SharedPreferences n;

    public static String d() {
        return "23699722";
    }

    private AppInfo() {
        try {
            String packageName = this.e.getPackageName();
            LoggerFactory.f().a((String) "AppInfo", "getPackageName ".concat(String.valueOf(packageName)));
            Context context = this.e;
            StringBuilder sb = new StringBuilder();
            sb.append(packageName);
            sb.append("_config");
            this.n = context.getSharedPreferences(sb.toString(), 0);
            this.l = (String) this.e.getPackageManager().getApplicationLabel(this.e.getPackageManager().getApplicationInfo(this.e.getPackageName(), 16512));
            this.f = (ActivityManager) this.e.getSystemService(WidgetType.ACTIVITY);
            this.m = Process.myPid();
            this.j = BehavorReporter.PROVIDE_BY_ALIPAY;
            this.k = "";
        } catch (Throwable th) {
            LoggerFactory.e().a((String) IPreloadManager.SIR_COMMON_TYPE, (String) "AppInfoInitEx", th);
        }
    }

    public static AppInfo a() {
        if (d == null) {
            d = new AppInfo();
        }
        return d;
    }

    public static synchronized AppInfo b() {
        AppInfo a2;
        synchronized (AppInfo.class) {
            a2 = a();
        }
        return a2;
    }

    public final String c() {
        return this.k;
    }

    public final void a(Map<String, String> map) {
        if (map == null) {
            LoggerFactory.f().b((String) "inside", (String) "configMap is null");
            return;
        }
        this.h = map.containsKey(b) ? map.get(b) : "";
        this.g = map.containsKey(c) ? map.get(c) : "";
        this.i = map.containsKey(a) ? map.get(a) : "";
        TraceLogger f2 = LoggerFactory.f();
        StringBuilder sb = new StringBuilder();
        sb.append(this.h);
        sb.append(", ");
        sb.append(this.g);
        sb.append(", ");
        sb.append(this.i);
        f2.b((String) "inside", sb.toString());
    }

    public final String e() {
        return this.g;
    }

    @Deprecated
    public final String f() {
        return this.h;
    }

    public final String g() {
        return this.h;
    }

    public final String h() {
        return this.j;
    }
}
