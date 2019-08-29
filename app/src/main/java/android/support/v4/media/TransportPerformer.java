package android.support.v4.media;

import android.os.SystemClock;
import android.view.KeyEvent;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetPriority;

public abstract class TransportPerformer {
    static final int AUDIOFOCUS_GAIN = 1;
    static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    static final int AUDIOFOCUS_LOSS = -1;
    static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;

    public int onGetBufferPercentage() {
        return 100;
    }

    public abstract long onGetCurrentPosition();

    public abstract long onGetDuration();

    public int onGetTransportControlFlags() {
        return 60;
    }

    public abstract boolean onIsPlaying();

    public boolean onMediaButtonUp(int i, KeyEvent keyEvent) {
        return true;
    }

    public abstract void onPause();

    public abstract void onSeekTo(long j);

    public abstract void onStart();

    public abstract void onStop();

    public boolean onMediaButtonDown(int i, KeyEvent keyEvent) {
        switch (i) {
            case 79:
            case WidgetPriority.GPS /*85*/:
                if (!onIsPlaying()) {
                    onStart();
                    break;
                } else {
                    onPause();
                    break;
                }
            case 86:
                onStop();
                return true;
            case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                onStart();
                return true;
            case 127:
                onPause();
                return true;
        }
        return true;
    }

    public void onAudioFocusChange(int i) {
        if ((i != -1 ? (char) 0 : 127) != 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = uptimeMillis;
            long j2 = uptimeMillis;
            KeyEvent keyEvent = new KeyEvent(j, j2, 0, 127, 0);
            onMediaButtonDown(127, keyEvent);
            KeyEvent keyEvent2 = new KeyEvent(j, j2, 1, 127, 0);
            onMediaButtonUp(127, keyEvent2);
        }
    }
}
