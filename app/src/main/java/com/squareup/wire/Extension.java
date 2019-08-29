package com.squareup.wire;

import com.squareup.wire.ExtendableMessage;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import java.util.List;
import okio.ByteString;

public final class Extension<T extends ExtendableMessage<?>, E> implements Comparable<Extension<?, ?>> {
    private final Datatype datatype;
    private final Class<? extends ProtoEnum> enumType;
    private final Class<T> extendedType;
    private final Label label;
    private final Class<? extends Message> messageType;
    private final String name;
    private final int tag;

    public static final class Builder<T extends ExtendableMessage<?>, E> {
        private final Datatype datatype;
        private final Class<? extends ProtoEnum> enumType;
        private final Class<T> extendedType;
        private Label label;
        private final Class<? extends Message> messageType;
        private String name;
        private int tag;

        private Builder(Class<T> cls, Datatype datatype2) {
            this.name = null;
            this.tag = -1;
            this.label = null;
            this.extendedType = cls;
            this.messageType = null;
            this.enumType = null;
            this.datatype = datatype2;
        }

        private Builder(Class<T> cls, Class<? extends Message> cls2, Class<? extends ProtoEnum> cls3, Datatype datatype2) {
            this.name = null;
            this.tag = -1;
            this.label = null;
            this.extendedType = cls;
            this.messageType = cls2;
            this.enumType = cls3;
            this.datatype = datatype2;
        }

        public final Builder<T, E> setName(String str) {
            this.name = str;
            return this;
        }

        public final Builder<T, E> setTag(int i) {
            this.tag = i;
            return this;
        }

