package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.module.PushChannelRegion;
import com.xiaomi.xmpush.thrift.g;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class a {
    private static PushChannelRegion q;
    private static volatile a r;
    private final Object a = new Object();
    private final String b = "mipush_region";
    private final String c = "mipush_region.lock";
    private final String d = "success.";
    private final String e = "fail.";
    private final String f = ".";
    /* access modifiers changed from: private */
    public final Object g = new Object();
    /* access modifiers changed from: private */
    public final Object h = new Object();
    /* access modifiers changed from: private */
    public final Object i = new Object();
    /* access modifiers changed from: private */
    public Context j;
    private String k;
    /* access modifiers changed from: private */
    public String l = null;
    private boolean m = false;
    private boolean n = false;
    /* access modifiers changed from: private */
    public boolean o = false;
    /* access modifiers changed from: private */
    public boolean p = false;

    public a(Context context) {
        this.j = context;
    }

    public static a a(Context context) {
        if (r == null) {
            synchronized (a.class) {
                try {
                    if (r == null) {
                        r = new a(context);
                    }
                }
            }
        }
        return r;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x004a A[SYNTHETIC, Splitter:B:31:0x004a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.content.Context r6, com.xiaomi.push.service.module.PushChannelRegion r7) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.a
            monitor-enter(r0)
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            java.io.File r3 = r6.getFilesDir()     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            java.lang.String r4 = "mipush_region.lock"
            r2.<init>(r3, r4)     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            com.xiaomi.channel.commonutils.file.a.a(r2)     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            java.lang.String r4 = "rw"
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x0043, all -> 0x0040 }
            java.nio.channels.FileChannel r2 = r3.getChannel()     // Catch:{ Exception -> 0x003e }
            java.nio.channels.FileLock r2 = r2.lock()     // Catch:{ Exception -> 0x003e }
            r5.b(r6, r7)     // Catch:{ Exception -> 0x003b, all -> 0x0038 }
            if (r2 == 0) goto L_0x0034
            boolean r6 = r2.isValid()     // Catch:{ all -> 0x006d }
            if (r6 == 0) goto L_0x0034
            r2.release()     // Catch:{ IOException -> 0x0030 }
            goto L_0x0034
        L_0x0030:
            r6 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch:{ all -> 0x006d }
        L_0x0034:
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ all -> 0x006d }
            goto L_0x0059
        L_0x0038:
            r6 = move-exception
            r1 = r2
            goto L_0x005c
        L_0x003b:
            r6 = move-exception
            r1 = r2
            goto L_0x0045
        L_0x003e:
            r6 = move-exception
            goto L_0x0045
        L_0x0040:
            r6 = move-exception
            r3 = r1
            goto L_0x005c
        L_0x0043:
            r6 = move-exception
            r3 = r1
        L_0x0045:
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch:{ all -> 0x005b }
            if (r1 == 0) goto L_0x0034
            boolean r6 = r1.isValid()     // Catch:{ all -> 0x006d }
            if (r6 == 0) goto L_0x0034
            r1.release()     // Catch:{ IOException -> 0x0054 }
            goto L_0x0034
        L_0x0054:
            r6 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch:{ all -> 0x006d }
            goto L_0x0034
        L_0x0059:
            monitor-exit(r0)     // Catch:{ all -> 0x006d }
            return
        L_0x005b:
            r6 = move-exception
        L_0x005c:
            if (r1 == 0) goto L_0x006f
            boolean r7 = r1.isValid()     // Catch:{ all -> 0x006d }
            if (r7 == 0) goto L_0x006f
            r1.release()     // Catch:{ IOException -> 0x0068 }
            goto L_0x006f
        L_0x0068:
            r7 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r7)     // Catch:{ all -> 0x006d }
            goto L_0x006f
        L_0x006d:
            r6 = move-exception
            goto L_0x0073
        L_0x006f:
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ all -> 0x006d }
            throw r6     // Catch:{ all -> 0x006d }
        L_0x0073:
            monitor-exit(r0)     // Catch:{ all -> 0x006d }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.a.a(android.content.Context, com.xiaomi.push.service.module.PushChannelRegion):void");
    }

    private void a(String str, String str2) {
        if (an.a(this.j).a(g.GlobalRegionIOSwitch.a(), true)) {
            bf.a(this.j, str, "region_io", 1, str2);
        }
    }

    private PushChannelRegion b() {
        return q;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0063 A[SYNTHETIC, Splitter:B:35:0x0063] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String b(android.content.Context r6) {
        /*
            r5 = this;
            java.io.File r0 = new java.io.File
            java.io.File r1 = r6.getFilesDir()
            java.lang.String r2 = "mipush_region"
            r0.<init>(r1, r2)
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 != 0) goto L_0x0018
            java.lang.String r6 = "Region no ready file to get data."
            com.xiaomi.channel.commonutils.logger.b.a(r6)
            return r1
        L_0x0018:
            java.lang.Object r0 = r5.a
            monitor-enter(r0)
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            java.io.File r3 = r6.getFilesDir()     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            java.lang.String r4 = "mipush_region.lock"
            r2.<init>(r3, r4)     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            com.xiaomi.channel.commonutils.file.a.a(r2)     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            java.lang.String r4 = "rw"
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x005b, all -> 0x0058 }
            java.nio.channels.FileChannel r2 = r3.getChannel()     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.nio.channels.FileLock r2 = r2.lock()     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.lang.String r6 = r5.c(r6)     // Catch:{ Exception -> 0x0051 }
            if (r2 == 0) goto L_0x004c
            boolean r1 = r2.isValid()     // Catch:{ all -> 0x0089 }
            if (r1 == 0) goto L_0x004c
            r2.release()     // Catch:{ IOException -> 0x0048 }
            goto L_0x004c
        L_0x0048:
            r1 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r1)     // Catch:{ all -> 0x0089 }
        L_0x004c:
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ all -> 0x0089 }
            monitor-exit(r0)     // Catch:{ all -> 0x0089 }
            return r6
        L_0x0051:
            r6 = move-exception
            goto L_0x005e
        L_0x0053:
            r6 = move-exception
            goto L_0x0078
        L_0x0055:
            r6 = move-exception
            r2 = r1
            goto L_0x005e
        L_0x0058:
            r6 = move-exception
            r3 = r1
            goto L_0x0078
        L_0x005b:
            r6 = move-exception
            r2 = r1
            r3 = r2
        L_0x005e:
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch:{ all -> 0x0076 }
            if (r2 == 0) goto L_0x0071
            boolean r6 = r2.isValid()     // Catch:{ all -> 0x0089 }
            if (r6 == 0) goto L_0x0071
            r2.release()     // Catch:{ IOException -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r6 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r6)     // Catch:{ all -> 0x0089 }
        L_0x0071:
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ all -> 0x0089 }
            monitor-exit(r0)     // Catch:{ all -> 0x0089 }
            return r1
        L_0x0076:
            r6 = move-exception
            r1 = r2
        L_0x0078:
            if (r1 == 0) goto L_0x008b
            boolean r2 = r1.isValid()     // Catch:{ all -> 0x0089 }
            if (r2 == 0) goto L_0x008b
            r1.release()     // Catch:{ IOException -> 0x0084 }
            goto L_0x008b
        L_0x0084:
            r1 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r1)     // Catch:{ all -> 0x0089 }
            goto L_0x008b
        L_0x0089:
            r6 = move-exception
            goto L_0x008f
        L_0x008b:
            com.xiaomi.channel.commonutils.file.a.a(r3)     // Catch:{ all -> 0x0089 }
            throw r6     // Catch:{ all -> 0x0089 }
        L_0x008f:
            monitor-exit(r0)     // Catch:{ all -> 0x0089 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.a.b(android.content.Context):java.lang.String");
    }

    private void b(Context context, PushChannelRegion pushChannelRegion) {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = context.openFileOutput("mipush_region", 32768);
            try {
                fileOutputStream2.write(pushChannelRegion.name().getBytes());
                fileOutputStream2.flush();
                com.xiaomi.channel.commonutils.file.a.a((OutputStream) fileOutputStream2);
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = fileOutputStream2;
                try {
                    b.a((Throwable) e);
                    com.xiaomi.channel.commonutils.file.a.a((OutputStream) fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    com.xiaomi.channel.commonutils.file.a.a((OutputStream) fileOutputStream2);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                com.xiaomi.channel.commonutils.file.a.a((OutputStream) fileOutputStream2);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            b.a((Throwable) e);
            com.xiaomi.channel.commonutils.file.a.a((OutputStream) fileOutputStream);
        }
    }

    private void b(PushChannelRegion pushChannelRegion) {
        q = pushChannelRegion;
    }

    private String c(Context context) {
        InputStream inputStream;
        BufferedReader bufferedReader;
        Throwable e2;
        String str = "";
        try {
            inputStream = context.openFileInput("mipush_region");
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(readLine);
                        str = sb.toString();
                    } catch (Exception e3) {
                        e2 = e3;
                        try {
                            b.a(e2);
                            com.xiaomi.channel.commonutils.file.a.a(inputStream);
                            com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                            return str;
                        } catch (Throwable th) {
                            th = th;
                        }
                    }
                }
            } catch (Exception e4) {
                Throwable th2 = e4;
                bufferedReader = null;
                e2 = th2;
                b.a(e2);
                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                return str;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                throw th;
            }
        } catch (Exception e5) {
            bufferedReader = null;
            e2 = e5;
            inputStream = null;
            b.a(e2);
            com.xiaomi.channel.commonutils.file.a.a(inputStream);
            com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
            return str;
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            bufferedReader = null;
            com.xiaomi.channel.commonutils.file.a.a(inputStream);
            com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
            throw th;
        }
        com.xiaomi.channel.commonutils.file.a.a(inputStream);
        com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
        return str;
    }

    public String a() {
        String a2 = a(true);
        return (!TextUtils.isEmpty(a2) || b() == null) ? a2 : b().name();
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0034 */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0049  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a(boolean r8) {
        /*
            r7 = this;
            boolean r0 = r7.m
            if (r0 != 0) goto L_0x00bc
            java.lang.Thread r0 = new java.lang.Thread
            com.xiaomi.push.service.b r1 = new com.xiaomi.push.service.b
            r1.<init>(r7)
            r0.<init>(r1)
            r0.start()
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.Object r2 = r7.g
            monitor-enter(r2)
            java.lang.Object r3 = r7.g     // Catch:{ InterruptedException -> 0x0034 }
            android.content.Context r4 = r7.j     // Catch:{ InterruptedException -> 0x0034 }
            com.xiaomi.push.service.an r4 = com.xiaomi.push.service.an.a(r4)     // Catch:{ InterruptedException -> 0x0034 }
            com.xiaomi.xmpush.thrift.g r5 = com.xiaomi.xmpush.thrift.g.GlobalRegionIOWait     // Catch:{ InterruptedException -> 0x0034 }
            int r5 = r5.a()     // Catch:{ InterruptedException -> 0x0034 }
            r6 = 100
            int r4 = r4.a(r5, r6)     // Catch:{ InterruptedException -> 0x0034 }
            long r4 = (long) r4     // Catch:{ InterruptedException -> 0x0034 }
            r3.wait(r4)     // Catch:{ InterruptedException -> 0x0034 }
            goto L_0x0034
        L_0x0031:
            r8 = move-exception
            goto L_0x00ba
        L_0x0034:
            monitor-exit(r2)     // Catch:{ all -> 0x0031 }
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 - r0
            r0 = 1
            r7.m = r0
            java.lang.Object r0 = r7.i
            monitor-enter(r0)
            boolean r1 = r7.o     // Catch:{ all -> 0x00b7 }
            java.lang.String r4 = r7.l     // Catch:{ all -> 0x00b7 }
            r7.k = r4     // Catch:{ all -> 0x00b7 }
            monitor-exit(r0)     // Catch:{ all -> 0x00b7 }
            if (r8 == 0) goto L_0x00bc
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r2)
            java.lang.String r0 = "."
            r8.append(r0)
            android.content.Context r0 = r7.j
            com.xiaomi.push.service.bc r0 = com.xiaomi.push.service.bc.a(r0)
            java.lang.String r2 = "mipush_extra"
            java.lang.String r3 = "mipush_registed"
            r4 = 0
            boolean r0 = r0.a(r2, r3, r4)
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            if (r1 == 0) goto L_0x0098
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.content.Context r1 = r7.j
            java.lang.String r1 = r1.getPackageName()
            r0.append(r1)
            java.lang.String r1 = " get region success."
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.c(r0)
            java.lang.String r0 = "category_region_write"
            java.lang.String r1 = "success."
        L_0x008c:
            java.lang.String r8 = java.lang.String.valueOf(r8)
            java.lang.String r8 = r1.concat(r8)
            r7.a(r0, r8)
            goto L_0x00bc
        L_0x0098:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.content.Context r1 = r7.j
            java.lang.String r1 = r1.getPackageName()
            r0.append(r1)
            java.lang.String r1 = " get region fail."
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.a(r0)
            java.lang.String r0 = "category_region_write"
            java.lang.String r1 = "fail."
            goto L_0x008c
        L_0x00b7:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00b7 }
            throw r8
        L_0x00ba:
            monitor-exit(r2)     // Catch:{ all -> 0x0031 }
            throw r8
        L_0x00bc:
            java.lang.String r8 = r7.k
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.a.a(boolean):java.lang.String");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0047 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x009d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.xiaomi.push.service.module.PushChannelRegion r13) {
        /*
            r12 = this;
            r12.b(r13)
            long r6 = java.lang.System.currentTimeMillis()
            android.content.Context r0 = r12.j
            com.xiaomi.push.service.an r0 = com.xiaomi.push.service.an.a(r0)
            com.xiaomi.xmpush.thrift.g r1 = com.xiaomi.xmpush.thrift.g.GlobalRegionIOWait
            int r1 = r1.a()
            r2 = 100
            int r8 = r0.a(r1, r2)
            boolean r0 = r12.n
            if (r0 != 0) goto L_0x00c4
            r9 = 0
            java.lang.String r0 = r12.a(r9)
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x00c4
            java.lang.Thread r10 = new java.lang.Thread
            com.xiaomi.push.service.c r11 = new com.xiaomi.push.service.c
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r6
            r5 = r8
            r0.<init>(r1, r2, r3, r5)
            r10.<init>(r11)
            r10.start()
            java.lang.Object r13 = r12.h
            monitor-enter(r13)
            java.lang.Object r0 = r12.h     // Catch:{ InterruptedException -> 0x0047 }
            long r1 = (long) r8     // Catch:{ InterruptedException -> 0x0047 }
            r0.wait(r1)     // Catch:{ InterruptedException -> 0x0047 }
            goto L_0x0047
        L_0x0044:
            r0 = move-exception
            goto L_0x00c2
        L_0x0047:
            monitor-exit(r13)     // Catch:{ all -> 0x0044 }
            long r0 = java.lang.System.currentTimeMillis()
            long r0 = r0 - r6
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r0)
            java.lang.String r0 = "."
            r13.append(r0)
            android.content.Context r0 = r12.j
            com.xiaomi.push.service.bc r0 = com.xiaomi.push.service.bc.a(r0)
            java.lang.String r1 = "mipush_extra"
            java.lang.String r2 = "mipush_registed"
            boolean r0 = r0.a(r1, r2, r9)
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            boolean r0 = r12.p
            if (r0 == 0) goto L_0x009d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.content.Context r1 = r12.j
            java.lang.String r1 = r1.getPackageName()
            r0.append(r1)
            java.lang.String r1 = " set region success."
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.c(r0)
            java.lang.String r0 = "category_region_read"
            java.lang.String r1 = "success."
        L_0x0091:
            java.lang.String r13 = java.lang.String.valueOf(r13)
            java.lang.String r13 = r1.concat(r13)
            r12.a(r0, r13)
            goto L_0x00bc
        L_0x009d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            android.content.Context r1 = r12.j
            java.lang.String r1 = r1.getPackageName()
            r0.append(r1)
            java.lang.String r1 = " set region fail."
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.a(r0)
            java.lang.String r0 = "category_region_read"
            java.lang.String r1 = "fail."
            goto L_0x0091
        L_0x00bc:
            r13 = 1
            r12.n = r13
            r12.m = r9
            return
        L_0x00c2:
            monitor-exit(r13)     // Catch:{ all -> 0x0044 }
            throw r0
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.a.a(com.xiaomi.push.service.module.PushChannelRegion):void");
    }
}
