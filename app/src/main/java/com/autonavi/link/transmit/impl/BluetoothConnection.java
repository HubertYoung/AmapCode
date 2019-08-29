package com.autonavi.link.transmit.impl;

import android.bluetooth.BluetoothSocket;
import com.autonavi.link.transmit.inter.Connection;

public class BluetoothConnection implements Connection {
    private final BluetoothSocket a;

    public boolean isConnected() {
        return true;
    }

    public BluetoothConnection(BluetoothSocket bluetoothSocket) {
        this.a = bluetoothSocket;
    }

    public int read(byte[] bArr, int i, int i2) {
        try {
            return this.a.getInputStream().read(bArr, i, i2);
        } catch (Exception unused) {
            return -1;
        }
    }

    public int write(byte[] bArr, int i, int i2) {
        int i3;
        if (i2 > 512) {
            int i4 = 0;
            while (true) {
                i3 = i2 - i4;
                if (i3 <= 512) {
                    break;
                }
                try {
                    this.a.getOutputStream().write(bArr, i + i4, 512);
                    i4 += 512;
                    Thread.sleep(2);
                } catch (Exception unused) {
                    return -1;
                }
            }
            this.a.getOutputStream().write(bArr, i + i4, i3);
        } else {
            this.a.getOutputStream().write(bArr, i, i2);
        }
        return i2;
    }
}
