package com.alipay.mobile.common.logging.render;

import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;

public class BatteryRender extends BaseRender {
    public BatteryRender(LogContext logContext) {
        super(logContext);
    }

    public final String a(BatteryModel batteryModel) {
        if (batteryModel.type == BatteryID.UNKNOWN) {
            throw new IllegalStateException("batteryModel has been recycled");
        }
        StringBuilder msg = new StringBuilder();
        msg.append("BTR");
        LoggingUtil.appendParam(msg, LoggingUtil.getNowTime());
        LoggingUtil.appendParam(msg, this.b.getProductId());
        LoggingUtil.appendParam(msg, this.b.getProductVersion());
        LoggingUtil.appendParam(msg, this.b.getUserId());
        LoggingUtil.appendParam(msg, this.b.getDeviceId());
        if (batteryModel.type == null) {
            batteryModel.type = BatteryID.UNKNOWN;
        }
        LoggingUtil.appendParam(msg, batteryModel.type.getDes());
        LoggingUtil.appendParam(msg, String.valueOf(batteryModel.power));
        LoggingUtil.appendParam(msg, batteryModel.bundle);
        LoggingUtil.appendExtParam(msg, batteryModel.params);
        LoggingUtil.appendParam(msg, VERSION.RELEASE);
        LoggingUtil.appendParam(msg, Build.MODEL);
        LoggingUtil.appendParam(msg, batteryModel.diagnose);
        LoggingUtil.appendParam(msg, LoggerFactory.getProcessInfo().getProcessAlias());
        LoggingUtil.appendParam(msg, TianyanLoggingStatus.isMonitorBackground() ? "1" : "0");
        LoggingUtil.appendParam(msg, TianyanLoggingStatus.isStrictBackground() ? "1" : "0");
        LoggingUtil.appendParam(msg, TianyanLoggingStatus.isRelaxedBackground() ? "1" : "0");
        LoggingUtil.appendParam(msg, a());
        batteryModel.recycle();
        return msg.append("$$").toString();
    }
}
