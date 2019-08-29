package org.altbeacon.beacon.service;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;

/* compiled from: ScanHelper */
final class o {
    final int a;
    @NonNull
    BluetoothDevice b;
    @NonNull
    byte[] c;
    final /* synthetic */ m d;

    o(m mVar, @NonNull BluetoothDevice device, int rssi, @NonNull byte[] scanRecord) {
        this.d = mVar;
        this.b = device;
        this.a = rssi;
        this.c = scanRecord;
    }
}
