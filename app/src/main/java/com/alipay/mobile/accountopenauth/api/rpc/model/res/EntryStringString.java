package com.alipay.mobile.accountopenauth.api.rpc.model.res;

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

    public EntryStringString(EntryStringString entryStringString) {
        super(entryStringString);
        if (entryStringString != null) {
            this.key = entryStringString.key;
            this.value = entryStringString.value;
        }
    }

    public EntryStringString() {
    }

    public final EntryStringString fillTagValue(int i, Object obj) {
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
        if (!(obj instanceof EntryStringString)) {
            return false;
        }
        EntryStringString entryStringString = (EntryStringString) obj;
        return equals((Object) this.key, (Object) entryStringString.key) && equals((Object) this.value, (Object) entryStringString.value);
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
