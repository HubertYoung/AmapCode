package com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.ProtoField;

public final class MMDPTextMarkParam extends Message {
    public static final String DEFAULT_COLOR = "";
    public static final String DEFAULT_EXPR = "";
    public static final Integer DEFAULT_FONTTYPE = Integer.valueOf(0);
    public static final Integer DEFAULT_POSITION = Integer.valueOf(0);
    public static final Integer DEFAULT_SIZE = Integer.valueOf(0);
    public static final String DEFAULT_TEXT = "";
    public static final Integer DEFAULT_TRANSPARENCY = Integer.valueOf(0);
    public static final int TAG_COLOR = 3;
    public static final int TAG_EXPR = 1;
    public static final int TAG_FONTTYPE = 7;
    public static final int TAG_POSITION = 6;
    public static final int TAG_SIZE = 4;
    public static final int TAG_TEXT = 2;
    public static final int TAG_TRANSPARENCY = 5;
    @ProtoField(tag = 3, type = Datatype.STRING)
    public String color;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String expr;
    @ProtoField(tag = 7, type = Datatype.UINT32)
    public Integer fonttype;
    @ProtoField(tag = 6, type = Datatype.UINT32)
    public Integer position;
    @ProtoField(tag = 4, type = Datatype.UINT32)
    public Integer size;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String text;
    @ProtoField(tag = 5, type = Datatype.UINT32)
    public Integer transparency;

    public MMDPTextMarkParam(MMDPTextMarkParam message) {
        super(message);
        if (message != null) {
            this.expr = message.expr;
            this.text = message.text;
            this.color = message.color;
            this.size = message.size;
            this.transparency = message.transparency;
            this.position = message.position;
            this.fonttype = message.fonttype;
        }
    }

    public MMDPTextMarkParam() {
    }

    public final MMDPTextMarkParam fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.expr = (String) value;
                break;
            case 2:
                this.text = (String) value;
                break;
            case 3:
                this.color = (String) value;
                break;
            case 4:
                this.size = (Integer) value;
                break;
            case 5:
                this.transparency = (Integer) value;
                break;
            case 6:
                this.position = (Integer) value;
                break;
            case 7:
                this.fonttype = (Integer) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MMDPTextMarkParam)) {
            return false;
        }
        MMDPTextMarkParam o = (MMDPTextMarkParam) other;
        if (!equals((Object) this.expr, (Object) o.expr) || !equals((Object) this.text, (Object) o.text) || !equals((Object) this.color, (Object) o.color) || !equals((Object) this.size, (Object) o.size) || !equals((Object) this.transparency, (Object) o.transparency) || !equals((Object) this.position, (Object) o.position) || !equals((Object) this.fonttype, (Object) o.fonttype)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.expr != null ? this.expr.hashCode() : 0) * 37;
        if (this.text != null) {
            i = this.text.hashCode();
        } else {
            i = 0;
        }
        int i7 = (i + hashCode) * 37;
        if (this.color != null) {
            i2 = this.color.hashCode();
        } else {
            i2 = 0;
        }
        int i8 = (i2 + i7) * 37;
        if (this.size != null) {
            i3 = this.size.hashCode();
        } else {
            i3 = 0;
        }
        int i9 = (i3 + i8) * 37;
        if (this.transparency != null) {
            i4 = this.transparency.hashCode();
        } else {
            i4 = 0;
        }
        int i10 = (i4 + i9) * 37;
        if (this.position != null) {
            i5 = this.position.hashCode();
        } else {
            i5 = 0;
        }
        int i11 = (i5 + i10) * 37;
        if (this.fonttype != null) {
            i6 = this.fonttype.hashCode();
        }
        int result2 = i11 + i6;
        this.hashCode = result2;
        return result2;
    }
}
