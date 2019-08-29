package com.squareup.wire;

import com.alipay.sdk.util.h;
import com.squareup.wire.Message;
import com.squareup.wire.Message.Builder;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import okio.ByteString;

final class MessageAdapter<M extends Message> {
    private static final String FULL_BLOCK = "█";
    private static final String REDACTED = "██";
    private final TagMap<FieldInfo> fieldInfoMap;
    private final Class<M> messageType;
    private final Map<String, Integer> tagMap = new LinkedHashMap();
    private final Wire wire;

    public static final class FieldInfo {
        /* access modifiers changed from: private */
        public final Field builderField;
        final Datatype datatype;
        EnumAdapter<? extends ProtoEnum> enumAdapter;
        final Class<? extends ProtoEnum> enumType;
        final Label label;
        MessageAdapter<? extends Message> messageAdapter;
        /* access modifiers changed from: private */
        public final Field messageField;
        final Class<? extends Message> messageType;
        final String name;
        final boolean redacted;
        final int tag;

        private FieldInfo(int i, String str, Datatype datatype2, Label label2, boolean z, Class<?> cls, Field field, Field field2) {
            this.tag = i;
            this.name = str;
            this.datatype = datatype2;
            this.label = label2;
            this.redacted = z;
            if (datatype2 == Datatype.ENUM) {
                this.enumType = cls;
                this.messageType = null;
            } else if (datatype2 == Datatype.MESSAGE) {
                this.messageType = cls;
                this.enumType = null;
            } else {
                this.enumType = null;
                this.messageType = null;
            }
            this.messageField = field;
            this.builderField = field2;
        }
    }

    static class ImmutableList<T> extends AbstractList<T> implements Serializable, Cloneable, RandomAccess {
        /* access modifiers changed from: private */
        public final List<T> list = new ArrayList();

        public Object clone() {
            return this;
        }

        ImmutableList() {
        }

        public int size() {
            return this.list.size();
        }

        public T get(int i) {
            return this.list.get(i);
        }
    }

    static class Storage {
        private Map<Integer, ImmutableList<Object>> map;

        private Storage() {
        }

        /* access modifiers changed from: 0000 */
        public void add(int i, Object obj) {
            ImmutableList immutableList = this.map == null ? null : this.map.get(Integer.valueOf(i));
            if (immutableList == null) {
                immutableList = new ImmutableList();
                if (this.map == null) {
                    this.map = new LinkedHashMap();
                }
                this.map.put(Integer.valueOf(i), immutableList);
            }
            immutableList.list.add(obj);
        }

        /* access modifiers changed from: 0000 */
        public Set<Integer> getTags() {
            if (this.map == null) {
                return Collections.emptySet();
            }
            return this.map.keySet();
        }

        /* access modifiers changed from: 0000 */
        public List<Object> get(int i) {
            if (this.map == null) {
                return null;
            }
            return this.map.get(Integer.valueOf(i));
        }
    }

    /* access modifiers changed from: 0000 */
    public final Collection<FieldInfo> getFields() {
        return this.fieldInfoMap.values();
    }

    /* access modifiers changed from: 0000 */
    public final FieldInfo getField(String str) {
        Integer num = this.tagMap.get(str);
        if (num == null) {
            return null;
        }
        return (FieldInfo) this.fieldInfoMap.get(num.intValue());
    }

