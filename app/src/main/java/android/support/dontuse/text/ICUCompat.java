package android.support.dontuse.text;

import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import java.util.Locale;

public final class ICUCompat {
    private static final ICUCompatBaseImpl a;

    @RequiresApi(21)
    static class ICUCompatApi21Impl extends ICUCompatBaseImpl {
        ICUCompatApi21Impl() {
        }

        public final String a(Locale locale) {
            return ICUCompatApi21.a(locale);
        }
    }

    static class ICUCompatBaseImpl {
        ICUCompatBaseImpl() {
        }

        public String a(Locale locale) {
            return ICUCompatIcs.a(locale);
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            a = new ICUCompatApi21Impl();
        } else {
            a = new ICUCompatBaseImpl();
        }
    }

    @Nullable
    public static String a(Locale locale) {
        return a.a(locale);
    }
}
