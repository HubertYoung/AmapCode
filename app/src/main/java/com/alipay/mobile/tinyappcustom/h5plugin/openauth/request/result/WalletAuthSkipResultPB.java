package com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.result;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class WalletAuthSkipResultPB extends Message {
    public static final Boolean DEFAULT_CANSKIPAUTH = Boolean.valueOf(false);
    public static final String DEFAULT_ERRORCODE = "";
    public static final String DEFAULT_ERRORMSG = "";
    public static final Boolean DEFAULT_ISSUCCESS = Boolean.valueOf(true);
    public static final int TAG_AUTHCONTENTRESULT = 6;
    public static final int TAG_AUTHEXECUTERESULT = 5;
    public static final int TAG_CANSKIPAUTH = 4;
    public static final int TAG_ERRORCODE = 2;
    public static final int TAG_ERRORMSG = 3;
    public static final int TAG_ISSUCCESS = 1;
    @ProtoField(tag = 6)
    public WalletAuthContentResultPB authContentResult;
    @ProtoField(tag = 5)
    public WalletAuthExecuteResultPB authExecuteResult;
    @ProtoField(tag = 4, type = Datatype.BOOL)
    public Boolean canSkipAuth;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String errorCode;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String errorMsg;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean isSuccess;

    public WalletAuthSkipResultPB(WalletAuthSkipResultPB message) {
        super(message);
        if (message != null) {
            this.isSuccess = message.isSuccess;
            this.errorCode = message.errorCode;
            this.errorMsg = message.errorMsg;
            this.canSkipAuth = message.canSkipAuth;
            this.authExecuteResult = message.authExecuteResult;
            this.authContentResult = message.authContentResult;
        }
    }

    public WalletAuthSkipResultPB() {
    }

    public final WalletAuthSkipResultPB fillTagValue(int tag, Object value) {
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
                this.canSkipAuth = (Boolean) value;
                break;
            case 5:
                this.authExecuteResult = (WalletAuthExecuteResultPB) value;
                break;
            case 6:
                this.authContentResult = (WalletAuthContentResultPB) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WalletAuthSkipResultPB)) {
            return false;
        }
        WalletAuthSkipResultPB o = (WalletAuthSkipResultPB) other;
        if (!equals((Object) this.isSuccess, (Object) o.isSuccess) || !equals((Object) this.errorCode, (Object) o.errorCode) || !equals((Object) this.errorMsg, (Object) o.errorMsg) || !equals((Object) this.canSkipAuth, (Object) o.canSkipAuth) || !equals((Object) this.authExecuteResult, (Object) o.authExecuteResult) || !equals((Object) this.authContentResult, (Object) o.authContentResult)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
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
        int i6 = (i + hashCode) * 37;
        if (this.errorMsg != null) {
            i2 = this.errorMsg.hashCode();
        } else {
            i2 = 0;
        }
        int i7 = (i2 + i6) * 37;
        if (this.canSkipAuth != null) {
            i3 = this.canSkipAuth.hashCode();
        } else {
            i3 = 0;
        }
        int i8 = (i3 + i7) * 37;
        if (this.authExecuteResult != null) {
            i4 = this.authExecuteResult.hashCode();
        } else {
            i4 = 0;
        }
        int i9 = (i4 + i8) * 37;
        if (this.authContentResult != null) {
            i5 = this.authContentResult.hashCode();
        }
        int result2 = i9 + i5;
        this.hashCode = result2;
        return result2;
    }
}
