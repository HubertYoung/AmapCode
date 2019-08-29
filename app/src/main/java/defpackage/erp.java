package defpackage;

import android.content.Context;
import android.graphics.Typeface;
import java.util.HashMap;

/* renamed from: erp reason: default package */
/* compiled from: CustomerTypeface */
public final class erp {
    private static erp a;
    private HashMap<String, Typeface> b = new HashMap<>();
    private Context c;

    private erp(Context context) {
        this.c = context;
    }

    public static erp a(Context context) {
        if (a == null) {
            a = new erp(context.getApplicationContext());
        }
        return a;
    }

    public final Typeface a(String str) {
        Typeface typeface = this.b.get(str);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(this.c.getAssets(), "font/".concat(String.valueOf(str)));
                this.b.put(str, typeface);
            } catch (Exception unused) {
                return null;
            }
        }
        return typeface;
    }
}
