package com.alipay.mobileappcommon.biz.rpc.pginfo.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class PgDataPB extends Message {
    public static final String DEFAULT_KEY = "";
    public static final String DEFAULT_VALUE = "";
    public static final int TAG_KEY = 1;
    public static final int TAG_VALUE = 2;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String key;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String value;

    public PgDataPB() {
    }

    public PgDataPB(PgDataPB pgDataPB) {
        super(pgDataPB);
        if (pgDataPB != null) {
            this.key = pgDataPB.key;
            this.value = pgDataPB.value;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PgDataPB)) {
            return false;
        }
        PgDataPB pgDataPB = (PgDataPB) obj;
        return equals((Object) this.key, (Object) pgDataPB.key) && equals((Object) this.value, (Object) pgDataPB.value);
    }

    public final PgDataPB fillTagValue(int i, Object obj) {
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
