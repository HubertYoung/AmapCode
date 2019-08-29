package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback.Stub;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.media.session.PlaybackStateCompat.CustomAction;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public final class MediaControllerCompat {
    private static final String TAG = "MediaControllerCompat";
    private final MediaControllerImpl mImpl;
    private final Token mToken;

    public static abstract class Callback implements DeathRecipient {
        /* access modifiers changed from: private */
        public final Object mCallbackObj;
        /* access modifiers changed from: private */
        public MessageHandler mHandler;
        /* access modifiers changed from: private */
        public boolean mRegistered = false;

        class MessageHandler extends Handler {
            public MessageHandler(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message message) {
                if (Callback.this.mRegistered) {
                    switch (message.what) {
                        case 1:
                            Callback.this.onSessionEvent((String) message.obj, message.getData());
                            return;
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) message.obj);
                            return;
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) message.obj);
                            return;
                        case 4:
                            Callback.this.onAudioInfoChanged((PlaybackInfo) message.obj);
                            return;
                        case 5:
                            Callback.this.onQueueChanged((List) message.obj);
                            return;
                        case 6:
                            Callback.this.onQueueTitleChanged((CharSequence) message.obj);
                            return;
                        case 7:
                            Callback.this.onExtrasChanged((Bundle) message.obj);
                            return;
                        case 8:
                            Callback.this.onSessionDestroyed();
                            break;
                    }
                }
            }

            public final void a(int i, Object obj) {
                obtainMessage(i, obj).sendToTarget();
            }
        }

        class StubApi21 implements android.support.v4.media.session.MediaControllerCompatApi21.Callback {
            private StubApi21() {
            }

            /* synthetic */ StubApi21(Callback callback, byte b) {
                this();
            }

            public void onSessionDestroyed() {
                Callback.this.onSessionDestroyed();
            }

            public void onSessionEvent(String str, Bundle bundle) {
                Callback.this.onSessionEvent(str, bundle);
            }

            public void onPlaybackStateChanged(Object obj) {
                Callback.this.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(obj));
            }

            public void onMetadataChanged(Object obj) {
                Callback.this.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(obj));
            }
        }

        class StubCompat extends Stub {
            private StubCompat() {
            }

            public void onEvent(String str, Bundle bundle) throws RemoteException {
                Callback.this.mHandler.a(1, str);
            }

            public void onSessionDestroyed() throws RemoteException {
                Callback.this.mHandler.a(8, null);
            }

            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                Callback.this.mHandler.a(2, playbackStateCompat);
            }

            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                Callback.this.mHandler.a(3, mediaMetadataCompat);
            }

            public void onQueueChanged(List<QueueItem> list) throws RemoteException {
                Callback.this.mHandler.a(5, list);
            }

            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                Callback.this.mHandler.a(6, charSequence);
            }

            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Callback.this.mHandler.a(7, bundle);
            }

            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                Callback.this.mHandler.a(4, parcelableVolumeInfo != null ? new PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume) : null);
            }
        }

        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        public void onExtrasChanged(Bundle bundle) {
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onQueueChanged(List<QueueItem> list) {
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String str, Bundle bundle) {
        }

        public Callback() {
            if (VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaControllerCompatApi21.a((android.support.v4.media.session.MediaControllerCompatApi21.Callback) new StubApi21(this, 0));
            } else {
                this.mCallbackObj = new StubCompat();
            }
        }

        public void binderDied() {
            onSessionDestroyed();
        }

        /* access modifiers changed from: private */
        public void setHandler(Handler handler) {
            this.mHandler = new MessageHandler(handler.getLooper());
        }
    }

    interface MediaControllerImpl {
        TransportControls a();

        void a(int i, int i2);

        void a(Callback callback);

        void a(Callback callback, Handler handler);

        void a(String str, Bundle bundle, ResultReceiver resultReceiver);

        boolean a(KeyEvent keyEvent);

        PlaybackStateCompat b();

        void b(int i, int i2);

        MediaMetadataCompat c();

        List<QueueItem> d();

        CharSequence e();

        Bundle f();

        int g();

        long h();

        PlaybackInfo i();

        PendingIntent j();

        String k();

        Object l();
    }

    static class MediaControllerImplApi21 implements MediaControllerImpl {
        protected final Object a;

        public MediaControllerImplApi21(Context context, MediaSessionCompat mediaSessionCompat) {
            this.a = MediaControllerCompatApi21.a(context, mediaSessionCompat.getSessionToken().getToken());
        }

        public MediaControllerImplApi21(Context context, Token token) throws RemoteException {
            this.a = MediaControllerCompatApi21.a(context, token.getToken());
            if (this.a == null) {
                throw new RemoteException();
            }
        }

        public final void a(Callback callback, Handler handler) {
            MediaControllerCompatApi21.a(this.a, callback.mCallbackObj, handler);
        }

        public final void a(Callback callback) {
            MediaControllerCompatApi21.a(this.a, callback.mCallbackObj);
        }

        public final boolean a(KeyEvent keyEvent) {
            return MediaControllerCompatApi21.a(this.a, keyEvent);
        }

        public TransportControls a() {
            Object a2 = MediaControllerCompatApi21.a(this.a);
            if (a2 != null) {
                return new TransportControlsApi21(a2);
            }
            return null;
        }

        public final PlaybackStateCompat b() {
            Object b = MediaControllerCompatApi21.b(this.a);
            if (b != null) {
                return PlaybackStateCompat.fromPlaybackState(b);
            }
            return null;
        }

        public final MediaMetadataCompat c() {
            Object c = MediaControllerCompatApi21.c(this.a);
            if (c != null) {
                return MediaMetadataCompat.fromMediaMetadata(c);
            }
            return null;
        }

        public final List<QueueItem> d() {
            List<Object> d = MediaControllerCompatApi21.d(this.a);
            if (d == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obtain : d) {
                arrayList.add(QueueItem.obtain(obtain));
            }
            return arrayList;
        }

        public final CharSequence e() {
            return MediaControllerCompatApi21.e(this.a);
        }

        public final Bundle f() {
            return MediaControllerCompatApi21.f(this.a);
        }

        public final int g() {
            return MediaControllerCompatApi21.g(this.a);
        }

        public final long h() {
            return MediaControllerCompatApi21.h(this.a);
        }

        public final PlaybackInfo i() {
            Object i = MediaControllerCompatApi21.i(this.a);
            if (i == null) {
                return null;
            }
            PlaybackInfo playbackInfo = new PlaybackInfo(android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(i), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(i), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(i), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(i), android.support.v4.media.session.MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(i));
            return playbackInfo;
        }

        public final PendingIntent j() {
            return MediaControllerCompatApi21.j(this.a);
        }

        public final void a(int i, int i2) {
            MediaControllerCompatApi21.a(this.a, i, i2);
        }

        public final void b(int i, int i2) {
            MediaControllerCompatApi21.b(this.a, i, i2);
        }

        public final void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            MediaControllerCompatApi21.a(this.a, str, bundle, resultReceiver);
        }

        public final String k() {
            return MediaControllerCompatApi21.k(this.a);
        }

        public final Object l() {
            return this.a;
        }
    }

    static class MediaControllerImplApi23 extends MediaControllerImplApi21 {
        public MediaControllerImplApi23(Context context, MediaSessionCompat mediaSessionCompat) {
            super(context, mediaSessionCompat);
        }

        public final TransportControls a() {
            Object a = MediaControllerCompatApi21.a(this.a);
            if (a != null) {
                return new TransportControlsApi23(a);
            }
            return null;
        }
    }

    static class MediaControllerImplBase implements MediaControllerImpl {
        private Token a;
        private IMediaSession b;
        private TransportControls c;

        public final Object l() {
            return null;
        }

        public MediaControllerImplBase(Token token) {
            this.a = token;
            this.b = IMediaSession.Stub.asInterface((IBinder) token.getToken());
        }

        public final void a(Callback callback, Handler handler) {
            if (callback == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.b.asBinder().linkToDeath(callback, 0);
                this.b.registerCallbackListener((IMediaControllerCallback) callback.mCallbackObj);
                callback.setHandler(handler);
                callback.mRegistered = true;
            } catch (RemoteException e) {
                new StringBuilder("Dead object in registerCallback. ").append(e);
                callback.onSessionDestroyed();
            }
        }

        public final void a(Callback callback) {
            if (callback == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.b.unregisterCallbackListener((IMediaControllerCallback) callback.mCallbackObj);
                this.b.asBinder().unlinkToDeath(callback, 0);
                callback.mRegistered = false;
            } catch (RemoteException e) {
                new StringBuilder("Dead object in unregisterCallback. ").append(e);
            }
        }

        public final boolean a(KeyEvent keyEvent) {
            if (keyEvent == null) {
                throw new IllegalArgumentException("event may not be null.");
            }
            try {
                this.b.sendMediaButton(keyEvent);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in dispatchMediaButtonEvent. ").append(e);
            }
            return false;
        }

        public final TransportControls a() {
            if (this.c == null) {
                this.c = new TransportControlsBase(this.b);
            }
            return this.c;
        }

        public final PlaybackStateCompat b() {
            try {
                return this.b.getPlaybackState();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getPlaybackState. ").append(e);
                return null;
            }
        }

        public final MediaMetadataCompat c() {
            try {
                return this.b.getMetadata();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getMetadata. ").append(e);
                return null;
            }
        }

        public final List<QueueItem> d() {
            try {
                return this.b.getQueue();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getQueue. ").append(e);
                return null;
            }
        }

        public final CharSequence e() {
            try {
                return this.b.getQueueTitle();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getQueueTitle. ").append(e);
                return null;
            }
        }

        public final Bundle f() {
            try {
                return this.b.getExtras();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getExtras. ").append(e);
                return null;
            }
        }

        public final int g() {
            try {
                return this.b.getRatingType();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getRatingType. ").append(e);
                return 0;
            }
        }

        public final long h() {
            try {
                return this.b.getFlags();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getFlags. ").append(e);
                return 0;
            }
        }

        public final PlaybackInfo i() {
            try {
                ParcelableVolumeInfo volumeAttributes = this.b.getVolumeAttributes();
                PlaybackInfo playbackInfo = new PlaybackInfo(volumeAttributes.volumeType, volumeAttributes.audioStream, volumeAttributes.controlType, volumeAttributes.maxVolume, volumeAttributes.currentVolume);
                return playbackInfo;
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getPlaybackInfo. ").append(e);
                return null;
            }
        }

        public final PendingIntent j() {
            try {
                return this.b.getLaunchPendingIntent();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getSessionActivity. ").append(e);
                return null;
            }
        }

        public final void a(int i, int i2) {
            try {
                this.b.setVolumeTo(i, i2, null);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in setVolumeTo. ").append(e);
            }
        }

        public final void b(int i, int i2) {
            try {
                this.b.adjustVolume(i, i2, null);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in adjustVolume. ").append(e);
            }
        }

        public final void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            try {
                this.b.sendCommand(str, bundle, new ResultReceiverWrapper(resultReceiver));
            } catch (RemoteException e) {
                new StringBuilder("Dead object in sendCommand. ").append(e);
            }
        }

        public final String k() {
            try {
                return this.b.getPackageName();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in getPackageName. ").append(e);
                return null;
            }
        }
    }

    public static final class PlaybackInfo {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final int mAudioStream;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mPlaybackType;
        private final int mVolumeControl;

        PlaybackInfo(int i, int i2, int i3, int i4, int i5) {
            this.mPlaybackType = i;
            this.mAudioStream = i2;
            this.mVolumeControl = i3;
            this.mMaxVolume = i4;
            this.mCurrentVolume = i5;
        }

        public final int getPlaybackType() {
            return this.mPlaybackType;
        }

        public final int getAudioStream() {
            return this.mAudioStream;
        }

        public final int getVolumeControl() {
            return this.mVolumeControl;
        }

        public final int getMaxVolume() {
            return this.mMaxVolume;
        }

        public final int getCurrentVolume() {
            return this.mCurrentVolume;
        }
    }

    public static abstract class TransportControls {
        public abstract void fastForward();

        public abstract void pause();

        public abstract void play();

        public abstract void playFromMediaId(String str, Bundle bundle);

        public abstract void playFromSearch(String str, Bundle bundle);

        public abstract void playFromUri(Uri uri, Bundle bundle);

        public abstract void rewind();

        public abstract void seekTo(long j);

        public abstract void sendCustomAction(CustomAction customAction, Bundle bundle);

        public abstract void sendCustomAction(String str, Bundle bundle);

        public abstract void setRating(RatingCompat ratingCompat);

        public abstract void skipToNext();

        public abstract void skipToPrevious();

        public abstract void skipToQueueItem(long j);

        public abstract void stop();

        TransportControls() {
        }
    }

    static class TransportControlsApi21 extends TransportControls {
        protected final Object a;

        public void playFromUri(Uri uri, Bundle bundle) {
        }

        public TransportControlsApi21(Object obj) {
            this.a = obj;
        }

        public void play() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.play(this.a);
        }

        public void pause() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.pause(this.a);
        }

        public void stop() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.stop(this.a);
        }

        public void seekTo(long j) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.seekTo(this.a, j);
        }

        public void fastForward() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.fastForward(this.a);
        }

        public void rewind() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.rewind(this.a);
        }

        public void skipToNext() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToNext(this.a);
        }

        public void skipToPrevious() {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToPrevious(this.a);
        }

        public void setRating(RatingCompat ratingCompat) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.setRating(this.a, ratingCompat != null ? ratingCompat.getRating() : null);
        }

        public void playFromMediaId(String str, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.playFromMediaId(this.a, str, bundle);
        }

        public void playFromSearch(String str, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.playFromSearch(this.a, str, bundle);
        }

        public void skipToQueueItem(long j) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.a, j);
        }

        public void sendCustomAction(CustomAction customAction, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.a, customAction.getAction(), bundle);
        }

        public void sendCustomAction(String str, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.sendCustomAction(this.a, str, bundle);
        }
    }

    static class TransportControlsApi23 extends TransportControlsApi21 {
        public TransportControlsApi23(Object obj) {
            super(obj);
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            android.support.v4.media.session.MediaControllerCompatApi23.TransportControls.playFromUri(this.a, uri, bundle);
        }
    }

    static class TransportControlsBase extends TransportControls {
        private IMediaSession a;

        public TransportControlsBase(IMediaSession iMediaSession) {
            this.a = iMediaSession;
        }

        public void play() {
            try {
                this.a.play();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in play. ").append(e);
            }
        }

        public void playFromMediaId(String str, Bundle bundle) {
            try {
                this.a.playFromMediaId(str, bundle);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in playFromMediaId. ").append(e);
            }
        }

        public void playFromSearch(String str, Bundle bundle) {
            try {
                this.a.playFromSearch(str, bundle);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in playFromSearch. ").append(e);
            }
        }

        public void playFromUri(Uri uri, Bundle bundle) {
            try {
                this.a.playFromUri(uri, bundle);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in playFromUri. ").append(e);
            }
        }

        public void skipToQueueItem(long j) {
            try {
                this.a.skipToQueueItem(j);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in skipToQueueItem. ").append(e);
            }
        }

        public void pause() {
            try {
                this.a.pause();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in pause. ").append(e);
            }
        }

        public void stop() {
            try {
                this.a.stop();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in stop. ").append(e);
            }
        }

        public void seekTo(long j) {
            try {
                this.a.seekTo(j);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in seekTo. ").append(e);
            }
        }

        public void fastForward() {
            try {
                this.a.fastForward();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in fastForward. ").append(e);
            }
        }

        public void skipToNext() {
            try {
                this.a.next();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in skipToNext. ").append(e);
            }
        }

        public void rewind() {
            try {
                this.a.rewind();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in rewind. ").append(e);
            }
        }

        public void skipToPrevious() {
            try {
                this.a.previous();
            } catch (RemoteException e) {
                new StringBuilder("Dead object in skipToPrevious. ").append(e);
            }
        }

        public void setRating(RatingCompat ratingCompat) {
            try {
                this.a.rate(ratingCompat);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in setRating. ").append(e);
            }
        }

        public void sendCustomAction(CustomAction customAction, Bundle bundle) {
            sendCustomAction(customAction.getAction(), bundle);
        }

        public void sendCustomAction(String str, Bundle bundle) {
            try {
                this.a.sendCustomAction(str, bundle);
            } catch (RemoteException e) {
                new StringBuilder("Dead object in sendCustomAction. ").append(e);
            }
        }
    }

    public MediaControllerCompat(Context context, MediaSessionCompat mediaSessionCompat) {
        if (mediaSessionCompat == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        this.mToken = mediaSessionCompat.getSessionToken();
        if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaControllerImplApi23(context, mediaSessionCompat);
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerImplApi21(context, mediaSessionCompat);
        } else {
            this.mImpl = new MediaControllerImplBase(this.mToken);
        }
    }

    public MediaControllerCompat(Context context, Token token) throws RemoteException {
        if (token == null) {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
        this.mToken = token;
        if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerImplApi21(context, token);
        } else {
            this.mImpl = new MediaControllerImplBase(this.mToken);
        }
    }

    public final TransportControls getTransportControls() {
        return this.mImpl.a();
    }

    public final boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return this.mImpl.a(keyEvent);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public final PlaybackStateCompat getPlaybackState() {
        return this.mImpl.b();
    }

    public final MediaMetadataCompat getMetadata() {
        return this.mImpl.c();
    }

    public final List<QueueItem> getQueue() {
        return this.mImpl.d();
    }

    public final CharSequence getQueueTitle() {
        return this.mImpl.e();
    }

    public final Bundle getExtras() {
        return this.mImpl.f();
    }

    public final int getRatingType() {
        return this.mImpl.g();
    }

    public final long getFlags() {
        return this.mImpl.h();
    }

    public final PlaybackInfo getPlaybackInfo() {
        return this.mImpl.i();
    }

    public final PendingIntent getSessionActivity() {
        return this.mImpl.j();
    }

    public final Token getSessionToken() {
        return this.mToken;
    }

    public final void setVolumeTo(int i, int i2) {
        this.mImpl.a(i, i2);
    }

    public final void adjustVolume(int i, int i2) {
        this.mImpl.b(i, i2);
    }

    public final void registerCallback(Callback callback) {
        registerCallback(callback, null);
    }

    public final void registerCallback(Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        if (handler == null) {
            handler = new Handler();
        }
        this.mImpl.a(callback, handler);
    }

    public final void unregisterCallback(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        this.mImpl.a(callback);
    }

    public final void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command cannot be null or empty");
        }
        this.mImpl.a(str, bundle, resultReceiver);
    }

    public final String getPackageName() {
        return this.mImpl.k();
    }

    public final Object getMediaController() {
        return this.mImpl.l();
    }
}
