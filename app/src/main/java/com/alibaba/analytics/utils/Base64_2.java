package com.alibaba.analytics.utils;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class Base64_2 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte[] _ORDERED_ALPHABET = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] _ORDERED_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 0, -9, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -9, -9, -9, -1, -9, -9, -9, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, -9, -9, -9, -9, 37, -9, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, 62, 63, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _STANDARD_ALPHABET = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] _STANDARD_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};
    private static final byte[] _URL_SAFE_ALPHABET = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] _URL_SAFE_DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, WHITE_SPACE_ENC, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, WHITE_SPACE_ENC, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, EQUALS_SIGN, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9};

    public static class InputStream extends FilterInputStream {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream inputStream) {
            this(inputStream, 0);
        }

        public InputStream(java.io.InputStream inputStream, int i) {
            super(inputStream);
            this.options = i;
            boolean z = true;
            this.breakLines = (i & 8) > 0;
            this.encode = (i & 1) <= 0 ? false : z;
            this.bufferLength = this.encode ? 4 : 3;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = 0;
            this.decodabet = Base64_2.getDecodabet(i);
        }

        public int read() throws IOException {
            int read;
            if (this.position < 0) {
                if (this.encode) {
                    byte[] bArr = new byte[3];
                    int i = 0;
                    for (int i2 = 0; i2 < 3; i2++) {
                        int read2 = this.in.read();
                        if (read2 < 0) {
                            break;
                        }
                        bArr[i2] = (byte) read2;
                        i++;
                    }
                    if (i <= 0) {
                        return -1;
                    }
                    Base64_2.encode3to4(bArr, 0, i, this.buffer, 0, this.options);
                    this.position = 0;
                    this.numSigBytes = 4;
                } else {
                    byte[] bArr2 = new byte[4];
                    int i3 = 0;
                    while (i3 < 4) {
                        do {
                            read = this.in.read();
                            if (read < 0) {
                                break;
                            }
                        } while (this.decodabet[read & 127] <= -5);
                        if (read < 0) {
                            break;
                        }
                        bArr2[i3] = (byte) read;
                        i3++;
                    }
                    if (i3 == 4) {
                        this.numSigBytes = Base64_2.decode4to3(bArr2, 0, this.buffer, 0, this.options);
                        this.position = 0;
                    } else if (i3 == 0) {
                        return -1;
                    } else {
                        throw new IOException("Improperly padded Base64 input.");
                    }
                }
            }
            if (this.position < 0) {
                throw new IOException("Error in Base64 code reading stream.");
            } else if (this.position >= this.numSigBytes) {
                return -1;
            } else {
                if (!this.encode || !this.breakLines || this.lineLength < 76) {
                    this.lineLength++;
                    byte[] bArr3 = this.buffer;
                    int i4 = this.position;
                    this.position = i4 + 1;
                    byte b = bArr3[i4];
                    if (this.position >= this.bufferLength) {
                        this.position = -1;
                    }
                    return b & 255;
                }
                this.lineLength = 0;
                return 10;
            }
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    break;
                }
                int read = read();
                if (read >= 0) {
                    bArr[i + i3] = (byte) read;
                    i3++;
                } else if (i3 == 0) {
                    return -1;
                }
            }
            return i3;
        }
    }

    public static class OutputStream extends FilterOutputStream {
        private byte[] b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream outputStream) {
            this(outputStream, 1);
        }

        public OutputStream(java.io.OutputStream outputStream, int i) {
            super(outputStream);
            boolean z = true;
            this.breakLines = (i & 8) != 0;
            this.encode = (i & 1) == 0 ? false : z;
            this.bufferLength = this.encode ? 3 : 4;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.b4 = new byte[4];
            this.options = i;
            this.decodabet = Base64_2.getDecodabet(i);
        }

        public void write(int i) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(i);
                return;
            }
            if (this.encode) {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64_2.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                    this.lineLength += 4;
                    if (this.breakLines && this.lineLength >= 76) {
                        this.out.write(10);
                        this.lineLength = 0;
                    }
                    this.position = 0;
                }
            } else {
                int i3 = i & 127;
                if (this.decodabet[i3] > -5) {
                    byte[] bArr2 = this.buffer;
                    int i4 = this.position;
                    this.position = i4 + 1;
                    bArr2[i4] = (byte) i;
                    if (this.position >= this.bufferLength) {
                        this.out.write(this.b4, 0, Base64_2.decode4to3(this.buffer, 0, this.b4, 0, this.options));
                        this.position = 0;
                    }
                } else if (this.decodabet[i3] != -5) {
                    throw new IOException("Invalid character in Base64 data.");
                }
            }
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(bArr, i, i2);
                return;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                write(bArr[i + i3]);
            }
        }

        public void flushBase64() throws IOException {
            if (this.position <= 0) {
                return;
            }
            if (this.encode) {
                this.out.write(Base64_2.encode3to4(this.b4, this.buffer, this.position, this.options));
                this.position = 0;
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        public void close() throws IOException {
            flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            flushBase64();
            this.suspendEncoding = true;
        }

        public void resumeEncoding() {
            this.suspendEncoding = false;
        }
    }

    private static final byte[] getAlphabet(int i) {
        if ((i & 16) == 16) {
            return _URL_SAFE_ALPHABET;
        }
        if ((i & 32) == 32) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    /* access modifiers changed from: private */
    public static final byte[] getDecodabet(int i) {
        if ((i & 16) == 16) {
            return _URL_SAFE_DECODABET;
        }
        if ((i & 32) == 32) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    private Base64_2() {
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] bArr, byte[] bArr2, int i, int i2) {
        encode3to4(bArr2, 0, i, bArr, 0, i2);
        return bArr;
    }

    /* access modifiers changed from: private */
    public static byte[] encode3to4(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        byte[] alphabet = getAlphabet(i4);
        int i5 = 0;
        int i6 = (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0) | (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0);
        if (i2 > 2) {
            i5 = (bArr[i + 2] << 24) >>> 24;
        }
        int i7 = i6 | i5;
        switch (i2) {
            case 1:
                bArr2[i3] = alphabet[i7 >>> 18];
                bArr2[i3 + 1] = alphabet[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = EQUALS_SIGN;
                bArr2[i3 + 3] = EQUALS_SIGN;
                return bArr2;
            case 2:
                bArr2[i3] = alphabet[i7 >>> 18];
                bArr2[i3 + 1] = alphabet[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = alphabet[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = EQUALS_SIGN;
                return bArr2;
            case 3:
                bArr2[i3] = alphabet[i7 >>> 18];
                bArr2[i3 + 1] = alphabet[(i7 >>> 12) & 63];
                bArr2[i3 + 2] = alphabet[(i7 >>> 6) & 63];
                bArr2[i3 + 3] = alphabet[i7 & 63];
                return bArr2;
            default:
                return bArr2;
        }
    }

    public static void encode(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        byte[] bArr = new byte[3];
        byte[] bArr2 = new byte[4];
        while (byteBuffer.hasRemaining()) {
            int min = Math.min(3, byteBuffer.remaining());
            byteBuffer.get(bArr, 0, min);
            encode3to4(bArr2, bArr, min, 0);
            byteBuffer2.put(bArr2);
        }
    }

    public static void encode(ByteBuffer byteBuffer, CharBuffer charBuffer) {
        byte[] bArr = new byte[3];
        byte[] bArr2 = new byte[4];
        while (byteBuffer.hasRemaining()) {
            int min = Math.min(3, byteBuffer.remaining());
            byteBuffer.get(bArr, 0, min);
            encode3to4(bArr2, bArr, min, 0);
            for (int i = 0; i < 4; i++) {
                charBuffer.put((char) (bArr2[i] & 255));
            }
        }
    }

    public static String encodeObject(Serializable serializable) throws IOException {
        return encodeObject(serializable, 0);
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.ObjectOutputStream] */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.io.OutputStream, java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r0v5, types: [java.io.ObjectOutputStream] */
    /* JADX WARNING: type inference failed for: r6v11, types: [java.io.ObjectOutputStream] */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.io.ObjectOutputStream] */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r0v21 */
    /* JADX WARNING: type inference failed for: r0v22 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: type inference failed for: r0v24 */
    /* JADX WARNING: type inference failed for: r0v25 */
    /* JADX WARNING: Can't wrap try/catch for region: R(11:18|19|53|54|55|56|57|58|59|60|61) */
    /* JADX WARNING: Can't wrap try/catch for region: R(17:6|7|8|(5:10|11|12|13|14)(3:22|23|24)|25|26|27|28|29|30|31|32|33|34|35|36|37) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0045 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0048 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x004b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x004e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x007f */
    /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x0082 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x0085 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v11
      assigns: []
      uses: []
      mth insns count: 79
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 14 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String encodeObject(java.io.Serializable r5, int r6) throws java.io.IOException {
        /*
            if (r5 != 0) goto L_0x000a
            java.lang.NullPointerException r5 = new java.lang.NullPointerException
            java.lang.String r6 = "Cannot serialize a null object."
            r5.<init>(r6)
            throw r5
        L_0x000a:
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0073, all -> 0x006e }
            r1.<init>()     // Catch:{ IOException -> 0x0073, all -> 0x006e }
            com.alibaba.analytics.utils.Base64_2$OutputStream r2 = new com.alibaba.analytics.utils.Base64_2$OutputStream     // Catch:{ IOException -> 0x0068, all -> 0x0064 }
            r3 = r6 | 1
            r2.<init>(r1, r3)     // Catch:{ IOException -> 0x0068, all -> 0x0064 }
            r6 = r6 & 2
            if (r6 == 0) goto L_0x0037
            java.util.zip.GZIPOutputStream r6 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x0032, all -> 0x002f }
            r6.<init>(r2)     // Catch:{ IOException -> 0x0032, all -> 0x002f }
            java.io.ObjectOutputStream r3 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x002a, all -> 0x0027 }
            r3.<init>(r6)     // Catch:{ IOException -> 0x002a, all -> 0x0027 }
            r0 = r3
            goto L_0x003f
        L_0x0027:
            r5 = move-exception
            goto L_0x007c
        L_0x002a:
            r5 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0077
        L_0x002f:
            r5 = move-exception
            r6 = r0
            goto L_0x007c
        L_0x0032:
            r5 = move-exception
            r6 = r0
            r0 = r1
            r1 = r6
            goto L_0x0077
        L_0x0037:
            java.io.ObjectOutputStream r6 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0032, all -> 0x002f }
            r6.<init>(r2)     // Catch:{ IOException -> 0x0032, all -> 0x002f }
            r4 = r0
            r0 = r6
            r6 = r4
        L_0x003f:
            r0.writeObject(r5)     // Catch:{ IOException -> 0x002a, all -> 0x0027 }
            r0.close()     // Catch:{ Exception -> 0x0045 }
        L_0x0045:
            r6.close()     // Catch:{ Exception -> 0x0048 }
        L_0x0048:
            r2.close()     // Catch:{ Exception -> 0x004b }
        L_0x004b:
            r1.close()     // Catch:{ Exception -> 0x004e }
        L_0x004e:
            java.lang.String r5 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x005a }
            byte[] r6 = r1.toByteArray()     // Catch:{ UnsupportedEncodingException -> 0x005a }
            java.lang.String r0 = "US-ASCII"
            r5.<init>(r6, r0)     // Catch:{ UnsupportedEncodingException -> 0x005a }
            return r5
        L_0x005a:
            java.lang.String r5 = new java.lang.String
            byte[] r6 = r1.toByteArray()
            r5.<init>(r6)
            return r5
        L_0x0064:
            r5 = move-exception
            r6 = r0
            r2 = r6
            goto L_0x007c
        L_0x0068:
            r5 = move-exception
            r6 = r0
            r2 = r6
            r0 = r1
            r1 = r2
            goto L_0x0077
        L_0x006e:
            r5 = move-exception
            r6 = r0
            r1 = r6
            r2 = r1
            goto L_0x007c
        L_0x0073:
            r5 = move-exception
            r6 = r0
            r1 = r6
            r2 = r1
        L_0x0077:
            throw r5     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r5 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x007c:
            r0.close()     // Catch:{ Exception -> 0x007f }
        L_0x007f:
            r6.close()     // Catch:{ Exception -> 0x0082 }
        L_0x0082:
            r2.close()     // Catch:{ Exception -> 0x0085 }
        L_0x0085:
            r1.close()     // Catch:{ Exception -> 0x0088 }
        L_0x0088:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.Base64_2.encodeObject(java.io.Serializable, int):java.lang.String");
    }

    public static String encodeBytes(byte[] bArr) {
        try {
            return encodeBytes(bArr, 0, bArr.length, 0);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bArr, int i) throws IOException {
        return encodeBytes(bArr, 0, bArr.length, i);
    }

    public static String encodeBytes(byte[] bArr, int i, int i2) {
        try {
            return encodeBytes(bArr, i, i2, 0);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bArr, int i, int i2, int i3) throws IOException {
        byte[] encodeBytesToBytes = encodeBytesToBytes(bArr, i, i2, i3);
        try {
            return new String(encodeBytesToBytes, "US-ASCII");
        } catch (UnsupportedEncodingException unused) {
            return new String(encodeBytesToBytes);
        }
    }

    public static byte[] encodeBytesToBytes(byte[] bArr) {
        try {
            return encodeBytesToBytes(bArr, 0, bArr.length, 0);
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r4v4, types: [java.util.zip.GZIPOutputStream] */
    /* JADX WARNING: type inference failed for: r3v6, types: [com.alibaba.analytics.utils.Base64_2$OutputStream] */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11, types: [java.io.OutputStream, com.alibaba.analytics.utils.Base64_2$OutputStream] */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9, types: [java.util.zip.GZIPOutputStream] */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v19 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* JADX WARNING: type inference failed for: r3v21 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: Can't wrap try/catch for region: R(10:35|36|46|54|55|56|57|58|59|60) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:23|24|25|26|27|28|(2:29|30)|31|33) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x007d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0080 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:56:0x00a7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:58:0x00aa */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v2
      assigns: []
      uses: []
      mth insns count: 133
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] encodeBytesToBytes(byte[] r18, int r19, int r20, int r21) throws java.io.IOException {
        /*
            r7 = r18
            r8 = r19
            r9 = r20
            if (r7 != 0) goto L_0x0010
            java.lang.NullPointerException r1 = new java.lang.NullPointerException
            java.lang.String r2 = "Cannot serialize a null array."
            r1.<init>(r2)
            throw r1
        L_0x0010:
            if (r8 >= 0) goto L_0x0022
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Cannot have negative offset: "
            java.lang.String r3 = java.lang.String.valueOf(r19)
            java.lang.String r2 = r2.concat(r3)
            r1.<init>(r2)
            throw r1
        L_0x0022:
            if (r9 >= 0) goto L_0x0034
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Cannot have length offset: "
            java.lang.String r3 = java.lang.String.valueOf(r20)
            java.lang.String r2 = r2.concat(r3)
            r1.<init>(r2)
            throw r1
        L_0x0034:
            int r1 = r8 + r9
            int r2 = r7.length
            r10 = 1
            r11 = 0
            if (r1 <= r2) goto L_0x005e
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Cannot have offset of %d and length of %d with array of length %d"
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r19)
            r3[r11] = r4
            java.lang.Integer r4 = java.lang.Integer.valueOf(r20)
            r3[r10] = r4
            int r4 = r7.length
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r5 = 2
            r3[r5] = r4
            java.lang.String r2 = java.lang.String.format(r2, r3)
            r1.<init>(r2)
            throw r1
        L_0x005e:
            r1 = r21 & 2
            if (r1 == 0) goto L_0x00ae
            r1 = 0
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x009c, all -> 0x0096 }
            r2.<init>()     // Catch:{ IOException -> 0x009c, all -> 0x0096 }
            com.alibaba.analytics.utils.Base64_2$OutputStream r3 = new com.alibaba.analytics.utils.Base64_2$OutputStream     // Catch:{ IOException -> 0x0093, all -> 0x0090 }
            r4 = r21 | 1
            r3.<init>(r2, r4)     // Catch:{ IOException -> 0x0093, all -> 0x0090 }
            java.util.zip.GZIPOutputStream r4 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x008d, all -> 0x008a }
            r4.<init>(r3)     // Catch:{ IOException -> 0x008d, all -> 0x008a }
            r4.write(r7, r8, r9)     // Catch:{ IOException -> 0x0088 }
            r4.close()     // Catch:{ IOException -> 0x0088 }
            r4.close()     // Catch:{ Exception -> 0x007d }
        L_0x007d:
            r3.close()     // Catch:{ Exception -> 0x0080 }
        L_0x0080:
            r2.close()     // Catch:{ Exception -> 0x0083 }
        L_0x0083:
            byte[] r1 = r2.toByteArray()
            return r1
        L_0x0088:
            r0 = move-exception
            goto L_0x00a0
        L_0x008a:
            r0 = move-exception
            r4 = r1
            goto L_0x009a
        L_0x008d:
            r0 = move-exception
            r4 = r1
            goto L_0x00a0
        L_0x0090:
            r0 = move-exception
            r3 = r1
            goto L_0x0099
        L_0x0093:
            r0 = move-exception
            r3 = r1
            goto L_0x009f
        L_0x0096:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x0099:
            r4 = r3
        L_0x009a:
            r1 = r0
            goto L_0x00a4
        L_0x009c:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x009f:
            r4 = r3
        L_0x00a0:
            r1 = r0
            throw r1     // Catch:{ all -> 0x00a2 }
        L_0x00a2:
            r0 = move-exception
            goto L_0x009a
        L_0x00a4:
            r4.close()     // Catch:{ Exception -> 0x00a7 }
        L_0x00a7:
            r3.close()     // Catch:{ Exception -> 0x00aa }
        L_0x00aa:
            r2.close()     // Catch:{ Exception -> 0x00ad }
        L_0x00ad:
            throw r1
        L_0x00ae:
            r1 = r21 & 8
            if (r1 == 0) goto L_0x00b4
            r12 = 1
            goto L_0x00b5
        L_0x00b4:
            r12 = 0
        L_0x00b5:
            int r1 = r9 / 3
            r13 = 4
            int r1 = r1 * 4
            int r2 = r9 % 3
            if (r2 <= 0) goto L_0x00c0
            r2 = 4
            goto L_0x00c1
        L_0x00c0:
            r2 = 0
        L_0x00c1:
            int r1 = r1 + r2
            if (r12 == 0) goto L_0x00c7
            int r2 = r1 / 76
            int r1 = r1 + r2
        L_0x00c7:
            byte[] r14 = new byte[r1]
            int r6 = r9 + -2
            r5 = 0
            r15 = 0
            r16 = 0
        L_0x00cf:
            if (r5 >= r6) goto L_0x00fb
            int r2 = r5 + r8
            r3 = 3
            r1 = r7
            r4 = r14
            r11 = r5
            r5 = r15
            r17 = r6
            r6 = r21
            encode3to4(r1, r2, r3, r4, r5, r6)
            int r1 = r16 + 4
            if (r12 == 0) goto L_0x00f2
            r2 = 76
            if (r1 < r2) goto L_0x00f2
            int r1 = r15 + 4
            r2 = 10
            r14[r1] = r2
            int r15 = r15 + 1
            r16 = 0
            goto L_0x00f4
        L_0x00f2:
            r16 = r1
        L_0x00f4:
            int r5 = r11 + 3
            int r15 = r15 + r13
            r6 = r17
            r11 = 0
            goto L_0x00cf
        L_0x00fb:
            r11 = r5
            if (r11 >= r9) goto L_0x010c
            int r2 = r11 + r8
            int r3 = r9 - r11
            r1 = r7
            r4 = r14
            r5 = r15
            r6 = r21
            encode3to4(r1, r2, r3, r4, r5, r6)
            int r15 = r15 + 4
        L_0x010c:
            r1 = r15
            int r2 = r14.length
            int r2 = r2 - r10
            if (r1 > r2) goto L_0x0118
            byte[] r2 = new byte[r1]
            r3 = 0
            java.lang.System.arraycopy(r14, r3, r2, r3, r1)
            return r2
        L_0x0118:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.Base64_2.encodeBytesToBytes(byte[], int, int, int):byte[]");
    }

    /* access modifiers changed from: private */
    public static int decode4to3(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (bArr == null) {
            throw new NullPointerException("Source array was null.");
        } else if (bArr2 == null) {
            throw new NullPointerException("Destination array was null.");
        } else {
            if (i >= 0) {
                int i4 = i + 3;
                if (i4 < bArr.length) {
                    if (i2 >= 0) {
                        int i5 = i2 + 2;
                        if (i5 < bArr2.length) {
                            byte[] decodabet = getDecodabet(i3);
                            int i6 = i + 2;
                            if (bArr[i6] == 61) {
                                bArr2[i2] = (byte) ((((decodabet[bArr[i + 1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES) | ((decodabet[bArr[i]] & 255) << 18)) >>> 16);
                                return 1;
                            } else if (bArr[i4] == 61) {
                                int i7 = (decodabet[bArr[i + 1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                                int i8 = ((decodabet[bArr[i6]] & 255) << 6) | i7 | ((decodabet[bArr[i]] & 255) << 18);
                                bArr2[i2] = (byte) (i8 >>> 16);
                                bArr2[i2 + 1] = (byte) (i8 >>> 8);
                                return 2;
                            } else {
                                int i9 = (decodabet[bArr[i + 1]] & 255) << ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                                byte b = (decodabet[bArr[i4]] & 255) | i9 | ((decodabet[bArr[i]] & 255) << 18) | ((decodabet[bArr[i6]] & 255) << 6);
                                bArr2[i2] = (byte) (b >> 16);
                                bArr2[i2 + 1] = (byte) (b >> 8);
                                bArr2[i5] = (byte) b;
                                return 3;
                            }
                        }
                    }
                    throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[]{Integer.valueOf(bArr2.length), Integer.valueOf(i2)}));
                }
            }
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i)}));
        }
    }

    public static byte[] decode(byte[] bArr) throws IOException {
        return decode(bArr, 0, bArr.length, 0);
    }

    public static String encodeBase64String(byte[] bArr) {
        return (bArr == null || bArr.length <= 0) ? "" : encodeBytes(bArr);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) throws IOException {
        if (bArr == null) {
            throw new NullPointerException("Cannot decode null source array.");
        }
        if (i >= 0) {
            int i4 = i + i2;
            if (i4 <= bArr.length) {
                if (i2 == 0) {
                    return new byte[0];
                }
                if (i2 < 4) {
                    throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was ".concat(String.valueOf(i2)));
                }
                byte[] decodabet = getDecodabet(i3);
                byte[] bArr2 = new byte[((i2 * 3) / 4)];
                byte[] bArr3 = new byte[4];
                int i5 = 0;
                int i6 = 0;
                while (i < i4) {
                    byte b = decodabet[bArr[i] & 255];
                    if (b >= -5) {
                        if (b >= -1) {
                            int i7 = i5 + 1;
                            bArr3[i5] = bArr[i];
                            if (i7 > 3) {
                                i6 += decode4to3(bArr3, 0, bArr2, i6, i3);
                                if (bArr[i] == 61) {
                                    break;
                                }
                                i5 = 0;
                            } else {
                                i5 = i7;
                            }
                        }
                        i++;
                    } else {
                        throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[]{Integer.valueOf(bArr[i] & 255), Integer.valueOf(i)}));
                    }
                }
                byte[] bArr4 = new byte[i6];
                System.arraycopy(bArr2, 0, bArr4, 0, i6);
                return bArr4;
            }
        }
        throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    public static byte[] decode(String str) throws IOException {
        return decode(str, 0);
    }

    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r0v8, types: [java.util.zip.GZIPInputStream] */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.util.zip.GZIPInputStream] */
    /* JADX WARNING: type inference failed for: r0v10, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.util.zip.GZIPInputStream] */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: Can't wrap try/catch for region: R(10:60|61|62|63|64|65|66|67|68|69) */
    /* JADX WARNING: Can't wrap try/catch for region: R(16:19|20|21|22|23|24|(3:25|26|(1:28)(1:72))|29|30|31|32|33|34|35|36|78) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:52|53|54|55|56|57|58|59|79) */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        return r5;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0061 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x0064 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:56:0x0087 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:58:0x008a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x0094 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x0097 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v3
      assigns: []
      uses: []
      mth insns count: 85
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decode(java.lang.String r5, int r6) throws java.io.IOException {
        /*
            if (r5 != 0) goto L_0x000a
            java.lang.NullPointerException r5 = new java.lang.NullPointerException
            java.lang.String r6 = "Input string was null."
            r5.<init>(r6)
            throw r5
        L_0x000a:
            java.lang.String r0 = "US-ASCII"
            byte[] r0 = r5.getBytes(r0)     // Catch:{ UnsupportedEncodingException -> 0x0011 }
            goto L_0x0015
        L_0x0011:
            byte[] r0 = r5.getBytes()
        L_0x0015:
            int r5 = r0.length
            r1 = 0
            byte[] r5 = decode(r0, r1, r5, r6)
            r0 = 4
            r6 = r6 & r0
            r2 = 1
            if (r6 == 0) goto L_0x0022
            r6 = 1
            goto L_0x0023
        L_0x0022:
            r6 = 0
        L_0x0023:
            if (r5 == 0) goto L_0x009b
            int r3 = r5.length
            if (r3 < r0) goto L_0x009b
            if (r6 != 0) goto L_0x009b
            byte r6 = r5[r1]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r0 = r5[r2]
            int r0 = r0 << 8
            r2 = 65280(0xff00, float:9.1477E-41)
            r0 = r0 & r2
            r6 = r6 | r0
            r0 = 35615(0x8b1f, float:4.9907E-41)
            if (r0 != r6) goto L_0x009b
            r6 = 2048(0x800, float:2.87E-42)
            byte[] r6 = new byte[r6]
            r0 = 0
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x007e, all -> 0x007a }
            r2.<init>()     // Catch:{ IOException -> 0x007e, all -> 0x007a }
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r3.<init>(r5)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x006f, all -> 0x006d }
            r4.<init>(r3)     // Catch:{ IOException -> 0x006f, all -> 0x006d }
        L_0x0050:
            int r0 = r4.read(r6)     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            if (r0 < 0) goto L_0x005a
            r2.write(r6, r1, r0)     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            goto L_0x0050
        L_0x005a:
            byte[] r6 = r2.toByteArray()     // Catch:{ IOException -> 0x006b, all -> 0x0069 }
            r2.close()     // Catch:{ Exception -> 0x0061 }
        L_0x0061:
            r4.close()     // Catch:{ Exception -> 0x0064 }
        L_0x0064:
            r3.close()     // Catch:{ Exception -> 0x0067 }
        L_0x0067:
            r5 = r6
            goto L_0x009b
        L_0x0069:
            r5 = move-exception
            goto L_0x0090
        L_0x006b:
            r6 = move-exception
            goto L_0x0078
        L_0x006d:
            r5 = move-exception
            goto L_0x0091
        L_0x006f:
            r6 = move-exception
            r4 = r0
            goto L_0x0078
        L_0x0072:
            r5 = move-exception
            r3 = r0
            goto L_0x0091
        L_0x0075:
            r6 = move-exception
            r3 = r0
            r4 = r3
        L_0x0078:
            r0 = r2
            goto L_0x0081
        L_0x007a:
            r5 = move-exception
            r2 = r0
            r3 = r2
            goto L_0x0091
        L_0x007e:
            r6 = move-exception
            r3 = r0
            r4 = r3
        L_0x0081:
            r6.printStackTrace()     // Catch:{ all -> 0x008e }
            r0.close()     // Catch:{ Exception -> 0x0087 }
        L_0x0087:
            r4.close()     // Catch:{ Exception -> 0x008a }
        L_0x008a:
            r3.close()     // Catch:{ Exception -> 0x009b }
            goto L_0x009b
        L_0x008e:
            r5 = move-exception
            r2 = r0
        L_0x0090:
            r0 = r4
        L_0x0091:
            r2.close()     // Catch:{ Exception -> 0x0094 }
        L_0x0094:
            r0.close()     // Catch:{ Exception -> 0x0097 }
        L_0x0097:
            r3.close()     // Catch:{ Exception -> 0x009a }
        L_0x009a:
            throw r5
        L_0x009b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.Base64_2.decode(java.lang.String, int):byte[]");
    }

    public static Object decodeToObject(String str) throws IOException, ClassNotFoundException {
        return decodeToObject(str, 0, null);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.ClassLoader] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.ObjectInputStream] */
    /* JADX WARNING: type inference failed for: r0v0, types: [java.io.ByteArrayInputStream] */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.ByteArrayInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|(2:4|5)(1:13)|6|14|15|16|17|18|19) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:7|8|32|33|34|35|36) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x002a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x003d */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v3
      assigns: []
      uses: []
      mth insns count: 44
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object decodeToObject(java.lang.String r1, int r2, final java.lang.ClassLoader r3) throws java.io.IOException, java.lang.ClassNotFoundException {
        /*
            byte[] r1 = decode(r1, r2)
            r2 = 0
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x0034, ClassNotFoundException -> 0x0031, all -> 0x002e }
            r0.<init>(r1)     // Catch:{ IOException -> 0x0034, ClassNotFoundException -> 0x0031, all -> 0x002e }
            if (r3 != 0) goto L_0x001d
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x0019, ClassNotFoundException -> 0x0015, all -> 0x0013 }
            r1.<init>(r0)     // Catch:{ IOException -> 0x0019, ClassNotFoundException -> 0x0015, all -> 0x0013 }
        L_0x0011:
            r2 = r1
            goto L_0x0023
        L_0x0013:
            r1 = move-exception
            goto L_0x003a
        L_0x0015:
            r1 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x0033
        L_0x0019:
            r1 = move-exception
            r3 = r2
            r2 = r0
            goto L_0x0036
        L_0x001d:
            com.alibaba.analytics.utils.Base64_2$1 r1 = new com.alibaba.analytics.utils.Base64_2$1     // Catch:{ IOException -> 0x0019, ClassNotFoundException -> 0x0015, all -> 0x0013 }
            r1.<init>(r0, r3)     // Catch:{ IOException -> 0x0019, ClassNotFoundException -> 0x0015, all -> 0x0013 }
            goto L_0x0011
        L_0x0023:
            java.lang.Object r1 = r2.readObject()     // Catch:{ IOException -> 0x0019, ClassNotFoundException -> 0x0015, all -> 0x0013 }
            r0.close()     // Catch:{ Exception -> 0x002a }
        L_0x002a:
            r2.close()     // Catch:{ Exception -> 0x002d }
        L_0x002d:
            return r1
        L_0x002e:
            r1 = move-exception
            r0 = r2
            goto L_0x003a
        L_0x0031:
            r1 = move-exception
            r3 = r2
        L_0x0033:
            throw r1     // Catch:{ all -> 0x0037 }
        L_0x0034:
            r1 = move-exception
            r3 = r2
        L_0x0036:
            throw r1     // Catch:{ all -> 0x0037 }
        L_0x0037:
            r1 = move-exception
            r0 = r2
            r2 = r3
        L_0x003a:
            r0.close()     // Catch:{ Exception -> 0x003d }
        L_0x003d:
            r2.close()     // Catch:{ Exception -> 0x0040 }
        L_0x0040:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.Base64_2.decodeToObject(java.lang.String, int, java.lang.ClassLoader):java.lang.Object");
    }

    public static void encodeToFile(byte[] bArr, String str) throws IOException {
        if (bArr == null) {
            throw new NullPointerException("Data to encode was null.");
        }
        OutputStream outputStream = null;
        try {
            OutputStream outputStream2 = new OutputStream(new FileOutputStream(str), 1);
            try {
                outputStream2.write(bArr);
                try {
                    outputStream2.close();
                } catch (Exception unused) {
                }
            } catch (IOException e) {
                e = e;
                outputStream = outputStream2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                outputStream = outputStream2;
                try {
                    outputStream.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            throw e;
        }
    }

    public static void decodeToFile(String str, String str2) throws IOException {
        OutputStream outputStream = null;
        try {
            OutputStream outputStream2 = new OutputStream(new FileOutputStream(str2), 0);
            try {
                outputStream2.write(str.getBytes("US-ASCII"));
                try {
                    outputStream2.close();
                } catch (Exception unused) {
                }
            } catch (IOException e) {
                e = e;
                outputStream = outputStream2;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                outputStream = outputStream2;
                try {
                    outputStream.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            throw e;
        }
    }

    public static byte[] decodeFromFile(String str) throws IOException {
        InputStream inputStream = null;
        try {
            File file = new File(str);
            if (file.length() > 2147483647L) {
                StringBuilder sb = new StringBuilder("File is too big for this convenience method (");
                sb.append(file.length());
                sb.append(" bytes).");
                throw new IOException(sb.toString());
            }
            byte[] bArr = new byte[((int) file.length())];
            InputStream inputStream2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
            int i = 0;
            while (true) {
                try {
                    int read = inputStream2.read(bArr, i, 4096);
                    if (read < 0) {
                        break;
                    }
                    i += read;
                } catch (IOException e) {
                    e = e;
                    inputStream = inputStream2;
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStream2;
                    try {
                        inputStream.close();
                    } catch (Exception unused) {
                    }
                    throw th;
                }
            }
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            try {
                inputStream2.close();
            } catch (Exception unused2) {
            }
            return bArr2;
        } catch (IOException e2) {
            e = e2;
            throw e;
        }
    }

    public static String encodeFromFile(String str) throws IOException {
        InputStream inputStream = null;
        try {
            File file = new File(str);
            byte[] bArr = new byte[Math.max((int) ((((double) file.length()) * 1.4d) + 1.0d), 40)];
            InputStream inputStream2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
            int i = 0;
            while (true) {
                try {
                    int read = inputStream2.read(bArr, i, 4096);
                    if (read < 0) {
                        break;
                    }
                    i += read;
                } catch (IOException e) {
                    e = e;
                    inputStream = inputStream2;
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStream2;
                    try {
                        inputStream.close();
                    } catch (Exception unused) {
                    }
                    throw th;
                }
            }
            String str2 = new String(bArr, 0, i, "US-ASCII");
            try {
                inputStream2.close();
            } catch (Exception unused2) {
            }
            return str2;
        } catch (IOException e2) {
            e = e2;
            throw e;
        }
    }

    public static void encodeFileToFile(String str, String str2) throws IOException {
        String encodeFromFile = encodeFromFile(str);
        java.io.OutputStream outputStream = null;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
            try {
                bufferedOutputStream.write(encodeFromFile.getBytes("US-ASCII"));
                try {
                    bufferedOutputStream.close();
                } catch (Exception unused) {
                }
            } catch (IOException e) {
                e = e;
                outputStream = bufferedOutputStream;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                outputStream = bufferedOutputStream;
                try {
                    outputStream.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            throw e;
        }
    }

    public static void decodeFileToFile(String str, String str2) throws IOException {
        byte[] decodeFromFile = decodeFromFile(str);
        java.io.OutputStream outputStream = null;
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
            try {
                bufferedOutputStream.write(decodeFromFile);
                try {
                    bufferedOutputStream.close();
                } catch (Exception unused) {
                }
            } catch (IOException e) {
                e = e;
                outputStream = bufferedOutputStream;
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                outputStream = bufferedOutputStream;
                try {
                    outputStream.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            throw e;
        }
    }
}
