package com.autonavi.minimap.offline.model;

import com.autonavi.minimap.offline.controller.IOfflineCallback;
import com.autonavi.minimap.offline.model.network.RequestGpu3dSupportInfo;

public final class NetworkRequestHelper {
    private static final String TAG = "NetworkRequestHelper";

    public static void requestGpu3dSupport(String str, IOfflineCallback iOfflineCallback) {
        new RequestGpu3dSupportInfo(str).exec(iOfflineCallback);
    }
}
