package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.TransportMediator;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaSession.Stub;
import android.support.v4.media.session.PlaybackStateCompat.Builder;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetPriority;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class MediaSessionCompat {
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    private static final String TAG = "MediaSessionCompat";
    private final ArrayList<OnActiveChangeListener> mActiveListeners;
    private final MediaControllerCompat mController;
    private final MediaSessionImpl mImpl;

    public static abstract class Callback {
        final Object mCallbackObj;

        class StubApi21 implements Callback {
            private StubApi21() {
            }

            /* synthetic */ StubApi21(Callback callback, byte b) {
                this();
            }

            public final void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
                Callback.this.onCommand(str, bundle, resultReceiver);
            }

            public final boolean a(Intent intent) {
                return Callback.this.onMediaButtonEvent(intent);
            }

            public final void a() {
                Callback.this.onPlay();
            }

            public final void a(String str, Bundle bundle) {
                Callback.this.onPlayFromMediaId(str, bundle);
            }

            public final void b(String str, Bundle bundle) {
                Callback.this.onPlayFromSearch(str, bundle);
            }

            public final void a(long j) {
                Callback.this.onSkipToQueueItem(j);
            }

            public final void b() {
                Callback.this.onPause();
            }

            public final void c() {
                Callback.this.onSkipToNext();
            }

            public final void d() {
                Callback.this.onSkipToPrevious();
            }

            public final void e() {
                Callback.this.onFastForward();
            }

            public final void f() {
                Callback.this.onRewind();
            }

            public final void g() {
                Callback.this.onStop();
            }

            public final void b(long j) {
                Callback.this.onSeekTo(j);
            }

            public final void a(Object obj) {
                Callback.this.onSetRating(RatingCompat.fromRating(obj));
            }

            public final void c(String str, Bundle bundle) {
                Callback.this.onCustomAction(str, bundle);
            }
        }

        class StubApi23 extends StubApi21 implements android.support.v4.media.session.MediaSessionCompatApi23.Callback {
            private StubApi23() {
                super(Callback.this, 0);
            }

            /* synthetic */ StubApi23(Callback callback, byte b2) {
                this();
            }

            public void onPlayFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPlayFromUri(uri, bundle);
            }
        }

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String str, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public boolean onMediaButtonEvent(Intent intent) {
            return false;
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long j) {
        }

        public void onSetRating(RatingCompat ratingCompat) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long j) {
        }

        public void onStop() {
        }

        public Callback() {
            if (VERSION.SDK_INT >= 23) {
                this.mCallbackObj = MediaSessionCompatApi23.a(new StubApi23(this, 0));
            } else if (VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaSessionCompatApi21.a((Callback) new StubApi21(this, 0));
            } else {
                this.mCallbackObj = null;
            }
        }
    }

    interface MediaSessionImpl {
        void a(int i);

        void a(PendingIntent pendingIntent);

        void a(Bundle bundle);

        void a(MediaMetadataCompat mediaMetadataCompat);

        void a(VolumeProviderCompat volumeProviderCompat);

        void a(Callback callback, Handler handler);

        void a(PlaybackStateCompat playbackStateCompat);

        void a(CharSequence charSequence);

        void a(String str, Bundle bundle);

        void a(List<QueueItem> list);

        void a(boolean z);

        boolean a();

        void b();

        void b(int i);

        void b(PendingIntent pendingIntent);

        Token c();

        void c(int i);

        Object d();

        Object e();
    }

    static class MediaSessionImplApi21 implements MediaSessionImpl {
        private final Object a;
        private final Token b = new Token(MediaSessionCompatApi21.e(this.a));
        private PendingIntent c;

        public final Object e() {
            return null;
        }

        public MediaSessionImplApi21(Context context, String str) {
            this.a = MediaSessionCompatApi21.a(context, str);
        }

        public MediaSessionImplApi21(Object obj) {
            this.a = MediaSessionCompatApi21.a(obj);
        }

        public final void a(Callback callback, Handler handler) {
            MediaSessionCompatApi21.a(this.a, callback == null ? null : callback.mCallbackObj, handler);
        }

        public final void a(int i) {
            MediaSessionCompatApi21.a(this.a, i);
        }

        public final void b(int i) {
            MediaSessionCompatApi21.b(this.a, i);
        }

        public final void a(VolumeProviderCompat volumeProviderCompat) {
            MediaSessionCompatApi21.a(this.a, volumeProviderCompat.getVolumeProvider());
        }

        public final void a(boolean z) {
            MediaSessionCompatApi21.a(this.a, z);
        }

        public final boolean a() {
            return MediaSessionCompatApi21.c(this.a);
        }

        public final void a(String str, Bundle bundle) {
            MediaSessionCompatApi21.a(this.a, str, bundle);
        }

        public final void b() {
            MediaSessionCompatApi21.d(this.a);
        }

        public final Token c() {
            return this.b;
        }

        public final void a(PlaybackStateCompat playbackStateCompat) {
            MediaSessionCompatApi21.b(this.a, playbackStateCompat == null ? null : playbackStateCompat.getPlaybackState());
        }

        public final void a(MediaMetadataCompat mediaMetadataCompat) {
            MediaSessionCompatApi21.c(this.a, mediaMetadataCompat == null ? null : mediaMetadataCompat.getMediaMetadata());
        }

        public final void a(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.a(this.a, pendingIntent);
        }

        public final void b(PendingIntent pendingIntent) {
            this.c = pendingIntent;
            MediaSessionCompatApi21.b(this.a, pendingIntent);
        }

        public final void a(List<QueueItem> list) {
            ArrayList arrayList;
            if (list != null) {
                arrayList = new ArrayList();
                for (QueueItem queueItem : list) {
                    arrayList.add(queueItem.getQueueItem());
                }
            } else {
                arrayList = null;
            }
            MediaSessionCompatApi21.a(this.a, (List<Object>) arrayList);
        }

        public final void a(CharSequence charSequence) {
            MediaSessionCompatApi21.a(this.a, charSequence);
        }

        public final void c(int i) {
            if (VERSION.SDK_INT >= 22) {
                MediaSessionCompatApi22.a(this.a, i);
            }
        }

        public final void a(Bundle bundle) {
            MediaSessionCompatApi21.a(this.a, bundle);
        }

        public final Object d() {
            return this.a;
        }
    }

    static class MediaSessionImplBase implements MediaSessionImpl {
        /* access modifiers changed from: private */
        public int A;
        /* access modifiers changed from: private */
        public VolumeProviderCompat B;
        private android.support.v4.media.VolumeProviderCompat.Callback C = new android.support.v4.media.VolumeProviderCompat.Callback() {
            public void onVolumeChanged(VolumeProviderCompat volumeProviderCompat) {
                if (MediaSessionImplBase.this.B == volumeProviderCompat) {
                    ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(MediaSessionImplBase.this.z, MediaSessionImplBase.this.A, volumeProviderCompat.getVolumeControl(), volumeProviderCompat.getMaxVolume(), volumeProviderCompat.getCurrentVolume());
                    MediaSessionImplBase.this.a(parcelableVolumeInfo);
                }
            }
        };
        private final Context a;
        private final ComponentName b;
        private final PendingIntent c;
        private final Object d;
        private final MediaSessionStub e;
        private final Token f;
        /* access modifiers changed from: private */
        public final MessageHandler g;
        /* access modifiers changed from: private */
        public final String h;
        /* access modifiers changed from: private */
        public final String i;
        /* access modifiers changed from: private */
        public final AudioManager j;
        /* access modifiers changed from: private */
        public final Object k = new Object();
        /* access modifiers changed from: private */
        public final RemoteCallbackList<IMediaControllerCallback> l = new RemoteCallbackList<>();
        /* access modifiers changed from: private */
        public boolean m = false;
        private boolean n = false;
        private boolean o = false;
        private boolean p = false;
        /* access modifiers changed from: private */
        public Callback q;
        /* access modifiers changed from: private */
        public int r;
        /* access modifiers changed from: private */
        public MediaMetadataCompat s;
        /* access modifiers changed from: private */
        public PlaybackStateCompat t;
        /* access modifiers changed from: private */
        public PendingIntent u;
        /* access modifiers changed from: private */
        public List<QueueItem> v;
        /* access modifiers changed from: private */
        public CharSequence w;
        /* access modifiers changed from: private */
        public int x;
        /* access modifiers changed from: private */
        public Bundle y;
        /* access modifiers changed from: private */
        public int z;

        static final class Command {
            public final String a;
            public final Bundle b;
            public final ResultReceiver c;

            public Command(String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.a = str;
                this.b = bundle;
                this.c = resultReceiver;
            }
        }

        class MediaSessionStub extends Stub {
            MediaSessionStub() {
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                MediaSessionImplBase.this.g.a(15, new Command(str, bundle, resultReceiverWrapper.a));
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                boolean z = true;
                if ((MediaSessionImplBase.this.r & 1) == 0) {
                    z = false;
                }
                if (z) {
                    MediaSessionImplBase.this.g.a(14, keyEvent);
                }
                return z;
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (MediaSessionImplBase.this.m) {
                    try {
                        iMediaControllerCallback.onSessionDestroyed();
                    } catch (Exception unused) {
                    }
                } else {
                    MediaSessionImplBase.this.l.register(iMediaControllerCallback);
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplBase.this.l.unregister(iMediaControllerCallback);
            }

            public String getPackageName() {
                return MediaSessionImplBase.this.h;
            }

            public String getTag() {
                return MediaSessionImplBase.this.i;
            }

            public PendingIntent getLaunchPendingIntent() {
                PendingIntent k;
                synchronized (MediaSessionImplBase.this.k) {
                    k = MediaSessionImplBase.this.u;
                }
                return k;
            }

            public long getFlags() {
                long e;
                synchronized (MediaSessionImplBase.this.k) {
                    e = (long) MediaSessionImplBase.this.r;
                }
                return e;
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                int b;
                int c;
                int i;
                int i2;
                int i3;
                synchronized (MediaSessionImplBase.this.k) {
                    b = MediaSessionImplBase.this.z;
                    c = MediaSessionImplBase.this.A;
                    VolumeProviderCompat a = MediaSessionImplBase.this.B;
                    if (b == 2) {
                        int volumeControl = a.getVolumeControl();
                        int maxVolume = a.getMaxVolume();
                        i = a.getCurrentVolume();
                        i2 = maxVolume;
                        i3 = volumeControl;
                    } else {
                        i2 = MediaSessionImplBase.this.j.getStreamMaxVolume(c);
                        i = MediaSessionImplBase.this.j.getStreamVolume(c);
                        i3 = 2;
                    }
                }
                ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(b, c, i3, i2, i);
                return parcelableVolumeInfo;
            }

            public void adjustVolume(int i, int i2, String str) {
                MediaSessionImplBase.a(MediaSessionImplBase.this, i, i2);
            }

            public void setVolumeTo(int i, int i2, String str) {
                MediaSessionImplBase.b(MediaSessionImplBase.this, i, i2);
            }

            public void play() throws RemoteException {
                MediaSessionImplBase.this.g.a(1, null);
            }

            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.g.a(2, str, bundle);
            }

            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.g.a(3, str, bundle);
            }

            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.g.a(18, uri, bundle);
            }

            public void skipToQueueItem(long j) {
                MediaSessionImplBase.this.g.a(4, Long.valueOf(j));
            }

            public void pause() throws RemoteException {
                MediaSessionImplBase.this.g.a(5, null);
            }

            public void stop() throws RemoteException {
                MediaSessionImplBase.this.g.a(6, null);
            }

            public void next() throws RemoteException {
                MediaSessionImplBase.this.g.a(7, null);
            }

            public void previous() throws RemoteException {
                MediaSessionImplBase.this.g.a(8, null);
            }

            public void fastForward() throws RemoteException {
                MediaSessionImplBase.this.g.a(9, null);
            }

            public void rewind() throws RemoteException {
                MediaSessionImplBase.this.g.a(10, null);
            }

            public void seekTo(long j) throws RemoteException {
                MediaSessionImplBase.this.g.a(11, Long.valueOf(j));
            }

            public void rate(RatingCompat ratingCompat) throws RemoteException {
                MediaSessionImplBase.this.g.a(12, ratingCompat);
            }

            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.g.a(13, str, bundle);
            }

            public MediaMetadataCompat getMetadata() {
                return MediaSessionImplBase.this.s;
            }

            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionImplBase.this.g();
            }

            public List<QueueItem> getQueue() {
                List<QueueItem> o;
                synchronized (MediaSessionImplBase.this.k) {
                    o = MediaSessionImplBase.this.v;
                }
                return o;
            }

            public CharSequence getQueueTitle() {
                return MediaSessionImplBase.this.w;
            }

            public Bundle getExtras() {
                Bundle q;
                synchronized (MediaSessionImplBase.this.k) {
                    q = MediaSessionImplBase.this.y;
                }
                return q;
            }

            public int getRatingType() {
                return MediaSessionImplBase.this.x;
            }

            public boolean isTransportControlEnabled() {
                return (MediaSessionImplBase.this.r & 2) != 0;
            }
        }

        class MessageHandler extends Handler {
            public MessageHandler(Looper looper) {
                super(looper);
            }

            public final void a(int i, Object obj, Bundle bundle) {
                Message obtainMessage = obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }

            public final void a(int i, Object obj) {
                obtainMessage(i, obj).sendToTarget();
            }

            public void handleMessage(Message message) {
                if (MediaSessionImplBase.this.q != null) {
                    switch (message.what) {
                        case 1:
                            MediaSessionImplBase.this.q.onPlay();
                            return;
                        case 2:
                            MediaSessionImplBase.this.q.onPlayFromMediaId((String) message.obj, message.getData());
                            return;
                        case 3:
                            MediaSessionImplBase.this.q.onPlayFromSearch((String) message.obj, message.getData());
                            return;
                        case 4:
                            MediaSessionImplBase.this.q.onSkipToQueueItem(((Long) message.obj).longValue());
                            return;
                        case 5:
                            MediaSessionImplBase.this.q.onPause();
                            return;
                        case 6:
                            MediaSessionImplBase.this.q.onStop();
                            return;
                        case 7:
                            MediaSessionImplBase.this.q.onSkipToNext();
                            return;
                        case 8:
                            MediaSessionImplBase.this.q.onSkipToPrevious();
                            return;
                        case 9:
                            MediaSessionImplBase.this.q.onFastForward();
                            return;
                        case 10:
                            MediaSessionImplBase.this.q.onRewind();
                            return;
                        case 11:
                            MediaSessionImplBase.this.q.onSeekTo(((Long) message.obj).longValue());
                            return;
                        case 12:
                            MediaSessionImplBase.this.q.onSetRating((RatingCompat) message.obj);
                            return;
                        case 13:
                            MediaSessionImplBase.this.q.onCustomAction((String) message.obj, message.getData());
                            return;
                        case 14:
                            KeyEvent keyEvent = (KeyEvent) message.obj;
                            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                            intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                            if (!MediaSessionImplBase.this.q.onMediaButtonEvent(intent) && keyEvent != null && keyEvent.getAction() == 0) {
                                long actions = MediaSessionImplBase.this.t == null ? 0 : MediaSessionImplBase.this.t.getActions();
                                int keyCode = keyEvent.getKeyCode();
                                if (keyCode != 79) {
                                    switch (keyCode) {
                                        case WidgetPriority.GPS /*85*/:
                                            break;
                                        case 86:
                                            if ((1 & actions) != 0) {
                                                MediaSessionImplBase.this.q.onStop();
                                                return;
                                            }
                                            break;
                                        case 87:
                                            if ((32 & actions) != 0) {
                                                MediaSessionImplBase.this.q.onSkipToNext();
                                                return;
                                            }
                                            break;
                                        case IjkMediaMeta.FF_PROFILE_H264_EXTENDED /*88*/:
                                            if ((16 & actions) != 0) {
                                                MediaSessionImplBase.this.q.onSkipToPrevious();
                                                return;
                                            }
                                            break;
                                        case 89:
                                            if ((8 & actions) != 0) {
                                                MediaSessionImplBase.this.q.onRewind();
                                                return;
                                            }
                                            break;
                                        case 90:
                                            if ((64 & actions) != 0) {
                                                MediaSessionImplBase.this.q.onFastForward();
                                                return;
                                            }
                                            break;
                                        default:
                                            switch (keyCode) {
                                                case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                                                    if ((4 & actions) != 0) {
                                                        MediaSessionImplBase.this.q.onPlay();
                                                        return;
                                                    }
                                                    break;
                                                case 127:
                                                    if ((2 & actions) != 0) {
                                                        MediaSessionImplBase.this.q.onPause();
                                                        return;
                                                    }
                                                    break;
                                            }
                                    }
                                }
                                boolean z = true;
                                boolean z2 = MediaSessionImplBase.this.t != null && MediaSessionImplBase.this.t.getState() == 3;
                                boolean z3 = (516 & actions) != 0;
                                if ((actions & 514) == 0) {
                                    z = false;
                                }
                                if (!z2 || !z) {
                                    if (!z2 && z3) {
                                        MediaSessionImplBase.this.q.onPlay();
                                    }
                                    return;
                                }
                                MediaSessionImplBase.this.q.onPause();
                                return;
                            }
                            return;
                        case 15:
                            Command command = (Command) message.obj;
                            MediaSessionImplBase.this.q.onCommand(command.a, command.b, command.c);
                            return;
                        case 16:
                            MediaSessionImplBase.a(MediaSessionImplBase.this, ((Integer) message.obj).intValue(), 0);
                            return;
                        case 17:
                            MediaSessionImplBase.b(MediaSessionImplBase.this, ((Integer) message.obj).intValue(), 0);
                            break;
                        case 18:
                            MediaSessionImplBase.this.q.onPlayFromUri((Uri) message.obj, message.getData());
                            return;
                    }
                }
            }
        }

        public final void b(PendingIntent pendingIntent) {
        }

        public final Object d() {
            return null;
        }

        public MediaSessionImplBase(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
            if (componentName == null) {
                throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
            }
            this.a = context;
            this.h = context.getPackageName();
            this.j = (AudioManager) context.getSystemService("audio");
            this.i = str;
            this.b = componentName;
            this.c = pendingIntent;
            this.e = new MediaSessionStub();
            this.f = new Token(this.e);
            this.g = new MessageHandler(Looper.myLooper());
            this.x = 0;
            this.z = 1;
            this.A = 3;
            if (VERSION.SDK_INT >= 14) {
                this.d = MediaSessionCompatApi14.a(pendingIntent);
            } else {
                this.d = null;
            }
        }

        public final void a(final Callback callback, Handler handler) {
            if (callback != this.q) {
                if (callback == null || VERSION.SDK_INT < 18) {
                    if (VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.a(this.d, (Object) null);
                    }
                    if (VERSION.SDK_INT >= 19) {
                        MediaSessionCompatApi19.a(this.d, (Object) null);
                    }
                } else {
                    if (handler == null) {
                        new Handler();
                    }
                    AnonymousClass2 r5 = new Callback() {
                        public final void a(Object obj) {
                            callback.onSetRating(RatingCompat.fromRating(obj));
                        }

                        public final void a(long j) {
                            callback.onSeekTo(j);
                        }
                    };
                    if (VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.a(this.d, MediaSessionCompatApi18.a((Callback) r5));
                    }
                    if (VERSION.SDK_INT >= 19) {
                        MediaSessionCompatApi19.a(this.d, MediaSessionCompatApi19.a(r5));
                    }
                }
                this.q = callback;
            }
        }

        public final void a(int i2) {
            synchronized (this.k) {
                this.r = i2;
            }
            f();
        }

        public final void b(int i2) {
            if (this.B != null) {
                this.B.setCallback(null);
            }
            this.z = 1;
            ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.z, this.A, 2, this.j.getStreamMaxVolume(this.A), this.j.getStreamVolume(this.A));
            a(parcelableVolumeInfo);
        }

        public final void a(VolumeProviderCompat volumeProviderCompat) {
            if (volumeProviderCompat == null) {
                throw new IllegalArgumentException("volumeProvider may not be null");
            }
            if (this.B != null) {
                this.B.setCallback(null);
            }
            this.z = 2;
            this.B = volumeProviderCompat;
            ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.z, this.A, this.B.getVolumeControl(), this.B.getMaxVolume(), this.B.getCurrentVolume());
            a(parcelableVolumeInfo);
            volumeProviderCompat.setCallback(this.C);
        }

        public final void a(boolean z2) {
            if (z2 != this.n) {
                this.n = z2;
                if (f()) {
                    a(this.s);
                    a(this.t);
                }
            }
        }

        public final boolean a() {
            return this.n;
        }

        public final void b() {
            this.n = false;
            this.m = true;
            f();
            for (int beginBroadcast = this.l.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.l.getBroadcastItem(beginBroadcast).onSessionDestroyed();
                } catch (RemoteException unused) {
                }
            }
            this.l.finishBroadcast();
            this.l.kill();
        }

        public final Token c() {
            return this.f;
        }

        public final void a(PlaybackStateCompat playbackStateCompat) {
            synchronized (this.k) {
                this.t = playbackStateCompat;
            }
            b(playbackStateCompat);
            if (this.n) {
                if (playbackStateCompat != null) {
                    if (VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.a(this.d, playbackStateCompat.getState(), playbackStateCompat.getPosition(), playbackStateCompat.getPlaybackSpeed(), playbackStateCompat.getLastPositionUpdateTime());
                    } else if (VERSION.SDK_INT >= 14) {
                        MediaSessionCompatApi14.a(this.d, playbackStateCompat.getState());
                    }
                    if (VERSION.SDK_INT >= 19) {
                        MediaSessionCompatApi19.a(this.d, playbackStateCompat.getActions());
                    } else if (VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.a(this.d, playbackStateCompat.getActions());
                    } else if (VERSION.SDK_INT >= 14) {
                        MediaSessionCompatApi14.a(this.d, playbackStateCompat.getActions());
                    }
                } else if (VERSION.SDK_INT >= 14) {
                    MediaSessionCompatApi14.a(this.d, 0);
                    MediaSessionCompatApi14.a(this.d, 0);
                }
            }
        }

        public final void a(MediaMetadataCompat mediaMetadataCompat) {
            synchronized (this.k) {
                this.s = mediaMetadataCompat;
            }
            b(mediaMetadataCompat);
            if (this.n) {
                Bundle bundle = null;
                if (VERSION.SDK_INT >= 19) {
                    Object obj = this.d;
                    if (mediaMetadataCompat != null) {
                        bundle = mediaMetadataCompat.getBundle();
                    }
                    MediaSessionCompatApi19.a(obj, bundle, this.t == null ? 0 : this.t.getActions());
                    return;
                }
                if (VERSION.SDK_INT >= 14) {
                    Object obj2 = this.d;
                    if (mediaMetadataCompat != null) {
                        bundle = mediaMetadataCompat.getBundle();
                    }
                    MediaSessionCompatApi14.a(obj2, bundle);
                }
            }
        }

        public final void a(PendingIntent pendingIntent) {
            synchronized (this.k) {
                this.u = pendingIntent;
            }
        }

        public final void a(List<QueueItem> list) {
            this.v = list;
            for (int beginBroadcast = this.l.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.l.getBroadcastItem(beginBroadcast).onQueueChanged(list);
                } catch (RemoteException unused) {
                }
            }
            this.l.finishBroadcast();
        }

        public final void a(CharSequence charSequence) {
            this.w = charSequence;
            for (int beginBroadcast = this.l.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.l.getBroadcastItem(beginBroadcast).onQueueTitleChanged(charSequence);
                } catch (RemoteException unused) {
                }
            }
            this.l.finishBroadcast();
        }

        public final Object e() {
            return this.d;
        }

        public final void c(int i2) {
            this.x = i2;
        }

        public final void a(Bundle bundle) {
            this.y = bundle;
        }

        private boolean f() {
            if (this.n) {
                if (VERSION.SDK_INT >= 8) {
                    if (!this.p && (this.r & 1) != 0) {
                        if (VERSION.SDK_INT >= 18) {
                            MediaSessionCompatApi18.a(this.a, this.c, this.b);
                        } else {
                            MediaSessionCompatApi8.a(this.a, this.b);
                        }
                        this.p = true;
                    } else if (this.p && (this.r & 1) == 0) {
                        if (VERSION.SDK_INT >= 18) {
                            MediaSessionCompatApi18.b(this.a, this.c, this.b);
                        } else {
                            MediaSessionCompatApi8.b(this.a, this.b);
                        }
                        this.p = false;
                    }
                }
                if (VERSION.SDK_INT >= 14) {
                    if (!this.o && (this.r & 2) != 0) {
                        MediaSessionCompatApi14.a(this.a, this.d);
                        this.o = true;
                        return true;
                    } else if (this.o && (this.r & 2) == 0) {
                        MediaSessionCompatApi14.a(this.d, 0);
                        MediaSessionCompatApi14.b(this.a, this.d);
                        this.o = false;
                    }
                }
            } else {
                if (this.p) {
                    if (VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.b(this.a, this.c, this.b);
                    } else {
                        MediaSessionCompatApi8.b(this.a, this.b);
                    }
                    this.p = false;
                }
                if (this.o) {
                    MediaSessionCompatApi14.a(this.d, 0);
                    MediaSessionCompatApi14.b(this.a, this.d);
                    this.o = false;
                }
            }
            return false;
        }

        /* access modifiers changed from: private */
        public PlaybackStateCompat g() {
            PlaybackStateCompat playbackStateCompat;
            long j2;
            synchronized (this.k) {
                playbackStateCompat = this.t;
                j2 = (this.s == null || !this.s.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) ? -1 : this.s.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
            }
            PlaybackStateCompat playbackStateCompat2 = null;
            if (playbackStateCompat != null && (playbackStateCompat.getState() == 3 || playbackStateCompat.getState() == 4 || playbackStateCompat.getState() == 5)) {
                long lastPositionUpdateTime = playbackStateCompat.getLastPositionUpdateTime();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (lastPositionUpdateTime > 0) {
                    long playbackSpeed = ((long) (playbackStateCompat.getPlaybackSpeed() * ((float) (elapsedRealtime - lastPositionUpdateTime)))) + playbackStateCompat.getPosition();
                    long j3 = (j2 < 0 || playbackSpeed <= j2) ? playbackSpeed < 0 ? 0 : playbackSpeed : j2;
                    Builder builder = new Builder(playbackStateCompat);
                    builder.setState(playbackStateCompat.getState(), j3, playbackStateCompat.getPlaybackSpeed(), elapsedRealtime);
                    playbackStateCompat2 = builder.build();
                }
            }
            return playbackStateCompat2 == null ? playbackStateCompat : playbackStateCompat2;
        }

        /* access modifiers changed from: private */
        public void a(ParcelableVolumeInfo parcelableVolumeInfo) {
            for (int beginBroadcast = this.l.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.l.getBroadcastItem(beginBroadcast).onVolumeInfoChanged(parcelableVolumeInfo);
                } catch (RemoteException unused) {
                }
            }
            this.l.finishBroadcast();
        }

        private void b(PlaybackStateCompat playbackStateCompat) {
            for (int beginBroadcast = this.l.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.l.getBroadcastItem(beginBroadcast).onPlaybackStateChanged(playbackStateCompat);
                } catch (RemoteException unused) {
                }
            }
            this.l.finishBroadcast();
        }

        private void b(MediaMetadataCompat mediaMetadataCompat) {
            for (int beginBroadcast = this.l.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.l.getBroadcastItem(beginBroadcast).onMetadataChanged(mediaMetadataCompat);
                } catch (RemoteException unused) {
                }
            }
            this.l.finishBroadcast();
        }

        public final void a(String str, Bundle bundle) {
            for (int beginBroadcast = this.l.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.l.getBroadcastItem(beginBroadcast).onEvent(str, bundle);
                } catch (RemoteException unused) {
                }
            }
            this.l.finishBroadcast();
        }

        static /* synthetic */ void a(MediaSessionImplBase mediaSessionImplBase, int i2, int i3) {
            if (mediaSessionImplBase.z != 2) {
                mediaSessionImplBase.j.adjustStreamVolume(mediaSessionImplBase.A, i2, i3);
            } else if (mediaSessionImplBase.B != null) {
                mediaSessionImplBase.B.onAdjustVolume(i2);
            }
        }

        static /* synthetic */ void b(MediaSessionImplBase mediaSessionImplBase, int i2, int i3) {
            if (mediaSessionImplBase.z != 2) {
                mediaSessionImplBase.j.setStreamVolume(mediaSessionImplBase.A, i2, i3);
            } else if (mediaSessionImplBase.B != null) {
                mediaSessionImplBase.B.onSetVolumeTo(i2);
            }
        }
    }

    public interface OnActiveChangeListener {
        void onActiveChanged();
    }

    public static final class QueueItem implements Parcelable {
        public static final Creator<QueueItem> CREATOR = new Creator<QueueItem>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new QueueItem[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private Object mItem;

        public final int describeContents() {
            return 0;
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            this(null, mediaDescriptionCompat, j);
        }

        private QueueItem(Object obj, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            } else if (j == -1) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            } else {
                this.mDescription = mediaDescriptionCompat;
                this.mId = j;
                this.mItem = obj;
            }
        }

        private QueueItem(Parcel parcel) {
            this.mDescription = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        public final MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        public final long getQueueId() {
            return this.mId;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            this.mDescription.writeToParcel(parcel, i);
            parcel.writeLong(this.mId);
        }

        public final Object getQueueItem() {
            if (this.mItem != null || VERSION.SDK_INT < 21) {
                return this.mItem;
            }
            this.mItem = QueueItem.a(this.mDescription.getMediaDescription(), this.mId);
            return this.mItem;
        }

        public static QueueItem obtain(Object obj) {
            return new QueueItem(obj, MediaDescriptionCompat.fromMediaDescription(QueueItem.a(obj)), QueueItem.b(obj));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MediaSession.QueueItem {Description=");
            sb.append(this.mDescription);
            sb.append(", Id=");
            sb.append(this.mId);
            sb.append(" }");
            return sb.toString();
        }
    }

    static final class ResultReceiverWrapper implements Parcelable {
        public static final Creator<ResultReceiverWrapper> CREATOR = new Creator<ResultReceiverWrapper>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new ResultReceiverWrapper[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }
        };
        /* access modifiers changed from: private */
        public ResultReceiver a;

        public final int describeContents() {
            return 0;
        }

        public ResultReceiverWrapper(ResultReceiver resultReceiver) {
            this.a = resultReceiver;
        }

        ResultReceiverWrapper(Parcel parcel) {
            this.a = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public final void writeToParcel(Parcel parcel, int i) {
            this.a.writeToParcel(parcel, i);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    public static final class Token implements Parcelable {
        public static final Creator<Token> CREATOR = new Creator<Token>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new Token[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                Object obj;
                if (VERSION.SDK_INT >= 21) {
                    obj = parcel.readParcelable(null);
                } else {
                    obj = parcel.readStrongBinder();
                }
                return new Token(obj);
            }
        };
        private final Object mInner;

        public final int describeContents() {
            return 0;
        }

        Token(Object obj) {
            this.mInner = obj;
        }

        public static Token fromToken(Object obj) {
            if (obj == null || VERSION.SDK_INT < 21) {
                return null;
            }
            return new Token(MediaSessionCompatApi21.b(obj));
        }

        public final void writeToParcel(Parcel parcel, int i) {
            if (VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable) this.mInner, i);
            } else {
                parcel.writeStrongBinder((IBinder) this.mInner);
            }
        }

        public final Object getToken() {
            return this.mInner;
        }
    }

    public MediaSessionCompat(Context context, String str) {
        this(context, str, null, null);
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
        this.mActiveListeners = new ArrayList<>();
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("tag must not be null or empty");
        } else {
            if (componentName == null) {
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.setPackage(context.getPackageName());
                List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
                if (queryBroadcastReceivers.size() == 1) {
                    ResolveInfo resolveInfo = queryBroadcastReceivers.get(0);
                    componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                } else {
                    queryBroadcastReceivers.size();
                }
            }
            if (componentName != null && pendingIntent == null) {
                Intent intent2 = new Intent("android.intent.action.MEDIA_BUTTON");
                intent2.setComponent(componentName);
                pendingIntent = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            if (VERSION.SDK_INT >= 21) {
                this.mImpl = new MediaSessionImplApi21(context, str);
                this.mImpl.b(pendingIntent);
            } else {
                this.mImpl = new MediaSessionImplBase(context, str, componentName, pendingIntent);
            }
            this.mController = new MediaControllerCompat(context, this);
        }
    }

    private MediaSessionCompat(Context context, MediaSessionImpl mediaSessionImpl) {
        this.mActiveListeners = new ArrayList<>();
        this.mImpl = mediaSessionImpl;
        this.mController = new MediaControllerCompat(context, this);
    }

    public void setCallback(Callback callback) {
        setCallback(callback, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        MediaSessionImpl mediaSessionImpl = this.mImpl;
        if (handler == null) {
            handler = new Handler();
        }
        mediaSessionImpl.a(callback, handler);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        this.mImpl.a(pendingIntent);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.mImpl.b(pendingIntent);
    }

    public void setFlags(int i) {
        this.mImpl.a(i);
    }

    public void setPlaybackToLocal(int i) {
        this.mImpl.b(i);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat == null) {
            throw new IllegalArgumentException("volumeProvider may not be null!");
        }
        this.mImpl.a(volumeProviderCompat);
    }

    public void setActive(boolean z) {
        this.mImpl.a(z);
        Iterator<OnActiveChangeListener> it = this.mActiveListeners.iterator();
        while (it.hasNext()) {
            it.next().onActiveChanged();
        }
    }

    public boolean isActive() {
        return this.mImpl.a();
    }

    public void sendSessionEvent(String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("event cannot be null or empty");
        }
        this.mImpl.a(str, bundle);
    }

    public void release() {
        this.mImpl.b();
    }

    public Token getSessionToken() {
        return this.mImpl.c();
    }

    public MediaControllerCompat getController() {
        return this.mController;
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.mImpl.a(playbackStateCompat);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.mImpl.a(mediaMetadataCompat);
    }

    public void setQueue(List<QueueItem> list) {
        this.mImpl.a(list);
    }

    public void setQueueTitle(CharSequence charSequence) {
        this.mImpl.a(charSequence);
    }

    public void setRatingType(int i) {
        this.mImpl.c(i);
    }

    public void setExtras(Bundle bundle) {
        this.mImpl.a(bundle);
    }

    public Object getMediaSession() {
        return this.mImpl.d();
    }

    public Object getRemoteControlClient() {
        return this.mImpl.e();
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.add(onActiveChangeListener);
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.remove(onActiveChangeListener);
    }

    public static MediaSessionCompat obtain(Context context, Object obj) {
        return new MediaSessionCompat(context, (MediaSessionImpl) new MediaSessionImplApi21(obj));
    }
}
