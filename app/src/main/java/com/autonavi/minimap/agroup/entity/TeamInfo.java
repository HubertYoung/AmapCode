package com.autonavi.minimap.agroup.entity;

import android.support.annotation.Nullable;
import com.amap.bundle.blutils.log.DebugLog;
import java.io.Serializable;

public class TeamInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = -1545318433020037075L;
    private DestinationInfo destination = new DestinationInfo();
    public long lastUpdate;
    public TeamAnnouncement mAnnouncement = new TeamAnnouncement();
    public long teamCreatedTime;
    public long teamDismissTime;
    public String teamId;
    public String teamLeaderUid;
    public String teamName;
    public String teamNumber;

    @Nullable
    public DestinationInfo getDestinationInfo() {
        return this.destination;
    }

    public TeamInfo setDestination(DestinationInfo destinationInfo) {
        this.destination = destinationInfo;
        return this;
    }

    public TeamInfo clone() {
        TeamInfo teamInfo;
        Throwable e;
        try {
            teamInfo = (TeamInfo) super.clone();
            try {
                teamInfo.destination = this.destination.clone();
            } catch (CloneNotSupportedException e2) {
                e = e2;
            }
        } catch (CloneNotSupportedException e3) {
            Throwable th = e3;
            teamInfo = null;
            e = th;
            DebugLog.e("TeamInfo", "clone", e);
            return teamInfo;
        }
        return teamInfo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TeamInfo{teamId='");
        sb.append(this.teamId);
        sb.append('\'');
        sb.append(", teamName='");
        sb.append(this.teamName);
        sb.append('\'');
        sb.append(", teamNumber='");
        sb.append(this.teamNumber);
        sb.append('\'');
        sb.append(", teamLeaderUid='");
        sb.append(this.teamLeaderUid);
        sb.append('\'');
        sb.append(", teamCreatedTime=");
        sb.append(this.teamCreatedTime);
        sb.append(", teamDismissTime=");
        sb.append(this.teamDismissTime);
        sb.append(", lastUpdate=");
        sb.append(this.lastUpdate);
        sb.append(", destination=");
        sb.append(this.destination);
        sb.append(", announcement=");
        sb.append(this.mAnnouncement);
        sb.append('}');
        return sb.toString();
    }
}
