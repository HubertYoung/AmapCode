package com.google.protobuf.nano;

import com.google.protobuf.nano.ExtendableMessageNano;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Extension<M extends ExtendableMessageNano<M>, T> {
    public static final int TYPE_BOOL = 8;
    public static final int TYPE_BYTES = 12;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_ENUM = 14;
    public static final int TYPE_FIXED32 = 7;
    public static final int TYPE_FIXED64 = 6;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_GROUP = 10;
    public static final int TYPE_INT32 = 5;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_MESSAGE = 11;
    public static final int TYPE_SFIXED32 = 15;
    public static final int TYPE_SFIXED64 = 16;
    public static final int TYPE_SINT32 = 17;
    public static final int TYPE_SINT64 = 18;
    public static final int TYPE_STRING = 9;
    public static final int TYPE_UINT32 = 13;
    public static final int TYPE_UINT64 = 4;
    protected final Class<T> clazz;
    protected final boolean repeated;
    public final int tag;
    protected final int type;

    static class PrimitiveExtension<M extends ExtendableMessageNano<M>, T> extends Extension<M, T> {
        private final int nonPackedTag;
        private final int packedTag;

        public PrimitiveExtension(int i, Class<T> cls, int i2, boolean z, int i3, int i4) {
            super(i, cls, i2, z);
            this.nonPackedTag = i3;
            this.packedTag = i4;
        }

        /* access modifiers changed from: protected */
        public Object readData(CodedInputByteBufferNano codedInputByteBufferNano) {
            try {
                return codedInputByteBufferNano.readPrimitiveField(this.type);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error reading extension field", e);
            }
        }

        /* access modifiers changed from: protected */
        public void readDataInto(UnknownFieldData unknownFieldData, List<Object> list) {
            if (unknownFieldData.tag == this.nonPackedTag) {
                list.add(readData(CodedInputByteBufferNano.newInstance(unknownFieldData.bytes)));
                return;
            }
            CodedInputByteBufferNano newInstance = CodedInputByteBufferNano.newInstance(unknownFieldData.bytes);
            try {
                newInstance.pushLimit(newInstance.readRawVarint32());
                while (!newInstance.isAtEnd()) {
                    list.add(readData(newInstance));
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Error reading extension field", e);
            }
        }

        /* access modifiers changed from: protected */
        public final void writeSingularData(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) {
            try {
                codedOutputByteBufferNano.writeRawVarint32(this.tag);
                switch (this.type) {
                    case 1:
                        codedOutputByteBufferNano.writeDoubleNoTag(((Double) obj).doubleValue());
                        return;
                    case 2:
                        codedOutputByteBufferNano.writeFloatNoTag(((Float) obj).floatValue());
                        return;
                    case 3:
                        codedOutputByteBufferNano.writeInt64NoTag(((Long) obj).longValue());
                        return;
                    case 4:
                        codedOutputByteBufferNano.writeUInt64NoTag(((Long) obj).longValue());
                        return;
                    case 5:
                        codedOutputByteBufferNano.writeInt32NoTag(((Integer) obj).intValue());
                        return;
                    case 6:
                        codedOutputByteBufferNano.writeFixed64NoTag(((Long) obj).longValue());
                        return;
                    case 7:
                        codedOutputByteBufferNano.writeFixed32NoTag(((Integer) obj).intValue());
                        return;
                    case 8:
                        codedOutputByteBufferNano.writeBoolNoTag(((Boolean) obj).booleanValue());
                        return;
                    case 9:
                        codedOutputByteBufferNano.writeStringNoTag((String) obj);
                        return;
                    case 12:
                        codedOutputByteBufferNano.writeBytesNoTag((byte[]) obj);
                        return;
                    case 13:
                        codedOutputByteBufferNano.writeUInt32NoTag(((Integer) obj).intValue());
                        return;
                    case 14:
                        codedOutputByteBufferNano.writeEnumNoTag(((Integer) obj).intValue());
                        return;
                    case 15:
                        codedOutputByteBufferNano.writeSFixed32NoTag(((Integer) obj).intValue());
                        return;
                    case 16:
                        codedOutputByteBufferNano.writeSFixed64NoTag(((Long) obj).longValue());
                        return;
                    case 17:
                        codedOutputByteBufferNano.writeSInt32NoTag(((Integer) obj).intValue());
                        return;
                    case 18:
                        codedOutputByteBufferNano.writeSInt64NoTag(((Long) obj).longValue());
                        return;
                    default:
                        StringBuilder sb = new StringBuilder("Unknown type ");
                        sb.append(this.type);
                        throw new IllegalArgumentException(sb.toString());
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x003f, code lost:
            if (r2 >= r0) goto L_0x004b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0041, code lost:
            r7.writeSInt64NoTag(java.lang.reflect.Array.getLong(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
            if (r2 >= r0) goto L_0x0058;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
            r7.writeSInt32NoTag(java.lang.reflect.Array.getInt(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0058, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0059, code lost:
            if (r2 >= r0) goto L_0x0065;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x005b, code lost:
            r7.writeSFixed64NoTag(java.lang.reflect.Array.getLong(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0065, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0066, code lost:
            if (r2 >= r0) goto L_0x0072;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0068, code lost:
            r7.writeSFixed32NoTag(java.lang.reflect.Array.getInt(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0072, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0073, code lost:
            if (r2 >= r0) goto L_0x007f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0075, code lost:
            r7.writeEnumNoTag(java.lang.reflect.Array.getInt(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x007f, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0080, code lost:
            if (r2 >= r0) goto L_0x008c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0082, code lost:
            r7.writeUInt32NoTag(java.lang.reflect.Array.getInt(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x008c, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x008d, code lost:
            if (r2 >= r0) goto L_0x0099;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x008f, code lost:
            r7.writeBoolNoTag(java.lang.reflect.Array.getBoolean(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0099, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x009a, code lost:
            if (r2 >= r0) goto L_0x00a6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x009c, code lost:
            r7.writeFixed32NoTag(java.lang.reflect.Array.getInt(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a6, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a7, code lost:
            if (r2 >= r0) goto L_0x00b3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a9, code lost:
            r7.writeFixed64NoTag(java.lang.reflect.Array.getLong(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b3, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b4, code lost:
            if (r2 >= r0) goto L_0x00c0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b6, code lost:
            r7.writeInt32NoTag(java.lang.reflect.Array.getInt(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c0, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c1, code lost:
            if (r2 >= r0) goto L_0x00cd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c3, code lost:
            r7.writeUInt64NoTag(java.lang.reflect.Array.getLong(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00cd, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ce, code lost:
            if (r2 >= r0) goto L_0x00da;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d0, code lost:
            r7.writeInt64NoTag(java.lang.reflect.Array.getLong(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x00da, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x00db, code lost:
            if (r2 >= r0) goto L_0x00e7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x00dd, code lost:
            r7.writeFloatNoTag(java.lang.reflect.Array.getFloat(r6, r2));
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e7, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e8, code lost:
            if (r2 >= r0) goto L_0x00f4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x00ea, code lost:
            r7.writeDoubleNoTag(java.lang.reflect.Array.getDouble(r6, r2));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00f1, code lost:
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f4, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void writeRepeatedData(java.lang.Object r6, com.google.protobuf.nano.CodedOutputByteBufferNano r7) {
            /*
                r5 = this;
                int r0 = r5.tag
                int r1 = r5.nonPackedTag
                if (r0 != r1) goto L_0x000a
                com.google.protobuf.nano.Extension.super.writeRepeatedData(r6, r7)
                return
            L_0x000a:
                int r0 = r5.tag
                int r1 = r5.packedTag
                if (r0 != r1) goto L_0x00fc
                int r0 = java.lang.reflect.Array.getLength(r6)
                int r1 = r5.computePackedDataSize(r6)
                int r2 = r5.tag     // Catch:{ IOException -> 0x00f5 }
                r7.writeRawVarint32(r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeRawVarint32(r1)     // Catch:{ IOException -> 0x00f5 }
                int r1 = r5.type     // Catch:{ IOException -> 0x00f5 }
                r2 = 0
                switch(r1) {
                    case 1: goto L_0x00e8;
                    case 2: goto L_0x00db;
                    case 3: goto L_0x00ce;
                    case 4: goto L_0x00c1;
                    case 5: goto L_0x00b4;
                    case 6: goto L_0x00a7;
                    case 7: goto L_0x009a;
                    case 8: goto L_0x008d;
                    default: goto L_0x0026;
                }     // Catch:{ IOException -> 0x00f5 }
            L_0x0026:
                switch(r1) {
                    case 13: goto L_0x0080;
                    case 14: goto L_0x0073;
                    case 15: goto L_0x0066;
                    case 16: goto L_0x0059;
                    case 17: goto L_0x004c;
                    case 18: goto L_0x003f;
                    default: goto L_0x0029;
                }     // Catch:{ IOException -> 0x00f5 }
            L_0x0029:
                java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch:{ IOException -> 0x00f5 }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f5 }
                java.lang.String r0 = "Unpackable type "
                r7.<init>(r0)     // Catch:{ IOException -> 0x00f5 }
                int r0 = r5.type     // Catch:{ IOException -> 0x00f5 }
                r7.append(r0)     // Catch:{ IOException -> 0x00f5 }
                java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x00f5 }
                r6.<init>(r7)     // Catch:{ IOException -> 0x00f5 }
                throw r6     // Catch:{ IOException -> 0x00f5 }
            L_0x003f:
                if (r2 >= r0) goto L_0x004b
                long r3 = java.lang.reflect.Array.getLong(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeSInt64NoTag(r3)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x003f
            L_0x004b:
                return
            L_0x004c:
                if (r2 >= r0) goto L_0x0058
                int r1 = java.lang.reflect.Array.getInt(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeSInt32NoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x004c
            L_0x0058:
                return
            L_0x0059:
                if (r2 >= r0) goto L_0x0065
                long r3 = java.lang.reflect.Array.getLong(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeSFixed64NoTag(r3)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x0059
            L_0x0065:
                return
            L_0x0066:
                if (r2 >= r0) goto L_0x0072
                int r1 = java.lang.reflect.Array.getInt(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeSFixed32NoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x0066
            L_0x0072:
                return
            L_0x0073:
                if (r2 >= r0) goto L_0x007f
                int r1 = java.lang.reflect.Array.getInt(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeEnumNoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x0073
            L_0x007f:
                return
            L_0x0080:
                if (r2 >= r0) goto L_0x008c
                int r1 = java.lang.reflect.Array.getInt(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeUInt32NoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x0080
            L_0x008c:
                return
            L_0x008d:
                if (r2 >= r0) goto L_0x0099
                boolean r1 = java.lang.reflect.Array.getBoolean(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeBoolNoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x008d
            L_0x0099:
                return
            L_0x009a:
                if (r2 >= r0) goto L_0x00a6
                int r1 = java.lang.reflect.Array.getInt(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeFixed32NoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x009a
            L_0x00a6:
                return
            L_0x00a7:
                if (r2 >= r0) goto L_0x00b3
                long r3 = java.lang.reflect.Array.getLong(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeFixed64NoTag(r3)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x00a7
            L_0x00b3:
                return
            L_0x00b4:
                if (r2 >= r0) goto L_0x00c0
                int r1 = java.lang.reflect.Array.getInt(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeInt32NoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x00b4
            L_0x00c0:
                return
            L_0x00c1:
                if (r2 >= r0) goto L_0x00cd
                long r3 = java.lang.reflect.Array.getLong(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeUInt64NoTag(r3)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x00c1
            L_0x00cd:
                return
            L_0x00ce:
                if (r2 >= r0) goto L_0x00da
                long r3 = java.lang.reflect.Array.getLong(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeInt64NoTag(r3)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x00ce
            L_0x00da:
                return
            L_0x00db:
                if (r2 >= r0) goto L_0x00e7
                float r1 = java.lang.reflect.Array.getFloat(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeFloatNoTag(r1)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x00db
            L_0x00e7:
                return
            L_0x00e8:
                if (r2 >= r0) goto L_0x00f4
                double r3 = java.lang.reflect.Array.getDouble(r6, r2)     // Catch:{ IOException -> 0x00f5 }
                r7.writeDoubleNoTag(r3)     // Catch:{ IOException -> 0x00f5 }
                int r2 = r2 + 1
                goto L_0x00e8
            L_0x00f4:
                return
            L_0x00f5:
                r6 = move-exception
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                r7.<init>(r6)
                throw r7
            L_0x00fc:
                java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                java.lang.String r0 = "Unexpected repeated extension tag "
                r7.<init>(r0)
                int r0 = r5.tag
                r7.append(r0)
                java.lang.String r0 = ", unequal to both non-packed variant "
                r7.append(r0)
                int r0 = r5.nonPackedTag
                r7.append(r0)
                java.lang.String r0 = " and packed variant "
                r7.append(r0)
                int r0 = r5.packedTag
                r7.append(r0)
                java.lang.String r7 = r7.toString()
                r6.<init>(r7)
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.Extension.PrimitiveExtension.writeRepeatedData(java.lang.Object, com.google.protobuf.nano.CodedOutputByteBufferNano):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:26:0x008c, code lost:
            r0 = r0 * 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x008f, code lost:
            r0 = r0 * 8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
            return r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int computePackedDataSize(java.lang.Object r6) {
            /*
                r5 = this;
                int r0 = java.lang.reflect.Array.getLength(r6)
                int r1 = r5.type
                r2 = 0
                switch(r1) {
                    case 1: goto L_0x008f;
                    case 2: goto L_0x008c;
                    case 3: goto L_0x007d;
                    case 4: goto L_0x006e;
                    case 5: goto L_0x005f;
                    case 6: goto L_0x008f;
                    case 7: goto L_0x008c;
                    case 8: goto L_0x0091;
                    default: goto L_0x000a;
                }
            L_0x000a:
                switch(r1) {
                    case 13: goto L_0x0050;
                    case 14: goto L_0x0041;
                    case 15: goto L_0x008c;
                    case 16: goto L_0x008f;
                    case 17: goto L_0x0032;
                    case 18: goto L_0x0023;
                    default: goto L_0x000d;
                }
            L_0x000d:
                java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Unexpected non-packable type "
                r0.<init>(r1)
                int r1 = r5.type
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r6.<init>(r0)
                throw r6
            L_0x0023:
                r1 = 0
            L_0x0024:
                if (r2 >= r0) goto L_0x0092
                long r3 = java.lang.reflect.Array.getLong(r6, r2)
                int r3 = com.google.protobuf.nano.CodedOutputByteBufferNano.computeSInt64SizeNoTag(r3)
                int r1 = r1 + r3
                int r2 = r2 + 1
                goto L_0x0024
            L_0x0032:
                r1 = 0
            L_0x0033:
                if (r2 >= r0) goto L_0x0092
                int r3 = java.lang.reflect.Array.getInt(r6, r2)
                int r3 = com.google.protobuf.nano.CodedOutputByteBufferNano.computeSInt32SizeNoTag(r3)
                int r1 = r1 + r3
                int r2 = r2 + 1
                goto L_0x0033
            L_0x0041:
                r1 = 0
            L_0x0042:
                if (r2 >= r0) goto L_0x0092
                int r3 = java.lang.reflect.Array.getInt(r6, r2)
                int r3 = com.google.protobuf.nano.CodedOutputByteBufferNano.computeEnumSizeNoTag(r3)
                int r1 = r1 + r3
                int r2 = r2 + 1
                goto L_0x0042
            L_0x0050:
                r1 = 0
            L_0x0051:
                if (r2 >= r0) goto L_0x0092
                int r3 = java.lang.reflect.Array.getInt(r6, r2)
                int r3 = com.google.protobuf.nano.CodedOutputByteBufferNano.computeUInt32SizeNoTag(r3)
                int r1 = r1 + r3
                int r2 = r2 + 1
                goto L_0x0051
            L_0x005f:
                r1 = 0
            L_0x0060:
                if (r2 >= r0) goto L_0x0092
                int r3 = java.lang.reflect.Array.getInt(r6, r2)
                int r3 = com.google.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(r3)
                int r1 = r1 + r3
                int r2 = r2 + 1
                goto L_0x0060
            L_0x006e:
                r1 = 0
            L_0x006f:
                if (r2 >= r0) goto L_0x0092
                long r3 = java.lang.reflect.Array.getLong(r6, r2)
                int r3 = com.google.protobuf.nano.CodedOutputByteBufferNano.computeUInt64SizeNoTag(r3)
                int r1 = r1 + r3
                int r2 = r2 + 1
                goto L_0x006f
            L_0x007d:
                r1 = 0
            L_0x007e:
                if (r2 >= r0) goto L_0x0092
                long r3 = java.lang.reflect.Array.getLong(r6, r2)
                int r3 = com.google.protobuf.nano.CodedOutputByteBufferNano.computeInt64SizeNoTag(r3)
                int r1 = r1 + r3
                int r2 = r2 + 1
                goto L_0x007e
            L_0x008c:
                int r0 = r0 * 4
                goto L_0x0091
            L_0x008f:
                int r0 = r0 * 8
            L_0x0091:
                r1 = r0
            L_0x0092:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.Extension.PrimitiveExtension.computePackedDataSize(java.lang.Object):int");
        }

        /* access modifiers changed from: protected */
        public int computeRepeatedSerializedSize(Object obj) {
            if (this.tag == this.nonPackedTag) {
                return Extension.super.computeRepeatedSerializedSize(obj);
            }
            if (this.tag == this.packedTag) {
                int computePackedDataSize = computePackedDataSize(obj);
                return computePackedDataSize + CodedOutputByteBufferNano.computeRawVarint32Size(computePackedDataSize) + CodedOutputByteBufferNano.computeRawVarint32Size(this.tag);
            }
            StringBuilder sb = new StringBuilder("Unexpected repeated extension tag ");
            sb.append(this.tag);
            sb.append(", unequal to both non-packed variant ");
            sb.append(this.nonPackedTag);
            sb.append(" and packed variant ");
            sb.append(this.packedTag);
            throw new IllegalArgumentException(sb.toString());
        }

        /* access modifiers changed from: protected */
        public final int computeSingularSerializedSize(Object obj) {
            int tagFieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
            switch (this.type) {
                case 1:
                    return CodedOutputByteBufferNano.computeDoubleSize(tagFieldNumber, ((Double) obj).doubleValue());
                case 2:
                    return CodedOutputByteBufferNano.computeFloatSize(tagFieldNumber, ((Float) obj).floatValue());
                case 3:
                    return CodedOutputByteBufferNano.computeInt64Size(tagFieldNumber, ((Long) obj).longValue());
                case 4:
                    return CodedOutputByteBufferNano.computeUInt64Size(tagFieldNumber, ((Long) obj).longValue());
                case 5:
                    return CodedOutputByteBufferNano.computeInt32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 6:
                    return CodedOutputByteBufferNano.computeFixed64Size(tagFieldNumber, ((Long) obj).longValue());
                case 7:
                    return CodedOutputByteBufferNano.computeFixed32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 8:
                    return CodedOutputByteBufferNano.computeBoolSize(tagFieldNumber, ((Boolean) obj).booleanValue());
                case 9:
                    return CodedOutputByteBufferNano.computeStringSize(tagFieldNumber, (String) obj);
                case 12:
                    return CodedOutputByteBufferNano.computeBytesSize(tagFieldNumber, (byte[]) obj);
                case 13:
                    return CodedOutputByteBufferNano.computeUInt32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 14:
                    return CodedOutputByteBufferNano.computeEnumSize(tagFieldNumber, ((Integer) obj).intValue());
                case 15:
                    return CodedOutputByteBufferNano.computeSFixed32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 16:
                    return CodedOutputByteBufferNano.computeSFixed64Size(tagFieldNumber, ((Long) obj).longValue());
                case 17:
                    return CodedOutputByteBufferNano.computeSInt32Size(tagFieldNumber, ((Integer) obj).intValue());
                case 18:
                    return CodedOutputByteBufferNano.computeSInt64Size(tagFieldNumber, ((Long) obj).longValue());
                default:
                    StringBuilder sb = new StringBuilder("Unknown type ");
                    sb.append(this.type);
                    throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    @Deprecated
    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int i, Class<T> cls, int i2) {
        return new Extension<>(i, cls, i2, false);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int i, Class<T> cls, long j) {
        return new Extension<>(i, cls, (int) j, false);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T[]> createRepeatedMessageTyped(int i, Class<T[]> cls, long j) {
        return new Extension<>(i, cls, (int) j, true);
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createPrimitiveTyped(int i, Class<T> cls, long j) {
        PrimitiveExtension primitiveExtension = new PrimitiveExtension(i, cls, (int) j, false, 0, 0);
        return primitiveExtension;
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createRepeatedPrimitiveTyped(int i, Class<T> cls, long j, long j2, long j3) {
        PrimitiveExtension primitiveExtension = new PrimitiveExtension(i, cls, (int) j, true, (int) j2, (int) j3);
        return primitiveExtension;
    }

    private Extension(int i, Class<T> cls, int i2, boolean z) {
        this.type = i;
        this.clazz = cls;
        this.tag = i2;
        this.repeated = z;
    }

    /* access modifiers changed from: 0000 */
    public final T getValueFrom(List<UnknownFieldData> list) {
        if (list == null) {
            return null;
        }
        return this.repeated ? getRepeatedValueFrom(list) : getSingularValueFrom(list);
    }

    private T getRepeatedValueFrom(List<UnknownFieldData> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            UnknownFieldData unknownFieldData = list.get(i);
            if (unknownFieldData.bytes.length != 0) {
                readDataInto(unknownFieldData, arrayList);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        T cast = this.clazz.cast(Array.newInstance(this.clazz.getComponentType(), size));
        for (int i2 = 0; i2 < size; i2++) {
            Array.set(cast, i2, arrayList.get(i2));
        }
        return cast;
    }

    private T getSingularValueFrom(List<UnknownFieldData> list) {
        if (list.isEmpty()) {
            return null;
        }
        return this.clazz.cast(readData(CodedInputByteBufferNano.newInstance(list.get(list.size() - 1).bytes)));
    }

    /* access modifiers changed from: protected */
    public Object readData(CodedInputByteBufferNano codedInputByteBufferNano) {
        Class componentType = this.repeated ? this.clazz.getComponentType() : this.clazz;
        try {
            switch (this.type) {
                case 10:
                    MessageNano messageNano = (MessageNano) componentType.newInstance();
                    codedInputByteBufferNano.readGroup(messageNano, WireFormatNano.getTagFieldNumber(this.tag));
                    return messageNano;
                case 11:
                    MessageNano messageNano2 = (MessageNano) componentType.newInstance();
                    codedInputByteBufferNano.readMessage(messageNano2);
                    return messageNano2;
                default:
                    StringBuilder sb = new StringBuilder("Unknown type ");
                    sb.append(this.type);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Error creating instance of class ".concat(String.valueOf(componentType)), e);
        } catch (IllegalAccessException e2) {
            throw new IllegalArgumentException("Error creating instance of class ".concat(String.valueOf(componentType)), e2);
        } catch (IOException e3) {
            throw new IllegalArgumentException("Error reading extension field", e3);
        }
    }

    /* access modifiers changed from: protected */
    public void readDataInto(UnknownFieldData unknownFieldData, List<Object> list) {
        list.add(readData(CodedInputByteBufferNano.newInstance(unknownFieldData.bytes)));
    }

    /* access modifiers changed from: 0000 */
    public void writeTo(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.repeated) {
            writeRepeatedData(obj, codedOutputByteBufferNano);
        } else {
            writeSingularData(obj, codedOutputByteBufferNano);
        }
    }

    /* access modifiers changed from: protected */
    public void writeSingularData(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) {
        try {
            codedOutputByteBufferNano.writeRawVarint32(this.tag);
            switch (this.type) {
                case 10:
                    int tagFieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
                    codedOutputByteBufferNano.writeGroupNoTag((MessageNano) obj);
                    codedOutputByteBufferNano.writeTag(tagFieldNumber, 4);
                    return;
                case 11:
                    codedOutputByteBufferNano.writeMessageNoTag((MessageNano) obj);
                    return;
                default:
                    StringBuilder sb = new StringBuilder("Unknown type ");
                    sb.append(this.type);
                    throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void writeRepeatedData(Object obj, CodedOutputByteBufferNano codedOutputByteBufferNano) {
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                writeSingularData(obj2, codedOutputByteBufferNano);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int computeSerializedSize(Object obj) {
        if (this.repeated) {
            return computeRepeatedSerializedSize(obj);
        }
        return computeSingularSerializedSize(obj);
    }

    /* access modifiers changed from: protected */
    public int computeRepeatedSerializedSize(Object obj) {
        int length = Array.getLength(obj);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Array.get(obj, i2) != null) {
                i += computeSingularSerializedSize(Array.get(obj, i2));
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public int computeSingularSerializedSize(Object obj) {
        int tagFieldNumber = WireFormatNano.getTagFieldNumber(this.tag);
        switch (this.type) {
            case 10:
                return CodedOutputByteBufferNano.computeGroupSize(tagFieldNumber, (MessageNano) obj);
            case 11:
                return CodedOutputByteBufferNano.computeMessageSize(tagFieldNumber, (MessageNano) obj);
            default:
                StringBuilder sb = new StringBuilder("Unknown type ");
                sb.append(this.type);
                throw new IllegalArgumentException(sb.toString());
        }
    }
}
