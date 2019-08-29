package com.alipay.android.phone.bluetoothsdk.better.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import java.util.Map;

public class BleDevice {
    public int RSSI;
    public String advertisData;
    public List<String> advertisServiceUUIDs;
    public String deviceId;
    public String deviceName;
    @JSONField(serialize = false)
    public BluetoothGatt gatt;
    public String localName;
    public String manufacturerData;
    public String name;
    public Map<String, String> serviceData;

    public boolean equals(Object o) {
        return this.deviceId != null && this.deviceId.equals(((BleDevice) o).deviceId);
    }

    public int hashCode() {
        return this.deviceId.hashCode();
    }

    public static BleDevice createBleDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return null;
        }
        BleDevice bleDevice = new BleDevice();
        bleDevice.deviceId = bluetoothDevice.getAddress();
        bleDevice.localName = bluetoothDevice.getName();
        bleDevice.deviceName = bluetoothDevice.getName();
        bleDevice.name = bluetoothDevice.getName();
        bleDevice.manufacturerData = "";
        bleDevice.advertisData = "";
        return bleDevice;
    }

    public static BleDevice createConnectedBleDevice(BluetoothDevice bluetoothDevice, BluetoothGatt gatt2) {
        BleDevice bleDevice = createBleDevice(bluetoothDevice);
        if (bleDevice != null) {
            bleDevice.gatt = gatt2;
        }
        return bleDevice;
    }
}