    /* access modifiers changed from: 0000 */
    public final Object getFieldValue(M m, FieldInfo fieldInfo) {
        if (fieldInfo.messageField == null) {
            throw new AssertionError("Field is not of type \"Message\"");
        }
        try {
            return fieldInfo.messageField.get(m);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    public final void setBuilderField(M m, int i, Object obj) {
        try {
            ((FieldInfo) this.fieldInfoMap.get(i)).builderField.set(m, obj);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0051, code lost:
        r8 = getMessageType(r12);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    MessageAdapter(com.squareup.wire.Wire r20, java.lang.Class<M> r21) {
        /*
            r19 = this;
            r0 = r19
            r19.<init>()
            java.util.LinkedHashMap r1 = new java.util.LinkedHashMap
            r1.<init>()
            r0.tagMap = r1
            r1 = r20
            r0.wire = r1
            r1 = r21
            r0.messageType = r1
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            java.lang.reflect.Field[] r1 = r21.getDeclaredFields()
            int r3 = r1.length
            r4 = 0
        L_0x001f:
            if (r4 >= r3) goto L_0x0083
            r12 = r1[r4]
            java.lang.Class<com.squareup.wire.ProtoField> r5 = com.squareup.wire.ProtoField.class
            java.lang.annotation.Annotation r5 = r12.getAnnotation(r5)
            com.squareup.wire.ProtoField r5 = (com.squareup.wire.ProtoField) r5
            if (r5 == 0) goto L_0x007c
            int r6 = r5.tag()
            java.lang.String r7 = r12.getName()
            java.util.Map<java.lang.String, java.lang.Integer> r8 = r0.tagMap
            java.lang.Integer r9 = java.lang.Integer.valueOf(r6)
            r8.put(r7, r9)
            r8 = 0
            com.squareup.wire.Message$Datatype r9 = r5.type()
            com.squareup.wire.Message$Datatype r10 = com.squareup.wire.Message.Datatype.ENUM
            if (r9 != r10) goto L_0x004d
            java.lang.Class r8 = r0.getEnumType(r12)
        L_0x004b:
            r11 = r8
            goto L_0x0056
        L_0x004d:
            com.squareup.wire.Message$Datatype r10 = com.squareup.wire.Message.Datatype.MESSAGE
            if (r9 != r10) goto L_0x004b
            java.lang.Class r8 = r0.getMessageType(r12)
            goto L_0x004b
        L_0x0056:
            java.lang.Integer r15 = java.lang.Integer.valueOf(r6)
            com.squareup.wire.MessageAdapter$FieldInfo r14 = new com.squareup.wire.MessageAdapter$FieldInfo
            com.squareup.wire.Message$Label r10 = r5.label()
            boolean r13 = r5.redacted()
            java.lang.reflect.Field r16 = r0.getBuilderField(r7)
            r17 = 0
            r5 = r14
            r8 = r9
            r9 = r10
            r10 = r13
            r13 = r16
            r18 = r1
            r1 = r14
            r14 = r17
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13)
            r2.put(r15, r1)
            goto L_0x007e
        L_0x007c:
            r18 = r1
        L_0x007e:
            int r4 = r4 + 1
            r1 = r18
            goto L_0x001f
        L_0x0083:
            com.squareup.wire.TagMap r1 = com.squareup.wire.TagMap.of(r2)
            r0.fieldInfoMap = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.wire.MessageAdapter.<init>(com.squareup.wire.Wire, java.lang.Class):void");
    }

    private Class<Builder<M>> getBuilderType(Class<M> cls) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(cls.getName());
            sb.append("$Builder");
            return Class.forName(sb.toString(), true, cls.getClassLoader());
        } catch (ClassNotFoundException unused) {
            StringBuilder sb2 = new StringBuilder("No builder class found for message type ");
            sb2.append(cls.getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    private Field getBuilderField(String str) {
        try {
            return this.messageType.getField(str);
        } catch (NoSuchFieldException unused) {
            StringBuilder sb = new StringBuilder("No builder field ");
            sb.append(this.messageType.getName());
            sb.append(".");
            sb.append(str);
            throw new AssertionError(sb.toString());
        }
    }

    private Class<Message> getMessageType(Field field) {
        Class<?> type = field.getType();
        if (Message.class.isAssignableFrom(type)) {
            return type;
        }
        if (List.class.isAssignableFrom(type)) {
            Type type2 = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            if (type2 instanceof Class) {
                Class<Message> cls = (Class) type2;
                if (Message.class.isAssignableFrom(cls)) {
                    return cls;
                }
            }
        }
        return null;
    }

    private Class<Enum> getEnumType(Field field) {
        Class<?> type = field.getType();
        if (Enum.class.isAssignableFrom(type)) {
            return type;
        }
        if (List.class.isAssignableFrom(type)) {
            Type type2 = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            if (type2 instanceof Class) {
                Class<Enum> cls = (Class) type2;
                if (Enum.class.isAssignableFrom(cls)) {
                    return cls;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final int getSerializedSize(M m) {
        int i = 0;
        for (FieldInfo next : getFields()) {
            Object fieldValue = getFieldValue(m, next);
            if (fieldValue != null) {
                int i2 = next.tag;
                Datatype datatype = next.datatype;
                Label label = next.label;
                if (!label.isRepeated()) {
                    i += getSerializedSize(i2, fieldValue, datatype);
                } else if (label.isPacked()) {
                    i += getPackedSize((List) fieldValue, i2, datatype);
                } else {
                    i += getRepeatedSize((List) fieldValue, i2, datatype);
                }
            }
        }
        if (m instanceof ExtendableMessage) {
            ExtendableMessage extendableMessage = (ExtendableMessage) m;
            if (extendableMessage.extensionMap != null) {
                i += getExtensionsSerializedSize(extendableMessage.extensionMap);
            }
        }
        return i + m.getUnknownFieldsSerializedSize();
    }

    private <T extends ExtendableMessage<?>> int getExtensionsSerializedSize(ExtensionMap<T> extensionMap) {
        int i;
        int i2 = 0;
        for (int i3 = 0; i3 < extensionMap.size(); i3++) {
            Extension extension = extensionMap.getExtension(i3);
            Object extensionValue = extensionMap.getExtensionValue(i3);
            int tag = extension.getTag();
            Datatype datatype = extension.getDatatype();
            Label label = extension.getLabel();
            if (!label.isRepeated()) {
                i = getSerializedSize(tag, extensionValue, datatype);
            } else if (label.isPacked()) {
                i = getPackedSize((List) extensionValue, tag, datatype);
            } else {
                i = getRepeatedSize((List) extensionValue, tag, datatype);
            }
            i2 += i;
        }
        return i2;
    }

    private int getRepeatedSize(List<?> list, int i, Datatype datatype) {
        int i2 = 0;
        for (Object serializedSize : list) {
            i2 += getSerializedSize(i, serializedSize, datatype);
        }
        return i2;
    }

    private int getPackedSize(List<?> list, int i, Datatype datatype) {
        int i2 = 0;
        for (Object serializedSizeNoTag : list) {
            i2 += getSerializedSizeNoTag(serializedSizeNoTag, datatype);
        }
        return WireOutput.varint32Size(WireOutput.makeTag(i, WireType.LENGTH_DELIMITED)) + WireOutput.varint32Size(i2) + i2;
    }

    /* access modifiers changed from: 0000 */
    public final void write(M m, WireOutput wireOutput) throws IOException {
        for (FieldInfo next : getFields()) {
            Object fieldValue = getFieldValue(m, next);
            if (fieldValue != null) {
                int i = next.tag;
                Datatype datatype = next.datatype;
                Label label = next.label;
                if (!label.isRepeated()) {
                    writeValue(wireOutput, i, fieldValue, datatype);
                } else if (label.isPacked()) {
                    writePacked(wireOutput, (List) fieldValue, i, datatype);
                } else {
                    writeRepeated(wireOutput, (List) fieldValue, i, datatype);
                }
            }
        }
        if (m instanceof ExtendableMessage) {
            ExtendableMessage extendableMessage = (ExtendableMessage) m;
            if (extendableMessage.extensionMap != null) {
                writeExtensions(wireOutput, extendableMessage.extensionMap);
            }
        }
        m.writeUnknownFieldMap(wireOutput);
    }

    private <T extends ExtendableMessage<?>> void writeExtensions(WireOutput wireOutput, ExtensionMap<T> extensionMap) throws IOException {
        for (int i = 0; i < extensionMap.size(); i++) {
            Extension extension = extensionMap.getExtension(i);
            Object extensionValue = extensionMap.getExtensionValue(i);
            int tag = extension.getTag();
            Datatype datatype = extension.getDatatype();
            Label label = extension.getLabel();
            if (!label.isRepeated()) {
                writeValue(wireOutput, tag, extensionValue, datatype);
            } else if (label.isPacked()) {
                writePacked(wireOutput, (List) extensionValue, tag, datatype);
            } else {
                writeRepeated(wireOutput, (List) extensionValue, tag, datatype);
            }
        }
    }

    private void writeRepeated(WireOutput wireOutput, List<?> list, int i, Datatype datatype) throws IOException {
        for (Object writeValue : list) {
            writeValue(wireOutput, i, writeValue, datatype);
        }
    }

    private void writePacked(WireOutput wireOutput, List<?> list, int i, Datatype datatype) throws IOException {
        int i2 = 0;
        for (Object serializedSizeNoTag : list) {
            i2 += getSerializedSizeNoTag(serializedSizeNoTag, datatype);
        }
        wireOutput.writeTag(i, WireType.LENGTH_DELIMITED);
        wireOutput.writeVarint32(i2);
        for (Object writeValueNoTag : list) {
            writeValueNoTag(wireOutput, writeValueNoTag, datatype);
        }
    }

    /* access modifiers changed from: 0000 */
    public final byte[] toByteArray(M m) {
        byte[] bArr = new byte[getSerializedSize(m)];
        try {
            write(m, WireOutput.newInstance(bArr));
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final String toString(M m) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.messageType.getSimpleName());
        sb.append("{");
        String str = "";
        for (FieldInfo next : getFields()) {
            Object fieldValue = getFieldValue(m, next);
            if (fieldValue != null) {
                sb.append(str);
                str = ", ";
                sb.append(next.name);
                sb.append("=");
                if (next.redacted) {
                    fieldValue = REDACTED;
                }
                sb.append(fieldValue);
            }
        }
        if (m instanceof ExtendableMessage) {
            sb.append(str);
            sb.append("{extensions=");
            sb.append(((ExtendableMessage) m).extensionsToString());
            sb.append(h.d);
        }
        sb.append(h.d);
        return sb.toString();
    }

    private int getSerializedSize(int i, Object obj, Datatype datatype) {
        return WireOutput.varintTagSize(i) + getSerializedSizeNoTag(obj, datatype);
    }

    private int getSerializedSizeNoTag(Object obj, Datatype datatype) {
        switch (datatype) {
            case INT32:
                return WireOutput.int32Size(((Integer) obj).intValue());
            case INT64:
            case UINT64:
                return WireOutput.varint64Size(((Long) obj).longValue());
            case UINT32:
                return WireOutput.varint32Size(((Integer) obj).intValue());
            case SINT32:
                return WireOutput.varint32Size(WireOutput.zigZag32(((Integer) obj).intValue()));
            case SINT64:
                return WireOutput.varint64Size(WireOutput.zigZag64(((Long) obj).longValue()));
            case BOOL:
                return 1;
            case ENUM:
                return getEnumSize((ProtoEnum) obj);
            case STRING:
                int utf8Length = utf8Length((String) obj);
                return WireOutput.varint32Size(utf8Length) + utf8Length;
            case BYTES:
                int size = ((ByteString) obj).size();
                return WireOutput.varint32Size(size) + size;
            case MESSAGE:
                return getMessageSize((Message) obj);
            case FIXED32:
            case SFIXED32:
            case FLOAT:
                return 4;
            case FIXED64:
            case SFIXED64:
            case DOUBLE:
                return 8;
            default:
                throw new RuntimeException();
        }
    }

    private int utf8Length(String str) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt <= 127) {
                i2++;
            } else if (charAt <= 2047) {
                i2 += 2;
            } else if (Character.isHighSurrogate(charAt)) {
                i2 += 4;
                i++;
            } else {
                i2 += 3;
            }
            i++;
        }
        return i2;
    }

    private <E extends ProtoEnum> int getEnumSize(E e) {
        return WireOutput.varint32Size(this.wire.enumAdapter(e.getClass()).toInt(e));
    }

    private <M extends Message> int getMessageSize(M m) {
        int serializedSize = m.getSerializedSize();
        return WireOutput.varint32Size(serializedSize) + serializedSize;
    }

    private void writeValue(WireOutput wireOutput, int i, Object obj, Datatype datatype) throws IOException {
        wireOutput.writeTag(i, datatype.wireType());
        writeValueNoTag(wireOutput, obj, datatype);
    }

    private void writeValueNoTag(WireOutput wireOutput, Object obj, Datatype datatype) throws IOException {
        switch (datatype) {
            case INT32:
                wireOutput.writeSignedVarint32(((Integer) obj).intValue());
                return;
            case INT64:
            case UINT64:
                wireOutput.writeVarint64(((Long) obj).longValue());
                return;
            case UINT32:
                wireOutput.writeVarint32(((Integer) obj).intValue());
                return;
            case SINT32:
                wireOutput.writeVarint32(WireOutput.zigZag32(((Integer) obj).intValue()));
                return;
            case SINT64:
                wireOutput.writeVarint64(WireOutput.zigZag64(((Long) obj).longValue()));
                return;
            case BOOL:
                wireOutput.writeRawByte(((Boolean) obj).booleanValue() ? 1 : 0);
                return;
            case ENUM:
                writeEnum((ProtoEnum) obj, wireOutput);
                return;
            case STRING:
                byte[] bytes = ((String) obj).getBytes("UTF-8");
                wireOutput.writeVarint32(bytes.length);
                wireOutput.writeRawBytes(bytes);
                return;
            case BYTES:
                ByteString byteString = (ByteString) obj;
                wireOutput.writeVarint32(byteString.size());
                wireOutput.writeRawBytes(byteString.toByteArray());
                return;
            case MESSAGE:
                writeMessage((Message) obj, wireOutput);
                return;
            case FIXED32:
            case SFIXED32:
                wireOutput.writeFixed32(((Integer) obj).intValue());
                return;
            case FLOAT:
                wireOutput.writeFixed32(Float.floatToIntBits(((Float) obj).floatValue()));
                return;
            case FIXED64:
            case SFIXED64:
                wireOutput.writeFixed64(((Long) obj).longValue());
                return;
            case DOUBLE:
                wireOutput.writeFixed64(Double.doubleToLongBits(((Double) obj).doubleValue()));
                return;
            default:
                throw new RuntimeException();
        }
    }

    private <M extends Message> void writeMessage(M m, WireOutput wireOutput) throws IOException {
        wireOutput.writeVarint32(m.getSerializedSize());
        this.wire.messageAdapter(m.getClass()).write(m, wireOutput);
    }

    private <E extends ProtoEnum> void writeEnum(E e, WireOutput wireOutput) throws IOException {
        wireOutput.writeVarint32(this.wire.enumAdapter(e.getClass()).toInt(e));
    }

    /* access modifiers changed from: 0000 */
    public final M read(WireInput wireInput) throws IOException {
        Extension extension;
        Datatype datatype;
        Label label;
        long j;
        try {
            M m = (Message) this.messageType.newInstance();
            Storage storage = new Storage();
            while (true) {
                int readTag = wireInput.readTag();
                int i = readTag >> 3;
                WireType valueOf = WireType.valueOf(readTag);
                if (i == 0) {
                    for (Integer intValue : storage.getTags()) {
                        int intValue2 = intValue.intValue();
                        if (this.fieldInfoMap.containsKey(intValue2)) {
                            setBuilderField(m, intValue2, storage.get(intValue2));
                        } else {
                            setExtension((ExtendableMessage) m, getExtension(intValue2), storage.get(intValue2));
                        }
                    }
                    return m;
                }
                FieldInfo fieldInfo = (FieldInfo) this.fieldInfoMap.get(i);
                if (fieldInfo != null) {
                    datatype = fieldInfo.datatype;
                    label = fieldInfo.label;
                    extension = null;
                } else {
                    Extension<ExtendableMessage<?>, ?> extension2 = getExtension(i);
                    if (extension2 == null) {
                        readUnknownField(m, wireInput, i, valueOf);
                    } else {
                        datatype = extension2.getDatatype();
                        extension = extension2;
                        label = extension2.getLabel();
                    }
                }
                if (!label.isPacked() || valueOf != WireType.LENGTH_DELIMITED) {
                    Object readValue = readValue(wireInput, i, datatype);
                    if (datatype == Datatype.ENUM && (readValue instanceof Integer)) {
                        m.addVarint(i, (long) ((Integer) readValue).intValue());
                    } else if (label.isRepeated()) {
                        storage.add(i, readValue);
                    } else if (extension != null) {
                        setExtension((ExtendableMessage) m, extension, readValue);
                    } else {
                        setBuilderField(m, i, readValue);
                    }
                } else {
                    int readVarint32 = wireInput.readVarint32();
                    long position = wireInput.getPosition();
                    int pushLimit = wireInput.pushLimit(readVarint32);
                    while (true) {
                        j = ((long) readVarint32) + position;
                        if (wireInput.getPosition() >= j) {
                            break;
                        }
                        Object readValue2 = readValue(wireInput, i, datatype);
                        if (datatype != Datatype.ENUM || !(readValue2 instanceof Integer)) {
                            storage.add(i, readValue2);
                        } else {
                            m.addVarint(i, (long) ((Integer) readValue2).intValue());
                        }
                    }
                    wireInput.popLimit(pushLimit);
                    if (wireInput.getPosition() != j) {
                        throw new IOException("Packed data had wrong length!");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        }
    }

    private Object readValue(WireInput wireInput, int i, Datatype datatype) throws IOException {
        switch (datatype) {
            case INT32:
            case UINT32:
                return Integer.valueOf(wireInput.readVarint32());
            case INT64:
            case UINT64:
                return Long.valueOf(wireInput.readVarint64());
            case SINT32:
                return Integer.valueOf(WireInput.decodeZigZag32(wireInput.readVarint32()));
            case SINT64:
                return Long.valueOf(WireInput.decodeZigZag64(wireInput.readVarint64()));
            case BOOL:
                return Boolean.valueOf(wireInput.readVarint32() != 0);
            case ENUM:
                EnumAdapter<? extends ProtoEnum> enumAdapter = getEnumAdapter(i);
                int readVarint32 = wireInput.readVarint32();
                try {
                    return enumAdapter.fromInt(readVarint32);
                } catch (IllegalArgumentException unused) {
                    return Integer.valueOf(readVarint32);
                }
            case STRING:
                return wireInput.readString();
            case BYTES:
                return wireInput.readBytes();
            case MESSAGE:
                return readMessage(wireInput, i);
            case FIXED32:
            case SFIXED32:
                return Integer.valueOf(wireInput.readFixed32());
            case FLOAT:
                return Float.valueOf(Float.intBitsToFloat(wireInput.readFixed32()));
            case FIXED64:
            case SFIXED64:
                return Long.valueOf(wireInput.readFixed64());
            case DOUBLE:
                return Double.valueOf(Double.longBitsToDouble(wireInput.readFixed64()));
            default:
                throw new RuntimeException();
        }
    }

    private Message readMessage(WireInput wireInput, int i) throws IOException {
        int readVarint32 = wireInput.readVarint32();
        if (wireInput.recursionDepth >= 64) {
            throw new IOException("Wire recursion limit exceeded");
        }
        int pushLimit = wireInput.pushLimit(readVarint32);
        wireInput.recursionDepth++;
        Message read = getMessageAdapter(i).read(wireInput);
        wireInput.checkLastTagWas(0);
        wireInput.recursionDepth--;
        wireInput.popLimit(pushLimit);
        return read;
    }

    private MessageAdapter<? extends Message> getMessageAdapter(int i) {
        FieldInfo fieldInfo = (FieldInfo) this.fieldInfoMap.get(i);
        if (fieldInfo != null && fieldInfo.messageAdapter != null) {
            return fieldInfo.messageAdapter;
        }
        MessageAdapter<? extends Message> messageAdapter = this.wire.messageAdapter(getMessageClass(i));
        if (fieldInfo != null) {
            fieldInfo.messageAdapter = messageAdapter;
        }
        return messageAdapter;
    }

    private EnumAdapter<? extends ProtoEnum> getEnumAdapter(int i) {
        FieldInfo fieldInfo = (FieldInfo) this.fieldInfoMap.get(i);
        if (fieldInfo != null && fieldInfo.enumAdapter != null) {
            return fieldInfo.enumAdapter;
        }
        EnumAdapter<? extends ProtoEnum> enumAdapter = this.wire.enumAdapter(getEnumClass(i));
        if (fieldInfo != null) {
            fieldInfo.enumAdapter = enumAdapter;
        }
        return enumAdapter;
    }

    private Class<Message> getMessageClass(int i) {
        Class<? extends Message> cls;
        FieldInfo fieldInfo = (FieldInfo) this.fieldInfoMap.get(i);
        if (fieldInfo == null) {
            cls = null;
        } else {
            cls = fieldInfo.messageType;
        }
        if (cls != null) {
            return cls;
        }
        Extension<ExtendableMessage<?>, ?> extension = getExtension(i);
        return extension != null ? extension.getMessageType() : cls;
    }

    private void readUnknownField(Message message, WireInput wireInput, int i, WireType wireType) throws IOException {
        switch (wireType) {
            case VARINT:
                message.ensureUnknownFieldMap().addVarint(i, Long.valueOf(wireInput.readVarint64()));
                return;
            case FIXED32:
                message.ensureUnknownFieldMap().addFixed32(i, Integer.valueOf(wireInput.readFixed32()));
                return;
            case FIXED64:
                message.ensureUnknownFieldMap().addFixed64(i, Long.valueOf(wireInput.readFixed64()));
                return;
            case LENGTH_DELIMITED:
                message.ensureUnknownFieldMap().addLengthDelimited(i, wireInput.readBytes(wireInput.readVarint32()));
                return;
            case START_GROUP:
                wireInput.skipGroup();
                return;
            case END_GROUP:
                return;
            default:
                throw new RuntimeException("Unsupported wire type: ".concat(String.valueOf(wireType)));
        }
    }

    private Extension<ExtendableMessage<?>, ?> getExtension(int i) {
        ExtensionRegistry extensionRegistry = this.wire.registry;
        if (extensionRegistry == null) {
            return null;
        }
        return extensionRegistry.getExtension(this.messageType, i);
    }

    /* access modifiers changed from: 0000 */
    public final Extension<ExtendableMessage<?>, ?> getExtension(String str) {
        ExtensionRegistry extensionRegistry = this.wire.registry;
        if (extensionRegistry == null) {
            return null;
        }
        return extensionRegistry.getExtension(this.messageType, str);
    }

    private void setExtension(ExtendableMessage extendableMessage, Extension<?, ?> extension, Object obj) {
        extendableMessage.setExtension(extension, obj);
    }

    private Class<? extends ProtoEnum> getEnumClass(int i) {
        Class<? extends ProtoEnum> cls;
        FieldInfo fieldInfo = (FieldInfo) this.fieldInfoMap.get(i);
        if (fieldInfo == null) {
            cls = null;
        } else {
            cls = fieldInfo.enumType;
        }
        if (cls != null) {
            return cls;
        }
        Extension<ExtendableMessage<?>, ?> extension = getExtension(i);
        return extension != null ? extension.getEnumType() : cls;
    }
}
