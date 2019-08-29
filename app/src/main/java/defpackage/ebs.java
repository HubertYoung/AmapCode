package defpackage;

import android.content.Context;
import android.os.Vibrator;

/* renamed from: ebs reason: default package */
/* compiled from: VibratorUtil */
public class ebs {
    private static volatile ebs b;
    public Vibrator a;

    public static ebs a(Context context) {
        if (b == null) {
            synchronized (ebs.class) {
                try {
                    if (b == null) {
                        b = new ebs(context);
                    }
                }
            }
        }
        return b;
    }

    private ebs(Context context) {
        this.a = (Vibrator) context.getSystemService("vibrator");
    }
}
