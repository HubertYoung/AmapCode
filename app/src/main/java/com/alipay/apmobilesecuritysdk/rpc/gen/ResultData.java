package com.alipay.apmobilesecuritysdk.rpc.gen;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class ResultData extends Message {
    public static final String DEFAULT_APDID = "";
    public static final String DEFAULT_APDIDTOKEN = "";
    public static final String DEFAULT_APPLISTCMDVER = "";
    public static final String DEFAULT_CREATETIME = "";
    public static final String DEFAULT_DRMSWITCH = "";
    public static final String DEFAULT_DYNAMICKEY = "";
    public static final String DEFAULT_TIMEINTERVAL = "";
    public static final String DEFAULT_WEBRTCURL = "";
    public static final int TAG_APDID = 1;
    public static final int TAG_APDIDTOKEN = 2;
    public static final int TAG_APPLISTCMDVER = 6;
    public static final int TAG_CREATETIME = 3;
    public static final int TAG_DRMSWITCH = 7;
    public static final int TAG_DYNAMICKEY = 4;
    public static final int TAG_TIMEINTERVAL = 5;
    public static final int TAG_WEBRTCURL = 8;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String apdid;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String apdidToken;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String appListCmdVer;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String createTime;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String drmSwitch;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String dynamicKey;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String timeInterval;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String webrtcUrl;

    public ResultData(ResultData resultData) {
        super(resultData);
        if (resultData != null) {
            this.apdid = resultData.apdid;
            this.apdidToken = resultData.apdidToken;
            this.createTime = resultData.createTime;
            this.dynamicKey = resultData.dynamicKey;
            this.timeInterval = resultData.timeInterval;
            this.appListCmdVer = resultData.appListCmdVer;
            this.drmSwitch = resultData.drmSwitch;
            this.webrtcUrl = resultData.webrtcUrl;
        }
    }

    public ResultData() {
    }

    public final ResultData fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.apdid = (String) obj;
                break;
            case 2:
                this.apdidToken = (String) obj;
                break;
            case 3:
                this.createTime = (String) obj;
                break;
            case 4:
                this.dynamicKey = (String) obj;
                break;
            case 5:
                this.timeInterval = (String) obj;
                break;
            case 6:
                this.appListCmdVer = (String) obj;
                break;
            case 7:
                this.drmSwitch = (String) obj;
                break;
            case 8:
                this.webrtcUrl = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ResultData)) {
            return false;
        }
        ResultData resultData = (ResultData) obj;
        return equals((Object) this.apdid, (Object) resultData.apdid) && equals((Object) this.apdidToken, (Object) resultData.apdidToken) && equals((Object) this.createTime, (Object) resultData.createTime) && equals((Object) this.dynamicKey, (Object) resultData.dynamicKey) && equals((Object) this.timeInterval, (Object) resultData.timeInterval) && equals((Object) this.appListCmdVer, (Object) resultData.appListCmdVer) && equals((Object) this.drmSwitch, (Object) resultData.drmSwitch) && equals((Object) this.webrtcUrl, (Object) resultData.webrtcUrl);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((this.apdid != null ? this.apdid.hashCode() : 0) * 37) + (this.apdidToken != null ? this.apdidToken.hashCode() : 0)) * 37) + (this.createTime != null ? this.createTime.hashCode() : 0)) * 37) + (this.dynamicKey != null ? this.dynamicKey.hashCode() : 0)) * 37) + (this.timeInterval != null ? this.timeInterval.hashCode() : 0)) * 37) + (this.appListCmdVer != null ? this.appListCmdVer.hashCode() : 0)) * 37) + (this.drmSwitch != null ? this.drmSwitch.hashCode() : 0)) * 37;
        if (this.webrtcUrl != null) {
            i2 = this.webrtcUrl.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
