package com.amap.location.icecream;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.location.common.d.a;
import com.amap.location.icecream.interfaces.IIcecream;
import com.amap.location.uptunnel.UpTunnel;
import com.autonavi.minimap.offline.model.FilePathHelper;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/* compiled from: IcecreamCone */
public class c {
    private static String a = "unknow";
    private static Map<String, DexClassLoader> b = new HashMap();
    private IIcecream c;
    private h d;

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(com.amap.location.icecream.h r2, android.content.Context r3, org.json.JSONObject r4) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r3 == 0) goto L_0x0054
            if (r2 != 0) goto L_0x0006
            goto L_0x0054
        L_0x0006:
            com.amap.location.icecream.interfaces.IIcecream r0 = r1.c     // Catch:{ all -> 0x0051 }
            if (r0 != 0) goto L_0x000d
            r1.a(r2, r3)     // Catch:{ all -> 0x0051 }
        L_0x000d:
            com.amap.location.icecream.interfaces.IIcecream r0 = r1.c     // Catch:{ all -> 0x0051 }
            if (r0 == 0) goto L_0x004f
            com.amap.location.icecream.interfaces.IIcecream r0 = r1.c     // Catch:{ Throwable -> 0x0018 }
            r0.start(r3, r4)     // Catch:{ Throwable -> 0x0018 }
            monitor-exit(r1)
            return
        L_0x0018:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0051 }
            r4.<init>()     // Catch:{ all -> 0x0051 }
            java.lang.String r2 = r2.a     // Catch:{ all -> 0x0051 }
            r4.append(r2)     // Catch:{ all -> 0x0051 }
            java.lang.String r2 = r1.b()     // Catch:{ all -> 0x0051 }
            r4.append(r2)     // Catch:{ all -> 0x0051 }
            java.lang.String r2 = android.util.Log.getStackTraceString(r3)     // Catch:{ all -> 0x0051 }
            r4.append(r2)     // Catch:{ all -> 0x0051 }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x0051 }
            java.lang.String r3 = "icecone"
            java.lang.String r4 = "start error:"
            java.lang.String r0 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0051 }
            java.lang.String r4 = r4.concat(r0)     // Catch:{ all -> 0x0051 }
            com.amap.location.common.d.a.c(r3, r4)     // Catch:{ all -> 0x0051 }
            r3 = 100057(0x186d9, float:1.4021E-40)
            byte[] r2 = r2.getBytes()     // Catch:{ all -> 0x0051 }
            com.amap.location.uptunnel.UpTunnel.reportEvent(r3, r2)     // Catch:{ all -> 0x0051 }
        L_0x004f:
            monitor-exit(r1)
            return
        L_0x0051:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        L_0x0054:
            monitor-exit(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.c.a(com.amap.location.icecream.h, android.content.Context, org.json.JSONObject):void");
    }

    /* access modifiers changed from: protected */
    public synchronized void a() {
        if (this.c != null) {
            try {
                this.c.stop();
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.d.a);
                sb.append(b());
                sb.append(Log.getStackTraceString(th));
                String sb2 = sb.toString();
                a.c("icecone", "stop error:".concat(String.valueOf(sb2)));
                UpTunnel.reportEvent(100058, sb2.getBytes());
            }
        }
    }

    private void a(h hVar, Context context) {
        if (!(context == null || hVar == null)) {
            this.d = hVar;
            a(context);
            DexClassLoader dexClassLoader = b.get(this.d.c);
            if (dexClassLoader == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(com.amap.location.icecream.a.a.a(context));
                sb.append(File.separator);
                sb.append(hVar.a);
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(com.amap.location.icecream.a.a.b(context));
                sb3.append(File.separator);
                sb3.append(hVar.a.replace(".apk", ""));
                String sb4 = sb3.toString();
                String a2 = a(sb2, sb4);
                if ("unknow".equals(a2)) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(this.d.a);
                    sb5.append(b());
                    String sb6 = sb5.toString();
                    a.c("icecone", "lib system type not match".concat(String.valueOf(sb6)));
                    UpTunnel.reportEvent(100053, sb6.getBytes());
                    return;
                }
                if (TextUtils.isEmpty(a2)) {
                    if (!hVar.f) {
                        a2 = context.getApplicationInfo().nativeLibraryDir;
                    } else {
                        return;
                    }
                }
                try {
                    dexClassLoader = new DexClassLoader(sb2, sb4, a2, context.getClassLoader());
                } catch (Throwable th) {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(this.d.a);
                    sb7.append(b());
                    sb7.append(Log.getStackTraceString(th));
                    String sb8 = sb7.toString();
                    a.c("icecone", "classloader error:".concat(String.valueOf(sb8)));
                    UpTunnel.reportEvent(100054, sb8.getBytes());
                }
            }
            Class<?> a3 = a(hVar.d, dexClassLoader);
            if (a3 == null) {
                StringBuilder sb9 = new StringBuilder("class create error:");
                sb9.append(hVar.a);
                sb9.append(b());
                a.c("icecone", sb9.toString());
                return;
            }
            try {
                Object newInstance = a3.newInstance();
                if (newInstance == null || !(newInstance instanceof IIcecream)) {
                    StringBuilder sb10 = new StringBuilder("instance create faild:");
                    sb10.append(hVar.a);
                    sb10.append(b());
                    a.c("icecone", sb10.toString());
                } else {
                    this.c = (IIcecream) newInstance;
                    if (this.c != null) {
                        b.put(this.d.c, dexClassLoader);
                    }
                }
            } catch (Throwable th2) {
                StringBuilder sb11 = new StringBuilder();
                sb11.append(this.d.a);
                sb11.append(b());
                sb11.append(Log.getStackTraceString(th2));
                String sb12 = sb11.toString();
                a.c("icecone", "instance create error:".concat(String.valueOf(sb12)));
                UpTunnel.reportEvent(100056, sb12.getBytes());
            }
        }
    }

    private String a(String str, String str2) {
        String str3;
        String str4 = "unknow";
        try {
            File file = new File(str2);
            if (!file.exists() && !file.mkdirs()) {
                return str4;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(File.separator);
            sb.append(new File(str).getName().replace(".apk", FilePathHelper.SUFFIX_DOT_ZIP));
            String sb2 = sb.toString();
            com.amap.location.icecream.a.a.b(str, sb2);
            com.amap.location.icecream.a.a.a(sb2, str2);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str2);
            sb3.append(File.separator);
            sb3.append("lib");
            String sb4 = sb3.toString();
            if (!new File(sb4).exists()) {
                str4 = null;
            } else if (TextUtils.isEmpty(a) || TextUtils.equals(a, "unknow")) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(sb4);
                sb5.append(File.separator);
                sb5.append("armeabi-v7a");
                File file2 = new File(sb5.toString());
                if (file2.exists()) {
                    str3 = file2.getAbsolutePath();
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(sb4);
                    sb6.append(File.separator);
                    sb6.append("armeabi");
                    File file3 = new File(sb6.toString());
                    if (file3.exists()) {
                        str3 = file3.getAbsolutePath();
                    }
                }
                return str3;
            } else {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(sb4);
                sb7.append(File.separator);
                sb7.append(a);
                File file4 = new File(sb7.toString());
                if (file4.exists()) {
                    str3 = file4.getAbsolutePath();
                    return str3;
                }
            }
            str3 = str4;
            return str3;
        } catch (Throwable th) {
            StringBuilder sb8 = new StringBuilder("get lib error:");
            sb8.append(Log.getStackTraceString(th));
            a.c("icecone", sb8.toString());
        }
    }

    private Class<?> a(String str, DexClassLoader dexClassLoader) {
        if (dexClassLoader == null) {
            return null;
        }
        try {
            return dexClassLoader.loadClass(str);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.d.a);
            sb.append(b());
            sb.append(Log.getStackTraceString(th));
            String sb2 = sb.toString();
            a.c("icecone", "class loader error:".concat(String.valueOf(sb2)));
            UpTunnel.reportEvent(100055, sb2.getBytes());
            return null;
        }
    }

    private void a(Context context) {
        if (TextUtils.equals(a, "unknow")) {
            if (VERSION.SDK_INT >= 21) {
                try {
                    ApplicationInfo applicationInfo = context.getApplicationInfo();
                    Field declaredField = Class.forName(ApplicationInfo.class.getName()).getDeclaredField("primaryCpuAbi");
                    declaredField.setAccessible(true);
                    a = (String) declaredField.get(applicationInfo);
                } catch (Throwable th) {
                    StringBuilder sb = new StringBuilder("get cpu type error:");
                    sb.append(Log.getStackTraceString(th));
                    a.c("icecone", sb.toString());
                }
            }
            StringBuilder sb2 = new StringBuilder("get system cpu:");
            sb2.append(a);
            sb2.append(", sdk:");
            sb2.append(VERSION.SDK_INT);
            a.b("icecone", sb2.toString());
        }
    }

    private String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(a);
        try {
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(Build.BRAND);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(Build.MODEL);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(VERSION.SDK_INT);
        } catch (Throwable unused) {
        }
        sb.append(MergeUtil.SEPARATOR_KV);
        return sb.toString();
    }
}
