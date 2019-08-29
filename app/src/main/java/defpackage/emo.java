package defpackage;

import android.content.Context;

/* renamed from: emo reason: default package */
/* compiled from: UIPerformance */
public class emo {
    private static volatile emo b;
    public emm a;
    private Context c;

    private emo(Context context) {
        this.a = new emm(context);
        this.c = context;
    }

    public static emo a(Context context) {
        if (b == null) {
            synchronized (emo.class) {
                try {
                    if (b == null) {
                        b = new emo(context.getApplicationContext());
                    }
                }
            }
        }
        return b;
    }

    public final void a() {
        if (!kj.c(this.c)) {
            kj.d(this.c);
        } else {
            this.a.a();
        }
    }
}
