package com.alipay.mobileappcommon.biz.rpc.pginfo.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class PgTemplateInfoDataPB extends Message {
    public static final String DEFAULT_KEY = "";
    public static final int TAG_KEY = 1;
    public static final int TAG_VALUE = 2;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String key;
    @ProtoField(tag = 2)
    public PgTemplateInfoPB value;

    public PgTemplateInfoDataPB() {
    }

    public PgTemplateInfoDataPB(PgTemplateInfoDataPB pgTemplateInfoDataPB) {
        super(pgTemplateInfoDataPB);
        if (pgTemplateInfoDataPB != null) {
            this.key = pgTemplateInfoDataPB.key;
            this.value = pgTemplateInfoDataPB.value;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PgTemplateInfoDataPB)) {
            return false;
        }
        PgTemplateInfoDataPB pgTemplateInfoDataPB = (PgTemplateInfoDataPB) obj;
        return equals((Object) this.key, (Object) pgTemplateInfoDataPB.key) && equals((Object) this.value, (Object) pgTemplateInfoDataPB.value);
    }

    public final PgTemplateInfoDataPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.key = (String) obj;
                break;
            case 2:
                this.value = (PgTemplateInfoPB) obj;
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
