package android.support.v4.media;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.IMediaBrowserServiceCompat.Stub;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

public abstract class MediaBrowserServiceCompat extends Service {
    private static final boolean DBG = false;
    public static final String KEY_MEDIA_ITEM = "media_item";
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserServiceCompat";
    private static final String TAG = "MediaBrowserServiceCompat";
    private ServiceBinder mBinder;
    /* access modifiers changed from: private */
    public final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap<>();
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler();
    Token mSession;

    public static final class BrowserRoot {
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String str, @Nullable Bundle bundle) {
            if (str == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.mRootId = str;
            this.mExtras = bundle;
        }

        public final String getRootId() {
            return this.mRootId;
        }

        public final Bundle getExtras() {
            return this.mExtras;
        }
    }

    class ConnectionRecord {
        String a;
        Bundle b;
        IMediaBrowserServiceCompatCallbacks c;
        BrowserRoot d;
        HashSet<String> e;

        private ConnectionRecord() {
            this.e = new HashSet<>();
        }

        /* synthetic */ ConnectionRecord(MediaBrowserServiceCompat mediaBrowserServiceCompat, byte b2) {
            this();
        }
    }

    public class Result<T> {
        private Object mDebug;
        private boolean mDetachCalled;
        private boolean mSendResultCalled;

        /* access modifiers changed from: 0000 */
        public void onResultSent(T t) {
        }

        Result(Object obj) {
            this.mDebug = obj;
        }

        public void sendResult(T t) {
            if (this.mSendResultCalled) {
                StringBuilder sb = new StringBuilder("sendResult() called twice for: ");
                sb.append(this.mDebug);
                throw new IllegalStateException(sb.toString());
            }
            this.mSendResultCalled = true;
            onResultSent(t);
        }

        public void detach() {
            if (this.mDetachCalled) {
                StringBuilder sb = new StringBuilder("detach() called when detach() had already been called for: ");
                sb.append(this.mDebug);
                throw new IllegalStateException(sb.toString());
            } else if (this.mSendResultCalled) {
                StringBuilder sb2 = new StringBuilder("detach() called when sendResult() had already been called for: ");
                sb2.append(this.mDebug);
                throw new IllegalStateException(sb2.toString());
            } else {
                this.mDetachCalled = true;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled;
        }
    }

    class ServiceBinder extends Stub {
        private ServiceBinder() {
        }

        public void connect(String str, Bundle bundle, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) {
            final int callingUid = Binder.getCallingUid();
            if (!MediaBrowserServiceCompat.this.isValidPackage(str, callingUid)) {
                StringBuilder sb = new StringBuilder("Package/uid mismatch: uid=");
                sb.append(callingUid);
                sb.append(" package=");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
            Handler access$300 = MediaBrowserServiceCompat.this.mHandler;
            final IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks2 = iMediaBrowserServiceCompatCallbacks;
            final String str2 = str;
            final Bundle bundle2 = bundle;
            AnonymousClass1 r0 = new Runnable() {
                public void run() {
                    IBinder asBinder = iMediaBrowserServiceCompatCallbacks2.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(asBinder);
                    ConnectionRecord connectionRecord = new ConnectionRecord(MediaBrowserServiceCompat.this, 0);
                    connectionRecord.a = str2;
                    connectionRecord.b = bundle2;
                    connectionRecord.c = iMediaBrowserServiceCompatCallbacks2;
                    connectionRecord.d = MediaBrowserServiceCompat.this.onGetRoot(str2, callingUid, bundle2);
                    if (connectionRecord.d == null) {
                        StringBuilder sb = new StringBuilder("No root for client ");
                        sb.append(str2);
                        sb.append(" from service ");
                        sb.append(getClass().getName());
                        try {
                            iMediaBrowserServiceCompatCallbacks2.onConnectFailed();
                        } catch (RemoteException unused) {
                            new StringBuilder("Calling onConnectFailed() failed. Ignoring. pkg=").append(str2);
                        }
                    } else {
                        try {
                            MediaBrowserServiceCompat.this.mConnections.put(asBinder, connectionRecord);
                            if (MediaBrowserServiceCompat.this.mSession != null) {
                                iMediaBrowserServiceCompatCallbacks2.onConnect(connectionRecord.d.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.d.getExtras());
                            }
                        } catch (RemoteException unused2) {
                            new StringBuilder("Calling onConnect() failed. Dropping client. pkg=").append(str2);
                            MediaBrowserServiceCompat.this.mConnections.remove(asBinder);
                        }
                    }
                }
            };
            access$300.post(r0);
        }

        public void disconnect(final IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    MediaBrowserServiceCompat.this.mConnections.remove(iMediaBrowserServiceCompatCallbacks.asBinder());
                }
            });
        }

