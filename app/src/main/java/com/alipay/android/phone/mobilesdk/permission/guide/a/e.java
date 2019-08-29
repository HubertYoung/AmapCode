package com.alipay.android.phone.mobilesdk.permission.guide.a;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

/* compiled from: PermissionGuideRecords */
public final class e extends Message {
    public static final List<d> a = Collections.emptyList();
    @ProtoField(label = Label.REPEATED, tag = 1)
    public List<d> b;

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof e)) {
            return false;
        }
        return equals(this.b, ((e) other).b);
    }

    public final int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int result2 = this.b != null ? this.b.hashCode() : 1;
        this.hashCode = result2;
        return result2;
    }
}
