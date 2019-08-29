package com.alipay.android.phone.inside.protobuf.wire;

import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.sdk.util.h;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

final class MessageAdapter<M extends Message> {
    private final Wire a;
    private final Class<M> b;
    private final Map<String, Integer> c = new LinkedHashMap();
    private final TagMap<FieldInfo> d;

    public static final class FieldInfo {
        final int a;
        final String b;
        final Datatype c;
        final Label d;
        final Class<? extends ProtoEnum> e;
        final Class<? extends Message> f;
        final boolean g;
        MessageAdapter<? extends Message> h;
        EnumAdapter<? extends ProtoEnum> i;
        /* access modifiers changed from: private */
        public final Field j;
        /* access modifiers changed from: private */
        public final Field k;

        /* synthetic */ FieldInfo(int i2, String str, Datatype datatype, Label label, boolean z, Class cls, Field field, Field field2, byte b2) {
            this(i2, str, datatype, label, z, cls, field, field2);
        }

        private FieldInfo(int i2, String str, Datatype datatype, Label label, boolean z, Class<?> cls, Field field, Field field2) {
            this.a = i2;
            this.b = str;
            this.c = datatype;
            this.d = label;
            this.g = z;
            if (datatype == Datatype.ENUM) {
                this.e = cls;
                this.f = null;
            } else if (datatype == Datatype.MESSAGE) {
                this.f = cls;
                this.e = null;
            } else {
                this.e = null;
                this.f = null;
            }
            this.j = field;
            this.k = field2;
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
        private Map<Integer, ImmutableList<Object>> a;

        private Storage() {
        }

        /* synthetic */ Storage(byte b) {
            this();
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i, Object obj) {
            ImmutableList immutableList = this.a == null ? null : this.a.get(Integer.valueOf(i));
            if (immutableList == null) {
                immutableList = new ImmutableList();
                if (this.a == null) {
                    this.a = new LinkedHashMap();
                }
                this.a.put(Integer.valueOf(i), immutableList);
            }
            immutableList.list.add(obj);
        }

        /* access modifiers changed from: 0000 */
        public final Set<Integer> a() {
            if (this.a == null) {
                return Collections.emptySet();
            }
            return this.a.keySet();
        }

        /* access modifiers changed from: 0000 */
        public final List<Object> a(int i) {
            if (this.a == null) {
                return null;
            }
            return this.a.get(Integer.valueOf(i));
        }
    }

