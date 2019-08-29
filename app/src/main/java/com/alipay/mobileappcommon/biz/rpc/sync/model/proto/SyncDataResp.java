package com.alipay.mobileappcommon.biz.rpc.sync.model.proto;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;

public final class SyncDataResp extends Message {
    public static final String DEFAULT_DATA = "";
    public static final String DEFAULT_REASON = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.valueOf(false);
    public static final String DEFAULT_TYPE = "";
    public static final String DEFAULT_UNIQID = "";
    public static final String DEFAULT_UPDATETIME = "";
    public static final int TAG_DATA = 5;
    public static final int TAG_REASON = 2;
    public static final int TAG_SUCCESS = 1;
    public static final int TAG_TYPE = 4;
    public static final int TAG_UNIQID = 3;
    public static final int TAG_UPDATETIME = 6;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String data;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String reason;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.BOOL)
    public Boolean success;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String type;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String uniqId;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String updateTime;

    public SyncDataResp(SyncDataResp message) {
        super(message);
        if (message != null) {
            this.success = message.success;
            this.reason = message.reason;
            this.uniqId = message.uniqId;
            this.type = message.type;
            this.data = message.data;
            this.updateTime = message.updateTime;
        }
    }

    public SyncDataResp() {
    }

    public final SyncDataResp fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.success = (Boolean) value;
                break;
            case 2:
                this.reason = (String) value;
                break;
            case 3:
                this.uniqId = (String) value;
                break;
            case 4:
                this.type = (String) value;
                break;
            case 5:
                this.data = (String) value;
                break;
            case 6:
                this.updateTime = (String) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SyncDataResp)) {
            return false;
        }
        SyncDataResp o = (SyncDataResp) other;
        if (!equals((Object) this.success, (Object) o.success) || !equals((Object) this.reason, (Object) o.reason) || !equals((Object) this.uniqId, (Object) o.uniqId) || !equals((Object) this.type, (Object) o.type) || !equals((Object) this.data, (Object) o.data) || !equals((Object) this.updateTime, (Object) o.updateTime)) {
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
        int hashCode = (this.success != null ? this.success.hashCode() : 0) * 37;
        if (this.reason != null) {
            i = this.reason.hashCode();
        } else {
            i = 0;
        }
        int i6 = (i + hashCode) * 37;
        if (this.uniqId != null) {
            i2 = this.uniqId.hashCode();
        } else {
            i2 = 0;
        }
        int i7 = (i2 + i6) * 37;
        if (this.type != null) {
            i3 = this.type.hashCode();
        } else {
            i3 = 0;
        }
        int i8 = (i3 + i7) * 37;
        if (this.data != null) {
            i4 = this.data.hashCode();
        } else {
            i4 = 0;
        }
        int i9 = (i4 + i8) * 37;
        if (this.updateTime != null) {
            i5 = this.updateTime.hashCode();
        }
        int result2 = i9 + i5;
        this.hashCode = result2;
        return result2;
    }
}
