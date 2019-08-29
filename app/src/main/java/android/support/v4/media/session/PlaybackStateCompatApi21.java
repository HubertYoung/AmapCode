package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.media.session.PlaybackState.CustomAction.Builder;
import android.os.Bundle;
import java.util.Iterator;
import java.util.List;

class PlaybackStateCompatApi21 {

    static final class CustomAction {
        CustomAction() {
        }

        public static String a(Object obj) {
            return ((android.media.session.PlaybackState.CustomAction) obj).getAction();
        }

        public static CharSequence b(Object obj) {
            return ((android.media.session.PlaybackState.CustomAction) obj).getName();
        }

        public static int c(Object obj) {
            return ((android.media.session.PlaybackState.CustomAction) obj).getIcon();
        }

        public static Bundle d(Object obj) {
            return ((android.media.session.PlaybackState.CustomAction) obj).getExtras();
        }

        public static Object a(String str, CharSequence charSequence, int i, Bundle bundle) {
            Builder builder = new Builder(str, charSequence, i);
            builder.setExtras(bundle);
            return builder.build();
        }
    }

    PlaybackStateCompatApi21() {
    }

    public static int a(Object obj) {
        return ((PlaybackState) obj).getState();
    }

    public static long b(Object obj) {
        return ((PlaybackState) obj).getPosition();
    }

    public static long c(Object obj) {
        return ((PlaybackState) obj).getBufferedPosition();
    }

    public static float d(Object obj) {
        return ((PlaybackState) obj).getPlaybackSpeed();
    }

    public static long e(Object obj) {
        return ((PlaybackState) obj).getActions();
    }

    public static CharSequence f(Object obj) {
        return ((PlaybackState) obj).getErrorMessage();
    }

    public static long g(Object obj) {
        return ((PlaybackState) obj).getLastPositionUpdateTime();
    }

    public static List<Object> h(Object obj) {
        return ((PlaybackState) obj).getCustomActions();
    }

    public static long i(Object obj) {
        return ((PlaybackState) obj).getActiveQueueItemId();
    }

    public static Object a(int i, long j, long j2, float f, long j3, CharSequence charSequence, long j4, List<Object> list, long j5) {
        PlaybackState.Builder builder = new PlaybackState.Builder();
        builder.setState(i, j, f, j4);
        builder.setBufferedPosition(j2);
        builder.setActions(j3);
        builder.setErrorMessage(charSequence);
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            builder.addCustomAction((android.media.session.PlaybackState.CustomAction) it.next());
        }
        builder.setActiveQueueItemId(j5);
        return builder.build();
    }
}
