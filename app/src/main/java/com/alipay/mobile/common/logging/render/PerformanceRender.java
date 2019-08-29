package com.alipay.mobile.common.logging.render;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceInfo;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.helper.DeviceHWRenderHelper;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.NetUtil;
import java.util.Map;

public class PerformanceRender extends BaseRender {
    public PerformanceRender(LogContext logContext) {
        super(logContext);
    }

    public final String a(PerformanceID performanceID, Performance performance, Map<String, String> customerParams, PendingRender pendingRender) {
        return a(performanceID, performance.getSubType(), performance.getParam1(), performance.getParam2(), performance.getParam3(), performance.getPageId(), performance.getExtPramas(), customerParams, performance.getLoggerLevel(), pendingRender);
    }

    public final String a(String performanceID, Performance performance, Map<String, String> customerParams, PendingRender pendingRender) {
        return a(performanceID, performance.getSubType(), performance.getParam1(), performance.getParam2(), performance.getParam3(), performance.getPageId(), performance.getExtPramas(), customerParams, performance.getLoggerLevel(), pendingRender);
    }

    public final String a(PerformanceID performanceID, String subType, String param1, String param2, String param3, Map<String, String> extParam) {
        return a(performanceID, subType, param1, param2, param3, (String) null, extParam, (Map<String, String>) null, 2, (PendingRender) null);
    }

    private String a(PerformanceID performanceID, String subType, String param1, String param2, String param3, String pageId, Map<String, String> extParam, Map<String, String> customerParams, int loggerLevel, PendingRender pendingRender) {
        return a(performanceID.getDes(), subType, param1, param2, param3, pageId, extParam, customerParams, loggerLevel, pendingRender);
    }

    private String a(String performanceID, String subType, String param1, String param2, String param3, String pageId, Map<String, String> extParam, Map<String, String> customerParams, int loggerLevel, PendingRender pendingRender) {
        StringBuilder msg = new StringBuilder();
        String header = null;
        if (customerParams != null) {
            header = customerParams.get(Performance.KEY_LOG_HEADER);
        }
        if (!TextUtils.isEmpty(header)) {
            msg.append(header);
        } else {
            msg.append("D-MM");
        }
        if (pendingRender == null || pendingRender.b == null) {
            LoggingUtil.appendParam(msg, LoggingUtil.getNowTime());
        } else {
            LoggingUtil.appendParam(msg, pendingRender.b);
        }
        LoggingUtil.appendParam(msg, this.b.getProductId());
        LoggingUtil.appendParam(msg, this.b.getProductVersion());
        LoggingUtil.appendParam(msg, "2");
        LoggingUtil.appendParam(msg, this.b.getClientId());
        LoggingUtil.appendParam(msg, this.b.getSessionId());
        LoggingUtil.appendParam(msg, this.b.getUserId());
        if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.e);
        } else {
            LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.LOCAL_STORAGE_ACTIONID));
        }
        if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.f);
        } else {
            LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN));
        }
        LoggingUtil.appendParam(msg, LogStrategyManager.getInstance().getHitTestRate(performanceID, loggerLevel));
        LoggingUtil.appendParam(msg, performanceID);
        LoggingUtil.appendParam(msg, subType);
        LoggingUtil.appendParam(msg, param1);
        LoggingUtil.appendParam(msg, param2);
        LoggingUtil.appendParam(msg, param3);
        LoggingUtil.appendExtParam(msg, extParam);
        LoggingUtil.appendParam(msg, "android");
        LoggingUtil.appendParam(msg, VERSION.RELEASE);
        LoggingUtil.appendParam(msg, NetUtil.getNetworkTypeOptimized(this.b.getApplicationContext()));
        LoggingUtil.appendParam(msg, Build.MODEL);
        LoggingUtil.appendParam(msg, this.b.getReleaseCode());
        LoggingUtil.appendParam(msg, this.b.getChannelId());
        LoggingUtil.appendParam(msg, this.b.getDeviceId());
        LoggingUtil.appendParam(msg, this.b.getLanguage());
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.a()));
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.b()));
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.a(this.b.getApplicationContext())));
        LoggingUtil.appendParam(msg, this.b.getHotpatchVersion());
        LoggingUtil.appendParam(msg, this.b.getApkUniqueId());
        LoggingUtil.appendExtParam(msg, this.b.getBizExternParams());
        LoggingUtil.appendParam(msg, pageId);
        LoggingUtil.appendParam(msg, DeviceInfo.getInstance(this.b.getApplicationContext()).getResolution());
        if (pendingRender != null) {
            LoggingUtil.appendParam(msg, pendingRender.g);
        } else {
            LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.STORAGE_APPID));
        }
        LoggingUtil.appendParam(msg, LoggerFactory.getProcessInfo().getProcessAlias());
        LoggingUtil.appendParam(msg, a());
        LoggingUtil.appendParam(msg, String.valueOf(loggerLevel));
        return msg.append("$$").toString();
    }
}
