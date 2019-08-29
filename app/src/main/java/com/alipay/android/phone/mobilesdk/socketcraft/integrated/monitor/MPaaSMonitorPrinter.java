package com.alipay.android.phone.mobilesdk.socketcraft.integrated.monitor;

import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorModel;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatUtil;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.monitor.DefaultMonitorPrinter;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.monitor.api.MonitorFactory;
import java.util.Map.Entry;

public class MPaaSMonitorPrinter extends DefaultMonitorPrinter {
    private static final String TAG = "MonitorPrinter";

    public void print(MonitorModel monitorModel) {
        try {
            if (monitorModel.itemMap.isEmpty()) {
                SCLogCatUtil.info(TAG, "itemMap is empty!");
                return;
            }
            Performance pf = new Performance();
            pf.setSubType(MonitorItemConstants.MONITOR_SUB_TYPE);
            pf.setParam1(MonitorItemConstants.PARTITION_NAME);
            pf.setParam2("INFO");
            pf.setParam3(monitorModel.logTitle);
            monitorModel.itemMap.entrySet();
            for (Entry entry : monitorModel.itemMap.entrySet()) {
                pf.getExtPramas().put(entry.getKey(), entry.getValue());
            }
            LoggerFactory.getMonitorLogger().performance(LogCategory.CATEGORY_NETWORK, pf);
            SCLogCatUtil.debug(pf.getSubType() + "_PERF", toPerformanceString(pf) + "\n");
        } catch (Throwable e) {
            SCLogCatUtil.error(TAG, "monitorLog exception. ", e);
        }
    }

    public void noteTraficConsume(DataflowMonitorModel dataflowMonitorModel) {
        if (dataflowMonitorModel == null) {
            try {
                SCLogCatUtil.warn((String) TAG, (String) "[noteTraficConsume] dataflowMonitorModel is null");
            } catch (Throwable e) {
                SCLogCatUtil.error(TAG, "[noteTraficConsume] exception. ", e);
            }
        } else if (dataflowMonitorModel.methodName == null || dataflowMonitorModel.methodName.trim().length() <= 0) {
            SCLogCatUtil.warn((String) TAG, (String) "[noteTraficConsume] methodName is empty.");
        } else {
            DataflowModel dataflowModel = DataflowModel.obtain(DataflowID.WEB_SOCKET, dataflowMonitorModel.url, (long) dataflowMonitorModel.sendSize, (long) dataflowMonitorModel.receiveSize, dataflowMonitorModel.methodName);
            dataflowModel.appId = dataflowMonitorModel.ownerId;
            if (DataflowMonitorModel.METHOD_NAME_SEND.equals(dataflowMonitorModel.methodName)) {
                dataflowModel.isUpload = true;
            }
            MonitorFactory.getMonitorContext().noteTraficConsume(dataflowModel);
            if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
                SCLogCatUtil.info(TAG, "[noteTraficConsume]  dataflowMonitorModel: " + dataflowMonitorModel.toString());
            }
        }
    }

    public String toPerformanceString(Performance pf) {
        StringBuffer sb = new StringBuffer();
        sb.append(pf.getSubType()).append(",");
        sb.append(pf.getParam1()).append(",");
        sb.append(pf.getParam2()).append(",");
        sb.append(pf.getParam3()).append(",");
        for (String key : pf.getExtPramas().keySet()) {
            sb.append(key + "=" + pf.getExtPramas().get(key) + "^");
        }
        return sb.toString();
    }
}
