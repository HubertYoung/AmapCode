package android.support.v4.media;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.os.ResultReceiver;

public interface IMediaBrowserServiceCompat extends IInterface {

    public static abstract class Stub extends Binder implements IMediaBrowserServiceCompat {
        private static final String DESCRIPTOR = "android.support.v4.media.IMediaBrowserServiceCompat";
        static final int TRANSACTION_addSubscription = 3;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getMediaItem = 5;
        static final int TRANSACTION_removeSubscription = 4;

        static class Proxy implements IMediaBrowserServiceCompat {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void connect(String str, Bundle bundle, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iMediaBrowserServiceCompatCallbacks != null ? iMediaBrowserServiceCompatCallbacks.asBinder() : null);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void disconnect(IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iMediaBrowserServiceCompatCallbacks != null ? iMediaBrowserServiceCompatCallbacks.asBinder() : null);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void addSubscription(String str, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMediaBrowserServiceCompatCallbacks != null ? iMediaBrowserServiceCompatCallbacks.asBinder() : null);
                    this.a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void removeSubscription(String str, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iMediaBrowserServiceCompatCallbacks != null ? iMediaBrowserServiceCompatCallbacks.asBinder() : null);
                    this.a.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getMediaItem(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (resultReceiver != null) {
                        obtain.writeInt(1);
                        resultReceiver.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaBrowserServiceCompat asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMediaBrowserServiceCompat)) {
                return new Proxy(iBinder);
            }
            return (IMediaBrowserServiceCompat) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v4, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v5, types: [android.support.v4.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.support.v4.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v8 */
        /* JADX WARNING: type inference failed for: r0v9 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.support.v4.os.ResultReceiver, android.os.Bundle]
          uses: [android.os.Bundle, android.support.v4.os.ResultReceiver]
          mth insns count: 48
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) throws android.os.RemoteException {
            /*
                r2 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r3 == r0) goto L_0x008a
                r0 = 0
                switch(r3) {
                    case 1: goto L_0x0066;
                    case 2: goto L_0x0055;
                    case 3: goto L_0x0040;
                    case 4: goto L_0x002b;
                    case 5: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r3 = super.onTransact(r3, r4, r5, r6)
                return r3
            L_0x000f:
                java.lang.String r3 = "android.support.v4.media.IMediaBrowserServiceCompat"
                r4.enforceInterface(r3)
                java.lang.String r3 = r4.readString()
                int r5 = r4.readInt()
                if (r5 == 0) goto L_0x0027
                android.os.Parcelable$Creator<android.support.v4.os.ResultReceiver> r5 = android.support.v4.os.ResultReceiver.CREATOR
                java.lang.Object r4 = r5.createFromParcel(r4)
                r0 = r4
                android.support.v4.os.ResultReceiver r0 = (android.support.v4.os.ResultReceiver) r0
            L_0x0027:
                r2.getMediaItem(r3, r0)
                return r1
            L_0x002b:
                java.lang.String r3 = "android.support.v4.media.IMediaBrowserServiceCompat"
                r4.enforceInterface(r3)
                java.lang.String r3 = r4.readString()
                android.os.IBinder r4 = r4.readStrongBinder()
                android.support.v4.media.IMediaBrowserServiceCompatCallbacks r4 = android.support.v4.media.IMediaBrowserServiceCompatCallbacks.Stub.asInterface(r4)
                r2.removeSubscription(r3, r4)
                return r1
            L_0x0040:
                java.lang.String r3 = "android.support.v4.media.IMediaBrowserServiceCompat"
                r4.enforceInterface(r3)
                java.lang.String r3 = r4.readString()
                android.os.IBinder r4 = r4.readStrongBinder()
                android.support.v4.media.IMediaBrowserServiceCompatCallbacks r4 = android.support.v4.media.IMediaBrowserServiceCompatCallbacks.Stub.asInterface(r4)
                r2.addSubscription(r3, r4)
                return r1
            L_0x0055:
                java.lang.String r3 = "android.support.v4.media.IMediaBrowserServiceCompat"
                r4.enforceInterface(r3)
                android.os.IBinder r3 = r4.readStrongBinder()
                android.support.v4.media.IMediaBrowserServiceCompatCallbacks r3 = android.support.v4.media.IMediaBrowserServiceCompatCallbacks.Stub.asInterface(r3)
                r2.disconnect(r3)
                return r1
            L_0x0066:
                java.lang.String r3 = "android.support.v4.media.IMediaBrowserServiceCompat"
                r4.enforceInterface(r3)
                java.lang.String r3 = r4.readString()
                int r5 = r4.readInt()
                if (r5 == 0) goto L_0x007e
                android.os.Parcelable$Creator r5 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r4)
                r0 = r5
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x007e:
                android.os.IBinder r4 = r4.readStrongBinder()
                android.support.v4.media.IMediaBrowserServiceCompatCallbacks r4 = android.support.v4.media.IMediaBrowserServiceCompatCallbacks.Stub.asInterface(r4)
                r2.connect(r3, r0, r4)
                return r1
            L_0x008a:
                java.lang.String r3 = "android.support.v4.media.IMediaBrowserServiceCompat"
                r5.writeString(r3)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.IMediaBrowserServiceCompat.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void addSubscription(String str, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException;

    void connect(String str, Bundle bundle, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException;

    void disconnect(IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException;

    void getMediaItem(String str, ResultReceiver resultReceiver) throws RemoteException;

    void removeSubscription(String str, IMediaBrowserServiceCompatCallbacks iMediaBrowserServiceCompatCallbacks) throws RemoteException;
}
