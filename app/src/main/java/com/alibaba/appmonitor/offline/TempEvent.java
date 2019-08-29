package com.alibaba.appmonitor.offline;

import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.db.annotation.Column;
import com.alibaba.analytics.core.db.annotation.Ingore;

public class TempEvent extends Entity {
    @Ingore
    public static final String TAG_ACCESS = "access";
    @Ingore
    public static final String TAG_ACCESSSUBTYPE = "sub_access";
    @Ingore
    public static final String TAG_COMMITTIME = "commit_time";
    @Ingore
    public static final String TAG_MODULE = "module";
    @Ingore
    public static final String TAG_MONITOR_POINT = "monitor_point";
    @Column("access")
    public String access;
    @Column("sub_access")
    public String accessSubType;
    @Column("commit_time")
    public long commitTime;
    @Column("module")
    public String module;
    @Column("monitor_point")
    public String monitorPoint;

    protected TempEvent() {
    }

    public TempEvent(String str, String str2, String str3, String str4) {
        this.module = str;
        this.monitorPoint = str2;
        this.commitTime = System.currentTimeMillis() / 1000;
        this.access = str3;
        this.accessSubType = str4;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TempEvent{");
        sb.append('}');
        return sb.toString();
    }
}
