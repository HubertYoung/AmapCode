package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: faw reason: default package */
/* compiled from: PushPackageUtils */
public final class faw {
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005d, code lost:
        if (r0.f != false) goto L_0x0061;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.ezt a(android.content.Context r13) {
        /*
            android.content.Context r13 = r13.getApplicationContext()
            ezt r0 = d(r13)
            if (r0 == 0) goto L_0x001a
            java.lang.String r13 = "PushPackageUtils"
            java.lang.String r1 = "get system push info :"
            java.lang.String r2 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r2)
            defpackage.fat.d(r13, r1)
            return r0
        L_0x001a:
            java.util.List r1 = e(r13)
            java.lang.String r2 = r13.getPackageName()
            ezt r2 = b(r13, r2)
            int r3 = r1.size()
            if (r3 > 0) goto L_0x003c
            if (r2 == 0) goto L_0x0033
            boolean r1 = r2.f
            if (r1 == 0) goto L_0x0033
            r0 = r2
        L_0x0033:
            java.lang.String r1 = "PushPackageUtils"
            java.lang.String r2 = "findAllPushPackages error: find no package!"
            defpackage.fat.a(r1, r2)
            goto L_0x00ec
        L_0x003c:
            fbc r0 = defpackage.fbc.b(r13)
            java.lang.String r3 = "com.vivo.push.cur_pkg"
            r4 = 0
            java.lang.String r0 = r0.a(r3, r4)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L_0x0060
            java.lang.String r3 = "com.vivo.pushservice.action.METHOD"
            boolean r3 = a(r13, r0, r3)
            if (r3 == 0) goto L_0x0060
            ezt r0 = b(r13, r0)
            if (r0 == 0) goto L_0x0060
            boolean r3 = r0.f
            if (r3 == 0) goto L_0x0060
            goto L_0x0061
        L_0x0060:
            r0 = r4
        L_0x0061:
            if (r2 == 0) goto L_0x0068
            boolean r3 = r2.f
            if (r3 == 0) goto L_0x0068
            goto L_0x0069
        L_0x0068:
            r2 = r4
        L_0x0069:
            if (r0 == 0) goto L_0x006c
            goto L_0x006d
        L_0x006c:
            r0 = r4
        L_0x006d:
            if (r2 == 0) goto L_0x008f
            if (r0 == 0) goto L_0x008e
            boolean r3 = r2.e
            if (r3 == 0) goto L_0x0082
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x008f
            long r5 = r2.b
            long r7 = r0.b
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 <= 0) goto L_0x008f
            goto L_0x008e
        L_0x0082:
            boolean r3 = r0.e
            if (r3 != 0) goto L_0x008e
            long r5 = r2.b
            long r7 = r0.b
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 <= 0) goto L_0x008f
        L_0x008e:
            r0 = r2
        L_0x008f:
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            if (r0 == 0) goto L_0x009f
            boolean r3 = r0.e
            if (r3 == 0) goto L_0x009b
            goto L_0x00a0
        L_0x009b:
            r12 = r4
            r4 = r0
            r0 = r12
            goto L_0x00a0
        L_0x009f:
            r0 = r4
        L_0x00a0:
            int r3 = r1.size()
            r5 = 0
            r12 = r4
            r4 = r0
            r0 = r12
        L_0x00a8:
            if (r5 >= r3) goto L_0x00e1
            java.lang.Object r6 = r1.get(r5)
            java.lang.String r6 = (java.lang.String) r6
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x00de
            ezt r7 = b(r13, r6)
            if (r7 == 0) goto L_0x00de
            r2.put(r6, r7)
            boolean r6 = r7.f
            if (r6 == 0) goto L_0x00de
            boolean r6 = r7.e
            if (r6 == 0) goto L_0x00d3
            if (r4 == 0) goto L_0x00d1
            long r8 = r7.b
            long r10 = r4.b
            int r6 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r6 <= 0) goto L_0x00de
        L_0x00d1:
            r4 = r7
            goto L_0x00de
        L_0x00d3:
            if (r0 == 0) goto L_0x00dd
            long r8 = r7.b
            long r10 = r0.b
            int r6 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r6 <= 0) goto L_0x00de
        L_0x00dd:
            r0 = r7
        L_0x00de:
            int r5 = r5 + 1
            goto L_0x00a8
        L_0x00e1:
            if (r0 == 0) goto L_0x00e4
            goto L_0x00ec
        L_0x00e4:
            java.lang.String r0 = "PushPackageUtils"
            java.lang.String r1 = "findSuitablePushPackage, all push app in balck list."
            defpackage.fat.d(r0, r1)
            r0 = r4
        L_0x00ec:
            if (r0 == 0) goto L_0x0138
            boolean r1 = r0.e
            if (r1 == 0) goto L_0x0115
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "查找最优包为:"
            r1.<init>(r2)
            java.lang.String r2 = r0.a
            r1.append(r2)
            java.lang.String r2 = "("
            r1.append(r2)
            long r2 = r0.b
            r1.append(r2)
            java.lang.String r2 = ", Black)"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            defpackage.fat.a(r13, r1)
            goto L_0x013d
        L_0x0115:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "查找最优包为:"
            r1.<init>(r2)
            java.lang.String r2 = r0.a
            r1.append(r2)
            java.lang.String r2 = "("
            r1.append(r2)
            long r2 = r0.b
            r1.append(r2)
            java.lang.String r2 = ")"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            defpackage.fat.a(r13, r1)
            goto L_0x013d
        L_0x0138:
            java.lang.String r1 = "查找最优包为空!"
            defpackage.fat.b(r13, r1)
        L_0x013d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.faw.a(android.content.Context):ezt");
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x00e1 A[SYNTHETIC, Splitter:B:62:0x00e1] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00e8 A[SYNTHETIC, Splitter:B:67:0x00e8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(android.content.Context r8) {
        /*
            r0 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch:{ Exception -> 0x00d4, all -> 0x00cf }
            android.net.Uri r2 = defpackage.fbi.a     // Catch:{ Exception -> 0x00d4, all -> 0x00cf }
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x00d4, all -> 0x00cf }
            if (r8 != 0) goto L_0x002d
            java.lang.String r1 = "PushPackageUtils"
            java.lang.String r2 = "cursor is null"
            defpackage.fat.a(r1, r2)     // Catch:{ Exception -> 0x0027 }
            if (r8 == 0) goto L_0x0026
            r8.close()     // Catch:{ Exception -> 0x001e }
            goto L_0x0026
        L_0x001e:
            r8 = move-exception
            java.lang.String r1 = "PushPackageUtils"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r8)
        L_0x0026:
            return r0
        L_0x0027:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x00d8
        L_0x002d:
            r1 = 0
            r1 = r0
            r2 = 0
        L_0x0030:
            boolean r3 = r8.moveToNext()     // Catch:{ Exception -> 0x00cd }
            if (r3 == 0) goto L_0x0098
            java.lang.String r3 = "pushPkgName"
            java.lang.String r4 = "name"
            int r4 = r8.getColumnIndex(r4)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r4 = r8.getString(r4)     // Catch:{ Exception -> 0x00cd }
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00cd }
            if (r3 == 0) goto L_0x0068
            java.lang.String r3 = "value"
            int r3 = r8.getColumnIndex(r3)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r3 = r8.getString(r3)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r1 = "PushPackageUtils"
            java.lang.String r4 = "get system app is "
            java.lang.String r5 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0063 }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ Exception -> 0x0063 }
            defpackage.fat.d(r1, r4)     // Catch:{ Exception -> 0x0063 }
            r1 = r3
            goto L_0x0030
        L_0x0063:
            r1 = move-exception
            r0 = r1
            r1 = r3
            goto L_0x00d8
        L_0x0068:
            java.lang.String r3 = "pushEnable"
            java.lang.String r4 = "name"
            int r4 = r8.getColumnIndex(r4)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r4 = r8.getString(r4)     // Catch:{ Exception -> 0x00cd }
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00cd }
            if (r3 == 0) goto L_0x0030
            java.lang.String r2 = "value"
            int r2 = r8.getColumnIndex(r2)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r2 = r8.getString(r2)     // Catch:{ Exception -> 0x00cd }
            boolean r2 = java.lang.Boolean.parseBoolean(r2)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r3 = "PushPackageUtils"
            java.lang.String r4 = "get system app isSystemOpen is "
            java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x00cd }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ Exception -> 0x00cd }
            defpackage.fat.d(r3, r4)     // Catch:{ Exception -> 0x00cd }
            goto L_0x0030
        L_0x0098:
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x00cd }
            if (r3 == 0) goto L_0x00ad
            if (r8 == 0) goto L_0x00ac
            r8.close()     // Catch:{ Exception -> 0x00a4 }
            goto L_0x00ac
        L_0x00a4:
            r8 = move-exception
            java.lang.String r1 = "PushPackageUtils"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r8)
        L_0x00ac:
            return r0
        L_0x00ad:
            if (r2 != 0) goto L_0x00be
            if (r8 == 0) goto L_0x00bd
            r8.close()     // Catch:{ Exception -> 0x00b5 }
            goto L_0x00bd
        L_0x00b5:
            r8 = move-exception
            java.lang.String r1 = "PushPackageUtils"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r8)
        L_0x00bd:
            return r0
        L_0x00be:
            if (r8 == 0) goto L_0x00e4
            r8.close()     // Catch:{ Exception -> 0x00c4 }
            goto L_0x00e4
        L_0x00c4:
            r8 = move-exception
            java.lang.String r0 = "PushPackageUtils"
            java.lang.String r2 = "close"
            defpackage.fat.a(r0, r2, r8)
            goto L_0x00e4
        L_0x00cd:
            r0 = move-exception
            goto L_0x00d8
        L_0x00cf:
            r8 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x00e6
        L_0x00d4:
            r1 = move-exception
            r8 = r0
            r0 = r1
            r1 = r8
        L_0x00d8:
            java.lang.String r2 = "PushPackageUtils"
            java.lang.String r3 = "getSystemPush"
            defpackage.fat.a(r2, r3, r0)     // Catch:{ all -> 0x00e5 }
            if (r8 == 0) goto L_0x00e4
            r8.close()     // Catch:{ Exception -> 0x00c4 }
        L_0x00e4:
            return r1
        L_0x00e5:
            r0 = move-exception
        L_0x00e6:
            if (r8 == 0) goto L_0x00f4
            r8.close()     // Catch:{ Exception -> 0x00ec }
            goto L_0x00f4
        L_0x00ec:
            r8 = move-exception
            java.lang.String r1 = "PushPackageUtils"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r8)
        L_0x00f4:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.faw.b(android.content.Context):java.lang.String");
    }

    private static ezt d(Context context) {
        String b = b(context);
        ApplicationInfo applicationInfo = null;
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        ezt ezt = new ezt(b);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(b, 128);
            if (packageInfo != null) {
                ezt.c = packageInfo.versionCode;
                ezt.d = packageInfo.versionName;
                applicationInfo = packageInfo.applicationInfo;
            }
            if (applicationInfo != null) {
                ezt.b = fbd.a(context, b);
            }
            ezt.e = a(context, ezt.b);
            ezt.f = a(context, b);
            return ezt;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            fat.d("PushPackageUtils", "PackageManager NameNotFoundException is null");
            return null;
        }
    }

    private static ezt b(Context context, String str) {
        ApplicationInfo applicationInfo = null;
        if (!TextUtils.isEmpty(str)) {
            if (a(context, str, "com.vivo.pushservice.action.METHOD") || a(context, str, "com.vivo.pushservice.action.RECEIVE")) {
                ezt ezt = new ezt(str);
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 128);
                    if (packageInfo != null) {
                        ezt.c = packageInfo.versionCode;
                        ezt.d = packageInfo.versionName;
                        applicationInfo = packageInfo.applicationInfo;
                    }
                    if (applicationInfo != null) {
                        ezt.b = fbd.a(context, str);
                    }
                } catch (NameNotFoundException e) {
                    fat.a("PushPackageUtils", "getPushPackageInfo exception: ", e);
                }
                ezt.f = a(context, str);
                ezt.e = a(context, ezt.b);
                return ezt;
            }
        }
        return null;
    }

    public static Set<String> c(Context context) {
        List list;
        List<ResolveInfo> list2;
        HashSet hashSet = new HashSet();
        try {
            list = context.getPackageManager().queryBroadcastReceivers(new Intent("com.vivo.pushservice.action.RECEIVE"), 576);
        } catch (Exception unused) {
            list = null;
        }
        try {
            list2 = context.getPackageManager().queryBroadcastReceivers(new Intent("com.vivo.pushclient.action.RECEIVE"), 576);
        } catch (Exception unused2) {
            list2 = null;
        }
        if (list != null && list.size() > 0) {
            if (list2 != null && list2.size() > 0) {
                list.addAll(list2);
            }
            list2 = list;
        }
        if (list2 != null && list2.size() > 0) {
            for (ResolveInfo resolveInfo : list2) {
                if (resolveInfo != null) {
                    String str = resolveInfo.activityInfo.packageName;
                    if (!TextUtils.isEmpty(str)) {
                        hashSet.add(str);
                    }
                }
            }
        }
        return hashSet;
    }

    public static boolean a(Context context, String str) {
        if (TextUtils.isEmpty(str) || context == null) {
            return false;
        }
        Intent intent = new Intent("com.vivo.pushservice.action.PUSH_SERVICE");
        intent.setPackage(str);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 576);
        if (queryIntentServices == null || queryIntentServices.size() <= 0) {
            fat.a((String) "PushPackageUtils", (String) "isEnablePush error: can not find push service.");
            return false;
        }
        int size = queryIntentServices.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = queryIntentServices.get(i);
            if (!(resolveInfo == null || resolveInfo.serviceInfo == null)) {
                String str2 = resolveInfo.serviceInfo.name;
                boolean z2 = resolveInfo.serviceInfo.exported;
                if ("com.vivo.push.sdk.service.PushService".equals(str2) && z2) {
                    boolean z3 = resolveInfo.serviceInfo.enabled;
                    int componentEnabledSetting = packageManager.getComponentEnabledSetting(new ComponentName(str, "com.vivo.push.sdk.service.PushService"));
                    boolean z4 = true;
                    if (componentEnabledSetting != 1 && (componentEnabledSetting != 0 || !z3)) {
                        z4 = false;
                    }
                    z = z4;
                }
            }
        }
        return z;
    }

    private static boolean a(Context context, long j) {
        ezn a = ezl.a().a(context);
        if (a != null) {
            return a.a(j);
        }
        return false;
    }

    public static boolean a(Context context, String str, String str2) {
        List<ResolveInfo> list;
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        try {
            list = context.getPackageManager().queryBroadcastReceivers(intent, 576);
        } catch (Exception unused) {
            list = null;
        }
        return list != null && list.size() > 0;
    }

    private static List<String> e(Context context) {
        List<ResolveInfo> list;
        fal.a("findAllCoreClientPush");
        ArrayList arrayList = new ArrayList();
        try {
            list = context.getPackageManager().queryIntentServices(new Intent("com.vivo.pushservice.action.PUSH_SERVICE"), 576);
        } catch (Exception unused) {
            list = null;
        }
        if (list != null && list.size() > 0) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ResolveInfo resolveInfo = list.get(i);
                if (resolveInfo != null) {
                    String str = resolveInfo.serviceInfo.packageName;
                    if (!TextUtils.isEmpty(str)) {
                        arrayList.add(str);
                    }
                }
            }
        }
        if (arrayList.size() <= 0) {
            fat.d("PushPackageUtils", "get all push packages is null");
        }
        return arrayList;
    }
}
