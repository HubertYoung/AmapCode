package com.alipay.android.phone.bluetoothsdk.better.ble;

import java.util.List;

public interface BetterBleListener {
    void onBluetoothAdapterStateChange(boolean z, boolean z2);

    void onBluetoothCharacteristicRead(String str, String str2, String str3, String str4);

    void onBluetoothCharacteristicValueChange(String str, String str2, String str3, String str4);

    void onBluetoothCharacteristicWrite(String str, String str2, String str3);

    void onBluetoothConnectionStateChange(String str, boolean z);

    void onBluetoothDescriptorWrite(String str, String str2, String str3, String str4);

    void onBluetoothDeviceFound(List<BleDevice> list);
}
