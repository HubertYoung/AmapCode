package com.alipay.android.phone.bluetoothsdk.better.ble;

import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.List;

public abstract class BetterBleService extends ExternalService {
    public abstract void closeBluetoothAdapter();

    public abstract BleResult connectBluetoothDevice(String str);

    public abstract BleResult disconnectBluetoothDevice(String str);

    public abstract BleResult getBluetoothCharacteristics(String str, String str2);

    public abstract List<BleDevice> getBluetoothDevices();

    public abstract BleResult getBluetoothServices(String str);

    public abstract int getBluetoothState();

    public abstract List<BleDevice> getConnectedBluetoothDevices();

    public abstract String getH5ActivityInstance();

    public abstract boolean isDiscovering();

    public abstract boolean isOpened();

    public abstract boolean isSupportBLE();

    public abstract BleResult notifyCharacteristicValueChange(String str, String str2, String str3, String str4, boolean z);

    public abstract void openBluetoothAdapter(String str, boolean z);

    public abstract BleResult readData(String str, String str2, String str3);

    public abstract BleResult sendData(String str, String str2, String str3, String str4);

    public abstract void setBetterBleListener(BetterBleListener betterBleListener);

    public abstract BleResult startBluetoothDevicesDiscovery(String[] strArr, boolean z, int i);

    public abstract void stopBluetoothDevicesDiscovery();
}
