package com.alipay.mobile.framework.pipeline.analysis;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.sdk.util.h;

public class ThreadSnapshotDelta {
    public String[] name = new String[2];
    public int[] nice = new int[2];
    public int[] priority = new int[2];
    public String[] state = new String[2];
    public long sysUseTime = -1;
    public long sysWaitTime = -1;
    public int tid = -1;
    public long uptime = -1;
    public long userUseTime = -1;
    public long userWaitTime = -1;

    public ThreadSnapshotDelta(ThreadSnapshot info0, ThreadSnapshot info1) {
        this.tid = info1.tid;
        this.name[0] = info0.name;
        this.name[1] = info1.name;
        this.state[0] = info0.state;
        this.state[1] = info1.state;
        this.uptime = info1.uptime - info0.uptime;
        this.userUseTime = info1.userUseTime - info0.userUseTime;
        this.sysUseTime = info1.sysUseTime - info0.sysUseTime;
        this.userWaitTime = info1.userWaitTime - info0.userWaitTime;
        this.sysWaitTime = info1.sysWaitTime - info0.sysWaitTime;
        this.priority[0] = info0.priority;
        this.priority[1] = info1.priority;
        this.nice[0] = info0.nice;
        this.nice[1] = info1.nice;
    }

    public String toString() {
        return parcelString() + AUScreenAdaptTool.PREFIX_ID + super.toString();
    }

    public String parcelString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{\"tid\":").append(this.tid);
        buffer.append(",\"name0\":\"").append(this.name[0]).append("\"");
        buffer.append(",\"name1\":\"").append(this.name[1]).append("\"");
        buffer.append(",\"state0\":\"").append(this.state[0]).append("\"");
        buffer.append(",\"state1\":\"").append(this.state[1]).append("\"");
        buffer.append(",\"uptime\":").append(this.uptime);
        buffer.append(",\"userUseTime\":").append(this.userUseTime);
        if (this.sysUseTime > 0) {
            buffer.append(",\"sysUseTime\":").append(this.sysUseTime);
        }
        if (this.userWaitTime > 0) {
            buffer.append(",\"userWaitTime\":").append(this.userWaitTime);
        }
        if (this.sysWaitTime > 0) {
            buffer.append(",\"sysWaitTime\":").append(this.sysWaitTime);
        }
        buffer.append(",\"priority0\":").append(this.priority[0]);
        buffer.append(",\"priority1\":").append(this.priority[1]);
        buffer.append(",\"nice0\":").append(this.nice[0]);
        buffer.append(",\"nice1\":").append(this.nice[1]);
        buffer.append(h.d);
        return buffer.toString();
    }
}
