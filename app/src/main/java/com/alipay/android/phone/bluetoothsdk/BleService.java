package com.alipay.android.phone.bluetoothsdk;

import android.bluetooth.BluetoothDevice;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.List;

public abstract class BleService extends ExternalService {
    public abstract boolean closeBluetooth();

    public abstract void configDevice(JSONObject jSONObject);

    public abstract boolean connect(String str);

    public abstract void disconnect();

    public abstract int getBluetoothState();

    public abstract List<BluetoothDevice> getConnectedDeviceList();

    public abstract List<BluetoothDevice> getDeviceList();

    public abstract boolean isSupportBLE();

    public abstract boolean openBluetooth();

    public abstract boolean sendDataToDevice(String str);

    public abstract void setDeviceInterface(DeviceConnectionInterface deviceConnectionInterface, DeviceDataInterface deviceDataInterface, DeviceStateInterface deviceStateInterface);

    public abstract boolean startScan(DeviceScanInterface deviceScanInterface);

    public abstract void stopScan();
}
