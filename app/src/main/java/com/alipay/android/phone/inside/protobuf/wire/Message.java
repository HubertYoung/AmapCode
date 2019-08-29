package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Message {
    /* access modifiers changed from: private */
    public static final Wire WIRE = new Wire((Class<?>[]) new Class[0]);
    private transient int cachedSerializedSize;
    protected transient int hashCode = 0;
    private transient boolean haveCachedSerializedSize;
    /* access modifiers changed from: private */
    public transient UnknownFieldMap unknownFields;

    public static abstract class Builder<T extends Message> {
        UnknownFieldMap b;
    }

    public enum Datatype {
        INT32(1),
        INT64(2),
        UINT32(3),
        UINT64(4),
        SINT32(5),
        SINT64(6),
        BOOL(7),
        ENUM(8),
        STRING(9),
        BYTES(10),
        MESSAGE(11),
        FIXED32(12),
        SFIXED32(13),
        FIXED64(14),
        SFIXED64(15),
        FLOAT(16),
        DOUBLE(17);
        
        public static final Comparator<Datatype> ORDER_BY_NAME = null;
        private static final Map<String, Datatype> TYPES_BY_NAME = null;
        private final int value;

        static {
            ORDER_BY_NAME = new Comparator<Datatype>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return ((Datatype) obj).name().compareTo(((Datatype) obj2).name());
                }
            };
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            TYPES_BY_NAME = linkedHashMap;
            linkedHashMap.put("int32", INT32);
            TYPES_BY_NAME.put("int64", INT64);
            TYPES_BY_NAME.put("uint32", UINT32);
            TYPES_BY_NAME.put("uint64", UINT64);
            TYPES_BY_NAME.put("sint32", SINT32);
            TYPES_BY_NAME.put("sint64", SINT64);
            TYPES_BY_NAME.put("bool", BOOL);
            TYPES_BY_NAME.put("enum", ENUM);
            TYPES_BY_NAME.put(ResUtils.STRING, STRING);
            TYPES_BY_NAME.put("bytes", BYTES);
            TYPES_BY_NAME.put("message", MESSAGE);
            TYPES_BY_NAME.put("fixed32", FIXED32);
            TYPES_BY_NAME.put("sfixed32", SFIXED32);
            TYPES_BY_NAME.put("fixed64", FIXED64);
            TYPES_BY_NAME.put("sfixed64", SFIXED64);
            TYPES_BY_NAME.put("float", FLOAT);
            TYPES_BY_NAME.put("double", DOUBLE);
        }

        private Datatype(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }

        public final WireType wireType() {
            switch (this) {
                case INT32:
                case INT64:
                case UINT32:
                case UINT64:
                case SINT32:
                case SINT64:
                case BOOL:
                case ENUM:
                    return WireType.VARINT;
                case FIXED32:
                case SFIXED32:
                case FLOAT:
                    return WireType.FIXED32;
                case FIXED64:
                case SFIXED64:
                case DOUBLE:
                    return WireType.FIXED64;
                case STRING:
                case BYTES:
                case MESSAGE:
                    return WireType.LENGTH_DELIMITED;
                default:
                    throw new AssertionError("No wiretype for datatype ".concat(String.valueOf(this)));
            }
        }

        public static Datatype of(String str) {
            return TYPES_BY_NAME.get(str);
        }
    }

    public enum Label {
        REQUIRED(32),
        OPTIONAL(64),
        REPEATED(128),
        PACKED(256);
        
        public static final Comparator<Label> ORDER_BY_NAME = null;
        private final int value;

        static {
            ORDER_BY_NAME = new Comparator<Label>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return ((Label) obj).name().compareTo(((Label) obj2).name());
                }
            };
        }

        private Label(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }

        public final boolean isRepeated() {
            return this == REPEATED || this == PACKED;
        }

        public final boolean isPacked() {
            return this == PACKED;
        }
    }

    protected Message(Message message) {
        if (message != null && message.unknownFields != null) {
            this.unknownFields = new UnknownFieldMap(message.unknownFields);
        }
    }

    protected Message() {
    }

    /* access modifiers changed from: protected */
    public void setBuilder(Builder builder) {
        if (builder.b != null) {
            this.unknownFields = new UnknownFieldMap(builder.b);
        }
    }

    /* access modifiers changed from: protected */
    public Collection<List<FieldValue>> unknownFields() {
        if (this.unknownFields == null) {
            return Collections.emptySet();
        }
        return this.unknownFields.a.values();
    }

    protected static <T> List<T> copyOf(List<T> list) {
        if (list == null) {
            return null;
        }
        return new ArrayList(list);
    }

    protected static <T> List<T> immutableCopyOf(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        if (list instanceof ImmutableList) {
            return list;
        }
        return Collections.unmodifiableList(new ArrayList(list));
    }

    public static <E extends Enum & ProtoEnum> int intFromEnum(E e) {
        WIRE.c(e.getClass());
        return EnumAdapter.a((ProtoEnum) e);
    }

    public static <E extends Enum & ProtoEnum> E enumFromInt(Class<E> cls, int i) {
        return (Enum) WIRE.c(cls).a(i);
    }

    public byte[] toByteArray() {
        checkAvailability();
        return WIRE.a(getClass()).b(this);
    }

    public void writeTo(byte[] bArr) {
        checkAvailability();
        writeTo(bArr, 0, bArr.length);
    }

    public void writeTo(byte[] bArr, int i, int i2) {
        checkAvailability();
        write(WireOutput.a(bArr, i, i2));
    }

    private void write(WireOutput wireOutput) {
        try {
            WIRE.a(getClass()).a(this, wireOutput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeUnknownFieldMap(WireOutput wireOutput) throws IOException {
        if (this.unknownFields != null) {
            UnknownFieldMap unknownFieldMap = this.unknownFields;
            if (unknownFieldMap.a != null) {
                for (Entry next : unknownFieldMap.a.entrySet()) {
                    int intValue = ((Integer) next.getKey()).intValue();
                    for (FieldValue a : (List) next.getValue()) {
                        a.a(intValue, wireOutput);
                    }
                }
            }
        }
    }

    public int getSerializedSize() {
        if (!this.haveCachedSerializedSize) {
            this.cachedSerializedSize = WIRE.a(getClass()).a(this);
            this.haveCachedSerializedSize = true;
        }
        return this.cachedSerializedSize;
    }

    public int getUnknownFieldsSerializedSize() {
        int i = 0;
        if (this.unknownFields == null) {
            return 0;
        }
        UnknownFieldMap unknownFieldMap = this.unknownFields;
        if (unknownFieldMap.a != null) {
            for (Entry next : unknownFieldMap.a.entrySet()) {
                for (FieldValue a : (List) next.getValue()) {
                    i = i + WireOutput.b(((Integer) next.getKey()).intValue()) + a.a();
                }
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    /* access modifiers changed from: protected */
    public boolean equals(List<?> list, List<?> list2) {
        if (list != null && list.isEmpty()) {
            list = null;
        }
        if (list2 != null && list2.isEmpty()) {
            list2 = null;
        }
        return list == list2 || (list != null && list.equals(list2));
    }

    public String toString() {
        return WIRE.a(getClass()).c(this);
    }

    public void addVarint(int i, long j) {
        try {
            ensureUnknownFieldMap().a(i, Long.valueOf(j));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addFixed32(int i, int i2) {
        try {
            ensureUnknownFieldMap().a(i, Integer.valueOf(i2));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addFixed64(int i, long j) {
        try {
            ensureUnknownFieldMap().b(i, Long.valueOf(j));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addLengthDelimited(int i, ByteString byteString) {
        try {
            ensureUnknownFieldMap().a(i, byteString);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /* access modifiers changed from: 0000 */
    public UnknownFieldMap ensureUnknownFieldMap() {
        if (this.unknownFields == null) {
            this.unknownFields = new UnknownFieldMap();
        }
        return this.unknownFields;
    }

    public void checkAvailability() {
        AvailabilityChecker<?> b = WIRE.b(getClass());
        b.a(this);
        b.b(this);
    }
}
