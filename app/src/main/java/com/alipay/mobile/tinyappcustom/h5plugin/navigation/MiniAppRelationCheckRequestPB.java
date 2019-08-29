package com.alipay.mobile.tinyappcustom.h5plugin.navigation;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class MiniAppRelationCheckRequestPB extends Message {
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String appId;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String targetId;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String userId;

    public MiniAppRelationCheckRequestPB(MiniAppRelationCheckRequestPB message) {
        super(message);
        if (message != null) {
            this.appId = message.appId;
            this.userId = message.userId;
            this.targetId = message.targetId;
        }
    }

    public MiniAppRelationCheckRequestPB() {
    }

    public final MiniAppRelationCheckRequestPB fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.appId = (String) value;
                break;
            case 2:
                this.userId = (String) value;
                break;
            case 3:
                this.targetId = (String) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MiniAppRelationCheckRequestPB)) {
            return false;
        }
        MiniAppRelationCheckRequestPB o = (MiniAppRelationCheckRequestPB) other;
        if (!equals((Object) this.appId, (Object) o.appId) || !equals((Object) this.userId, (Object) o.userId) || !equals((Object) this.targetId, (Object) o.targetId)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.appId != null ? this.appId.hashCode() : 0) * 37;
        if (this.userId != null) {
            i = this.userId.hashCode();
        } else {
            i = 0;
        }
        int i3 = (i + hashCode) * 37;
        if (this.targetId != null) {
            i2 = this.targetId.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }
}
