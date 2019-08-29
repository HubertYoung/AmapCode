package com.alipay.android.phone.bluetoothsdk;

public interface DeviceDataInterface {
    void onReceiveDataFromDevice(String str, String str2, String str3);

    void onSendDataToDevice(String str, String str2);
}
