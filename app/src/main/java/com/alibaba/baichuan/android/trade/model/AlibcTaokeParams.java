package com.alibaba.baichuan.android.trade.model;

import java.io.Serializable;

public class AlibcTaokeParams implements Serializable {
    public static final String PID_PREFIX = "mm_";
    public String pid;
    public String subPid;
    public String unionId;

    public AlibcTaokeParams(String str, String str2, String str3) {
        this.pid = str;
        this.unionId = str2;
        this.subPid = str3;
    }

    public boolean isValidPid() {
        return this.pid != null && this.pid.startsWith(PID_PREFIX);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{\"pid\":\"");
        sb.append(this.pid == null ? "" : this.pid);
        sb.append("\",\"unionId\":\"");
        sb.append(this.unionId == null ? "" : this.unionId);
        sb.append("\",\"subPid\":\"");
        sb.append(this.subPid == null ? "" : this.subPid);
        sb.append("\"}");
        return sb.toString();
    }
}
