package com.alipay.mobile.tinyappcustom.a;

import android.os.Build;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.monitor.util.MonitorUtils;
import com.alipay.mobile.monitor.util.MonitorUtils.FillBufferHandler;
import com.alipay.mobile.monitor.util.NetUtils;

/* compiled from: GuideAliveLogger */
public final class b {
    private Behavor a = new Behavor();
    private String b;

    public b(String type) {
        this.a.setBehaviourPro("TransferQRCode");
        this.b = type;
    }

    public final b a(String key, Object value) {
        this.a.addExtParam(key, value == null ? "" : value.toString());
        return this;
    }

    public final b a(String bizType) {
        this.a.setBehaviourPro(bizType);
        return this;
    }

    public final b a() {
        this.a.setLoggerLevel(1);
        return this;
    }

    public final void b() {
        a("network", NetUtils.getNetworkTypeOptimized());
        this.a.setParam1(Build.BRAND.toLowerCase());
        this.a.setParam2(LoggerFactory.getDeviceProperty().getRomVersion());
        this.a.setUserCaseID("GuideAliveLogger");
        this.a.setSeedID(this.b);
        LoggerFactory.getBehavorLogger().event(null, this.a);
        StringBuilder message = new StringBuilder();
        message.append(", type: ").append(this.b);
        MonitorUtils.fillBufferWithParams(message, this.a.getExtParams(), (FillBufferHandler) null);
        LoggerFactory.getTraceLogger().info("GuideAliveLogger", message.toString());
    }
}
