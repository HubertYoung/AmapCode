package com.squareup.wire;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

final class WireInput {
    private static final String ENCOUNTERED_A_MALFORMED_VARINT = "WireInput encountered a malformed varint.";
    private static final String ENCOUNTERED_A_NEGATIVE_SIZE = "Encountered a negative size";
    private static final String INPUT_ENDED_UNEXPECTEDLY = "The input ended unexpectedly in the middle of a field";
    private static final String PROTOCOL_MESSAGE_CONTAINED_AN_INVALID_TAG_ZERO = "Protocol message contained an invalid tag (zero).";
    private static final String PROTOCOL_MESSAGE_END_GROUP_TAG_DID_NOT_MATCH_EXPECTED_TAG = "Protocol message end-group tag did not match expected tag.";
    public static final int RECURSION_LIMIT = 64;
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private int currentLimit = Integer.MAX_VALUE;
    private int lastTag;
    private int pos = 0;
    public int recursionDepth;
    private final BufferedSource source;

    public static int decodeZigZag32(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long decodeZigZag64(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public static WireInput newInstance(byte[] bArr) {
        return new WireInput(new Buffer().write(bArr));
    }

    public static WireInput newInstance(byte[] bArr, int i, int i2) {
        return new WireInput(new Buffer().write(bArr, i, i2));
    }

    public static WireInput newInstance(InputStream inputStream) {
        return new WireInput(Okio.buffer(Okio.source(inputStream)));
    }

    public static WireInput newInstance(Source source2) {
        return new WireInput(Okio.buffer(source2));
    }

    public final int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readVarint32();
        if (this.lastTag != 0) {
            return this.lastTag;
        }
        throw new IOException(PROTOCOL_MESSAGE_CONTAINED_AN_INVALID_TAG_ZERO);
    }

    public final void checkLastTagWas(int i) throws IOException {
        if (this.lastTag != i) {
            throw new IOException(PROTOCOL_MESSAGE_END_GROUP_TAG_DID_NOT_MATCH_EXPECTED_TAG);
        }
    }

    public final String readString() throws IOException {
        int readVarint32 = readVarint32();
        this.pos += readVarint32;
        return this.source.readString((long) readVarint32, UTF_8);
    }

    public final ByteString readBytes() throws IOException {
        return readBytes(readVarint32());
    }

    public final ByteString readBytes(int i) throws IOException {
        this.pos += i;
        long j = (long) i;
        this.source.require(j);
        return this.source.readByteString(j);
    }

    public final int readVarint32() throws IOException {
        byte b;
        this.pos++;
        byte readByte = this.source.readByte();
        if (readByte >= 0) {
            return readByte;
        }
        byte b2 = readByte & Byte.MAX_VALUE;
        this.pos++;
        byte readByte2 = this.source.readByte();
        if (readByte2 >= 0) {
            b = b2 | (readByte2 << 7);
        } else {
            byte b3 = b2 | ((readByte2 & Byte.MAX_VALUE) << 7);
            this.pos++;
            byte readByte3 = this.source.readByte();
            if (readByte3 >= 0) {
                b = b3 | (readByte3 << 14);
            } else {
                byte b4 = b3 | ((readByte3 & Byte.MAX_VALUE) << 14);
                this.pos++;
                byte readByte4 = this.source.readByte();
                if (readByte4 >= 0) {
                    b = b4 | (readByte4 << 21);
                } else {
                    byte b5 = b4 | ((readByte4 & Byte.MAX_VALUE) << 21);
                    this.pos++;
                    byte readByte5 = this.source.readByte();
                    b = b5 | (readByte5 << 28);
                    if (readByte5 < 0) {
                        for (int i = 0; i < 5; i++) {
                            this.pos++;
                            if (this.source.readByte() >= 0) {
                                return b;
                            }
                        }
                        throw new IOException(ENCOUNTERED_A_MALFORMED_VARINT);
                    }
                }
            }
        }
        return b;
    }

    public final long readVarint64() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            this.pos++;
            byte readByte = this.source.readByte();
            j |= ((long) (readByte & Byte.MAX_VALUE)) << i;
            if ((readByte & 128) == 0) {
                return j;
            }
        }
        throw new IOException(ENCOUNTERED_A_MALFORMED_VARINT);
    }

    public final int readFixed32() throws IOException {
        this.pos += 4;
        return this.source.readIntLe();
    }

    public final long readFixed64() throws IOException {
        this.pos += 8;
        return this.source.readLongLe();
    }

    private WireInput(BufferedSource bufferedSource) {
        this.source = bufferedSource;
    }

    public final int pushLimit(int i) throws IOException {
        if (i < 0) {
            throw new IOException(ENCOUNTERED_A_NEGATIVE_SIZE);
        }
        int i2 = i + this.pos;
        int i3 = this.currentLimit;
        if (i2 > i3) {
            throw new EOFException(INPUT_ENDED_UNEXPECTEDLY);
        }
        this.currentLimit = i2;
        return i3;
    }

    public final void popLimit(int i) {
        this.currentLimit = i;
    }

    private boolean isAtEnd() throws IOException {
        if (getPosition() == ((long) this.currentLimit)) {
            return true;
        }
        return this.source.exhausted();
    }

    public final long getPosition() {
        return (long) this.pos;
    }

    public final void skipGroup() throws IOException {
        int readTag;
        do {
            readTag = readTag();
            if (readTag == 0) {
                return;
            }
        } while (!skipField(readTag));
    }

    private boolean skipField(int i) throws IOException {
        switch (WireType.valueOf(i)) {
            case VARINT:
                readVarint64();
                return false;
            case FIXED32:
                readFixed32();
                return false;
            case FIXED64:
                readFixed64();
                return false;
            case LENGTH_DELIMITED:
                skip((long) readVarint32());
                return false;
            case START_GROUP:
                skipGroup();
                checkLastTagWas((i & -8) | WireType.END_GROUP.value());
                return false;
            case END_GROUP:
                return true;
            default:
                throw new AssertionError();
        }
    }

    private void skip(long j) throws IOException {
        this.pos = (int) (((long) this.pos) + j);
        this.source.skip(j);
    }
}
