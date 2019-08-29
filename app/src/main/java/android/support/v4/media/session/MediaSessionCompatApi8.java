package android.support.v4.media.session;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;

class MediaSessionCompatApi8 {
    MediaSessionCompatApi8() {
    }

    public static void a(Context context, ComponentName componentName) {
        ((AudioManager) context.getSystemService("audio")).registerMediaButtonEventReceiver(componentName);
    }

    public static void b(Context context, ComponentName componentName) {
        ((AudioManager) context.getSystemService("audio")).unregisterMediaButtonEventReceiver(componentName);
    }
}
