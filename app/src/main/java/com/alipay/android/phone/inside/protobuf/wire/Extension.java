package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.wire.ExtendableMessage;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;

public final class Extension<T extends ExtendableMessage<?>, E> implements Comparable<Extension<?, ?>> {
    private final Class<T> a;
    private final Class<? extends Message> b;
    private final Class<? extends ProtoEnum> c;
    private final String d;
    private final int e;
    private final Datatype f;
    private final Label g;

    /* access modifiers changed from: private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=com.alipay.android.phone.inside.protobuf.wire.Extension<?, ?>, code=com.alipay.android.phone.inside.protobuf.wire.Extension, for r4v0, types: [com.alipay.android.phone.inside.protobuf.wire.Extension, com.alipay.android.phone.inside.protobuf.wire.Extension<?, ?>] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compareTo(com.alipay.android.phone.inside.protobuf.wire.Extension r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != r3) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = r3.e
            int r2 = r4.e
            if (r1 == r2) goto L_0x0010
            int r0 = r3.e
            int r4 = r4.e
            int r0 = r0 - r4
            return r0
        L_0x0010:
            com.alipay.android.phone.inside.protobuf.wire.Message$Datatype r1 = r3.f
            com.alipay.android.phone.inside.protobuf.wire.Message$Datatype r2 = r4.f
            if (r1 == r2) goto L_0x0024
            com.alipay.android.phone.inside.protobuf.wire.Message$Datatype r0 = r3.f
            int r0 = r0.value()
            com.alipay.android.phone.inside.protobuf.wire.Message$Datatype r4 = r4.f
            int r4 = r4.value()
            int r0 = r0 - r4
            return r0
        L_0x0024:
            com.alipay.android.phone.inside.protobuf.wire.Message$Label r1 = r3.g
            com.alipay.android.phone.inside.protobuf.wire.Message$Label r2 = r4.g
            if (r1 == r2) goto L_0x0038
            com.alipay.android.phone.inside.protobuf.wire.Message$Label r0 = r3.g
            int r0 = r0.value()
            com.alipay.android.phone.inside.protobuf.wire.Message$Label r4 = r4.g
            int r4 = r4.value()
            int r0 = r0 - r4
            return r0
        L_0x0038:
            java.lang.Class<T> r1 = r3.a
            if (r1 == 0) goto L_0x0057
            java.lang.Class<T> r1 = r3.a
            java.lang.Class<T> r2 = r4.a
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0057
            java.lang.Class<T> r0 = r3.a
            java.lang.String r0 = r0.getName()
            java.lang.Class<T> r4 = r4.a
            java.lang.String r4 = r4.getName()
            int r4 = r0.compareTo(r4)
            return r4
        L_0x0057:
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.Message> r1 = r3.b
            if (r1 == 0) goto L_0x0076
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.Message> r1 = r3.b
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.Message> r2 = r4.b
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0076
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.Message> r0 = r3.b
            java.lang.String r0 = r0.getName()
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.Message> r4 = r4.b
            java.lang.String r4 = r4.getName()
            int r4 = r0.compareTo(r4)
            return r4
        L_0x0076:
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.ProtoEnum> r1 = r3.c
            if (r1 == 0) goto L_0x0095
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.ProtoEnum> r1 = r3.c
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.ProtoEnum> r2 = r4.c
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0095
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.ProtoEnum> r0 = r3.c
            java.lang.String r0 = r0.getName()
            java.lang.Class<? extends com.alipay.android.phone.inside.protobuf.wire.ProtoEnum> r4 = r4.c
            java.lang.String r4 = r4.getName()
            int r4 = r0.compareTo(r4)
            return r4
        L_0x0095:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.protobuf.wire.Extension.compareTo(com.alipay.android.phone.inside.protobuf.wire.Extension):int");
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Extension) && compareTo((Extension) obj) == 0;
    }

    public final int hashCode() {
        int i = 0;
        int value = ((((((((this.e * 37) + this.f.value()) * 37) + this.g.value()) * 37) + this.a.hashCode()) * 37) + (this.b != null ? this.b.hashCode() : 0)) * 37;
        if (this.c != null) {
            i = this.c.hashCode();
        }
        return value + i;
    }

    public final String toString() {
        return String.format("[%s %s %s = %d]", new Object[]{this.g, this.f, this.d, Integer.valueOf(this.e)});
    }

    public final Class<T> a() {
        return this.a;
    }

    public final Class<? extends Message> b() {
        return this.b;
    }

    public final Class<? extends ProtoEnum> c() {
        return this.c;
    }

    public final String d() {
        return this.d;
    }

    public final int e() {
        return this.e;
    }

    public final Datatype f() {
        return this.f;
    }

    public final Label g() {
        return this.g;
    }
}
