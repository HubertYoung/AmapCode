package com.alipay.android.phone.inside.offlinecode.rpc.request;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class CloseDeviceReqPB extends Message {
    public static final String DEFAULT_SCOPE = "";
    public static final String DEFAULT_TID = "";
    public static final int TAG_SCOPE = 1;
    public static final int TAG_TID = 2;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String scope;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String tid;

    public CloseDeviceReqPB(CloseDeviceReqPB closeDeviceReqPB) {
        super(closeDeviceReqPB);
        if (closeDeviceReqPB != null) {
            this.scope = closeDeviceReqPB.scope;
            this.tid = closeDeviceReqPB.tid;
        }
    }

    public CloseDeviceReqPB() {
    }

    public final CloseDeviceReqPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.scope = (String) obj;
                break;
            case 2:
                this.tid = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CloseDeviceReqPB)) {
            return false;
        }
        CloseDeviceReqPB closeDeviceReqPB = (CloseDeviceReqPB) obj;
        return equals((Object) this.scope, (Object) closeDeviceReqPB.scope) && equals((Object) this.tid, (Object) closeDeviceReqPB.tid);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (this.scope != null ? this.scope.hashCode() : 0) * 37;
        if (this.tid != null) {
            i2 = this.tid.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
