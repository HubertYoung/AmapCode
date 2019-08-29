package defpackage;

import android.content.Context;
import android.media.AudioManager;

/* renamed from: tv reason: default package */
/* compiled from: MediaVolumeManager */
public class tv {
    private static volatile tv a;
    private AudioManager b;

    private tv(Context context) {
        this.b = (AudioManager) context.getSystemService("audio");
    }

    public static tv a(Context context) {
        if (a == null) {
            synchronized (tv.class) {
                try {
                    if (a == null) {
                        a = new tv(context);
                    }
                }
            }
        }
        return a;
    }

    public final void a(boolean z) {
        this.b.adjustStreamVolume(3, 1, z ? 5 : 4);
    }

    public final void b(boolean z) {
        this.b.adjustStreamVolume(3, -1, z ? 5 : 4);
    }
}
