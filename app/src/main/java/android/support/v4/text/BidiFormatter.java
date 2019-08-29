package android.support.v4.text;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.util.Locale;

public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    /* access modifiers changed from: private */
    public static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    /* access modifiers changed from: private */
    public static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    /* access modifiers changed from: private */
    public static TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = '‪';
    private static final char LRM = '‎';
    private static final String LRM_STRING = Character.toString(LRM);
    private static final char PDF = '‬';
    private static final char RLE = '‫';
    private static final char RLM = '‏';
    private static final String RLM_STRING = Character.toString(RLM);
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    private final int mFlags;
    private final boolean mIsRtlContext;

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

        public Builder() {
            initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
        }

        public Builder(boolean z) {
            initialize(z);
        }

        public Builder(Locale locale) {
            initialize(BidiFormatter.isRtlLocale(locale));
        }

        private void initialize(boolean z) {
            this.mIsRtlContext = z;
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public final Builder stereoReset(boolean z) {
            if (z) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
            return this;
        }

        public final Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.mTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
            return this;
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean z) {
            return z ? BidiFormatter.DEFAULT_RTL_INSTANCE : BidiFormatter.DEFAULT_LTR_INSTANCE;
        }

        public final BidiFormatter build() {
            if (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
        }
    }

    static class DirectionalityEstimator {
        private static final byte[] f = new byte[1792];
        final String a;
        final boolean b = false;
        final int c;
        int d;
        char e;

        static {
            for (int i = 0; i < 1792; i++) {
                f[i] = Character.getDirectionality(i);
            }
        }

        DirectionalityEstimator(String str) {
            this.a = str;
            this.c = str.length();
        }

        static byte a(char c2) {
            return c2 < 1792 ? f[c2] : Character.getDirectionality(c2);
        }

        /* access modifiers changed from: 0000 */
        public final byte a() {
            this.e = this.a.charAt(this.d - 1);
            if (Character.isLowSurrogate(this.e)) {
                int codePointBefore = Character.codePointBefore(this.a, this.d);
                this.d -= Character.charCount(codePointBefore);
                return Character.getDirectionality(codePointBefore);
            }
            this.d--;
            byte a2 = a(this.e);
            if (this.b) {
                if (this.e == '>') {
                    a2 = b();
                } else if (this.e == ';') {
                    a2 = c();
                }
            }
            return a2;
        }

        private byte b() {
            int i = this.d;
            while (this.d > 0) {
                String str = this.a;
                int i2 = this.d - 1;
                this.d = i2;
                this.e = str.charAt(i2);
                if (this.e != '<') {
                    if (this.e == '>') {
                        break;
                    } else if (this.e == '\"' || this.e == '\'') {
                        char c2 = this.e;
                        while (this.d > 0) {
                            String str2 = this.a;
                            int i3 = this.d - 1;
                            this.d = i3;
                            char charAt = str2.charAt(i3);
                            this.e = charAt;
                            if (charAt == c2) {
                                break;
                            }
                        }
                    }
                } else {
                    return ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                }
            }
            this.d = i;
            this.e = '>';
            return 13;
        }

        private byte c() {
            int i = this.d;
            while (this.d > 0) {
                String str = this.a;
                int i2 = this.d - 1;
                this.d = i2;
                this.e = str.charAt(i2);
                if (this.e != '&') {
                    if (this.e == ';') {
                        break;
                    }
                } else {
                    return ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                }
            }
            this.d = i;
            this.e = ';';
            return 13;
        }
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(boolean z) {
        return new Builder(z).build();
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    private BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mIsRtlContext = z;
        this.mFlags = i;
        this.mDefaultTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
    }

    public final boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public final boolean getStereoReset() {
        return (this.mFlags & 2) != 0;
    }

    private String markAfter(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean isRtl = textDirectionHeuristicCompat.isRtl((CharSequence) str, 0, str.length());
        if (this.mIsRtlContext || (!isRtl && getExitDir(str) != 1)) {
            return (!this.mIsRtlContext || (isRtl && getExitDir(str) != -1)) ? "" : RLM_STRING;
        }
        return LRM_STRING;
    }

    private String markBefore(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean isRtl = textDirectionHeuristicCompat.isRtl((CharSequence) str, 0, str.length());
        if (this.mIsRtlContext || (!isRtl && getEntryDir(str) != 1)) {
            return (!this.mIsRtlContext || (isRtl && getEntryDir(str) != -1)) ? "" : RLM_STRING;
        }
        return LRM_STRING;
    }

    public final boolean isRtl(String str) {
        return this.mDefaultTextDirectionHeuristicCompat.isRtl((CharSequence) str, 0, str.length());
    }

    public final String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        if (str == null) {
            return null;
        }
        boolean isRtl = textDirectionHeuristicCompat.isRtl((CharSequence) str, 0, str.length());
        StringBuilder sb = new StringBuilder();
        if (getStereoReset() && z) {
            sb.append(markBefore(str, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        if (isRtl != this.mIsRtlContext) {
            sb.append(isRtl ? RLE : LRE);
            sb.append(str);
            sb.append(PDF);
        } else {
            sb.append(str);
        }
        if (z) {
            sb.append(markAfter(str, isRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        return sb.toString();
    }

    public final String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(str, textDirectionHeuristicCompat, true);
    }

    public final String unicodeWrap(String str, boolean z) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, z);
    }

    public final String unicodeWrap(String str) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    /* access modifiers changed from: private */
    public static boolean isRtlLocale(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getExitDir(java.lang.String r6) {
        /*
            android.support.v4.text.BidiFormatter$DirectionalityEstimator r0 = new android.support.v4.text.BidiFormatter$DirectionalityEstimator
            r0.<init>(r6)
            int r6 = r0.c
            r0.d = r6
            r6 = 0
            r1 = 0
            r2 = 0
        L_0x000c:
            int r3 = r0.d
            if (r3 <= 0) goto L_0x003f
            byte r3 = r0.a()
            r4 = 9
            if (r3 == r4) goto L_0x000c
            r4 = 1
            r5 = -1
            switch(r3) {
                case 0: goto L_0x0038;
                case 1: goto L_0x0032;
                case 2: goto L_0x0032;
                default: goto L_0x001d;
            }
        L_0x001d:
            switch(r3) {
                case 14: goto L_0x002c;
                case 15: goto L_0x002c;
                case 16: goto L_0x0026;
                case 17: goto L_0x0026;
                case 18: goto L_0x0023;
                default: goto L_0x0020;
            }
        L_0x0020:
            if (r1 != 0) goto L_0x000c
            goto L_0x003d
        L_0x0023:
            int r2 = r2 + 1
            goto L_0x000c
        L_0x0026:
            if (r1 != r2) goto L_0x0029
            return r4
        L_0x0029:
            int r2 = r2 + -1
            goto L_0x000c
        L_0x002c:
            if (r1 != r2) goto L_0x002f
            return r5
        L_0x002f:
            int r2 = r2 + -1
            goto L_0x000c
        L_0x0032:
            if (r2 != 0) goto L_0x0035
            return r4
        L_0x0035:
            if (r1 != 0) goto L_0x000c
            goto L_0x003d
        L_0x0038:
            if (r2 != 0) goto L_0x003b
            return r5
        L_0x003b:
            if (r1 != 0) goto L_0x000c
        L_0x003d:
            r1 = r2
            goto L_0x000c
        L_0x003f:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.text.BidiFormatter.getExitDir(java.lang.String):int");
    }

    private static int getEntryDir(String str) {
        byte b;
        DirectionalityEstimator directionalityEstimator = new DirectionalityEstimator(str);
        directionalityEstimator.d = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (directionalityEstimator.d < directionalityEstimator.c && i == 0) {
            directionalityEstimator.e = directionalityEstimator.a.charAt(directionalityEstimator.d);
            if (Character.isHighSurrogate(directionalityEstimator.e)) {
                int codePointAt = Character.codePointAt(directionalityEstimator.a, directionalityEstimator.d);
                directionalityEstimator.d += Character.charCount(codePointAt);
                b = Character.getDirectionality(codePointAt);
            } else {
                directionalityEstimator.d++;
                b = DirectionalityEstimator.a(directionalityEstimator.e);
                boolean z = directionalityEstimator.b;
                byte b2 = ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                if (z) {
                    if (directionalityEstimator.e == '<') {
                        int i4 = directionalityEstimator.d;
                        while (true) {
                            if (directionalityEstimator.d < directionalityEstimator.c) {
                                String str2 = directionalityEstimator.a;
                                int i5 = directionalityEstimator.d;
                                directionalityEstimator.d = i5 + 1;
                                directionalityEstimator.e = str2.charAt(i5);
                                if (directionalityEstimator.e != '>') {
                                    if (directionalityEstimator.e == '\"' || directionalityEstimator.e == '\'') {
                                        char c = directionalityEstimator.e;
                                        while (directionalityEstimator.d < directionalityEstimator.c) {
                                            String str3 = directionalityEstimator.a;
                                            int i6 = directionalityEstimator.d;
                                            directionalityEstimator.d = i6 + 1;
                                            char charAt = str3.charAt(i6);
                                            directionalityEstimator.e = charAt;
                                            if (charAt == c) {
                                            }
                                        }
                                    }
                                }
                            } else {
                                directionalityEstimator.d = i4;
                                directionalityEstimator.e = '<';
                                b2 = 13;
                            }
                        }
                        b = b2;
                    } else if (directionalityEstimator.e == '&') {
                        while (directionalityEstimator.d < directionalityEstimator.c) {
                            String str4 = directionalityEstimator.a;
                            int i7 = directionalityEstimator.d;
                            directionalityEstimator.d = i7 + 1;
                            char charAt2 = str4.charAt(i7);
                            directionalityEstimator.e = charAt2;
                            if (charAt2 == ';') {
                                b = ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                            }
                        }
                        b = ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                    }
                }
            }
            if (b != 9) {
                switch (b) {
                    case 0:
                        if (i3 == 0) {
                            return -1;
                        }
                        break;
                    case 1:
                    case 2:
                        if (i3 == 0) {
                            return 1;
                        }
                        break;
                    default:
                        switch (b) {
                            case 14:
                            case 15:
                                i3++;
                                i2 = -1;
                                continue;
                            case 16:
                            case 17:
                                i3++;
                                i2 = 1;
                                continue;
                            case 18:
                                i3--;
                                i2 = 0;
                                continue;
                                continue;
                        }
                }
                i = i3;
            }
        }
        if (i != 0) {
            if (i2 == 0) {
                while (directionalityEstimator.d > 0) {
                    switch (directionalityEstimator.a()) {
                        case 14:
                        case 15:
                            if (i != i3) {
                                i3--;
                                break;
                            } else {
                                return -1;
                            }
                        case 16:
                        case 17:
                            if (i != i3) {
                                i3--;
                                break;
                            } else {
                                return 1;
                            }
                        case 18:
                            i3++;
                            break;
                    }
                }
            } else {
                return i2;
            }
        }
        return 0;
    }
}
