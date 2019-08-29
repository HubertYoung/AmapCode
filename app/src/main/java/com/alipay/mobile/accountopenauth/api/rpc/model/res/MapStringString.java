package com.alipay.mobile.accountopenauth.api.rpc.model.res;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class MapStringString extends Message {
    public static final List<EntryStringString> DEFAULT_ENTRIES = Collections.emptyList();
    public static final int TAG_ENTRIES = 1;
    @ProtoField(label = Label.REPEATED, tag = 1)
    public List<EntryStringString> entries;

    public MapStringString(MapStringString mapStringString) {
        super(mapStringString);
        if (mapStringString != null) {
            this.entries = copyOf(mapStringString.entries);
        }
    }

    public MapStringString() {
    }

    public final MapStringString fillTagValue(int i, Object obj) {
        if (i == 1) {
            this.entries = immutableCopyOf((List) obj);
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MapStringString)) {
            return false;
        }
        return equals(this.entries, ((MapStringString) obj).entries);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int hashCode = this.entries != null ? this.entries.hashCode() : 1;
        this.hashCode = hashCode;
        return hashCode;
    }
}
