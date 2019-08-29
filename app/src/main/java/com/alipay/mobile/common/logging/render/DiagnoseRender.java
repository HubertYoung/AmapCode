package com.alipay.mobile.common.logging.render;

import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.helper.DeviceHWRenderHelper;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.NetUtil;
import java.util.HashMap;
import java.util.Map;

public class DiagnoseRender extends BaseRender {
    public DiagnoseRender(LogContext logContext) {
        super(logContext);
    }

    public final String a(String type, String subType, Throwable cause, Map<String, String> diagnoseParam) {
        StringBuilder msg = new StringBuilder();
        msg.append("D-EM");
        LoggingUtil.appendParam(msg, LoggingUtil.getNowTime());
        LoggingUtil.appendParam(msg, this.b.getProductId());
        LoggingUtil.appendParam(msg, this.b.getProductVersion());
        LoggingUtil.appendParam(msg, "2");
        LoggingUtil.appendParam(msg, this.b.getClientId());
        LoggingUtil.appendParam(msg, this.b.getUserId());
        LoggingUtil.appendParam(msg, NetUtil.getNetworkTypeOptimized(this.b.getApplicationContext()));
        LoggingUtil.appendParam(msg, Build.MODEL);
        LoggingUtil.appendParam(msg, VERSION.RELEASE);
        LoggingUtil.appendParam(msg, this.b.getReleaseCode());
        LoggingUtil.appendParam(msg, this.b.getChannelId());
        LoggingUtil.appendParam(msg, this.b.getReleaseType());
        LoggingUtil.appendParam(msg, this.b.getStorageParam(LogContext.STORAGE_APPID));
        LoggingUtil.appendParam(msg, type);
        LoggingUtil.appendParam(msg, subType);
        if (cause != null) {
            if (diagnoseParam == null) {
                diagnoseParam = new HashMap<>();
            }
            diagnoseParam.put("stackFrame", LoggingUtil.throwableToString(cause));
        }
        LoggingUtil.appendExtParam(msg, diagnoseParam);
        LoggingUtil.appendParam(msg, this.b.getLanguage());
        LoggingUtil.appendParam(msg, this.b.getHotpatchVersion());
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.a()));
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.b()));
        LoggingUtil.appendParam(msg, String.valueOf(DeviceHWRenderHelper.a(this.b.getApplicationContext())));
        LoggingUtil.appendParam(msg, null);
        LoggingUtil.appendParam(msg, this.b.getApkUniqueId());
        LoggingUtil.appendParam(msg, LoggerFactory.getProcessInfo().getProcessAlias());
        LoggingUtil.appendParam(msg, this.b.getDeviceId());
        LoggingUtil.appendExtParam(msg, this.b.getBizExternParams());
        LoggingUtil.appendParam(msg, a());
        return msg.append("$$").toString();
    }
}
