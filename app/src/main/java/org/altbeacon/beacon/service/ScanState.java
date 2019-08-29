package org.altbeacon.beacon.service;

import android.content.Context;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.j;

public class ScanState implements Serializable {
    public static int a = 300000;
    private static final String b = ScanState.class.getSimpleName();
    private Map<Region, f> c = new HashMap();
    private transient MonitoringStatus d;
    private Set<j> e = new HashSet();
    private d f = new d();
    private long g;
    private long h;
    private long i;
    private long j;
    private boolean k;
    private long l = 0;
    private transient Context m;

    public final Boolean a() {
        return Boolean.valueOf(this.k);
    }

    public final Long b() {
        return Long.valueOf(this.h);
    }

    public final Long c() {
        return Long.valueOf(this.j);
    }

    public final Long d() {
        return Long.valueOf(this.g);
    }

    public final Long e() {
        return Long.valueOf(this.i);
    }

    public ScanState(Context context) {
        this.m = context;
    }

    public final MonitoringStatus f() {
        return this.d;
    }

    public final Map<Region, f> g() {
        return this.c;
    }

    public final d h() {
        return this.f;
    }

    public final Set<j> i() {
        return this.e;
    }

    public final void a(long time) {
        this.l = time;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0027 A[SYNTHETIC, Splitter:B:14:0x0027] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0030 A[Catch:{ IOException -> 0x00ae, all -> 0x00ba }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a3 A[SYNTHETIC, Splitter:B:39:0x00a3] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a8 A[SYNTHETIC, Splitter:B:42:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00cf A[Catch:{ all -> 0x00f8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00db A[SYNTHETIC, Splitter:B:59:0x00db] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00e0 A[SYNTHETIC, Splitter:B:62:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e6 A[SYNTHETIC, Splitter:B:65:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0144  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:36:0x0097=Splitter:B:36:0x0097, B:54:0x00cb=Splitter:B:54:0x00cb} */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:74:0x0103=Splitter:B:74:0x0103, B:28:0x0086=Splitter:B:28:0x0086, B:16:0x002c=Splitter:B:16:0x002c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.altbeacon.beacon.service.ScanState a(android.content.Context r12) {
        /*
            r5 = 0
            java.lang.Class<org.altbeacon.beacon.service.ScanState> r8 = org.altbeacon.beacon.service.ScanState.class
            monitor-enter(r8)
            r2 = 0
            r3 = 0
            java.lang.String r7 = "android-beacon-library-scan-state"
            java.io.FileInputStream r2 = r12.openFileInput(r7)     // Catch:{ FileNotFoundException -> 0x0096, IOException | ClassCastException | ClassNotFoundException -> 0x00ca }
            java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch:{ FileNotFoundException -> 0x0096, IOException | ClassCastException | ClassNotFoundException -> 0x00ca }
            r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0096, IOException | ClassCastException | ClassNotFoundException -> 0x00ca }
            java.lang.Object r7 = r4.readObject()     // Catch:{ FileNotFoundException -> 0x0140, IOException | ClassCastException | ClassNotFoundException -> 0x013d, all -> 0x013a }
            r0 = r7
            org.altbeacon.beacon.service.ScanState r0 = (org.altbeacon.beacon.service.ScanState) r0     // Catch:{ FileNotFoundException -> 0x0140, IOException | ClassCastException | ClassNotFoundException -> 0x013d, all -> 0x013a }
            r5 = r0
            r5.m = r12     // Catch:{ FileNotFoundException -> 0x0140, IOException | ClassCastException | ClassNotFoundException -> 0x013d, all -> 0x013a }
            if (r2 == 0) goto L_0x0020
            r2.close()     // Catch:{ IOException -> 0x0078 }
        L_0x0020:
            r4.close()     // Catch:{ IOException -> 0x0088 }
            r3 = r4
            r6 = r5
        L_0x0025:
            if (r6 != 0) goto L_0x0144
            org.altbeacon.beacon.service.ScanState r5 = new org.altbeacon.beacon.service.ScanState     // Catch:{ all -> 0x0136 }
            r5.<init>(r12)     // Catch:{ all -> 0x0136 }
        L_0x002c:
            org.altbeacon.beacon.service.d r7 = r5.f     // Catch:{ all -> 0x00ba }
            if (r7 != 0) goto L_0x0037
            org.altbeacon.beacon.service.d r7 = new org.altbeacon.beacon.service.d     // Catch:{ all -> 0x00ba }
            r7.<init>()     // Catch:{ all -> 0x00ba }
            r5.f = r7     // Catch:{ all -> 0x00ba }
        L_0x0037:
            org.altbeacon.beacon.service.MonitoringStatus r7 = org.altbeacon.beacon.service.MonitoringStatus.a(r12)     // Catch:{ all -> 0x00ba }
            r5.d = r7     // Catch:{ all -> 0x00ba }
            java.lang.String r7 = b     // Catch:{ all -> 0x00ba }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ba }
            java.lang.String r10 = "Scan state restore regions: monitored="
            r9.<init>(r10)     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.service.MonitoringStatus r10 = r5.f()     // Catch:{ all -> 0x00ba }
            java.util.Set r10 = r10.a()     // Catch:{ all -> 0x00ba }
            int r10 = r10.size()     // Catch:{ all -> 0x00ba }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x00ba }
            java.lang.String r10 = " ranged="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x00ba }
            java.util.Map r10 = r5.g()     // Catch:{ all -> 0x00ba }
            java.util.Set r10 = r10.keySet()     // Catch:{ all -> 0x00ba }
            int r10 = r10.size()     // Catch:{ all -> 0x00ba }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00ba }
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.b.d.a(r7, r9, r10)     // Catch:{ all -> 0x00ba }
            monitor-exit(r8)     // Catch:{ all -> 0x00ba }
            return r5
        L_0x0078:
            r7 = move-exception
            java.lang.String r7 = b     // Catch:{ all -> 0x0084 }
            java.lang.String r9 = "close inputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x0084 }
            org.altbeacon.beacon.b.d.d(r7, r9, r10)     // Catch:{ all -> 0x0084 }
            goto L_0x0020
        L_0x0084:
            r7 = move-exception
            r3 = r4
        L_0x0086:
            monitor-exit(r8)     // Catch:{ all -> 0x00ba }
            throw r7
        L_0x0088:
            r7 = move-exception
            java.lang.String r7 = b     // Catch:{ all -> 0x0084 }
            java.lang.String r9 = "close objectInputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x0084 }
            org.altbeacon.beacon.b.d.d(r7, r9, r10)     // Catch:{ all -> 0x0084 }
            r3 = r4
            r6 = r5
            goto L_0x0025
        L_0x0096:
            r7 = move-exception
        L_0x0097:
            java.lang.String r7 = b     // Catch:{ all -> 0x00f8 }
            java.lang.String r9 = "Serialized ScanState does not exist.  This may be normal on first run."
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00f8 }
            org.altbeacon.beacon.b.d.c(r7, r9, r10)     // Catch:{ all -> 0x00f8 }
            if (r2 == 0) goto L_0x00a6
            r2.close()     // Catch:{ IOException -> 0x00ae }
        L_0x00a6:
            if (r3 == 0) goto L_0x0147
            r3.close()     // Catch:{ IOException -> 0x00bc }
            r6 = r5
            goto L_0x0025
        L_0x00ae:
            r7 = move-exception
            java.lang.String r7 = b     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "close inputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.b.d.d(r7, r9, r10)     // Catch:{ all -> 0x00ba }
            goto L_0x00a6
        L_0x00ba:
            r7 = move-exception
            goto L_0x0086
        L_0x00bc:
            r7 = move-exception
            java.lang.String r7 = b     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "close objectInputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.b.d.d(r7, r9, r10)     // Catch:{ all -> 0x00ba }
            r6 = r5
            goto L_0x0025
        L_0x00ca:
            r1 = move-exception
        L_0x00cb:
            boolean r7 = r1 instanceof java.io.InvalidClassException     // Catch:{ all -> 0x00f8 }
            if (r7 == 0) goto L_0x00e6
            java.lang.String r7 = b     // Catch:{ all -> 0x00f8 }
            java.lang.String r9 = "Serialized ScanState has wrong class. Just ignoring saved state..."
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00f8 }
            org.altbeacon.beacon.b.d.a(r7, r9, r10)     // Catch:{ all -> 0x00f8 }
        L_0x00d9:
            if (r2 == 0) goto L_0x00de
            r2.close()     // Catch:{ IOException -> 0x0104 }
        L_0x00de:
            if (r3 == 0) goto L_0x0147
            r3.close()     // Catch:{ IOException -> 0x0110 }
            r6 = r5
            goto L_0x0025
        L_0x00e6:
            java.lang.String r7 = b     // Catch:{ all -> 0x00f8 }
            java.lang.String r9 = "Deserialization exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00f8 }
            org.altbeacon.beacon.b.d.d(r7, r9, r10)     // Catch:{ all -> 0x00f8 }
            java.lang.String r7 = b     // Catch:{ all -> 0x00f8 }
            java.lang.String r9 = "error: "
            android.util.Log.e(r7, r9, r1)     // Catch:{ all -> 0x00f8 }
            goto L_0x00d9
        L_0x00f8:
            r7 = move-exception
        L_0x00f9:
            if (r2 == 0) goto L_0x00fe
            r2.close()     // Catch:{ IOException -> 0x011e }
        L_0x00fe:
            if (r3 == 0) goto L_0x0103
            r3.close()     // Catch:{ IOException -> 0x012a }
        L_0x0103:
            throw r7     // Catch:{ all -> 0x00ba }
        L_0x0104:
            r7 = move-exception
            java.lang.String r7 = b     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "close inputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.b.d.d(r7, r9, r10)     // Catch:{ all -> 0x00ba }
            goto L_0x00de
        L_0x0110:
            r7 = move-exception
            java.lang.String r7 = b     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "close objectInputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.b.d.d(r7, r9, r10)     // Catch:{ all -> 0x00ba }
            r6 = r5
            goto L_0x0025
        L_0x011e:
            r9 = move-exception
            java.lang.String r9 = b     // Catch:{ all -> 0x00ba }
            java.lang.String r10 = "close inputStream exception"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.b.d.d(r9, r10, r11)     // Catch:{ all -> 0x00ba }
            goto L_0x00fe
        L_0x012a:
            r9 = move-exception
            java.lang.String r9 = b     // Catch:{ all -> 0x00ba }
            java.lang.String r10 = "close objectInputStream exception"
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00ba }
            org.altbeacon.beacon.b.d.d(r9, r10, r11)     // Catch:{ all -> 0x00ba }
            goto L_0x0103
        L_0x0136:
            r7 = move-exception
            r5 = r6
            goto L_0x0086
        L_0x013a:
            r7 = move-exception
            r3 = r4
            goto L_0x00f9
        L_0x013d:
            r1 = move-exception
            r3 = r4
            goto L_0x00cb
        L_0x0140:
            r7 = move-exception
            r3 = r4
            goto L_0x0097
        L_0x0144:
            r5 = r6
            goto L_0x002c
        L_0x0147:
            r6 = r5
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: org.altbeacon.beacon.service.ScanState.a(android.content.Context):org.altbeacon.beacon.service.ScanState");
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00c7 A[SYNTHETIC, Splitter:B:38:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00cc A[SYNTHETIC, Splitter:B:41:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ed A[SYNTHETIC, Splitter:B:52:0x00ed] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f2 A[SYNTHETIC, Splitter:B:55:0x00f2] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:27:0x00a4=Splitter:B:27:0x00a4, B:57:0x00f5=Splitter:B:57:0x00f5, B:18:0x008f=Splitter:B:18:0x008f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void j() {
        /*
            r12 = this;
            java.lang.Class<org.altbeacon.beacon.service.ScanState> r7 = org.altbeacon.beacon.service.ScanState.class
            monitor-enter(r7)
            r4 = 0
            r2 = 0
            android.content.Context r6 = r12.m     // Catch:{ IOException -> 0x00b3 }
            java.lang.String r8 = "android-beacon-library-scan-state-temp"
            r9 = 0
            java.io.FileOutputStream r4 = r6.openFileOutput(r8, r9)     // Catch:{ IOException -> 0x00b3 }
            java.io.ObjectOutputStream r3 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x00b3 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00b3 }
            r3.writeObject(r12)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            android.content.Context r6 = r12.m     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.io.File r6 = r6.getFilesDir()     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r8 = "android-beacon-library-scan-state"
            r1.<init>(r6, r8)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.io.File r5 = new java.io.File     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            android.content.Context r6 = r12.m     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.io.File r6 = r6.getFilesDir()     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r8 = "android-beacon-library-scan-state-temp"
            r5.<init>(r6, r8)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r6 = b     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r9 = "Temp file is "
            r8.<init>(r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r9 = r5.getAbsolutePath()     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            org.altbeacon.beacon.b.d.a(r6, r8, r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r6 = b     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r9 = "Perm file is "
            r8.<init>(r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r9 = r1.getAbsolutePath()     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            org.altbeacon.beacon.b.d.a(r6, r8, r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            boolean r6 = r1.delete()     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            if (r6 != 0) goto L_0x0076
            java.lang.String r6 = b     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r8 = "Error while saving scan status to file: Cannot delete existing file."
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            org.altbeacon.beacon.b.d.d(r6, r8, r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
        L_0x0076:
            boolean r6 = r5.renameTo(r1)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            if (r6 != 0) goto L_0x0086
            java.lang.String r6 = b     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            java.lang.String r8 = "Error while saving scan status to file: Cannot rename temp file."
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ IOException -> 0x0111, all -> 0x010e }
            org.altbeacon.beacon.b.d.d(r6, r8, r9)     // Catch:{ IOException -> 0x0111, all -> 0x010e }
        L_0x0086:
            if (r4 == 0) goto L_0x008b
            r4.close()     // Catch:{ IOException -> 0x0096 }
        L_0x008b:
            r3.close()     // Catch:{ IOException -> 0x00a6 }
            r2 = r3
        L_0x008f:
            org.altbeacon.beacon.service.MonitoringStatus r6 = r12.d     // Catch:{ all -> 0x00dc }
            r6.d()     // Catch:{ all -> 0x00dc }
            monitor-exit(r7)     // Catch:{ all -> 0x00dc }
            return
        L_0x0096:
            r6 = move-exception
            java.lang.String r6 = b     // Catch:{ all -> 0x00a2 }
            java.lang.String r8 = "close outputStream exception"
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x00a2 }
            org.altbeacon.beacon.b.d.d(r6, r8, r9)     // Catch:{ all -> 0x00a2 }
            goto L_0x008b
        L_0x00a2:
            r6 = move-exception
            r2 = r3
        L_0x00a4:
            monitor-exit(r7)     // Catch:{ all -> 0x00dc }
            throw r6
        L_0x00a6:
            r6 = move-exception
            java.lang.String r6 = b     // Catch:{ all -> 0x00a2 }
            java.lang.String r8 = "close objectOutputStream exception"
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x00a2 }
            org.altbeacon.beacon.b.d.d(r6, r8, r9)     // Catch:{ all -> 0x00a2 }
            r2 = r3
            goto L_0x008f
        L_0x00b3:
            r0 = move-exception
        L_0x00b4:
            java.lang.String r6 = b     // Catch:{ all -> 0x00ea }
            java.lang.String r8 = "Error while saving scan status to file: "
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x00ea }
            r10 = 0
            java.lang.String r11 = r0.getMessage()     // Catch:{ all -> 0x00ea }
            r9[r10] = r11     // Catch:{ all -> 0x00ea }
            org.altbeacon.beacon.b.d.d(r6, r8, r9)     // Catch:{ all -> 0x00ea }
            if (r4 == 0) goto L_0x00ca
            r4.close()     // Catch:{ IOException -> 0x00de }
        L_0x00ca:
            if (r2 == 0) goto L_0x008f
            r2.close()     // Catch:{ IOException -> 0x00d0 }
            goto L_0x008f
        L_0x00d0:
            r6 = move-exception
            java.lang.String r6 = b     // Catch:{ all -> 0x00dc }
            java.lang.String r8 = "close objectOutputStream exception"
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x00dc }
            org.altbeacon.beacon.b.d.d(r6, r8, r9)     // Catch:{ all -> 0x00dc }
            goto L_0x008f
        L_0x00dc:
            r6 = move-exception
            goto L_0x00a4
        L_0x00de:
            r6 = move-exception
            java.lang.String r6 = b     // Catch:{ all -> 0x00dc }
            java.lang.String r8 = "close outputStream exception"
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x00dc }
            org.altbeacon.beacon.b.d.d(r6, r8, r9)     // Catch:{ all -> 0x00dc }
            goto L_0x00ca
        L_0x00ea:
            r6 = move-exception
        L_0x00eb:
            if (r4 == 0) goto L_0x00f0
            r4.close()     // Catch:{ IOException -> 0x00f6 }
        L_0x00f0:
            if (r2 == 0) goto L_0x00f5
            r2.close()     // Catch:{ IOException -> 0x0102 }
        L_0x00f5:
            throw r6     // Catch:{ all -> 0x00dc }
        L_0x00f6:
            r8 = move-exception
            java.lang.String r8 = b     // Catch:{ all -> 0x00dc }
            java.lang.String r9 = "close outputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00dc }
            org.altbeacon.beacon.b.d.d(r8, r9, r10)     // Catch:{ all -> 0x00dc }
            goto L_0x00f0
        L_0x0102:
            r8 = move-exception
            java.lang.String r8 = b     // Catch:{ all -> 0x00dc }
            java.lang.String r9 = "close objectOutputStream exception"
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x00dc }
            org.altbeacon.beacon.b.d.d(r8, r9, r10)     // Catch:{ all -> 0x00dc }
            goto L_0x00f5
        L_0x010e:
            r6 = move-exception
            r2 = r3
            goto L_0x00eb
        L_0x0111:
            r0 = move-exception
            r2 = r3
            goto L_0x00b4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.altbeacon.beacon.service.ScanState.j():void");
    }

    public final int k() {
        long cyclePeriodMillis;
        if (a().booleanValue()) {
            cyclePeriodMillis = c().longValue() + b().longValue();
        } else {
            cyclePeriodMillis = e().longValue() + d().longValue();
        }
        int scanJobIntervalMillis = a;
        if (cyclePeriodMillis > ((long) a)) {
            return (int) cyclePeriodMillis;
        }
        return scanJobIntervalMillis;
    }

    public final int l() {
        long scanPeriodMillis;
        d.a(b, "ScanState says background mode for ScanJob is " + a(), new Object[0]);
        if (a().booleanValue()) {
            scanPeriodMillis = c().longValue();
        } else {
            scanPeriodMillis = e().longValue();
        }
        if (a().booleanValue() || scanPeriodMillis >= ((long) a)) {
            return (int) scanPeriodMillis;
        }
        return a;
    }

    public final void a(g beaconManager) {
        this.e = new HashSet(beaconManager.d());
        this.i = beaconManager.j();
        this.g = beaconManager.k();
        this.j = beaconManager.h();
        this.h = beaconManager.i();
        this.k = beaconManager.g();
        ArrayList existingMonitoredRegions = new ArrayList(this.d.a());
        ArrayList existingRangedRegions = new ArrayList(this.c.keySet());
        ArrayList newMonitoredRegions = new ArrayList(beaconManager.r());
        ArrayList newRangedRegions = new ArrayList(beaconManager.s());
        d.a(b, "ranged regions: old=" + existingRangedRegions.size() + " new=" + newRangedRegions.size(), new Object[0]);
        d.a(b, "monitored regions: old=" + existingMonitoredRegions.size() + " new=" + newMonitoredRegions.size(), new Object[0]);
        Iterator it = newRangedRegions.iterator();
        while (it.hasNext()) {
            Region newRangedRegion = (Region) it.next();
            if (!existingRangedRegions.contains(newRangedRegion)) {
                d.a(b, "Starting ranging region: " + newRangedRegion, new Object[0]);
                Map<Region, f> map = this.c;
                this.m.getPackageName();
                map.put(newRangedRegion, new f(new b()));
            }
        }
        Iterator it2 = existingRangedRegions.iterator();
        while (it2.hasNext()) {
            Region existingRangedRegion = (Region) it2.next();
            if (!newRangedRegions.contains(existingRangedRegion)) {
                d.a(b, "Stopping ranging region: " + existingRangedRegion, new Object[0]);
                this.c.remove(existingRangedRegion);
            }
        }
        d.a(b, "Updated state with " + newRangedRegions.size() + " ranging regions and " + newMonitoredRegions.size() + " monitoring regions.", new Object[0]);
        j();
    }
}
