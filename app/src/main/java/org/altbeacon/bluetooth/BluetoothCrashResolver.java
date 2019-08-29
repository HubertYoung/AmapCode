package org.altbeacon.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.SystemClock;
import java.util.HashSet;
import java.util.Set;
import org.altbeacon.beacon.b.d;

public class BluetoothCrashResolver {
    /* access modifiers changed from: private */
    public boolean a = false;
    /* access modifiers changed from: private */
    public boolean b = false;
    /* access modifiers changed from: private */
    public long c = 0;
    /* access modifiers changed from: private */
    public long d = 0;
    private long e = 0;
    private int f = 0;
    private int g = 0;
    private boolean h = false;
    private long i = 0;
    private Context j = null;
    private final Set<String> k = new HashSet();
    private final BroadcastReceiver l = new b(this);

    public BluetoothCrashResolver(Context context) {
        this.j = context.getApplicationContext();
        d.a("BluetoothCrashResolver", "constructed", new Object[0]);
        i();
    }

    public final void a() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        filter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        filter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.j.registerReceiver(this.l, filter);
        d.a("BluetoothCrashResolver", "started listening for BluetoothAdapter events", new Object[0]);
    }

    public final void b() {
        this.j.unregisterReceiver(this.l);
        d.a("BluetoothCrashResolver", "stopped listening for BluetoothAdapter events", new Object[0]);
        h();
    }

    @TargetApi(18)
    public final void a(BluetoothDevice device, LeScanCallback scanner) {
        int oldSize = this.k.size();
        synchronized (this.k) {
            this.k.add(device.getAddress());
        }
        int newSize = this.k.size();
        if (oldSize != newSize && newSize % 100 == 0) {
            d.a("BluetoothCrashResolver", "Distinct Bluetooth devices seen: %s", Integer.valueOf(this.k.size()));
        }
        if (this.k.size() > 1590 && !this.a) {
            d.c("BluetoothCrashResolver", "Large number of Bluetooth devices detected: %s Proactively attempting to clear out address list to prevent a crash", Integer.valueOf(this.k.size()));
            d.c("BluetoothCrashResolver", "Stopping LE Scan", new Object[0]);
            BluetoothAdapter.getDefaultAdapter().stopLeScan(scanner);
            f();
            e();
        }
    }

    public final void c() {
        if (VERSION.SDK_INT < 18) {
            d.a("BluetoothCrashResolver", "Ignoring crashes before API 18, because BLE is unsupported.", new Object[0]);
            return;
        }
        d.c("BluetoothCrashResolver", "BluetoothService crash detected", new Object[0]);
        if (this.k.size() > 0) {
            d.a("BluetoothCrashResolver", "Distinct Bluetooth devices seen at crash: %s", Integer.valueOf(this.k.size()));
        }
        this.e = SystemClock.elapsedRealtime();
        this.f++;
        if (this.a) {
            d.a("BluetoothCrashResolver", "Ignoring Bluetooth crash because recovery is already in progress.", new Object[0]);
        } else {
            f();
        }
        e();
    }

    public final boolean d() {
        return this.a;
    }

    private void e() {
        if (SystemClock.elapsedRealtime() - this.i > 60000) {
            h();
        }
    }

    @TargetApi(17)
    private void f() {
        this.g++;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        d.a("BluetoothCrashResolver", "about to check if discovery is active", new Object[0]);
        if (!adapter.isDiscovering()) {
            d.c("BluetoothCrashResolver", "Recovery attempt started", new Object[0]);
            this.a = true;
            this.b = false;
            d.a("BluetoothCrashResolver", "about to command discovery", new Object[0]);
            if (!adapter.startDiscovery()) {
                d.c("BluetoothCrashResolver", "Can't start discovery.  Is Bluetooth turned on?", new Object[0]);
            }
            d.a("BluetoothCrashResolver", "startDiscovery commanded.  isDiscovering()=%s", Boolean.valueOf(adapter.isDiscovering()));
            d.a("BluetoothCrashResolver", "We will be cancelling this discovery in %s milliseconds.", Integer.valueOf(5000));
            j();
            return;
        }
        d.c("BluetoothCrashResolver", "Already discovering.  Recovery attempt abandoned.", new Object[0]);
    }

    /* access modifiers changed from: private */
    public void g() {
        d.c("BluetoothCrashResolver", "Recovery attempt finished", new Object[0]);
        synchronized (this.k) {
            this.k.clear();
        }
        this.a = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d8 A[SYNTHETIC, Splitter:B:40:0x00d8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h() {
        /*
            r11 = this;
            r10 = 1
            r9 = 0
            r2 = 0
            long r4 = android.os.SystemClock.elapsedRealtime()
            r11.i = r4
            android.content.Context r4 = r11.j     // Catch:{ IOException -> 0x00ea }
            java.lang.String r5 = "BluetoothCrashResolverState.txt"
            r6 = 0
            java.io.FileOutputStream r1 = r4.openFileOutput(r5, r6)     // Catch:{ IOException -> 0x00ea }
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter     // Catch:{ IOException -> 0x00ea }
            r3.<init>(r1)     // Catch:{ IOException -> 0x00ea }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            r4.<init>()     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            long r5 = r11.e     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            r3.write(r4)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            r4.<init>()     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            int r5 = r11.f     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            r3.write(r4)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            r4.<init>()     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            int r5 = r11.g     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.String r5 = "\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            r3.write(r4)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            boolean r4 = r11.h     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            if (r4 == 0) goto L_0x00b5
            java.lang.String r4 = "1\n"
        L_0x0065:
            r3.write(r4)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.util.Set<java.lang.String> r5 = r11.k     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            monitor-enter(r5)     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
            java.util.Set<java.lang.String> r4 = r11.k     // Catch:{ all -> 0x0086 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x0086 }
        L_0x0071:
            boolean r6 = r4.hasNext()     // Catch:{ all -> 0x0086 }
            if (r6 == 0) goto L_0x00b8
            java.lang.Object r0 = r4.next()     // Catch:{ all -> 0x0086 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0086 }
            r3.write(r0)     // Catch:{ all -> 0x0086 }
            java.lang.String r6 = "\n"
            r3.write(r6)     // Catch:{ all -> 0x0086 }
            goto L_0x0071
        L_0x0086:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            throw r4     // Catch:{ IOException -> 0x0089, all -> 0x00e7 }
        L_0x0089:
            r4 = move-exception
            r2 = r3
        L_0x008b:
            java.lang.String r4 = "BluetoothCrashResolver"
            java.lang.String r5 = "Can't write macs to %s"
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x00d5 }
            r7 = 0
            java.lang.String r8 = "BluetoothCrashResolverState.txt"
            r6[r7] = r8     // Catch:{ all -> 0x00d5 }
            org.altbeacon.beacon.b.d.c(r4, r5, r6)     // Catch:{ all -> 0x00d5 }
            if (r2 == 0) goto L_0x009f
            r2.close()     // Catch:{ IOException -> 0x00ca }
        L_0x009f:
            java.lang.String r4 = "BluetoothCrashResolver"
            java.lang.String r5 = "Wrote %s Bluetooth addresses"
            java.lang.Object[] r6 = new java.lang.Object[r10]
            java.util.Set<java.lang.String> r7 = r11.k
            int r7 = r7.size()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6[r9] = r7
            org.altbeacon.beacon.b.d.a(r4, r5, r6)
            return
        L_0x00b5:
            java.lang.String r4 = "0\n"
            goto L_0x0065
        L_0x00b8:
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            r3.close()     // Catch:{ IOException -> 0x00be }
            r2 = r3
            goto L_0x009f
        L_0x00be:
            r4 = move-exception
            java.lang.String r4 = "BluetoothCrashResolver"
            java.lang.String r5 = "close writer exception"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r4, r5, r6)
            r2 = r3
            goto L_0x009f
        L_0x00ca:
            r4 = move-exception
            java.lang.String r4 = "BluetoothCrashResolver"
            java.lang.String r5 = "close writer exception"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r4, r5, r6)
            goto L_0x009f
        L_0x00d5:
            r4 = move-exception
        L_0x00d6:
            if (r2 == 0) goto L_0x00db
            r2.close()     // Catch:{ IOException -> 0x00dc }
        L_0x00db:
            throw r4
        L_0x00dc:
            r5 = move-exception
            java.lang.String r5 = "BluetoothCrashResolver"
            java.lang.String r6 = "close writer exception"
            java.lang.Object[] r7 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r5, r6, r7)
            goto L_0x00db
        L_0x00e7:
            r4 = move-exception
            r2 = r3
            goto L_0x00d6
        L_0x00ea:
            r4 = move-exception
            goto L_0x008b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.altbeacon.bluetooth.BluetoothCrashResolver.h():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x006c A[SYNTHETIC, Splitter:B:27:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b3 A[SYNTHETIC, Splitter:B:43:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c5 A[SYNTHETIC, Splitter:B:49:0x00c5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i() {
        /*
            r12 = this;
            r11 = 1
            r10 = 0
            r3 = 0
            android.content.Context r5 = r12.j     // Catch:{ IOException -> 0x00da, NumberFormatException -> 0x00a1 }
            java.lang.String r6 = "BluetoothCrashResolverState.txt"
            java.io.FileInputStream r0 = r5.openFileInput(r6)     // Catch:{ IOException -> 0x00da, NumberFormatException -> 0x00a1 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00da, NumberFormatException -> 0x00a1 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x00da, NumberFormatException -> 0x00a1 }
            r5.<init>(r0)     // Catch:{ IOException -> 0x00da, NumberFormatException -> 0x00a1 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x00da, NumberFormatException -> 0x00a1 }
            java.lang.String r1 = r4.readLine()     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            if (r1 == 0) goto L_0x0021
            long r5 = java.lang.Long.parseLong(r1)     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            r12.e = r5     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
        L_0x0021:
            java.lang.String r1 = r4.readLine()     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            if (r1 == 0) goto L_0x002d
            int r5 = java.lang.Integer.parseInt(r1)     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            r12.f = r5     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
        L_0x002d:
            java.lang.String r1 = r4.readLine()     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            if (r1 == 0) goto L_0x0039
            int r5 = java.lang.Integer.parseInt(r1)     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            r12.g = r5     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
        L_0x0039:
            java.lang.String r1 = r4.readLine()     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            if (r1 == 0) goto L_0x004d
            r5 = 0
            r12.h = r5     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            java.lang.String r5 = "1"
            boolean r5 = r1.equals(r5)     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            if (r5 == 0) goto L_0x004d
            r5 = 1
            r12.h = r5     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
        L_0x004d:
            java.lang.String r2 = r4.readLine()     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            if (r2 == 0) goto L_0x0085
            java.util.Set<java.lang.String> r5 = r12.k     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            r5.add(r2)     // Catch:{ IOException -> 0x0059, NumberFormatException -> 0x00d7, all -> 0x00d4 }
            goto L_0x004d
        L_0x0059:
            r5 = move-exception
            r3 = r4
        L_0x005b:
            java.lang.String r5 = "BluetoothCrashResolver"
            java.lang.String r6 = "Can't read macs from %s"
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x00c2 }
            r8 = 0
            java.lang.String r9 = "BluetoothCrashResolverState.txt"
            r7[r8] = r9     // Catch:{ all -> 0x00c2 }
            org.altbeacon.beacon.b.d.c(r5, r6, r7)     // Catch:{ all -> 0x00c2 }
            if (r3 == 0) goto L_0x006f
            r3.close()     // Catch:{ IOException -> 0x0096 }
        L_0x006f:
            java.lang.String r5 = "BluetoothCrashResolver"
            java.lang.String r6 = "Read %s Bluetooth addresses"
            java.lang.Object[] r7 = new java.lang.Object[r11]
            java.util.Set<java.lang.String> r8 = r12.k
            int r8 = r8.size()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r7[r10] = r8
            org.altbeacon.beacon.b.d.a(r5, r6, r7)
            return
        L_0x0085:
            r4.close()     // Catch:{ IOException -> 0x008a }
            r3 = r4
            goto L_0x006f
        L_0x008a:
            r5 = move-exception
            java.lang.String r5 = "BluetoothCrashResolver"
            java.lang.String r6 = "close reader exception"
            java.lang.Object[] r7 = new java.lang.Object[r10]
            org.altbeacon.beacon.b.d.d(r5, r6, r7)
            r3 = r4
            goto L_0x006f
        L_0x0096:
            r5 = move-exception
            java.lang.String r5 = "BluetoothCrashResolver"
            java.lang.String r6 = "close reader exception"
            java.lang.Object[] r7 = new java.lang.Object[r10]
            org.altbeacon.beacon.b.d.d(r5, r6, r7)
            goto L_0x006f
        L_0x00a1:
            r5 = move-exception
        L_0x00a2:
            java.lang.String r5 = "BluetoothCrashResolver"
            java.lang.String r6 = "Can't parse file %s"
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x00c2 }
            r8 = 0
            java.lang.String r9 = "BluetoothCrashResolverState.txt"
            r7[r8] = r9     // Catch:{ all -> 0x00c2 }
            org.altbeacon.beacon.b.d.c(r5, r6, r7)     // Catch:{ all -> 0x00c2 }
            if (r3 == 0) goto L_0x006f
            r3.close()     // Catch:{ IOException -> 0x00b7 }
            goto L_0x006f
        L_0x00b7:
            r5 = move-exception
            java.lang.String r5 = "BluetoothCrashResolver"
            java.lang.String r6 = "close reader exception"
            java.lang.Object[] r7 = new java.lang.Object[r10]
            org.altbeacon.beacon.b.d.d(r5, r6, r7)
            goto L_0x006f
        L_0x00c2:
            r5 = move-exception
        L_0x00c3:
            if (r3 == 0) goto L_0x00c8
            r3.close()     // Catch:{ IOException -> 0x00c9 }
        L_0x00c8:
            throw r5
        L_0x00c9:
            r6 = move-exception
            java.lang.String r6 = "BluetoothCrashResolver"
            java.lang.String r7 = "close reader exception"
            java.lang.Object[] r8 = new java.lang.Object[r10]
            org.altbeacon.beacon.b.d.d(r6, r7, r8)
            goto L_0x00c8
        L_0x00d4:
            r5 = move-exception
            r3 = r4
            goto L_0x00c3
        L_0x00d7:
            r5 = move-exception
            r3 = r4
            goto L_0x00a2
        L_0x00da:
            r5 = move-exception
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.altbeacon.bluetooth.BluetoothCrashResolver.i():void");
    }

    private void j() {
        try {
            Thread.sleep(5000);
            if (!this.b) {
                d.c("BluetoothCrashResolver", "BluetoothAdapter.ACTION_DISCOVERY_STARTED never received.  Recovery may fail.", new Object[0]);
            }
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (adapter.isDiscovering()) {
                d.a("BluetoothCrashResolver", "Cancelling discovery", new Object[0]);
                adapter.cancelDiscovery();
                return;
            }
            d.a("BluetoothCrashResolver", "Discovery not running.  Won't cancel it", new Object[0]);
        } catch (InterruptedException e2) {
            d.a("BluetoothCrashResolver", "DiscoveryCanceller sleep interrupted.", new Object[0]);
        }
    }
}
