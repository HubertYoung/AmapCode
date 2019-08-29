package com.alipay.android.phone.bluetoothsdk;

import com.alipay.android.phone.bluetoothsdk.better.ble.BetterBleService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;

public class H5BlePagePlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(CommonEvents.H5_PAGE_CLOSED);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return super.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (CommonEvents.H5_PAGE_CLOSED.equals(event.getAction())) {
            LoggerFactory.getTraceLogger().debug("H5BlePagePlugin", "h5 page closed");
            BleService bleService = (BleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(BleService.class.getName());
            if (bleService != null) {
                bleService.closeBluetooth();
            }
            BetterBleService betterBleService = (BetterBleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(BetterBleService.class.getName());
            if (betterBleService != null) {
                betterBleService.closeBluetoothAdapter();
            }
        }
        return super.handleEvent(event, context);
    }
}
