package com.alipay.android.phone.inside.config.rpc.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class SwitchInfoRespPbPB extends Message {
    public static final List<String> DEFAULT_DELETEKEYS = Collections.emptyList();
    public static final Boolean DEFAULT_INCREMENT = Boolean.FALSE;
    public static final String DEFAULT_RESPONSETIME = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.FALSE;
    public static final List<SwitchInfoEntryPbPB> DEFAULT_SWITCHES = Collections.emptyList();
    public static final int TAG_DELETEKEYS = 3;
    public static final int TAG_INCREMENT = 5;
    public static final int TAG_RESPONSETIME = 4;
    public static final int TAG_SUCCESS = 1;
    public static final int TAG_SWITCHES = 2;
    @ProtoField(label = Label.REPEATED, tag = 3, type = Datatype.STRING)
    public List<String> deleteKeys;
    @ProtoField(tag = 5, type = Datatype.BOOL)
    public Boolean increment;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String responseTime;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;
    @ProtoField(label = Label.REPEATED, tag = 2)
    public List<SwitchInfoEntryPbPB> switches;

    public SwitchInfoRespPbPB(SwitchInfoRespPbPB switchInfoRespPbPB) {
        super(switchInfoRespPbPB);
        if (switchInfoRespPbPB != null) {
            this.success = switchInfoRespPbPB.success;
            this.switches = copyOf(switchInfoRespPbPB.switches);
            this.deleteKeys = copyOf(switchInfoRespPbPB.deleteKeys);
            this.responseTime = switchInfoRespPbPB.responseTime;
            this.increment = switchInfoRespPbPB.increment;
        }
    }

    public SwitchInfoRespPbPB() {
    }

    public final SwitchInfoRespPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.switches = immutableCopyOf((List) obj);
                break;
            case 3:
                this.deleteKeys = immutableCopyOf((List) obj);
                break;
            case 4:
                this.responseTime = (String) obj;
                break;
            case 5:
                this.increment = (Boolean) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SwitchInfoRespPbPB)) {
            return false;
        }
        SwitchInfoRespPbPB switchInfoRespPbPB = (SwitchInfoRespPbPB) obj;
        return equals((Object) this.success, (Object) switchInfoRespPbPB.success) && equals(this.switches, switchInfoRespPbPB.switches) && equals(this.deleteKeys, switchInfoRespPbPB.deleteKeys) && equals((Object) this.responseTime, (Object) switchInfoRespPbPB.responseTime) && equals((Object) this.increment, (Object) switchInfoRespPbPB.increment);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int i3 = 1;
        int hashCode = (((this.success != null ? this.success.hashCode() : 0) * 37) + (this.switches != null ? this.switches.hashCode() : 1)) * 37;
        if (this.deleteKeys != null) {
            i3 = this.deleteKeys.hashCode();
        }
        int hashCode2 = (((hashCode + i3) * 37) + (this.responseTime != null ? this.responseTime.hashCode() : 0)) * 37;
        if (this.increment != null) {
            i2 = this.increment.hashCode();
        }
        int i4 = hashCode2 + i2;
        this.hashCode = i4;
        return i4;
    }
}
