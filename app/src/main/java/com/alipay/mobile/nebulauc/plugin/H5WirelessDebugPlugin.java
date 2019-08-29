package com.alipay.mobile.nebulauc.plugin;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.uc.webview.export.extension.UCCore;

public class H5WirelessDebugPlugin extends H5SimplePlugin {
    private static final String ACTION_OPEN_WIRELESS_DEBUG = "openWirelessDebug";
    private static final int ARGUMENT_ERROR = 102;
    private static final String TAG = "H5WirelessDebugPlugin";
    private static final int USER_ID_ERROR = 103;
    public static boolean sWirelessDebugOpening = false;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_OPEN_WIRELESS_DEBUG);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (ACTION_OPEN_WIRELESS_DEBUG.equals(event.getAction())) {
            openWirelessDebug(event, bridgeContext);
        }
        return true;
    }

    private void openWirelessDebug(H5Event event, H5BridgeContext bridgeContext) {
        if (!H5Utils.isDebuggable(H5Utils.getContext())) {
            sendError(bridgeContext, 103, "release not allow");
            return;
        }
        String debugAddr = H5Utils.getString(event.getParam(), (String) "debugAddr", (String) "");
        if (TextUtils.isEmpty(debugAddr)) {
            sendError(bridgeContext, 102, "UC core version is empty");
            return;
        }
        UCCore.startTCPDevtools(debugAddr, 0);
        sWirelessDebugOpening = true;
        H5Utils.executeOrdered(TAG, new Runnable() {
            public void run() {
                H5DevConfig.setBooleanConfig("h5_enable_ri_wired_debug", true);
            }
        });
        bridgeContext.sendBridgeResult(debugAddr, "wireless connect success");
    }

    private void sendError(H5BridgeContext bridgeContext, int error, String msg) {
        H5Log.d(TAG, "send error: " + msg);
        if (bridgeContext != null) {
            bridgeContext.sendError(error, msg);
        }
    }
}
