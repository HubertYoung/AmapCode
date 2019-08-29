package com.alipay.mobile.tinyappcustom.h5plugin.navigation;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class MiniAppRelationCheckResultPB extends Message {
    @ProtoField(tag = 4, type = Datatype.BOOL)
    public Boolean jumpable;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String resultCode;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String resultMsg;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public MiniAppRelationCheckResultPB(MiniAppRelationCheckResultPB message) {
        super(message);
        if (message != null) {
            this.success = message.success;
            this.resultCode = message.resultCode;
            this.resultMsg = message.resultMsg;
            this.jumpable = message.jumpable;
        }
    }

    public MiniAppRelationCheckResultPB() {
    }

    public final MiniAppRelationCheckResultPB fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.success = (Boolean) value;
                break;
            case 2:
                this.resultCode = (String) value;
                break;
            case 3:
                this.resultMsg = (String) value;
                break;
            case 4:
                this.jumpable = (Boolean) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MiniAppRelationCheckResultPB)) {
            return false;
        }
        MiniAppRelationCheckResultPB o = (MiniAppRelationCheckResultPB) other;
        if (!equals((Object) this.success, (Object) o.success) || !equals((Object) this.resultCode, (Object) o.resultCode) || !equals((Object) this.resultMsg, (Object) o.resultMsg) || !equals((Object) this.jumpable, (Object) o.jumpable)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.success != null ? this.success.hashCode() : 0) * 37;
        if (this.resultCode != null) {
            i = this.resultCode.hashCode();
        } else {
            i = 0;
        }
        int i4 = (i + hashCode) * 37;
        if (this.resultMsg != null) {
            i2 = this.resultMsg.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i2 + i4) * 37;
        if (this.jumpable != null) {
            i3 = this.jumpable.hashCode();
        }
        int result2 = i5 + i3;
        this.hashCode = result2;
        return result2;
    }
}
