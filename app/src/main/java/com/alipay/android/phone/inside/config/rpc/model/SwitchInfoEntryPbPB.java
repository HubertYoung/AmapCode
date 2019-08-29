package com.alipay.android.phone.inside.config.rpc.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import org.json.JSONException;
import org.json.JSONObject;

public final class SwitchInfoEntryPbPB extends Message {
    public static final String DEFAULT_EXPVERSIONID = "";
    public static final String DEFAULT_KEY = "";
    public static final String DEFAULT_VALUE = "";
    public static final int TAG_EXPVERSIONID = 3;
    public static final int TAG_KEY = 1;
    public static final int TAG_VALUE = 2;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String expVersionId;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String key;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String value;

    public SwitchInfoEntryPbPB(SwitchInfoEntryPbPB switchInfoEntryPbPB) {
        super(switchInfoEntryPbPB);
        if (switchInfoEntryPbPB != null) {
            this.key = switchInfoEntryPbPB.key;
            this.value = switchInfoEntryPbPB.value;
            this.expVersionId = switchInfoEntryPbPB.expVersionId;
        }
    }

    public SwitchInfoEntryPbPB() {
    }

    public final SwitchInfoEntryPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.key = (String) obj;
                break;
            case 2:
                this.value = (String) obj;
                break;
            case 3:
                this.expVersionId = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SwitchInfoEntryPbPB)) {
            return false;
        }
        SwitchInfoEntryPbPB switchInfoEntryPbPB = (SwitchInfoEntryPbPB) obj;
        return equals((Object) this.key, (Object) switchInfoEntryPbPB.key) && equals((Object) this.value, (Object) switchInfoEntryPbPB.value) && equals((Object) this.expVersionId, (Object) switchInfoEntryPbPB.expVersionId);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((this.key != null ? this.key.hashCode() : 0) * 37) + (this.value != null ? this.value.hashCode() : 0)) * 37;
        if (this.expVersionId != null) {
            i2 = this.expVersionId.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }

    public final JSONObject format() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("key", this.key);
        jSONObject.put("value", this.value);
        jSONObject.put("expVersionId", this.expVersionId);
        return jSONObject;
    }
}
