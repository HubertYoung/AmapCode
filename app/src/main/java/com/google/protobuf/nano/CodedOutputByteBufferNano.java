package com.google.protobuf.nano;

import com.alipay.zoloz.toyger.bean.Config;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class CodedOutputByteBufferNano {
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    public static final int LITTLE_ENDIAN_64_SIZE = 8;
    private static final int MAX_UTF8_EXPANSION = 3;
    private final ByteBuffer buffer;

    public static class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
        OutOfSpaceException(int i, int i2) {
            // StringBuilder sb = new StringBuilder("CodedOutputStream was writing to a flat byte array and ran out of space (pos ");
            // sb.append(i);
            // sb.append(" limit ");
            // sb.append(i2);
            // sb.append(").");
            super(sb.toString());
        }
    }

    public static int computeBoolSizeNoTag(boolean z) {
        return 1;
    }

    public static int computeDoubleSizeNoTag(double d) {
        return 8;
    }

    public static int computeFixed32SizeNoTag(int i) {
        return 4;
    }

    public static int computeFixed64SizeNoTag(long j) {
        return 8;
    }

    public static int computeFloatSizeNoTag(float f) {
        return 4;
    }

    public static int computeRawVarint32Size(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    public static int computeRawVarint64Size(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public static int computeSFixed32SizeNoTag(int i) {
        return 4;
    }

    public static int computeSFixed64SizeNoTag(long j) {
        return 8;
    }

    public static int encodeZigZag32(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static long encodeZigZag64(long j) {
        return (j >> 63) ^ (j << 1);
    }

    private CodedOutputByteBufferNano(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private CodedOutputByteBufferNano(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static CodedOutputByteBufferNano newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static CodedOutputByteBufferNano newInstance(byte[] bArr, int i, int i2) {
        return new CodedOutputByteBufferNano(bArr, i, i2);
    }

    public final void writeDouble(int i, double d) throws IOException {
        writeTag(i, 1);
        writeDoubleNoTag(d);
    }

    public final void writeFloat(int i, float f) throws IOException {
        writeTag(i, 5);
        writeFloatNoTag(f);
    }

    public final void writeUInt64(int i, long j) throws IOException {
        writeTag(i, 0);
        writeUInt64NoTag(j);
    }

    public final void writeInt64(int i, long j) throws IOException {
        writeTag(i, 0);
        writeInt64NoTag(j);
    }

    public final void writeInt32(int i, int i2) throws IOException {
        writeTag(i, 0);
        writeInt32NoTag(i2);
    }

    public final void writeFixed64(int i, long j) throws IOException {
        writeTag(i, 1);
        writeFixed64NoTag(j);
    }

    public final void writeFixed32(int i, int i2) throws IOException {
        writeTag(i, 5);
        writeFixed32NoTag(i2);
    }

    public final void writeBool(int i, boolean z) throws IOException {
        writeTag(i, 0);
        writeBoolNoTag(z);
    }

    public final void writeString(int i, String str) throws IOException {
        writeTag(i, 2);
        writeStringNoTag(str);
    }

    public final void writeGroup(int i, MessageNano messageNano) throws IOException {
        writeTag(i, 3);
        writeGroupNoTag(messageNano);
        writeTag(i, 4);
    }

    public final void writeMessage(int i, MessageNano messageNano) throws IOException {
        writeTag(i, 2);
        writeMessageNoTag(messageNano);
    }

    public final void writeBytes(int i, byte[] bArr) throws IOException {
        writeTag(i, 2);
        writeBytesNoTag(bArr);
    }

    public final void writeUInt32(int i, int i2) throws IOException {
        writeTag(i, 0);
        writeUInt32NoTag(i2);
    }

    public final void writeEnum(int i, int i2) throws IOException {
        writeTag(i, 0);
        writeEnumNoTag(i2);
    }

    public final void writeSFixed32(int i, int i2) throws IOException {
        writeTag(i, 5);
        writeSFixed32NoTag(i2);
    }

    public final void writeSFixed64(int i, long j) throws IOException {
        writeTag(i, 1);
        writeSFixed64NoTag(j);
    }

    public final void writeSInt32(int i, int i2) throws IOException {
        writeTag(i, 0);
        writeSInt32NoTag(i2);
    }

    public final void writeSInt64(int i, long j) throws IOException {
        writeTag(i, 0);
        writeSInt64NoTag(j);
    }

    public final void writeDoubleNoTag(double d) throws IOException {
        writeRawLittleEndian64(Double.doubleToLongBits(d));
    }

    public final void writeFloatNoTag(float f) throws IOException {
        writeRawLittleEndian32(Float.floatToIntBits(f));
    }

    public final void writeUInt64NoTag(long j) throws IOException {
        writeRawVarint64(j);
    }

    public final void writeInt64NoTag(long j) throws IOException {
        writeRawVarint64(j);
    }

    public final void writeInt32NoTag(int i) throws IOException {
        if (i >= 0) {
            writeRawVarint32(i);
        } else {
            writeRawVarint64((long) i);
        }
    }

    public final void writeFixed64NoTag(long j) throws IOException {
        writeRawLittleEndian64(j);
    }

    public final void writeFixed32NoTag(int i) throws IOException {
        writeRawLittleEndian32(i);
    }

    public final void writeBoolNoTag(boolean z) throws IOException {
        writeRawByte(z ? 1 : 0);
    }

    public final void writeStringNoTag(String str) throws IOException {
        try {
            int computeRawVarint32Size = computeRawVarint32Size(str.length());
            if (computeRawVarint32Size == computeRawVarint32Size(str.length() * 3)) {
                int position = this.buffer.position();
                if (this.buffer.remaining() < computeRawVarint32Size) {
                    throw new OutOfSpaceException(position + computeRawVarint32Size, this.buffer.limit());
                }
                this.buffer.position(position + computeRawVarint32Size);
                encode(str, this.buffer);
                int position2 = this.buffer.position();
                this.buffer.position(position);
                writeRawVarint32((position2 - position) - computeRawVarint32Size);
                this.buffer.position(position2);
                return;
            }
            writeRawVarint32(encodedLength(str));
            encode(str, this.buffer);
        } catch (BufferOverflowException e) {
            OutOfSpaceException outOfSpaceException = new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
            outOfSpaceException.initCause(e);
            throw outOfSpaceException;
        }
    }

    private static int encodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (true) {
            if (i >= length) {
                break;
            }
            char charAt = charSequence.charAt(i);
            if (charAt >= 2048) {
                i2 += encodedLengthGeneral(charSequence, i);
                break;
            }
            i2 += (127 - charAt) >>> 31;
            i++;
        }
        if (i2 >= length) {
            return i2;
        }
        StringBuilder sb = new StringBuilder("UTF-8 length does not fit in int: ");
        sb.append(((long) i2) + IjkMediaMeta.AV_CH_WIDE_RIGHT);
        throw new IllegalArgumentException(sb.toString());
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 2048) {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (Character.codePointAt(charSequence, i) < 65536) {
                        throw new IllegalArgumentException("Unpaired surrogate at index ".concat(String.valueOf(i)));
                    }
                    i++;
                }
            }
            i++;
        }
        return i2;
    }

    private static void encode(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(encode(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            encodeDirect(charSequence, byteBuffer);
        }
    }

    private static void encodeDirect(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 128) {
                byteBuffer.put((byte) charAt);
            } else if (charAt < 2048) {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else if (charAt < 55296 || 57343 < charAt) {
                byteBuffer.put((byte) ((charAt >>> 12) | Config.HQ_IMAGE_WIDTH));
                byteBuffer.put((byte) (((charAt >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else {
                int i2 = i + 1;
                if (i2 != charSequence.length()) {
                    char charAt2 = charSequence.charAt(i2);
                    if (!Character.isSurrogatePair(charAt, charAt2)) {
                        i = i2;
                    } else {
                        int codePoint = Character.toCodePoint(charAt, charAt2);
                        byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                        byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                        byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                        byteBuffer.put((byte) ((codePoint & 63) | 128));
                        i = i2;
                    }
                }
                StringBuilder sb = new StringBuilder("Unpaired surrogate at index ");
                sb.append(i - 1);
                throw new IllegalArgumentException(sb.toString());
            }
            i++;
        }
    }

    private static int encode(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        int length = charSequence.length();
        int i4 = i2 + i;
        int i5 = 0;
        while (i5 < length) {
            int i6 = i5 + i;
            if (i6 >= i4) {
                break;
            }
            char charAt = charSequence.charAt(i5);
            if (charAt >= 128) {
                break;
            }
            bArr[i6] = (byte) charAt;
            i5++;
        }
        if (i5 == length) {
            return i + length;
        }
        int i7 = i + i5;
        while (i5 < length) {
            char charAt2 = charSequence.charAt(i5);
            if (charAt2 < 128 && i7 < i4) {
                i3 = i7 + 1;
                bArr[i7] = (byte) charAt2;
            } else if (charAt2 < 2048 && i7 <= i4 - 2) {
                int i8 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                i7 = i8 + 1;
                bArr[i8] = (byte) ((charAt2 & '?') | 128);
                i5++;
            } else if ((charAt2 < 55296 || 57343 < charAt2) && i7 <= i4 - 3) {
                int i9 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 12) | Config.HQ_IMAGE_WIDTH);
                int i10 = i9 + 1;
                bArr[i9] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i3 = i10 + 1;
                bArr[i10] = (byte) ((charAt2 & '?') | 128);
            } else if (i7 <= i4 - 4) {
                int i11 = i5 + 1;
                if (i11 != charSequence.length()) {
                    char charAt3 = charSequence.charAt(i11);
                    if (!Character.isSurrogatePair(charAt2, charAt3)) {
                        i5 = i11;
                    } else {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        int i12 = i7 + 1;
                        bArr[i7] = (byte) ((codePoint >>> 18) | 240);
                        int i13 = i12 + 1;
                        bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int i14 = i13 + 1;
                        bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                        i7 = i14 + 1;
                        bArr[i14] = (byte) ((codePoint & 63) | 128);
                        i5 = i11;
                        i5++;
                    }
                }
                StringBuilder sb = new StringBuilder("Unpaired surrogate at index ");
                sb.append(i5 - 1);
                throw new IllegalArgumentException(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder("Failed writing ");
                sb2.append(charAt2);
                sb2.append(" at index ");
                sb2.append(i7);
                throw new ArrayIndexOutOfBoundsException(sb2.toString());
            }
            i7 = i3;
            i5++;
        }
        return i7;
    }

    public final void writeGroupNoTag(MessageNano messageNano) throws IOException {
        messageNano.writeTo(this);
    }

    public final void writeMessageNoTag(MessageNano messageNano) throws IOException {
        writeRawVarint32(messageNano.getCachedSize());
        messageNano.writeTo(this);
    }

    public final void writeBytesNoTag(byte[] bArr) throws IOException {
        writeRawVarint32(bArr.length);
        writeRawBytes(bArr);
    }

    public final void writeUInt32NoTag(int i) throws IOException {
        writeRawVarint32(i);
    }

    public final void writeEnumNoTag(int i) throws IOException {
        writeRawVarint32(i);
    }

    public final void writeSFixed32NoTag(int i) throws IOException {
        writeRawLittleEndian32(i);
    }

    public final void writeSFixed64NoTag(long j) throws IOException {
        writeRawLittleEndian64(j);
    }

    public final void writeSInt32NoTag(int i) throws IOException {
        writeRawVarint32(encodeZigZag32(i));
    }

    public final void writeSInt64NoTag(long j) throws IOException {
        writeRawVarint64(encodeZigZag64(j));
    }

    public static int computeDoubleSize(int i, double d) {
        return computeTagSize(i) + computeDoubleSizeNoTag(d);
    }

    public static int computeFloatSize(int i, float f) {
        return computeTagSize(i) + computeFloatSizeNoTag(f);
    }

    public static int computeUInt64Size(int i, long j) {
        return computeTagSize(i) + computeUInt64SizeNoTag(j);
    }

    public static int computeInt64Size(int i, long j) {
        return computeTagSize(i) + computeInt64SizeNoTag(j);
    }

    public static int computeInt32Size(int i, int i2) {
        return computeTagSize(i) + computeInt32SizeNoTag(i2);
    }

    public static int computeFixed64Size(int i, long j) {
        return computeTagSize(i) + computeFixed64SizeNoTag(j);
    }

    public static int computeFixed32Size(int i, int i2) {
        return computeTagSize(i) + computeFixed32SizeNoTag(i2);
    }

    public static int computeBoolSize(int i, boolean z) {
        return computeTagSize(i) + computeBoolSizeNoTag(z);
    }

    public static int computeStringSize(int i, String str) {
        return computeTagSize(i) + computeStringSizeNoTag(str);
    }

    public static int computeGroupSize(int i, MessageNano messageNano) {
        return (computeTagSize(i) * 2) + computeGroupSizeNoTag(messageNano);
    }

    public static int computeMessageSize(int i, MessageNano messageNano) {
        return computeTagSize(i) + computeMessageSizeNoTag(messageNano);
    }

    public static int computeBytesSize(int i, byte[] bArr) {
        return computeTagSize(i) + computeBytesSizeNoTag(bArr);
    }

    public static int computeUInt32Size(int i, int i2) {
        return computeTagSize(i) + computeUInt32SizeNoTag(i2);
    }

    public static int computeEnumSize(int i, int i2) {
        return computeTagSize(i) + computeEnumSizeNoTag(i2);
    }

    public static int computeSFixed32Size(int i, int i2) {
        return computeTagSize(i) + computeSFixed32SizeNoTag(i2);
    }

    public static int computeSFixed64Size(int i, long j) {
        return computeTagSize(i) + computeSFixed64SizeNoTag(j);
    }

    public static int computeSInt32Size(int i, int i2) {
        return computeTagSize(i) + computeSInt32SizeNoTag(i2);
    }

    public static int computeSInt64Size(int i, long j) {
        return computeTagSize(i) + computeSInt64SizeNoTag(j);
    }

    public static int computeUInt64SizeNoTag(long j) {
        return computeRawVarint64Size(j);
    }

    public static int computeInt64SizeNoTag(long j) {
        return computeRawVarint64Size(j);
    }

    public static int computeInt32SizeNoTag(int i) {
        if (i >= 0) {
            return computeRawVarint32Size(i);
        }
        return 10;
    }

    public static int computeStringSizeNoTag(String str) {
        int encodedLength = encodedLength(str);
        return computeRawVarint32Size(encodedLength) + encodedLength;
    }

    public static int computeGroupSizeNoTag(MessageNano messageNano) {
        return messageNano.getSerializedSize();
    }

    public static int computeMessageSizeNoTag(MessageNano messageNano) {
        int serializedSize = messageNano.getSerializedSize();
        return computeRawVarint32Size(serializedSize) + serializedSize;
    }

    public static int computeBytesSizeNoTag(byte[] bArr) {
        return computeRawVarint32Size(bArr.length) + bArr.length;
    }

    public static int computeUInt32SizeNoTag(int i) {
        return computeRawVarint32Size(i);
    }

    public static int computeEnumSizeNoTag(int i) {
        return computeRawVarint32Size(i);
    }

    public static int computeSInt32SizeNoTag(int i) {
        return computeRawVarint32Size(encodeZigZag32(i));
    }

    public static int computeSInt64SizeNoTag(long j) {
        return computeRawVarint64Size(encodeZigZag64(j));
    }

    public final int spaceLeft() {
        return this.buffer.remaining();
    }

    public final void checkNoSpaceLeft() {
        if (spaceLeft() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public final int position() {
        return this.buffer.position();
    }

    public final void reset() {
        this.buffer.clear();
    }

    public final void writeRawByte(byte b) throws IOException {
        if (!this.buffer.hasRemaining()) {
            throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.put(b);
    }

    public final void writeRawByte(int i) throws IOException {
        writeRawByte((byte) i);
    }

    public final void writeRawBytes(byte[] bArr) throws IOException {
        writeRawBytes(bArr, 0, bArr.length);
    }

    public final void writeRawBytes(byte[] bArr, int i, int i2) throws IOException {
        if (this.buffer.remaining() >= i2) {
            this.buffer.put(bArr, i, i2);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public final void writeTag(int i, int i2) throws IOException {
        writeRawVarint32(WireFormatNano.makeTag(i, i2));
    }

    public static int computeTagSize(int i) {
        return computeRawVarint32Size(WireFormatNano.makeTag(i, 0));
    }

    public final void writeRawVarint32(int i) throws IOException {
        while ((i & -128) != 0) {
            writeRawByte((i & 127) | 128);
            i >>>= 7;
        }
        writeRawByte(i);
    }

    public final void writeRawVarint64(long j) throws IOException {
        while ((-128 & j) != 0) {
            writeRawByte((((int) j) & 127) | 128);
            j >>>= 7;
        }
        writeRawByte((int) j);
    }

    public final void writeRawLittleEndian32(int i) throws IOException {
        if (this.buffer.remaining() < 4) {
            throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putInt(i);
    }

    public final void writeRawLittleEndian64(long j) throws IOException {
        if (this.buffer.remaining() < 8) {
            throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putLong(j);
    }

    static int computeFieldSize(int i, int i2, Object obj) {
        switch (i2) {
            case 1:
                return computeDoubleSize(i, ((Double) obj).doubleValue());
            case 2:
                return computeFloatSize(i, ((Float) obj).floatValue());
            case 3:
                return computeInt64Size(i, ((Long) obj).longValue());
            case 4:
                return computeUInt64Size(i, ((Long) obj).longValue());
            case 5:
                return computeInt32Size(i, ((Integer) obj).intValue());
            case 6:
                return computeFixed64Size(i, ((Long) obj).longValue());
            case 7:
                return computeFixed32Size(i, ((Integer) obj).intValue());
            case 8:
                return computeBoolSize(i, ((Boolean) obj).booleanValue());
            case 9:
                return computeStringSize(i, (String) obj);
            case 10:
                return computeGroupSize(i, (MessageNano) obj);
            case 11:
                return computeMessageSize(i, (MessageNano) obj);
            case 12:
                return computeBytesSize(i, (byte[]) obj);
            case 13:
                return computeUInt32Size(i, ((Integer) obj).intValue());
            case 14:
                return computeEnumSize(i, ((Integer) obj).intValue());
            case 15:
                return computeSFixed32Size(i, ((Integer) obj).intValue());
            case 16:
                return computeSFixed64Size(i, ((Long) obj).longValue());
            case 17:
                return computeSInt32Size(i, ((Integer) obj).intValue());
            case 18:
                return computeSInt64Size(i, ((Long) obj).longValue());
            default:
                throw new IllegalArgumentException("Unknown type: ".concat(String.valueOf(i2)));
        }
    }

    /* access modifiers changed from: 0000 */
    public final void writeField(int i, int i2, Object obj) throws IOException {
        switch (i2) {
            case 1:
                writeDouble(i, ((Double) obj).doubleValue());
                return;
            case 2:
                writeFloat(i, ((Float) obj).floatValue());
                return;
            case 3:
                writeInt64(i, ((Long) obj).longValue());
                return;
            case 4:
                writeUInt64(i, ((Long) obj).longValue());
                return;
            case 5:
                writeInt32(i, ((Integer) obj).intValue());
                return;
            case 6:
                writeFixed64(i, ((Long) obj).longValue());
                return;
            case 7:
                writeFixed32(i, ((Integer) obj).intValue());
                return;
            case 8:
                writeBool(i, ((Boolean) obj).booleanValue());
                return;
            case 9:
                writeString(i, (String) obj);
                return;
            case 10:
                writeGroup(i, (MessageNano) obj);
                return;
            case 11:
                writeMessage(i, (MessageNano) obj);
                return;
            case 12:
                writeBytes(i, (byte[]) obj);
                return;
            case 13:
                writeUInt32(i, ((Integer) obj).intValue());
                return;
            case 14:
                writeEnum(i, ((Integer) obj).intValue());
                return;
            case 15:
                writeSFixed32(i, ((Integer) obj).intValue());
                return;
            case 16:
                writeSFixed64(i, ((Long) obj).longValue());
                return;
            case 17:
                writeSInt32(i, ((Integer) obj).intValue());
                return;
            case 18:
                writeSInt64(i, ((Long) obj).longValue());
                return;
            default:
                throw new IOException("Unknown type: ".concat(String.valueOf(i2)));
        }
    }
}
