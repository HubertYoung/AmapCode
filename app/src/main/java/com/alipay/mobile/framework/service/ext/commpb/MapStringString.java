package com.alipay.mobile.framework.service.ext.commpb;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class MapStringString extends Message {
    public static final List<EntryStringString> DEFAULT_ENTRIES = Collections.emptyList();
    public static final int TAG_ENTRIES = 1;
    @ProtoField(label = Label.REPEATED, tag = 1)
    public List<EntryStringString> entries;

    public MapStringString(MapStringString message) {
        super(message);
        if (message != null) {
            this.entries = copyOf(message.entries);
        }
    }

    public MapStringString() {
    }

    public MapStringString(Map<String, String> map) {
        if (map != null) {
            this.entries = new ArrayList();
            for (Entry entry : map.entrySet()) {
                EntryStringString entryStringString = new EntryStringString();
                entryStringString.key = (String) entry.getKey();
                entryStringString.value = (String) entry.getValue();
                this.entries.add(entryStringString);
            }
        }
    }

    public final MapStringString fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.entries = immutableCopyOf((List) value);
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MapStringString)) {
            return false;
        }
        return equals(this.entries, ((MapStringString) other).entries);
    }

    public final int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.entries != null ? this.entries.hashCode() : 1;
        this.hashCode = result2;
        return result2;
    }

    public final Map<String, String> toMap() {
        Map map = new HashMap();
        if (this.entries != null) {
            for (EntryStringString entry : this.entries) {
                map.put(entry.key, entry.value);
            }
        }
        return map;
    }
}
