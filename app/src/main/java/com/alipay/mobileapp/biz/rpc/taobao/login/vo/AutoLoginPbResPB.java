package com.alipay.mobileapp.biz.rpc.taobao.login.vo;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class AutoLoginPbResPB extends Message {
    public static final String DEFAULT_COOKIE = "";
    public static final List<String> DEFAULT_DOMAINS = Collections.emptyList();
    public static final String DEFAULT_ECODE = "";
    public static final String DEFAULT_NOTICEURL = "";
    public static final String DEFAULT_REDIRECTURL = "";
    public static final String DEFAULT_RESULTSTATUS = "";
    public static final String DEFAULT_SID = "";
    public static final String DEFAULT_STATUSACTION = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final String DEFAULT_TBNICK = "";
    public static final String DEFAULT_TBUSERID = "";
    public static final List<String> DEFAULT_WHITELIST = Collections.emptyList();
    public static final int TAG_BINDTAOBAOPBRES = 10;
    public static final int TAG_COOKIE = 7;
    public static final int TAG_DOMAINS = 8;
    public static final int TAG_ECODE = 4;
    public static final int TAG_EXTRES = 14;
    public static final int TAG_NOTICEURL = 9;
    public static final int TAG_REDIRECTURL = 11;
    public static final int TAG_RESULTSTATUS = 2;
    public static final int TAG_SID = 3;
    public static final int TAG_STATUSACTION = 12;
    public static final int TAG_SUCCESS = 1;
    public static final int TAG_TBNICK = 6;
    public static final int TAG_TBUSERID = 5;
    public static final int TAG_WHITELIST = 13;
    @ProtoField(tag = 10)
    public BindTaobaoPbResPB bindTaobaoPbRes;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String cookie;
    @ProtoField(label = Label.REPEATED, tag = 8, type = Datatype.STRING)
    public List<String> domains;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String ecode;
    @ProtoField(tag = 14)
    public MapStringString extRes;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String noticeUrl;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String redirectUrl;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String resultStatus;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String sid;
    @ProtoField(tag = 12, type = Datatype.STRING)
    public String statusAction;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String tbNick;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String tbUserId;
    @ProtoField(label = Label.REPEATED, tag = 13, type = Datatype.STRING)
    public List<String> whiteList;

    public AutoLoginPbResPB(AutoLoginPbResPB autoLoginPbResPB) {
        super(autoLoginPbResPB);
        if (autoLoginPbResPB != null) {
            this.success = autoLoginPbResPB.success;
            this.resultStatus = autoLoginPbResPB.resultStatus;
            this.sid = autoLoginPbResPB.sid;
            this.ecode = autoLoginPbResPB.ecode;
            this.tbUserId = autoLoginPbResPB.tbUserId;
            this.tbNick = autoLoginPbResPB.tbNick;
            this.cookie = autoLoginPbResPB.cookie;
            this.domains = copyOf(autoLoginPbResPB.domains);
            this.noticeUrl = autoLoginPbResPB.noticeUrl;
            this.bindTaobaoPbRes = autoLoginPbResPB.bindTaobaoPbRes;
            this.redirectUrl = autoLoginPbResPB.redirectUrl;
            this.statusAction = autoLoginPbResPB.statusAction;
            this.whiteList = copyOf(autoLoginPbResPB.whiteList);
            this.extRes = autoLoginPbResPB.extRes;
        }
    }

    public AutoLoginPbResPB() {
    }

    public final AutoLoginPbResPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.resultStatus = (String) obj;
                break;
            case 3:
                this.sid = (String) obj;
                break;
            case 4:
                this.ecode = (String) obj;
                break;
            case 5:
                this.tbUserId = (String) obj;
                break;
            case 6:
                this.tbNick = (String) obj;
                break;
            case 7:
                this.cookie = (String) obj;
                break;
            case 8:
                this.domains = immutableCopyOf((List) obj);
                break;
            case 9:
                this.noticeUrl = (String) obj;
                break;
            case 10:
                this.bindTaobaoPbRes = (BindTaobaoPbResPB) obj;
                break;
            case 11:
                this.redirectUrl = (String) obj;
                break;
            case 12:
                this.statusAction = (String) obj;
                break;
            case 13:
                this.whiteList = immutableCopyOf((List) obj);
                break;
            case 14:
                this.extRes = (MapStringString) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AutoLoginPbResPB)) {
            return false;
        }
        AutoLoginPbResPB autoLoginPbResPB = (AutoLoginPbResPB) obj;
        return equals((Object) this.success, (Object) autoLoginPbResPB.success) && equals((Object) this.resultStatus, (Object) autoLoginPbResPB.resultStatus) && equals((Object) this.sid, (Object) autoLoginPbResPB.sid) && equals((Object) this.ecode, (Object) autoLoginPbResPB.ecode) && equals((Object) this.tbUserId, (Object) autoLoginPbResPB.tbUserId) && equals((Object) this.tbNick, (Object) autoLoginPbResPB.tbNick) && equals((Object) this.cookie, (Object) autoLoginPbResPB.cookie) && equals(this.domains, autoLoginPbResPB.domains) && equals((Object) this.noticeUrl, (Object) autoLoginPbResPB.noticeUrl) && equals((Object) this.bindTaobaoPbRes, (Object) autoLoginPbResPB.bindTaobaoPbRes) && equals((Object) this.redirectUrl, (Object) autoLoginPbResPB.redirectUrl) && equals((Object) this.statusAction, (Object) autoLoginPbResPB.statusAction) && equals(this.whiteList, autoLoginPbResPB.whiteList) && equals((Object) this.extRes, (Object) autoLoginPbResPB.extRes);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int i3 = 1;
        int hashCode = (((((((((((((((((((((((this.success != null ? this.success.hashCode() : 0) * 37) + (this.resultStatus != null ? this.resultStatus.hashCode() : 0)) * 37) + (this.sid != null ? this.sid.hashCode() : 0)) * 37) + (this.ecode != null ? this.ecode.hashCode() : 0)) * 37) + (this.tbUserId != null ? this.tbUserId.hashCode() : 0)) * 37) + (this.tbNick != null ? this.tbNick.hashCode() : 0)) * 37) + (this.cookie != null ? this.cookie.hashCode() : 0)) * 37) + (this.domains != null ? this.domains.hashCode() : 1)) * 37) + (this.noticeUrl != null ? this.noticeUrl.hashCode() : 0)) * 37) + (this.bindTaobaoPbRes != null ? this.bindTaobaoPbRes.hashCode() : 0)) * 37) + (this.redirectUrl != null ? this.redirectUrl.hashCode() : 0)) * 37) + (this.statusAction != null ? this.statusAction.hashCode() : 0)) * 37;
        if (this.whiteList != null) {
            i3 = this.whiteList.hashCode();
        }
        int i4 = (hashCode + i3) * 37;
        if (this.extRes != null) {
            i2 = this.extRes.hashCode();
        }
        int i5 = i4 + i2;
        this.hashCode = i5;
        return i5;
    }
}
