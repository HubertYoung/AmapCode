package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ParcelableNetworkListener extends IInterface {

    public static abstract class Stub extends Binder implements ParcelableNetworkListener {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.ParcelableNetworkListener";
        static final int TRANSACTION_getListenerState = 5;
        static final int TRANSACTION_onDataReceived = 1;
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onInputStreamGet = 4;
        static final int TRANSACTION_onResponseCode = 3;

        static class Proxy implements ParcelableNetworkListener {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultProgressEvent != null) {
                        obtain.writeInt(1);
                        defaultProgressEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultFinishEvent != null) {
                        obtain.writeInt(1);
                        defaultFinishEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = true;
                    if (parcelableHeader != null) {
                        obtain.writeInt(1);
                        parcelableHeader.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(parcelableInputStream != null ? parcelableInputStream.asBinder() : null);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public byte getListenerState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
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

        public static ParcelableNetworkListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ParcelableNetworkListener)) {
                return new Proxy(iBinder);
            }
            return (ParcelableNetworkListener) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [anetwork.channel.aidl.DefaultProgressEvent] */
        /* JADX WARNING: type inference failed for: r0v4, types: [anetwork.channel.aidl.DefaultProgressEvent] */
        /* JADX WARNING: type inference failed for: r0v5, types: [anetwork.channel.aidl.DefaultFinishEvent] */
        /* JADX WARNING: type inference failed for: r0v7, types: [anetwork.channel.aidl.DefaultFinishEvent] */
        /* JADX WARNING: type inference failed for: r0v8, types: [anetwork.channel.aidl.ParcelableHeader] */
        /* JADX WARNING: type inference failed for: r0v10, types: [anetwork.channel.aidl.ParcelableHeader] */
        /* JADX WARNING: type inference failed for: r0v11 */
        /* JADX WARNING: type inference failed for: r0v12 */
        /* JADX WARNING: type inference failed for: r0v13 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], anetwork.channel.aidl.DefaultFinishEvent, anetwork.channel.aidl.DefaultProgressEvent, anetwork.channel.aidl.ParcelableHeader]
          uses: [anetwork.channel.aidl.DefaultProgressEvent, anetwork.channel.aidl.DefaultFinishEvent, anetwork.channel.aidl.ParcelableHeader]
          mth insns count: 53
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
        /* JADX WARNING: Unknown variable types count: 4 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) throws android.os.RemoteException {
            /*
                r2 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r3 == r0) goto L_0x008c
                r0 = 0
                switch(r3) {
                    case 1: goto L_0x0071;
                    case 2: goto L_0x0056;
                    case 3: goto L_0x0033;
                    case 4: goto L_0x001f;
                    case 5: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r3 = super.onTransact(r3, r4, r5, r6)
                return r3
            L_0x000f:
                java.lang.String r3 = "anetwork.channel.aidl.ParcelableNetworkListener"
                r4.enforceInterface(r3)
                byte r3 = r2.getListenerState()
                r5.writeNoException()
                r5.writeByte(r3)
                return r1
            L_0x001f:
                java.lang.String r3 = "anetwork.channel.aidl.ParcelableNetworkListener"
                r4.enforceInterface(r3)
                android.os.IBinder r3 = r4.readStrongBinder()
                anetwork.channel.aidl.ParcelableInputStream r3 = anetwork.channel.aidl.ParcelableInputStream.Stub.asInterface(r3)
                r2.onInputStreamGet(r3)
                r5.writeNoException()
                return r1
            L_0x0033:
                java.lang.String r3 = "anetwork.channel.aidl.ParcelableNetworkListener"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                int r6 = r4.readInt()
                if (r6 == 0) goto L_0x004b
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableHeader> r6 = anetwork.channel.aidl.ParcelableHeader.c
                java.lang.Object r4 = r6.createFromParcel(r4)
                r0 = r4
                anetwork.channel.aidl.ParcelableHeader r0 = (anetwork.channel.aidl.ParcelableHeader) r0
            L_0x004b:
                boolean r3 = r2.onResponseCode(r3, r0)
                r5.writeNoException()
                r5.writeInt(r3)
                return r1
            L_0x0056:
                java.lang.String r3 = "anetwork.channel.aidl.ParcelableNetworkListener"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x006a
                android.os.Parcelable$Creator<anetwork.channel.aidl.DefaultFinishEvent> r3 = anetwork.channel.aidl.DefaultFinishEvent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                anetwork.channel.aidl.DefaultFinishEvent r0 = (anetwork.channel.aidl.DefaultFinishEvent) r0
            L_0x006a:
                r2.onFinished(r0)
                r5.writeNoException()
                return r1
            L_0x0071:
                java.lang.String r3 = "anetwork.channel.aidl.ParcelableNetworkListener"
                r4.enforceInterface(r3)
                int r3 = r4.readInt()
                if (r3 == 0) goto L_0x0085
                android.os.Parcelable$Creator<anetwork.channel.aidl.DefaultProgressEvent> r3 = anetwork.channel.aidl.DefaultProgressEvent.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r4)
                r0 = r3
                anetwork.channel.aidl.DefaultProgressEvent r0 = (anetwork.channel.aidl.DefaultProgressEvent) r0
            L_0x0085:
                r2.onDataReceived(r0)
                r5.writeNoException()
                return r1
            L_0x008c:
                java.lang.String r3 = "anetwork.channel.aidl.ParcelableNetworkListener"
                r5.writeString(r3)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.aidl.ParcelableNetworkListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    byte getListenerState() throws RemoteException;

    void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException;

    void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException;

    void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException;

    boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException;
}
