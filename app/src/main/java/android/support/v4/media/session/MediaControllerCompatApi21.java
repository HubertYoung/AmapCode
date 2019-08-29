package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession.QueueItem;
import android.media.session.MediaSession.Token;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

class MediaControllerCompatApi21 {

    public interface Callback {
        void onMetadataChanged(Object obj);

        void onPlaybackStateChanged(Object obj);

        void onSessionDestroyed();

        void onSessionEvent(String str, Bundle bundle);
    }

    static class CallbackProxy<T extends Callback> extends android.media.session.MediaController.Callback {
        protected final T a;

        public CallbackProxy(T t) {
            this.a = t;
        }

        public void onSessionDestroyed() {
            this.a.onSessionDestroyed();
        }

        public void onSessionEvent(String str, Bundle bundle) {
            this.a.onSessionEvent(str, bundle);
        }

        public void onPlaybackStateChanged(PlaybackState playbackState) {
            this.a.onPlaybackStateChanged(playbackState);
        }

        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            this.a.onMetadataChanged(mediaMetadata);
        }
    }

    public static class PlaybackInfo {
        private static final int FLAG_SCO = 4;
        private static final int STREAM_BLUETOOTH_SCO = 6;
        private static final int STREAM_SYSTEM_ENFORCED = 7;

        public static int getPlaybackType(Object obj) {
            return ((android.media.session.MediaController.PlaybackInfo) obj).getPlaybackType();
        }

        public static AudioAttributes getAudioAttributes(Object obj) {
            return ((android.media.session.MediaController.PlaybackInfo) obj).getAudioAttributes();
        }

        public static int getLegacyAudioStream(Object obj) {
            return toLegacyStreamType(getAudioAttributes(obj));
        }

        public static int getVolumeControl(Object obj) {
            return ((android.media.session.MediaController.PlaybackInfo) obj).getVolumeControl();
        }

        public static int getMaxVolume(Object obj) {
            return ((android.media.session.MediaController.PlaybackInfo) obj).getMaxVolume();
        }

        public static int getCurrentVolume(Object obj) {
            return ((android.media.session.MediaController.PlaybackInfo) obj).getCurrentVolume();
        }

        private static int toLegacyStreamType(AudioAttributes audioAttributes) {
            if ((audioAttributes.getFlags() & 1) == 1) {
                return 7;
            }
            if ((audioAttributes.getFlags() & 4) == 4) {
                return 6;
            }
            switch (audioAttributes.getUsage()) {
                case 1:
                case 11:
                case 12:
                case 14:
                    return 3;
                case 2:
                    return 0;
                case 3:
                    return 8;
                case 4:
                    return 4;
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                    return 5;
                case 6:
                    return 2;
                case 13:
                    return 1;
                default:
                    return 3;
            }
        }
    }

    public static class TransportControls {
        public static void play(Object obj) {
            ((android.media.session.MediaController.TransportControls) obj).play();
        }

        public static void pause(Object obj) {
            ((android.media.session.MediaController.TransportControls) obj).pause();
        }

        public static void stop(Object obj) {
            ((android.media.session.MediaController.TransportControls) obj).stop();
        }

        public static void seekTo(Object obj, long j) {
            ((android.media.session.MediaController.TransportControls) obj).seekTo(j);
        }

        public static void fastForward(Object obj) {
            ((android.media.session.MediaController.TransportControls) obj).fastForward();
        }

        public static void rewind(Object obj) {
            ((android.media.session.MediaController.TransportControls) obj).rewind();
        }

        public static void skipToNext(Object obj) {
            ((android.media.session.MediaController.TransportControls) obj).skipToNext();
        }

        public static void skipToPrevious(Object obj) {
            ((android.media.session.MediaController.TransportControls) obj).skipToPrevious();
        }

        public static void setRating(Object obj, Object obj2) {
            ((android.media.session.MediaController.TransportControls) obj).setRating((Rating) obj2);
        }

        public static void playFromMediaId(Object obj, String str, Bundle bundle) {
            ((android.media.session.MediaController.TransportControls) obj).playFromMediaId(str, bundle);
        }

        public static void playFromSearch(Object obj, String str, Bundle bundle) {
            ((android.media.session.MediaController.TransportControls) obj).playFromSearch(str, bundle);
        }

        public static void skipToQueueItem(Object obj, long j) {
            ((android.media.session.MediaController.TransportControls) obj).skipToQueueItem(j);
        }

        public static void sendCustomAction(Object obj, String str, Bundle bundle) {
            ((android.media.session.MediaController.TransportControls) obj).sendCustomAction(str, bundle);
        }
    }

    MediaControllerCompatApi21() {
    }

    public static Object a(Context context, Object obj) {
        return new MediaController(context, (Token) obj);
    }

    public static Object a(Callback callback) {
        return new CallbackProxy(callback);
    }

    public static void a(Object obj, Object obj2, Handler handler) {
        ((MediaController) obj).registerCallback((android.media.session.MediaController.Callback) obj2, handler);
    }

    public static void a(Object obj, Object obj2) {
        ((MediaController) obj).unregisterCallback((android.media.session.MediaController.Callback) obj2);
    }

    public static Object a(Object obj) {
        return ((MediaController) obj).getTransportControls();
    }

    public static Object b(Object obj) {
        return ((MediaController) obj).getPlaybackState();
    }

    public static Object c(Object obj) {
        return ((MediaController) obj).getMetadata();
    }

    public static List<Object> d(Object obj) {
        List<QueueItem> queue = ((MediaController) obj).getQueue();
        if (queue == null) {
            return null;
        }
        return new ArrayList(queue);
    }

    public static CharSequence e(Object obj) {
        return ((MediaController) obj).getQueueTitle();
    }

    public static Bundle f(Object obj) {
        return ((MediaController) obj).getExtras();
    }

    public static int g(Object obj) {
        return ((MediaController) obj).getRatingType();
    }

    public static long h(Object obj) {
        return ((MediaController) obj).getFlags();
    }

    public static Object i(Object obj) {
        return ((MediaController) obj).getPlaybackInfo();
    }

    public static PendingIntent j(Object obj) {
        return ((MediaController) obj).getSessionActivity();
    }

    public static boolean a(Object obj, KeyEvent keyEvent) {
        return ((MediaController) obj).dispatchMediaButtonEvent(keyEvent);
    }

    public static void a(Object obj, int i, int i2) {
        ((MediaController) obj).setVolumeTo(i, i2);
    }

    public static void b(Object obj, int i, int i2) {
        ((MediaController) obj).adjustVolume(i, i2);
    }

    public static void a(Object obj, String str, Bundle bundle, ResultReceiver resultReceiver) {
        ((MediaController) obj).sendCommand(str, bundle, resultReceiver);
    }

    public static String k(Object obj) {
        return ((MediaController) obj).getPackageName();
    }
}
