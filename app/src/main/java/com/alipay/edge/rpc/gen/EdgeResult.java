package com.alipay.edge.rpc.gen;

import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class EdgeResult extends Message {
    public static final String DEFAULT_ERRORCODE = "";
    public static final String DEFAULT_EXTDATA = "";
    public static final Integer DEFAULT_FLAG = Integer.valueOf(0);
    public static final ByteString DEFAULT_POLICYPACKDATA = ByteString.EMPTY;
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final int TAG_ERRORCODE = 2;
    public static final int TAG_EXTDATA = 5;
    public static final int TAG_FLAG = 4;
    public static final int TAG_POLICYPACKDATA = 3;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String errorCode;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String extData;
    @ProtoField(tag = 4, type = Datatype.INT32)
    public Integer flag;
    @ProtoField(tag = 3, type = Datatype.BYTES)
    public ByteString policyPackData;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public EdgeResult(EdgeResult edgeResult) {
        super(edgeResult);
        if (edgeResult != null) {
            this.success = edgeResult.success;
            this.errorCode = edgeResult.errorCode;
            this.policyPackData = edgeResult.policyPackData;
            this.flag = edgeResult.flag;
            this.extData = edgeResult.extData;
        }
    }

    public EdgeResult() {
    }

    public final EdgeResult fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.errorCode = (String) obj;
                break;
            case 3:
                this.policyPackData = (ByteString) obj;
                break;
            case 4:
                this.flag = (Integer) obj;
                break;
            case 5:
                this.extData = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EdgeResult)) {
            return false;
        }
        EdgeResult edgeResult = (EdgeResult) obj;
        return equals((Object) this.success, (Object) edgeResult.success) && equals((Object) this.errorCode, (Object) edgeResult.errorCode) && equals((Object) this.policyPackData, (Object) edgeResult.policyPackData) && equals((Object) this.flag, (Object) edgeResult.flag) && equals((Object) this.extData, (Object) edgeResult.extData);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((this.success != null ? this.success.hashCode() : 0) * 37) + (this.errorCode != null ? this.errorCode.hashCode() : 0)) * 37) + (this.policyPackData != null ? this.policyPackData.hashCode() : 0)) * 37) + (this.flag != null ? this.flag.hashCode() : 0)) * 37;
        if (this.extData != null) {
            i2 = this.extData.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
