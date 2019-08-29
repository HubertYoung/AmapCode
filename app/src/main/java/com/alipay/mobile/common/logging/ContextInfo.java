package com.alipay.mobile.common.logging;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.common.logging.helper.ClientIdHelper;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.common.logging.util.MpaasPropertiesUtil;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ContextInfo {
    private static final Map<String, String> a;
    private Context b;
    private Bundle c = new Bundle();
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t = "-";
    private String u = null;
    private long v;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("com.eg.android.AlipayGphone", "Android-container");
        a.put("com.eg.android.AlipayGphoneRC", "Android-container-RC");
    }

    public ContextInfo(Context context) {
        this.b = context;
        try {
            w();
            F(this.h);
            new ClientIdHelper();
            this.j = ClientIdHelper.a(context);
            this.i = z();
            this.k = A();
            this.l = B();
            this.o = C();
            this.r = D();
            this.s = E();
            this.t = y();
            this.u = x();
            b(LoggingSPCache.STORAGE_USERID, this.i);
            b(LoggingSPCache.STORAGE_CLIENTID, this.j);
            b("utdid", this.k);
        } catch (Throwable t2) {
            Log.e("ContextInfo", "ContextInfo", t2);
        }
    }

    public final String a() {
        return this.u;
    }

    public final String b() {
        return this.d;
    }

    public final String c() {
        return this.e;
    }

    public final String d() {
        return this.f;
    }

    public final String e() {
        return this.g;
    }

    public final String f() {
        return this.h;
    }

    public final String g() {
        if (LoggerFactory.getProcessInfo().isExtProcess() && this.i == null) {
            this.i = d(LoggingSPCache.STORAGE_USERID, "");
        }
        return this.i;
    }

    public final String h() {
        if (LoggerFactory.getProcessInfo().isExtProcess() && this.j == null) {
            this.j = d(LoggingSPCache.STORAGE_CLIENTID, "");
        }
        return this.j;
    }

    public final String i() {
        if (LoggerFactory.getProcessInfo().isExtProcess() && this.k == null) {
            this.k = d("utdid", "");
        }
        return this.k;
    }

    public final String j() {
        return this.l;
    }

    public final synchronized String k() {
        return this.m;
    }

    public final String l() {
        return this.n;
    }

    public final String m() {
        return this.o;
    }

    public final String n() {
        return this.r;
    }

    public final String o() {
        return this.s;
    }

    public final String p() {
        return this.p;
    }

    public final String q() {
        return this.q;
    }

    public final String r() {
        return this.t;
    }

    public final void a(String userSessionId) {
        if (!TextUtils.isEmpty(userSessionId)) {
            this.t = userSessionId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_USERSESSIONID, userSessionId);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_USERSESSIONID, userSessionId);
        }
    }

    public final void b(String channelId) {
        if (!TextUtils.isEmpty(channelId)) {
            this.d = channelId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_CHANNELID, channelId);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_CHANNELID, channelId);
        }
    }

    public final void c(String releaseType) {
        if (!TextUtils.isEmpty(releaseType)) {
            this.e = releaseType;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_RELEASETYPE, releaseType);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_RELEASETYPE, releaseType);
        }
    }

    public final void d(String releaseCode) {
        if (!TextUtils.isEmpty(releaseCode)) {
            this.f = releaseCode;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_RELEASECODE).append(this.h).toString(), releaseCode);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_RELEASECODE, releaseCode);
        }
    }

    public final void e(String productId) {
        if (!TextUtils.isEmpty(productId)) {
            this.g = productId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_PRODUCTID, productId);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_PRODUCTID, productId);
        }
    }

    public final void f(String productVersion) {
        if (!TextUtils.isEmpty(productVersion)) {
            this.h = productVersion;
        }
    }

    public final void g(String userId) {
        this.i = userId;
        LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_USERID, userId);
        a((Bundle) null, (String) LoggingSPCache.STORAGE_USERID, userId);
    }

    public final void h(String clientId) {
        if (!TextUtils.isEmpty(clientId)) {
            this.j = clientId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_CLIENTID, clientId);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_CLIENTID, clientId);
        }
    }

    public final void i(String deviceId) {
        if (!TextUtils.isEmpty(deviceId)) {
            this.k = deviceId;
            LoggingSPCache.getInstance().putStringApply("utdid", deviceId);
            a((Bundle) null, (String) "utdid", deviceId);
        }
    }

    public final void j(String language) {
        if (!TextUtils.isEmpty(language)) {
            this.l = language;
            LoggingSPCache.getInstance().putStringApply("language", language);
            a((Bundle) null, (String) "language", language);
        }
    }

    public final synchronized void s() {
        if (TextUtils.isEmpty(this.m) || Math.abs(System.currentTimeMillis() - this.v) > TimeUnit.MINUTES.toMillis(30)) {
            this.v = System.currentTimeMillis();
            this.m = UUID.randomUUID().toString();
        }
    }

    public final void k(String sourceId) {
        this.n = sourceId;
    }

    public final void l(String hotpatchVersion) {
        if (!TextUtils.isEmpty(hotpatchVersion)) {
            this.o = hotpatchVersion;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_HOTPATCHVERSION).append(this.h).toString(), hotpatchVersion);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_HOTPATCHVERSION, hotpatchVersion);
        }
    }

    public final void m(String bundleVersion) {
        if (!TextUtils.isEmpty(bundleVersion)) {
            this.r = bundleVersion;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_BUNDLEVERSION).append(this.h).toString(), bundleVersion);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_BUNDLEVERSION, bundleVersion);
        }
    }

    public final void n(String birdNestVersion) {
        if (!TextUtils.isEmpty(birdNestVersion)) {
            this.s = birdNestVersion;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_BIRDNESTVERSION).append(this.h).toString(), birdNestVersion);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_BIRDNESTVERSION, birdNestVersion);
        }
    }

    public final void o(String packageId) {
        if (!TextUtils.isEmpty(packageId)) {
            this.p = packageId;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_PACKAGEID).append(this.h).toString(), packageId);
            v();
            a((Bundle) null, (String) LoggingSPCache.STORAGE_PACKAGEID, packageId);
        }
    }

    public final void p(String apkUniqueId) {
        this.q = apkUniqueId;
    }

    public final void q(String logHost) {
        if (!TextUtils.isEmpty(logHost)) {
            this.u = logHost;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_LOGHOST, logHost);
            a((Bundle) null, (String) LoggingSPCache.STORAGE_LOGHOST, logHost);
        }
    }

    public final void r(String channelId) {
        if (!TextUtils.isEmpty(channelId)) {
            this.d = channelId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_CHANNELID, channelId);
            a(LoggingSPCache.STORAGE_CHANNELID, channelId);
        }
    }

    public final void s(String releaseType) {
        if (!TextUtils.isEmpty(releaseType)) {
            this.e = releaseType;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_RELEASETYPE, releaseType);
            a(LoggingSPCache.STORAGE_RELEASETYPE, releaseType);
        }
    }

    public final void t(String releaseCode) {
        if (!TextUtils.isEmpty(releaseCode)) {
            this.f = releaseCode;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_RELEASECODE).append(this.h).toString(), releaseCode);
            a(LoggingSPCache.STORAGE_RELEASECODE, releaseCode);
        }
    }

    public final void u(String productId) {
        if (!TextUtils.isEmpty(productId)) {
            this.g = productId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_PRODUCTID, productId);
            a(LoggingSPCache.STORAGE_PRODUCTID, productId);
        }
    }

    public final void v(String productVersion) {
        if (!TextUtils.isEmpty(productVersion)) {
            this.h = productVersion;
        }
    }

    public final void w(String userId) {
        if (!TextUtils.isEmpty(userId)) {
            this.i = userId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_USERID, userId);
            a(LoggingSPCache.STORAGE_USERID, userId);
        }
    }

    public final void x(String clientId) {
        if (!TextUtils.isEmpty(clientId)) {
            this.j = clientId;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_CLIENTID, clientId);
            a(LoggingSPCache.STORAGE_CLIENTID, clientId);
        }
    }

    public final void y(String deviceId) {
        if (!TextUtils.isEmpty(deviceId)) {
            this.k = deviceId;
            LoggingSPCache.getInstance().putStringApply("utdid", deviceId);
            a("utdid", deviceId);
        }
    }

    public final void z(String language) {
        if (!TextUtils.isEmpty(language)) {
            this.l = language;
            LoggingSPCache.getInstance().putStringApply("language", language);
            a("language", language);
        }
    }

    public final void A(String hotpatchVersion) {
        if (!TextUtils.isEmpty(hotpatchVersion)) {
            this.o = hotpatchVersion;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_HOTPATCHVERSION).append(this.h).toString(), hotpatchVersion);
            a(LoggingSPCache.STORAGE_HOTPATCHVERSION, hotpatchVersion);
        }
    }

    public final void B(String bundleVersion) {
        if (!TextUtils.isEmpty(bundleVersion)) {
            this.r = bundleVersion;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_BUNDLEVERSION).append(this.h).toString(), bundleVersion);
            a(LoggingSPCache.STORAGE_BUNDLEVERSION, bundleVersion);
        }
    }

    public final void C(String birdNestVersion) {
        if (!TextUtils.isEmpty(birdNestVersion)) {
            this.s = birdNestVersion;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_BIRDNESTVERSION).append(this.h).toString(), birdNestVersion);
            a(LoggingSPCache.STORAGE_BIRDNESTVERSION, birdNestVersion);
        }
    }

    public final void D(String packageId) {
        if (!TextUtils.isEmpty(packageId)) {
            this.p = packageId;
            LoggingSPCache.getInstance().putStringApply(new StringBuilder(LoggingSPCache.STORAGE_PACKAGEID).append(this.h).toString(), packageId);
            v();
            a(LoggingSPCache.STORAGE_PACKAGEID, packageId);
        }
    }

    public final void E(String logHost) {
        if (!TextUtils.isEmpty(logHost)) {
            this.u = logHost;
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_LOGHOST, logHost);
            a(LoggingSPCache.STORAGE_LOGHOST, logHost);
        }
    }

    private void a(String type, String value) {
        if (!TextUtils.isEmpty(type) && value != null) {
            this.c.putString(type, value);
            b(type, value);
            if (LoggerFactory.getProcessInfo().isPushProcess()) {
                b(null, type, value);
                c(null, type, value);
            }
        }
    }

    public final void t() {
        try {
            this.c.clear();
        } catch (Throwable th) {
        }
    }

    public final void u() {
        a(this.c, (String) null, (String) null);
        t();
    }

    private void a(Bundle extras, String type, String value) {
        if (LoggerFactory.getProcessInfo().isMainProcess()) {
            a(LogContext.PUSH_LOG_SERVICE_CLASS_NAME, extras, type, value);
            if (!LoggerFactory.getLogContext().isDisableToolsProcess()) {
                a(LogContext.TOOLS_SERVICE_CLASS_NAME, extras, type, value);
            }
        } else if (LoggerFactory.getProcessInfo().isPushProcess()) {
            if (!LoggerFactory.getLogContext().isDisableToolsProcess()) {
                a(LogContext.TOOLS_SERVICE_CLASS_NAME, extras, type, value);
            }
            b(type, value);
            b(extras, type, value);
            c(extras, type, value);
        } else if (!LoggerFactory.getProcessInfo().isToolsProcess() && !LoggerFactory.getProcessInfo().isExtProcess()) {
            Log.e("ContextInfo", "updateLogContext, error: unknown process " + LoggerFactory.getProcessInfo().getProcessAlias());
        }
    }

    private void a(String serviceClassName, Bundle extras, String extraType, String extraValue) {
        Intent intent = new Intent();
        intent.setClassName(this.b, serviceClassName);
        if (extras == null) {
            intent.setAction(this.b.getPackageName() + ".monitor.action.UPDATE_LOG_CONTEXT");
            intent.putExtra("type", extraType);
            intent.putExtra("value", extraValue);
        } else {
            intent.setAction(this.b.getPackageName() + ".monitor.action.UPDATE_LOG_CONTEXT_BATCH");
            try {
                intent.putExtras(extras);
            } catch (Throwable t2) {
                Log.w("ContextInfo", t2);
            }
        }
        try {
            intent.setPackage(this.b.getPackageName());
        } catch (Throwable th) {
        }
        try {
            if (this.b.startService(intent) == null) {
                Log.e("ContextInfo", "notifyOtherProcessToUpdateLogContext: start service occured error");
            }
        } catch (Throwable t3) {
            Log.e("ContextInfo", "notifyOtherProcessToUpdateLogContext: " + t3);
        }
    }

    private void b(Bundle extras, String extraType, String extraValue) {
        Map processMap = new HashMap();
        try {
            for (RunningAppProcessInfo process : ((ActivityManager) this.b.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                processMap.put(process.processName, process.processName);
            }
        } catch (Throwable t2) {
            Log.e("ContextInfo", "notifyLiteProcessToUpdateLogContext_1: " + t2);
        }
        for (int i2 = 1; i2 <= 5; i2++) {
            String liteProcessName = "com.alipay.mobile.common.logging.process.LogServiceInlite" + i2;
            if (processMap.containsKey(liteProcessName)) {
                Intent intent = new Intent();
                intent.setClassName(this.b, liteProcessName);
                if (extras == null) {
                    intent.setAction(this.b.getPackageName() + ".monitor.action.UPDATE_LOG_CONTEXT");
                    intent.putExtra("type", extraType);
                    intent.putExtra("value", extraValue);
                } else {
                    intent.setAction(this.b.getPackageName() + ".monitor.action.UPDATE_LOG_CONTEXT_BATCH");
                    try {
                        intent.putExtras(extras);
                    } catch (Throwable t3) {
                        Log.e("ContextInfo", "notifyLiteProcessToUpdateLogContext_2: " + t3);
                    }
                }
                try {
                    intent.setPackage(this.b.getPackageName());
                } catch (Throwable th) {
                }
                try {
                    if (this.b.startService(intent) == null) {
                        Log.e("ContextInfo", "notifyLiteProcessToUpdateLogContext: start service occured error");
                    }
                } catch (Throwable t4) {
                    Log.e("ContextInfo", "notifyLiteProcessToUpdateLogContext_3: " + t4);
                }
            } else if (extras == null) {
                a(new StringBuilder(ProcessInfo.ALIAS_LITE).append(i2).toString(), extraType, extraValue);
            } else {
                try {
                    for (String type : extras.keySet()) {
                        a(new StringBuilder(ProcessInfo.ALIAS_LITE).append(i2).toString(), type, extras.getString(type));
                    }
                } catch (Throwable t5) {
                    Log.e("ContextInfo", "notifyLiteProcessToUpdateLogContext_4: " + t5);
                }
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(android.os.Bundle r6, java.lang.String r7, java.lang.String r8) {
        /*
            r5 = this;
            com.alipay.mobile.common.logging.api.ProcessInfo r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x0013 }
            boolean r3 = r3.isExtProcessExist()     // Catch:{ Throwable -> 0x0013 }
            if (r3 == 0) goto L_0x000b
        L_0x000a:
            return
        L_0x000b:
            if (r6 != 0) goto L_0x001c
            java.lang.String r3 = "ext"
            r5.a(r3, r7, r8)     // Catch:{ Throwable -> 0x0013 }
            goto L_0x000a
        L_0x0013:
            r0 = move-exception
            java.lang.String r3 = "ContextInfo"
            java.lang.String r4 = "updateExtProcessLogContext"
            android.util.Log.e(r3, r4, r0)
            goto L_0x000a
        L_0x001c:
            java.util.Set r3 = r6.keySet()     // Catch:{ Throwable -> 0x003a }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x003a }
        L_0x0024:
            boolean r4 = r3.hasNext()     // Catch:{ Throwable -> 0x003a }
            if (r4 == 0) goto L_0x000a
            java.lang.Object r1 = r3.next()     // Catch:{ Throwable -> 0x003a }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x003a }
            java.lang.String r2 = r6.getString(r1)     // Catch:{ Throwable -> 0x003a }
            java.lang.String r4 = "ext"
            r5.a(r4, r1, r2)     // Catch:{ Throwable -> 0x003a }
            goto L_0x0024
        L_0x003a:
            r0 = move-exception
            java.lang.String r3 = "ContextInfo"
            java.lang.String r4 = "updateExtProcessLogContext"
            android.util.Log.e(r3, r4, r0)     // Catch:{ Throwable -> 0x0013 }
            goto L_0x000a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.ContextInfo.c(android.os.Bundle, java.lang.String, java.lang.String):void");
    }

    private void a(String aliasName, String type, String value) {
        if (!TextUtils.isEmpty(type) && value != null) {
            try {
                this.b.getSharedPreferences((this.b.getPackageName() + "-" + aliasName) + DjangoUtils.EXTENSION_SEPARATOR + LoggingSPCache.CACHE_FILE_NAME, 0).edit().putString(type, value).apply();
            } catch (Throwable e2) {
                Log.e("ContextInfo", "updateOtherProcessSP: " + aliasName + ", type: " + type, e2);
            }
        }
    }

    private void v() {
        if (TextUtils.isEmpty(this.p)) {
            this.q = this.p;
            return;
        }
        int index = this.p.lastIndexOf(45);
        if (index < 0) {
            this.q = null;
        } else {
            this.q = this.p.substring(index + 1);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x012c A[SYNTHETIC, Splitter:B:44:0x012c] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0131 A[SYNTHETIC, Splitter:B:47:0x0131] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x013a A[SYNTHETIC, Splitter:B:52:0x013a] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x013f A[SYNTHETIC, Splitter:B:55:0x013f] */
    /* JADX WARNING: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void F(java.lang.String r10) {
        /*
            r9 = this;
            r8 = 0
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()
            java.lang.String r6 = "channelId"
            java.lang.String r5 = r5.getString(r6, r8)
            r9.d = r5
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()
            java.lang.String r6 = "releaseType"
            java.lang.String r5 = r5.getString(r6, r8)
            r9.e = r5
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "releaseCode"
            r6.<init>(r7)
            java.lang.StringBuilder r6 = r6.append(r10)
            java.lang.String r6 = r6.toString()
            java.lang.String r5 = r5.getString(r6, r8)
            r9.f = r5
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "packageId"
            r6.<init>(r7)
            java.lang.StringBuilder r6 = r6.append(r10)
            java.lang.String r6 = r6.toString()
            java.lang.String r5 = r5.getString(r6, r8)
            r9.p = r5
            r9.v()
            java.lang.String r5 = r9.d
            if (r5 == 0) goto L_0x005e
            java.lang.String r5 = r9.e
            if (r5 == 0) goto L_0x005e
            java.lang.String r5 = r9.f
            if (r5 == 0) goto L_0x005e
            java.lang.String r5 = r9.p
            if (r5 != 0) goto L_0x0121
        L_0x005e:
            r2 = 0
            r0 = 0
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0122 }
            android.content.Context r5 = r9.b     // Catch:{ Throwable -> 0x0122 }
            android.content.res.Resources r5 = r5.getResources()     // Catch:{ Throwable -> 0x0122 }
            android.content.res.AssetManager r5 = r5.getAssets()     // Catch:{ Throwable -> 0x0122 }
            java.lang.String r6 = "channel.config"
            java.io.InputStream r5 = r5.open(r6)     // Catch:{ Throwable -> 0x0122 }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x0122 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0154, all -> 0x014d }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0154, all -> 0x014d }
            java.util.Properties r4 = new java.util.Properties     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r4.<init>()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r4.load(r1)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r5 = r9.d     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x00a1
            java.lang.String r5 = "channel_id"
            java.lang.String r5 = r4.getProperty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r9.d = r5     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r5 = r9.d     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x00a1
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r6 = "channelId"
            java.lang.String r7 = r9.d     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r5.putStringApply(r6, r7)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
        L_0x00a1:
            java.lang.String r5 = r9.e     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x00c0
            java.lang.String r5 = "release_type"
            java.lang.String r5 = r4.getProperty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r9.e = r5     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r5 = r9.e     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x00c0
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r6 = "releaseType"
            java.lang.String r7 = r9.e     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r5.putStringApply(r6, r7)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
        L_0x00c0:
            java.lang.String r5 = r9.f     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x00ec
            java.lang.String r5 = "release_version"
            java.lang.String r5 = r4.getProperty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r9.f = r5     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r5 = r9.f     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x00ec
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r7 = "releaseCode"
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r7 = r9.f     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r5.putStringApply(r6, r7)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
        L_0x00ec:
            java.lang.String r5 = r9.p     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x011b
            java.lang.String r5 = "package_id"
            java.lang.String r5 = r4.getProperty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r9.p = r5     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r5 = r9.p     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            if (r5 != 0) goto L_0x0118
            com.alipay.mobile.common.logging.util.LoggingSPCache r5 = com.alipay.mobile.common.logging.util.LoggingSPCache.getInstance()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r7 = "packageId"
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            java.lang.String r7 = r9.p     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
            r5.putStringApply(r6, r7)     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
        L_0x0118:
            r9.v()     // Catch:{ Throwable -> 0x0157, all -> 0x0150 }
        L_0x011b:
            r1.close()     // Catch:{ Throwable -> 0x0143 }
        L_0x011e:
            r3.close()     // Catch:{ Throwable -> 0x0145 }
        L_0x0121:
            return
        L_0x0122:
            r5 = move-exception
        L_0x0123:
            java.lang.String r5 = "ContextInfo"
            java.lang.String r6 = "read channel.config fail"
            android.util.Log.w(r5, r6)     // Catch:{ all -> 0x0137 }
            if (r0 == 0) goto L_0x012f
            r0.close()     // Catch:{ Throwable -> 0x0147 }
        L_0x012f:
            if (r2 == 0) goto L_0x0121
            r2.close()     // Catch:{ Throwable -> 0x0135 }
            goto L_0x0121
        L_0x0135:
            r5 = move-exception
            goto L_0x0121
        L_0x0137:
            r5 = move-exception
        L_0x0138:
            if (r0 == 0) goto L_0x013d
            r0.close()     // Catch:{ Throwable -> 0x0149 }
        L_0x013d:
            if (r2 == 0) goto L_0x0142
            r2.close()     // Catch:{ Throwable -> 0x014b }
        L_0x0142:
            throw r5
        L_0x0143:
            r5 = move-exception
            goto L_0x011e
        L_0x0145:
            r5 = move-exception
            goto L_0x0121
        L_0x0147:
            r5 = move-exception
            goto L_0x012f
        L_0x0149:
            r6 = move-exception
            goto L_0x013d
        L_0x014b:
            r6 = move-exception
            goto L_0x0142
        L_0x014d:
            r5 = move-exception
            r2 = r3
            goto L_0x0138
        L_0x0150:
            r5 = move-exception
            r0 = r1
            r2 = r3
            goto L_0x0138
        L_0x0154:
            r5 = move-exception
            r2 = r3
            goto L_0x0123
        L_0x0157:
            r5 = move-exception
            r0 = r1
            r2 = r3
            goto L_0x0123
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.ContextInfo.F(java.lang.String):void");
    }

    private void w() {
        this.g = LoggingSPCache.getInstance().getString(LoggingSPCache.STORAGE_PRODUCTID, null);
        if (this.g == null) {
            this.g = MpaasPropertiesUtil.getKeyFromManifest(this.b, "persistProductId");
        }
        if (this.g == null) {
            ApplicationInfo appInfo = null;
            try {
                appInfo = this.b.getPackageManager().getApplicationInfo(this.b.getPackageName(), 128);
            } catch (Throwable th) {
            }
            String appKey = null;
            String workspace = null;
            boolean mPaaSPropExists = false;
            if (!(appInfo == null || appInfo.metaData == null)) {
                Object appKeyObj = appInfo.metaData.get("appkey");
                if (appKeyObj != null) {
                    appKey = appKeyObj.toString();
                } else {
                    appKey = null;
                }
                workspace = appInfo.metaData.getString("workspaceId");
                if (!TextUtils.isEmpty(appKey)) {
                    String workSpaceId = MpaasPropertiesUtil.getWorkSpaceId(this.b);
                    if (workSpaceId != null) {
                        mPaaSPropExists = true;
                        workspace = workSpaceId;
                    }
                }
                Log.i("ContextInfo", "appKey:" + appKey + ", workspace:" + workspace);
            }
            if (appKey != null) {
                this.g = appKey;
                if (workspace != null && workspace.length() > 1) {
                    if (workspace.startsWith("0") || mPaaSPropExists) {
                        this.g += "-" + workspace;
                    } else {
                        this.g += "-" + workspace.substring(1);
                    }
                }
            }
        }
        if (this.g == null) {
            this.g = a.get(this.b.getPackageName());
        }
        if (this.h == null) {
            PackageInfo packageInfo = null;
            try {
                packageInfo = this.b.getPackageManager().getPackageInfo(this.b.getPackageName(), 0);
            } catch (Throwable th2) {
            }
            if (packageInfo != null) {
                this.h = packageInfo.versionName;
            }
        }
    }

    private static String x() {
        return LoggingSPCache.getInstance().getString(LoggingSPCache.STORAGE_LOGHOST, null);
    }

    private static String y() {
        return LoggingSPCache.getInstance().getString(LoggingSPCache.STORAGE_USERSESSIONID, "-");
    }

    private static String z() {
        return LoggingSPCache.getInstance().getString(LoggingSPCache.STORAGE_USERID, null);
    }

    private static String A() {
        return LoggingSPCache.getInstance().getString("utdid", null);
    }

    private static String B() {
        return LoggingSPCache.getInstance().getString("language", null);
    }

    private String C() {
        return LoggingSPCache.getInstance().getString(new StringBuilder(LoggingSPCache.STORAGE_HOTPATCHVERSION).append(this.h).toString(), "0");
    }

    private String D() {
        return LoggingSPCache.getInstance().getString(new StringBuilder(LoggingSPCache.STORAGE_BUNDLEVERSION).append(this.h).toString(), "0");
    }

    private String E() {
        return LoggingSPCache.getInstance().getString(new StringBuilder(LoggingSPCache.STORAGE_BIRDNESTVERSION).append(this.h).toString(), "0");
    }

    private void b(String type, String value) {
        if (!LoggerFactory.getProcessInfo().isPushProcess() || TextUtils.isEmpty(type) || value == null) {
            return;
        }
        if (LoggingSPCache.STORAGE_USERID.equals(type) || LoggingSPCache.STORAGE_CLIENTID.equals(type) || "utdid".equals(type)) {
            c(type, value);
        }
    }

    private void c(String type, String value) {
        try {
            this.b.getSharedPreferences("SharedInfoForMultiProc", 4).edit().putString("key_" + type, value).apply();
        } catch (Throwable t2) {
            Log.e("ContextInfo", "setSharedInfoForMultiProc", t2);
        }
    }

    private String d(String type, String defVal) {
        try {
            return this.b.getSharedPreferences("SharedInfoForMultiProc", 4).getString("key_" + type, defVal);
        } catch (Throwable t2) {
            Log.e("ContextInfo", "getSharedInfoFromMultiProc", t2);
            return defVal;
        }
    }
}
