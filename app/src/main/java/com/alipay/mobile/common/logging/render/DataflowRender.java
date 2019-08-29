package com.alipay.mobile.common.logging.render;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.logging.process.VariableStoreInToolsProcess;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.NetUtil;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;

public class DataflowRender extends BaseRender {
    public DataflowRender(LogContext logContext) {
        super(logContext);
    }

    public final String a(DataflowModel dataflowModel) {
        if (!dataflowModel.isInUse()) {
            throw new IllegalStateException("dataflowModel has been recycled");
        }
        StringBuilder msg = new StringBuilder();
        String processAlias = LoggerFactory.getProcessInfo().getProcessAlias();
        if (dataflowModel.type == DataflowID.MDAP_LOG && !TextUtils.isEmpty(VariableStoreInToolsProcess.d)) {
            processAlias = VariableStoreInToolsProcess.d;
        }
        boolean isMonitorBackground = TianyanLoggingStatus.isMonitorBackground();
        if (dataflowModel.type == DataflowID.MDAP_LOG && !TextUtils.isEmpty(VariableStoreInToolsProcess.d)) {
            isMonitorBackground = VariableStoreInToolsProcess.a;
        }
        boolean isStrictBackground = TianyanLoggingStatus.isStrictBackground();
        if (dataflowModel.type == DataflowID.MDAP_LOG && !TextUtils.isEmpty(VariableStoreInToolsProcess.d)) {
            isStrictBackground = VariableStoreInToolsProcess.b;
        }
        boolean isRelaxedBackground = TianyanLoggingStatus.isRelaxedBackground();
        if (dataflowModel.type == DataflowID.MDAP_LOG && !TextUtils.isEmpty(VariableStoreInToolsProcess.d)) {
            isRelaxedBackground = VariableStoreInToolsProcess.c;
        }
        msg.append("DF");
        LoggingUtil.appendParam(msg, LoggingUtil.getNowTime());
        LoggingUtil.appendParam(msg, this.b.getProductId());
        LoggingUtil.appendParam(msg, this.b.getProductVersion());
        LoggingUtil.appendParam(msg, this.b.getUserId());
        LoggingUtil.appendParam(msg, this.b.getDeviceId());
        Context context = this.b.getApplicationContext();
        LoggingUtil.appendParam(msg, isStrictBackground ? NetUtil.getNetworkTypeOptimizedStrict(context) : NetUtil.getNetworkTypeOptimized(context));
        if (dataflowModel.type == null) {
            dataflowModel.type = DataflowID.UNKNOWN;
        }
        LoggingUtil.appendParam(msg, dataflowModel.type.getDes());
        LoggingUtil.appendParam(msg, dataflowModel.url);
        LoggingUtil.appendParam(msg, String.valueOf(dataflowModel.reqSize + dataflowModel.respSize));
        LoggingUtil.appendParam(msg, dataflowModel.bundle);
        LoggingUtil.appendExtParam(msg, dataflowModel.params);
        LoggingUtil.appendParam(msg, VERSION.RELEASE);
        LoggingUtil.appendParam(msg, Build.MODEL);
        LoggingUtil.appendParam(msg, dataflowModel.diagnose);
        LoggingUtil.appendParam(msg, processAlias);
        LoggingUtil.appendParam(msg, isMonitorBackground ? "1" : "0");
        LoggingUtil.appendParam(msg, isStrictBackground ? "1" : "0");
        LoggingUtil.appendParam(msg, isRelaxedBackground ? "1" : "0");
        LoggingUtil.appendParam(msg, a());
        LoggingUtil.appendParam(msg, dataflowModel.getLoggerLevel());
        LoggingUtil.appendParam(msg, LogStrategyManager.getInstance().getHitTestRate(LogCategory.CATEGORY_DATAFLOW, dataflowModel.getLoggerLevel()));
        dataflowModel.recycle();
        return msg.append("$$").toString();
    }
}
