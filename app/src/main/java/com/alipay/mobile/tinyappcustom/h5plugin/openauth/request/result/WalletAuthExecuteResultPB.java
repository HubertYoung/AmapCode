package com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.result;

import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.MapStringString;
import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class WalletAuthExecuteResultPB extends Message {
    public static final String DEFAULT_APPID = "";
    public static final String DEFAULT_AUTHCODE = "";
    public static final String DEFAULT_ERRORCODE = "";
    public static final String DEFAULT_ERRORMSG = "";
    public static final Boolean DEFAULT_ISSUCCESS = Boolean.valueOf(true);
    public static final String DEFAULT_ISVAPPID = "";
    public static final String DEFAULT_STATE = "";
    public static final List<String> DEFAULT_SUCCESSSCOPES = Collections.emptyList();
    public static final int TAG_APPID = 4;
    public static final int TAG_AUTHCODE = 5;
    public static final int TAG_ERRORCODE = 2;
    public static final int TAG_ERRORMSG = 3;
    public static final int TAG_ERRORSCOPES = 7;
    public static final int TAG_EXTINFO = 10;
    public static final int TAG_ISSUCCESS = 1;
    public static final int TAG_ISVAPPID = 9;
    public static final int TAG_STATE = 8;
    public static final int TAG_SUCCESSSCOPES = 6;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String appId;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String authCode;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String errorCode;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String errorMsg;
    @ProtoField(tag = 7)
    public MapStringString errorScopes;
    @ProtoField(tag = 10)
    public MapStringString extInfo;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean isSuccess;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String isvAppId;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String state;
    @ProtoField(label = Label.REPEATED, tag = 6, type = Datatype.STRING)
    public List<String> successScopes;

    public WalletAuthExecuteResultPB(WalletAuthExecuteResultPB message) {
        super(message);
        if (message != null) {
            this.isSuccess = message.isSuccess;
            this.errorCode = message.errorCode;
            this.errorMsg = message.errorMsg;
            this.appId = message.appId;
            this.authCode = message.authCode;
            this.successScopes = copyOf(message.successScopes);
            this.errorScopes = message.errorScopes;
            this.state = message.state;
            this.isvAppId = message.isvAppId;
            this.extInfo = message.extInfo;
        }
    }

    public WalletAuthExecuteResultPB() {
    }

    public final WalletAuthExecuteResultPB fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.isSuccess = (Boolean) value;
                break;
            case 2:
                this.errorCode = (String) value;
                break;
            case 3:
                this.errorMsg = (String) value;
                break;
            case 4:
                this.appId = (String) value;
                break;
            case 5:
                this.authCode = (String) value;
                break;
            case 6:
                this.successScopes = immutableCopyOf((List) value);
                break;
            case 7:
                this.errorScopes = (MapStringString) value;
                break;
            case 8:
                this.state = (String) value;
                break;
            case 9:
                this.isvAppId = (String) value;
                break;
            case 10:
                this.extInfo = (MapStringString) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WalletAuthExecuteResultPB)) {
            return false;
        }
        WalletAuthExecuteResultPB o = (WalletAuthExecuteResultPB) other;
        if (!equals((Object) this.isSuccess, (Object) o.isSuccess) || !equals((Object) this.errorCode, (Object) o.errorCode) || !equals((Object) this.errorMsg, (Object) o.errorMsg) || !equals((Object) this.appId, (Object) o.appId) || !equals((Object) this.authCode, (Object) o.authCode) || !equals(this.successScopes, o.successScopes) || !equals((Object) this.errorScopes, (Object) o.errorScopes) || !equals((Object) this.state, (Object) o.state) || !equals((Object) this.isvAppId, (Object) o.isvAppId) || !equals((Object) this.extInfo, (Object) o.extInfo)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.isSuccess != null ? this.isSuccess.hashCode() : 0) * 37;
        if (this.errorCode != null) {
            i = this.errorCode.hashCode();
        } else {
            i = 0;
        }
        int i9 = (i + hashCode) * 37;
        if (this.errorMsg != null) {
            i2 = this.errorMsg.hashCode();
        } else {
            i2 = 0;
        }
        int i10 = (i2 + i9) * 37;
        if (this.appId != null) {
            i3 = this.appId.hashCode();
        } else {
            i3 = 0;
        }
        int i11 = (i3 + i10) * 37;
        if (this.authCode != null) {
            i4 = this.authCode.hashCode();
        } else {
            i4 = 0;
        }
        int hashCode2 = ((this.successScopes != null ? this.successScopes.hashCode() : 1) + ((i4 + i11) * 37)) * 37;
        if (this.errorScopes != null) {
            i5 = this.errorScopes.hashCode();
        } else {
            i5 = 0;
        }
        int i12 = (i5 + hashCode2) * 37;
        if (this.state != null) {
            i6 = this.state.hashCode();
        } else {
            i6 = 0;
        }
        int i13 = (i6 + i12) * 37;
        if (this.isvAppId != null) {
            i7 = this.isvAppId.hashCode();
        } else {
            i7 = 0;
        }
        int i14 = (i7 + i13) * 37;
        if (this.extInfo != null) {
            i8 = this.extInfo.hashCode();
        }
        int result2 = i14 + i8;
        this.hashCode = result2;
        return result2;
    }
}
