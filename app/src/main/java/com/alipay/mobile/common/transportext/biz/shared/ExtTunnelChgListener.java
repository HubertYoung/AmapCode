package com.alipay.mobile.common.transportext.biz.shared;

import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.transport.strategy.ExtTransportTunnelWatchdog;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelChangedListener;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.strategy.TunnelChangeEventModel;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.api.ExtTransportManager;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign.GeneralEventListenServiceImpl;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl;
import java.util.Observable;

public class ExtTunnelChgListener implements NetworkTunnelChangedListener {
    private static final String TAG = "ExtTunnelChgListener";

    private ExtTunnelChgListener() {
    }

    public void update(Observable observable, Object tunnelChangeEventModel) {
        TunnelChangeEventModel lTunnelChangeEventModel = (TunnelChangeEventModel) tunnelChangeEventModel;
        LogCatUtil.info(TAG, "收到链路切换：current: " + lTunnelChangeEventModel.currentTunnelType + ", new: " + lTunnelChangeEventModel.newTunnelType);
        handleSpdyTunnelSwitch(lTunnelChangeEventModel);
        handleOriginTunnelSwitch(lTunnelChangeEventModel);
        handleAmnetTunnelSwitch(lTunnelChangeEventModel);
    }

    private void handleOriginTunnelSwitch(TunnelChangeEventModel lTunnelChangeEventModel) {
        if (lTunnelChangeEventModel.newTunnelType == 1) {
            AmnetHelper.getAmnetManager().getAmnetGeneralEventManager().notifyConnStateChange(0);
            LogCatUtil.info(TAG, "origin tunnel started!");
        }
    }

    private void handleAmnetTunnelSwitch(TunnelChangeEventModel lTunnelChangeEventModel) {
        if (lTunnelChangeEventModel.newTunnelType == 3) {
            ExtTransportManager mmtpTransportManager = ((ExtTransportManagerImpl) ExtTransportOffice.getInstance().getExtTransportManager()).getMMTPTransportManager();
            if (!mmtpTransportManager.isInited()) {
                mmtpTransportManager.init(ExtTransportEnv.getAppContext());
            }
            LogCatUtil.info(TAG, "Main proc amnet tunnel inited!");
            if (ExtTransportTunnelWatchdog.getInstance().isDowngraded()) {
                ExtTransportTunnelWatchdog.getInstance().resetDowngradeFlag();
                return;
            }
            return;
        }
        GeneralEventListenServiceImpl.getInstance().setAmnetLifeState(1);
        LogCatUtil.info(TAG, "===Amnet is shutdown===");
    }

    private void handleSpdyTunnelSwitch(TunnelChangeEventModel lTunnelChangeEventModel) {
        try {
            if (lTunnelChangeEventModel.newTunnelType == 2) {
                ExtTransportManager spdyTransportManager = ((ExtTransportManagerImpl) ExtTransportOffice.getInstance().getExtTransportManager()).getSpdyTransportManager();
                if (!spdyTransportManager.isInited()) {
                    spdyTransportManager.init(ExtTransportEnv.getAppContext());
                }
                SpdyLongLinkConnManagerImpl.getInstance().asynConnect();
                AmnetHelper.getAmnetManager().getAmnetGeneralEventManager().notifyConnStateChange(0);
                LogCatUtil.info(TAG, "Spdy tunnel started!");
            } else if (lTunnelChangeEventModel.currentTunnelType == 2 && lTunnelChangeEventModel.newTunnelType != 2 && !NetworkTunnelStrategy.getInstance().isCanUseSpdy()) {
                SpdyLongLinkConnManagerImpl.getInstance().closeConnection();
                LogCatUtil.info(TAG, "Spdy tunnel closed!");
            }
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
        }
    }
}
