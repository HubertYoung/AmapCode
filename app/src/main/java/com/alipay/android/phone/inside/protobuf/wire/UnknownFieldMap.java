package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

final class UnknownFieldMap {
    Map<Integer, List<FieldValue>> a;

    static abstract class FieldValue {
        private final int a;
        private final WireType b;

        public abstract int a();

        public abstract void a(int i, WireOutput wireOutput) throws IOException;

        public FieldValue(int i, WireType wireType) {
            this.a = i;
            this.b = wireType;
        }

        public static VarintFieldValue a(int i, Long l) {
            return new VarintFieldValue(i, l);
        }

        public static Fixed32FieldValue a(int i, Integer num) {
            return new Fixed32FieldValue(i, num);
        }

        public static Fixed64FieldValue b(int i, Long l) {
            return new Fixed64FieldValue(i, l);
        }

        public static LengthDelimitedFieldValue a(int i, ByteString byteString) {
            return new LengthDelimitedFieldValue(i, byteString);
        }

        public final WireType b() {
            return this.b;
        }
    }

    static final class Fixed32FieldValue extends FieldValue {
        private final Integer a;

        public final int a() {
            return 4;
        }

        public Fixed32FieldValue(int i, Integer num) {
            super(i, WireType.FIXED32);
            this.a = num;
        }

        public final void a(int i, WireOutput wireOutput) throws IOException {
            wireOutput.b(i, WireType.FIXED32);
            wireOutput.f(this.a.intValue());
        }
    }

    static final class Fixed64FieldValue extends FieldValue {
        private final Long a;

        public final int a() {
            return 8;
        }

        public Fixed64FieldValue(int i, Long l) {
            super(i, WireType.FIXED64);
            this.a = l;
        }

        public final void a(int i, WireOutput wireOutput) throws IOException {
            wireOutput.b(i, WireType.FIXED64);
            wireOutput.c(this.a.longValue());
        }
    }

    static final class LengthDelimitedFieldValue extends FieldValue {
        private final ByteString a;

        public LengthDelimitedFieldValue(int i, ByteString byteString) {
            super(i, WireType.LENGTH_DELIMITED);
            this.a = byteString;
        }

        public final int a() {
            return WireOutput.c(this.a.size()) + this.a.size();
        }

        public final void a(int i, WireOutput wireOutput) throws IOException {
            wireOutput.b(i, WireType.LENGTH_DELIMITED);
            wireOutput.e(this.a.size());
            wireOutput.b(this.a.toByteArray());
        }
    }

    enum UnknownFieldType {
        VARINT,
        FIXED32,
        FIXED64,
        LENGTH_DELIMITED;

        public static UnknownFieldType of(String str) {
            if ("varint".equals(str)) {
                return VARINT;
            }
            if ("fixed32".equals(str)) {
                return FIXED32;
            }
            if ("fixed64".equals(str)) {
                return FIXED64;
            }
            if ("length-delimited".equals(str)) {
                return LENGTH_DELIMITED;
            }
            throw new IllegalArgumentException("Unknown type ".concat(String.valueOf(str)));
        }
    }

    static final class VarintFieldValue extends FieldValue {
        private final Long a;

        public VarintFieldValue(int i, Long l) {
            super(i, WireType.VARINT);
            this.a = l;
        }

        public final int a() {
            return WireOutput.a(this.a.longValue());
        }

        public final void a(int i, WireOutput wireOutput) throws IOException {
            wireOutput.b(i, WireType.VARINT);
            wireOutput.b(this.a.longValue());
        }
    }

    UnknownFieldMap() {
    }

    UnknownFieldMap(UnknownFieldMap unknownFieldMap) {
        if (unknownFieldMap.a != null) {
            a().putAll(unknownFieldMap.a);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i, Long l) throws IOException {
        a(a(), i, l, WireType.VARINT);
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i, Integer num) throws IOException {
        a(a(), i, num, WireType.FIXED32);
    }

    /* access modifiers changed from: 0000 */
    public final void b(int i, Long l) throws IOException {
        a(a(), i, l, WireType.FIXED64);
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i, ByteString byteString) throws IOException {
        a(a(), i, byteString, WireType.LENGTH_DELIMITED);
    }

    private Map<Integer, List<FieldValue>> a() {
        if (this.a == null) {
            this.a = new TreeMap();
        }
        return this.a;
    }

    private static <T> void a(Map<Integer, List<FieldValue>> map, int i, T t, WireType wireType) throws IOException {
        FieldValue fieldValue;
        List list = map.get(Integer.valueOf(i));
        if (list == null) {
            list = new ArrayList();
            map.put(Integer.valueOf(i), list);
        }
        switch (wireType) {
            case VARINT:
                fieldValue = FieldValue.a(i, (Long) t);
                break;
            case FIXED32:
                fieldValue = FieldValue.a(i, (Integer) t);
                break;
            case FIXED64:
                fieldValue = FieldValue.b(i, (Long) t);
                break;
            case LENGTH_DELIMITED:
                fieldValue = FieldValue.a(i, (ByteString) t);
                break;
            default:
                throw new IllegalArgumentException("Unsupported wireType = ".concat(String.valueOf(wireType)));
        }
        if (list.size() <= 0 || ((FieldValue) list.get(0)).b() == fieldValue.b()) {
            list.add(fieldValue);
        } else {
            throw new IOException(String.format("Wire type %s differs from previous type %s for tag %s", new Object[]{fieldValue.b(), ((FieldValue) list.get(0)).b(), Integer.valueOf(i)}));
        }
    }
}
