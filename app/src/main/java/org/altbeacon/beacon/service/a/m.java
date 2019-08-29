package org.altbeacon.beacon.service.a;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanSettings;
import android.support.annotation.WorkerThread;
import java.util.List;
import org.altbeacon.beacon.b.d;

/* compiled from: CycledLeScannerForLollipop */
final class m implements Runnable {
    final /* synthetic */ BluetoothLeScanner a;
    final /* synthetic */ List b;
    final /* synthetic */ ScanSettings c;
    final /* synthetic */ ScanCallback d;
    final /* synthetic */ k e;

    m(k this$0, BluetoothLeScanner bluetoothLeScanner, List list, ScanSettings scanSettings, ScanCallback scanCallback) {
        this.e = this$0;
        this.a = bluetoothLeScanner;
        this.b = list;
        this.c = scanSettings;
        this.d = scanCallback;
    }

    @WorkerThread
    public final void run() {
        try {
            this.a.startScan(this.b, this.c, this.d);
        } catch (IllegalStateException e2) {
            d.c("CycledLeScannerForLollipop", "Cannot start scan. Bluetooth may be turned off.", new Object[0]);
        } catch (NullPointerException e3) {
            d.b(e3, "CycledLeScannerForLollipop", "Cannot start scan. Unexpected NPE.", new Object[0]);
        } catch (SecurityException e4) {
            d.d("CycledLeScannerForLollipop", "Cannot start scan.  Security Exception", new Object[0]);
        }
    }
}
