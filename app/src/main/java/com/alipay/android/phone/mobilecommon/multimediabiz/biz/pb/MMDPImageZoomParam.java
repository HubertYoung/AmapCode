package com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class MMDPImageZoomParam extends Message {
    public static final String DEFAULT_EXPR = "";
    public static final MMDPImageFormat DEFAULT_FORMAT = MMDPImageFormat.NOFORMAT;
    public static final Integer DEFAULT_HEIGHT = Integer.valueOf(0);
    public static final Integer DEFAULT_QUALITY = Integer.valueOf(0);
    public static final MMDPImageScale DEFAULT_SCALE = MMDPImageScale.NOSCALE;
    public static final Integer DEFAULT_WIDTH = Integer.valueOf(0);
    public static final int TAG_EXPR = 1;
    public static final int TAG_FORMAT = 6;
    public static final int TAG_HEIGHT = 3;
    public static final int TAG_QUALITY = 5;
    public static final int TAG_SCALE = 4;
    public static final int TAG_WIDTH = 2;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String expr;
    @ProtoField(tag = 6, type = Datatype.ENUM)
    public MMDPImageFormat format;
    @ProtoField(tag = 3, type = Datatype.UINT32)
    public Integer height;
    @ProtoField(tag = 5, type = Datatype.UINT32)
    public Integer quality;
    @ProtoField(tag = 4, type = Datatype.ENUM)
    public MMDPImageScale scale;
    @ProtoField(tag = 2, type = Datatype.UINT32)
    public Integer width;

    public MMDPImageZoomParam(MMDPImageZoomParam message) {
        super(message);
        if (message != null) {
            this.expr = message.expr;
            this.width = message.width;
            this.height = message.height;
            this.scale = message.scale;
            this.quality = message.quality;
            this.format = message.format;
        }
    }

    public MMDPImageZoomParam() {
    }

    public final MMDPImageZoomParam fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.expr = (String) value;
                break;
            case 2:
                this.width = (Integer) value;
                break;
            case 3:
                this.height = (Integer) value;
                break;
            case 4:
                this.scale = (MMDPImageScale) value;
                break;
            case 5:
                this.quality = (Integer) value;
                break;
            case 6:
                this.format = (MMDPImageFormat) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MMDPImageZoomParam)) {
            return false;
        }
        MMDPImageZoomParam o = (MMDPImageZoomParam) other;
        if (!equals((Object) this.expr, (Object) o.expr) || !equals((Object) this.width, (Object) o.width) || !equals((Object) this.height, (Object) o.height) || !equals((Object) this.scale, (Object) o.scale) || !equals((Object) this.quality, (Object) o.quality) || !equals((Object) this.format, (Object) o.format)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.expr != null ? this.expr.hashCode() : 0) * 37;
        if (this.width != null) {
            i = this.width.hashCode();
        } else {
            i = 0;
        }
        int i6 = (i + hashCode) * 37;
        if (this.height != null) {
            i2 = this.height.hashCode();
        } else {
            i2 = 0;
        }
        int i7 = (i2 + i6) * 37;
        if (this.scale != null) {
            i3 = this.scale.hashCode();
        } else {
            i3 = 0;
        }
        int i8 = (i3 + i7) * 37;
        if (this.quality != null) {
            i4 = this.quality.hashCode();
        } else {
            i4 = 0;
        }
        int i9 = (i4 + i8) * 37;
        if (this.format != null) {
            i5 = this.format.hashCode();
        }
        int result2 = i9 + i5;
        this.hashCode = result2;
        return result2;
    }
}
