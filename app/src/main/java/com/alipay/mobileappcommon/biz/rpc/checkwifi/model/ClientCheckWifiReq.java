package com.alipay.mobileappcommon.biz.rpc.checkwifi.model;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class ClientCheckWifiReq extends Message {
    public static final String DEFAULT_BSSID = "";
    public static final String DEFAULT_SSID = "";
    public static final int TAG_BSSID = 2;
    public static final int TAG_SSID = 1;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String bssid;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String ssid;

    public ClientCheckWifiReq(ClientCheckWifiReq message) {
        super(message);
        if (message != null) {
            this.ssid = message.ssid;
            this.bssid = message.bssid;
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public ClientCheckWifiReq() {
    }

    public final ClientCheckWifiReq fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.ssid = (String) value;
                break;
            case 2:
                this.bssid = (String) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClientCheckWifiReq)) {
            return false;
        }
        ClientCheckWifiReq o = (ClientCheckWifiReq) other;
        if (!equals((Object) this.ssid, (Object) o.ssid) || !equals((Object) this.bssid, (Object) o.bssid)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        if (this.ssid != null) {
            i = this.ssid.hashCode();
        } else {
            i = 0;
        }
        int i3 = i * 37;
        if (this.bssid != null) {
            i2 = this.bssid.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }
}
