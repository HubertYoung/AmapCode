package com.alipay.android.phone.mobilesdk.permission.guide.a;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;

/* compiled from: PermissionGuideRecord */
public final class d extends Message {
    public static final Long a = Long.valueOf(0);
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.STRING)
    public String b;
    @ProtoField(label = Label.REQUIRED, tag = 2, type = Datatype.STRING)
    public String c;
    @ProtoField(label = Label.REQUIRED, tag = 3, type = Datatype.INT64)
    public Long d;

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof d)) {
            return false;
        }
        d o = (d) other;
        if (!equals((Object) this.b, (Object) o.b) || !equals((Object) this.c, (Object) o.c) || !equals((Object) this.d, (Object) o.d)) {
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
        int hashCode = (this.b != null ? this.b.hashCode() : 0) * 37;
        if (this.c != null) {
            i = this.c.hashCode();
        } else {
            i = 0;
        }
        int i3 = (i + hashCode) * 37;
        if (this.d != null) {
            i2 = this.d.hashCode();
        }
        int result2 = i3 + i2;
        this.hashCode = result2;
        return result2;
    }
}
