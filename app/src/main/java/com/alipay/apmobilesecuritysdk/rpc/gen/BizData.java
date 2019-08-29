package com.alipay.apmobilesecuritysdk.rpc.gen;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class BizData extends Message {
    public static final String DEFAULT_APDID = "";
    public static final String DEFAULT_APDIDTOKEN = "";
    public static final String DEFAULT_DYNAMICKEY = "";
    public static final String DEFAULT_LASTTIME = "";
    public static final String DEFAULT_UMIDTOKEN = "";
    public static final int TAG_APDID = 1;
    public static final int TAG_APDIDTOKEN = 2;
    public static final int TAG_DYNAMICKEY = 4;
    public static final int TAG_LASTTIME = 3;
    public static final int TAG_UMIDTOKEN = 5;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String apdid;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String apdidToken;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String dynamicKey;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String lastTime;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String umidToken;

    public BizData(BizData bizData) {
        super(bizData);
        if (bizData != null) {
            this.apdid = bizData.apdid;
            this.apdidToken = bizData.apdidToken;
            this.lastTime = bizData.lastTime;
            this.dynamicKey = bizData.dynamicKey;
            this.umidToken = bizData.umidToken;
        }
    }

    public BizData() {
    }

    public final BizData fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.apdid = (String) obj;
                break;
            case 2:
                this.apdidToken = (String) obj;
                break;
            case 3:
                this.lastTime = (String) obj;
                break;
            case 4:
                this.dynamicKey = (String) obj;
                break;
            case 5:
                this.umidToken = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BizData)) {
            return false;
        }
        BizData bizData = (BizData) obj;
        return equals((Object) this.apdid, (Object) bizData.apdid) && equals((Object) this.apdidToken, (Object) bizData.apdidToken) && equals((Object) this.lastTime, (Object) bizData.lastTime) && equals((Object) this.dynamicKey, (Object) bizData.dynamicKey) && equals((Object) this.umidToken, (Object) bizData.umidToken);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((this.apdid != null ? this.apdid.hashCode() : 0) * 37) + (this.apdidToken != null ? this.apdidToken.hashCode() : 0)) * 37) + (this.lastTime != null ? this.lastTime.hashCode() : 0)) * 37) + (this.dynamicKey != null ? this.dynamicKey.hashCode() : 0)) * 37;
        if (this.umidToken != null) {
            i2 = this.umidToken.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
