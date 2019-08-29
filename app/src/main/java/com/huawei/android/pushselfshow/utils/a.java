package com.huawei.android.pushselfshow.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.a.a.d;
import com.huawei.android.pushagent.a.a.c;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class a {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static Typeface b;

    public static int a(int i, int i2) {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder("enter ctrlSockets(cmd:");
        sb.append(i);
        sb.append(" param:");
        sb.append(i2);
        sb.append(")");
        c.a("PushSelfShowLog", sb.toString());
        try {
            return ((Integer) Class.forName("dalvik.system.Zygote").getMethod("ctrlSockets", new Class[]{Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)})).intValue();
        } catch (NoSuchMethodException e) {
            e = e;
            str = "PushSelfShowLog";
            str2 = "NoSuchMethodException:";
            c.d(str, str2.concat(String.valueOf(e)));
            return -2;
        } catch (ClassNotFoundException e2) {
            e = e2;
            str = "PushSelfShowLog";
            str2 = "ClassNotFoundException:";
            c.d(str, str2.concat(String.valueOf(e)));
            return -2;
        } catch (IllegalAccessException e3) {
            e = e3;
            str = "PushSelfShowLog";
            str2 = "IllegalAccessException:";
            c.d(str, str2.concat(String.valueOf(e)));
            return -2;
        } catch (InvocationTargetException e4) {
            e = e4;
            str = "PushSelfShowLog";
            str2 = "InvocationTargetException:";
            c.d(str, str2.concat(String.valueOf(e)));
            return -2;
        } catch (RuntimeException e5) {
            e = e5;
            str = "PushSelfShowLog";
            str2 = "RuntimeException:";
            c.d(str, str2.concat(String.valueOf(e)));
            return -2;
        } catch (Exception e6) {
            e = e6;
            str = "PushSelfShowLog";
            str2 = "Exception:";
            c.d(str, str2.concat(String.valueOf(e)));
            return -2;
        }
    }

    public static int a(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static long a(Context context) {
        c.a("PushSelfShowLog", "enter getVersion()");
        long j = -1000;
        try {
            List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new Intent("com.huawei.android.push.intent.REGISTER").setPackage(context.getPackageName()), 640);
            if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() == 0) {
                return -1000;
            }
            j = a(queryBroadcastReceivers.get(0), (String) "CS_cloud_version");
            c.a("PushSelfShowLog", "get the version is :".concat(String.valueOf(j)));
            return j;
        } catch (Exception e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    public static long a(ResolveInfo resolveInfo, String str) {
        if (resolveInfo == null) {
            return -1;
        }
        try {
            return Long.parseLong(b(resolveInfo, str));
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" is not set in ");
            sb.append(a(resolveInfo));
            c.b("PushSelfShowLog", sb.toString());
            return -1;
        }
    }

    public static Intent a(Context context, String str) {
        try {
            return context.getPackageManager().getLaunchIntentForPackage(str);
        } catch (Exception e) {
            c.b((String) "PushSelfShowLog", e.toString(), (Throwable) e);
            return null;
        }
    }

    public static Boolean a(Context context, String str, Intent intent) {
        try {
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
                int size = queryIntentActivities.size();
                for (int i = 0; i < size; i++) {
                    if (queryIntentActivities.get(i).activityInfo != null && str.equals(queryIntentActivities.get(i).activityInfo.applicationInfo.packageName)) {
                        return Boolean.TRUE;
                    }
                }
            }
        } catch (Exception e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
        return Boolean.FALSE;
    }

    public static String a(Context context, String str, String str2) {
        try {
            return context.getResources().getConfiguration().locale.getLanguage().endsWith("zh") ? str : str2;
        } catch (Exception e) {
            c.c("PushSelfShowLog", "getStringByLanguage failed ", e);
        }
    }

    public static String a(ResolveInfo resolveInfo) {
        return resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.packageName : resolveInfo.activityInfo.packageName;
    }

    public static String a(String str) {
        try {
            return a(d.a(str.getBytes("UTF-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf5raDExuuXbsVNCWl48yuB89W\rfNOuuhPuS2Mptii/0UorpzypBkNTTGt11E7aorCc1lFwlB+4KDMIpFyQsdChSk+A\rt9UfhFKa95uiDpMe5rMfU+DAhoXGER6WQ2qGtrHmBWVv33i3lc76u9IgEfYuLwC6\r1mhQDHzAKPiViY6oeQIDAQAB\r"));
        } catch (Exception e) {
            c.d("PushSelfShowLog", "encrypter error ", e);
            r3 = "";
            return "";
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(a[(bArr[i] >>> 4) & 15]);
            sb.append(a[bArr[i] & 15]);
        }
        return sb.toString();
    }

    public static void a(Context context, Intent intent, long j) {
        try {
            StringBuilder sb = new StringBuilder("enter setAPDelayAlarm(intent:");
            sb.append(intent.toURI());
            sb.append(" interval:");
            sb.append(j);
            sb.append("ms, context:");
            sb.append(context);
            c.a("PushSelfShowLog", sb.toString());
            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(0, System.currentTimeMillis() + j, PendingIntent.getBroadcast(context, new SecureRandom().nextInt(), intent, 0));
        } catch (Exception e) {
            c.a((String) "PushSelfShowLog", (String) "set DelayAlarm error", (Throwable) e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0049, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r3, android.widget.TextView r4) {
        /*
            java.lang.Class<com.huawei.android.pushselfshow.utils.a> r0 = com.huawei.android.pushselfshow.utils.a.class
            monitor-enter(r0)
            if (r3 == 0) goto L_0x004a
            if (r4 != 0) goto L_0x0008
            goto L_0x004a
        L_0x0008:
            int r3 = com.huawei.android.pushagent.a.a.a.a()     // Catch:{ all -> 0x0053 }
            r1 = 10
            if (r3 < r1) goto L_0x0048
            boolean r3 = e()     // Catch:{ all -> 0x0053 }
            if (r3 == 0) goto L_0x0048
            java.lang.String r3 = "chnfzxh"
            int r1 = com.huawei.android.pushagent.a.a.a.a()     // Catch:{ all -> 0x0053 }
            r2 = 11
            if (r1 < r2) goto L_0x0022
            java.lang.String r3 = "HwChinese-medium"
        L_0x0022:
            android.graphics.Typeface r1 = b     // Catch:{ all -> 0x0053 }
            if (r1 != 0) goto L_0x0038
            r1 = 0
            android.graphics.Typeface r3 = android.graphics.Typeface.create(r3, r1)     // Catch:{ Exception -> 0x002e }
            b = r3     // Catch:{ Exception -> 0x002e }
            goto L_0x0038
        L_0x002e:
            r3 = move-exception
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0053 }
            com.huawei.android.pushagent.a.a.c.d(r1, r3)     // Catch:{ all -> 0x0053 }
        L_0x0038:
            android.graphics.Typeface r3 = b     // Catch:{ all -> 0x0053 }
            if (r3 == 0) goto L_0x0048
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r1 = "setTypeFaceEx success"
            com.huawei.android.pushagent.a.a.c.a(r3, r1)     // Catch:{ all -> 0x0053 }
            android.graphics.Typeface r3 = b     // Catch:{ all -> 0x0053 }
            r4.setTypeface(r3)     // Catch:{ all -> 0x0053 }
        L_0x0048:
            monitor-exit(r0)
            return
        L_0x004a:
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "context is null or textView is null"
            com.huawei.android.pushagent.a.a.c.b(r3, r4)     // Catch:{ all -> 0x0053 }
            monitor-exit(r0)
            return
        L_0x0053:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.a(android.content.Context, android.widget.TextView):void");
    }

    public static void a(Context context, String str, com.huawei.android.pushselfshow.b.a aVar) {
        if (aVar != null) {
            a(context, str, aVar.m, aVar.p);
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        new Thread(new b(context, str2, str, str3)).start();
    }

    public static void a(File file) {
        if (file != null) {
            StringBuilder sb = new StringBuilder("delete file ");
            sb.append(file.getAbsolutePath());
            c.a("PushSelfShowLog", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append(System.currentTimeMillis());
            File file2 = new File(sb2.toString());
            if (file.renameTo(file2)) {
                if ((!file2.isFile() || !file2.delete()) && file2.isDirectory()) {
                    File[] listFiles = file2.listFiles();
                    if (listFiles != null && listFiles.length != 0) {
                        for (File a2 : listFiles) {
                            a(a2);
                        }
                        if (!file2.delete()) {
                            c.a("PushSelfShowLog", "delete file unsuccess");
                        }
                    } else if (!file2.delete()) {
                        c.a("PushSelfShowLog", "delete file failed");
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x0120 A[SYNTHETIC, Splitter:B:104:0x0120] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00a5 A[SYNTHETIC, Splitter:B:61:0x00a5] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00b3 A[SYNTHETIC, Splitter:B:66:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00d1 A[SYNTHETIC, Splitter:B:75:0x00d1] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00df A[SYNTHETIC, Splitter:B:80:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x00e6 A[SYNTHETIC, Splitter:B:85:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x00f4 A[SYNTHETIC, Splitter:B:90:0x00f4] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0112 A[SYNTHETIC, Splitter:B:99:0x0112] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.io.File r6, java.io.File r7) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0098, all -> 0x0093 }
            r1.<init>(r6)     // Catch:{ IOException -> 0x0098, all -> 0x0093 }
            java.io.BufferedInputStream r6 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x008f, all -> 0x008b }
            r6.<init>(r1)     // Catch:{ IOException -> 0x008f, all -> 0x008b }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0085, all -> 0x007e }
            r2.<init>(r7)     // Catch:{ IOException -> 0x0085, all -> 0x007e }
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0078, all -> 0x0071 }
            r7.<init>(r2)     // Catch:{ IOException -> 0x0078, all -> 0x0071 }
            r0 = 5120(0x1400, float:7.175E-42)
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x006c, all -> 0x0066 }
        L_0x0019:
            int r3 = r6.read(r0)     // Catch:{ IOException -> 0x006c, all -> 0x0066 }
            r4 = -1
            if (r3 == r4) goto L_0x0025
            r4 = 0
            r7.write(r0, r4, r3)     // Catch:{ IOException -> 0x006c, all -> 0x0066 }
            goto L_0x0019
        L_0x0025:
            r6.close()     // Catch:{ Exception -> 0x0029 }
            goto L_0x0031
        L_0x0029:
            r6 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r3 = "inBuff.close() "
            com.huawei.android.pushagent.a.a.c.d(r0, r3, r6)
        L_0x0031:
            r7.flush()     // Catch:{ Exception -> 0x0035 }
            goto L_0x003f
        L_0x0035:
            r6 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r3 = r6.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r3, r6)
        L_0x003f:
            r7.close()     // Catch:{ Exception -> 0x0043 }
            goto L_0x004d
        L_0x0043:
            r6 = move-exception
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.String r0 = r6.toString()
            com.huawei.android.pushagent.a.a.c.c(r7, r0, r6)
        L_0x004d:
            r2.close()     // Catch:{ Exception -> 0x0051 }
            goto L_0x0059
        L_0x0051:
            r6 = move-exception
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.String r0 = "output.close() "
            com.huawei.android.pushagent.a.a.c.d(r7, r0, r6)
        L_0x0059:
            r1.close()     // Catch:{ Exception -> 0x005d }
            return
        L_0x005d:
            r6 = move-exception
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.String r0 = "input.close() "
            com.huawei.android.pushagent.a.a.c.d(r7, r0, r6)
            return
        L_0x0066:
            r0 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
            goto L_0x00e4
        L_0x006c:
            r0 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
            goto L_0x009c
        L_0x0071:
            r7 = move-exception
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r5
            goto L_0x00e4
        L_0x0078:
            r7 = move-exception
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r5
            goto L_0x009c
        L_0x007e:
            r7 = move-exception
            r2 = r0
            r0 = r6
            r6 = r7
            r7 = r2
            goto L_0x00e4
        L_0x0085:
            r7 = move-exception
            r2 = r0
            r0 = r6
            r6 = r7
            r7 = r2
            goto L_0x009c
        L_0x008b:
            r6 = move-exception
            r7 = r0
            r2 = r7
            goto L_0x00e4
        L_0x008f:
            r6 = move-exception
            r7 = r0
            r2 = r7
            goto L_0x009c
        L_0x0093:
            r6 = move-exception
            r7 = r0
            r1 = r7
            r2 = r1
            goto L_0x00e4
        L_0x0098:
            r6 = move-exception
            r7 = r0
            r1 = r7
            r2 = r1
        L_0x009c:
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "copyFile "
            com.huawei.android.pushagent.a.a.c.d(r3, r4, r6)     // Catch:{ all -> 0x00e3 }
            if (r0 == 0) goto L_0x00b1
            r0.close()     // Catch:{ Exception -> 0x00a9 }
            goto L_0x00b1
        L_0x00a9:
            r6 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r3 = "inBuff.close() "
            com.huawei.android.pushagent.a.a.c.d(r0, r3, r6)
        L_0x00b1:
            if (r7 == 0) goto L_0x00cf
            r7.flush()     // Catch:{ Exception -> 0x00b7 }
            goto L_0x00c1
        L_0x00b7:
            r6 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r3 = r6.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r3, r6)
        L_0x00c1:
            r7.close()     // Catch:{ Exception -> 0x00c5 }
            goto L_0x00cf
        L_0x00c5:
            r6 = move-exception
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.String r0 = r6.toString()
            com.huawei.android.pushagent.a.a.c.c(r7, r0, r6)
        L_0x00cf:
            if (r2 == 0) goto L_0x00dd
            r2.close()     // Catch:{ Exception -> 0x00d5 }
            goto L_0x00dd
        L_0x00d5:
            r6 = move-exception
            java.lang.String r7 = "PushSelfShowLog"
            java.lang.String r0 = "output.close() "
            com.huawei.android.pushagent.a.a.c.d(r7, r0, r6)
        L_0x00dd:
            if (r1 == 0) goto L_0x00e2
            r1.close()     // Catch:{ Exception -> 0x005d }
        L_0x00e2:
            return
        L_0x00e3:
            r6 = move-exception
        L_0x00e4:
            if (r0 == 0) goto L_0x00f2
            r0.close()     // Catch:{ Exception -> 0x00ea }
            goto L_0x00f2
        L_0x00ea:
            r0 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "inBuff.close() "
            com.huawei.android.pushagent.a.a.c.d(r3, r4, r0)
        L_0x00f2:
            if (r7 == 0) goto L_0x0110
            r7.flush()     // Catch:{ Exception -> 0x00f8 }
            goto L_0x0102
        L_0x00f8:
            r0 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = r0.toString()
            com.huawei.android.pushagent.a.a.c.c(r3, r4, r0)
        L_0x0102:
            r7.close()     // Catch:{ Exception -> 0x0106 }
            goto L_0x0110
        L_0x0106:
            r7 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r3 = r7.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r3, r7)
        L_0x0110:
            if (r2 == 0) goto L_0x011e
            r2.close()     // Catch:{ Exception -> 0x0116 }
            goto L_0x011e
        L_0x0116:
            r7 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r2 = "output.close() "
            com.huawei.android.pushagent.a.a.c.d(r0, r2, r7)
        L_0x011e:
            if (r1 == 0) goto L_0x012c
            r1.close()     // Catch:{ Exception -> 0x0124 }
            goto L_0x012c
        L_0x0124:
            r7 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "input.close() "
            com.huawei.android.pushagent.a.a.c.d(r0, r1, r7)
        L_0x012c:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.a(java.io.File, java.io.File):void");
    }

    public static boolean a(Context context, Intent intent) {
        if (context == null) {
            c.b("PushSelfShowLog", "context is null");
            return false;
        } else if (intent == null) {
            c.b("PushSelfShowLog", "intent is null");
            return false;
        } else {
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 640);
            if (queryIntentActivities == null || queryIntentActivities.size() == 0) {
                c.d("PushSelfShowLog", "no activity exist, may be system Err!! pkgName:");
                return false;
            }
            boolean z = queryIntentActivities.get(0).activityInfo.exported;
            c.b("PushSelfShowLog", "exportedFlag:".concat(String.valueOf(z)));
            String str = queryIntentActivities.get(0).activityInfo.permission;
            c.b("PushSelfShowLog", "need permission:".concat(String.valueOf(str)));
            if (!z) {
                return false;
            }
            return TextUtils.isEmpty(str) || "com.huawei.pushagent.permission.LAUNCH_ACTIVITY".equals(str);
        }
    }

    public static boolean a(String str, String str2) {
        try {
            c.a((String) "PushSelfShowLog", (String) "urlSrc is %s ,urlDest is %s,urlDest is already exist?%s ", str, str2, Boolean.valueOf(new File(str2).mkdirs()));
            File[] listFiles = new File(str).listFiles();
            if (listFiles != null) {
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isFile()) {
                        File file = listFiles[i];
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(File.separator);
                        sb.append(listFiles[i].getName());
                        a(file, new File(sb.toString()));
                    }
                    if (listFiles[i].isDirectory()) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(File.separator);
                        sb2.append(listFiles[i].getName());
                        String sb3 = sb2.toString();
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str2);
                        sb4.append(File.separator);
                        sb4.append(listFiles[i].getName());
                        b(sb3, sb4.toString());
                    }
                }
            }
            return true;
        } catch (Exception e) {
            c.d("PushSelfShowLog", "fileCopy error ", e);
            return false;
        }
    }

    public static long b(String str) {
        if (str == null) {
            str = "";
        }
        try {
            Date date = new Date();
            int hours = (date.getHours() * 2) + (date.getMinutes() / 30);
            String concat = str.concat(str);
            c.a((String) "PushSelfShowLog", (String) "startIndex is %s ,and ap is %s ,length is %s", Integer.valueOf(hours), concat, Integer.valueOf(concat.length()));
            for (int i = hours; i < concat.length(); i++) {
                if (concat.charAt(i) != '0') {
                    long minutes = ((long) (((i - hours) * 30) - (date.getMinutes() % 30))) * 60000;
                    c.a((String) "PushSelfShowLog", (String) "startIndex is %s i is %s delay %s", Integer.valueOf(hours), Integer.valueOf(i), Long.valueOf(minutes));
                    if (minutes >= 0) {
                        return minutes;
                    }
                    return 0;
                }
            }
        } catch (Exception e) {
            c.c("PushSelfShowLog", "error ", e);
        }
        return 0;
    }

    public static String b(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return telephonyManager != null ? telephonyManager.getDeviceId() : "";
        } catch (Exception unused) {
            return "";
        }
    }

    private static String b(ResolveInfo resolveInfo, String str) {
        Bundle bundle = resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.metaData : resolveInfo.activityInfo.metaData;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x005d A[SYNTHETIC, Splitter:B:39:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x006b A[SYNTHETIC, Splitter:B:44:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0072 A[SYNTHETIC, Splitter:B:49:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0080 A[SYNTHETIC, Splitter:B:54:0x0080] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(android.content.Context r2, java.lang.String r3, java.lang.String r4) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x0052, all -> 0x004f }
            r1.<init>(r4)     // Catch:{ IOException -> 0x0052, all -> 0x004f }
            boolean r1 = r1.exists()     // Catch:{ IOException -> 0x0052, all -> 0x004f }
            if (r1 != 0) goto L_0x0030
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ IOException -> 0x0052, all -> 0x004f }
            java.io.InputStream r2 = r2.open(r3)     // Catch:{ IOException -> 0x0052, all -> 0x004f }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x002e }
            r3.<init>(r4)     // Catch:{ IOException -> 0x002e }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ IOException -> 0x002b, all -> 0x0028 }
        L_0x001d:
            int r0 = r2.read(r4)     // Catch:{ IOException -> 0x002b, all -> 0x0028 }
            if (r0 <= 0) goto L_0x0032
            r1 = 0
            r3.write(r4, r1, r0)     // Catch:{ IOException -> 0x002b, all -> 0x0028 }
            goto L_0x001d
        L_0x0028:
            r4 = move-exception
            r0 = r3
            goto L_0x0070
        L_0x002b:
            r4 = move-exception
            r0 = r3
            goto L_0x0054
        L_0x002e:
            r4 = move-exception
            goto L_0x0054
        L_0x0030:
            r2 = r0
            r3 = r2
        L_0x0032:
            if (r3 == 0) goto L_0x0040
            r3.close()     // Catch:{ Exception -> 0x0038 }
            goto L_0x0040
        L_0x0038:
            r3 = move-exception
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.String r0 = "fos.close() "
            com.huawei.android.pushagent.a.a.c.d(r4, r0, r3)
        L_0x0040:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ Exception -> 0x0046 }
            return
        L_0x0046:
            r2 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "is.close() "
            com.huawei.android.pushagent.a.a.c.d(r3, r4, r2)
        L_0x004e:
            return
        L_0x004f:
            r4 = move-exception
            r2 = r0
            goto L_0x0070
        L_0x0052:
            r4 = move-exception
            r2 = r0
        L_0x0054:
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r1 = "copyAsset "
            com.huawei.android.pushagent.a.a.c.d(r3, r1, r4)     // Catch:{ all -> 0x006f }
            if (r0 == 0) goto L_0x0069
            r0.close()     // Catch:{ Exception -> 0x0061 }
            goto L_0x0069
        L_0x0061:
            r3 = move-exception
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.String r0 = "fos.close() "
            com.huawei.android.pushagent.a.a.c.d(r4, r0, r3)
        L_0x0069:
            if (r2 == 0) goto L_0x006e
            r2.close()     // Catch:{ Exception -> 0x0046 }
        L_0x006e:
            return
        L_0x006f:
            r4 = move-exception
        L_0x0070:
            if (r0 == 0) goto L_0x007e
            r0.close()     // Catch:{ Exception -> 0x0076 }
            goto L_0x007e
        L_0x0076:
            r3 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "fos.close() "
            com.huawei.android.pushagent.a.a.c.d(r0, r1, r3)
        L_0x007e:
            if (r2 == 0) goto L_0x008c
            r2.close()     // Catch:{ Exception -> 0x0084 }
            goto L_0x008c
        L_0x0084:
            r2 = move-exception
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r0 = "is.close() "
            com.huawei.android.pushagent.a.a.c.d(r3, r0, r2)
        L_0x008c:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.b(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void b(File file) {
        c.a("PushSelfShowLog", "delete file before ");
        if (file != null && file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                long currentTimeMillis = System.currentTimeMillis();
                for (int i = 0; i < listFiles.length; i++) {
                    try {
                        File file2 = listFiles[i];
                        if (currentTimeMillis - file2.lastModified() > 86400000) {
                            StringBuilder sb = new StringBuilder("delete file before ");
                            sb.append(file2.getAbsolutePath());
                            c.e("PushSelfShowLog", sb.toString());
                            a(file2);
                        }
                    } catch (Exception e) {
                        c.e("PushSelfShowLog", e.toString());
                    }
                }
            }
        }
    }

    private static void b(String str, String str2) throws IOException {
        if (new File(str2).mkdirs()) {
            c.e("PushSelfShowLog", "mkdir");
        }
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    File file = listFiles[i];
                    StringBuilder sb = new StringBuilder();
                    sb.append(new File(str2).getAbsolutePath());
                    sb.append(File.separator);
                    sb.append(listFiles[i].getName());
                    a(file, new File(sb.toString()));
                }
                if (listFiles[i].isDirectory()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("/");
                    sb2.append(listFiles[i].getName());
                    String sb3 = sb2.toString();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str2);
                    sb4.append("/");
                    sb4.append(listFiles[i].getName());
                    b(sb3, sb4.toString());
                }
            }
        }
    }

    public static boolean b() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean b(Context context, String str) {
        if (context == null || str == null || "".equals(str)) {
            return false;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(str, 8192) == null) {
                return false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" is installed");
            c.a("PushSelfShowLog", sb.toString());
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String c(Context context, String str) {
        String str2 = "";
        try {
            String path = (!Environment.getExternalStorageState().equals("mounted") ? context.getFilesDir() : Environment.getExternalStorageDirectory()).getPath();
            StringBuilder sb = new StringBuilder();
            sb.append(path);
            sb.append(File.separator);
            sb.append("PushService");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(File.separator);
            sb3.append(str);
            String sb4 = sb3.toString();
            try {
                c.a("PushSelfShowLog", "dbPath is ".concat(String.valueOf(sb4)));
                return sb4;
            } catch (Exception e) {
                str2 = sb4;
                e = e;
                c.d("PushSelfShowLog", "getDbPath error", e);
                return str2;
            }
        } catch (Exception e2) {
            e = e2;
            c.d("PushSelfShowLog", "getDbPath error", e);
            return str2;
        }
    }

    public static ArrayList c(Context context) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id="));
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!(queryIntentActivities == null || queryIntentActivities.size() == 0)) {
            int size = queryIntentActivities.size();
            for (int i = 0; i < size; i++) {
                if (queryIntentActivities.get(i).activityInfo != null) {
                    arrayList.add(queryIntentActivities.get(i).activityInfo.applicationInfo.packageName);
                }
            }
        }
        return arrayList;
    }

    public static boolean c() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean d() {
        return com.huawei.android.pushagent.a.a.a.a() >= 9;
    }

    public static boolean d(Context context) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setPackage("com.android.email");
        intent.setData(Uri.fromParts("mailto", "xxxx@xxxx.com", null));
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        return (queryIntentActivities == null || queryIntentActivities.size() == 0) ? false : true;
    }

    private static boolean e() {
        return "zh".equals(Locale.getDefault().getLanguage());
    }

    public static boolean e(Context context) {
        return "com.huawei.android.pushagent".equals(context.getPackageName());
    }

    public static boolean f(Context context) {
        try {
            if (context.getPackageManager().getApplicationInfo("com.huawei.android.pushagent", 128) != null) {
                return true;
            }
        } catch (NameNotFoundException unused) {
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean g(android.content.Context r7) {
        /*
            android.content.ContentResolver r0 = r7.getContentResolver()
            r7 = 0
            r6 = 0
            android.net.Uri r1 = com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a.a     // Catch:{ Exception -> 0x0049 }
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0049 }
            if (r0 == 0) goto L_0x0040
            boolean r1 = r0.moveToFirst()     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            if (r1 == 0) goto L_0x0040
            java.lang.String r1 = "isSupport"
            int r1 = r0.getColumnIndex(r1)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            int r1 = r0.getInt(r1)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "isExistProvider:"
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            com.huawei.android.pushagent.a.a.c.a(r2, r3)     // Catch:{ Exception -> 0x003d, all -> 0x003b }
            r2 = 1
            if (r2 != r1) goto L_0x0035
            r7 = 1
        L_0x0035:
            if (r0 == 0) goto L_0x003a
            r0.close()
        L_0x003a:
            return r7
        L_0x003b:
            r7 = move-exception
            goto L_0x0059
        L_0x003d:
            r1 = move-exception
            r6 = r0
            goto L_0x004a
        L_0x0040:
            if (r0 == 0) goto L_0x0058
            r0.close()
            return r7
        L_0x0046:
            r7 = move-exception
            r0 = r6
            goto L_0x0059
        L_0x0049:
            r1 = move-exception
        L_0x004a:
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r2 = r1.toString()     // Catch:{ all -> 0x0046 }
            com.huawei.android.pushagent.a.a.c.a(r0, r2, r1)     // Catch:{ all -> 0x0046 }
            if (r6 == 0) goto L_0x0058
            r6.close()
        L_0x0058:
            return r7
        L_0x0059:
            if (r0 == 0) goto L_0x005e
            r0.close()
        L_0x005e:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.g(android.content.Context):boolean");
    }

    public static int h(Context context) {
        if (context == null) {
            return 3;
        }
        return (VERSION.SDK_INT < 16 || context.getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null) == 0) ? 3 : 0;
    }

    public static int i(Context context) {
        String str;
        String str2;
        try {
            Class<?> cls = Class.forName("com.huawei.android.immersion.ImmersionStyle");
            int intValue = ((Integer) cls.getDeclaredMethod("getPrimaryColor", new Class[]{Context.class}).invoke(cls, new Object[]{context})).intValue();
            c.b("PushSelfShowLog", "colorPrimary:".concat(String.valueOf(intValue)));
            return intValue;
        } catch (ClassNotFoundException unused) {
            c.d("PushSelfShowLog", "ImmersionStyle ClassNotFoundException");
            return 0;
        } catch (SecurityException e) {
            str2 = "PushSelfShowLog";
            str = e.toString();
            r6 = e;
            c.c(str2, str, r6);
            return 0;
        } catch (NoSuchMethodException e2) {
            str2 = "PushSelfShowLog";
            str = e2.toString();
            r6 = e2;
            c.c(str2, str, r6);
            return 0;
        } catch (IllegalArgumentException e3) {
            str2 = "PushSelfShowLog";
            str = e3.toString();
            r6 = e3;
            c.c(str2, str, r6);
            return 0;
        } catch (IllegalAccessException e4) {
            str2 = "PushSelfShowLog";
            str = e4.toString();
            r6 = e4;
            c.c(str2, str, r6);
            return 0;
        } catch (InvocationTargetException e5) {
            str2 = "PushSelfShowLog";
            str = e5.toString();
            r6 = e5;
            c.c(str2, str, r6);
            return 0;
        }
    }

    /* JADX WARNING: type inference failed for: r8v4, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r8v5, types: [java.lang.SecurityException] */
    /* JADX WARNING: type inference failed for: r8v6, types: [java.lang.SecurityException] */
    /* JADX WARNING: type inference failed for: r8v9, types: [java.lang.IllegalArgumentException] */
    /* JADX WARNING: type inference failed for: r8v10, types: [java.lang.IllegalArgumentException] */
    /* JADX WARNING: type inference failed for: r0v10, types: [java.lang.SecurityException] */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r8v22 */
    /* JADX WARNING: type inference failed for: r0v12, types: [java.lang.NoSuchMethodException] */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r0v14, types: [java.lang.IllegalArgumentException] */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r8v24 */
    /* JADX WARNING: type inference failed for: r0v16, types: [java.lang.IllegalAccessException] */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r0v18, types: [java.lang.reflect.InvocationTargetException] */
    /* JADX WARNING: type inference failed for: r7v4 */
    /* JADX WARNING: type inference failed for: r8v27 */
    /* JADX WARNING: type inference failed for: r8v28 */
    /* JADX WARNING: type inference failed for: r8v29 */
    /* JADX WARNING: type inference failed for: r8v31 */
    /* JADX WARNING: type inference failed for: r8v32 */
    /* JADX WARNING: type inference failed for: r8v33 */
    /* JADX WARNING: type inference failed for: r8v35 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 15 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int j(android.content.Context r8) {
        /*
            r0 = -1
            java.lang.String r1 = "com.huawei.android.immersion.ImmersionStyle"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.String r2 = "getPrimaryColor"
            r3 = 1
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Class<android.content.Context> r5 = android.content.Context.class
            r6 = 0
            r4[r6] = r5     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.reflect.Method r2 = r1.getDeclaredMethod(r2, r4)     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            r4[r6] = r8     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Object r8 = r2.invoke(r1, r4)     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            int r8 = r8.intValue()     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.String r2 = "getSuggestionForgroundColorStyle"
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            r4[r6] = r5     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.reflect.Method r2 = r1.getDeclaredMethod(r2, r4)     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            r3[r6] = r8     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Object r8 = r2.invoke(r1, r3)     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            int r8 = r8.intValue()     // Catch:{ ClassNotFoundException -> 0x0096, SecurityException -> 0x008b, NoSuchMethodException -> 0x0083, IllegalArgumentException -> 0x007b, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x006b }
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "getSuggestionForgroundColorStyle:"
            java.lang.String r2 = java.lang.String.valueOf(r8)     // Catch:{ ClassNotFoundException -> 0x0097, SecurityException -> 0x0066, NoSuchMethodException -> 0x0061, IllegalArgumentException -> 0x005c, IllegalAccessException -> 0x0057, InvocationTargetException -> 0x0052 }
            java.lang.String r1 = r1.concat(r2)     // Catch:{ ClassNotFoundException -> 0x0097, SecurityException -> 0x0066, NoSuchMethodException -> 0x0061, IllegalArgumentException -> 0x005c, IllegalAccessException -> 0x0057, InvocationTargetException -> 0x0052 }
            com.huawei.android.pushagent.a.a.c.b(r0, r1)     // Catch:{ ClassNotFoundException -> 0x0097, SecurityException -> 0x0066, NoSuchMethodException -> 0x0061, IllegalArgumentException -> 0x005c, IllegalAccessException -> 0x0057, InvocationTargetException -> 0x0052 }
            goto L_0x009e
        L_0x0052:
            r0 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x006c
        L_0x0057:
            r0 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x0074
        L_0x005c:
            r0 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x007c
        L_0x0061:
            r0 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x0084
        L_0x0066:
            r0 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x008c
        L_0x006b:
            r8 = move-exception
        L_0x006c:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = r8.toString()
            goto L_0x0092
        L_0x0073:
            r8 = move-exception
        L_0x0074:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = r8.toString()
            goto L_0x0092
        L_0x007b:
            r8 = move-exception
        L_0x007c:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = r8.toString()
            goto L_0x0092
        L_0x0083:
            r8 = move-exception
        L_0x0084:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = r8.toString()
            goto L_0x0092
        L_0x008b:
            r8 = move-exception
        L_0x008c:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = r8.toString()
        L_0x0092:
            com.huawei.android.pushagent.a.a.c.c(r1, r2, r8)
            return r0
        L_0x0096:
            r8 = -1
        L_0x0097:
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "ImmersionStyle ClassNotFoundException"
            com.huawei.android.pushagent.a.a.c.d(r0, r1)
        L_0x009e:
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.j(android.content.Context):int");
    }

    public static String k(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            return externalCacheDir.getPath();
        }
        StringBuilder sb = new StringBuilder("/Android/data/");
        sb.append(context.getPackageName());
        sb.append("/cache");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(Environment.getExternalStorageDirectory().getPath());
        sb3.append(sb2);
        return sb3.toString();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0030 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0031 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean m(android.content.Context r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = -1
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch:{ Exception -> 0x0021 }
            java.lang.String r2 = "user_experience_involved"
            int r4 = android.provider.Settings.Secure.getInt(r4, r2, r1)     // Catch:{ Exception -> 0x0021 }
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "settingMainSwitch:"
            java.lang.String r3 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x001f }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Exception -> 0x001f }
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ Exception -> 0x001f }
            goto L_0x002d
        L_0x001f:
            r1 = move-exception
            goto L_0x0024
        L_0x0021:
            r4 = move-exception
            r1 = r4
            r4 = -1
        L_0x0024:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = r1.toString()
            com.huawei.android.pushagent.a.a.c.c(r2, r3, r1)
        L_0x002d:
            r1 = 1
            if (r4 != r1) goto L_0x0031
            return r1
        L_0x0031:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.m(android.content.Context):boolean");
    }
}
