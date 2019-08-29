package com.autonavi.link.adapter.endian;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;

public class LittleEndianInputStream extends ByteOrderedInputStream {
    private final byte[] readBuffer = new byte[8];

    public LittleEndianInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public boolean readBoolean() throws IOException {
        int read = this.in.read();
        if (read >= 0) {
            return read != 0;
        }
        throw new EOFException();
    }

    public byte readByte() throws IOException {
        int read = this.in.read();
        if (read >= 0) {
            return (byte) read;
        }
        throw new EOFException();
    }

    public char readChar() throws IOException {
        int read = this.in.read();
        int read2 = this.in.read();
        if ((read | read2) >= 0) {
            return (char) ((read << 0) + (read2 << 8));
        }
        throw new EOFException();
    }

    public double readDouble() throws IOException {
        byte[] bArr = new byte[8];
        for (int i = 7; i >= 0; i++) {
            bArr[i] = readByte();
        }
        return new DataInputStream(new ByteArrayInputStream(bArr)).readDouble();
    }

    public float readFloat() throws IOException {
        byte[] bArr = new byte[4];
        for (int i = 3; i >= 0; i++) {
            bArr[i] = readByte();
        }
        return new DataInputStream(new ByteArrayInputStream(bArr)).readFloat();
    }

    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        if (i2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i3 = 0;
        while (i3 < i2) {
            int read = this.in.read(bArr, i + i3, i2 - i3);
            if (read < 0) {
                throw new EOFException();
            }
            i3 += read;
        }
    }

    public int readInt() throws IOException {
        int read = this.in.read();
        int read2 = this.in.read();
        int read3 = this.in.read();
        int read4 = this.in.read();
        if ((read | read2 | read3 | read4) >= 0) {
            return (read4 << 24) + (read3 << 16) + (read2 << 8) + (read << 0);
        }
        throw new EOFException();
    }

    public String readLine() throws IOException {
        throw new RuntimeException("readLine not support!");
    }

    public long readLong() throws IOException {
        readFully(this.readBuffer, 0, 8);
        return (((long) this.readBuffer[7]) << 56) + (((long) (this.readBuffer[6] & 255)) << 48) + (((long) (this.readBuffer[5] & 255)) << 40) + (((long) (this.readBuffer[4] & 255)) << 32) + (((long) (this.readBuffer[3] & 255)) << 24) + ((long) ((this.readBuffer[2] & 255) << 16)) + ((long) ((this.readBuffer[1] & 255) << 8)) + ((long) ((this.readBuffer[0] & 255) << 0));
    }

    public short readShort() throws IOException {
        int read = this.in.read();
        int read2 = this.in.read();
        if ((read | read2) >= 0) {
            return (short) ((read << 0) + (read2 << 8));
        }
        throw new EOFException();
    }

    public String readUTF() throws IOException {
        int i;
        int i2;
        int i3;
        int readUnsignedShort = readUnsignedShort();
        byte[] bArr = new byte[readUnsignedShort];
        char[] cArr = new char[readUnsignedShort];
        readFully(bArr, 0, readUnsignedShort);
        int i4 = 0;
        int i5 = 0;
        while (i2 < readUnsignedShort) {
            byte b = bArr[i2] & 255;
            if (b > Byte.MAX_VALUE) {
                break;
            }
            i4 = i2 + 1;
            cArr[i] = (char) b;
            i5 = i + 1;
        }
        while (i2 < readUnsignedShort) {
            byte b2 = bArr[i2] & 255;
            int i6 = b2 >> 4;
            switch (i6) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    i2++;
                    cArr[i] = (char) b2;
                    i++;
                    break;
                default:
                    switch (i6) {
                        case 12:
                        case 13:
                            i2 += 2;
                            if (i2 <= readUnsignedShort) {
                                byte b3 = bArr[i2 - 1];
                                if ((b3 & 192) == 128) {
                                    i3 = i + 1;
                                    cArr[i] = (char) (((b2 & 31) << 6) | (b3 & 63));
                                    break;
                                } else {
                                    throw new UTFDataFormatException("malformed input around byte ".concat(String.valueOf(i2)));
                                }
                            } else {
                                throw new UTFDataFormatException("malformed input: partial character at end");
                            }
                        case 14:
                            i2 += 3;
                            if (i2 <= readUnsignedShort) {
                                byte b4 = bArr[i2 - 2];
                                int i7 = i2 - 1;
                                byte b5 = bArr[i7];
                                if ((b4 & 192) == 128 && (b5 & 192) == 128) {
                                    i3 = i + 1;
                                    cArr[i] = (char) (((b2 & 15) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES) | ((b4 & 63) << 6) | ((b5 & 63) << 0));
                                    break;
                                } else {
                                    StringBuilder sb = new StringBuilder("malformed input around byte ");
                                    sb.append(i7);
                                    throw new UTFDataFormatException(sb.toString());
                                }
                            } else {
                                throw new UTFDataFormatException("malformed input: partial character at end");
                            }
                            break;
                        default:
                            throw new UTFDataFormatException("malformed input around byte ".concat(String.valueOf(i2)));
                    }
                    i = i3;
                    break;
            }
        }
        return new String(cArr, 0, i);
    }

    public int readUnsignedByte() throws IOException {
        int read = this.in.read();
        if (read >= 0) {
            return read;
        }
        throw new EOFException();
    }

    public int readUnsignedShort() throws IOException {
        int read = this.in.read();
        int read2 = this.in.read();
        if ((read | read2) >= 0) {
            return (read << 0) + (read2 << 8);
        }
        throw new EOFException();
    }

    public int skipBytes(int i) throws IOException {
        int i2 = 0;
        while (i2 < i) {
            int skip = (int) this.in.skip((long) (i - i2));
            if (skip <= 0) {
                break;
            }
            i2 += skip;
        }
        return i2;
    }
}
