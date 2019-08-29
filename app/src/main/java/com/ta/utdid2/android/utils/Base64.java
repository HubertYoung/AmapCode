package com.ta.utdid2.android.utils;

import android.annotation.SuppressLint;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.UnsupportedEncodingException;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    static abstract class Coder {
        public int op;
        public byte[] output;

        public abstract int maxOutputSize(int i);

        public abstract boolean process(byte[] bArr, int i, int i2, boolean z);

        Coder() {
        }
    }

    static class Decoder extends Coder {
        private static final int[] DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int i, byte[] bArr) {
            this.output = bArr;
            this.alphabet = (i & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        public int maxOutputSize(int i) {
            return ((i * 3) / 4) + 10;
        }

        /* JADX WARNING: Removed duplicated region for block: B:49:0x00e5  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x00ec  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean process(byte[] r12, int r13, int r14, boolean r15) {
            /*
                r11 = this;
                int r0 = r11.state
                r1 = 0
                r2 = 6
                if (r0 != r2) goto L_0x0007
                return r1
            L_0x0007:
                int r14 = r14 + r13
                int r0 = r11.state
                int r3 = r11.value
                byte[] r4 = r11.output
                int[] r5 = r11.alphabet
                r6 = 4
                r7 = 0
            L_0x0012:
                if (r13 >= r14) goto L_0x00e2
                if (r0 != 0) goto L_0x0059
            L_0x0016:
                int r8 = r13 + 4
                if (r8 > r14) goto L_0x0057
                byte r3 = r12[r13]
                r3 = r3 & 255(0xff, float:3.57E-43)
                r3 = r5[r3]
                int r3 = r3 << 18
                int r9 = r13 + 1
                byte r9 = r12[r9]
                r9 = r9 & 255(0xff, float:3.57E-43)
                r9 = r5[r9]
                int r9 = r9 << 12
                r3 = r3 | r9
                int r9 = r13 + 2
                byte r9 = r12[r9]
                r9 = r9 & 255(0xff, float:3.57E-43)
                r9 = r5[r9]
                int r9 = r9 << r2
                r3 = r3 | r9
                int r9 = r13 + 3
                byte r9 = r12[r9]
                r9 = r9 & 255(0xff, float:3.57E-43)
                r9 = r5[r9]
                r3 = r3 | r9
                if (r3 < 0) goto L_0x0057
                int r13 = r7 + 2
                byte r9 = (byte) r3
                r4[r13] = r9
                int r13 = r7 + 1
                int r9 = r3 >> 8
                byte r9 = (byte) r9
                r4[r13] = r9
                int r13 = r3 >> 16
                byte r13 = (byte) r13
                r4[r7] = r13
                int r7 = r7 + 3
                r13 = r8
                goto L_0x0016
            L_0x0057:
                if (r13 >= r14) goto L_0x00e2
            L_0x0059:
                int r8 = r13 + 1
                byte r13 = r12[r13]
                r13 = r13 & 255(0xff, float:3.57E-43)
                r13 = r5[r13]
                r9 = -2
                r10 = -1
                switch(r0) {
                    case 0: goto L_0x00d4;
                    case 1: goto L_0x00c7;
                    case 2: goto L_0x00ac;
                    case 3: goto L_0x0078;
                    case 4: goto L_0x006d;
                    case 5: goto L_0x0068;
                    default: goto L_0x0066;
                }
            L_0x0066:
                goto L_0x00df
            L_0x0068:
                if (r13 == r10) goto L_0x00df
                r11.state = r2
                return r1
            L_0x006d:
                if (r13 != r9) goto L_0x0073
                int r0 = r0 + 1
                goto L_0x00df
            L_0x0073:
                if (r13 == r10) goto L_0x00df
                r11.state = r2
                return r1
            L_0x0078:
                if (r13 < 0) goto L_0x0095
                int r0 = r3 << 6
                r3 = r0 | r13
                int r13 = r7 + 2
                byte r0 = (byte) r3
                r4[r13] = r0
                int r13 = r7 + 1
                int r0 = r3 >> 8
                byte r0 = (byte) r0
                r4[r13] = r0
                int r13 = r3 >> 16
                byte r13 = (byte) r13
                r4[r7] = r13
                int r7 = r7 + 3
                r13 = r8
                r0 = 0
                goto L_0x0012
            L_0x0095:
                if (r13 != r9) goto L_0x00a7
                int r13 = r7 + 1
                int r0 = r3 >> 2
                byte r0 = (byte) r0
                r4[r13] = r0
                int r13 = r3 >> 10
                byte r13 = (byte) r13
                r4[r7] = r13
                int r7 = r7 + 2
                r0 = 5
                goto L_0x00df
            L_0x00a7:
                if (r13 == r10) goto L_0x00df
                r11.state = r2
                return r1
            L_0x00ac:
                if (r13 < 0) goto L_0x00b4
                int r3 = r3 << 6
                r3 = r3 | r13
                int r0 = r0 + 1
                goto L_0x00df
            L_0x00b4:
                if (r13 != r9) goto L_0x00c2
                int r13 = r7 + 1
                int r0 = r3 >> 4
                byte r0 = (byte) r0
                r4[r7] = r0
                r7 = r13
                r13 = r8
                r0 = 4
                goto L_0x0012
            L_0x00c2:
                if (r13 == r10) goto L_0x00df
                r11.state = r2
                return r1
            L_0x00c7:
                if (r13 < 0) goto L_0x00cf
                int r3 = r3 << 6
                r3 = r3 | r13
                int r0 = r0 + 1
                goto L_0x00df
            L_0x00cf:
                if (r13 == r10) goto L_0x00df
                r11.state = r2
                return r1
            L_0x00d4:
                if (r13 < 0) goto L_0x00da
                int r0 = r0 + 1
                r3 = r13
                goto L_0x00df
            L_0x00da:
                if (r13 == r10) goto L_0x00df
                r11.state = r2
                return r1
            L_0x00df:
                r13 = r8
                goto L_0x0012
            L_0x00e2:
                r12 = 1
                if (r15 != 0) goto L_0x00ec
                r11.state = r0
                r11.value = r3
                r11.op = r7
                return r12
            L_0x00ec:
                switch(r0) {
                    case 0: goto L_0x010e;
                    case 1: goto L_0x010b;
                    case 2: goto L_0x0102;
                    case 3: goto L_0x00f3;
                    case 4: goto L_0x00f0;
                    default: goto L_0x00ef;
                }
            L_0x00ef:
                goto L_0x010e
            L_0x00f0:
                r11.state = r2
                return r1
            L_0x00f3:
                int r13 = r7 + 1
                int r14 = r3 >> 10
                byte r14 = (byte) r14
                r4[r7] = r14
                int r7 = r13 + 1
                int r14 = r3 >> 2
                byte r14 = (byte) r14
                r4[r13] = r14
                goto L_0x010e
            L_0x0102:
                int r13 = r7 + 1
                int r14 = r3 >> 4
                byte r14 = (byte) r14
                r4[r7] = r14
                r7 = r13
                goto L_0x010e
            L_0x010b:
                r11.state = r2
                return r1
            L_0x010e:
                r11.state = r0
                r11.op = r7
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.android.utils.Base64.Decoder.process(byte[], int, int, boolean):boolean");
        }
    }

    static class Encoder extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte[] ENCODE = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        static {
            Class<Base64> cls = Base64.class;
        }

        public Encoder(int i, byte[] bArr) {
            this.output = bArr;
            boolean z = true;
            this.do_padding = (i & 1) == 0;
            this.do_newline = (i & 2) == 0;
            this.do_cr = (i & 4) == 0 ? false : z;
            this.alphabet = (i & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        public int maxOutputSize(int i) {
            return ((i * 8) / 5) + 10;
        }

        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean process(byte[] r19, int r20, int r21, boolean r22) {
            /*
                r18 = this;
                r0 = r18
                byte[] r3 = r0.alphabet
                byte[] r4 = r0.output
                int r5 = r0.count
                int r6 = r21 + r20
                int r7 = r0.tailLen
                r8 = -1
                r9 = 0
                r10 = 1
                switch(r7) {
                    case 0: goto L_0x004f;
                    case 1: goto L_0x0030;
                    case 2: goto L_0x0013;
                    default: goto L_0x0012;
                }
            L_0x0012:
                goto L_0x004f
            L_0x0013:
                int r7 = r20 + 1
                if (r7 > r6) goto L_0x004f
                byte[] r11 = r0.tail
                byte r11 = r11[r9]
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 16
                byte[] r12 = r0.tail
                byte r12 = r12[r10]
                r12 = r12 & 255(0xff, float:3.57E-43)
                int r12 = r12 << 8
                r11 = r11 | r12
                byte r2 = r19[r20]
                r2 = r2 & 255(0xff, float:3.57E-43)
                r2 = r2 | r11
                r0.tailLen = r9
                goto L_0x0052
            L_0x0030:
                int r7 = r20 + 2
                if (r7 > r6) goto L_0x004f
                byte[] r7 = r0.tail
                byte r7 = r7[r9]
                r7 = r7 & 255(0xff, float:3.57E-43)
                int r7 = r7 << 16
                int r11 = r20 + 1
                byte r2 = r19[r20]
                r2 = r2 & 255(0xff, float:3.57E-43)
                int r2 = r2 << 8
                r2 = r2 | r7
                int r7 = r11 + 1
                byte r11 = r19[r11]
                r11 = r11 & 255(0xff, float:3.57E-43)
                r2 = r2 | r11
                r0.tailLen = r9
                goto L_0x0052
            L_0x004f:
                r7 = r20
                r2 = -1
            L_0x0052:
                r12 = 4
                r13 = 13
                r14 = 10
                r15 = 2
                if (r2 == r8) goto L_0x0090
                int r8 = r2 >> 18
                r8 = r8 & 63
                byte r8 = r3[r8]
                r4[r9] = r8
                int r8 = r2 >> 12
                r8 = r8 & 63
                byte r8 = r3[r8]
                r4[r10] = r8
                int r8 = r2 >> 6
                r8 = r8 & 63
                byte r8 = r3[r8]
                r4[r15] = r8
                r2 = r2 & 63
                byte r2 = r3[r2]
                r8 = 3
                r4[r8] = r2
                int r5 = r5 + -1
                if (r5 != 0) goto L_0x008d
                boolean r2 = r0.do_cr
                if (r2 == 0) goto L_0x0085
                r2 = 5
                r4[r12] = r13
                goto L_0x0086
            L_0x0085:
                r2 = 4
            L_0x0086:
                int r5 = r2 + 1
                r4[r2] = r14
                r2 = 19
                goto L_0x0092
            L_0x008d:
                r2 = r5
                r5 = 4
                goto L_0x0092
            L_0x0090:
                r2 = r5
                r5 = 0
            L_0x0092:
                int r8 = r7 + 3
                if (r8 > r6) goto L_0x00eb
                byte r11 = r19[r7]
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 16
                int r16 = r7 + 1
                byte r10 = r19[r16]
                r10 = r10 & 255(0xff, float:3.57E-43)
                int r10 = r10 << 8
                r10 = r10 | r11
                int r7 = r7 + 2
                byte r7 = r19[r7]
                r7 = r7 & 255(0xff, float:3.57E-43)
                r7 = r7 | r10
                int r10 = r7 >> 18
                r10 = r10 & 63
                byte r10 = r3[r10]
                r4[r5] = r10
                int r10 = r5 + 1
                int r11 = r7 >> 12
                r11 = r11 & 63
                byte r11 = r3[r11]
                r4[r10] = r11
                int r10 = r5 + 2
                int r11 = r7 >> 6
                r11 = r11 & 63
                byte r11 = r3[r11]
                r4[r10] = r11
                int r10 = r5 + 3
                r7 = r7 & 63
                byte r7 = r3[r7]
                r4[r10] = r7
                int r5 = r5 + 4
                int r2 = r2 + -1
                if (r2 != 0) goto L_0x00e8
                boolean r2 = r0.do_cr
                if (r2 == 0) goto L_0x00df
                int r2 = r5 + 1
                r4[r5] = r13
                goto L_0x00e0
            L_0x00df:
                r2 = r5
            L_0x00e0:
                int r5 = r2 + 1
                r4[r2] = r14
                r7 = r8
                r2 = 19
                goto L_0x00e9
            L_0x00e8:
                r7 = r8
            L_0x00e9:
                r10 = 1
                goto L_0x0092
            L_0x00eb:
                if (r22 == 0) goto L_0x01c9
                int r8 = r0.tailLen
                int r8 = r7 - r8
                int r10 = r6 + -1
                if (r8 != r10) goto L_0x013d
                int r6 = r0.tailLen
                if (r6 <= 0) goto L_0x00ff
                byte[] r1 = r0.tail
                byte r1 = r1[r9]
                r9 = 1
                goto L_0x0101
            L_0x00ff:
                byte r1 = r19[r7]
            L_0x0101:
                r1 = r1 & 255(0xff, float:3.57E-43)
                int r1 = r1 << r12
                int r6 = r0.tailLen
                int r6 = r6 - r9
                r0.tailLen = r6
                int r6 = r5 + 1
                int r7 = r1 >> 6
                r7 = r7 & 63
                byte r7 = r3[r7]
                r4[r5] = r7
                int r5 = r6 + 1
                r1 = r1 & 63
                byte r1 = r3[r1]
                r4[r6] = r1
                boolean r1 = r0.do_padding
                if (r1 == 0) goto L_0x0129
                int r1 = r5 + 1
                r3 = 61
                r4[r5] = r3
                int r5 = r1 + 1
                r4[r1] = r3
            L_0x0129:
                boolean r1 = r0.do_newline
                if (r1 == 0) goto L_0x01c7
                boolean r1 = r0.do_cr
                if (r1 == 0) goto L_0x0136
                int r1 = r5 + 1
                r4[r5] = r13
                goto L_0x0137
            L_0x0136:
                r1 = r5
            L_0x0137:
                int r5 = r1 + 1
                r4[r1] = r14
                goto L_0x01c7
            L_0x013d:
                int r8 = r0.tailLen
                int r8 = r7 - r8
                int r6 = r6 - r15
                if (r8 != r6) goto L_0x01ae
                int r6 = r0.tailLen
                r8 = 1
                if (r6 <= r8) goto L_0x014f
                byte[] r6 = r0.tail
                byte r6 = r6[r9]
                r9 = 1
                goto L_0x0158
            L_0x014f:
                int r6 = r7 + 1
                byte r7 = r19[r7]
                r17 = r7
                r7 = r6
                r6 = r17
            L_0x0158:
                r6 = r6 & 255(0xff, float:3.57E-43)
                int r6 = r6 << r14
                int r8 = r0.tailLen
                if (r8 <= 0) goto L_0x0167
                byte[] r1 = r0.tail
                int r7 = r9 + 1
                byte r1 = r1[r9]
                r9 = r7
                goto L_0x0169
            L_0x0167:
                byte r1 = r19[r7]
            L_0x0169:
                r1 = r1 & 255(0xff, float:3.57E-43)
                int r1 = r1 << r15
                r1 = r1 | r6
                int r6 = r0.tailLen
                int r6 = r6 - r9
                r0.tailLen = r6
                int r6 = r5 + 1
                int r7 = r1 >> 12
                r7 = r7 & 63
                byte r7 = r3[r7]
                r4[r5] = r7
                int r5 = r6 + 1
                int r7 = r1 >> 6
                r7 = r7 & 63
                byte r7 = r3[r7]
                r4[r6] = r7
                int r6 = r5 + 1
                r1 = r1 & 63
                byte r1 = r3[r1]
                r4[r5] = r1
                boolean r1 = r0.do_padding
                if (r1 == 0) goto L_0x0199
                int r1 = r6 + 1
                r3 = 61
                r4[r6] = r3
                goto L_0x019a
            L_0x0199:
                r1 = r6
            L_0x019a:
                boolean r3 = r0.do_newline
                if (r3 == 0) goto L_0x01ac
                boolean r3 = r0.do_cr
                if (r3 == 0) goto L_0x01a7
                int r3 = r1 + 1
                r4[r1] = r13
                r1 = r3
            L_0x01a7:
                int r3 = r1 + 1
                r4[r1] = r14
                r1 = r3
            L_0x01ac:
                r5 = r1
                goto L_0x01c7
            L_0x01ae:
                boolean r1 = r0.do_newline
                if (r1 == 0) goto L_0x01c7
                if (r5 <= 0) goto L_0x01c7
                r1 = 19
                if (r2 == r1) goto L_0x01c7
                boolean r1 = r0.do_cr
                if (r1 == 0) goto L_0x01c1
                int r1 = r5 + 1
                r4[r5] = r13
                goto L_0x01c2
            L_0x01c1:
                r1 = r5
            L_0x01c2:
                int r3 = r1 + 1
                r4[r1] = r14
                r5 = r3
            L_0x01c7:
                r6 = 1
                goto L_0x01f7
            L_0x01c9:
                int r3 = r6 + -1
                if (r7 != r3) goto L_0x01da
                byte[] r3 = r0.tail
                int r4 = r0.tailLen
                int r6 = r4 + 1
                r0.tailLen = r6
                byte r1 = r19[r7]
                r3[r4] = r1
                goto L_0x01c7
            L_0x01da:
                int r6 = r6 - r15
                if (r7 != r6) goto L_0x01c7
                byte[] r3 = r0.tail
                int r4 = r0.tailLen
                int r6 = r4 + 1
                r0.tailLen = r6
                byte r6 = r19[r7]
                r3[r4] = r6
                byte[] r3 = r0.tail
                int r4 = r0.tailLen
                int r6 = r4 + 1
                r0.tailLen = r6
                r6 = 1
                int r7 = r7 + r6
                byte r1 = r19[r7]
                r3[r4] = r1
            L_0x01f7:
                r0.op = r5
                r0.count = r2
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.android.utils.Base64.Encoder.process(byte[], int, int, boolean):boolean");
        }
    }

    public static byte[] decode(String str, int i) {
        return decode(str.getBytes(), i);
    }

    public static byte[] decode(byte[] bArr, int i) {
        return decode(bArr, 0, bArr.length, i);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) {
        Decoder decoder = new Decoder(i3, new byte[((i2 * 3) / 4)]);
        if (!decoder.process(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (decoder.op == decoder.output.length) {
            return decoder.output;
        } else {
            byte[] bArr2 = new byte[decoder.op];
            System.arraycopy(decoder.output, 0, bArr2, 0, decoder.op);
            return bArr2;
        }
    }

    public static String encodeToString(byte[] bArr, int i) {
        try {
            return new String(encode(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static String encodeToString(byte[] bArr, int i, int i2, int i3) {
        try {
            return new String(encode(bArr, i, i2, i3), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, bArr.length, i);
    }

    @SuppressLint({"Assert"})
    public static byte[] encode(byte[] bArr, int i, int i2, int i3) {
        Encoder encoder = new Encoder(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!encoder.do_padding) {
            switch (i2 % 3) {
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (encoder.do_newline && i2 > 0) {
            i4 += (((i2 - 1) / 57) + 1) * (encoder.do_cr ? 2 : 1);
        }
        encoder.output = new byte[i4];
        encoder.process(bArr, i, i2, true);
        return encoder.output;
    }

    private Base64() {
    }
}
