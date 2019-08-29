package org.altbeacon.beacon.service.a;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.MainThread;

@MainThread
/* compiled from: CycledLeScanCallback */
public interface a {
    void a();

    void a(BluetoothDevice bluetoothDevice, int i, byte[] bArr);
}
