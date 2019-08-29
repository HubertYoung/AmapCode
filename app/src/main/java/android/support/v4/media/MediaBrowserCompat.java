package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.IMediaBrowserServiceCompat.Stub;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public final class MediaBrowserCompat {
    private final MediaBrowserImplBase mImpl;

    public static class ConnectionCallback {
        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }
    }

    public static abstract class ItemCallback {
        public void onError(@NonNull String str) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }
    }

    static class MediaBrowserImplBase {
        /* access modifiers changed from: 0000 */
        public final Context a;
        /* access modifiers changed from: 0000 */
        public final ComponentName b;
        final Handler c = new Handler();
        /* access modifiers changed from: 0000 */
        public final ArrayMap<String, Subscription> d = new ArrayMap<>();
        /* access modifiers changed from: 0000 */
        public int e = 0;
        /* access modifiers changed from: 0000 */
        public MediaServiceConnection f;
        /* access modifiers changed from: 0000 */
        public IMediaBrowserServiceCompat g;
        /* access modifiers changed from: 0000 */
        public IMediaBrowserServiceCompatCallbacks h;
        /* access modifiers changed from: 0000 */
        public String i;
        /* access modifiers changed from: 0000 */
        public Token j;
        /* access modifiers changed from: 0000 */
        public Bundle k;
        /* access modifiers changed from: private */
        public final ConnectionCallback l;
        /* access modifiers changed from: private */
        public final Bundle m;

        class MediaServiceConnection implements ServiceConnection {
            private MediaServiceConnection() {
            }

            /* synthetic */ MediaServiceConnection(MediaBrowserImplBase mediaBrowserImplBase, byte b) {
                this();
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (a("onServiceConnected")) {
                    MediaBrowserImplBase.this.g = Stub.asInterface(iBinder);
                    MediaBrowserImplBase.this.h = MediaBrowserImplBase.i(MediaBrowserImplBase.this);
                    MediaBrowserImplBase.this.e = 1;
                    try {
                        MediaBrowserImplBase.this.g.connect(MediaBrowserImplBase.this.a.getPackageName(), MediaBrowserImplBase.this.m, MediaBrowserImplBase.this.h);
                    } catch (RemoteException unused) {
                        new StringBuilder("RemoteException during connect for ").append(MediaBrowserImplBase.this.b);
                    }
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                if (a("onServiceDisconnected")) {
                    MediaBrowserImplBase.this.g = null;
                    MediaBrowserImplBase.this.h = null;
                    MediaBrowserImplBase.this.e = 3;
                    MediaBrowserImplBase.this.l.onConnectionSuspended();
                }
            }

            private boolean a(String str) {
                if (MediaBrowserImplBase.this.f == this) {
                    return true;
                }
                if (MediaBrowserImplBase.this.e != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(" for ");
                    sb.append(MediaBrowserImplBase.this.b);
                    sb.append(" with mServiceConnection=");
                    sb.append(MediaBrowserImplBase.this.f);
                    sb.append(" this=");
                    sb.append(this);
                }
                return false;
            }
        }

        static class ServiceCallbacks extends IMediaBrowserServiceCompatCallbacks.Stub {
            private WeakReference<MediaBrowserImplBase> mMediaBrowser;

            public ServiceCallbacks(MediaBrowserImplBase mediaBrowserImplBase) {
                this.mMediaBrowser = new WeakReference<>(mediaBrowserImplBase);
            }

            public void onConnect(String str, Token token, Bundle bundle) {
                MediaBrowserImplBase mediaBrowserImplBase = (MediaBrowserImplBase) this.mMediaBrowser.get();
                if (mediaBrowserImplBase != null) {
                    MediaBrowserImplBase.a(mediaBrowserImplBase, this, str, token, bundle);
                }
            }

            public void onConnectFailed() {
                MediaBrowserImplBase mediaBrowserImplBase = (MediaBrowserImplBase) this.mMediaBrowser.get();
                if (mediaBrowserImplBase != null) {
                    mediaBrowserImplBase.c.post(new Runnable(this) {
                        public void run() {
                            new StringBuilder("onConnectFailed for ").append(MediaBrowserImplBase.this.b);
                            if (MediaBrowserImplBase.a(MediaBrowserImplBase.this, r3, "onConnectFailed")) {
                                if (MediaBrowserImplBase.this.e != 1) {
                                    StringBuilder sb = new StringBuilder("onConnect from service while mState=");
                                    sb.append(MediaBrowserImplBase.a(MediaBrowserImplBase.this.e));
                                    sb.append("... ignoring");
                                    return;
                                }
                                MediaBrowserImplBase.this.a();
                                MediaBrowserImplBase.this.l.onConnectionFailed();
                            }
                        }
                    });
                }
            }

            public void onLoadChildren(String str, List list) {
                MediaBrowserImplBase mediaBrowserImplBase = (MediaBrowserImplBase) this.mMediaBrowser.get();
                if (mediaBrowserImplBase != null) {
                    mediaBrowserImplBase.c.post(new Runnable(this, list, str) {
                        public void run() {
                            if (MediaBrowserImplBase.a(MediaBrowserImplBase.this, r3, "onLoadChildren")) {
                                List list = r5;
                                if (list == null) {
                                    list = Collections.emptyList();
                                }
                                Subscription subscription = (Subscription) MediaBrowserImplBase.this.d.get(r4);
                                if (subscription != null) {
                                    subscription.b.onChildrenLoaded(r4, list);
                                }
                            }
                        }
                    });
                }
            }
        }

        static class Subscription {
            final String a;
            SubscriptionCallback b;

            Subscription(String str) {
                this.a = str;
            }
        }

        public MediaBrowserImplBase(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            if (context == null) {
                throw new IllegalArgumentException("context must not be null");
            } else if (componentName == null) {
                throw new IllegalArgumentException("service component must not be null");
            } else if (connectionCallback == null) {
                throw new IllegalArgumentException("connection callback must not be null");
            } else {
                this.a = context;
                this.b = componentName;
                this.l = connectionCallback;
                this.m = bundle;
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (this.f != null) {
                this.a.unbindService(this.f);
            }
            this.e = 0;
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = null;
            this.j = null;
        }

        public final boolean b() {
            return this.e == 2;
        }

        /* access modifiers changed from: 0000 */
        public static String a(int i2) {
            switch (i2) {
                case 0:
                    return "CONNECT_STATE_DISCONNECTED";
                case 1:
                    return "CONNECT_STATE_CONNECTING";
                case 2:
                    return "CONNECT_STATE_CONNECTED";
                case 3:
                    return "CONNECT_STATE_SUSPENDED";
                default:
                    return "UNKNOWN/".concat(String.valueOf(i2));
            }
        }

        static /* synthetic */ boolean a(MediaBrowserImplBase mediaBrowserImplBase, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks, String str) {
            if (mediaBrowserImplBase.h == iMediaBrowserServiceCompatCallbacks) {
                return true;
            }
            if (mediaBrowserImplBase.e != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" for ");
                sb.append(mediaBrowserImplBase.b);
                sb.append(" with mServiceConnection=");
                sb.append(mediaBrowserImplBase.h);
                sb.append(" this=");
                sb.append(mediaBrowserImplBase);
            }
            return false;
        }

        static /* synthetic */ ServiceCallbacks i(MediaBrowserImplBase mediaBrowserImplBase) {
            return new ServiceCallbacks(mediaBrowserImplBase);
        }

        static /* synthetic */ void a(MediaBrowserImplBase mediaBrowserImplBase, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks, String str, Token token, Bundle bundle) {
            Handler handler = mediaBrowserImplBase.c;
            final IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks2 = iMediaBrowserServiceCompatCallbacks;
            final String str2 = str;
            final Token token2 = token;
            final Bundle bundle2 = bundle;
            AnonymousClass5 r1 = new Runnable() {
                public void run() {
                    if (MediaBrowserImplBase.a(MediaBrowserImplBase.this, iMediaBrowserServiceCompatCallbacks2, "onConnect")) {
                        if (MediaBrowserImplBase.this.e != 1) {
                            StringBuilder sb = new StringBuilder("onConnect from service while mState=");
                            sb.append(MediaBrowserImplBase.a(MediaBrowserImplBase.this.e));
                            sb.append("... ignoring");
                            return;
                        }
                        MediaBrowserImplBase.this.i = str2;
                        MediaBrowserImplBase.this.j = token2;
                        MediaBrowserImplBase.this.k = bundle2;
                        MediaBrowserImplBase.this.e = 2;
                        MediaBrowserImplBase.this.l.onConnected();
                        for (String addSubscription : MediaBrowserImplBase.this.d.keySet()) {
                            try {
                                MediaBrowserImplBase.this.g.addSubscription(addSubscription, MediaBrowserImplBase.this.h);
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                }
            };
            handler.post(r1);
        }
    }

    public static class MediaItem implements Parcelable {
        public static final Creator<MediaItem> CREATOR = new Creator<MediaItem>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new MediaItem[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final MediaDescriptionCompat mDescription;
        private final int mFlags;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        public int describeContents() {
            return 0;
        }

        public MediaItem(@NonNull MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (TextUtils.isEmpty(mediaDescriptionCompat.getMediaId())) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            } else {
                this.mFlags = i;
                this.mDescription = mediaDescriptionCompat;
            }
        }

        private MediaItem(Parcel parcel) {
            this.mFlags = parcel.readInt();
            this.mDescription = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mFlags);
            this.mDescription.writeToParcel(parcel, i);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("MediaItem{");
            sb.append("mFlags=");
            sb.append(this.mFlags);
            sb.append(", mDescription=");
            sb.append(this.mDescription);
            sb.append('}');
            return sb.toString();
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean isBrowsable() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isPlayable() {
            return (this.mFlags & 2) != 0;
        }

        @NonNull
        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        @NonNull
        public String getMediaId() {
            return this.mDescription.getMediaId();
        }
    }

    public static abstract class SubscriptionCallback {
        public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaItem> list) {
        }

        public void onError(@NonNull String str) {
        }
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        this.mImpl = new MediaBrowserImplBase(context, componentName, connectionCallback, bundle);
    }

    public final void connect() {
        boolean z;
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (mediaBrowserImplBase.e != 0) {
            StringBuilder sb = new StringBuilder("connect() called while not disconnected (state=");
            sb.append(MediaBrowserImplBase.a(mediaBrowserImplBase.e));
            sb.append(")");
            throw new IllegalStateException(sb.toString());
        } else if (mediaBrowserImplBase.g != null) {
            StringBuilder sb2 = new StringBuilder("mServiceBinder should be null. Instead it is ");
            sb2.append(mediaBrowserImplBase.g);
            throw new RuntimeException(sb2.toString());
        } else if (mediaBrowserImplBase.h != null) {
            StringBuilder sb3 = new StringBuilder("mServiceCallbacks should be null. Instead it is ");
            sb3.append(mediaBrowserImplBase.h);
            throw new RuntimeException(sb3.toString());
        } else {
            mediaBrowserImplBase.e = 1;
            Intent intent = new Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE);
            intent.setComponent(mediaBrowserImplBase.b);
            MediaServiceConnection mediaServiceConnection = new MediaServiceConnection(mediaBrowserImplBase, 0);
            mediaBrowserImplBase.f = mediaServiceConnection;
            try {
                z = mediaBrowserImplBase.a.bindService(intent, mediaBrowserImplBase.f, 1);
            } catch (Exception unused) {
                new StringBuilder("Failed binding to service ").append(mediaBrowserImplBase.b);
                z = false;
            }
            if (!z) {
                mediaBrowserImplBase.c.post(new Runnable(mediaServiceConnection) {
                    final /* synthetic */ ServiceConnection a;

                    {
                        this.a = r2;
                    }

                    public void run() {
                        if (this.a == MediaBrowserImplBase.this.f) {
                            MediaBrowserImplBase.this.a();
                            MediaBrowserImplBase.this.l.onConnectionFailed();
                        }
                    }
                });
            }
        }
    }

    public final void disconnect() {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (mediaBrowserImplBase.h != null) {
            try {
                mediaBrowserImplBase.g.disconnect(mediaBrowserImplBase.h);
            } catch (RemoteException unused) {
                new StringBuilder("RemoteException during connect for ").append(mediaBrowserImplBase.b);
            }
        }
        mediaBrowserImplBase.a();
    }

    public final boolean isConnected() {
        return this.mImpl.b();
    }

    @NonNull
    public final ComponentName getServiceComponent() {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (mediaBrowserImplBase.b()) {
            return mediaBrowserImplBase.b;
        }
        StringBuilder sb = new StringBuilder("getServiceComponent() called while not connected (state=");
        sb.append(mediaBrowserImplBase.e);
        sb.append(")");
        throw new IllegalStateException(sb.toString());
    }

    @NonNull
    public final String getRoot() {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (mediaBrowserImplBase.b()) {
            return mediaBrowserImplBase.i;
        }
        StringBuilder sb = new StringBuilder("getSessionToken() called while not connected(state=");
        sb.append(MediaBrowserImplBase.a(mediaBrowserImplBase.e));
        sb.append(")");
        throw new IllegalStateException(sb.toString());
    }

    @Nullable
    public final Bundle getExtras() {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (mediaBrowserImplBase.b()) {
            return mediaBrowserImplBase.k;
        }
        StringBuilder sb = new StringBuilder("getExtras() called while not connected (state=");
        sb.append(MediaBrowserImplBase.a(mediaBrowserImplBase.e));
        sb.append(")");
        throw new IllegalStateException(sb.toString());
    }

    @NonNull
    public final Token getSessionToken() {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (mediaBrowserImplBase.b()) {
            return mediaBrowserImplBase.j;
        }
        StringBuilder sb = new StringBuilder("getSessionToken() called while not connected(state=");
        sb.append(mediaBrowserImplBase.e);
        sb.append(")");
        throw new IllegalStateException(sb.toString());
    }

    public final void subscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (str == null) {
            throw new IllegalArgumentException("parentId is null");
        } else if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback is null");
        } else {
            Subscription subscription = (Subscription) mediaBrowserImplBase.d.get(str);
            if (subscription == null) {
                subscription = new Subscription(str);
                mediaBrowserImplBase.d.put(str, subscription);
            }
            subscription.b = subscriptionCallback;
            if (mediaBrowserImplBase.e == 2) {
                try {
                    mediaBrowserImplBase.g.addSubscription(str, mediaBrowserImplBase.h);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void unsubscribe(@NonNull String str) {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty.");
        }
        Subscription subscription = (Subscription) mediaBrowserImplBase.d.remove(str);
        if (mediaBrowserImplBase.e == 2 && subscription != null) {
            try {
                mediaBrowserImplBase.g.removeSubscription(str, mediaBrowserImplBase.h);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
        MediaBrowserImplBase mediaBrowserImplBase = this.mImpl;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("mediaId is empty.");
        } else if (itemCallback == null) {
            throw new IllegalArgumentException("cb is null.");
        } else if (mediaBrowserImplBase.e != 2) {
            mediaBrowserImplBase.c.post(new Runnable(itemCallback, str) {
                final /* synthetic */ ItemCallback a;
                final /* synthetic */ String b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public void run() {
                    this.a.onError(this.b);
                }
            });
        } else {
            try {
                mediaBrowserImplBase.g.getMediaItem(str, new ResultReceiver(mediaBrowserImplBase.c, itemCallback, str) {
                    final /* synthetic */ ItemCallback a;
                    final /* synthetic */ String b;

                    {
                        this.a = r3;
                        this.b = r4;
                    }

                    public void onReceiveResult(int i, Bundle bundle) {
                        if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                            this.a.onError(this.b);
                            return;
                        }
                        Parcelable parcelable = bundle.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
                        if (!(parcelable instanceof MediaItem)) {
                            this.a.onError(this.b);
                        } else {
                            this.a.onItemLoaded((MediaItem) parcelable);
                        }
                    }
                });
            } catch (RemoteException unused) {
                mediaBrowserImplBase.c.post(new Runnable(itemCallback, str) {
                    final /* synthetic */ ItemCallback a;
                    final /* synthetic */ String b;

                    {
                        this.a = r2;
                        this.b = r3;
                    }

                    public void run() {
                        this.a.onError(this.b);
                    }
                });
            }
        }
    }
}
