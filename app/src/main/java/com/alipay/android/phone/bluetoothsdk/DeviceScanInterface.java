package com.alipay.android.phone.bluetoothsdk;

import android.bluetooth.BluetoothDevice;

public interface DeviceScanInterface {
    void onDeviceFound(BluetoothDevice bluetoothDevice, int i, String str);
}
