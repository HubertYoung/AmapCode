package com.alipay.mobile.framework.pipeline.analysis;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.sdk.util.h;

public class AnalysedRunnableInfo {
    public int analysisType;
    public long endAnalysisUptime;
    public long endRunningUptime;
    public String endThreadName;
    public ThreadSnapshot endThreadSnapshot;
    public boolean isRunning;
    public String name;
    public long startAnalysisUptime;
    public long startRunningUptime;
    public String startThreadName;
    public ThreadSnapshot startThreadSnapshot;
    public int tid;
    public int uptimeType;

    public AnalysedRunnableInfo() {
        reset();
    }

    public void reset() {
        this.isRunning = false;
        this.tid = -1;
        this.name = null;
        this.startThreadName = null;
        this.endThreadName = null;
        this.startRunningUptime = -1;
        this.endRunningUptime = -1;
        this.startAnalysisUptime = -1;
        this.endAnalysisUptime = -1;
        this.startThreadSnapshot = null;
        this.endThreadSnapshot = null;
        this.analysisType = -1;
        this.uptimeType = -1;
    }

    public AnalysedRunnableInfo fullClone() {
        AnalysedRunnableInfo info = new AnalysedRunnableInfo();
        info.isRunning = this.isRunning;
        info.tid = this.tid;
        info.name = this.name;
        info.startThreadName = this.startThreadName;
        info.endThreadName = this.endThreadName;
        info.startRunningUptime = this.startRunningUptime;
        info.endRunningUptime = this.endRunningUptime;
        info.startAnalysisUptime = this.startAnalysisUptime;
        info.endAnalysisUptime = this.endAnalysisUptime;
        info.startThreadSnapshot = this.startThreadSnapshot;
        info.endThreadSnapshot = this.endThreadSnapshot;
        info.analysisType = this.analysisType;
        info.uptimeType = this.uptimeType;
        return info;
    }

    public String toString() {
        return parcelString() + AUScreenAdaptTool.PREFIX_ID + super.toString();
    }

    public String parcelString() {
        String startSnapshotParcel = this.startThreadSnapshot == null ? null : this.startThreadSnapshot.parcelString();
        String endSnapshotParcel = this.endThreadSnapshot == null ? null : this.endThreadSnapshot.parcelString();
        StringBuilder buffer = new StringBuilder();
        buffer.append("{\"a\":\"").append(this.name).append("\"");
        buffer.append(",\"c\":").append(startSnapshotParcel);
        buffer.append(",\"d\":").append(endSnapshotParcel);
        buffer.append(",\"e\":").append(this.tid);
        buffer.append(",\"m\":").append(this.uptimeType);
        buffer.append(h.d);
        return buffer.toString();
    }
}
