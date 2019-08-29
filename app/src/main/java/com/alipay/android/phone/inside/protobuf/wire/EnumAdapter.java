package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.wire.ProtoEnum;
import java.util.Arrays;
import java.util.Comparator;

final class EnumAdapter<E extends ProtoEnum> {
    private static final Comparator<ProtoEnum> a = new Comparator<ProtoEnum>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return ((ProtoEnum) obj).getValue() - ((ProtoEnum) obj2).getValue();
        }
    };
    private final Class<E> b;
    private final int[] c;
    private final E[] d;
    private final boolean e;

    EnumAdapter(Class<E> cls) {
        this.b = cls;
        this.d = (ProtoEnum[]) cls.getEnumConstants();
        Arrays.sort(this.d, a);
        int length = this.d.length;
        if (this.d[0].getValue() == 1 && this.d[length - 1].getValue() == length) {
            this.e = true;
            this.c = null;
            return;
        }
        this.e = false;
        this.c = new int[length];
        for (int i = 0; i < length; i++) {
            this.c[i] = this.d[i].getValue();
        }
    }

    public static int a(E e2) {
        return e2.getValue();
    }

    public final E a(int i) {
        try {
            return this.d[this.e ? i - 1 : Arrays.binarySearch(this.c, i)];
        } catch (IndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder("Unknown enum tag ");
            sb.append(i);
            sb.append(" for ");
            sb.append(this.b.getCanonicalName());
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
