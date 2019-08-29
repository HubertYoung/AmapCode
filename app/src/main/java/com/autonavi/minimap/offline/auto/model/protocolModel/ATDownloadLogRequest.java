package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATDownloadLogRequest {
    private long lastDownloadTime;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public long getLastDownloadTime() {
        return this.lastDownloadTime;
    }

    public void setLastDownloadTime(long j) {
        this.lastDownloadTime = j;
    }
}
