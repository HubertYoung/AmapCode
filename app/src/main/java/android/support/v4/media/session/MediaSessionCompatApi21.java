package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes.Builder;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Token;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MediaSessionCompatApi21 {

    interface Callback {
        void a();

        void a(long j);

        void a(Object obj);

        void a(String str, Bundle bundle);

        void a(String str, Bundle bundle, ResultReceiver resultReceiver);

        boolean a(Intent intent);

        void b();

        void b(long j);

        void b(String str, Bundle bundle);

        void c();

        void c(String str, Bundle bundle);

        void d();

        void e();

        void f();

        void g();
    }

    static class CallbackProxy<T extends Callback> extends android.media.session.MediaSession.Callback {
        protected final T a;

        public CallbackProxy(T t) {
            this.a = t;
        }

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            this.a.a(str, bundle, resultReceiver);
        }

        public boolean onMediaButtonEvent(Intent intent) {
            return this.a.a(intent) || super.onMediaButtonEvent(intent);
        }

        public void onPlay() {
            this.a.a();
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
            this.a.a(str, bundle);
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
            this.a.b(str, bundle);
        }

        public void onSkipToQueueItem(long j) {
            this.a.a(j);
        }

        public void onPause() {
            this.a.b();
        }

        public void onSkipToNext() {
            this.a.c();
        }

        public void onSkipToPrevious() {
            this.a.d();
        }

        public void onFastForward() {
            this.a.e();
        }

        public void onRewind() {
            this.a.f();
        }

        public void onStop() {
            this.a.g();
        }

        public void onSeekTo(long j) {
            this.a.b(j);
        }

        public void onSetRating(Rating rating) {
            this.a.a((Object) rating);
        }

        public void onCustomAction(String str, Bundle bundle) {
            this.a.c(str, bundle);
        }
    }

    static class QueueItem {
        QueueItem() {
        }

        public static Object a(Object obj, long j) {
            return new android.media.session.MediaSession.QueueItem((MediaDescription) obj, j);
        }

        public static Object a(Object obj) {
            return ((android.media.session.MediaSession.QueueItem) obj).getDescription();
        }

        public static long b(Object obj) {
            return ((android.media.session.MediaSession.QueueItem) obj).getQueueId();
        }
    }

    MediaSessionCompatApi21() {
    }

    public static Object a(Context context, String str) {
        return new MediaSession(context, str);
    }

    public static Object a(Object obj) {
        if (obj instanceof MediaSession) {
            return obj;
        }
        throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
    }

    public static Object b(Object obj) {
        if (obj instanceof Token) {
            return obj;
        }
        throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
    }

    public static Object a(Callback callback) {
        return new CallbackProxy(callback);
    }

    public static void a(Object obj, Object obj2, Handler handler) {
        ((MediaSession) obj).setCallback((android.media.session.MediaSession.Callback) obj2, handler);
    }

    public static void a(Object obj, int i) {
        ((MediaSession) obj).setFlags(i);
    }

    public static void b(Object obj, int i) {
        Builder builder = new Builder();
        builder.setLegacyStreamType(i);
        ((MediaSession) obj).setPlaybackToLocal(builder.build());
    }

    public static void a(Object obj, Object obj2) {
        ((MediaSession) obj).setPlaybackToRemote((VolumeProvider) obj2);
    }

    public static void a(Object obj, boolean z) {
        ((MediaSession) obj).setActive(z);
    }

    public static boolean c(Object obj) {
        return ((MediaSession) obj).isActive();
    }

    public static void a(Object obj, String str, Bundle bundle) {
        ((MediaSession) obj).sendSessionEvent(str, bundle);
    }

    public static void d(Object obj) {
        ((MediaSession) obj).release();
    }

    public static Parcelable e(Object obj) {
        return ((MediaSession) obj).getSessionToken();
    }

    public static void b(Object obj, Object obj2) {
        ((MediaSession) obj).setPlaybackState((PlaybackState) obj2);
    }

    public static void c(Object obj, Object obj2) {
        ((MediaSession) obj).setMetadata((MediaMetadata) obj2);
    }

    public static void a(Object obj, PendingIntent pendingIntent) {
        ((MediaSession) obj).setSessionActivity(pendingIntent);
    }

    public static void b(Object obj, PendingIntent pendingIntent) {
        ((MediaSession) obj).setMediaButtonReceiver(pendingIntent);
    }

    public static void a(Object obj, List<Object> list) {
        if (list == null) {
            ((MediaSession) obj).setQueue(null);
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((android.media.session.MediaSession.QueueItem) it.next());
        }
        ((MediaSession) obj).setQueue(arrayList);
    }

    public static void a(Object obj, CharSequence charSequence) {
        ((MediaSession) obj).setQueueTitle(charSequence);
    }

    public static void a(Object obj, Bundle bundle) {
        ((MediaSession) obj).setExtras(bundle);
    }
}
