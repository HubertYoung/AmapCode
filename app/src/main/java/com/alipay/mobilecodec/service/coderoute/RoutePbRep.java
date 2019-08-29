package com.alipay.mobilecodec.service.coderoute;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class RoutePbRep extends Message {
    public static final String DEFAULT_MEMO = "";
    public static final Integer DEFAULT_RESULTCODE = Integer.valueOf(101);
    public static final String DEFAULT_ROUTEINFOS = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.valueOf(false);
    public static final int TAG_MEMO = 3;
    public static final int TAG_RESULTCODE = 2;
    public static final int TAG_ROUTEINFOS = 4;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String memo;
    @ProtoField(tag = 2, type = Datatype.INT32)
    public Integer resultCode;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String routeInfos;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public RoutePbRep(RoutePbRep message) {
        super(message);
        if (message != null) {
            this.success = message.success;
            this.resultCode = message.resultCode;
            this.memo = message.memo;
            this.routeInfos = message.routeInfos;
        }
    }

    public RoutePbRep() {
    }

    public RoutePbRep fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.success = (Boolean) value;
                break;
            case 2:
                this.resultCode = (Integer) value;
                break;
            case 3:
                this.memo = (String) value;
                break;
            case 4:
                this.routeInfos = (String) value;
                break;
        }
        return this;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RoutePbRep)) {
            return false;
        }
        RoutePbRep o = (RoutePbRep) other;
        if (!equals((Object) this.success, (Object) o.success) || !equals((Object) this.resultCode, (Object) o.resultCode) || !equals((Object) this.memo, (Object) o.memo) || !equals((Object) this.routeInfos, (Object) o.routeInfos)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
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
        int i4 = (hashCode + i) * 37;
        if (this.memo != null) {
            i2 = this.memo.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i4 + i2) * 37;
        if (this.routeInfos != null) {
            i3 = this.routeInfos.hashCode();
        }
        int result2 = i5 + i3;
        this.hashCode = result2;
        return result2;
    }
}
