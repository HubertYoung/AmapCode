package com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoField;

public final class MMDPBizParam extends Message {
    public static final int TAG_IMAGEMARKPARAM = 3;
    public static final int TAG_TEXTMARKPARAM = 2;
    public static final int TAG_ZOOMPARAM = 1;
    @ProtoField(tag = 3)
    public MMDPImageMarkParam imageMarkParam;
    @ProtoField(tag = 2)
    public MMDPTextMarkParam textMarkParam;
    @ProtoField(tag = 1)
    public MMDPImageZoomParam zoomParam;

    public MMDPBizParam(MMDPBizParam message) {
        super(message);
        if (message != null) {
            this.zoomParam = message.zoomParam;
            this.textMarkParam = message.textMarkParam;
            this.imageMarkParam = message.imageMarkParam;
        }
    }

    public MMDPBizParam() {
    }

    public final MMDPBizParam fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.zoomParam = (MMDPImageZoomParam) value;
                break;
            case 2:
                this.textMarkParam = (MMDPTextMarkParam) value;
                break;
            case 3:
                this.imageMarkParam = (MMDPImageMarkParam) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MMDPBizParam)) {
            return false;
        }
        MMDPBizParam o = (MMDPBizParam) other;
        if (!equals((Object) this.zoomParam, (Object) o.zoomParam) || !equals((Object) this.textMarkParam, (Object) o.textMarkParam) || !equals((Object) this.imageMarkParam, (Object) o.imageMarkParam)) {
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
        int hashCode = (this.zoomParam != null ? this.zoomParam.hashCode() : 0) * 37;
        if (this.textMarkParam != null) {
            i = this.textMarkParam.hashCode();
        } else {
            i = 0;
        }
        int i3 = (i + hashCode) * 37;
        if (this.imageMarkParam != null) {
            i2 = this.imageMarkParam.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }
}
