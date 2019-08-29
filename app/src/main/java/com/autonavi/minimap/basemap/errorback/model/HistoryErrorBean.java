package com.autonavi.minimap.basemap.errorback.model;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class HistoryErrorBean {
    public long date;
    public String fromName;
    public String naviId;
    public String toName;

    public HistoryErrorBean(String str, String str2, String str3, long j) {
        this.fromName = str;
        this.toName = str2;
        this.naviId = str3;
        this.date = j;
    }
}
