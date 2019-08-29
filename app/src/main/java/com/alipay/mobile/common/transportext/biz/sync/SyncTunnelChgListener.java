package com.alipay.mobile.common.transportext.biz.sync;

import com.alipay.mobile.common.transport.strategy.NetworkTunnelChangedListener;
import com.alipay.mobile.common.transport.strategy.TunnelChangeEventModel;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.util.Observable;

public class SyncTunnelChgListener implements NetworkTunnelChangedListener {
    private static final String TAG = "SyncTunnelChgListener";

    private SyncTunnelChgListener() {
    }

    public void update(Observable observable, Object tunnelChangeEventModel) {
        TunnelChangeEventModel lTunnelChangeEventModel = (TunnelChangeEventModel) tunnelChangeEventModel;
        LogCatUtil.info(TAG, "SYNC 收到链路切换：current: " + lTunnelChangeEventModel.currentTunnelType + ", new: " + lTunnelChangeEventModel.newTunnelType);
        if (lTunnelChangeEventModel.newTunnelType != 2 && lTunnelChangeEventModel.newTunnelType != 1) {
            int i = lTunnelChangeEventModel.newTunnelType;
        }
    }
}
