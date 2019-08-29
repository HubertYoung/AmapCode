package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.xiaomi.channel.commonutils.android.d;
import com.xiaomi.push.service.module.PushChannelRegion;
import com.xiaomi.smack.b;

public class t {
    private static s a;
    private static a b;

    public interface a {
        void a();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ac, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.xiaomi.push.service.s a(android.content.Context r13) {
        /*
            java.lang.Class<com.xiaomi.push.service.t> r0 = com.xiaomi.push.service.t.class
            monitor-enter(r0)
            com.xiaomi.push.service.s r1 = a     // Catch:{ all -> 0x00ad }
            if (r1 == 0) goto L_0x000b
            com.xiaomi.push.service.s r13 = a     // Catch:{ all -> 0x00ad }
            monitor-exit(r0)
            return r13
        L_0x000b:
            java.lang.String r1 = "mipush_account"
            r2 = 0
            android.content.SharedPreferences r1 = r13.getSharedPreferences(r1, r2)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "uuid"
            r3 = 0
            java.lang.String r5 = r1.getString(r2, r3)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "token"
            java.lang.String r6 = r1.getString(r2, r3)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "security"
            java.lang.String r7 = r1.getString(r2, r3)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "app_id"
            java.lang.String r8 = r1.getString(r2, r3)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "app_token"
            java.lang.String r9 = r1.getString(r2, r3)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "package_name"
            java.lang.String r10 = r1.getString(r2, r3)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = "device_id"
            java.lang.String r2 = r1.getString(r2, r3)     // Catch:{ all -> 0x00ad }
            java.lang.String r4 = "env_type"
            r11 = 1
            int r11 = r1.getInt(r4, r11)     // Catch:{ all -> 0x00ad }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x00ad }
            if (r4 != 0) goto L_0x0063
            java.lang.String r4 = "a-"
            boolean r4 = r2.startsWith(r4)     // Catch:{ all -> 0x00ad }
            if (r4 == 0) goto L_0x0063
            java.lang.String r2 = com.xiaomi.channel.commonutils.android.d.h(r13)     // Catch:{ all -> 0x00ad }
            android.content.SharedPreferences$Editor r1 = r1.edit()     // Catch:{ all -> 0x00ad }
            java.lang.String r4 = "device_id"
            android.content.SharedPreferences$Editor r1 = r1.putString(r4, r2)     // Catch:{ all -> 0x00ad }
            r1.commit()     // Catch:{ all -> 0x00ad }
        L_0x0063:
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x00ad }
            if (r1 != 0) goto L_0x00ab
            boolean r1 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x00ad }
            if (r1 != 0) goto L_0x00ab
            boolean r1 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x00ad }
            if (r1 != 0) goto L_0x00ab
            java.lang.String r1 = com.xiaomi.channel.commonutils.android.d.h(r13)     // Catch:{ all -> 0x00ad }
            java.lang.String r4 = "com.xiaomi.xmsf"
            java.lang.String r12 = r13.getPackageName()     // Catch:{ all -> 0x00ad }
            boolean r4 = r4.equals(r12)     // Catch:{ all -> 0x00ad }
            if (r4 != 0) goto L_0x00a1
            boolean r4 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x00ad }
            if (r4 != 0) goto L_0x00a1
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x00ad }
            if (r4 != 0) goto L_0x00a1
            boolean r1 = r2.equals(r1)     // Catch:{ all -> 0x00ad }
            if (r1 != 0) goto L_0x00a1
            java.lang.String r1 = "erase the old account."
            com.xiaomi.channel.commonutils.logger.b.d(r1)     // Catch:{ all -> 0x00ad }
            c(r13)     // Catch:{ all -> 0x00ad }
            monitor-exit(r0)
            return r3
        L_0x00a1:
            com.xiaomi.push.service.s r13 = new com.xiaomi.push.service.s     // Catch:{ all -> 0x00ad }
            r4 = r13
            r4.<init>(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x00ad }
            a = r13     // Catch:{ all -> 0x00ad }
            monitor-exit(r0)
            return r13
        L_0x00ab:
            monitor-exit(r0)
            return r3
        L_0x00ad:
            r13 = move-exception
            monitor-exit(r0)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.t.a(android.content.Context):com.xiaomi.push.service.s");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0183, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.xiaomi.push.service.s a(android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            java.lang.Class<com.xiaomi.push.service.t> r0 = com.xiaomi.push.service.t.class
            monitor-enter(r0)
            java.util.TreeMap r1 = new java.util.TreeMap     // Catch:{ all -> 0x0184 }
            r1.<init>()     // Catch:{ all -> 0x0184 }
            java.lang.String r2 = "devid"
            r3 = 0
            java.lang.String r3 = com.xiaomi.channel.commonutils.android.d.a(r9, r3)     // Catch:{ all -> 0x0184 }
            r1.put(r2, r3)     // Catch:{ all -> 0x0184 }
            boolean r2 = d(r9)     // Catch:{ all -> 0x0184 }
            if (r2 == 0) goto L_0x001a
            java.lang.String r11 = "1000271"
        L_0x001a:
            r5 = r11
            boolean r11 = d(r9)     // Catch:{ all -> 0x0184 }
            if (r11 == 0) goto L_0x0023
            java.lang.String r12 = "420100086271"
        L_0x0023:
            r6 = r12
            boolean r11 = d(r9)     // Catch:{ all -> 0x0184 }
            if (r11 == 0) goto L_0x002c
            java.lang.String r10 = "com.xiaomi.xmsf"
        L_0x002c:
            r7 = r10
            java.lang.String r10 = "appid"
            r1.put(r10, r5)     // Catch:{ all -> 0x0184 }
            java.lang.String r10 = "apptoken"
            r1.put(r10, r6)     // Catch:{ all -> 0x0184 }
            r10 = 0
            android.content.pm.PackageManager r11 = r9.getPackageManager()     // Catch:{ Exception -> 0x0043 }
            r12 = 16384(0x4000, float:2.2959E-41)
            android.content.pm.PackageInfo r11 = r11.getPackageInfo(r7, r12)     // Catch:{ Exception -> 0x0043 }
            goto L_0x0048
        L_0x0043:
            r11 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r11)     // Catch:{ all -> 0x0184 }
            r11 = r10
        L_0x0048:
            java.lang.String r12 = "appversion"
            if (r11 == 0) goto L_0x0053
            int r11 = r11.versionCode     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch:{ all -> 0x0184 }
            goto L_0x0055
        L_0x0053:
            java.lang.String r11 = "0"
        L_0x0055:
            r1.put(r12, r11)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = "sdkversion"
            r12 = 30602(0x778a, float:4.2883E-41)
            java.lang.String r12 = java.lang.Integer.toString(r12)     // Catch:{ all -> 0x0184 }
            r1.put(r11, r12)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = "packagename"
            r1.put(r11, r7)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = "model"
            java.lang.String r12 = android.os.Build.MODEL     // Catch:{ all -> 0x0184 }
            r1.put(r11, r12)     // Catch:{ all -> 0x0184 }
            boolean r11 = com.xiaomi.channel.commonutils.android.f.e()     // Catch:{ all -> 0x0184 }
            if (r11 == 0) goto L_0x00c3
            java.lang.String r11 = ""
            java.lang.String r12 = com.xiaomi.channel.commonutils.android.d.b(r9)     // Catch:{ all -> 0x0184 }
            boolean r2 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x0184 }
            if (r2 != 0) goto L_0x0094
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0184 }
            r2.<init>()     // Catch:{ all -> 0x0184 }
            r2.append(r11)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = com.xiaomi.channel.commonutils.string.d.a(r12)     // Catch:{ all -> 0x0184 }
            r2.append(r11)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = r2.toString()     // Catch:{ all -> 0x0184 }
        L_0x0094:
            java.lang.String r12 = com.xiaomi.channel.commonutils.android.d.d(r9)     // Catch:{ all -> 0x0184 }
            boolean r2 = android.text.TextUtils.isEmpty(r11)     // Catch:{ all -> 0x0184 }
            if (r2 != 0) goto L_0x00b8
            boolean r2 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x0184 }
            if (r2 != 0) goto L_0x00b8
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0184 }
            r2.<init>()     // Catch:{ all -> 0x0184 }
            r2.append(r11)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = ","
            r2.append(r11)     // Catch:{ all -> 0x0184 }
            r2.append(r12)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = r2.toString()     // Catch:{ all -> 0x0184 }
        L_0x00b8:
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ all -> 0x0184 }
            if (r12 != 0) goto L_0x00c3
            java.lang.String r12 = "imei_md5"
            r1.put(r12, r11)     // Catch:{ all -> 0x0184 }
        L_0x00c3:
            java.lang.String r11 = "os"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0184 }
            r12.<init>()     // Catch:{ all -> 0x0184 }
            java.lang.String r2 = android.os.Build.VERSION.RELEASE     // Catch:{ all -> 0x0184 }
            r12.append(r2)     // Catch:{ all -> 0x0184 }
            java.lang.String r2 = "-"
            r12.append(r2)     // Catch:{ all -> 0x0184 }
            java.lang.String r2 = android.os.Build.VERSION.INCREMENTAL     // Catch:{ all -> 0x0184 }
            r12.append(r2)     // Catch:{ all -> 0x0184 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0184 }
            r1.put(r11, r12)     // Catch:{ all -> 0x0184 }
            int r11 = com.xiaomi.channel.commonutils.android.d.b()     // Catch:{ all -> 0x0184 }
            if (r11 < 0) goto L_0x00ef
            java.lang.String r12 = "space_id"
            java.lang.String r11 = java.lang.Integer.toString(r11)     // Catch:{ all -> 0x0184 }
            r1.put(r12, r11)     // Catch:{ all -> 0x0184 }
        L_0x00ef:
            java.lang.String r11 = com.xiaomi.channel.commonutils.android.d.j(r9)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = com.xiaomi.channel.commonutils.string.d.a(r11)     // Catch:{ all -> 0x0184 }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ all -> 0x0184 }
            if (r12 != 0) goto L_0x0102
            java.lang.String r12 = "mac_address"
            r1.put(r12, r11)     // Catch:{ all -> 0x0184 }
        L_0x0102:
            java.lang.String r11 = "android_id"
            java.lang.String r12 = com.xiaomi.channel.commonutils.android.d.a(r9)     // Catch:{ all -> 0x0184 }
            r1.put(r11, r12)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = b(r9)     // Catch:{ all -> 0x0184 }
            com.xiaomi.channel.commonutils.network.b r11 = com.xiaomi.channel.commonutils.network.d.a(r9, r11, r1)     // Catch:{ all -> 0x0184 }
            java.lang.String r12 = ""
            if (r11 == 0) goto L_0x011b
            java.lang.String r12 = r11.a()     // Catch:{ all -> 0x0184 }
        L_0x011b:
            boolean r11 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x0184 }
            if (r11 != 0) goto L_0x0182
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ all -> 0x0184 }
            r11.<init>(r12)     // Catch:{ all -> 0x0184 }
            java.lang.String r1 = "code"
            int r1 = r11.getInt(r1)     // Catch:{ all -> 0x0184 }
            if (r1 != 0) goto L_0x0170
            java.lang.String r10 = "data"
            org.json.JSONObject r10 = r11.getJSONObject(r10)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = "ssecurity"
            java.lang.String r4 = r10.getString(r11)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = "token"
            java.lang.String r3 = r10.getString(r11)     // Catch:{ all -> 0x0184 }
            java.lang.String r11 = "userId"
            java.lang.String r10 = r10.getString(r11)     // Catch:{ all -> 0x0184 }
            com.xiaomi.push.service.s r11 = new com.xiaomi.push.service.s     // Catch:{ all -> 0x0184 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0184 }
            r12.<init>()     // Catch:{ all -> 0x0184 }
            r12.append(r10)     // Catch:{ all -> 0x0184 }
            java.lang.String r10 = "@xiaomi.com/an"
            r12.append(r10)     // Catch:{ all -> 0x0184 }
            r10 = 6
            java.lang.String r10 = com.xiaomi.channel.commonutils.string.d.a(r10)     // Catch:{ all -> 0x0184 }
            r12.append(r10)     // Catch:{ all -> 0x0184 }
            java.lang.String r2 = r12.toString()     // Catch:{ all -> 0x0184 }
            int r8 = com.xiaomi.channel.commonutils.misc.a.c()     // Catch:{ all -> 0x0184 }
            r1 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0184 }
            a(r9, r11)     // Catch:{ all -> 0x0184 }
            a = r11     // Catch:{ all -> 0x0184 }
            monitor-exit(r0)
            return r11
        L_0x0170:
            java.lang.String r1 = "code"
            int r1 = r11.getInt(r1)     // Catch:{ all -> 0x0184 }
            java.lang.String r2 = "description"
            java.lang.String r11 = r11.optString(r2)     // Catch:{ all -> 0x0184 }
            com.xiaomi.push.service.w.a(r9, r1, r11)     // Catch:{ all -> 0x0184 }
            com.xiaomi.channel.commonutils.logger.b.a(r12)     // Catch:{ all -> 0x0184 }
        L_0x0182:
            monitor-exit(r0)
            return r10
        L_0x0184:
            r9 = move-exception
            monitor-exit(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.t.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String):com.xiaomi.push.service.s");
    }

    public static void a() {
        if (b != null) {
            b.a();
        }
    }

    public static void a(Context context, s sVar) {
        Editor edit = context.getSharedPreferences("mipush_account", 0).edit();
        edit.putString("uuid", sVar.a);
        edit.putString("security", sVar.c);
        edit.putString("token", sVar.b);
        edit.putString("app_id", sVar.d);
        edit.putString("package_name", sVar.f);
        edit.putString("app_token", sVar.e);
        edit.putString("device_id", d.h(context));
        edit.putInt("env_type", sVar.g);
        edit.commit();
        a();
    }

    public static String b(Context context) {
        StringBuilder sb;
        String str;
        String a2 = a.a(context).a();
        if (com.xiaomi.channel.commonutils.misc.a.b()) {
            sb = new StringBuilder(AjxHttpLoader.DOMAIN_HTTP);
            sb.append(b.c);
            str = ":9085/pass/register";
        } else if (PushChannelRegion.Global.name().equals(a.a(context).a())) {
            return "https://register.xmpush.global.xiaomi.com/pass/register";
        } else {
            if (PushChannelRegion.Europe.name().equals(a2)) {
                return "https://fr.register.xmpush.global.xiaomi.com/pass/register";
            }
            sb = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
            sb.append(com.xiaomi.channel.commonutils.misc.a.a() ? "sandbox.xmpush.xiaomi.com" : "register.xmpush.xiaomi.com");
            str = "/pass/register";
        }
        sb.append(str);
        return sb.toString();
    }

    public static void c(Context context) {
        context.getSharedPreferences("mipush_account", 0).edit().clear().commit();
        a = null;
        a();
    }

    private static boolean d(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }
}
