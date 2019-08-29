package com.autonavi.link.adapter.endian;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LittleEndianOutputStream extends ByteOrderedOutputStream {
    private final byte[] writeBuffer = new byte[8];
    protected int written;

    public LittleEndianOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    private void incCount(int i) {
        int i2 = this.written + i;
        if (i2 < 0) {
            i2 = Integer.MAX_VALUE;
        }
        this.written = i2;
    }

    public void writeBoolean(boolean z) throws IOException {
        this.out.write(z ? 1 : 0);
        incCount(1);
    }

    public void writeByte(int i) throws IOException {
        this.out.write(i);
        incCount(1);
    }

    public void writeBytes(String str) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            this.out.write((byte) str.charAt(i));
        }
        incCount(length);
    }

    public void writeChar(int i) throws IOException {
        this.out.write((i >>> 0) & 255);
        this.out.write((i >>> 8) & 255);
        incCount(2);
    }

    public void writeChars(String str) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            this.out.write((charAt >>> 0) & 255);
            this.out.write((charAt >>> 8) & 255);
        }
        incCount(length * 2);
    }

    public void writeDouble(double d) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putDouble(d);
        this.out.write(allocate.array());
        incCount(8);
    }

    public void writeFloat(float f) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putFloat(f);
        this.out.write(allocate.array());
        incCount(4);
    }

    public void writeInt(int i) throws IOException {
        this.out.write((i >>> 0) & 255);
        this.out.write((i >>> 8) & 255);
        this.out.write((i >>> 16) & 255);
        this.out.write((i >>> 24) & 255);
        incCount(4);
    }

    public void writeLong(long j) throws IOException {
        this.writeBuffer[0] = (byte) ((int) (j >>> 0));
        this.writeBuffer[1] = (byte) ((int) (j >>> 8));
        this.writeBuffer[2] = (byte) ((int) (j >>> 16));
        this.writeBuffer[3] = (byte) ((int) (j >>> 24));
        this.writeBuffer[4] = (byte) ((int) (j >>> 32));
        this.writeBuffer[5] = (byte) ((int) (j >>> 40));
        this.writeBuffer[6] = (byte) ((int) (j >>> 48));
        this.writeBuffer[7] = (byte) ((int) (j >>> 56));
        this.out.write(this.writeBuffer, 0, 8);
        incCount(8);
    }

    public void writeShort(int i) throws IOException {
        this.out.write((i >>> 0) & 255);
        this.out.write((i >>> 8) & 255);
        incCount(2);
    }

    public void writeUTF(String str) throws IOException {
        int i;
        int i2;
        int length = str.length();
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char charAt = str.charAt(i4);
            i3 = (charAt <= 0 || charAt > 127) ? charAt > 2047 ? i3 + 3 : i3 + 2 : i3 + 1;
        }
        if (i3 > 65535) {
            StringBuilder sb = new StringBuilder("encoded string too long: ");
            sb.append(i3);
            sb.append(" bytes");
            throw new UTFDataFormatException(sb.toString());
        }
        int i5 = i3 + 2;
        byte[] bArr = new byte[i5];
        bArr[0] = (byte) ((i3 >>> 0) & 255);
        bArr[1] = (byte) ((i3 >>> 8) & 255);
        int i6 = 0;
        int i7 = 2;
        while (i6 < length) {
            char charAt2 = str.charAt(i6);
            if (charAt2 <= 0 || charAt2 > 127) {
                break;
            }
            bArr[i] = (byte) charAt2;
            i6++;
            i7 = i + 1;
        }
        while (i6 < length) {
            char charAt3 = str.charAt(i6);
            if (charAt3 > 0 && charAt3 <= 127) {
                i2 = i + 1;
                bArr[i] = (byte) charAt3;
            } else if (charAt3 > 2047) {
                int i8 = i + 1;
                bArr[i] = (byte) (((charAt3 >> 12) & 15) | 224);
                int i9 = i8 + 1;
                bArr[i8] = (byte) (((charAt3 >> 6) & 63) | 128);
                i2 = i9 + 1;
                bArr[i9] = (byte) (((charAt3 >> 0) & 63) | 128);
            } else {
                int i10 = i + 1;
                bArr[i] = (byte) (((charAt3 >> 6) & 31) | 192);
                i = i10 + 1;
                bArr[i10] = (byte) (((charAt3 >> 0) & 63) | 128);
                i6++;
            }
            i = i2;
            i6++;
        }
        this.out.write(bArr, 0, i5);
    }
}
