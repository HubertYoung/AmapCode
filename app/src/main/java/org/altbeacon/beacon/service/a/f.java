package org.altbeacon.beacon.service.a;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import org.altbeacon.beacon.b.d;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

@TargetApi(18)
/* compiled from: CycledLeScannerForJellyBeanMr2 */
public final class f extends b {
    private LeScanCallback k;

    public f(Context context, boolean backgroundFlag, a cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        super(context, backgroundFlag, cycledLeScanCallback, crashResolver);
    }

    /* access modifiers changed from: protected */
    public final void f() {
        n();
    }

    /* access modifiers changed from: protected */
    public final boolean g() {
        long millisecondsUntilStart = this.a - SystemClock.elapsedRealtime();
        if (millisecondsUntilStart <= 0) {
            return false;
        }
        d.a("CycledLeScannerForJellyBeanMr2", "Waiting to start next Bluetooth scan for another %s milliseconds", Long.valueOf(millisecondsUntilStart));
        if (this.i) {
            l();
        }
        Handler handler = this.e;
        g gVar = new g(this);
        if (millisecondsUntilStart > 1000) {
            millisecondsUntilStart = 1000;
        }
        handler.postDelayed(gVar, millisecondsUntilStart);
        return true;
    }

    /* access modifiers changed from: protected */
    public final void h() {
        m();
    }

    /* access modifiers changed from: protected */
    public final void j() {
        n();
        this.b = true;
    }

    private void m() {
        BluetoothAdapter bluetoothAdapter = k();
        if (bluetoothAdapter != null) {
            LeScanCallback leScanCallback = o();
            this.f.removeCallbacksAndMessages(null);
            this.f.post(new h(this, bluetoothAdapter, leScanCallback));
        }
    }

    private void n() {
        BluetoothAdapter bluetoothAdapter = k();
        if (bluetoothAdapter != null) {
            LeScanCallback leScanCallback = o();
            this.f.removeCallbacksAndMessages(null);
            this.f.post(new i(this, bluetoothAdapter, leScanCallback));
        }
    }

    /* access modifiers changed from: private */
    public LeScanCallback o() {
        if (this.k == null) {
            this.k = new j(this);
        }
        return this.k;
    }
}
