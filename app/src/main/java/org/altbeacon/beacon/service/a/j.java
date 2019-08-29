package org.altbeacon.beacon.service.a;

import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import org.altbeacon.beacon.b.d;

/* compiled from: CycledLeScannerForJellyBeanMr2 */
final class j implements LeScanCallback {
    final /* synthetic */ f a;

    j(f this$0) {
        this.a = this$0;
    }

    public final void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        d.a("CycledLeScannerForJellyBeanMr2", "got record", new Object[0]);
        this.a.h.a(device, rssi, scanRecord);
        if (this.a.g != null) {
            this.a.g.a(device, this.a.o());
        }
    }
}
