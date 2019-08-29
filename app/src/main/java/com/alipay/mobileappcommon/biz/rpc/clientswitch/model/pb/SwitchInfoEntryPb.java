package com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class SwitchInfoEntryPb extends Message {
    public static final String DEFAULT_KEY = "";
    public static final String DEFAULT_VALUE = "";
    public static final int TAG_KEY = 1;
    public static final int TAG_VALUE = 2;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String key;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String value;

    public SwitchInfoEntryPb() {
    }

    public SwitchInfoEntryPb(SwitchInfoEntryPb switchInfoEntryPb) {
        super(switchInfoEntryPb);
        if (switchInfoEntryPb != null) {
            this.key = switchInfoEntryPb.key;
            this.value = switchInfoEntryPb.value;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SwitchInfoEntryPb)) {
            return false;
        }
        SwitchInfoEntryPb switchInfoEntryPb = (SwitchInfoEntryPb) obj;
        return equals((Object) this.key, (Object) switchInfoEntryPb.key) && equals((Object) this.value, (Object) switchInfoEntryPb.value);
    }

    public final SwitchInfoEntryPb fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.key = (String) obj;
                break;
            case 2:
                this.value = (String) obj;
                break;
        }
        return this;
    }

    public final int hashCode() {
        int i = 0;
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int hashCode = (this.key != null ? this.key.hashCode() : 0) * 37;
        if (this.value != null) {
            i = this.value.hashCode();
        }
        int i3 = hashCode + i;
        this.hashCode = i3;
        return i3;
    }
}