        public void addSubscription(final String str, final IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(iMediaBrowserServiceCompatCallbacks.asBinder());
                    if (connectionRecord == null) {
                        new StringBuilder("addSubscription for callback that isn't registered id=").append(str);
                    } else {
                        MediaBrowserServiceCompat.this.addSubscription(str, connectionRecord);
                    }
                }
            });
        }

        public void removeSubscription(final String str, final IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                public void run() {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(iMediaBrowserServiceCompatCallbacks.asBinder());
                    if (connectionRecord == null) {
                        new StringBuilder("removeSubscription for callback that isn't registered id=").append(str);
                        return;
                    }
                    if (!connectionRecord.e.remove(str)) {
                        StringBuilder sb = new StringBuilder("removeSubscription called for ");
                        sb.append(str);
                        sb.append(" which is not subscribed");
                    }
                }
            });
        }

        public void getMediaItem(final String str, final ResultReceiver resultReceiver) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
                    public void run() {
                        MediaBrowserServiceCompat.this.performLoadItem(str, resultReceiver);
                    }
                });
            }
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaItem>> result);

    public void onCreate() {
        super.onCreate();
        this.mBinder = new ServiceBinder();
    }

    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    public void onLoadItem(String str, Result<MediaItem> result) {
        result.sendResult(null);
    }

    public void setSessionToken(final Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        } else if (this.mSession != null) {
            throw new IllegalStateException("The session token has already been set.");
        } else {
            this.mSession = token;
            this.mHandler.post(new Runnable() {
                public void run() {
                    for (IBinder iBinder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                        ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(iBinder);
                        try {
                            connectionRecord.c.onConnect(connectionRecord.d.getRootId(), token, connectionRecord.d.getExtras());
                        } catch (RemoteException unused) {
                            StringBuilder sb = new StringBuilder("Connection for ");
                            sb.append(connectionRecord.a);
                            sb.append(" is no longer valid.");
                            MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
                        }
                    }
                }
            });
        }
    }

    @Nullable
    public Token getSessionToken() {
        return this.mSession;
    }

    public void notifyChildrenChanged(@NonNull final String str) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mHandler.post(new Runnable() {
            public void run() {
                for (IBinder iBinder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                    ConnectionRecord connectionRecord = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get(iBinder);
                    if (connectionRecord.e.contains(str)) {
                        MediaBrowserServiceCompat.this.performLoadChildren(str, connectionRecord);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean isValidPackage(String str, int i) {
        if (str == null) {
            return false;
        }
        for (String equals : getPackageManager().getPackagesForUid(i)) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void addSubscription(String str, ConnectionRecord connectionRecord) {
        connectionRecord.e.add(str);
        performLoadChildren(str, connectionRecord);
    }

    /* access modifiers changed from: private */
    public void performLoadChildren(final String str, final ConnectionRecord connectionRecord) {
        AnonymousClass3 r0 = new Result<List<MediaItem>>(str) {
            /* access modifiers changed from: 0000 */
            public /* synthetic */ void onResultSent(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    StringBuilder sb = new StringBuilder("onLoadChildren sent null list for id ");
                    sb.append(str);
                    throw new IllegalStateException(sb.toString());
                }
                if (MediaBrowserServiceCompat.this.mConnections.get(connectionRecord.c.asBinder()) == connectionRecord) {
                    try {
                        connectionRecord.c.onLoadChildren(str, list);
                    } catch (RemoteException unused) {
                        StringBuilder sb2 = new StringBuilder("Calling onLoadChildren() failed for id=");
                        sb2.append(str);
                        sb2.append(" package=");
                        sb2.append(connectionRecord.a);
                    }
                }
            }
        };
        onLoadChildren(str, r0);
        if (!r0.isDone()) {
            StringBuilder sb = new StringBuilder("onLoadChildren must call detach() or sendResult() before returning for package=");
            sb.append(connectionRecord.a);
            sb.append(" id=");
            sb.append(str);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public void performLoadItem(String str, final ResultReceiver resultReceiver) {
        AnonymousClass4 r0 = new Result<MediaItem>(str) {
            /* access modifiers changed from: 0000 */
            public /* synthetic */ void onResultSent(Object obj) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, (MediaItem) obj);
                resultReceiver.send(0, bundle);
            }
        };
        onLoadItem(str, r0);
        if (!r0.isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=".concat(String.valueOf(str)));
        }
    }
}