    private static Object a(M m, FieldInfo fieldInfo) {
        if (fieldInfo.j == null) {
            throw new AssertionError("Field is not of type \"Message\"");
        }
        try {
            return fieldInfo.j.get(m);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    private void a(M m, int i, Object obj) {
        try {
            ((FieldInfo) this.d.a(i)).k.set(m, obj);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x006d, code lost:
        r10 = (java.lang.Class) r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0075, code lost:
        if (java.lang.Enum.class.isAssignableFrom(r10) != false) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a4, code lost:
        r10 = (java.lang.Class) r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ac, code lost:
        if (com.alipay.android.phone.inside.protobuf.wire.Message.class.isAssignableFrom(r10) != false) goto L_0x0079;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    MessageAdapter(com.alipay.android.phone.inside.protobuf.wire.Wire r20, java.lang.Class<M> r21) {
        /*
            r19 = this;
            r0 = r19
            r19.<init>()
            java.util.LinkedHashMap r1 = new java.util.LinkedHashMap
            r1.<init>()
            r0.c = r1
            r1 = r20
            r0.a = r1
            r1 = r21
            r0.b = r1
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            java.lang.reflect.Field[] r1 = r21.getDeclaredFields()
            int r3 = r1.length
            r4 = 0
            r5 = 0
        L_0x0020:
            if (r5 >= r3) goto L_0x00dd
            r13 = r1[r5]
            java.lang.Class<com.alipay.android.phone.inside.protobuf.wire.ProtoField> r6 = com.alipay.android.phone.inside.protobuf.wire.ProtoField.class
            java.lang.annotation.Annotation r6 = r13.getAnnotation(r6)
            com.alipay.android.phone.inside.protobuf.wire.ProtoField r6 = (com.alipay.android.phone.inside.protobuf.wire.ProtoField) r6
            if (r6 == 0) goto L_0x00d4
            int r7 = r6.tag()
            java.lang.String r8 = r13.getName()
            java.util.Map<java.lang.String, java.lang.Integer> r9 = r0.c
            java.lang.Integer r10 = java.lang.Integer.valueOf(r7)
            r9.put(r8, r10)
            com.alipay.android.phone.inside.protobuf.wire.Message$Datatype r9 = r6.type()
            com.alipay.android.phone.inside.protobuf.wire.Message$Datatype r10 = com.alipay.android.phone.inside.protobuf.wire.Message.Datatype.ENUM
            r11 = 0
            if (r9 != r10) goto L_0x007b
            java.lang.Class r10 = r13.getType()
            java.lang.Class<java.lang.Enum> r12 = java.lang.Enum.class
            boolean r12 = r12.isAssignableFrom(r10)
            if (r12 == 0) goto L_0x0055
            goto L_0x0079
        L_0x0055:
            java.lang.Class<java.util.List> r12 = java.util.List.class
            boolean r10 = r12.isAssignableFrom(r10)
            if (r10 == 0) goto L_0x0078
            java.lang.reflect.Type r10 = r13.getGenericType()
            java.lang.reflect.ParameterizedType r10 = (java.lang.reflect.ParameterizedType) r10
            java.lang.reflect.Type[] r10 = r10.getActualTypeArguments()
            r10 = r10[r4]
            boolean r12 = r10 instanceof java.lang.Class
            if (r12 == 0) goto L_0x0078
            java.lang.Class<java.lang.Enum> r12 = java.lang.Enum.class
            java.lang.Class r10 = (java.lang.Class) r10
            boolean r12 = r12.isAssignableFrom(r10)
            if (r12 == 0) goto L_0x0078
            goto L_0x0079
        L_0x0078:
            r10 = r11
        L_0x0079:
            r12 = r10
            goto L_0x00b0
        L_0x007b:
            com.alipay.android.phone.inside.protobuf.wire.Message$Datatype r10 = com.alipay.android.phone.inside.protobuf.wire.Message.Datatype.MESSAGE
            if (r9 != r10) goto L_0x00af
            java.lang.Class r10 = r13.getType()
            java.lang.Class<com.alipay.android.phone.inside.protobuf.wire.Message> r12 = com.alipay.android.phone.inside.protobuf.wire.Message.class
            boolean r12 = r12.isAssignableFrom(r10)
            if (r12 == 0) goto L_0x008c
            goto L_0x0079
        L_0x008c:
            java.lang.Class<java.util.List> r12 = java.util.List.class
            boolean r10 = r12.isAssignableFrom(r10)
            if (r10 == 0) goto L_0x0078
            java.lang.reflect.Type r10 = r13.getGenericType()
            java.lang.reflect.ParameterizedType r10 = (java.lang.reflect.ParameterizedType) r10
            java.lang.reflect.Type[] r10 = r10.getActualTypeArguments()
            r10 = r10[r4]
            boolean r12 = r10 instanceof java.lang.Class
            if (r12 == 0) goto L_0x0078
            java.lang.Class<com.alipay.android.phone.inside.protobuf.wire.Message> r12 = com.alipay.android.phone.inside.protobuf.wire.Message.class
            java.lang.Class r10 = (java.lang.Class) r10
            boolean r12 = r12.isAssignableFrom(r10)
            if (r12 == 0) goto L_0x0078
            goto L_0x0079
        L_0x00af:
            r12 = r11
        L_0x00b0:
            java.lang.Integer r15 = java.lang.Integer.valueOf(r7)
            com.alipay.android.phone.inside.protobuf.wire.MessageAdapter$FieldInfo r14 = new com.alipay.android.phone.inside.protobuf.wire.MessageAdapter$FieldInfo
            com.alipay.android.phone.inside.protobuf.wire.Message$Label r10 = r6.label()
            boolean r11 = r6.redacted()
            java.lang.reflect.Field r16 = r0.a(r8)
            r17 = 0
            r6 = r14
            r4 = r14
            r14 = r16
            r18 = r1
            r1 = r15
            r15 = r17
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15)
            r2.put(r1, r4)
            goto L_0x00d6
        L_0x00d4:
            r18 = r1
        L_0x00d6:
            int r5 = r5 + 1
            r1 = r18
            r4 = 0
            goto L_0x0020
        L_0x00dd:
            com.alipay.android.phone.inside.protobuf.wire.TagMap r1 = com.alipay.android.phone.inside.protobuf.wire.TagMap.a(r2)
            r0.d = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.protobuf.wire.MessageAdapter.<init>(com.alipay.android.phone.inside.protobuf.wire.Wire, java.lang.Class):void");
    }

    private Field a(String str) {
        try {
            return this.b.getField(str);
        } catch (NoSuchFieldException unused) {
            StringBuilder sb = new StringBuilder("No builder field ");
            sb.append(this.b.getName());
            sb.append(".");
            sb.append(str);
            throw new AssertionError(sb.toString());
        }
    }

    private int a(List<?> list, int i, Datatype datatype) {
        int i2 = 0;
        for (Object a2 : list) {
            i2 += a(i, (Object) a2, datatype);
        }
        return i2;
    }

    private int b(List<?> list, int i, Datatype datatype) {
        int i2 = 0;
        for (Object a2 : list) {
            i2 += a((Object) a2, datatype);
        }
        return WireOutput.c(WireOutput.a(i, WireType.LENGTH_DELIMITED)) + WireOutput.c(i2) + i2;
    }

    private void a(WireOutput wireOutput, List<?> list, int i, Datatype datatype) throws IOException {
        for (Object a2 : list) {
            a(wireOutput, i, (Object) a2, datatype);
        }
    }

    private void b(WireOutput wireOutput, List<?> list, int i, Datatype datatype) throws IOException {
        int i2 = 0;
        for (Object a2 : list) {
            i2 += a((Object) a2, datatype);
        }
        wireOutput.b(i, WireType.LENGTH_DELIMITED);
        wireOutput.e(i2);
        for (Object a3 : list) {
            a(wireOutput, (Object) a3, datatype);
        }
    }

    /* access modifiers changed from: 0000 */
    public final byte[] b(M m) {
        byte[] bArr = new byte[a(m)];
        try {
            a(m, WireOutput.a(bArr));
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final String c(M m) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getSimpleName());
        sb.append("{");
        String str = "";
        for (T t : this.d.a) {
            Object a2 = a(m, (FieldInfo) t);
            if (a2 != null) {
                sb.append(str);
                str = ", ";
                sb.append(t.b);
                sb.append("=");
                if (t.g) {
                    a2 = "██";
                }
                sb.append(a2);
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

    private int a(int i, Object obj, Datatype datatype) {
        return WireOutput.b(i) + a(obj, datatype);
    }

    private int a(Object obj, Datatype datatype) {
        switch (datatype) {
            case INT32:
                return WireOutput.a(((Integer) obj).intValue());
            case INT64:
            case UINT64:
                return WireOutput.a(((Long) obj).longValue());
            case UINT32:
                return WireOutput.c(((Integer) obj).intValue());
            case SINT32:
                return WireOutput.c(WireOutput.g(((Integer) obj).intValue()));
            case SINT64:
                return WireOutput.a(WireOutput.d(((Long) obj).longValue()));
            case BOOL:
                return 1;
            case ENUM:
                ProtoEnum protoEnum = (ProtoEnum) obj;
                this.a.c(protoEnum.getClass());
                return WireOutput.c(EnumAdapter.a(protoEnum));
            case STRING:
                String str = (String) obj;
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
                return WireOutput.c(i2) + i2;
            case BYTES:
                int size = ((ByteString) obj).size();
                return WireOutput.c(size) + size;
            case MESSAGE:
                int serializedSize = ((Message) obj).getSerializedSize();
                return WireOutput.c(serializedSize) + serializedSize;
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

    private void a(WireOutput wireOutput, int i, Object obj, Datatype datatype) throws IOException {
        wireOutput.b(i, datatype.wireType());
        a(wireOutput, obj, datatype);
    }

    private void a(WireOutput wireOutput, Object obj, Datatype datatype) throws IOException {
        switch (datatype) {
            case INT32:
                int intValue = ((Integer) obj).intValue();
                if (intValue >= 0) {
                    wireOutput.e(intValue);
                    return;
                } else {
                    wireOutput.b((long) intValue);
                    return;
                }
            case INT64:
            case UINT64:
                wireOutput.b(((Long) obj).longValue());
                return;
            case UINT32:
                wireOutput.e(((Integer) obj).intValue());
                return;
            case SINT32:
                wireOutput.e(WireOutput.g(((Integer) obj).intValue()));
                return;
            case SINT64:
                wireOutput.b(WireOutput.d(((Long) obj).longValue()));
                return;
            case BOOL:
                wireOutput.d(((Boolean) obj).booleanValue() ? 1 : 0);
                return;
            case ENUM:
                ProtoEnum protoEnum = (ProtoEnum) obj;
                this.a.c(protoEnum.getClass());
                wireOutput.e(EnumAdapter.a(protoEnum));
                return;
            case STRING:
                byte[] bytes = ((String) obj).getBytes("UTF-8");
                wireOutput.e(bytes.length);
                wireOutput.b(bytes);
                return;
            case BYTES:
                ByteString byteString = (ByteString) obj;
                wireOutput.e(byteString.size());
                wireOutput.b(byteString.toByteArray());
                return;
            case MESSAGE:
                Message message = (Message) obj;
                wireOutput.e(message.getSerializedSize());
                this.a.a(message.getClass()).a((M) message, wireOutput);
                return;
            case FIXED32:
            case SFIXED32:
                wireOutput.f(((Integer) obj).intValue());
                return;
            case FLOAT:
                wireOutput.f(Float.floatToIntBits(((Float) obj).floatValue()));
                return;
            case FIXED64:
            case SFIXED64:
                wireOutput.c(((Long) obj).longValue());
                return;
            case DOUBLE:
                wireOutput.c(Double.doubleToLongBits(((Double) obj).doubleValue()));
                return;
            default:
                throw new RuntimeException();
        }
    }

    /* access modifiers changed from: 0000 */
    public final M a(WireInput wireInput) throws IOException {
        Datatype datatype;
        Label label;
        try {
            M m = (Message) this.b.newInstance();
            Storage storage = new Storage(0);
            while (true) {
                Extension<ExtendableMessage<?>, ?> extension = null;
                int a2 = wireInput.a();
                int i = a2 >> 3;
                WireType valueOf = WireType.valueOf(a2);
                if (i == 0) {
                    for (Integer intValue : storage.a()) {
                        int intValue2 = intValue.intValue();
                        if (this.d.b(intValue2)) {
                            a(m, intValue2, (Object) storage.a(intValue2));
                        } else {
                            ((ExtendableMessage) m).setExtension(a(intValue2), storage.a(intValue2));
                        }
                    }
                    return m;
                }
                FieldInfo fieldInfo = (FieldInfo) this.d.a(i);
                if (fieldInfo != null) {
                    datatype = fieldInfo.c;
                    label = fieldInfo.d;
                } else {
                    extension = a(i);
                    if (extension == null) {
                        switch (valueOf) {
                            case VARINT:
                                m.ensureUnknownFieldMap().a(i, Long.valueOf(wireInput.d()));
                                break;
                            case FIXED32:
                                m.ensureUnknownFieldMap().a(i, Integer.valueOf(wireInput.e()));
                                break;
                            case FIXED64:
                                m.ensureUnknownFieldMap().b(i, Long.valueOf(wireInput.f()));
                                break;
                            case LENGTH_DELIMITED:
                                m.ensureUnknownFieldMap().a(i, wireInput.b(wireInput.c()));
                                break;
                            case START_GROUP:
                                wireInput.h();
                                break;
                            case END_GROUP:
                                break;
                            default:
                                throw new RuntimeException("Unsupported wire type: ".concat(String.valueOf(valueOf)));
                        }
                    } else {
                        datatype = extension.f();
                        label = extension.g();
                    }
                }
                if (!label.isPacked() || valueOf != WireType.LENGTH_DELIMITED) {
                    Object a3 = a(wireInput, i, datatype);
                    if (datatype == Datatype.ENUM && (a3 instanceof Integer)) {
                        m.addVarint(i, (long) ((Integer) a3).intValue());
                    } else if (label.isRepeated()) {
                        storage.a(i, a3);
                    } else if (extension != null) {
                        ((ExtendableMessage) m).setExtension(extension, a3);
                    } else {
                        a(m, i, a3);
                    }
                } else {
                    int c2 = wireInput.c();
                    long g = wireInput.g();
                    int d2 = wireInput.d(c2);
                    while (true) {
                        long j = ((long) c2) + g;
                        if (wireInput.g() < j) {
                            Object a4 = a(wireInput, i, datatype);
                            if (datatype != Datatype.ENUM || !(a4 instanceof Integer)) {
                                storage.a(i, a4);
                            } else {
                                m.addVarint(i, (long) ((Integer) a4).intValue());
                            }
                        } else {
                            wireInput.e(d2);
                            if (wireInput.g() != j) {
                                throw new IOException("Packed data had wrong length!");
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        }
    }

    private Object a(WireInput wireInput, int i, Datatype datatype) throws IOException {
        EnumAdapter<? extends ProtoEnum> enumAdapter;
        MessageAdapter<? extends Message> messageAdapter;
        boolean z = false;
        Class cls = null;
        switch (datatype) {
            case INT32:
            case UINT32:
                return Integer.valueOf(wireInput.c());
            case INT64:
            case UINT64:
                return Long.valueOf(wireInput.d());
            case SINT32:
                return Integer.valueOf(WireInput.c(wireInput.c()));
            case SINT64:
                return Long.valueOf(WireInput.a(wireInput.d()));
            case BOOL:
                if (wireInput.c() != 0) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case ENUM:
                FieldInfo fieldInfo = (FieldInfo) this.d.a(i);
                if (fieldInfo == null || fieldInfo.i == null) {
                    Wire wire = this.a;
                    FieldInfo fieldInfo2 = (FieldInfo) this.d.a(i);
                    if (fieldInfo2 != null) {
                        cls = fieldInfo2.e;
                    }
                    if (cls == null) {
                        Extension<ExtendableMessage<?>, ?> a2 = a(i);
                        if (a2 != null) {
                            cls = a2.c();
                        }
                    }
                    enumAdapter = wire.c(cls);
                    if (fieldInfo != null) {
                        fieldInfo.i = enumAdapter;
                    }
                } else {
                    enumAdapter = fieldInfo.i;
                }
                int c2 = wireInput.c();
                try {
                    return enumAdapter.a(c2);
                } catch (IllegalArgumentException unused) {
                    return Integer.valueOf(c2);
                }
            case STRING:
                return wireInput.b();
            case BYTES:
                return wireInput.b(wireInput.c());
            case MESSAGE:
                int c3 = wireInput.c();
                if (wireInput.a >= 64) {
                    throw new IOException("Wire recursion limit exceeded");
                }
                int d2 = wireInput.d(c3);
                wireInput.a++;
                FieldInfo fieldInfo3 = (FieldInfo) this.d.a(i);
                if (fieldInfo3 == null || fieldInfo3.h == null) {
                    Wire wire2 = this.a;
                    FieldInfo fieldInfo4 = (FieldInfo) this.d.a(i);
                    if (fieldInfo4 != null) {
                        cls = fieldInfo4.f;
                    }
                    if (cls == null) {
                        Extension<ExtendableMessage<?>, ?> a3 = a(i);
                        if (a3 != null) {
                            cls = a3.b();
                        }
                    }
                    messageAdapter = wire2.a(cls);
                    if (fieldInfo3 != null) {
                        fieldInfo3.h = messageAdapter;
                    }
                } else {
                    messageAdapter = fieldInfo3.h;
                }
                Message a4 = messageAdapter.a(wireInput);
                wireInput.a(0);
                wireInput.a--;
                wireInput.e(d2);
                return a4;
            case FIXED32:
            case SFIXED32:
                return Integer.valueOf(wireInput.e());
            case FLOAT:
                return Float.valueOf(Float.intBitsToFloat(wireInput.e()));
            case FIXED64:
            case SFIXED64:
                return Long.valueOf(wireInput.f());
            case DOUBLE:
                return Double.valueOf(Double.longBitsToDouble(wireInput.f()));
            default:
                throw new RuntimeException();
        }
    }

    private Extension<ExtendableMessage<?>, ?> a(int i) {
        ExtensionRegistry extensionRegistry = this.a.a;
        if (extensionRegistry == null) {
            return null;
        }
        return extensionRegistry.a(this.b, i);
    }

    /* access modifiers changed from: 0000 */
    public final int a(M m) {
        int i;
        int i2 = 0;
        for (T t : this.d.a) {
            Object a2 = a(m, (FieldInfo) t);
            if (a2 != null) {
                int i3 = t.a;
                Datatype datatype = t.c;
                Label label = t.d;
                if (!label.isRepeated()) {
                    i2 += a(i3, a2, datatype);
                } else if (label.isPacked()) {
                    i2 += b((List) a2, i3, datatype);
                } else {
                    i2 += a((List) a2, i3, datatype);
                }
            }
        }
        if (m instanceof ExtendableMessage) {
            ExtendableMessage extendableMessage = (ExtendableMessage) m;
            if (extendableMessage.extensionMap != null) {
                ExtensionMap<T> extensionMap = extendableMessage.extensionMap;
                int i4 = 0;
                for (int i5 = 0; i5 < extensionMap.a(); i5++) {
                    Extension a3 = extensionMap.a(i5);
                    Object b2 = extensionMap.b(i5);
                    int e = a3.e();
                    Datatype f = a3.f();
                    Label g = a3.g();
                    if (!g.isRepeated()) {
                        i = a(e, b2, f);
                    } else if (g.isPacked()) {
                        i = b((List) b2, e, f);
                    } else {
                        i = a((List) b2, e, f);
                    }
                    i4 += i;
                }
                i2 += i4;
            }
        }
        return i2 + m.getUnknownFieldsSerializedSize();
    }

    /* access modifiers changed from: 0000 */
    public final void a(M m, WireOutput wireOutput) throws IOException {
        for (T t : this.d.a) {
            Object a2 = a(m, (FieldInfo) t);
            if (a2 != null) {
                int i = t.a;
                Datatype datatype = t.c;
                Label label = t.d;
                if (!label.isRepeated()) {
                    a(wireOutput, i, a2, datatype);
                } else if (label.isPacked()) {
                    b(wireOutput, (List) a2, i, datatype);
                } else {
                    a(wireOutput, (List) a2, i, datatype);
                }
            }
        }
        if (m instanceof ExtendableMessage) {
            ExtendableMessage extendableMessage = (ExtendableMessage) m;
            if (extendableMessage.extensionMap != null) {
                ExtensionMap<T> extensionMap = extendableMessage.extensionMap;
                for (int i2 = 0; i2 < extensionMap.a(); i2++) {
                    Extension a3 = extensionMap.a(i2);
                    Object b2 = extensionMap.b(i2);
                    int e = a3.e();
                    Datatype f = a3.f();
                    Label g = a3.g();
                    if (!g.isRepeated()) {
                        a(wireOutput, e, b2, f);
                    } else if (g.isPacked()) {
                        b(wireOutput, (List) b2, e, f);
                    } else {
                        a(wireOutput, (List) b2, e, f);
                    }
                }
            }
        }
        m.writeUnknownFieldMap(wireOutput);
    }
}
