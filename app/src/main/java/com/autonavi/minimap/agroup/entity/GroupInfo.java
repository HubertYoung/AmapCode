package com.autonavi.minimap.agroup.entity;

import android.text.TextUtils;
import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupInfo implements Serializable, Cloneable {
    private static final int INVALID_CODE = -1;
    private static final int UPDATE_MEMBER_LIST_VALUE = 8;
    private static final int UPDATE_MEMBER_STAMP_VALUE = 4;
    private static final int UPDATE_NONE_VALUE = 0;
    private static final int UPDATE_TEAM_INFO_VALUE = 1;
    private static final int UPDATE_TEAM_STAMP_VALUE = 2;
    private static final int UPDATE_TEAM_STATUS_VALUE = 16;
    private static final long serialVersionUID = -1804567839610821544L;
    private int mCode = -1;
    private int mSuperGroupMemberCount = -1;
    private List<MemberInfo> memberInfoList;
    private String memberStamp;
    private TeamInfo teamInfo;
    private String teamStamp;
    private TeamStatus teamStatus = TeamStatus.STATUS_NONE;
    private int updateMask = 0;

    public TeamInfo getTeamInfo() {
        return this.teamInfo;
    }

    public void setTeamInfo(TeamInfo teamInfo2) {
        if (teamInfo2 != null) {
            this.teamInfo = teamInfo2;
            this.updateMask |= 1;
        }
    }

    public String getTeamStamp() {
        return this.teamStamp;
    }

    public void setTeamStamp(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals(this.teamStamp)) {
            this.teamStamp = str;
            this.updateMask |= 2;
        }
    }

    public String getMemberStamp() {
        return this.memberStamp;
    }

    public void setMemberStamp(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals(this.memberStamp)) {
            this.memberStamp = str;
            this.updateMask |= 4;
        }
    }

    public List<MemberInfo> getMemberInfoList() {
        return this.memberInfoList;
    }

    public void setMemberInfoList(List<MemberInfo> list) {
        this.memberInfoList = list;
        this.updateMask |= 8;
    }

    public TeamStatus getTeamStatus() {
        return this.teamStatus;
    }

    public void setTeamStatus(TeamStatus teamStatus2) {
        if (teamStatus2 != null && !teamStatus2.equals(this.teamStatus)) {
            this.teamStatus = teamStatus2;
            this.updateMask |= 16;
        }
    }

    public void setCode(int i) {
        this.mCode = i;
    }

    public int getCode() {
        return this.mCode;
    }

    public int getUpdateMask() {
        return this.updateMask;
    }

    public final void setSuperGroupMemberCount(int i) {
        this.mSuperGroupMemberCount = i;
    }

    public final int getSuperGroupMemberCount() {
        return this.mSuperGroupMemberCount;
    }

    public void clear() {
        this.teamStatus = TeamStatus.STATUS_NONE;
        this.updateMask = 0;
        this.teamInfo = null;
        this.teamStamp = null;
        this.memberStamp = null;
        this.memberInfoList = null;
        this.mSuperGroupMemberCount = -1;
        this.mCode = -1;
    }

    public GroupInfo clone() {
        GroupInfo groupInfo;
        Throwable e;
        try {
            groupInfo = (GroupInfo) super.clone();
            try {
                if (this.teamInfo != null) {
                    groupInfo.teamInfo = this.teamInfo.clone();
                }
                if (this.memberInfoList != null && !this.memberInfoList.isEmpty()) {
                    groupInfo.memberInfoList = new ArrayList();
                    for (MemberInfo clone : this.memberInfoList) {
                        groupInfo.memberInfoList.add(clone.clone());
                    }
                }
                groupInfo.teamStatus = this.teamStatus;
                groupInfo.mCode = this.mCode;
                groupInfo.mSuperGroupMemberCount = this.mSuperGroupMemberCount;
            } catch (CloneNotSupportedException e2) {
                e = e2;
                DebugLog.e("TeamInfo", "clone", e);
                return groupInfo;
            }
        } catch (CloneNotSupportedException e3) {
            Throwable th = e3;
            groupInfo = null;
            e = th;
            DebugLog.e("TeamInfo", "clone", e);
            return groupInfo;
        }
        return groupInfo;
    }

    private String memberInfoListToString() {
        StringBuilder sb = new StringBuilder();
        if (this.memberInfoList != null && !this.memberInfoList.isEmpty()) {
            sb.append("[\n");
            int i = 0;
            for (MemberInfo append : this.memberInfoList) {
                sb.append("index:");
                sb.append(i);
                sb.append(": ");
                sb.append(append);
                sb.append(10);
                i++;
            }
            sb.append("\n]");
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GroupInfo{updateMask=");
        sb.append(this.updateMask);
        sb.append(", teamInfo=");
        sb.append(this.teamInfo == null ? "null" : this.teamInfo);
        sb.append(", teamStamp='");
        sb.append(this.teamStamp);
        sb.append('\'');
        sb.append(", memberStamp='");
        sb.append(this.memberStamp);
        sb.append('\'');
        sb.append(", memberInfoList=");
        sb.append(memberInfoListToString());
        sb.append(", superGroupMemberCount=");
        sb.append(this.mSuperGroupMemberCount);
        sb.append(", teamStatus=");
        sb.append(this.teamStatus);
        sb.append(", code=");
        sb.append(this.mCode);
        sb.append('}');
        return sb.toString();
    }
}
