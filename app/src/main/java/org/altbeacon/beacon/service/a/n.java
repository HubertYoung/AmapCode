package org.altbeacon.beacon.service.a;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.support.annotation.WorkerThread;
import org.altbeacon.beacon.b.d;

/* compiled from: CycledLeScannerForLollipop */
final class n implements Runnable {
    final /* synthetic */ BluetoothLeScanner a;
    final /* synthetic */ ScanCallback b;
    final /* synthetic */ k c;

    n(k this$0, BluetoothLeScanner bluetoothLeScanner, ScanCallback scanCallback) {
        this.c = this$0;
        this.a = bluetoothLeScanner;
        this.b = scanCallback;
    }

    @WorkerThread
    public final void run() {
        try {
            d.a("CycledLeScannerForLollipop", "Stopping LE scan on scan handler", new Object[0]);
            this.a.stopScan(this.b);
        } catch (IllegalStateException e) {
            d.c("CycledLeScannerForLollipop", "Cannot stop scan. Bluetooth may be turned off.", new Object[0]);
        } catch (NullPointerException e2) {
            d.b(e2, "CycledLeScannerForLollipop", "Cannot stop scan. Unexpected NPE.", new Object[0]);
        } catch (SecurityException e3) {
            d.d("CycledLeScannerForLollipop", "Cannot stop scan.  Security Exception", new Object[0]);
        }
    }
}
