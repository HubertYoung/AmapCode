package com.alipay.mobile.common.netsdkextdepend.monitorinfo;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.netsdkextdependapi.logger.LoggerManager;
import com.alipay.mobile.common.netsdkextdependapi.logger.LoggerManagerFactory;
import com.alipay.mobile.common.netsdkextdependapi.monitorinfo.MonitorInfoManagerAdapter;
import com.alipay.mobile.common.netsdkextdependapi.monitorinfo.MonitorLoggerModel;

public class DefaultMonitorInfoManager extends MonitorInfoManagerAdapter {
    public void record(MonitorLoggerModel monitorLoggerModel) {
        if (monitorLoggerModel == null) {
            ((LoggerManager) LoggerManagerFactory.getInstance().getDefaultBean()).warn((String) "DefaultMonitorInfoManager", (String) "[record] monitorLoggerModel is null");
            return;
        }
        Performance performance = new Performance();
        performance.setLoggerLevel(monitorLoggerModel.getLoggerLevel());
        performance.setSubType(monitorLoggerModel.getSubType());
        performance.setParam1(monitorLoggerModel.getParam1());
        performance.setParam2(monitorLoggerModel.getParam2());
        performance.setParam3(monitorLoggerModel.getParam3());
        performance.getExtPramas().putAll(monitorLoggerModel.getExtParams());
        if (!TextUtils.isEmpty(monitorLoggerModel.getBizType())) {
            LoggerFactory.getMonitorLogger().performance(monitorLoggerModel.getBizType(), performance);
        } else {
            LoggerFactory.getMonitorLogger().performance((String) "network", performance);
        }
    }
}
