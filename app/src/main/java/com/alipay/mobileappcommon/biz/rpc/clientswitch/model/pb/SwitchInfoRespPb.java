package com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class SwitchInfoRespPb extends Message {
    public static final List<String> DEFAULT_DELETEKEYS = Collections.emptyList();
    public static final Boolean DEFAULT_INCREMENT = Boolean.valueOf(false);
    public static final String DEFAULT_RESPONSETIME = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.valueOf(false);
    public static final List<SwitchInfoEntryPb> DEFAULT_SWITCHES = Collections.emptyList();
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
    public List<SwitchInfoEntryPb> switches;

    public SwitchInfoRespPb() {
    }

    public SwitchInfoRespPb(SwitchInfoRespPb switchInfoRespPb) {
        super(switchInfoRespPb);
        if (switchInfoRespPb != null) {
            this.success = switchInfoRespPb.success;
            this.switches = copyOf(switchInfoRespPb.switches);
            this.deleteKeys = copyOf(switchInfoRespPb.deleteKeys);
            this.responseTime = switchInfoRespPb.responseTime;
            this.increment = switchInfoRespPb.increment;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SwitchInfoRespPb)) {
            return false;
        }
        SwitchInfoRespPb switchInfoRespPb = (SwitchInfoRespPb) obj;
        return equals((Object) this.success, (Object) switchInfoRespPb.success) && equals(this.switches, switchInfoRespPb.switches) && equals(this.deleteKeys, switchInfoRespPb.deleteKeys) && equals((Object) this.responseTime, (Object) switchInfoRespPb.responseTime) && equals((Object) this.increment, (Object) switchInfoRespPb.increment);
    }

    public final SwitchInfoRespPb fillTagValue(int i, Object obj) {
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

    public final int hashCode() {
        int i = 1;
        int i2 = 0;
        int i3 = this.hashCode;
        if (i3 != 0) {
            return i3;
        }
        int hashCode = ((this.switches != null ? this.switches.hashCode() : 1) + ((this.success != null ? this.success.hashCode() : 0) * 37)) * 37;
        if (this.deleteKeys != null) {
            i = this.deleteKeys.hashCode();
        }
        int hashCode2 = ((this.responseTime != null ? this.responseTime.hashCode() : 0) + ((hashCode + i) * 37)) * 37;
        if (this.increment != null) {
            i2 = this.increment.hashCode();
        }
        int i4 = hashCode2 + i2;
        this.hashCode = i4;
        return i4;
    }
}
