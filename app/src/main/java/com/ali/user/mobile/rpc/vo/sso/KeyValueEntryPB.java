package com.ali.user.mobile.rpc.vo.sso;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class KeyValueEntryPB extends Message {
    public static final String DEFAULT_KEY = "";
    public static final String DEFAULT_VALUE = "";
    public static final int TAG_KEY = 1;
    public static final int TAG_VALUE = 2;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String key;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String value;

    public KeyValueEntryPB(KeyValueEntryPB keyValueEntryPB) {
        super(keyValueEntryPB);
        if (keyValueEntryPB != null) {
            this.key = keyValueEntryPB.key;
            this.value = keyValueEntryPB.value;
        }
    }

    public KeyValueEntryPB() {
    }

    public final KeyValueEntryPB fillTagValue(int i, Object obj) {
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

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyValueEntryPB)) {
            return false;
        }
        KeyValueEntryPB keyValueEntryPB = (KeyValueEntryPB) obj;
        return equals((Object) this.key, (Object) keyValueEntryPB.key) && equals((Object) this.value, (Object) keyValueEntryPB.value);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (this.key != null ? this.key.hashCode() : 0) * 37;
        if (this.value != null) {
            i2 = this.value.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
