package android.support.dontuse.text;

import java.util.Locale;

public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat a = new TextDirectionHeuristicInternal(null, false);
    public static final TextDirectionHeuristicCompat b = new TextDirectionHeuristicInternal(null, true);
    public static final TextDirectionHeuristicCompat c = new TextDirectionHeuristicInternal(FirstStrong.a, false);
    public static final TextDirectionHeuristicCompat d = new TextDirectionHeuristicInternal(FirstStrong.a, true);
    public static final TextDirectionHeuristicCompat e = new TextDirectionHeuristicInternal(AnyStrong.a, false);
    public static final TextDirectionHeuristicCompat f = TextDirectionHeuristicLocale.a;

    static class AnyStrong implements TextDirectionAlgorithm {
        public static final AnyStrong a = new AnyStrong(true);
        public static final AnyStrong b = new AnyStrong(false);
        private final boolean c;

        public final int a(CharSequence charSequence, int i) {
            int i2 = i + 0;
            boolean z = false;
            for (int i3 = 0; i3 < i2; i3++) {
                switch (TextDirectionHeuristicsCompat.a(Character.getDirectionality(charSequence.charAt(i3)))) {
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

        public final int a(CharSequence charSequence, int i) {
            int i2 = i + 0;
            int i3 = 2;
            for (int i4 = 0; i4 < i2 && i3 == 2; i4++) {
                i3 = TextDirectionHeuristicsCompat.b(Character.getDirectionality(charSequence.charAt(i4)));
            }
            return i3;
        }

        private FirstStrong() {
        }
    }

    interface TextDirectionAlgorithm {
        int a(CharSequence charSequence, int i);
    }

    static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm a;

        /* access modifiers changed from: protected */
        public abstract boolean a();

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.a = textDirectionAlgorithm;
        }

        public final boolean a(CharSequence charSequence, int i) {
            if (charSequence == null || i < 0 || charSequence.length() - i < 0) {
                throw new IllegalArgumentException();
            } else if (this.a == null) {
                return a();
            } else {
                switch (this.a.a(charSequence, i)) {
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

        TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z) {
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
            return TextUtilsCompat.a(Locale.getDefault()) == 1;
        }
    }

    static int a(int i) {
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

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000b, code lost:
        return 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int b(int r0) {
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
        throw new UnsupportedOperationException("Method not decompiled: android.support.dontuse.text.TextDirectionHeuristicsCompat.b(int):int");
    }
}
