package defpackage;

import android.content.Context;

/* renamed from: enh reason: default package */
/* compiled from: MapSoHotfix */
public class enh extends eni {
    private static volatile enh f;

    private enh(Context context) {
        super(context);
    }

    public static enh a(Context context) {
        if (f == null) {
            synchronized (enh.class) {
                try {
                    if (f == null) {
                        f = new enh(context);
                    }
                }
            }
        }
        return f;
    }
}
