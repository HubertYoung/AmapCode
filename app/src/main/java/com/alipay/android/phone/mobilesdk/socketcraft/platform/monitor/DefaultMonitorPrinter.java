package com.alipay.android.phone.mobilesdk.socketcraft.platform.monitor;

import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorModel;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatUtil;

public class DefaultMonitorPrinter implements MonitorPrinter {
    private static final String TAG = "WS_PERF";

    public void print(MonitorModel monitorModel) {
        SCLogCatUtil.info(TAG, monitorModel.toString());
    }

    public void noteTraficConsume(DataflowMonitorModel dataflowMonitorModel) {
        SCLogCatUtil.info(TAG, dataflowMonitorModel.toString());
    }
}
