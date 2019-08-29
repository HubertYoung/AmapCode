package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.os.SystemClock;

class MediaSessionCompatApi18 {
    private static boolean a = true;

    static class OnPlaybackPositionUpdateListener<T extends Callback> implements android.media.RemoteControlClient.OnPlaybackPositionUpdateListener {
        protected final T a;

        public OnPlaybackPositionUpdateListener(T t) {
            this.a = t;
        }

        public void onPlaybackPositionUpdate(long j) {
            this.a.a(j);
        }
    }

    MediaSessionCompatApi18() {
    }

    public static Object a(Callback callback) {
        return new OnPlaybackPositionUpdateListener(callback);
    }

    public static void a(Context context, PendingIntent pendingIntent, ComponentName componentName) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (a) {
            try {
                audioManager.registerMediaButtonEventReceiver(pendingIntent);
            } catch (NullPointerException unused) {
                a = false;
            }
        }
        if (!a) {
            audioManager.registerMediaButtonEventReceiver(componentName);
        }
    }

    public static void b(Context context, PendingIntent pendingIntent, ComponentName componentName) {
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (a) {
            audioManager.unregisterMediaButtonEventReceiver(pendingIntent);
        } else {
            audioManager.unregisterMediaButtonEventReceiver(componentName);
        }
    }

    public static void a(Object obj, int i, long j, float f, long j2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (i == 3) {
            long j3 = 0;
            if (j > 0) {
                if (j2 > 0) {
                    j3 = elapsedRealtime - j2;
                    if (f > 0.0f && f != 1.0f) {
                        j3 = (long) (((float) j3) * f);
                    }
                }
                j += j3;
            }
        }
        ((RemoteControlClient) obj).setPlaybackState(MediaSessionCompatApi14.a(i), j, f);
    }

    public static void a(Object obj, long j) {
        ((RemoteControlClient) obj).setTransportControlFlags(a(j));
    }

    public static void a(Object obj, Object obj2) {
        ((RemoteControlClient) obj).setPlaybackPositionUpdateListener((android.media.RemoteControlClient.OnPlaybackPositionUpdateListener) obj2);
    }

    static int a(long j) {
        int a2 = MediaSessionCompatApi14.a(j);
        return (j & 256) != 0 ? a2 | 256 : a2;
    }
}
