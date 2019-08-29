package com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;

public final class MMDPImageMarkParam extends Message {
    public static final String DEFAULT_EXPR = "";
    public static final String DEFAULT_FILEID = "";
    public static final Integer DEFAULT_HEIGHT = Integer.valueOf(0);
    public static final Integer DEFAULT_POSITION = Integer.valueOf(0);
    public static final Integer DEFAULT_PWH = Integer.valueOf(0);
    public static final Integer DEFAULT_PXY = Integer.valueOf(0);
    public static final Integer DEFAULT_TRANSPARENCY = Integer.valueOf(0);
    public static final Integer DEFAULT_WIDTH = Integer.valueOf(0);
    public static final Integer DEFAULT_X = Integer.valueOf(0);
    public static final Integer DEFAULT_Y = Integer.valueOf(0);
    public static final int TAG_EXPR = 2;
    public static final int TAG_FILEID = 1;
    public static final int TAG_HEIGHT = 6;
    public static final int TAG_POSITION = 3;
    public static final int TAG_PWH = 9;
    public static final int TAG_PXY = 10;
    public static final int TAG_TRANSPARENCY = 4;
    public static final int TAG_WIDTH = 5;
    public static final int TAG_X = 7;
    public static final int TAG_Y = 8;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String expr;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.STRING)
    public String fileid;
    @ProtoField(tag = 6, type = Datatype.UINT32)
    public Integer height;
    @ProtoField(tag = 3, type = Datatype.UINT32)
    public Integer position;
    @ProtoField(tag = 9, type = Datatype.UINT32)
    public Integer pwh;
    @ProtoField(tag = 10, type = Datatype.UINT32)
    public Integer pxy;
    @ProtoField(tag = 4, type = Datatype.UINT32)
    public Integer transparency;
    @ProtoField(tag = 5, type = Datatype.UINT32)
    public Integer width;
    @ProtoField(tag = 7, type = Datatype.UINT32)
    public Integer x;
    @ProtoField(tag = 8, type = Datatype.UINT32)
    public Integer y;

    public MMDPImageMarkParam(MMDPImageMarkParam message) {
        super(message);
        if (message != null) {
            this.fileid = message.fileid;
            this.expr = message.expr;
            this.position = message.position;
            this.transparency = message.transparency;
            this.width = message.width;
            this.height = message.height;
            this.x = message.x;
            this.y = message.y;
            this.pwh = message.pwh;
            this.pxy = message.pxy;
        }
    }

    public MMDPImageMarkParam() {
    }

    public final MMDPImageMarkParam fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.fileid = (String) value;
                break;
            case 2:
                this.expr = (String) value;
                break;
            case 3:
                this.position = (Integer) value;
                break;
            case 4:
                this.transparency = (Integer) value;
                break;
            case 5:
                this.width = (Integer) value;
                break;
            case 6:
                this.height = (Integer) value;
                break;
            case 7:
                this.x = (Integer) value;
                break;
            case 8:
                this.y = (Integer) value;
                break;
            case 9:
                this.pwh = (Integer) value;
                break;
            case 10:
                this.pxy = (Integer) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MMDPImageMarkParam)) {
            return false;
        }
        MMDPImageMarkParam o = (MMDPImageMarkParam) other;
        if (!equals((Object) this.fileid, (Object) o.fileid) || !equals((Object) this.expr, (Object) o.expr) || !equals((Object) this.position, (Object) o.position) || !equals((Object) this.transparency, (Object) o.transparency) || !equals((Object) this.width, (Object) o.width) || !equals((Object) this.height, (Object) o.height) || !equals((Object) this.x, (Object) o.x) || !equals((Object) this.y, (Object) o.y) || !equals((Object) this.pwh, (Object) o.pwh) || !equals((Object) this.pxy, (Object) o.pxy)) {
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
        int i6;
        int i7;
        int i8;
        int i9 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.fileid != null ? this.fileid.hashCode() : 0) * 37;
        if (this.expr != null) {
            i = this.expr.hashCode();
        } else {
            i = 0;
        }
        int i10 = (i + hashCode) * 37;
        if (this.position != null) {
            i2 = this.position.hashCode();
        } else {
            i2 = 0;
        }
        int i11 = (i2 + i10) * 37;
        if (this.transparency != null) {
            i3 = this.transparency.hashCode();
        } else {
            i3 = 0;
        }
        int i12 = (i3 + i11) * 37;
        if (this.width != null) {
            i4 = this.width.hashCode();
        } else {
            i4 = 0;
        }
        int i13 = (i4 + i12) * 37;
        if (this.height != null) {
            i5 = this.height.hashCode();
        } else {
            i5 = 0;
        }
        int i14 = (i5 + i13) * 37;
        if (this.x != null) {
            i6 = this.x.hashCode();
        } else {
            i6 = 0;
        }
        int i15 = (i6 + i14) * 37;
        if (this.y != null) {
            i7 = this.y.hashCode();
        } else {
            i7 = 0;
        }
        int i16 = (i7 + i15) * 37;
        if (this.pwh != null) {
            i8 = this.pwh.hashCode();
        } else {
            i8 = 0;
        }
        int i17 = (i8 + i16) * 37;
        if (this.pxy != null) {
            i9 = this.pxy.hashCode();
        }
        int result2 = i17 + i9;
        this.hashCode = result2;
        return result2;
    }
}
