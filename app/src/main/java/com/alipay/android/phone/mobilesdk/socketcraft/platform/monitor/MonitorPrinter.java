package com.alipay.android.phone.mobilesdk.socketcraft.platform.monitor;

import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorModel;

public interface MonitorPrinter {
    void noteTraficConsume(DataflowMonitorModel dataflowMonitorModel);

    void print(MonitorModel monitorModel);
}
