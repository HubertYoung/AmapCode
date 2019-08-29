package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Locale;

public class TextUtilsCompat {
    /* access modifiers changed from: private */
    public static String ARAB_SCRIPT_SUBTAG = "Arab";
    /* access modifiers changed from: private */
    public static String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final TextUtilsCompatImpl IMPL;
    public static final Locale ROOT = new Locale("", "");

    static class TextUtilsCompatImpl {
        private TextUtilsCompatImpl() {
        }

        /* synthetic */ TextUtilsCompatImpl(byte b) {
            this();
        }

        @NonNull
        public String a(@NonNull String str) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt == '\"') {
                    sb.append("&quot;");
                } else if (charAt == '<') {
                    sb.append("&lt;");
                } else if (charAt != '>') {
                    switch (charAt) {
                        case '&':
                            sb.append("&amp;");
                            break;
                        case '\'':
                            sb.append("&#39;");
                            break;
                        default:
                            sb.append(charAt);
                            break;
                    }
                } else {
                    sb.append("&gt;");
                }
            }
            return sb.toString();
        }

        public int a(@Nullable Locale locale) {
            if (locale != null && !locale.equals(TextUtilsCompat.ROOT)) {
                String maximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale);
                if (maximizeAndGetScript == null) {
                    switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                        case 1:
                        case 2:
                            return 1;
                        default:
                            return 0;
                    }
                } else if (maximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.ARAB_SCRIPT_SUBTAG) || maximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.HEBR_SCRIPT_SUBTAG)) {
                    return 1;
                }
            }
            return 0;
        }
    }

    static class TextUtilsCompatJellybeanMr1Impl extends TextUtilsCompatImpl {
        private TextUtilsCompatJellybeanMr1Impl() {
            super(0);
        }

        /* synthetic */ TextUtilsCompatJellybeanMr1Impl(byte b) {
            this();
        }

        @NonNull
        public final String a(@NonNull String str) {
            return TextUtilsCompatJellybeanMr1.htmlEncode(str);
        }

        public final int a(@Nullable Locale locale) {
            return TextUtilsCompatJellybeanMr1.getLayoutDirectionFromLocale(locale);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new TextUtilsCompatJellybeanMr1Impl(0);
        } else {
            IMPL = new TextUtilsCompatImpl(0);
        }
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        return IMPL.a(str);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        return IMPL.a(locale);
    }
}
