package com.alibaba.wireless.security.framework.utils;

import android.content.Context;
import android.os.Process;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.UTMini;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserTrackMethodJniBridge {
    private static Context a;
    private static String b;
    private static int c;
    private static int d;
    private static int e;
    private static int f;
    private static Class g;
    private static Class h;
    private static Class i;
    private static Constructor j;
    private static Method k;
    private static Method l;
    private static Method m;
    private static Method n;
    private static final char[] o = "0123456789abcdef".toCharArray();

    private static synchronized String a() {
        String substring;
        synchronized (UserTrackMethodJniBridge.class) {
            if (b == null || b.length() == 0) {
                b = b();
            }
            substring = b.substring(0, b.length() / 8);
        }
        return substring;
    }

    private static String a(String str) {
        if (!(str == null || str.length() == 0)) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        r2 = "";
        return "";
    }

    public static int addUtRecord(String str, int i2, int i3, String str2, long j2, String str3, String str4, String str5, String str6, String str7) {
        if (!(utAvaiable() == 0 || str == null || str.length() == 0)) {
            if (str2 == null) {
                str2 = "";
            }
            try {
                String valueOf = String.valueOf(i2 % 100);
                HashMap hashMap = new HashMap();
                hashMap.put("plugin", String.valueOf(i3));
                hashMap.put("pid", String.valueOf(Process.myPid()));
                hashMap.put("tid", String.valueOf(Thread.currentThread().getId()));
                hashMap.put("time", String.valueOf(j2));
                if (d == 0) {
                    c = f.c(a) ? 1 : 0;
                    d = 1;
                }
                hashMap.put("ui", String.valueOf(c));
                hashMap.put(Constants.KEY_SID, a());
                hashMap.put("uuid", b());
                hashMap.put("msg", a(str3));
                hashMap.put("rsv1", a(str4));
                hashMap.put("rsv2", a(str5));
                hashMap.put("rsv3", a(str6));
                hashMap.put("rsv4", a(str7));
                Object newInstance = j.newInstance(new Object[]{"Page_SecurityGuardSDK", Integer.valueOf(UTMini.EVENTID_AGOO), str, str2, valueOf, hashMap});
                if (newInstance == null) {
                    return 0;
                }
                Map map = (Map) k.invoke(newInstance, new Object[0]);
                if (map == null || map.size() == 0) {
                    return 0;
                }
                Object invoke = l.invoke(h, new Object[0]);
                if (invoke == null) {
                    return 0;
                }
                Object invoke2 = m.invoke(invoke, new Object[0]);
                if (invoke2 == null) {
                    return 0;
                }
                n.invoke(invoke2, new Object[]{map});
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    private static String b() {
        try {
            String uuid = UUID.randomUUID().toString();
            String valueOf = String.valueOf(System.nanoTime());
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            StringBuilder sb = new StringBuilder();
            sb.append(uuid);
            sb.append(valueOf);
            return bytesToHex(instance.digest(sb.toString().getBytes("UTF-8")));
        } catch (Exception unused) {
            return "";
        }
    }

    public static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2] & 255;
            int i3 = i2 * 2;
            cArr[i3] = o[b2 >>> 4];
            cArr[i3 + 1] = o[b2 & 15];
        }
        return new String(cArr);
    }

    public static String getStackTrace(int i2, int i3) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace == null || stackTrace.length <= 0 || i2 <= 0 || i3 <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i4 = 0;
        for (int i5 = 0; i5 < stackTrace.length && i4 < i3 && sb.length() < i2; i5++) {
            if (i5 > 1) {
                i4++;
                StackTraceElement stackTraceElement = stackTrace[i5];
                StringBuilder sb2 = new StringBuilder();
                sb2.append(stackTraceElement.getClassName());
                sb2.append(".");
                sb2.append(stackTraceElement.getMethodName());
                sb.append(sb2.toString());
                if (i5 < stackTrace.length - 1) {
                    sb.append(MetaRecord.LOG_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    public static void init(Context context) {
        if (context != null) {
            a = context;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int utAvaiable() {
        /*
            int r0 = f
            if (r0 != 0) goto L_0x008a
            java.lang.Class<com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge> r0 = com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.class
            monitor-enter(r0)
            int r1 = f     // Catch:{ all -> 0x0087 }
            if (r1 != 0) goto L_0x0085
            r1 = 1
            java.lang.String r2 = "com.ut.mini.internal.UTOriginalCustomHitBuilder"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x0083 }
            g = r2     // Catch:{ ClassNotFoundException -> 0x0083 }
            java.lang.String r2 = "com.ut.mini.UTAnalytics"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x0083 }
            h = r2     // Catch:{ ClassNotFoundException -> 0x0083 }
            java.lang.String r2 = "com.ut.mini.UTTracker"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x0083 }
            i = r2     // Catch:{ ClassNotFoundException -> 0x0083 }
            java.lang.Class r2 = g     // Catch:{  }
            r3 = 6
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{  }
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            r5 = 0
            r3[r5] = r4     // Catch:{  }
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{  }
            r3[r1] = r4     // Catch:{  }
            r4 = 2
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r3[r4] = r6     // Catch:{  }
            r4 = 3
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r3[r4] = r6     // Catch:{  }
            r4 = 4
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r3[r4] = r6     // Catch:{  }
            r4 = 5
            java.lang.Class<java.util.Map> r6 = java.util.Map.class
            r3[r4] = r6     // Catch:{  }
            java.lang.reflect.Constructor r2 = r2.getConstructor(r3)     // Catch:{  }
            j = r2     // Catch:{  }
            java.lang.Class r2 = g     // Catch:{  }
            java.lang.String r3 = "build"
            java.lang.Class[] r4 = new java.lang.Class[r5]     // Catch:{  }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{  }
            k = r2     // Catch:{  }
            java.lang.Class r2 = h     // Catch:{  }
            java.lang.String r3 = "getInstance"
            java.lang.Class[] r4 = new java.lang.Class[r5]     // Catch:{  }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{  }
            l = r2     // Catch:{  }
            java.lang.Class r2 = h     // Catch:{  }
            java.lang.String r3 = "getDefaultTracker"
            java.lang.Class[] r4 = new java.lang.Class[r5]     // Catch:{  }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{  }
            m = r2     // Catch:{  }
            java.lang.Class r2 = i     // Catch:{  }
            java.lang.String r3 = "send"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{  }
            java.lang.Class<java.util.Map> r6 = java.util.Map.class
            r4[r5] = r6     // Catch:{  }
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{  }
            n = r2     // Catch:{  }
            e = r1     // Catch:{ all -> 0x0087 }
        L_0x0083:
            f = r1     // Catch:{ all -> 0x0087 }
        L_0x0085:
            monitor-exit(r0)     // Catch:{ all -> 0x0087 }
            goto L_0x008a
        L_0x0087:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0087 }
            throw r1
        L_0x008a:
            int r0 = e
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.utAvaiable():int");
    }
}
