package com.alipay.mobileappcommon.biz.rpc.checkwifi.model;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class ClientCheckWifiRes extends Message {
    public static final String DEFAULT_ISWIFI = "";
    public static final int TAG_ISWIFI = 1;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String isWIFI;

    public ClientCheckWifiRes(ClientCheckWifiRes message) {
        super(message);
        if (message != null) {
            this.isWIFI = message.isWIFI;
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public ClientCheckWifiRes() {
    }

    public final ClientCheckWifiRes fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.isWIFI = (String) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClientCheckWifiRes)) {
            return false;
        }
        return equals((Object) this.isWIFI, (Object) ((ClientCheckWifiRes) other).isWIFI);
    }

    public final int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.isWIFI != null ? this.isWIFI.hashCode() : 0;
        this.hashCode = result2;
        return result2;
    }
}
