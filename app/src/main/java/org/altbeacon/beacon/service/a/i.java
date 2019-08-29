package org.altbeacon.beacon.service.a;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.support.annotation.WorkerThread;
import org.altbeacon.beacon.b.d;

/* compiled from: CycledLeScannerForJellyBeanMr2 */
final class i implements Runnable {
    final /* synthetic */ BluetoothAdapter a;
    final /* synthetic */ LeScanCallback b;
    final /* synthetic */ f c;

    i(f this$0, BluetoothAdapter bluetoothAdapter, LeScanCallback leScanCallback) {
        this.c = this$0;
        this.a = bluetoothAdapter;
        this.b = leScanCallback;
    }

    @WorkerThread
    public final void run() {
        try {
            this.a.stopLeScan(this.b);
        } catch (Exception e) {
            d.b(e, "CycledLeScannerForJellyBeanMr2", "Internal Android exception in stopLeScan()", new Object[0]);
        }
    }
}
