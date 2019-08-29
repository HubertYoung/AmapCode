package org.altbeacon.beacon;

import android.bluetooth.BluetoothDevice;

/* compiled from: AltBeaconParser */
public final class b extends j {
    public b() {
        this.u = new int[]{280};
        a((String) "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
        this.t = "altbeacon";
    }

    public final Beacon a(byte[] scanData, int rssi, BluetoothDevice device) {
        return a(scanData, rssi, device, (Beacon) new AltBeacon());
    }
}
