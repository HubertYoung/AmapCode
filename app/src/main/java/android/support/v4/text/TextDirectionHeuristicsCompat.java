package android.support.v4.text;

import java.nio.CharBuffer;
import java.util.Locale;

public class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.a, false, 0);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.a, false, 0);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.a, true, 0);
    public static final TextDirectionHeuristicCompat LOCALE = TextDirectionHeuristicLocale.a;
    public static final TextDirectionHeuristicCompat LTR = new TextDirectionHeuristicInternal(null, false, 0);
    public static final TextDirectionHeuristicCompat RTL = new TextDirectionHeuristicInternal(null, true, 0);
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    static class AnyStrong implements TextDirectionAlgorithm {
        public static final AnyStrong a = new AnyStrong(true);
        public static final AnyStrong b = new AnyStrong(false);
        private final boolean c;

        public final int a(CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            boolean z = false;
            while (i < i3) {
                switch (TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality(charSequence.charAt(i)))) {
                    case 0:
                        if (this.c) {
                            return 0;
                        }
                        break;
                    case 1:
                        if (!this.c) {
                            return 1;
                        }
                        break;
                }
                z = true;
                i++;
            }
            if (z) {
                return this.c ? 1 : 0;
            }
            return 2;
        }

        private AnyStrong(boolean z) {
            this.c = z;
        }
    }

    static class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong a = new FirstStrong();

        public final int a(CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            int i4 = 2;
            while (i < i3 && i4 == 2) {
                i4 = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(charSequence.charAt(i)));
                i++;
            }
            return i4;
        }

        private FirstStrong() {
        }
    }

    interface TextDirectionAlgorithm {
        int a(CharSequence charSequence, int i, int i2);
    }

    static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm a;

        /* access modifiers changed from: protected */
        public abstract boolean a();

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.a = textDirectionAlgorithm;
        }

        public boolean isRtl(char[] cArr, int i, int i2) {
            return isRtl((CharSequence) CharBuffer.wrap(cArr), i, i2);
        }

        public boolean isRtl(CharSequence charSequence, int i, int i2) {
            if (charSequence == null || i < 0 || i2 < 0 || charSequence.length() - i2 < i) {
                throw new IllegalArgumentException();
            } else if (this.a == null) {
                return a();
            } else {
                switch (this.a.a(charSequence, i, i2)) {
                    case 0:
                        return true;
                    case 1:
                        return false;
                    default:
                        return a();
                }
            }
        }
    }

    static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final boolean a;

        /* synthetic */ TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z, byte b) {
            this(textDirectionAlgorithm, z);
        }

        private TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z) {
            super(textDirectionAlgorithm);
            this.a = z;
        }

        /* access modifiers changed from: protected */
        public final boolean a() {
            return this.a;
        }
    }

    static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale a = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        /* access modifiers changed from: protected */
        public final boolean a() {
            return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }
    }

    /* access modifiers changed from: private */
    public static int isRtlText(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                return 2;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000b, code lost:
        return 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int isRtlTextOrFormat(int r0) {
        /*
            switch(r0) {
                case 0: goto L_0x000a;
                case 1: goto L_0x0008;
                case 2: goto L_0x0008;
                default: goto L_0x0003;
            }
        L_0x0003:
            switch(r0) {
                case 14: goto L_0x000a;
                case 15: goto L_0x000a;
                case 16: goto L_0x0008;
                case 17: goto L_0x0008;
                default: goto L_0x0006;
            }
        L_0x0006:
            r0 = 2
            return r0
        L_0x0008:
            r0 = 0
            return r0
        L_0x000a:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.text.TextDirectionHeuristicsCompat.isRtlTextOrFormat(int):int");
    }
}
