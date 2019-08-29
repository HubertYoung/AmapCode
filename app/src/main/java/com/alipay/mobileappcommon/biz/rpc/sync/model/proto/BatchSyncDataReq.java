package com.alipay.mobileappcommon.biz.rpc.sync.model.proto;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class BatchSyncDataReq extends Message {
    public static final List<String> DEFAULT_UNIQIDS = Collections.emptyList();
    public static final int TAG_UNIQIDS = 1;
    @ProtoField(label = Label.REPEATED, tag = 1, type = Datatype.STRING)
    public List<String> uniqIds;

    public BatchSyncDataReq(BatchSyncDataReq message) {
        super(message);
        if (message != null) {
            this.uniqIds = copyOf(message.uniqIds);
        }
    }

    public BatchSyncDataReq() {
    }

    public final BatchSyncDataReq fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.uniqIds = immutableCopyOf((List) value);
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BatchSyncDataReq)) {
            return false;
        }
        return equals(this.uniqIds, ((BatchSyncDataReq) other).uniqIds);
    }

    public final int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.uniqIds != null ? this.uniqIds.hashCode() : 1;
        this.hashCode = result2;
        return result2;
    }
}
