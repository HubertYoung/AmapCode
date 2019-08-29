package com.alipay.mobileapp.biz.rpc.mdap.vo.pb;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class UnifyCustomMdapReqPb extends Message {
    public static final String DEFAULT_ALIPAYPRODUCTID = "";
    public static final String DEFAULT_ALIPAYPRODUCTVERSION = "";
    public static final String DEFAULT_APP_CHANNEL = "";
    public static final String DEFAULT_BEHAVIORTYPE = "";
    public static final String DEFAULT_DEVICE_MODEL = "";
    public static final String DEFAULT_EXINFO1 = "";
    public static final String DEFAULT_EXINFO2 = "";
    public static final String DEFAULT_EXINFO3 = "";
    public static final String DEFAULT_EXINFO4 = "";
    public static final String DEFAULT_INNER_VERSION = "";
    public static final String DEFAULT_IP = "";
    public static final String DEFAULT_LANGUAGE = "";
    public static final String DEFAULT_LBSLOCATION = "";
    public static final String DEFAULT_LOGEDTIME = "";
    public static final String DEFAULT_LOGTIME = "";
    public static final String DEFAULT_NETWORK = "";
    public static final String DEFAULT_OS_VERSION = "";
    public static final String DEFAULT_RESOLUTION = "";
    public static final String DEFAULT_SEED = "";
    public static final String DEFAULT_TCID = "";
    public static final String DEFAULT_USERID = "";
    public static final String DEFAULT_UTDID = "";
    public static final String DEFAULT_VIEWID = "";
    public static final int TAG_ALIPAYPRODUCTID = 3;
    public static final int TAG_ALIPAYPRODUCTVERSION = 4;
    public static final int TAG_APP_CHANNEL = 20;
    public static final int TAG_BEHAVIORTYPE = 9;
    public static final int TAG_DEVICE_MODEL = 16;
    public static final int TAG_EXINFO1 = 10;
    public static final int TAG_EXINFO2 = 11;
    public static final int TAG_EXINFO3 = 12;
    public static final int TAG_EXINFO4 = 13;
    public static final int TAG_INNER_VERSION = 19;
    public static final int TAG_IP = 23;
    public static final int TAG_LANGUAGE = 21;
    public static final int TAG_LBSLOCATION = 15;
    public static final int TAG_LOGEDTIME = 1;
    public static final int TAG_LOGTIME = 2;
    public static final int TAG_NETWORK = 18;
    public static final int TAG_OS_VERSION = 17;
    public static final int TAG_RESOLUTION = 22;
    public static final int TAG_SEED = 8;
    public static final int TAG_TCID = 5;
    public static final int TAG_USERID = 6;
    public static final int TAG_UTDID = 14;
    public static final int TAG_VIEWID = 7;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String alipayproductid;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String alipayproductversion;
    @ProtoField(tag = 20, type = Datatype.STRING)
    public String app_channel;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String behaviortype;
    @ProtoField(tag = 16, type = Datatype.STRING)
    public String device_model;
    @ProtoField(tag = 10, type = Datatype.STRING)
    public String exinfo1;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String exinfo2;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String exinfo3;
    @ProtoField(tag = 13, type = Datatype.STRING)
    public String exinfo4;
    @ProtoField(tag = 19, type = Datatype.STRING)
    public String inner_version;
    @ProtoField(tag = 23, type = Datatype.STRING)
    public String ip;
    @ProtoField(tag = 21, type = Datatype.STRING)
    public String language;
    @ProtoField(tag = 15, type = Datatype.STRING)
    public String lbslocation;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String logedtime;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String logtime;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String network;
    @ProtoField(tag = 17, type = Datatype.STRING)
    public String os_version;
    @ProtoField(tag = 22, type = Datatype.STRING)
    public String resolution;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String seed;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String tcid;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String userid;
    @ProtoField(tag = 14, type = Datatype.STRING)
    public String utdid;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String viewid;

    public UnifyCustomMdapReqPb(UnifyCustomMdapReqPb unifyCustomMdapReqPb) {
        super(unifyCustomMdapReqPb);
        if (unifyCustomMdapReqPb != null) {
            this.logedtime = unifyCustomMdapReqPb.logedtime;
            this.logtime = unifyCustomMdapReqPb.logtime;
            this.alipayproductid = unifyCustomMdapReqPb.alipayproductid;
            this.alipayproductversion = unifyCustomMdapReqPb.alipayproductversion;
            this.tcid = unifyCustomMdapReqPb.tcid;
            this.userid = unifyCustomMdapReqPb.userid;
            this.viewid = unifyCustomMdapReqPb.viewid;
            this.seed = unifyCustomMdapReqPb.seed;
            this.behaviortype = unifyCustomMdapReqPb.behaviortype;
            this.exinfo1 = unifyCustomMdapReqPb.exinfo1;
            this.exinfo2 = unifyCustomMdapReqPb.exinfo2;
            this.exinfo3 = unifyCustomMdapReqPb.exinfo3;
            this.exinfo4 = unifyCustomMdapReqPb.exinfo4;
            this.utdid = unifyCustomMdapReqPb.utdid;
            this.lbslocation = unifyCustomMdapReqPb.lbslocation;
            this.device_model = unifyCustomMdapReqPb.device_model;
            this.os_version = unifyCustomMdapReqPb.os_version;
            this.network = unifyCustomMdapReqPb.network;
            this.inner_version = unifyCustomMdapReqPb.inner_version;
            this.app_channel = unifyCustomMdapReqPb.app_channel;
            this.language = unifyCustomMdapReqPb.language;
            this.resolution = unifyCustomMdapReqPb.resolution;
            this.ip = unifyCustomMdapReqPb.ip;
        }
    }

    public UnifyCustomMdapReqPb() {
    }

    public final UnifyCustomMdapReqPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.logedtime = (String) obj;
                break;
            case 2:
                this.logtime = (String) obj;
                break;
            case 3:
                this.alipayproductid = (String) obj;
                break;
            case 4:
                this.alipayproductversion = (String) obj;
                break;
            case 5:
                this.tcid = (String) obj;
                break;
            case 6:
                this.userid = (String) obj;
                break;
            case 7:
                this.viewid = (String) obj;
                break;
            case 8:
                this.seed = (String) obj;
                break;
            case 9:
                this.behaviortype = (String) obj;
                break;
            case 10:
                this.exinfo1 = (String) obj;
                break;
            case 11:
                this.exinfo2 = (String) obj;
                break;
            case 12:
                this.exinfo3 = (String) obj;
                break;
            case 13:
                this.exinfo4 = (String) obj;
                break;
            case 14:
                this.utdid = (String) obj;
                break;
            case 15:
                this.lbslocation = (String) obj;
                break;
            case 16:
                this.device_model = (String) obj;
                break;
            case 17:
                this.os_version = (String) obj;
                break;
            case 18:
                this.network = (String) obj;
                break;
            case 19:
                this.inner_version = (String) obj;
                break;
            case 20:
                this.app_channel = (String) obj;
                break;
            case 21:
                this.language = (String) obj;
                break;
            case 22:
                this.resolution = (String) obj;
                break;
            case 23:
                this.ip = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnifyCustomMdapReqPb)) {
            return false;
        }
        UnifyCustomMdapReqPb unifyCustomMdapReqPb = (UnifyCustomMdapReqPb) obj;
        return equals((Object) this.logedtime, (Object) unifyCustomMdapReqPb.logedtime) && equals((Object) this.logtime, (Object) unifyCustomMdapReqPb.logtime) && equals((Object) this.alipayproductid, (Object) unifyCustomMdapReqPb.alipayproductid) && equals((Object) this.alipayproductversion, (Object) unifyCustomMdapReqPb.alipayproductversion) && equals((Object) this.tcid, (Object) unifyCustomMdapReqPb.tcid) && equals((Object) this.userid, (Object) unifyCustomMdapReqPb.userid) && equals((Object) this.viewid, (Object) unifyCustomMdapReqPb.viewid) && equals((Object) this.seed, (Object) unifyCustomMdapReqPb.seed) && equals((Object) this.behaviortype, (Object) unifyCustomMdapReqPb.behaviortype) && equals((Object) this.exinfo1, (Object) unifyCustomMdapReqPb.exinfo1) && equals((Object) this.exinfo2, (Object) unifyCustomMdapReqPb.exinfo2) && equals((Object) this.exinfo3, (Object) unifyCustomMdapReqPb.exinfo3) && equals((Object) this.exinfo4, (Object) unifyCustomMdapReqPb.exinfo4) && equals((Object) this.utdid, (Object) unifyCustomMdapReqPb.utdid) && equals((Object) this.lbslocation, (Object) unifyCustomMdapReqPb.lbslocation) && equals((Object) this.device_model, (Object) unifyCustomMdapReqPb.device_model) && equals((Object) this.os_version, (Object) unifyCustomMdapReqPb.os_version) && equals((Object) this.network, (Object) unifyCustomMdapReqPb.network) && equals((Object) this.inner_version, (Object) unifyCustomMdapReqPb.inner_version) && equals((Object) this.app_channel, (Object) unifyCustomMdapReqPb.app_channel) && equals((Object) this.language, (Object) unifyCustomMdapReqPb.language) && equals((Object) this.resolution, (Object) unifyCustomMdapReqPb.resolution) && equals((Object) this.ip, (Object) unifyCustomMdapReqPb.ip);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((this.logedtime != null ? this.logedtime.hashCode() : 0) * 37) + (this.logtime != null ? this.logtime.hashCode() : 0)) * 37) + (this.alipayproductid != null ? this.alipayproductid.hashCode() : 0)) * 37) + (this.alipayproductversion != null ? this.alipayproductversion.hashCode() : 0)) * 37) + (this.tcid != null ? this.tcid.hashCode() : 0)) * 37) + (this.userid != null ? this.userid.hashCode() : 0)) * 37) + (this.viewid != null ? this.viewid.hashCode() : 0)) * 37) + (this.seed != null ? this.seed.hashCode() : 0)) * 37) + (this.behaviortype != null ? this.behaviortype.hashCode() : 0)) * 37) + (this.exinfo1 != null ? this.exinfo1.hashCode() : 0)) * 37) + (this.exinfo2 != null ? this.exinfo2.hashCode() : 0)) * 37) + (this.exinfo3 != null ? this.exinfo3.hashCode() : 0)) * 37) + (this.exinfo4 != null ? this.exinfo4.hashCode() : 0)) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.lbslocation != null ? this.lbslocation.hashCode() : 0)) * 37) + (this.device_model != null ? this.device_model.hashCode() : 0)) * 37) + (this.os_version != null ? this.os_version.hashCode() : 0)) * 37) + (this.network != null ? this.network.hashCode() : 0)) * 37) + (this.inner_version != null ? this.inner_version.hashCode() : 0)) * 37) + (this.app_channel != null ? this.app_channel.hashCode() : 0)) * 37) + (this.language != null ? this.language.hashCode() : 0)) * 37) + (this.resolution != null ? this.resolution.hashCode() : 0)) * 37;
        if (this.ip != null) {
            i2 = this.ip.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
