package com.alipay.android.phone.bluetoothsdk.beacon;

import java.util.List;

public interface MyBeaconListener {
    void onBeaconServiceChange(boolean z, boolean z2);

    void onBeaconUpdate(List<MyBeacon> list);
}
