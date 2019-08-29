package com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.result;

import com.alipay.mobile.tinyappcustom.h5plugin.openauth.model.AuthAgreementModelPB;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.MapStringString;
import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class WalletAuthContentResultPB extends Message {
    public static final List<AuthAgreementModelPB> DEFAULT_AGREEMENTS = Collections.emptyList();
    public static final String DEFAULT_APPLOGOLINK = "";
    public static final String DEFAULT_APPNAME = "";
    public static final List<String> DEFAULT_AUTHTEXT = Collections.emptyList();
    public static final String DEFAULT_ERRORCODE = "";
    public static final String DEFAULT_ERRORMSG = "";
    public static final Boolean DEFAULT_ISSUCCESS = Boolean.valueOf(true);
    public static final Boolean DEFAULT_ISVAGENT = Boolean.valueOf(false);
    public static final String DEFAULT_ISVAGENTDESC = "";
    public static final int TAG_AGREEMENTS = 7;
    public static final int TAG_APPLOGOLINK = 6;
    public static final int TAG_APPNAME = 5;
    public static final int TAG_AUTHTEXT = 4;
    public static final int TAG_ERRORCODE = 2;
    public static final int TAG_ERRORMSG = 3;
    public static final int TAG_EXTINFO = 10;
    public static final int TAG_ISSUCCESS = 1;
    public static final int TAG_ISVAGENT = 8;
    public static final int TAG_ISVAGENTDESC = 9;
    @ProtoField(label = Label.REPEATED, tag = 7)
    public List<AuthAgreementModelPB> agreements;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String appLogoLink;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String appName;
    @ProtoField(label = Label.REPEATED, tag = 4, type = Datatype.STRING)
    public List<String> authText;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String errorCode;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String errorMsg;
    @ProtoField(tag = 10)
    public MapStringString extInfo;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean isSuccess;
    @ProtoField(tag = 8, type = Datatype.BOOL)
    public Boolean isvAgent;
    @ProtoField(tag = 9, type = Datatype.STRING)
    public String isvAgentDesc;

    public WalletAuthContentResultPB(WalletAuthContentResultPB message) {
        super(message);
        if (message != null) {
            this.isSuccess = message.isSuccess;
            this.errorCode = message.errorCode;
            this.errorMsg = message.errorMsg;
            this.authText = copyOf(message.authText);
            this.appName = message.appName;
            this.appLogoLink = message.appLogoLink;
            this.agreements = copyOf(message.agreements);
            this.isvAgent = message.isvAgent;
            this.isvAgentDesc = message.isvAgentDesc;
            this.extInfo = message.extInfo;
        }
    }

    public WalletAuthContentResultPB() {
    }

    public final WalletAuthContentResultPB fillTagValue(int tag, Object value) {
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
                this.authText = immutableCopyOf((List) value);
                break;
            case 5:
                this.appName = (String) value;
                break;
            case 6:
                this.appLogoLink = (String) value;
                break;
            case 7:
                this.agreements = immutableCopyOf((List) value);
                break;
            case 8:
                this.isvAgent = (Boolean) value;
                break;
            case 9:
                this.isvAgentDesc = (String) value;
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
        if (!(other instanceof WalletAuthContentResultPB)) {
            return false;
        }
        WalletAuthContentResultPB o = (WalletAuthContentResultPB) other;
        if (!equals((Object) this.isSuccess, (Object) o.isSuccess) || !equals((Object) this.errorCode, (Object) o.errorCode) || !equals((Object) this.errorMsg, (Object) o.errorMsg) || !equals(this.authText, o.authText) || !equals((Object) this.appName, (Object) o.appName) || !equals((Object) this.appLogoLink, (Object) o.appLogoLink) || !equals(this.agreements, o.agreements) || !equals((Object) this.isvAgent, (Object) o.isvAgent) || !equals((Object) this.isvAgentDesc, (Object) o.isvAgentDesc) || !equals((Object) this.extInfo, (Object) o.extInfo)) {
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
        int i8 = 1;
        int i9 = 0;
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
        int i10 = (i + hashCode) * 37;
        if (this.errorMsg != null) {
            i2 = this.errorMsg.hashCode();
        } else {
            i2 = 0;
        }
        int i11 = (i2 + i10) * 37;
        if (this.authText != null) {
            i3 = this.authText.hashCode();
        } else {
            i3 = 1;
        }
        int i12 = (i3 + i11) * 37;
        if (this.appName != null) {
            i4 = this.appName.hashCode();
        } else {
            i4 = 0;
        }
        int i13 = (i4 + i12) * 37;
        if (this.appLogoLink != null) {
            i5 = this.appLogoLink.hashCode();
        } else {
            i5 = 0;
        }
        int i14 = (i5 + i13) * 37;
        if (this.agreements != null) {
            i8 = this.agreements.hashCode();
        }
        int i15 = (i14 + i8) * 37;
        if (this.isvAgent != null) {
            i6 = this.isvAgent.hashCode();
        } else {
            i6 = 0;
        }
        int i16 = (i6 + i15) * 37;
        if (this.isvAgentDesc != null) {
            i7 = this.isvAgentDesc.hashCode();
        } else {
            i7 = 0;
        }
        int i17 = (i7 + i16) * 37;
        if (this.extInfo != null) {
            i9 = this.extInfo.hashCode();
        }
        int result2 = i17 + i9;
        this.hashCode = result2;
        return result2;
    }
}
