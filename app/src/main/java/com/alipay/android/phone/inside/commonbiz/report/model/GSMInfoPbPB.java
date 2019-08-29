package com.alipay.android.phone.inside.commonbiz.report.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class GSMInfoPbPB extends Message {
    public static final Integer DEFAULT_CID = Integer.valueOf(0);
    public static final Integer DEFAULT_LAC = Integer.valueOf(0);
    public static final Integer DEFAULT_MCC = Integer.valueOf(0);
    public static final Integer DEFAULT_MNC = Integer.valueOf(0);
    public static final Integer DEFAULT_RSSI = Integer.valueOf(0);
    public static final int TAG_CID = 3;
    public static final int TAG_LAC = 4;
    public static final int TAG_MCC = 1;
    public static final int TAG_MNC = 2;
    public static final int TAG_RSSI = 5;
    @ProtoField(tag = 3, type = Datatype.INT32)
    public Integer cid;
    @ProtoField(tag = 4, type = Datatype.INT32)
    public Integer lac;
    @ProtoField(tag = 1, type = Datatype.INT32)
    public Integer mcc;
    @ProtoField(tag = 2, type = Datatype.INT32)
    public Integer mnc;
    @ProtoField(tag = 5, type = Datatype.INT32)
    public Integer rssi;

    public GSMInfoPbPB(GSMInfoPbPB gSMInfoPbPB) {
        super(gSMInfoPbPB);
        if (gSMInfoPbPB != null) {
            this.mcc = gSMInfoPbPB.mcc;
            this.mnc = gSMInfoPbPB.mnc;
            this.cid = gSMInfoPbPB.cid;
            this.lac = gSMInfoPbPB.lac;
            this.rssi = gSMInfoPbPB.rssi;
        }
    }

    public GSMInfoPbPB() {
    }

    public final GSMInfoPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.mcc = (Integer) obj;
                break;
            case 2:
                this.mnc = (Integer) obj;
                break;
            case 3:
                this.cid = (Integer) obj;
                break;
            case 4:
                this.lac = (Integer) obj;
                break;
            case 5:
                this.rssi = (Integer) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GSMInfoPbPB)) {
            return false;
        }
        GSMInfoPbPB gSMInfoPbPB = (GSMInfoPbPB) obj;
        return equals((Object) this.mcc, (Object) gSMInfoPbPB.mcc) && equals((Object) this.mnc, (Object) gSMInfoPbPB.mnc) && equals((Object) this.cid, (Object) gSMInfoPbPB.cid) && equals((Object) this.lac, (Object) gSMInfoPbPB.lac) && equals((Object) this.rssi, (Object) gSMInfoPbPB.rssi);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((this.mcc != null ? this.mcc.hashCode() : 0) * 37) + (this.mnc != null ? this.mnc.hashCode() : 0)) * 37) + (this.cid != null ? this.cid.hashCode() : 0)) * 37) + (this.lac != null ? this.lac.hashCode() : 0)) * 37;
        if (this.rssi != null) {
            i2 = this.rssi.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
