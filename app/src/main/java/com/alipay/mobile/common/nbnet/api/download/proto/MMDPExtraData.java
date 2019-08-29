package com.alipay.mobile.common.nbnet.api.download.proto;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;

public final class MMDPExtraData extends Message {
    public static final String DEFAULT_NAME = "";
    public static final String DEFAULT_VALUE = "";
    public static final int TAG_NAME = 1;
    public static final int TAG_VALUE = 2;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.STRING)
    public String name;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String value;

    public MMDPExtraData(MMDPExtraData message) {
        super(message);
        if (message != null) {
            this.name = message.name;
            this.value = message.value;
        }
    }

    public MMDPExtraData() {
    }

    public final MMDPExtraData fillTagValue(int tag, Object value2) {
        switch (tag) {
            case 1:
                this.name = (String) value2;
                break;
            case 2:
                this.value = (String) value2;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MMDPExtraData)) {
            return false;
        }
        MMDPExtraData o = (MMDPExtraData) other;
        if (!equals((Object) this.name, (Object) o.name) || !equals((Object) this.value, (Object) o.value)) {
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
        if (this.name != null) {
            i = this.name.hashCode();
        } else {
            i = 0;
        }
        int i3 = i * 37;
        if (this.value != null) {
            i2 = this.value.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }
}
