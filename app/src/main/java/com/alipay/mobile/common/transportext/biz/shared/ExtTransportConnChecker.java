package com.alipay.mobile.common.transportext.biz.shared;

import android.content.Context;
import com.alipay.mobile.common.transport.concurrent.TaskExecutorManager;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.spdy.http.AndroidSpdyHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.LongLinkTransportManager;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl;

public class ExtTransportConnChecker {
    private static final String TAG = "Conn_Checker";

    public static boolean isConnectionAvailable() {
        NetworkTunnelStrategy networkTunnelStrategy = NetworkTunnelStrategy.getInstance();
        if (NetworkTunnelStrategy.getInstance().isCanUseAmnet() && AmnetHelper.isAmnetActivite()) {
            LogCatUtil.debug(TAG, "AMNET is allowed and alive, data can go with that");
            return true;
        } else if (!networkTunnelStrategy.isCanUseSpdy()) {
            LogCatUtil.debug(TAG, "SPDY is not allowed by tunnel strategy....");
            return false;
        } else if (!LongLinkTransportManager.getInstance().isConnected()) {
            LogCatUtil.debug(TAG, "SPDY connection is not yet connected, start one!");
            Context ctx = ExtTransportEnv.getAppContext();
            AndroidSpdyHttpClient.newInstance(ctx).asynPreConnect(TaskExecutorManager.getInstance(ctx).getBgExecutor());
            return false;
        } else {
            LogCatUtil.debug(TAG, "SPDY connection is connected, start ping regardless of the current HB status!");
            SpdyLongLinkConnManagerImpl.getInstance().startPing();
            return true;
        }
    }
}
