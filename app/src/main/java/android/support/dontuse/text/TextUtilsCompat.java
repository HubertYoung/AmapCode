package android.support.dontuse.text;

import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.Locale;

public final class TextUtilsCompat {
    @Deprecated
    public static final Locale a = new Locale("", "");

    public static int a(@Nullable Locale locale) {
        if (VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale(locale);
        }
        if (locale != null && !locale.equals(a)) {
            String a2 = ICUCompat.a(locale);
            if (a2 == null) {
                switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                    case 1:
                    case 2:
                        return 1;
                    default:
                        return 0;
                }
            } else if (a2.equalsIgnoreCase("Arab") || a2.equalsIgnoreCase("Hebr")) {
                return 1;
            }
        }
        return 0;
    }
}
