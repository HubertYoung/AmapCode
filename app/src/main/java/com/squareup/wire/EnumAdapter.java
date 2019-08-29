package com.squareup.wire;

import com.squareup.wire.ProtoEnum;
import java.util.Arrays;
import java.util.Comparator;

final class EnumAdapter<E extends ProtoEnum> {
    private static final Comparator<ProtoEnum> COMPARATOR = new Comparator<ProtoEnum>() {
        public final int compare(ProtoEnum protoEnum, ProtoEnum protoEnum2) {
            return protoEnum.getValue() - protoEnum2.getValue();
        }
    };
    private final E[] constants;
    private final boolean isDense;
    private final Class<E> type;
    private final int[] values;

    EnumAdapter(Class<E> cls) {
        this.type = cls;
        this.constants = (ProtoEnum[]) cls.getEnumConstants();
        Arrays.sort(this.constants, COMPARATOR);
        int length = this.constants.length;
        if (this.constants[0].getValue() == 1 && this.constants[length - 1].getValue() == length) {
            this.isDense = true;
            this.values = null;
            return;
        }
        this.isDense = false;
        this.values = new int[length];
        for (int i = 0; i < length; i++) {
            this.values[i] = this.constants[i].getValue();
        }
    }

    public final int toInt(E e) {
        return e.getValue();
    }

    public final E fromInt(int i) {
        try {
            return this.constants[this.isDense ? i - 1 : Arrays.binarySearch(this.values, i)];
        } catch (IndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder("Unknown enum tag ");
            sb.append(i);
            sb.append(" for ");
            sb.append(this.type.getCanonicalName());
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
