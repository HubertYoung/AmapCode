package com.alipay.android.phone.inside.commonbiz.report.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class CDMAInfoPbPB extends Message {
    public static final Integer DEFAULT_BSID = Integer.valueOf(0);
    public static final Integer DEFAULT_NID = Integer.valueOf(0);
    public static final Integer DEFAULT_RSSI = Integer.valueOf(0);
    public static final Integer DEFAULT_SID = Integer.valueOf(0);
    public static final int TAG_BSID = 1;
    public static final int TAG_NID = 2;
    public static final int TAG_RSSI = 4;
    public static final int TAG_SID = 3;
    @ProtoField(tag = 1, type = Datatype.INT32)
    public Integer bsid;
    @ProtoField(tag = 2, type = Datatype.INT32)
    public Integer nid;
    @ProtoField(tag = 4, type = Datatype.INT32)
    public Integer rssi;
    @ProtoField(tag = 3, type = Datatype.INT32)
    public Integer sid;

    public CDMAInfoPbPB(CDMAInfoPbPB cDMAInfoPbPB) {
        super(cDMAInfoPbPB);
        if (cDMAInfoPbPB != null) {
            this.bsid = cDMAInfoPbPB.bsid;
            this.nid = cDMAInfoPbPB.nid;
            this.sid = cDMAInfoPbPB.sid;
            this.rssi = cDMAInfoPbPB.rssi;
        }
    }

    public CDMAInfoPbPB() {
    }

    public final CDMAInfoPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.bsid = (Integer) obj;
                break;
            case 2:
                this.nid = (Integer) obj;
                break;
            case 3:
                this.sid = (Integer) obj;
                break;
            case 4:
                this.rssi = (Integer) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CDMAInfoPbPB)) {
            return false;
        }
        CDMAInfoPbPB cDMAInfoPbPB = (CDMAInfoPbPB) obj;
        return equals((Object) this.bsid, (Object) cDMAInfoPbPB.bsid) && equals((Object) this.nid, (Object) cDMAInfoPbPB.nid) && equals((Object) this.sid, (Object) cDMAInfoPbPB.sid) && equals((Object) this.rssi, (Object) cDMAInfoPbPB.rssi);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((this.bsid != null ? this.bsid.hashCode() : 0) * 37) + (this.nid != null ? this.nid.hashCode() : 0)) * 37) + (this.sid != null ? this.sid.hashCode() : 0)) * 37;
        if (this.rssi != null) {
            i2 = this.rssi.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
