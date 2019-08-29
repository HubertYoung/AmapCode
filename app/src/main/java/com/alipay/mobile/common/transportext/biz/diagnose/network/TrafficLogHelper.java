package com.alipay.mobile.common.transportext.biz.diagnose.network;

import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo.DeviceTrafficStateInfoDelta;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;

public class TrafficLogHelper {
    public static String getTrafficLog(DeviceTrafficStateInfo deviceTrafficStateInfo) {
        if (deviceTrafficStateInfo == null) {
            return null;
        }
        DeviceTrafficStateInfoDelta delta = AlipayQosService.getInstance().stopTrafficMonitor(deviceTrafficStateInfo);
        return "traffic:TRX:" + delta.mDiffTotalRxBytes + ";TTX:" + delta.mDiffTotalTxBytes + ";TMRX:" + delta.mDiffMobileRxBytes + ";TMTX:" + delta.mDiffMobileTxBytes + ";TTS:" + delta.mDeltaTS + " s ;speed:" + String.format("%.4f", new Object[]{Double.valueOf(AlipayQosService.getInstance().getSpeed())}) + ";bandwidth:" + String.format("%.4f", new Object[]{Double.valueOf(AlipayQosService.getInstance().getBandwidth())});
    }
}
