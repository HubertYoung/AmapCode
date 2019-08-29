package com.alipay.mobile.tinyappcustom.h5plugin.openauth.request;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class EntryStringString extends Message {
    public static final String DEFAULT_KEY = "";
    public static final String DEFAULT_VALUE = "";
    public static final int TAG_KEY = 1;
    public static final int TAG_VALUE = 2;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String key;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String value;

    public EntryStringString(EntryStringString message) {
        super(message);
        if (message != null) {
            this.key = message.key;
            this.value = message.value;
        }
    }

    public EntryStringString() {
    }

    public final EntryStringString fillTagValue(int tag, Object value2) {
        switch (tag) {
            case 1:
                this.key = (String) value2;
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
        if (!(other instanceof EntryStringString)) {
            return false;
        }
        EntryStringString o = (EntryStringString) other;
        if (!equals((Object) this.key, (Object) o.key) || !equals((Object) this.value, (Object) o.value)) {
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
        if (this.key != null) {
            i = this.key.hashCode();
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
