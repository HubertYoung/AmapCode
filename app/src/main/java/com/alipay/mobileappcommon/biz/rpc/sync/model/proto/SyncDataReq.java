package com.alipay.mobileappcommon.biz.rpc.sync.model.proto;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;

public final class SyncDataReq extends Message {
    public static final String DEFAULT_UNIQID = "";
    public static final int TAG_UNIQID = 1;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.STRING)
    public String uniqId;

    public SyncDataReq(SyncDataReq message) {
        super(message);
        if (message != null) {
            this.uniqId = message.uniqId;
        }
    }

    public SyncDataReq() {
    }

    public final SyncDataReq fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.uniqId = (String) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SyncDataReq)) {
            return false;
        }
        return equals((Object) this.uniqId, (Object) ((SyncDataReq) other).uniqId);
    }

    public final int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.uniqId != null ? this.uniqId.hashCode() : 0;
        this.hashCode = result2;
        return result2;
    }
}
