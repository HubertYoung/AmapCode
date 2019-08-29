package defpackage;

import android.content.Context;
import android.media.AudioManager;

/* renamed from: ka reason: default package */
/* compiled from: AmapAudioManager */
public class ka {
    private static volatile ka b;
    public AudioManager a;

    public static ka a(Context context) {
        if (b == null) {
            synchronized (ka.class) {
                try {
                    if (b == null) {
                        b = new ka(context);
                    }
                }
            }
        }
        return b;
    }

    private ka(Context context) {
        this.a = (AudioManager) context.getApplicationContext().getSystemService("audio");
    }
}
