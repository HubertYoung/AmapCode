package com.xiaomi.metoknlp.devicediscover;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class m extends Thread {
    private WeakReference a;
    private WeakReference b;
    private final int c;

    public m(Handler handler, Context context, int i) {
        this.a = new WeakReference(handler);
        this.b = new WeakReference(context);
        this.c = i;
        start();
    }

    private Handler a() {
        if (this.a == null) {
            return null;
        }
        return (Handler) this.a.get();
    }

    public static void a(Context context, Handler handler, int i) {
        new m(handler, context, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0085 A[SYNTHETIC, Splitter:B:36:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0097 A[SYNTHETIC, Splitter:B:45:0x0097] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009e A[SYNTHETIC, Splitter:B:51:0x009e] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:33:0x0079=Splitter:B:33:0x0079, B:42:0x008b=Splitter:B:42:0x008b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r6, int r7, int r8, java.util.HashMap r9) {
        /*
            r5 = this;
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x008a, IOException -> 0x0078 }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x008a, IOException -> 0x0078 }
            java.lang.String r3 = "/proc/net/arp"
            r2.<init>(r3)     // Catch:{ FileNotFoundException -> 0x008a, IOException -> 0x0078 }
            r1.<init>(r2)     // Catch:{ FileNotFoundException -> 0x008a, IOException -> 0x0078 }
            r1.readLine()     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            int r8 = r8 - r7
            int r8 = r8 + 1
            java.lang.String[] r7 = new java.lang.String[r8]     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r0 = 0
            r2 = 0
        L_0x0017:
            if (r2 >= r8) goto L_0x0032
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r3.<init>()     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r3.append(r6)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            java.lang.String r4 = "."
            r3.append(r4)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r3.append(r2)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            java.lang.String r3 = r3.toString()     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r7[r2] = r3     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            int r2 = r2 + 1
            goto L_0x0017
        L_0x0032:
            java.lang.String r6 = r1.readLine()     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            if (r6 == 0) goto L_0x0069
            java.lang.String r8 = "[ ]+"
            java.lang.String[] r6 = r6.split(r8)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            int r8 = r6.length     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r2 = 6
            if (r8 < r2) goto L_0x0032
            r8 = r6[r0]     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r2 = 3
            r6 = r6[r2]     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            int r2 = r7.length     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            r3 = 0
        L_0x0049:
            if (r3 >= r2) goto L_0x0032
            r4 = r7[r3]     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            boolean r4 = r4.equals(r8)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            if (r4 == 0) goto L_0x0066
            java.lang.String r4 = "..:..:..:..:..:.."
            boolean r4 = r6.matches(r4)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            if (r4 == 0) goto L_0x0066
            java.lang.String r4 = "00:00:00:00:00:00"
            boolean r4 = r4.equals(r6)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
            if (r4 != 0) goto L_0x0066
            r9.put(r8, r6)     // Catch:{ FileNotFoundException -> 0x0072, IOException -> 0x006f, all -> 0x006d }
        L_0x0066:
            int r3 = r3 + 1
            goto L_0x0049
        L_0x0069:
            r1.close()     // Catch:{ IOException -> 0x006c }
        L_0x006c:
            return
        L_0x006d:
            r6 = move-exception
            goto L_0x009c
        L_0x006f:
            r6 = move-exception
            r0 = r1
            goto L_0x0079
        L_0x0072:
            r6 = move-exception
            r0 = r1
            goto L_0x008b
        L_0x0075:
            r6 = move-exception
            r1 = r0
            goto L_0x009c
        L_0x0078:
            r6 = move-exception
        L_0x0079:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0075 }
            java.lang.String r8 = "get address read file error:"
            r7.<init>(r8)     // Catch:{ all -> 0x0075 }
            r7.append(r6)     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x0089
            r0.close()     // Catch:{ IOException -> 0x0089 }
            return
        L_0x0089:
            return
        L_0x008a:
            r6 = move-exception
        L_0x008b:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0075 }
            java.lang.String r8 = "get address file not found:"
            r7.<init>(r8)     // Catch:{ all -> 0x0075 }
            r7.append(r6)     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x009b
            r0.close()     // Catch:{ IOException -> 0x009b }
            return
        L_0x009b:
            return
        L_0x009c:
            if (r1 == 0) goto L_0x00a1
            r1.close()     // Catch:{ IOException -> 0x00a1 }
        L_0x00a1:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.metoknlp.devicediscover.m.a(java.lang.String, int, int, java.util.HashMap):void");
    }

    private void a(HashMap hashMap) {
        Handler a2 = a();
        Message obtainMessage = a2.obtainMessage(this.c);
        obtainMessage.obj = hashMap;
        a2.sendMessage(obtainMessage);
    }

    private Context b() {
        if (this.a == null) {
            return null;
        }
        return (Context) this.b.get();
    }

    private HashMap c() {
        StringBuilder sb;
        HashMap hashMap = new HashMap();
        String b2 = j.b(b());
        if (b2 == null) {
            return hashMap;
        }
        String substring = b2.substring(0, b2.lastIndexOf("."));
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(40);
        try {
            Runnable[] runnableArr = new Runnable[255];
            for (int i = 1; i < 255; i++) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(substring);
                sb2.append(".");
                sb2.append(i);
                runnableArr[i] = new e(this, sb2.toString());
            }
            for (int i2 = 1; i2 < 255; i2++) {
                newFixedThreadPool.execute(runnableArr[i2]);
            }
            try {
                newFixedThreadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e = e;
                sb = new StringBuilder("find device error:");
            }
        } catch (Exception e2) {
            new StringBuilder("multi-threading error: ").append(e2);
            try {
                newFixedThreadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
            } catch (Exception e3) {
                e = e3;
                sb = new StringBuilder("find device error:");
            }
        } finally {
            newFixedThreadPool.shutdown();
            try {
                newFixedThreadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
            } catch (Exception e4) {
                new StringBuilder("find device error:").append(e4);
            }
        }
        a(substring, 1, 255, hashMap);
        return hashMap;
        sb.append(e);
        a(substring, 1, 255, hashMap);
        return hashMap;
    }

    public void run() {
        a(c());
    }
}
