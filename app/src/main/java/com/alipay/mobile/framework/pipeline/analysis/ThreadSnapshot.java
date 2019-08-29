package com.alipay.mobile.framework.pipeline.analysis;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.sdk.util.h;

public class ThreadSnapshot {
    public String name;
    public int nice = -1;
    public int priority = -1;
    public String state;
    public long sysUseTime = -1;
    public long sysWaitTime = -1;
    public int tid = -1;
    public long uptime = -1;
    public long userUseTime = -1;
    public long userWaitTime = -1;

    public String toString() {
        return parcelString() + AUScreenAdaptTool.PREFIX_ID + super.toString();
    }

    public String parcelString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{\"a\":\"").append(this.name).append("\"");
        buffer.append(",\"d\":").append(this.uptime);
        buffer.append(",\"e\":").append(this.userUseTime);
        if (this.sysUseTime > -1) {
            buffer.append(",\"f\":").append(this.sysUseTime);
        }
        if (this.userWaitTime > -1) {
            buffer.append(",\"g\":").append(this.userWaitTime);
        }
        if (this.sysWaitTime > -1) {
            buffer.append(",\"h\":").append(this.sysWaitTime);
        }
        buffer.append(",\"i\":").append(this.priority);
        buffer.append(h.d);
        return buffer.toString();
    }
}