        public final Extension<T, E> buildOptional() {
            this.label = Label.OPTIONAL;
            validate();
            Extension extension = new Extension(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
            return extension;
        }

        public final Extension<T, E> buildRequired() {
            this.label = Label.REQUIRED;
            validate();
            Extension extension = new Extension(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
            return extension;
        }

        public final Extension<T, List<E>> buildRepeated() {
            this.label = Label.REPEATED;
            validate();
            Extension extension = new Extension(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
            return extension;
        }

        public final Extension<T, List<E>> buildPacked() {
            this.label = Label.PACKED;
            validate();
            Extension extension = new Extension(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
            return extension;
        }

        private void validate() {
            if (this.extendedType == null) {
                throw new IllegalArgumentException("extendedType == null");
            } else if (this.name == null) {
                throw new IllegalArgumentException("name == null");
            } else if (this.datatype == null) {
                throw new IllegalArgumentException("datatype == null");
            } else if (this.label == null) {
                throw new IllegalArgumentException("label == null");
            } else if (this.tag <= 0) {
                StringBuilder sb = new StringBuilder("tag == ");
                sb.append(this.tag);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.datatype == Datatype.MESSAGE) {
                if (this.messageType == null || this.enumType != null) {
                    throw new IllegalStateException("Message w/o messageType or w/ enumType");
                }
            } else if (this.datatype == Datatype.ENUM) {
                if (this.messageType != null || this.enumType == null) {
                    throw new IllegalStateException("Enum w/ messageType or w/o enumType");
                }
            } else if (this.messageType != null || this.enumType != null) {
                throw new IllegalStateException("Scalar w/ messageType or enumType");
            }
        }
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> int32Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.INT32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> sint32Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.SINT32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> uint32Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.UINT32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> fixed32Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.FIXED32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> sfixed32Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.SFIXED32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> int64Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.INT64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> sint64Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.SINT64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> uint64Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.UINT64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> fixed64Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.FIXED64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> sfixed64Extending(Class<T> cls) {
        return new Builder<>(cls, Datatype.SFIXED64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Boolean> boolExtending(Class<T> cls) {
        return new Builder<>(cls, Datatype.BOOL);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, String> stringExtending(Class<T> cls) {
        return new Builder<>(cls, Datatype.STRING);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, ByteString> bytesExtending(Class<T> cls) {
        return new Builder<>(cls, Datatype.BYTES);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Float> floatExtending(Class<T> cls) {
        return new Builder<>(cls, Datatype.FLOAT);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Double> doubleExtending(Class<T> cls) {
        return new Builder<>(cls, Datatype.DOUBLE);
    }

    public static <T extends ExtendableMessage<?>, E extends Enum & ProtoEnum> Builder<T, E> enumExtending(Class<E> cls, Class<T> cls2) {
        Builder builder = new Builder(cls2, null, cls, Datatype.ENUM);
        return builder;
    }

    public static <T extends ExtendableMessage<?>, M extends Message> Builder<T, M> messageExtending(Class<M> cls, Class<T> cls2) {
        Builder builder = new Builder(cls2, cls, null, Datatype.MESSAGE);
        return builder;
    }

    private Extension(Class<T> cls, Class<? extends Message> cls2, Class<? extends ProtoEnum> cls3, String str, int i, Label label2, Datatype datatype2) {
        this.extendedType = cls;
        this.name = str;
        this.tag = i;
        this.datatype = datatype2;
        this.label = label2;
        this.messageType = cls2;
        this.enumType = cls3;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.squareup.wire.Extension<?, ?>, code=com.squareup.wire.Extension, for r4v0, types: [com.squareup.wire.Extension<?, ?>, com.squareup.wire.Extension] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int compareTo(com.squareup.wire.Extension r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != r3) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = r3.tag
            int r2 = r4.tag
            if (r1 == r2) goto L_0x0010
            int r0 = r3.tag
            int r4 = r4.tag
            int r0 = r0 - r4
            return r0
        L_0x0010:
            com.squareup.wire.Message$Datatype r1 = r3.datatype
            com.squareup.wire.Message$Datatype r2 = r4.datatype
            if (r1 == r2) goto L_0x0024
            com.squareup.wire.Message$Datatype r0 = r3.datatype
            int r0 = r0.value()
            com.squareup.wire.Message$Datatype r4 = r4.datatype
            int r4 = r4.value()
            int r0 = r0 - r4
            return r0
        L_0x0024:
            com.squareup.wire.Message$Label r1 = r3.label
            com.squareup.wire.Message$Label r2 = r4.label
            if (r1 == r2) goto L_0x0038
            com.squareup.wire.Message$Label r0 = r3.label
            int r0 = r0.value()
            com.squareup.wire.Message$Label r4 = r4.label
            int r4 = r4.value()
            int r0 = r0 - r4
            return r0
        L_0x0038:
            java.lang.Class<T> r1 = r3.extendedType
            if (r1 == 0) goto L_0x0057
            java.lang.Class<T> r1 = r3.extendedType
            java.lang.Class<T> r2 = r4.extendedType
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0057
            java.lang.Class<T> r0 = r3.extendedType
            java.lang.String r0 = r0.getName()
            java.lang.Class<T> r4 = r4.extendedType
            java.lang.String r4 = r4.getName()
            int r4 = r0.compareTo(r4)
            return r4
        L_0x0057:
            java.lang.Class<? extends com.squareup.wire.Message> r1 = r3.messageType
            if (r1 == 0) goto L_0x0076
            java.lang.Class<? extends com.squareup.wire.Message> r1 = r3.messageType
            java.lang.Class<? extends com.squareup.wire.Message> r2 = r4.messageType
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0076
            java.lang.Class<? extends com.squareup.wire.Message> r0 = r3.messageType
            java.lang.String r0 = r0.getName()
            java.lang.Class<? extends com.squareup.wire.Message> r4 = r4.messageType
            java.lang.String r4 = r4.getName()
            int r4 = r0.compareTo(r4)
            return r4
        L_0x0076:
            java.lang.Class<? extends com.squareup.wire.ProtoEnum> r1 = r3.enumType
            if (r1 == 0) goto L_0x0095
            java.lang.Class<? extends com.squareup.wire.ProtoEnum> r1 = r3.enumType
            java.lang.Class<? extends com.squareup.wire.ProtoEnum> r2 = r4.enumType
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0095
            java.lang.Class<? extends com.squareup.wire.ProtoEnum> r0 = r3.enumType
            java.lang.String r0 = r0.getName()
            java.lang.Class<? extends com.squareup.wire.ProtoEnum> r4 = r4.enumType
            java.lang.String r4 = r4.getName()
            int r4 = r0.compareTo(r4)
            return r4
        L_0x0095:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.wire.Extension.compareTo(com.squareup.wire.Extension):int");
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Extension) && compareTo((Extension) obj) == 0;
    }

    public final int hashCode() {
        int i = 0;
        int value = ((((((((this.tag * 37) + this.datatype.value()) * 37) + this.label.value()) * 37) + this.extendedType.hashCode()) * 37) + (this.messageType != null ? this.messageType.hashCode() : 0)) * 37;
        if (this.enumType != null) {
            i = this.enumType.hashCode();
        }
        return value + i;
    }

    public final String toString() {
        return String.format("[%s %s %s = %d]", new Object[]{this.label, this.datatype, this.name, Integer.valueOf(this.tag)});
    }

    public final Class<T> getExtendedType() {
        return this.extendedType;
    }

    public final Class<? extends Message> getMessageType() {
        return this.messageType;
    }

    public final Class<? extends ProtoEnum> getEnumType() {
        return this.enumType;
    }

    public final String getName() {
        return this.name;
    }

    public final int getTag() {
        return this.tag;
    }

    public final Datatype getDatatype() {
        return this.datatype;
    }

    public final Label getLabel() {
        return this.label;
    }
}
