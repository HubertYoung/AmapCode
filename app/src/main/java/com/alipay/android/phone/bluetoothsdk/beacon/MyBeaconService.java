package com.alipay.android.phone.bluetoothsdk.beacon;

import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class MyBeaconService extends ExternalService {
    public abstract BeaconResult getBeacons();

    public abstract void setMyBeaconListener(MyBeaconListener myBeaconListener);

    public abstract BeaconResult startBeaconDiscovery(String[] strArr);

    public abstract BeaconResult stopBeaconDiscovery();
}
