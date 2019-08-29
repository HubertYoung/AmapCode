package com.autonavi.minimap.agroup.entity;

import com.amap.bundle.blutils.log.DebugLog;
import java.io.Serializable;

public class TeamAnnouncement implements Serializable, Cloneable {
    private static final long serialVersionUID = 4423042382146232511L;
    private String mContent;
    private long mTimeStamp;

    public final void setTimeStamp(long j) {
        this.mTimeStamp = j;
    }

    public final long getTimeStamp() {
        return this.mTimeStamp;
    }

    public final void setContent(String str) {
        this.mContent = str;
    }

    public final String getContent() {
        return this.mContent;
    }

    public TeamAnnouncement clone() {
        try {
            return (TeamAnnouncement) super.clone();
        } catch (CloneNotSupportedException e) {
            DebugLog.e("TeamAnnouncement", "clone", e);
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TeamAnnouncement{timeStamp='");
        sb.append(this.mTimeStamp);
        sb.append('\'');
        sb.append(", content='");
        sb.append(this.mContent);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
