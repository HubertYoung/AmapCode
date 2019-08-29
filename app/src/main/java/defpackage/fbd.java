package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;

/* renamed from: fbd reason: default package */
/* compiled from: Utility */
public final class fbd {
    private static String[] a = {"com.vivo.push.sdk.RegistrationReceiver", "com.vivo.push.sdk.service.PushService", "com.vivo.push.sdk.service.CommonJobService"};
    private static String[] b = {"android.permission.INTERNET", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WRITE_SETTINGS", "android.permission.VIBRATE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_WIFI_STATE", "android.permission.WAKE_LOCK", "android.permission.GET_ACCOUNTS", "com.bbk.account.permission.READ_ACCOUNTINFO", "android.permission.AUTHENTICATE_ACCOUNTS", "android.permission.MOUNT_UNMOUNT_FILESYSTEMS", "android.permission.GET_TASKS"};
    private static Boolean c;
    private static String[] d = {"com.vivo.push.sdk.service.CommandService", "com.vivo.push.sdk.service.CommonJobService"};
    private static String[] e = {"com.vivo.push.sdk.RegistrationReceiver"};
    private static String[] f = new String[0];

    public static boolean a(Context context) {
        if (c != null) {
            return c.booleanValue();
        }
        String b2 = faw.b(context);
        if (context != null && context.getPackageName().equals(b2)) {
            Boolean bool = Boolean.TRUE;
            c = bool;
            return bool.booleanValue();
        } else if (context == null) {
            fat.d("Utility", "isPushProcess context is null");
            return false;
        } else {
            int myPid = Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
            String str = null;
            if (activityManager != null) {
                List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                if (runningAppProcesses != null && runningAppProcesses.size() != 0) {
                    Iterator<RunningAppProcessInfo> it = runningAppProcesses.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        RunningAppProcessInfo next = it.next();
                        if (next.pid == myPid) {
                            str = next.processName;
                            break;
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Boolean valueOf = Boolean.valueOf(str.contains(":pushservice"));
            c = valueOf;
            return valueOf.booleanValue();
        }
    }

    public static long b(Context context) {
        String b2 = faw.b(context);
        if (!TextUtils.isEmpty(b2)) {
            return a(context, b2);
        }
        fat.a((String) "Utility", (String) "systemPushPkgName is null");
        return -1;
    }

    public static long a(Context context, String str) {
        Object b2 = b(context, str, "com.vivo.push.sdk_version");
        if (b2 == null) {
            b2 = b(context, str, "sdk_version");
        }
        if (b2 != null) {
            try {
                return Long.parseLong(b2.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
                fat.a("Utility", "getSdkVersionCode error ", e2);
                return -1;
            }
        } else {
            fat.a((String) "Utility", (String) "getSdkVersionCode sdk version is null");
            return -1;
        }
    }

    public static String b(Context context, String str) {
        Object b2 = b(context, str, "com.vivo.push.app_id");
        if (b2 != null) {
            return b2.toString();
        }
        Object b3 = b(context, str, "app_id");
        return b3 != null ? b3.toString() : "";
    }

    private static Object b(Context context, String str, String str2) {
        Object obj = null;
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
            Bundle bundle = applicationInfo != null ? applicationInfo.metaData : null;
            if (bundle != null) {
                obj = bundle.get(str2);
            }
        } catch (NameNotFoundException unused) {
        }
        return obj;
    }

    public static Object a(String str, String str2) throws Exception {
        Class<?> cls = Class.forName(str);
        return cls.getField(str2).get(cls);
    }

    public static String b(String str, String str2) {
        String str3;
        try {
            str3 = (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{str});
        } catch (Exception e2) {
            e2.printStackTrace();
            str3 = str2;
        }
        return (str3 == null || str3.length() == 0) ? str2 : str3;
    }

    public static PublicKey c(Context context) {
        Cursor query = context.getContentResolver().query(fbi.a, null, null, null, null);
        if (query == null) {
            return null;
        }
        do {
            try {
                if (query.moveToNext()) {
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Exception unused) {
                }
                throw th;
            }
            try {
                query.close();
            } catch (Exception unused2) {
            }
            return null;
        } while (!"pushkey".equals(query.getString(query.getColumnIndex("name"))));
        String string = query.getString(query.getColumnIndex("value"));
        fat.d("Utility", "result key : ".concat(String.valueOf(string)));
        PublicKey a2 = fax.a(string);
        try {
            query.close();
        } catch (Exception unused3) {
        }
        return a2;
    }

    public static boolean c(Context context, String str) {
        if (context == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(128);
        if (installedApplications == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ApplicationInfo next : installedApplications) {
            String str2 = next.packageName;
            if ((packageName.equals(str2) || str.equals(str2)) && (next.flags & 129) != 0) {
                StringBuilder sb = new StringBuilder(" matching ");
                sb.append(str2);
                sb.append(" is system app");
                fat.d("Utility", sb.toString());
                return true;
            }
        }
        return false;
    }

    public static void a(Context context, Intent intent) {
        String b2 = faw.b(context);
        String stringExtra = intent.getStringExtra("client_pkgname");
        if (TextUtils.isEmpty(b2)) {
            fat.a((String) "Utility", (String) "illegality abe adapter : push pkg is null");
        } else if (TextUtils.isEmpty(stringExtra)) {
            fat.a((String) "Utility", (String) "illegality abe adapter : src pkg is null");
        } else if (b2.equals(context.getPackageName())) {
            fat.a((String) "Utility", (String) "illegality abe adapter : abe is not pushservice");
        } else if (!b2.equals(stringExtra)) {
            StringBuilder sb = new StringBuilder("proxy to core : intent pkg : ");
            sb.append(intent.getPackage());
            sb.append(" ; src pkg : ");
            sb.append(stringExtra);
            sb.append(" ; push pkg : ");
            sb.append(b2);
            fat.d("Utility", sb.toString());
            intent.setPackage(b2);
            intent.setClassName(b2, "com.vivo.push.sdk.service.PushService");
            context.startService(intent);
        } else {
            StringBuilder sb2 = new StringBuilder("illegality abe adapter : pushPkg = ");
            sb2.append(b2);
            sb2.append(" ; srcPkg = ");
            sb2.append(stringExtra);
            fat.a((String) "Utility", sb2.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x008e A[SYNTHETIC, Splitter:B:44:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x009d A[SYNTHETIC, Splitter:B:50:0x009d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean d(android.content.Context r10) {
        /*
            r0 = 0
            r1 = 0
            if (r10 != 0) goto L_0x0012
            java.lang.String r10 = "Utility"
            java.lang.String r2 = "context is null"
            defpackage.fat.a(r10, r2)     // Catch:{ Exception -> 0x000f }
            return r0
        L_0x000c:
            r10 = move-exception
            goto L_0x009b
        L_0x000f:
            r10 = move-exception
            goto L_0x0085
        L_0x0012:
            java.lang.String r2 = r10.getPackageName()     // Catch:{ Exception -> 0x000f }
            android.content.pm.PackageManager r3 = r10.getPackageManager()     // Catch:{ Exception -> 0x000f }
            android.content.pm.PackageInfo r3 = r3.getPackageInfo(r2, r0)     // Catch:{ Exception -> 0x000f }
            int r3 = r3.versionCode     // Catch:{ Exception -> 0x000f }
            android.content.ContentResolver r4 = r10.getContentResolver()     // Catch:{ Exception -> 0x000f }
            android.net.Uri r5 = defpackage.fbi.b     // Catch:{ Exception -> 0x000f }
            r6 = 0
            java.lang.String r7 = "pushVersion = ? and appPkgName = ? and appCode = ? "
            r10 = 3
            java.lang.String[] r8 = new java.lang.String[r10]     // Catch:{ Exception -> 0x000f }
            java.lang.String r10 = "280"
            r8[r0] = r10     // Catch:{ Exception -> 0x000f }
            r10 = 1
            r8[r10] = r2     // Catch:{ Exception -> 0x000f }
            r2 = 2
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x000f }
            r8[r2] = r3     // Catch:{ Exception -> 0x000f }
            r9 = 0
            android.database.Cursor r2 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x000f }
            if (r2 != 0) goto L_0x005d
            java.lang.String r10 = "Utility"
            java.lang.String r1 = "cursor is null"
            defpackage.fat.a(r10, r1)     // Catch:{ Exception -> 0x005a, all -> 0x0057 }
            if (r2 == 0) goto L_0x0056
            r2.close()     // Catch:{ Exception -> 0x004e }
            goto L_0x0056
        L_0x004e:
            r10 = move-exception
            java.lang.String r1 = "Utility"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r10)
        L_0x0056:
            return r0
        L_0x0057:
            r10 = move-exception
            r1 = r2
            goto L_0x009b
        L_0x005a:
            r10 = move-exception
            r1 = r2
            goto L_0x0085
        L_0x005d:
            boolean r1 = r2.moveToFirst()     // Catch:{ Exception -> 0x005a, all -> 0x0057 }
            if (r1 == 0) goto L_0x007f
            java.lang.String r1 = "permission"
            int r1 = r2.getColumnIndex(r1)     // Catch:{ Exception -> 0x005a, all -> 0x0057 }
            int r1 = r2.getInt(r1)     // Catch:{ Exception -> 0x005a, all -> 0x0057 }
            r1 = r1 & r10
            if (r1 == 0) goto L_0x007f
            if (r2 == 0) goto L_0x007e
            r2.close()     // Catch:{ Exception -> 0x0076 }
            goto L_0x007e
        L_0x0076:
            r0 = move-exception
            java.lang.String r1 = "Utility"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r0)
        L_0x007e:
            return r10
        L_0x007f:
            if (r2 == 0) goto L_0x009a
            r2.close()     // Catch:{ Exception -> 0x0092 }
            goto L_0x009a
        L_0x0085:
            java.lang.String r2 = "Utility"
            java.lang.String r3 = "isSupport"
            defpackage.fat.a(r2, r3, r10)     // Catch:{ all -> 0x000c }
            if (r1 == 0) goto L_0x009a
            r1.close()     // Catch:{ Exception -> 0x0092 }
            goto L_0x009a
        L_0x0092:
            r10 = move-exception
            java.lang.String r1 = "Utility"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r10)
        L_0x009a:
            return r0
        L_0x009b:
            if (r1 == 0) goto L_0x00a9
            r1.close()     // Catch:{ Exception -> 0x00a1 }
            goto L_0x00a9
        L_0x00a1:
            r0 = move-exception
            java.lang.String r1 = "Utility"
            java.lang.String r2 = "close"
            defpackage.fat.a(r1, r2, r0)
        L_0x00a9:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fbd.d(android.content.Context):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x0079 A[SYNTHETIC, Splitter:B:42:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0088 A[SYNTHETIC, Splitter:B:48:0x0088] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
            r0 = 0
            r1 = 0
            if (r8 != 0) goto L_0x0011
            java.lang.String r8 = "Utility"
            java.lang.String r9 = "context is null"
            defpackage.fat.a(r8, r9)     // Catch:{ Exception -> 0x000f }
            return r0
        L_0x000c:
            r8 = move-exception
            goto L_0x0086
        L_0x000f:
            r8 = move-exception
            goto L_0x0070
        L_0x0011:
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch:{ Exception -> 0x000f }
            android.net.Uri r3 = defpackage.fbi.c     // Catch:{ Exception -> 0x000f }
            r4 = 0
            java.lang.String r5 = "appPkgName = ? and regId = ? "
            r8 = 2
            java.lang.String[] r6 = new java.lang.String[r8]     // Catch:{ Exception -> 0x000f }
            r6[r0] = r9     // Catch:{ Exception -> 0x000f }
            r8 = 1
            r6[r8] = r10     // Catch:{ Exception -> 0x000f }
            r7 = 0
            android.database.Cursor r8 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x000f }
            if (r8 != 0) goto L_0x0047
            java.lang.String r9 = "Utility"
            java.lang.String r10 = "cursor is null"
            defpackage.fat.a(r9, r10)     // Catch:{ Exception -> 0x0043, all -> 0x003f }
            if (r8 == 0) goto L_0x003e
            r8.close()     // Catch:{ Exception -> 0x0036 }
            goto L_0x003e
        L_0x0036:
            r8 = move-exception
            java.lang.String r9 = "Utility"
            java.lang.String r10 = "close"
            defpackage.fat.a(r9, r10, r8)
        L_0x003e:
            return r0
        L_0x003f:
            r9 = move-exception
            r1 = r8
            r8 = r9
            goto L_0x0086
        L_0x0043:
            r9 = move-exception
            r1 = r8
            r8 = r9
            goto L_0x0070
        L_0x0047:
            boolean r9 = r8.moveToFirst()     // Catch:{ Exception -> 0x0043, all -> 0x003f }
            if (r9 == 0) goto L_0x006a
            java.lang.String r9 = "clientState"
            int r9 = r8.getColumnIndex(r9)     // Catch:{ Exception -> 0x0043, all -> 0x003f }
            java.lang.String r9 = r8.getString(r9)     // Catch:{ Exception -> 0x0043, all -> 0x003f }
            boolean r9 = java.lang.Boolean.parseBoolean(r9)     // Catch:{ Exception -> 0x0043, all -> 0x003f }
            if (r8 == 0) goto L_0x0069
            r8.close()     // Catch:{ Exception -> 0x0061 }
            goto L_0x0069
        L_0x0061:
            r8 = move-exception
            java.lang.String r10 = "Utility"
            java.lang.String r0 = "close"
            defpackage.fat.a(r10, r0, r8)
        L_0x0069:
            return r9
        L_0x006a:
            if (r8 == 0) goto L_0x0085
            r8.close()     // Catch:{ Exception -> 0x007d }
            goto L_0x0085
        L_0x0070:
            java.lang.String r9 = "Utility"
            java.lang.String r10 = "isOverdue"
            defpackage.fat.a(r9, r10, r8)     // Catch:{ all -> 0x000c }
            if (r1 == 0) goto L_0x0085
            r1.close()     // Catch:{ Exception -> 0x007d }
            goto L_0x0085
        L_0x007d:
            r8 = move-exception
            java.lang.String r9 = "Utility"
            java.lang.String r10 = "close"
            defpackage.fat.a(r9, r10, r8)
        L_0x0085:
            return r0
        L_0x0086:
            if (r1 == 0) goto L_0x0094
            r1.close()     // Catch:{ Exception -> 0x008c }
            goto L_0x0094
        L_0x008c:
            r9 = move-exception
            java.lang.String r10 = "Utility"
            java.lang.String r0 = "close"
            defpackage.fat.a(r10, r0, r9)
        L_0x0094:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fbd.a(android.content.Context, java.lang.String, java.lang.String):boolean");
    }
}
