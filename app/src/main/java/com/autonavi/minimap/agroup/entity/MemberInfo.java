package com.autonavi.minimap.agroup.entity;

import com.amap.bundle.blutils.log.DebugLog;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;

public class MemberInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1408442125031209966L;
    private CreateType createType;
    private Integer hashCode;
    private int identityType;
    public String imgUrl;
    private int index;
    public boolean isSelf = false;
    public long joinTime;
    public double lat;
    public long locationUpdateTime;
    public double lon;
    public String nickname;
    public boolean online;
    public String source = "amap7a";
    public String uid;

    public enum CreateType {
        BASE_INFO,
        LOCATION_INFO,
        ALL_INFO
    }

    public enum IdentityType {
        COMMON(0),
        MYSELF(1),
        LEADER(2);
        
        /* access modifiers changed from: private */
        public int type;

        private IdentityType(int i) {
            this.type = i;
        }

        public final int getType() {
            return this.type;
        }
    }

    private MemberInfo() {
    }

    public CreateType getCreateType() {
        return this.createType;
    }

    public static MemberInfo createMemberInfo(String str, String str2, String str3, double d, double d2, long j, long j2, boolean z, String str4, int i) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.createType = CreateType.ALL_INFO;
        memberInfo.uid = str;
        memberInfo.imgUrl = str2;
        memberInfo.nickname = str3;
        memberInfo.lat = d;
        memberInfo.lon = d2;
        memberInfo.locationUpdateTime = j;
        memberInfo.joinTime = j2;
        memberInfo.online = z;
        memberInfo.source = str4;
        memberInfo.index = i;
        return memberInfo;
    }

    public static MemberInfo createMemberInfo(String str, double d, double d2, long j, boolean z) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.createType = CreateType.LOCATION_INFO;
        memberInfo.uid = str;
        memberInfo.lat = d;
        memberInfo.lon = d2;
        memberInfo.online = z;
        memberInfo.locationUpdateTime = j;
        return memberInfo;
    }

    public MemberInfo copyFrom(MemberInfo memberInfo) {
        if (memberInfo == null) {
            return this;
        }
        CreateType createType2 = memberInfo.getCreateType();
        if (createType2 == CreateType.ALL_INFO) {
            this.uid = memberInfo.uid;
            this.imgUrl = memberInfo.imgUrl;
            this.nickname = memberInfo.nickname;
            this.lat = memberInfo.lat;
            this.lon = memberInfo.lon;
            this.locationUpdateTime = memberInfo.locationUpdateTime;
            this.joinTime = memberInfo.joinTime;
            this.online = memberInfo.online;
            this.source = memberInfo.source;
            this.index = memberInfo.index;
            this.identityType = memberInfo.identityType;
        } else if (createType2 == CreateType.BASE_INFO) {
            this.uid = memberInfo.uid;
            this.imgUrl = memberInfo.imgUrl;
            this.nickname = memberInfo.nickname;
            this.identityType = memberInfo.identityType;
        } else if (createType2 == CreateType.LOCATION_INFO) {
            this.uid = memberInfo.uid;
            this.lat = memberInfo.lat;
            this.lon = memberInfo.lon;
            this.online = memberInfo.online;
            this.locationUpdateTime = memberInfo.locationUpdateTime;
            this.identityType = memberInfo.identityType;
        }
        return this;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public void setIdentityType(IdentityType identityType2) {
        this.identityType = identityType2.type | this.identityType;
    }

    public boolean isMyself() {
        return (this.identityType & IdentityType.MYSELF.type) > 0;
    }

    public boolean isLeader() {
        return (this.identityType & IdentityType.LEADER.type) > 0;
    }

    public int getIdentityType() {
        return this.identityType;
    }

    @SuppressFBWarnings({"HE_HASHCODE_USE_OBJECT_EQUALS"})
    public int hashCode() {
        if (this.hashCode == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.nickname);
            sb.append(this.imgUrl);
            sb.append(this.online);
            sb.append(this.uid);
            this.hashCode = Integer.valueOf(sb.toString().hashCode());
        }
        return this.hashCode.intValue();
    }

    public MemberInfo clone() {
        try {
            return (MemberInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            DebugLog.e("MemberInfo", "clone", e);
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("MemberInfo{uid='");
        sb.append(this.uid);
        sb.append('\'');
        sb.append(", imgUrl='");
        sb.append(this.imgUrl);
        sb.append('\'');
        sb.append(", nickname='");
        sb.append(this.nickname);
        sb.append('\'');
        sb.append(", lat=");
        sb.append(this.lat);
        sb.append(", lon=");
        sb.append(this.lon);
        sb.append(", locationUpdateTime=");
        sb.append(this.locationUpdateTime);
        sb.append(", joinTime=");
        sb.append(this.joinTime);
        sb.append(", online=");
        sb.append(this.online);
        sb.append(", source='");
        sb.append(this.source);
        sb.append('\'');
        sb.append(", index=");
        sb.append(this.index);
        sb.append(", identityType=");
        sb.append(this.identityType);
        sb.append(", createType=");
        sb.append(this.createType);
        sb.append('}');
        return sb.toString();
    }
}
