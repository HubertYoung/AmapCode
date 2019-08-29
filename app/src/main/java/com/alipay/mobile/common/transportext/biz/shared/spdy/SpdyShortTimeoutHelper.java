package com.alipay.mobile.common.transportext.biz.shared.spdy;

import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.LongLinkTransportManager;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkConnManagerImpl;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;

public class SpdyShortTimeoutHelper {
    private static long leaveTime = -1;

    public static final void adjustmentSpdyTimeout() {
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "start adjustmentSpdyTimeout");
        if (!NetworkTunnelStrategy.getInstance().isCanUseSpdy()) {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "adjustmentSpdyTimeout.  spdy off.");
        } else if (!LongLinkTransportManager.getInstance().isConnected()) {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "adjustmentSpdyTimeout.  spdy connection closed.");
        } else if (SpdyLongLinkConnManagerImpl.getInstance().isNetworkActive()) {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "adjustmentSpdyTimeout. spdy not active.");
        } else {
            int timeToAsleep = TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.SPDY_TIME_TO_ASLEEP);
            long leaveHowTime = System.currentTimeMillis() - leaveTime;
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "adjustmentSpdyTimeout. leaveTime=[" + leaveTime + "] leaveHowTime=[" + leaveHowTime + "] timeToAsleep=[" + timeToAsleep + "]");
            if (leaveTime != -1 && leaveHowTime > ((long) timeToAsleep)) {
                ExtTransportStrategy.setUseSpdyShortReadTimeout(true);
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "adjustmentSpdyTimeout. setUseSpdyShortReadTimeout");
            }
        }
    }

    public static final void setLeaveTime(long leaveTime2) {
        leaveTime = leaveTime2;
    }
}
