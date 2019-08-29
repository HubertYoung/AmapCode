package android.util;

import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.UnsupportedEncodingException;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = (!Base64.class.desiredAssertionStatus());
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    abstract class Coder {
        public int op;
        public byte[] output;

        public abstract int maxOutputSize(int i);

        public abstract boolean process(byte[] bArr, int i, int i2, boolean z);

        Coder() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    class Decoder extends Coder {
        private static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private int c;
        private int d;
        private final int[] e;

        public Decoder(int flags, byte[] output) {
            this.output = output;
            this.e = (flags & 8) == 0 ? a : b;
            this.c = 0;
            this.d = 0;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public int maxOutputSize(int len) {
            return ((len * 3) / 4) + 10;
        }

        /* JADX WARNING: Removed duplicated region for block: B:52:0x0106  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x010f  */
        /* JADX WARNING: Removed duplicated region for block: B:64:0x0103 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean process(byte[] r12, int r13, int r14, boolean r15) {
            /*
                r11 = this;
                int r9 = r11.c
                r10 = 6
                if (r9 != r10) goto L_0x0007
                r9 = 0
            L_0x0006:
                return r9
            L_0x0007:
                r5 = r13
                int r14 = r14 + r13
                int r7 = r11.c
                int r8 = r11.d
                r2 = 0
                byte[] r4 = r11.output
                int[] r0 = r11.e
            L_0x0012:
                if (r5 >= r14) goto L_0x0103
                if (r7 != 0) goto L_0x005c
            L_0x0016:
                int r9 = r5 + 4
                if (r9 > r14) goto L_0x005a
                byte r9 = r12[r5]
                r9 = r9 & 255(0xff, float:3.57E-43)
                r9 = r0[r9]
                int r9 = r9 << 18
                int r10 = r5 + 1
                byte r10 = r12[r10]
                r10 = r10 & 255(0xff, float:3.57E-43)
                r10 = r0[r10]
                int r10 = r10 << 12
                r9 = r9 | r10
                int r10 = r5 + 2
                byte r10 = r12[r10]
                r10 = r10 & 255(0xff, float:3.57E-43)
                r10 = r0[r10]
                int r10 = r10 << 6
                r9 = r9 | r10
                int r10 = r5 + 3
                byte r10 = r12[r10]
                r10 = r10 & 255(0xff, float:3.57E-43)
                r10 = r0[r10]
                r8 = r9 | r10
                if (r8 < 0) goto L_0x005a
                int r9 = r2 + 2
                byte r10 = (byte) r8
                r4[r9] = r10
                int r9 = r2 + 1
                int r10 = r8 >> 8
                byte r10 = (byte) r10
                r4[r9] = r10
                int r9 = r8 >> 16
                byte r9 = (byte) r9
                r4[r2] = r9
                int r2 = r2 + 3
                int r5 = r5 + 4
                goto L_0x0016
            L_0x005a:
                if (r5 >= r14) goto L_0x0103
            L_0x005c:
                int r6 = r5 + 1
                byte r9 = r12[r5]
                r9 = r9 & 255(0xff, float:3.57E-43)
                r1 = r0[r9]
                switch(r7) {
                    case 0: goto L_0x0069;
                    case 1: goto L_0x0078;
                    case 2: goto L_0x008b;
                    case 3: goto L_0x00ae;
                    case 4: goto L_0x00e9;
                    case 5: goto L_0x00fa;
                    default: goto L_0x0067;
                }
            L_0x0067:
                r5 = r6
                goto L_0x0012
            L_0x0069:
                if (r1 < 0) goto L_0x0070
                r8 = r1
                int r7 = r7 + 1
                r5 = r6
                goto L_0x0012
            L_0x0070:
                r9 = -1
                if (r1 == r9) goto L_0x0067
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            L_0x0078:
                if (r1 < 0) goto L_0x0082
                int r9 = r8 << 6
                r8 = r9 | r1
                int r7 = r7 + 1
                r5 = r6
                goto L_0x0012
            L_0x0082:
                r9 = -1
                if (r1 == r9) goto L_0x0067
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            L_0x008b:
                if (r1 < 0) goto L_0x0096
                int r9 = r8 << 6
                r8 = r9 | r1
                int r7 = r7 + 1
                r5 = r6
                goto L_0x0012
            L_0x0096:
                r9 = -2
                if (r1 != r9) goto L_0x00a5
                int r3 = r2 + 1
                int r9 = r8 >> 4
                byte r9 = (byte) r9
                r4[r2] = r9
                r7 = 4
                r2 = r3
                r5 = r6
                goto L_0x0012
            L_0x00a5:
                r9 = -1
                if (r1 == r9) goto L_0x0067
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            L_0x00ae:
                if (r1 < 0) goto L_0x00cb
                int r9 = r8 << 6
                r8 = r9 | r1
                int r9 = r2 + 2
                byte r10 = (byte) r8
                r4[r9] = r10
                int r9 = r2 + 1
                int r10 = r8 >> 8
                byte r10 = (byte) r10
                r4[r9] = r10
                int r9 = r8 >> 16
                byte r9 = (byte) r9
                r4[r2] = r9
                int r2 = r2 + 3
                r7 = 0
                r5 = r6
                goto L_0x0012
            L_0x00cb:
                r9 = -2
                if (r1 != r9) goto L_0x00e0
                int r9 = r2 + 1
                int r10 = r8 >> 2
                byte r10 = (byte) r10
                r4[r9] = r10
                int r9 = r8 >> 10
                byte r9 = (byte) r9
                r4[r2] = r9
                int r2 = r2 + 2
                r7 = 5
                r5 = r6
                goto L_0x0012
            L_0x00e0:
                r9 = -1
                if (r1 == r9) goto L_0x0067
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            L_0x00e9:
                r9 = -2
                if (r1 != r9) goto L_0x00f1
                int r7 = r7 + 1
                r5 = r6
                goto L_0x0012
            L_0x00f1:
                r9 = -1
                if (r1 == r9) goto L_0x0067
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            L_0x00fa:
                r9 = -1
                if (r1 == r9) goto L_0x0067
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            L_0x0103:
                r3 = r2
                if (r15 != 0) goto L_0x010f
                r11.c = r7
                r11.d = r8
                r11.op = r3
                r9 = 1
                goto L_0x0006
            L_0x010f:
                switch(r7) {
                    case 0: goto L_0x011a;
                    case 1: goto L_0x011c;
                    case 2: goto L_0x0122;
                    case 3: goto L_0x012a;
                    case 4: goto L_0x013a;
                    default: goto L_0x0112;
                }
            L_0x0112:
                r2 = r3
            L_0x0113:
                r11.c = r7
                r11.op = r2
                r9 = 1
                goto L_0x0006
            L_0x011a:
                r2 = r3
                goto L_0x0113
            L_0x011c:
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            L_0x0122:
                int r2 = r3 + 1
                int r9 = r8 >> 4
                byte r9 = (byte) r9
                r4[r3] = r9
                goto L_0x0113
            L_0x012a:
                int r2 = r3 + 1
                int r9 = r8 >> 10
                byte r9 = (byte) r9
                r4[r3] = r9
                int r3 = r2 + 1
                int r9 = r8 >> 2
                byte r9 = (byte) r9
                r4[r2] = r9
                r2 = r3
                goto L_0x0113
            L_0x013a:
                r9 = 6
                r11.c = r9
                r9 = 0
                goto L_0x0006
            */
            throw new UnsupportedOperationException("Method not decompiled: android.util.Base64.Decoder.process(byte[], int, int, boolean):boolean");
        }
    }

    class Encoder extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled;
        public static final int LINE_GROUPS = 19;
        private static final byte[] a = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] b = {65, 66, 67, 68, 69, 70, ImageFileType.HEAD_GIF_0, ImageFileType.HEAD_HEVC_0, 73, 74, 75, 76, 77, 78, 79, 80, 81, ImageFileType.HEAD_WEBP_0, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        private final byte[] c;
        private int d;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] e;
        int tailLen;

        static {
            boolean z;
            if (!Base64.class.desiredAssertionStatus()) {
                z = true;
            } else {
                z = false;
            }
            $assertionsDisabled = z;
        }

        public Encoder(int flags, byte[] output) {
            boolean z;
            boolean z2 = true;
            this.output = output;
            this.do_padding = (flags & 1) == 0;
            if ((flags & 2) == 0) {
                z = true;
            } else {
                z = false;
            }
            this.do_newline = z;
            this.do_cr = (flags & 4) == 0 ? false : z2;
            this.e = (flags & 8) == 0 ? a : b;
            this.c = new byte[2];
            this.tailLen = 0;
            this.d = this.do_newline ? 19 : -1;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public int maxOutputSize(int len) {
            return ((len * 8) / 5) + 10;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
            	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:30)
            	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
            	at jadx.core.ProcessClass.process(ProcessClass.java:35)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
            */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x005c  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0102  */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x015d  */
        /* JADX WARNING: Removed duplicated region for block: B:82:0x0210  */
        /* JADX WARNING: Removed duplicated region for block: B:93:0x0100 A[SYNTHETIC] */
        public boolean process(byte[] r15, int r16, int r17, boolean r18) {
            /*
                r14 = this;
                byte[] r1 = r14.e
                byte[] r5 = r14.output
                r3 = 0
                int r2 = r14.d
                r6 = r16
                int r17 = r17 + r16
                r10 = -1
                int r11 = r14.tailLen
                switch(r11) {
                    case 0: goto L_0x0011;
                    case 1: goto L_0x00b4;
                    case 2: goto L_0x00da;
                    default: goto L_0x0011;
                }
            L_0x0011:
                r11 = -1
                if (r10 == r11) goto L_0x024b
                r11 = 0
                int r3 = r3 + 1
                int r12 = r10 >> 18
                r12 = r12 & 63
                byte r12 = r1[r12]
                r5[r11] = r12
                r11 = 1
                int r3 = r3 + 1
                int r12 = r10 >> 12
                r12 = r12 & 63
                byte r12 = r1[r12]
                r5[r11] = r12
                r11 = 2
                int r3 = r3 + 1
                int r12 = r10 >> 6
                r12 = r12 & 63
                byte r12 = r1[r12]
                r5[r11] = r12
                r11 = 3
                int r3 = r3 + 1
                r12 = r10 & 63
                byte r12 = r1[r12]
                r5[r11] = r12
                int r2 = r2 + -1
                if (r2 != 0) goto L_0x024b
                boolean r11 = r14.do_cr
                if (r11 == 0) goto L_0x004d
                r11 = 4
                int r3 = r3 + 1
                r12 = 13
                r5[r11] = r12
            L_0x004d:
                int r4 = r3 + 1
                r11 = 10
                r5[r3] = r11
                r2 = 19
                r7 = r6
            L_0x0056:
                int r11 = r7 + 3
                r0 = r17
                if (r11 > r0) goto L_0x0100
                byte r11 = r15[r7]
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 16
                int r12 = r7 + 1
                byte r12 = r15[r12]
                r12 = r12 & 255(0xff, float:3.57E-43)
                int r12 = r12 << 8
                r11 = r11 | r12
                int r12 = r7 + 2
                byte r12 = r15[r12]
                r12 = r12 & 255(0xff, float:3.57E-43)
                r10 = r11 | r12
                int r11 = r10 >> 18
                r11 = r11 & 63
                byte r11 = r1[r11]
                r5[r4] = r11
                int r11 = r4 + 1
                int r12 = r10 >> 12
                r12 = r12 & 63
                byte r12 = r1[r12]
                r5[r11] = r12
                int r11 = r4 + 2
                int r12 = r10 >> 6
                r12 = r12 & 63
                byte r12 = r1[r12]
                r5[r11] = r12
                int r11 = r4 + 3
                r12 = r10 & 63
                byte r12 = r1[r12]
                r5[r11] = r12
                int r6 = r7 + 3
                int r3 = r4 + 4
                int r2 = r2 + -1
                if (r2 != 0) goto L_0x024b
                boolean r11 = r14.do_cr
                if (r11 == 0) goto L_0x00aa
                int r4 = r3 + 1
                r11 = 13
                r5[r3] = r11
                r3 = r4
            L_0x00aa:
                int r4 = r3 + 1
                r11 = 10
                r5[r3] = r11
                r2 = 19
                r7 = r6
                goto L_0x0056
            L_0x00b4:
                int r11 = r16 + 2
                r0 = r17
                if (r11 > r0) goto L_0x0011
                byte[] r11 = r14.c
                r12 = 0
                byte r11 = r11[r12]
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 16
                int r6 = r6 + 1
                byte r12 = r15[r16]
                r12 = r12 & 255(0xff, float:3.57E-43)
                int r12 = r12 << 8
                r11 = r11 | r12
                int r7 = r6 + 1
                byte r12 = r15[r6]
                r12 = r12 & 255(0xff, float:3.57E-43)
                r10 = r11 | r12
                r11 = 0
                r14.tailLen = r11
                r6 = r7
                goto L_0x0011
            L_0x00da:
                int r11 = r16 + 1
                r0 = r17
                if (r11 > r0) goto L_0x0011
                byte[] r11 = r14.c
                r12 = 0
                byte r11 = r11[r12]
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 16
                byte[] r12 = r14.c
                r13 = 1
                byte r12 = r12[r13]
                r12 = r12 & 255(0xff, float:3.57E-43)
                int r12 = r12 << 8
                r11 = r11 | r12
                int r6 = r6 + 1
                byte r12 = r15[r16]
                r12 = r12 & 255(0xff, float:3.57E-43)
                r10 = r11 | r12
                r11 = 0
                r14.tailLen = r11
                goto L_0x0011
            L_0x0100:
                if (r18 == 0) goto L_0x0210
                int r11 = r14.tailLen
                int r11 = r7 - r11
                int r12 = r17 + -1
                if (r11 != r12) goto L_0x016c
                r8 = 0
                int r11 = r14.tailLen
                if (r11 <= 0) goto L_0x0167
                byte[] r11 = r14.c
                r12 = 0
                int r8 = r8 + 1
                byte r11 = r11[r12]
                r6 = r7
            L_0x0117:
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r10 = r11 << 4
                int r11 = r14.tailLen
                int r11 = r11 - r8
                r14.tailLen = r11
                int r3 = r4 + 1
                int r11 = r10 >> 6
                r11 = r11 & 63
                byte r11 = r1[r11]
                r5[r4] = r11
                int r4 = r3 + 1
                r11 = r10 & 63
                byte r11 = r1[r11]
                r5[r3] = r11
                boolean r11 = r14.do_padding
                if (r11 == 0) goto L_0x0142
                int r3 = r4 + 1
                r11 = 61
                r5[r4] = r11
                int r4 = r3 + 1
                r11 = 61
                r5[r3] = r11
            L_0x0142:
                r3 = r4
                boolean r11 = r14.do_newline
                if (r11 == 0) goto L_0x0159
                boolean r11 = r14.do_cr
                if (r11 == 0) goto L_0x0152
                int r4 = r3 + 1
                r11 = 13
                r5[r3] = r11
                r3 = r4
            L_0x0152:
                int r4 = r3 + 1
                r11 = 10
                r5[r3] = r11
            L_0x0158:
                r3 = r4
            L_0x0159:
                boolean r11 = $assertionsDisabled
                if (r11 != 0) goto L_0x0202
                int r11 = r14.tailLen
                if (r11 == 0) goto L_0x0202
                java.lang.AssertionError r11 = new java.lang.AssertionError
                r11.<init>()
                throw r11
            L_0x0167:
                int r6 = r7 + 1
                byte r11 = r15[r7]
                goto L_0x0117
            L_0x016c:
                int r11 = r14.tailLen
                int r11 = r7 - r11
                int r12 = r17 + -2
                if (r11 != r12) goto L_0x01e4
                r8 = 0
                int r11 = r14.tailLen
                r12 = 1
                if (r11 <= r12) goto L_0x01d9
                byte[] r11 = r14.c
                r12 = 0
                int r8 = r8 + 1
                byte r11 = r11[r12]
                r6 = r7
            L_0x0182:
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r12 = r11 << 10
                int r11 = r14.tailLen
                if (r11 <= 0) goto L_0x01de
                byte[] r11 = r14.c
                int r9 = r8 + 1
                byte r11 = r11[r8]
                r8 = r9
            L_0x0191:
                r11 = r11 & 255(0xff, float:3.57E-43)
                int r11 = r11 << 2
                r10 = r12 | r11
                int r11 = r14.tailLen
                int r11 = r11 - r8
                r14.tailLen = r11
                int r3 = r4 + 1
                int r11 = r10 >> 12
                r11 = r11 & 63
                byte r11 = r1[r11]
                r5[r4] = r11
                int r4 = r3 + 1
                int r11 = r10 >> 6
                r11 = r11 & 63
                byte r11 = r1[r11]
                r5[r3] = r11
                int r3 = r4 + 1
                r11 = r10 & 63
                byte r11 = r1[r11]
                r5[r4] = r11
                boolean r11 = r14.do_padding
                if (r11 == 0) goto L_0x01c3
                int r4 = r3 + 1
                r11 = 61
                r5[r3] = r11
                r3 = r4
            L_0x01c3:
                boolean r11 = r14.do_newline
                if (r11 == 0) goto L_0x0159
                boolean r11 = r14.do_cr
                if (r11 == 0) goto L_0x01d2
                int r4 = r3 + 1
                r11 = 13
                r5[r3] = r11
                r3 = r4
            L_0x01d2:
                int r4 = r3 + 1
                r11 = 10
                r5[r3] = r11
                goto L_0x0158
            L_0x01d9:
                int r6 = r7 + 1
                byte r11 = r15[r7]
                goto L_0x0182
            L_0x01de:
                int r7 = r6 + 1
                byte r11 = r15[r6]
                r6 = r7
                goto L_0x0191
            L_0x01e4:
                boolean r11 = r14.do_newline
                if (r11 == 0) goto L_0x01fe
                if (r4 <= 0) goto L_0x01fe
                r11 = 19
                if (r2 == r11) goto L_0x01fe
                boolean r11 = r14.do_cr
                if (r11 == 0) goto L_0x0249
                int r3 = r4 + 1
                r11 = 13
                r5[r4] = r11
            L_0x01f8:
                int r4 = r3 + 1
                r11 = 10
                r5[r3] = r11
            L_0x01fe:
                r6 = r7
                r3 = r4
                goto L_0x0159
            L_0x0202:
                boolean r11 = $assertionsDisabled
                if (r11 != 0) goto L_0x0222
                r0 = r17
                if (r6 == r0) goto L_0x0222
                java.lang.AssertionError r11 = new java.lang.AssertionError
                r11.<init>()
                throw r11
            L_0x0210:
                int r11 = r17 + -1
                if (r7 != r11) goto L_0x0228
                byte[] r11 = r14.c
                int r12 = r14.tailLen
                int r13 = r12 + 1
                r14.tailLen = r13
                byte r13 = r15[r7]
                r11[r12] = r13
                r6 = r7
                r3 = r4
            L_0x0222:
                r14.op = r3
                r14.d = r2
                r11 = 1
                return r11
            L_0x0228:
                int r11 = r17 + -2
                if (r7 != r11) goto L_0x0246
                byte[] r11 = r14.c
                int r12 = r14.tailLen
                int r13 = r12 + 1
                r14.tailLen = r13
                byte r13 = r15[r7]
                r11[r12] = r13
                byte[] r11 = r14.c
                int r12 = r14.tailLen
                int r13 = r12 + 1
                r14.tailLen = r13
                int r13 = r7 + 1
                byte r13 = r15[r13]
                r11[r12] = r13
            L_0x0246:
                r6 = r7
                r3 = r4
                goto L_0x0222
            L_0x0249:
                r3 = r4
                goto L_0x01f8
            L_0x024b:
                r7 = r6
                r4 = r3
                goto L_0x0056
            */
            throw new UnsupportedOperationException("Method not decompiled: android.util.Base64.Encoder.process(byte[], int, int, boolean):boolean");
        }
    }

    public static byte[] decode(String str, int flags) {
        return decode(str.getBytes(), flags);
    }

    public static byte[] decode(byte[] input, int flags) {
        return decode(input, 0, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        Decoder decoder = new Decoder(flags, new byte[((len * 3) / 4)]);
        if (!decoder.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (decoder.op == decoder.output.length) {
            return decoder.output;
        } else {
            byte[] temp = new byte[decoder.op];
            System.arraycopy(decoder.output, 0, temp, 0, decoder.op);
            return temp;
        }
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(encode(input, flags), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static String encodeToString(byte[] input, int offset, int len, int flags) {
        try {
            return new String(encode(input, offset, len, flags), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return encode(input, 0, input.length, flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        int i;
        Encoder encoder = new Encoder(flags, null);
        int output_len = (len / 3) * 4;
        if (!encoder.do_padding) {
            switch (len % 3) {
                case 1:
                    output_len += 2;
                    break;
                case 2:
                    output_len += 3;
                    break;
            }
        } else if (len % 3 > 0) {
            output_len += 4;
        }
        if (encoder.do_newline && len > 0) {
            int i2 = ((len - 1) / 57) + 1;
            if (encoder.do_cr) {
                i = 2;
            } else {
                i = 1;
            }
            output_len += i * i2;
        }
        encoder.output = new byte[output_len];
        encoder.process(input, offset, len, true);
        if ($assertionsDisabled || encoder.op == output_len) {
            return encoder.output;
        }
        throw new AssertionError();
    }

    private Base64() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
