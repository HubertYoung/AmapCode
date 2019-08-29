package com.squareup.wire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import okio.ByteString;

final class UnknownFieldMap {
    Map<Integer, List<FieldValue>> fieldMap;

    static abstract class FieldValue {
        private final int tag;
        private final WireType wireType;

        public abstract int getSerializedSize();

        public abstract void write(int i, WireOutput wireOutput) throws IOException;

        public FieldValue(int i, WireType wireType2) {
            this.tag = i;
            this.wireType = wireType2;
        }

        public static VarintFieldValue varint(int i, Long l) {
            return new VarintFieldValue(i, l);
        }

        public static Fixed32FieldValue fixed32(int i, Integer num) {
            return new Fixed32FieldValue(i, num);
        }

        public static Fixed64FieldValue fixed64(int i, Long l) {
            return new Fixed64FieldValue(i, l);
        }

        public static LengthDelimitedFieldValue lengthDelimited(int i, ByteString byteString) {
            return new LengthDelimitedFieldValue(i, byteString);
        }

        public int getTag() {
            return this.tag;
        }

        public WireType getWireType() {
            return this.wireType;
        }

        public Integer getAsInteger() {
            throw new IllegalStateException();
        }

        public Long getAsLong() {
            throw new IllegalStateException();
        }

        public ByteString getAsBytes() {
            throw new IllegalStateException();
        }
    }

    static final class Fixed32FieldValue extends FieldValue {
        private final Integer value;

        public final int getSerializedSize() {
            return 4;
        }

        public Fixed32FieldValue(int i, Integer num) {
            super(i, WireType.FIXED32);
            this.value = num;
        }

        public final void write(int i, WireOutput wireOutput) throws IOException {
            wireOutput.writeTag(i, WireType.FIXED32);
            wireOutput.writeFixed32(this.value.intValue());
        }

        public final Integer getAsInteger() {
            return this.value;
        }
    }

    static final class Fixed64FieldValue extends FieldValue {
        private final Long value;

        public final int getSerializedSize() {
            return 8;
        }

        public Fixed64FieldValue(int i, Long l) {
            super(i, WireType.FIXED64);
            this.value = l;
        }

        public final void write(int i, WireOutput wireOutput) throws IOException {
            wireOutput.writeTag(i, WireType.FIXED64);
            wireOutput.writeFixed64(this.value.longValue());
        }

        public final Long getAsLong() {
            return this.value;
        }
    }

    static final class LengthDelimitedFieldValue extends FieldValue {
        private final ByteString value;

        public LengthDelimitedFieldValue(int i, ByteString byteString) {
            super(i, WireType.LENGTH_DELIMITED);
            this.value = byteString;
        }

        public final int getSerializedSize() {
            return WireOutput.varint32Size(this.value.size()) + this.value.size();
        }

        public final void write(int i, WireOutput wireOutput) throws IOException {
            wireOutput.writeTag(i, WireType.LENGTH_DELIMITED);
            wireOutput.writeVarint32(this.value.size());
            wireOutput.writeRawBytes(this.value.toByteArray());
        }

        public final ByteString getAsBytes() {
            return this.value;
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
        private final Long value;

        public VarintFieldValue(int i, Long l) {
            super(i, WireType.VARINT);
            this.value = l;
        }

        public final int getSerializedSize() {
            return WireOutput.varint64Size(this.value.longValue());
        }

        public final void write(int i, WireOutput wireOutput) throws IOException {
            wireOutput.writeTag(i, WireType.VARINT);
            wireOutput.writeVarint64(this.value.longValue());
        }

        public final Long getAsLong() {
            return this.value;
        }
    }

    UnknownFieldMap() {
    }

    UnknownFieldMap(UnknownFieldMap unknownFieldMap) {
        if (unknownFieldMap.fieldMap != null) {
            ensureFieldMap().putAll(unknownFieldMap.fieldMap);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void addVarint(int i, Long l) throws IOException {
        addElement(ensureFieldMap(), i, l, WireType.VARINT);
    }

    /* access modifiers changed from: 0000 */
    public final void addFixed32(int i, Integer num) throws IOException {
        addElement(ensureFieldMap(), i, num, WireType.FIXED32);
    }

    /* access modifiers changed from: 0000 */
    public final void addFixed64(int i, Long l) throws IOException {
        addElement(ensureFieldMap(), i, l, WireType.FIXED64);
    }

    /* access modifiers changed from: 0000 */
    public final void addLengthDelimited(int i, ByteString byteString) throws IOException {
        addElement(ensureFieldMap(), i, byteString, WireType.LENGTH_DELIMITED);
    }

    private Map<Integer, List<FieldValue>> ensureFieldMap() {
        if (this.fieldMap == null) {
            this.fieldMap = new TreeMap();
        }
        return this.fieldMap;
    }

    private <T> void addElement(Map<Integer, List<FieldValue>> map, int i, T t, WireType wireType) throws IOException {
        FieldValue fieldValue;
        List list = map.get(Integer.valueOf(i));
        if (list == null) {
            list = new ArrayList();
            map.put(Integer.valueOf(i), list);
        }
        switch (wireType) {
            case VARINT:
                fieldValue = FieldValue.varint(i, (Long) t);
                break;
            case FIXED32:
                fieldValue = FieldValue.fixed32(i, (Integer) t);
                break;
            case FIXED64:
                fieldValue = FieldValue.fixed64(i, (Long) t);
                break;
            case LENGTH_DELIMITED:
                fieldValue = FieldValue.lengthDelimited(i, (ByteString) t);
                break;
            default:
                throw new IllegalArgumentException("Unsupported wireType = ".concat(String.valueOf(wireType)));
        }
        if (list.size() <= 0 || ((FieldValue) list.get(0)).getWireType() == fieldValue.getWireType()) {
            list.add(fieldValue);
        } else {
            throw new IOException(String.format("Wire type %s differs from previous type %s for tag %s", new Object[]{fieldValue.getWireType(), ((FieldValue) list.get(0)).getWireType(), Integer.valueOf(i)}));
        }
    }

    /* access modifiers changed from: 0000 */
    public final int getSerializedSize() {
        int i = 0;
        if (this.fieldMap != null) {
            for (Entry next : this.fieldMap.entrySet()) {
                for (FieldValue serializedSize : (List) next.getValue()) {
                    i = i + WireOutput.varintTagSize(((Integer) next.getKey()).intValue()) + serializedSize.getSerializedSize();
                }
            }
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public final void write(WireOutput wireOutput) throws IOException {
        if (this.fieldMap != null) {
            for (Entry next : this.fieldMap.entrySet()) {
                int intValue = ((Integer) next.getKey()).intValue();
                for (FieldValue write : (List) next.getValue()) {
                    write.write(intValue, wireOutput);
                }
            }
        }
    }
}
