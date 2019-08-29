package com.alipay.mobile.tinyappcustom.h5plugin.openauth;

import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.MapStringString;
import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class AuthRequestContextPB extends Message {
    public static final String DEFAULT_CURRENTLONGITUDEANDLATITUDE = "";
    public static final String DEFAULT_TERMINALTYPE = "";
    public static final int TAG_CTUEXTINFO = 3;
    public static final int TAG_CURRENTLONGITUDEANDLATITUDE = 1;
    public static final int TAG_TERMINALTYPE = 2;
    @ProtoField(tag = 3)
    public MapStringString ctuExtInfo;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String currentLongitudeAndLatitude;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String terminalType;

    public AuthRequestContextPB(AuthRequestContextPB message) {
        super(message);
        if (message != null) {
            this.currentLongitudeAndLatitude = message.currentLongitudeAndLatitude;
            this.terminalType = message.terminalType;
            this.ctuExtInfo = message.ctuExtInfo;
        }
    }

    public AuthRequestContextPB() {
    }

    public final AuthRequestContextPB fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.currentLongitudeAndLatitude = (String) value;
                break;
            case 2:
                this.terminalType = (String) value;
                break;
            case 3:
                this.ctuExtInfo = (MapStringString) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AuthRequestContextPB)) {
            return false;
        }
        AuthRequestContextPB o = (AuthRequestContextPB) other;
        if (!equals((Object) this.currentLongitudeAndLatitude, (Object) o.currentLongitudeAndLatitude) || !equals((Object) this.terminalType, (Object) o.terminalType) || !equals((Object) this.ctuExtInfo, (Object) o.ctuExtInfo)) {
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
        int hashCode = (this.currentLongitudeAndLatitude != null ? this.currentLongitudeAndLatitude.hashCode() : 0) * 37;
        if (this.terminalType != null) {
            i = this.terminalType.hashCode();
        } else {
            i = 0;
        }
        int i3 = (i + hashCode) * 37;
        if (this.ctuExtInfo != null) {
            i2 = this.ctuExtInfo.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }
}
