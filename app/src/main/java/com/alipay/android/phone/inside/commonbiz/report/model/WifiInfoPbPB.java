package com.alipay.android.phone.inside.commonbiz.report.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;

public final class WifiInfoPbPB extends Message {
    public static final Double DEFAULT_RSSI = Double.valueOf(0.0d);
    public static final String DEFAULT_SSID = "";
    public static final String DEFAULT_WIFIMAC = "";
    public static final int TAG_RSSI = 3;
    public static final int TAG_SSID = 2;
    public static final int TAG_WIFIMAC = 1;
    @ProtoField(tag = 3, type = Datatype.DOUBLE)
    public Double rssi;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String ssid;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String wifiMac;

    public WifiInfoPbPB(WifiInfoPbPB wifiInfoPbPB) {
        super(wifiInfoPbPB);
        if (wifiInfoPbPB != null) {
            this.wifiMac = wifiInfoPbPB.wifiMac;
            this.ssid = wifiInfoPbPB.ssid;
            this.rssi = wifiInfoPbPB.rssi;
        }
    }

    public WifiInfoPbPB() {
    }

    public final WifiInfoPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.wifiMac = (String) obj;
                break;
            case 2:
                this.ssid = (String) obj;
                break;
            case 3:
                this.rssi = (Double) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WifiInfoPbPB)) {
            return false;
        }
        WifiInfoPbPB wifiInfoPbPB = (WifiInfoPbPB) obj;
        return equals((Object) this.wifiMac, (Object) wifiInfoPbPB.wifiMac) && equals((Object) this.ssid, (Object) wifiInfoPbPB.ssid) && equals((Object) this.rssi, (Object) wifiInfoPbPB.rssi);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((this.wifiMac != null ? this.wifiMac.hashCode() : 0) * 37) + (this.ssid != null ? this.ssid.hashCode() : 0)) * 37;
        if (this.rssi != null) {
            i2 = this.rssi.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
