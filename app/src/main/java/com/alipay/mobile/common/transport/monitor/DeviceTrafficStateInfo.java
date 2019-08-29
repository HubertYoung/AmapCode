package com.alipay.mobile.common.transport.monitor;

import android.net.TrafficStats;
import com.alipay.mobile.common.transport.monitor.networkqos.DeviceTrafficManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class DeviceTrafficStateInfo {
    public long mMobileRxBytes;
    public long mMobileTxBytes;
    public long mTotalRxBytes;
    public long mTotalTxBytes;
    public long mUpdateTime;

    public class DeviceTrafficStateInfoDelta {
        public double mDeltaTS = 0.0d;
        public long mDiffMobileRxBytes = 0;
        public long mDiffMobileTxBytes = 0;
        public long mDiffTotalRxBytes = 0;
        public long mDiffTotalTxBytes = 0;

        public DeviceTrafficStateInfoDelta(DeviceTrafficStateInfo startData, DeviceTrafficStateInfo stopData) {
            try {
                this.mDeltaTS = (((double) (stopData.mUpdateTime - startData.mUpdateTime)) * 1.0d) / 1000.0d;
                this.mDiffMobileRxBytes = stopData.mMobileRxBytes - startData.mMobileRxBytes;
                this.mDiffTotalRxBytes = stopData.mTotalRxBytes - startData.mTotalRxBytes;
                this.mDiffMobileTxBytes = stopData.mMobileTxBytes - startData.mMobileTxBytes;
                this.mDiffTotalTxBytes = stopData.mTotalTxBytes - startData.mTotalTxBytes;
                DeviceTrafficManager.getInstance().calcSpeedAndBandwidth(this.mDiffTotalRxBytes + this.mDiffTotalTxBytes, this.mDeltaTS);
                LogCatUtil.debug("DTStatInfo", "Diffs - TRX:" + this.mDiffTotalRxBytes + ",TTX:" + this.mDiffTotalTxBytes + ",TMRX:" + this.mDiffMobileRxBytes + ",TMTX:" + this.mDiffMobileTxBytes + ",TTS:" + this.mDeltaTS);
            } catch (Throwable ex) {
                LogCatUtil.error((String) "DTStatInfo", ex);
            }
        }
    }

    public DeviceTrafficStateInfo() {
        this.mTotalRxBytes = 0;
        this.mMobileRxBytes = 0;
        this.mTotalTxBytes = 0;
        this.mMobileTxBytes = 0;
        this.mMobileRxBytes = TrafficStats.getMobileRxBytes();
        if (this.mMobileRxBytes == -1) {
            LogCatUtil.debug("DTStatInfo", "TrafficStats for mobile seems not supported");
        }
        this.mMobileTxBytes = TrafficStats.getMobileTxBytes();
        this.mTotalRxBytes = TrafficStats.getTotalRxBytes();
        this.mTotalTxBytes = TrafficStats.getTotalTxBytes();
        this.mUpdateTime = System.currentTimeMillis();
    }

    public DeviceTrafficStateInfoDelta getDiff(DeviceTrafficStateInfo stopData) {
        if (stopData != null) {
            return new DeviceTrafficStateInfoDelta(this, stopData);
        }
        LogCatUtil.debug("DTStatInfo", "Can't calculate diff");
        return null;
    }
}
